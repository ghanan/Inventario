package com.antovar.inventario;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteTableLockedException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class ListaRegistrosActivity extends Activity {
    protected BDatos bdatos;
    private List<String> registros = new ArrayList<>();
    ListView listRegistros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_registros);
        //final Context contexto = this;
        bdatos = (BDatos) getApplicationContext();
        //Bundle extras = getIntent().getExtras();
        //String lista = extras.getString("lista");
        rellena_registros();
        listRegistros = (ListView) findViewById(R.id.listViewRegistros);
        ArrayAdapter<String> registrosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, registros);
        listRegistros.setAdapter(registrosAdapter);
        //protected void onListItemClick(ListView l, View v, int position, long id)

        listRegistros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                bdatos.registro = bdatos.posRegistrosSelec.get(position);
                Intent intent = new Intent();
                setResult(1, intent);
                finish();
            }
        });
    }

    private void rellena_registros() {
        for (int indice: bdatos.posRegistrosSelec) {
            registros.add(bdatos.aNombre.get(indice));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_registros, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.volver) {
            finish();
        }
        return true;
//        return super.onOptionsItemSelected(item);
    }
}
