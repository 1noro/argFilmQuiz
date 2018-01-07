package com.a22g.argfilmquiz;

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

    private String levelData = "[\"NIVEL 1\", \"NIVEL 2\"]";
    //private String levelData = "[\"NIVEL 1\", \"NIVEL 2\", \"NIVEL 3\", \"NIVEL 4\"]";

    private JSONArray jsonResponse;

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
                        Intent intentLevel = new Intent(getApplicationContext(), Level.class);
                        try {
                            intentLevel.putExtra("id", this.params.getInt("id"));
                            intentLevel.putExtra("levelName", this.params.getString("levelName"));
                        } catch (JSONException e) {
                            Log.d("##### EXCPETION","this.params.getInt(\"id\") || this.params.getString(\"levelName\")");
                            e.printStackTrace();
                        }
                        startActivity(intentLevel);
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
