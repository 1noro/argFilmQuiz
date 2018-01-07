package com.a22g.argfilmquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;


public class Menu extends AppCompatActivity {

    Button botonPeliculas;
    Button botonReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // MUY NECESARIO
        SDMng.iniSavedData(this);

        botonPeliculas = findViewById(R.id.films_button);
        botonPeliculas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLevelList = new Intent(getApplicationContext(), LevelList.class);
                startActivity(intentLevelList);
            }
        });

        botonReset = findViewById(R.id.reset_button);
        botonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SDMng.clearSavedData(getApplicationContext());
            }
        });

    }
}
