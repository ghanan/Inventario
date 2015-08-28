package com.antovar.inventario;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Toast;

public class MenuInicial extends Activity {

    private BDatos bdatos;
    private Context este;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuinicial);
        este = this;
        bdatos = (BDatos)getApplicationContext();
//        comprueba_almacenamiento();
        //if (bdatos.nombredb.equals("")
        //setTitle(bdatos.nombredb.equals("") ? bdatos.nombredb : R.string.title_no_abierto);
        setTitle(bdatos.nombredb.equals("") ? getString(R.string.title_no_abierto) : bdatos.nombredb);
        //if (!bdatos.disponible) Toast.makeText(this, bdatos.log, Toast.LENGTH_LONG).show();
    }

//    private void comprueba_almacenamiento() {
//        if (!bdatos.disponible) {
//            Toast.makeText(this, bdatos.log, Toast.LENGTH_LONG).show();
//        }
//    }

    public void onButtonClick(View v) {
        if (v.getId() == R.id.botonSalir) {
            finish();
            return;
        }
        if (!bdatos.disponible) {
            Toast.makeText(this, bdatos.log, Toast.LENGTH_LONG).show();
            return;
        }
        if (bdatos.nombredb.equals("")) {
            Toast.makeText(this, getString(R.string.msg_no_abierto), Toast.LENGTH_LONG).show();
            return;
        }
        Intent pantalla_alta = new Intent(this, AltaActivity.class);
        if (v.getId() == R.id.botonAlta) {
            pantalla_alta.putExtra("alta", true);
        } else {
            pantalla_alta.putExtra("alta", false);
        }
        startActivity(pantalla_alta);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BDatos.INTENT_INVENTARIOS && resultCode == BDatos.CAMBIO_INVENTARIO) {
            bdatos.abrir_bd(data.getExtras().getString("seleccionado"));
            if (bdatos.disponible) setTitle(bdatos.nombredb);
            //String nuevasClaves = data.getExtras().getString("claves");
        } else if (requestCode == BDatos.INTENT_INVENTARIOS && resultCode == BDatos.BORRAR_INVENTARIO) {
            bdatos.borrar_db(data.getExtras().getString("seleccionado"));
            if (bdatos.disponible) setTitle(getString(R.string.msg_no_abierto));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inicial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.abrir) {
            Intent listaInventarios = new Intent(this, ListaRegistrosActivity.class);
            listaInventarios.putExtra("lista", BDatos.LISTA_INVENTARIOS);
            listaInventarios.putExtra("accion", BDatos.CAMBIO_INVENTARIO);
            startActivityForResult(listaInventarios, BDatos.INTENT_INVENTARIOS);
        } else if (id == R.id.nuevo) {
            final EditText entrada = new EditText(this);
            entrada.setText("");
            new AlertDialog.Builder(this)
                .setTitle(getString(R.string.nombre_nuevo))
                .setView(entrada)
                    .setPositiveButton(getString(R.string.boton_aceptar), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String nombre = (entrada.getText().toString() + "\n.").split("\n")[0].trim();
                            if (!nombre.equals("")) {
//                                boolean existe = false;
//                                for (String n : bdatos.inventarios)
//                                    if (n.toLowerCase().equals(nombre.toLowerCase())) {
//                                        Toast.makeText(este, R.string.msg_nombre_repe, Toast.LENGTH_LONG).show();
//                                        existe = true;
//                                        break;
//                                    }
//                                if (!existe) {
                                bdatos.crea_db(nombre, este);
                                if (bdatos.disponible) setTitle(bdatos.nombredb);
//                                }
                            }
                        }
                    })
                    .setNegativeButton(getString(R.string.boton_cancelar), null)
                .show();
        }
        else if (id == R.id.borrar) {
            Intent listaInventarios = new Intent(this, ListaRegistrosActivity.class);
            listaInventarios.putExtra("lista", BDatos.LISTA_INVENTARIOS);
            listaInventarios.putExtra("accion", BDatos.BORRAR_INVENTARIO);
            startActivityForResult(listaInventarios, BDatos.INTENT_INVENTARIOS);
        }
//        if (id == R.id.acerca) {
        return true;
//        return super.onOptionsItemSelected(item);
    }


}
