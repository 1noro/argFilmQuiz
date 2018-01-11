package com.a22g.argfilmquiz;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;

public class Menu extends Activity {

    private Button botonPeliculas;
    private Button botonCreditos;
    private Button botonImport;
    private Button botonExport;
    private Button botonReset;

    public void importProgress() {
        final EditText txtUrl = new EditText(this);

        // Set the default text to a link of the Queen
        //txtUrl.setHint("hola, soy una pista xD");
        txtUrl.setSingleLine(true);

        new AlertDialog.Builder(this)
            .setTitle("IMPORTAR PROGRESO")
            .setMessage("Pegue aquí el código exportado anteriormente.")
            .setView(txtUrl)
            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String txt = txtUrl.getText().toString();
                    try {
                        byte[] data = Base64.decode(txt, Base64.DEFAULT);
                        String txt2 = new String(data, "UTF-8");
                        Log.d("### LALALALALA","el texto es: '"+txt+"' --> '"+txt2+"'");
                        if (SDMng.validateSavedData(txt2)) {
                            SDMng.importSavedData(getApplicationContext(),txt2);
                            //Log.d("### LALALALALA","TEXTO VALIDO");
                        } else {
                            Log.d("### LALALALALA","TEXTO NO VALIDO");
                        }
                    } catch (UnsupportedEncodingException e) {
                        Log.d("### EXCEPTION", "FALLO EN ''txt = new String(data, \"UTF-8\");''");
                        e.printStackTrace();
                    } catch (Exception e) {
                        Log.d("### EXCEPTION", "FALLO EN ''byte[] data = Base64.decode(txt, Base64.DEFAULT);''");
                        e.printStackTrace();
                    }
                }
            })
            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                }
            })
            .show();
    }

    public void exportProgress() {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("JSON codificado en base64 de la partida guardada de argFilmQuiz", SDMng.exportSavedData());
        clipboard.setPrimaryClip(clip);
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("EXPORTAR PROGRESO");
        alertDialog.setMessage("Se ha copiado tu progreso en el portapapeles guardalo como texto donde consideres oportuno.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Cerrar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void clearSavedDataButtonConfirm() {
        new AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("BORRAR PROGRESO")
            .setMessage("¿Seguro que desea borrar todo su progreso?")
            .setPositiveButton("Si", new DialogInterface.OnClickListener()
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
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Cerrar",
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

        botonImport = findViewById(R.id.import_button);
        botonImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                importProgress();
            }
        });

        botonExport = findViewById(R.id.export_button);
        botonExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exportProgress();
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
