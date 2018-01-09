package com.a22g.argfilmquiz;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class Menu extends Activity {

    private Button botonPeliculas;
    private Button botonCreditos;
    private Button botonReset;

    public void clearSavedDataButtonConfirm() {
        new AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("BORRAR PROGRESO")
            .setMessage("¿Seguro que desea borrar todo su progreso?")
            .setPositiveButton("Yes", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SDMng.clearSavedData(getApplicationContext());
                }

            })
            .setNegativeButton("No", null)
            .show();
    }

    public void showCredits() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("CRÉDITOS");
        alertDialog.setMessage("Creado y editiado por Inoro.\niinnoorroo@gmail.com");
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
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // DEBE IR ANTES DEL SETCONTENTVIEW
        /*ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.f0));*/
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

        botonCreditos = findViewById(R.id.credits_button);
        botonCreditos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCredits();
            }
        });

        botonReset = findViewById(R.id.reset_button);
        botonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SDMng.clearSavedData(getApplicationContext());
                clearSavedDataButtonConfirm();
            }
        });

    }
}
