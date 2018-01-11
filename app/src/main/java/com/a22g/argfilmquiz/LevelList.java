package com.a22g.argfilmquiz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LevelList extends AppCompatActivity {

    private String levelData = "[\"NIVEL 1\", \"NIVEL 2\", \"NIVEL 3\"]";
    //private String levelData = "[\"NIVEL 1\", \"NIVEL 2\", \"NIVEL 3\", \"NIVEL 4\"]";

    private JSONArray jsonResponse;

    public void levelNotAvaliable(int leelId) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        //alertDialog.setTitle("INFORMACIÓN");
        alertDialog.setMessage("El nivel al que intentas acceder no se ha desbloqueado aún porque no has logrado acertar "+SDMng.levelMin+" películas en el nivel anterior.\n\nTe faltan: "+SDMng.howManyFilmsAreMissing(leelId));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "CERRAR",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        alertDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        int i;
        String levelName;

        try {
            jsonResponse=new JSONArray(levelData);
        } catch (JSONException e) {
            Log.d("##### EXCPETION","new JSONArray(levelData)");
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_list);
        setTitle(R.string.films_button);

        LinearLayout ll = findViewById(R.id.level_list_layout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,5,0,5);

        i=0;
        while (i<jsonResponse.length()) {
            Button myButton = new Button(this);

            try {
                levelName=(String)jsonResponse.get(i);
                myButton.setText(levelName);
                myButton.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                myButton.setTextColor(getResources().getColor(R.color.c222));
                myButton.setTextSize(17);
                JSONObject para = new JSONObject("{\"id\":"+i+",\"levelName\":\""+levelName+"\"}");
                myButton.setOnClickListener(new MyOnClickListener2(para) {
                    @Override
                    public void onClick(View view) {
                        try {
                            int id = this.params.getInt("id");
                            if (SDMng.checkIfLevelIsAvaliable(id)) {
                                Intent intentLevel = new Intent(getApplicationContext(), Level.class);
                                intentLevel.putExtra("id", id);
                                intentLevel.putExtra("levelName", this.params.getString("levelName"));
                                startActivity(intentLevel);
                            } else {
                                levelNotAvaliable(id);
                            }
                        } catch (JSONException e) {
                            Log.d("##### EXCPETION","this.params.getInt(\"id\") || this.params.getString(\"levelName\")");
                            e.printStackTrace();
                        }
                    }
                });
            } catch (JSONException e) {
                Log.d("##### EXCPETION", "jsonResponse.get || new JSONObject(...)");
                e.printStackTrace();
            }

            ll.addView(myButton, lp);
            i++;
        }

    }
}
