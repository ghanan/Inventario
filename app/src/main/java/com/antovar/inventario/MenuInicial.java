package com.antovar.inventario;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MenuInicial extends Activity {

    private BDatos bdatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuinicial);
        bdatos = (BDatos)getApplicationContext();
//        comprueba_almacenamiento();
        //if (bdatos.nombredb.equals("")
        //setTitle(bdatos.nombredb.equals("") ? bdatos.nombredb : R.string.title_no_abierto);
        setTitle(bdatos.nombredb.equals("") ? getString(R.string.title_no_abierto) : bdatos.nombredb);
        if (!bdatos.disponible) Toast.makeText(this, bdatos.log, Toast.LENGTH_LONG).show();
    }

//    private void comprueba_almacenamiento() {
//        if (!bdatos.disponible) {
//            Toast.makeText(this, bdatos.log, Toast.LENGTH_LONG).show();
//        }
//    }

    public void onButtonClick(View v) {
        if (v.getId() == R.id.botonSalir) {
            finish();
            return;
        }
        if (!bdatos.disponible) {
            Toast.makeText(this, bdatos.log, Toast.LENGTH_LONG).show();
            return;
        }
        if (bdatos.nombredb.equals("")) {
            Toast.makeText(this, getString(R.string.msg_no_abierto), Toast.LENGTH_LONG).show();
            return;
        }
        Intent pantalla_alta = new Intent(this, AltaActivity.class);
        if (v.getId() == R.id.botonAlta) {
            pantalla_alta.putExtra("alta", true);
        } else {
            pantalla_alta.putExtra("alta", false);
        }
        startActivity(pantalla_alta);
    }

}
