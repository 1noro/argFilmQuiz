package com.a22g.argfilmquiz;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

// ANTES EXTENDIA A: AppCompatActivity (cambiado para eliminar el titulo)
public class LevelItem extends Activity {

    private JSONObject JSONobj;
    private JSONArray itemFrames;
    private JSONArray itemTitles;

    private ImageView frameView;
    private Button btnSubmit;
    private EditText textImput;

    private boolean newSuccess=false;
    private int levelImageViewId;

    private boolean checkItemOk(String id) {
        return SDMng.savedProgress.contains(id);
    }

    private ArrayList<String> JAtoAL2(JSONArray JSONa) throws JSONException {
        ArrayList<String> out = new ArrayList<>();
        // (JSONArray) JSONa --> (ArrayList<String>) out
        if (JSONa != null) {
            for (int i=0;i<JSONa.length();i++){
                out.add(JSONa.getString(i).toLowerCase());
            }
        }
        return out;
    }

    private void displaySuccess() throws JSONException {
        LinearLayout ll = findViewById(R.id.level_item_linearLayout2);
        Button btnSubmit = findViewById(R.id.level_item_btnSubmit);
        EditText etTit = findViewById(R.id.level_item_editText);
        ll.removeView(etTit);
        ll.removeView(btnSubmit);

        /*LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) ll.getLayoutParams();
        lp.setMargins(0,0,0,0);
        ll.setLayoutParams(lp);*/

        TextView txtv = new TextView(getApplicationContext());
        txtv.setText((String) itemTitles.get(0));
        txtv.setGravity(Gravity.CENTER);
        txtv.setTextColor(Color.WHITE);
        txtv.setBackgroundColor(getResources().getColor(R.color.c555));
        txtv.setTextSize(30);
        txtv.setPadding(10,10,10,10);
        ll.addView(txtv);
    }

    public void comprobacion() {
        try {

            JSONArray itemTitles = JSONobj.getJSONArray("title");
            String itemId = JSONobj.getString("id");
            ArrayList<String> itemTitlesAL = JAtoAL2(itemTitles);

                            /*LinearLayout ll = findViewById(R.id.level_item_linearLayout2);
                            Button btnSubmit = findViewById(R.id.level_item_btnSubmit);*/
            EditText etTit = findViewById(R.id.level_item_editText);

            if (itemTitlesAL.contains(etTit.getText().toString().toLowerCase())) {
                // ### success
                newSuccess=true;
                displaySuccess();
                SDMng.saveProgress(getApplicationContext(), itemId);
            } else {
                // ### FAIL
                etTit.setTextColor(Color.RED);
            }

        } catch (JSONException e) {
            Log.d("##### EXCPETION", "MUCHAS COSAS");
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        if (newSuccess) {
            returnIntent.putExtra("result", levelImageViewId);
            setResult(Activity.RESULT_OK, returnIntent);
        } else {
            setResult(Activity.RESULT_CANCELED, returnIntent);
        }
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE); // DEBE IR ANTES DEL SETCONTENTVIEW
        setContentView(R.layout.activity_level_item2);

        frameView =findViewById(R.id.level_item_imageView);
        btnSubmit=findViewById(R.id.level_item_btnSubmit);
        textImput=findViewById(R.id.level_item_editText);

        Bundle b = getIntent().getExtras();
        String JSONstr = b.getString("levelItemJson","[]");


        try {
            JSONobj=new JSONObject(JSONstr);
            levelImageViewId = JSONobj.getInt("levelImageViewId");
            itemFrames = JSONobj.getJSONArray("frame");
            itemTitles = JSONobj.getJSONArray("title");

            Context context = frameView.getContext();
            String frameStrId = itemFrames.getString(0);
            int frameId = context.getResources().getIdentifier(frameStrId, "drawable", context.getPackageName());
            frameView.setImageResource(frameId);

            if (!checkItemOk(JSONobj.getString("id"))) {

                textImput.setOnKeyListener(new View.OnKeyListener() {
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        // If the event is a key-down event on the "enter" button
                        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                                (keyCode == KeyEvent.KEYCODE_ENTER)) {
                            // Perform action on key press
                            //Toast.makeText(HelloFormStuff.this, edittext.getText(), Toast.LENGTH_SHORT).show();
                            Log.d("### LALALALALA", "SE HA PULSADO ENTER XD");
                            comprobacion();
                            return true;
                        }
                        return false;
                    }
                });

                btnSubmit.setOnClickListener(new MyOnClickListener2(JSONobj) {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(View view) {
                        comprobacion();
                    }
                });

            } else {
                // YA ACERTADO
                displaySuccess();
            }

        } catch (Exception e) {
            Log.d("##### EXCPETION","readJsonFile || MOAR THINGS");
            e.printStackTrace();
        }

    }
}
