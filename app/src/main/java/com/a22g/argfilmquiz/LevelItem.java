package com.a22g.argfilmquiz;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.Window;
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

    JSONObject JSONobj;
    JSONArray itemFrames;
    JSONArray itemTitles;

    ImageView frameView;
    Button btnSubmit;

    public boolean checkItemOk(String id) {
        return SDMng.savedProgress.contains(id);
    }

    public ArrayList<String> JAtoAL2(JSONArray JSONa) throws JSONException {
        ArrayList<String> out = new ArrayList<>();
        // (JSONArray) JSONa --> (ArrayList<String>) out
        if (JSONa != null) {
            for (int i=0;i<JSONa.length();i++){
                out.add(JSONa.getString(i).toLowerCase());
            }
        }
        return out;
    }

    public void displaySuccess() throws JSONException {
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
        ll.addView(txtv);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE); // DEBE IR ANTES DEL SETCONTENTVIEW
        setContentView(R.layout.activity_level_item2);

        frameView =findViewById(R.id.level_item_imageView);
        btnSubmit=findViewById(R.id.level_item_btnSubmit);

        Bundle b = getIntent().getExtras();
        String JSONstr = b.getString("levelItemJson","[]");

        try {
            JSONobj=new JSONObject(JSONstr);
            itemFrames = JSONobj.getJSONArray("frame");
            itemTitles = JSONobj.getJSONArray("title");

            Context context = frameView.getContext();
            String frameStrId = itemFrames.getString(0);
            int frameId = context.getResources().getIdentifier(frameStrId, "drawable", context.getPackageName());
            frameView.setImageResource(frameId);

            if (!checkItemOk(JSONobj.getString("id"))) {

                btnSubmit.setOnClickListener(new MyOnClickListener2(JSONobj) {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(View view) {
                        try {

                            JSONArray itemTitles = this.params.getJSONArray("title");
                            String itemId = this.params.getString("id");
                            ArrayList<String> itemTitlesAL = JAtoAL2(itemTitles);

                            LinearLayout ll = findViewById(R.id.level_item_linearLayout2);
                            Button btnSubmit = findViewById(R.id.level_item_btnSubmit);
                            EditText etTit = findViewById(R.id.level_item_editText);

                            if (itemTitlesAL.contains(etTit.getText().toString().toLowerCase())) {
                                // ### success
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
