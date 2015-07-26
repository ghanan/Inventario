package com.antovar.inventario;


//import android.app.Activity;
import android.app.Application;
import android.os.Environment;
//import android.widget.Toast;

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
import android.widget.Spinner;

public class BDatos extends Application {

	public final int nCAMPOS = 8;
	public final int iNOMBRE = 0;
	public final int iNOTA = 1;
	public final int iCUARTO = 2;
	public final int iMUEBLE = 3;
	public final int iCUERPO = 4;
	public final int iHUECO = 5;
	public final int iFILA_COL = 6;
	public final int iCLAVES = 7;
	public final String FS = ";";
	public final String CS = ",";
	
    private final String nombredb = "InventarioCasa";
    public boolean disponible = false;
    public String log = "";
    //private Activity vista;
    private File fichero;
    private FileWriter fw;
	private int numRegistros = 0;
	private String[] aLinea = new String[nCAMPOS]; //registro de trabajo
	public ArrayList<String> aNombre = new ArrayList<>(); //para cargar datos en mem
	public ArrayList<String> aNota = new ArrayList<>();
	public ArrayList<String> aClaves = new ArrayList<>();
	public ArrayList<String> aCuarto = new ArrayList<>();
	public ArrayList<String> aMueble = new ArrayList<>();
	public ArrayList<String> aCuerpo = new ArrayList<>();
	public ArrayList<String> aHueco = new ArrayList<>();
	public ArrayList<String> aFila_col = new ArrayList<>();
	
	public List<String> cuartos = new ArrayList<>();
	public List<String> muebles = new ArrayList<>();
	public List<String> cuerpos = new ArrayList<>();
	public List<String> huecos = new ArrayList<>();
	public List<String> fila_cols = new ArrayList<>();
	public List<String> claves = new ArrayList<>();
	
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
		if (!lee_fichero_a_arrays()) return;
		rellena_arrays();
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
		procesa_linea(reg);
        return true;
    }

	private boolean lee_fichero_a_arrays() {
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
				numRegistros += 1;
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
		aLinea = linea.split(FS);
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

	private void rellena_arrays() {
		rellena_un_array(aCuarto, cuartos);
		rellena_un_array(aMueble, muebles);
		rellena_un_array(aCuerpo, cuerpos);
		rellena_un_array(aHueco, huecos);
		rellena_un_array(aFila_col, fila_cols);
		rellena_claves();
	}

	private void rellena_un_array(List<String> origen, List<String> destino) {
		destino.add("");
		for (String valor: origen) {
			if (!destino.contains(valor)) destino.add(valor);
		}
		Collections.sort(destino, String.CASE_INSENSITIVE_ORDER);
		destino.add("NUEVO");
	}

	// para cuando se añade un valor nuevo a mano
	public void add_valor(Spinner desple, List<String> lista, String valor) {
		lista.remove("NUEVO");
		lista.add(valor);
		Collections.sort(lista, String.CASE_INSENSITIVE_ORDER);
		lista.add("NUEVO");
		desple.setSelection(lista.indexOf(valor));
	}

	private void rellena_claves() {
		//claves.add("");
		for (String strClaves: aClaves) {
			if (strClaves.length() > 0)
				for (String sClave: strClaves.split(CS)) {
					if (!claves.contains(sClave)) claves.add(sClave);
				}
		}
		Collections.sort(claves, String.CASE_INSENSITIVE_ORDER);
		claves.add("NUEVA");
	}

	public void anade_clave(String nueva) {
		if (claves.contains(nueva)) return;
		claves.remove("NUEVA");
		claves.add(nueva);
		Collections.sort(claves, String.CASE_INSENSITIVE_ORDER);
		claves.add("NUEVA");
	}

	public String getCampo(int i) {
		return aLinea[i];
	}

}
