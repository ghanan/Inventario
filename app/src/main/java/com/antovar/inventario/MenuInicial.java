package com.antovar.inventario;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;

public class MenuInicial extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuinicial);
    }

    public void onButtonClick(View v) {
        /*System.out.println("clickado");*/
        if (v.getId() == R.id.botonAlta) {
            Intent pantalla = new Intent(this, AltaActivity.class);
            startActivity(pantalla);
        }
    }
}
