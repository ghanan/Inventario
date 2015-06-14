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

import java.util.Collections;
import java.util.*;
import android.text.style.*;

public class BDatos extends Application {

	private final int nCAMPOS = 8;
	private final int iNOMBRE = 0;
	private final int iNOTA = 1;
	private final int iCUARTO = 2;
	private final int iMUEBLE = 3;
	private final int iCUERPO = 4;
	private final int iHUECO = 5;
	private final int iFILA_COL = 6;
	private final int iCLAVES = 7;
	private final String CS = ";";
	
    private final String nombredb = "InventarioCasa";
    public boolean disponible = false;
    public String log = "";
    private Activity vista;
    private File fichero;
    private FileWriter fw;
	private String[] aLinea = new String[nCAMPOS];
	public ArrayList<String> aNombre = new ArrayList<String>();
	public ArrayList<String> aNota = new ArrayList<String>();
	public ArrayList<String> aClaves = new ArrayList<String>();
	public ArrayList<String> aCuarto = new ArrayList<String>();
	public ArrayList<String> aMueble = new ArrayList<String>();
	public ArrayList<String> aCuerpo = new ArrayList<String>();
	public ArrayList<String> aHueco = new ArrayList<String>();
	public ArrayList<String> aFila_col = new ArrayList<String>();
	
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
		aLinea = linea.split(CS);
		aNombre.add(aLinea[iNOMBRE]);
		aNota.add(aLinea[iNOTA]);
		aCuarto.add(aLinea[iCUARTO]);
		aMueble.add(aLinea[iMUEBLE]);
		aCuerpo.add(aLinea[iCUERPO]);
		aHueco.add(aLinea[iHUECO]);
		aFila_col.add(aLinea[iFILA_COL]);
		aClaves.add(aLinea[iCLAVES]);
//		System.out.println(aClaves.toString());
		
	}
	
}
