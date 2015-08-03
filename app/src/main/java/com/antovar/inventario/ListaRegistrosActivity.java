package com.antovar.inventario;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;


public class ListaRegistrosActivity extends Activity {
    protected BDatos bdatos;
    private List<Integer> posLista = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_registros);
        //final Context contexto = this;
        bdatos = (BDatos) getApplicationContext();
        Bundle extras = getIntent().getExtras();
        String lista = extras.getString("lista");
        array_con_lista(lista);

    }

    private void array_con_lista(String lista) {
        System.out.println(lista);
        String lis = lista.replace("[","").replace("]","").replace(" ","");
        System.out.println(lis);
        System.out.println(lis.split(",")[0]);
        System.out.println(lis.split(",")[1]);
        //posLista.add((int)lis.split(",")[0]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_registros, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}