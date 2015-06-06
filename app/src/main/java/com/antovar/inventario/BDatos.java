package com.antovar.inventario;


import android.app.Activity;
import android.view.View;
import android.widget.Toast;

public class BDatos {
    private String fichero;
    private boolean disponible;
    private Activity vista;

    public BDatos(Activity llamante, String nombre) {
        vista = llamante;
        if (existe(nombre)) {
            fichero = nombre;
        } else {
            aviso_db(nombre);
        }
    }

    private boolean existe(String nombre) {
        return false;
    }

    private void aviso_db(String nombre) {
        System.out.println("No existe el fichero " + nombre);
        Toast.makeText(vista, R.string.msg_no_existe_fichero, Toast.LENGTH_LONG).show();
    }

    public boolean getDisponible() {
        return disponible;
    }

}
