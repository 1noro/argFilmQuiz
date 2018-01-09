package com.a22g.argfilmquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class Menu extends Activity {

    private Button botonPeliculas;
    private Button botonReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // DEBE IR ANTES DEL SETCONTENTVIEW
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
