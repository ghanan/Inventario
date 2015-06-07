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
        comprueba_almacenamiento();
    }

    private void comprueba_almacenamiento() {
        if (!bdatos.disponible) {
            Toast.makeText(this, bdatos.log, Toast.LENGTH_LONG).show();
        }
    }

    public void onButtonClick(View v) {
        //System.out.println("clickado");
        if (v.getId() == R.id.botonAlta) {
            if (!bdatos.disponible) {
                Toast.makeText(this, R.string.msg_no_puedo_escribir_ext, Toast.LENGTH_LONG).show();
                return;
            }
            Intent pantalla_alta = new Intent(this, AltaActivity.class);
            startActivity(pantalla_alta);
        }
    }

}
