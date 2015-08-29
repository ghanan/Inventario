package com.antovar.inventario;


//import android.app.Activity;
import android.app.Application;
import android.os.Environment;
//import android.widget.Toast;
import android.content.Context;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.*;
import android.text.style.*;
//import android.util.Log;
import android.view.ViewConfiguration;
import android.widget.Spinner;
import android.widget.Toast;

public class BDatos extends Application {

    public final static int nCAMPOS = 9;
    public final static int iNOMBRE = 0;
    public final static int iNOTA = 1;
    public final static int iCUARTO = 2;
    public final static int iMUEBLE = 3;
    public final static int iCUERPO = 4;
    public final static int iHUECO = 5;
    public final static int iFILA_COL = 6;
    public final static int iCLAVES = 7;
    // el campo foto no puede estar vacío, rellenar con un punto
    public final static int iFOTO = 8;
    public final static String FS = ";";
    public final static String CS = ",";
    private final static String PREFIJO = "INV_";
    public final static int INTENT_REGISTROS = 4;
    public final static int INTENT_INVENTARIOS = 5;
    public final static int CAMBIO_INVENTARIO = 1;
    public final static int BORRAR_INVENTARIO = 2;
    public final static boolean LISTA_REGISTROS = true;
    public final static boolean LISTA_INVENTARIOS = false;

    private final static String nombreapp = "Inventario";
    public String nombredb = "";
    public boolean disponible = false;
    public String log = "";
    //private Activity vista;
    //public File dir;
    public File dirAPP;
    public File dirDB;
    private File fichero;
    private FileWriter fw;
    private int numRegistros = 0;
    //private ArrayList<String> dirs_db = new ArrayList<>();
    private String[] aLinea = new String[nCAMPOS]; //registro de trabajo
    public ArrayList<String> aNombre = new ArrayList<>(); //para cargar datos en mem
    public ArrayList<String> aNota = new ArrayList<>();
    public ArrayList<String> aClaves = new ArrayList<>();
    public ArrayList<String> aCuarto = new ArrayList<>();
    public ArrayList<String> aMueble = new ArrayList<>();
    public ArrayList<String> aCuerpo = new ArrayList<>();
    public ArrayList<String> aHueco = new ArrayList<>();
    public ArrayList<String> aFila_col = new ArrayList<>();
    public ArrayList<String> aFoto = new ArrayList<>();

    public List<String> inventarios = new ArrayList<>();
    public List<String> cuartos = new ArrayList<>();
    public List<String> muebles = new ArrayList<>();
    public List<String> cuerpos = new ArrayList<>();
    public List<String> huecos = new ArrayList<>();
    public List<String> fila_cols = new ArrayList<>();
    public List<String> claves = new ArrayList<>();
    public List<String> fotos = new ArrayList<>();

    public List<Integer> posRegistrosSelec = new ArrayList<>();
    public int registro;

    private Context ctx;

    //public BDatos() {
    public void onCreate() {
        super.onCreate();
        ctx = getApplicationContext();
        makeActionOverflowMenuShown();
        if (!isExternalStorageWritable()) {
            log = getString(R.string.msg_sin_almacenamiento);
            return;
        }
        File path = Environment.getExternalStorageDirectory();
        dirAPP = new File(path, nombreapp);
        if (!dirAPP.mkdir() && !dirAPP.isDirectory()) {
            log = getString(R.string.msg_no_creado_dir) + " " + nombreapp;
            return;
        }
        rellena_inventarios();
        if (inventarios.size() == 0) log = getString(R.string.msg_no_hay_inventarios);
        else if (inventarios.size() == 1) abrir_bd(inventarios.get(0));
        else log = getString(R.string.msg_no_abierto);
    }

    private void makeActionOverflowMenuShown() {
        //devices with hardware menu button (e.g. Samsung Note) don't show action overflow menu
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            //Log.d("TAG", e.getLocalizedMessage());
        }
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public void rellena_inventarios() {
        for (File fich: dirAPP.listFiles())
            if (fich.isDirectory())
                if (fich.getName().length() > PREFIJO.length())
                    if (fich.getName().startsWith(PREFIJO))
                        inventarios.add(fich.getName().substring(PREFIJO.length(),fich.getName().length()));
        Collections.sort(inventarios, String.CASE_INSENSITIVE_ORDER);
    }

    public void abrir_bd(String nombre) {
        File _dirDB = new File(dirAPP, PREFIJO+nombre);
        File _fichero = new File(_dirDB, nombre + ".csv");
        if (!_fichero.exists()) {
            log = getString(R.string.msg_no_existe_fichero) + " " + nombre + ".csv";
            return;
        }
        dirDB = _dirDB;
        fichero = _fichero;
        if (!lee_fichero_a_arrays()) {
            disponible = false;
            return;
        }
        rellena_arrays();
        nombredb = nombre;
        disponible = true;
    }

    public void crea_db(String nombre, Context ctx) {
        File _dirDB = new File(dirAPP, PREFIJO+nombre);
        if (_dirDB.exists()) {
            Toast.makeText(ctx, getString(R.string.msg_nombre_repe) + " " + nombre, Toast.LENGTH_LONG).show();
            log = getString(R.string.msg_nombre_repe) + " " + nombre;
            return;
        }
        if (!_dirDB.mkdir()) {
            Toast.makeText(ctx, R.string.msg_no_creado_dir, Toast.LENGTH_LONG).show();
            log = getString(R.string.msg_no_creado_dir) + " " + PREFIJO+nombre;
            return;
        }
        File _fichero = new File(_dirDB, nombre + ".csv");
//        try { _fichero.createNewFile(); }
        try {
            if (!_fichero.createNewFile()) {
                Toast.makeText(ctx, R.string.msg_no_creado_fich, Toast.LENGTH_LONG).show();
                log = getString(R.string.msg_no_creado_fich) + " " + nombredb + ".csv";
                return;
            }
        } catch (IOException e) {
            Toast.makeText(ctx, R.string.msg_no_creado_fich, Toast.LENGTH_LONG).show();
            log = getString(R.string.msg_no_creado_fich) + " " + nombredb + ".csv";
            return;
        }
        inventarios.add(nombre);
        Collections.sort(inventarios, String.CASE_INSENSITIVE_ORDER);
        disponible = false;
        abrir_bd(nombre);
    }

    public void borrar_db(String nombre) {
        File _dirDB = new File(dirAPP, PREFIJO+nombre);
        for (File f: _dirDB.listFiles()) {
            f.deleteOnExit();
            f.delete();
        }
        _dirDB.deleteOnExit();
        _dirDB.delete();
        if (nombredb.equals(nombre)) nombredb = "";
        inventarios.remove(nombre);
    }

    public boolean alta(String reg) {
        try {
            fw = new FileWriter(fichero, true);
        } catch (IOException e) {
//            log = "Error en alta al abrir fichero";
            log = getString(R.string.msg_error_crear_fwriter);
            return false;
        }
        try {
            fw.append(reg + "\n");
        } catch (IOException e) {
//            log = "Error en alta al escribir en fichero";
            log = getString(R.string.msg_error_escribiendo);
            return false;
        }
        try {
            fw.close();
        } catch (IOException e) {
//            log = "Error en alta al cerrar el fichero";
            log = getString(R.string.msg_error_cerrando);
            return false;
        }
        procesa_linea(reg);
        return true;
    }

    public boolean modificar(String reg, String nueva_foto, boolean borrar) {
        try {
            fw = new FileWriter(new File(dirDB, nombredb + ".mod"), false);
        } catch (IOException e) {
            log = getString(R.string.msg_error_crear_fwriter);
            return false;
        }
        for (int i=0; i<registro; i++) {
            try {
                fw.append(aNombre.get(i) + FS + aNota.get(i) + FS + aCuarto.get(i) + FS
                    + aMueble.get(i) + FS + aCuerpo.get(i) + FS + aHueco.get(i) + FS
                    + aFila_col.get(i) + FS + aClaves.get(i) + FS + aFoto.get(i) + "\n");
            } catch (IOException e) {
                log = getString(R.string.msg_error_escribiendo_inicio);
                return false;
            }
        }
        if (borrar) {
            borra_foto(dirDB + "/" + aFoto.get(registro));
        } else {
            try {
                fw.append(reg + "\n");
            } catch (IOException e) {
//				log = "Error en modificar al escribir el nuevo";
                log = getString(R.string.msg_error_modificando);
                return false;
            }
            if (!aFoto.get(registro).equals(".") && !aFoto.get(registro).equals(nueva_foto)) {
                borra_foto(dirDB + "/" + aFoto.get(registro));
            }
        }
        for (int i=registro+1; i<aNombre.size(); i++) {
            try {
                fw.append(aNombre.get(i) + FS + aNota.get(i) + FS + aCuarto.get(i) + FS
                    + aMueble.get(i) + FS + aCuerpo.get(i) + FS + aHueco.get(i) + FS
                    + aFila_col.get(i) + FS + aClaves.get(i) + FS + aFoto.get(i) + "\n");
            } catch (IOException e) {
//				log = "Error en modificar al escribir el resto";
                log = getString(R.string.msg_error_escribiendo_final);
                return false;
            }
        }
        try {
            fw.close();
        } catch (IOException e) {
//			log = "Error en modificar al cerrar el fichero";
            log = getString(R.string.msg_error_cerrando);
            return false;
        }
        File modi = new File(dirDB, nombredb + ".mod");
        File back = new File(dirDB, nombredb + ".bck");
        if (back.exists()) {
            return false;
        }
        if (!fichero.renameTo(back)) {
            return false;
        }
        if (!modi.renameTo(fichero)) {
            return false;
        }
        if (borrar) elimina_de_arrays();
        else actualiza_arrays(reg);
        //procesa_linea(reg);
        back.delete();
        modi.delete();
        return true;
    }

    private void actualiza_arrays(String reg) {
        String[] campos = reg.split(FS);
        aNombre.set(registro, campos[iNOMBRE]);
        aNota.set(registro, campos[iNOTA]);
        aCuarto.set(registro, campos[iCUARTO]);
        aMueble.set(registro, campos[iMUEBLE]);
        aCuerpo.set(registro, campos[iCUERPO]);
        aHueco.set(registro, campos[iHUECO]);
        aFila_col.set(registro, campos[iFILA_COL]);
        aClaves.set(registro, campos[iCLAVES]);
        aFoto.set(registro, campos[iFOTO]);
    }

    private void elimina_de_arrays() {
        aNombre.remove(registro);
        aNota.remove(registro);
        aCuarto.remove(registro);
        aMueble.remove(registro);
        aCuerpo.remove(registro);
        aHueco.remove(registro);
        aFila_col.remove(registro);
        aClaves.remove(registro);
        aFoto.remove(registro);
    }

    private boolean lee_fichero_a_arrays() {
        limpia_arrays();
        FileReader fr;
        try { fr = new FileReader(fichero); }
        catch (IOException e) {
//			log = "Error al abrir fichero para leer";
            log = getString(R.string.msg_error_crear_freader);
            return false;
        }
        BufferedReader br = new BufferedReader(fr);
        String s;
        boolean fin = false;
        while ( !fin ) {
            try { s = br.readLine(); }
            catch (IOException e) {
//				log = "Error al leer fichero";
                log = getString(R.string.msg_error_leyendo);
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
//			log = "Error al cerrar fichero tras lectura";
            log = getString(R.string.msg_error_cerrando);
            return false;
        }
        return true;
    }

    private void limpia_arrays() {
        aNombre.clear();
        aNota.clear();
        aCuarto.clear();
        aMueble.clear();
        aCuerpo.clear();
        aHueco.clear();
        aFila_col.clear();
        aClaves.clear();
        aFoto.clear();
        cuartos.clear();
        muebles.clear();
        cuerpos.clear();
        huecos.clear();
        fila_cols.clear();
        fotos.clear();
        claves.clear();
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
        aFoto.add(aLinea[iFOTO]);
//		System.out.println(aClaves.toString());
    }

    private void rellena_arrays() {
        rellena_un_array(aCuarto, cuartos);
        rellena_un_array(aMueble, muebles);
        rellena_un_array(aCuerpo, cuerpos);
        rellena_un_array(aHueco, huecos);
        rellena_un_array(aFila_col, fila_cols);
        rellena_un_array(aFoto, fotos);
        rellena_claves();
    }

    private void rellena_un_array(List<String> origen, List<String> destino) {
        destino.add("");
        for (String valor: origen) {
            if (!destino.contains(valor)) destino.add(valor);
        }
        Collections.sort(destino, String.CASE_INSENSITIVE_ORDER);
        destino.add(getString(R.string.nuevo));
    }

    // para cuando se añade un valor nuevo a mano
    public void add_valor(Spinner desple, List<String> lista, String valor) {
        //lista.remove("NUEVO");
        lista.remove(getString(R.string.nuevo));
        lista.add(valor);
        Collections.sort(lista, String.CASE_INSENSITIVE_ORDER);
        //lista.add("NUEVO");
        lista.add(getString(R.string.nuevo));
        //desple.getAdapter().notifyDataSetChanged();
        desple.setAdapter(desple.getAdapter());
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
        claves.add(getString(R.string.nuevo));
    }

    public void anade_clave(String nueva) {
        if (claves.contains(nueva)) return;
        claves.remove(getString(R.string.nuevo));
        claves.add(nueva);
        Collections.sort(claves, String.CASE_INSENSITIVE_ORDER);
        claves.add(getString(R.string.nuevo));
    }

    public void borra_foto(String fichero) {
        File fich = new File(fichero);
        fich.deleteOnExit();
        fich.delete();
    }

    public String getCampo(int i) {
        return aLinea[i];
    }

}
