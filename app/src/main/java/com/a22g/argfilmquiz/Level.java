package com.a22g.argfilmquiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

public class Level extends AppCompatActivity {

    private JSONObject JSONobj;

    private boolean checkItemOk(String id) {
        return SDMng.savedProgress.contains(id);
    }

    private String readJsonFile(int id) throws Exception {

        InputStream is = getResources().openRawResource(id);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
            is.close();
        }

        return writer.toString();
    }

    /* PARA ACTUALIZAR AL VOLVER ATRAS
    @Override
    public void onResume() {  // After a pause OR at startup
        super.onResume();
        //Refresh your stuff here
    }
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int i, id;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        Bundle b = getIntent().getExtras();
        id = b.getInt("id",0);
        setTitle(b.getString("levelName","¿nivel?"));

        try {
            JSONobj=new JSONObject(readJsonFile(R.raw.level_content));
        } catch (Exception e) {
            Log.d("##### EXCPETION","readJsonFile || new JSONArray(levelData)");
            e.printStackTrace();
        }

        LinearLayout ll = findViewById(R.id.level_layout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        try {
            JSONArray moviesArr = JSONobj.getJSONArray("movies");
            JSONArray levelArr = (JSONArray) moviesArr.get(id);

            i=0;
            while (i<=levelArr.length()) {

                JSONObject levelItem = (JSONObject) levelArr.get(i);
                JSONArray itemFrames = levelItem.getJSONArray("frame");
                String frameStrId = itemFrames.getString(0);

                ImageView imgb = new ImageView(this);
                Context context = imgb.getContext();
                int frameId = context.getResources().getIdentifier(frameStrId, "drawable", context.getPackageName());
                imgb.setImageResource(frameId);
                imgb.setAdjustViewBounds(true);

                if (checkItemOk(levelItem.getString("id"))) {
                    imgb.setBackground(getResources().getDrawable(R.drawable.border_true));
                } else {
                    imgb.setBackground(getResources().getDrawable(R.drawable.border_false));
                }
                imgb.setPadding(10,0,10,0);

                imgb.setOnClickListener(new MyOnClickListener2(levelItem) {
                    @Override
                    public void onClick(View view) {
                        Intent intentLevelItem = new Intent(getApplicationContext(), LevelItem.class);
                        intentLevelItem.putExtra("levelItemJson", this.params.toString());
                        startActivity(intentLevelItem);
                    }
                });

                ll.addView(imgb,lp);

                i++;
            }

        } catch (JSONException e) {
            Log.d("##### EXCPETION", "jsonResponse.get(" + id + ")");
            e.printStackTrace();
        }



    }
}
