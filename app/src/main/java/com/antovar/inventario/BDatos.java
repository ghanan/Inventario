package com.antovar.inventario;


import android.app.Activity;
import android.app.Application;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class BDatos extends Application {

    private final String nombredb = "InventarioCasa";
    public boolean disponible = false;
    public String log = "";
    private Activity vista;
    private File fichero;
    private FileWriter fw;
	public String[] cuartos = {"", "salon", "NUEVO"};

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
		if (!lee_arrays()) return;
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

	
	
	private boolean lee_arrays() {
		FileReader fr;
		try { fr = new FileReader(fichero); }
		catch (IOException e) {
			log = "Error al abrir fichero para leer";
            return false;
		}
		BufferedReader br = new BufferedReader(fr); 
		String s;
		boolean fin = false;
		while ( !fin ) {
			try { s = br.readLine(); }
			catch (IOException e) {
				log = "Error al leer fichero";
				return false;
			}
			if (s == null) fin = true;
			else {
				procesa_linea(s);
			}
		}
		try { fr.close(); }
		catch (IOException e) {
			log = "Error al cerrar fichero tras lectura";
			return false;
		}
		return true;
	}
	
	private void procesa_linea(String linea) {
		System.out.println(linea);
	}
	
}
