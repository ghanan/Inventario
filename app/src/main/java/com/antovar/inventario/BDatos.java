package com.antovar.inventario;


import android.app.Activity;
import android.app.Application;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BDatos extends Application {

    private final String nombredb = "InventarioCasa";
    public boolean disponible = false;
    public String log = "";
    private Activity vista;
    private File fichero;
    private FileWriter fw;

    public BDatos() {
        if (!isExternalStorageWritable()) {
            log = "Sin acceso a almcenamiento externo";
            return;
        }
        File path = Environment.getExternalStorageDirectory();
        File dir = new File(path, nombredb);
        if (!dir.mkdir() && !dir.isDirectory()) {
            log = "No se puede crear el directorio " + nombredb;
            return;
        }
        fichero = new File(dir, nombredb + ".csv");
        if (!fichero.exists()) {
            try { fichero.createNewFile(); }
            catch (IOException e) {
                log = "No se puede crear el fichero " + nombredb + ".csv";
                return;
            }
        }
        disponible = true;
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

//    public boolean alta(String[] campos) {
    public boolean alta(String reg) {
        try {
            fw = new FileWriter(fichero, true);
        } catch (IOException e) {
            log = "Error en alta al abrir fichero";
            return false;
        }
        try {
            fw.append(reg + "\n");
        } catch (IOException e) {
            log = "Error en alta al escribir en fichero";
            return false;
        }
        try {
            fw.close();
        } catch (IOException e) {
            log = "Error en alta al cerrar el fichero";
            return false;
        }
        return true;
    }

}
