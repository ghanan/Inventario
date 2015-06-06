package com.antovar.inventario;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

public class MenuInicial extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuinicial);
    }

    public void onButtonClick(View v) {
        //System.out.println("clickado");
        if (v.getId() == R.id.botonAlta) {
            if (!isExternalStorageWritable()) {
                Toast.makeText(this, R.string.msg_no_puedo_escribir_ext, Toast.LENGTH_LONG).show();
                return;
            }
            Intent pantalla = new Intent(this, AltaActivity.class);
            startActivity(pantalla);
        }
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

}
