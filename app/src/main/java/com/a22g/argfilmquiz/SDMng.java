package com.a22g.argfilmquiz;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public final class SDMng {

    public static final String fileName = "argFilmQuiz_saved_data";
    public static ArrayList<String> savedProgress = new ArrayList<>();



    public static ArrayList<String> JAtoAL(JSONArray JSONa) throws JSONException {
        ArrayList<String> out = new ArrayList<>();
        // (JSONArray) JSONa --> (ArrayList<String>) out
        if (JSONa != null) {
            for (int i=0;i<JSONa.length();i++){
                out.add(JSONa.getString(i));
            }
        }
        return out;
    }

    public static JSONArray ALtoJA(ArrayList<String> al) {
        return new JSONArray(al);
    }

    public static void saveData(Context ctx) {
        SharedPreferences.Editor editor = ctx.getSharedPreferences(fileName, MODE_PRIVATE).edit();
        String JSONstr=ALtoJA(savedProgress).toString();
        //Log.d("### LALALALALA","JSON to save: "+JSONstr);
        editor.putString("JSON",JSONstr);
        editor.commit();
    }

    public static void saveProgress (Context ctx, String str) {
        savedProgress.add(str);
        saveData(ctx);
    }

    public static void clearSavedData(Context ctx) {
        SharedPreferences.Editor editor = ctx.getSharedPreferences(fileName, MODE_PRIVATE).edit();
        editor.putString("JSON","[]");
        editor.commit();
        savedProgress = new ArrayList<>();
    }

    public static void iniSavedData(Context ctx) {
        SharedPreferences savedData = ctx.getSharedPreferences(fileName, MODE_PRIVATE);
        String JSONstr = savedData.getString("JSON", null);
        if (JSONstr == null) {
            SharedPreferences.Editor editor = ctx.getSharedPreferences(fileName, MODE_PRIVATE).edit();
            editor.putString("JSON", "[]");
            editor.commit();
            //Log.d("### LALALALALA","CREATED EMPTY JSON SAVE DATA");
            savedData = ctx.getSharedPreferences(fileName, MODE_PRIVATE);
            JSONstr = savedData.getString("JSON", null);
        }
        try {
            SDMng.savedProgress=JAtoAL(new JSONArray(JSONstr));
        } catch (JSONException e) {
            Log.d("### EXCEPTION","FALLO AL EVALUAR --> JSONstr: "+JSONstr);
            e.printStackTrace();
        }
        //Log.d("### LALALALALA","JSONstr: "+JSONstr);
    }

}
