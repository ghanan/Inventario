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

    public boolean almacenamiento_disponible = false;
    public File fichero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuinicial);
        comprueba_almacenamiento();
    }

    private void comprueba_almacenamiento() {
        if (!isExternalStorageWritable()) {
            Toast.makeText(this, R.string.msg_no_acceso_ext, Toast.LENGTH_LONG).show();
            return;
        }
        File path = Environment.getExternalStorageDirectory();
        File dir = new File(path, "InventarioCasa");
        Toast.makeText(this, (CharSequence) dir.toString(), Toast.LENGTH_LONG).show();
        if (dir.mkdir()) {
            Toast.makeText(this, R.string.msg_dir_creado + "InventarioCasa", Toast.LENGTH_LONG).show();
        } else {
            if (!dir.isDirectory()) {
                Toast.makeText(this, R.string.msg_no_creado_dir, Toast.LENGTH_LONG).show();
            }
        }
        fichero = new File(dir, "InventarioCasa.csv");
        if (!fichero.exists()) {
            try {
                if (fichero.createNewFile()) {
                    Toast.makeText(this, R.string.msg_fich_creado + "InventarioCasa.csv", Toast.LENGTH_LONG).show();
                }
            } catch (IOException e) {
                Toast.makeText(this, R.string.msg_no_creado_fich + "InventarioCasa.csv", Toast.LENGTH_LONG).show();
            }
        }
        almacenamiento_disponible = true;

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
            Intent pantalla_alta = new Intent(this, AltaActivity.class);
            pantalla_alta.putExtra("fich", fichero);
            startActivity(pantalla_alta);
        }
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public File getFichero() {
        return fichero;
    }
}
