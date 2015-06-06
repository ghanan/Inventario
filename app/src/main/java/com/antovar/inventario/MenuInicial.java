package com.antovar.inventario;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MenuInicial extends Activity {

    public boolean almacenamiento_disponible;
    public File path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuinicial);
        comprueba_almacenamiento();
    }

    private void comprueba_almacenamiento() {
        if (!isExternalStorageWritable()) {
            Toast.makeText(this, R.string.msg_no_acceso_ext, Toast.LENGTH_LONG).show();
            almacenamiento_disponible = false;
            return;
        }
        File path = Environment.getExternalStorageDirectory();
        File dir = new File(path, "InventarioCasa");
        Toast.makeText(this, (CharSequence) dir.toString(), Toast.LENGTH_LONG).show();
        if (!dir.mkdir())
            if (!dir.isDirectory()) Toast.makeText(this, R.string.msg_no_creado_dir, Toast.LENGTH_LONG).show();
//        } catch (Exception e) {
//            if (!dir.isDirectory()) {
//                Toast.makeText(this, R.string.msg_no_creado_dir, Toast.LENGTH_LONG).show();
//                almacenamiento_disponible = false;
//                return;
//            }

    }

    public void onButtonClick(View v) {
        //System.out.println("clickado");
        if (v.getId() == R.id.botonAlta) {
            if (!almacenamiento_disponible) {
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
