package com.antovar.inventario;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GestionClavesActivity extends Activity {
    protected BDatos bdatos;
    private List<Integer> posClavesMarcadas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gestion_claves);
        bdatos = (BDatos) getApplicationContext();
        // lee las claves actuales que le pasan
        Bundle extras = getIntent().getExtras();
        String actuales = extras.getString("clavesOri");
        array_con_posiciones_de_clavesOri(actuales);

        final ListView listClaves = (ListView) findViewById(R.id.listViewClaves);
        final ArrayAdapter clavesAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, bdatos.claves) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                //TextView text = (TextView) view.findViewById(android.R.id.text1);
                if (posClavesMarcadas.contains(position)) view.setBackgroundColor(0xFFA0A0A0);
                else view.setBackgroundColor(0xFFFFFFFF);
                return view;
            }
        };
        listClaves.setAdapter(clavesAdapter);

        listClaves.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // final String item = (String) parent.getItemAtPosition(position);
                if (posClavesMarcadas.contains(position)) {
                    view.setBackgroundColor(0xFFFFFFFF);
                    eliminaMarcada(position);
                } else {
                    view.setBackgroundColor(0xFFA0A0A0);
                    posClavesMarcadas.add(position);
                }
            }
        });

    }

    // para llevar el control de las seleccionadas
    private void array_con_posiciones_de_clavesOri(String sClaves) {
        //private List<String> lClaves = sClaves.split(",");
        if (sClaves.length() == 0) return;
        for (String valor: sClaves.split(bdatos.CS)) {
            int pos = bdatos.claves.indexOf(valor);
            posClavesMarcadas.add(pos);
        }
    }

    private void eliminaMarcada(int pos) {
        int posicion = 0;
        for (int valor: posClavesMarcadas) {
            if (valor == pos) {
                posClavesMarcadas.remove(posicion);
                return;
            }
            posicion++;
        }
    }


//		listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
//    		@Override
//			public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
//				final String item = (String) parent.getItemAtPosition(position);
//				view.animate().setDuration(2000).alpha(0)
//					.withEndAction(new Runnable() {
//						@Override
//						public void run() {
//							list.remove(item);
//							adapter.notifyDataSetChanged();
//							view.setAlpha(1);
//						}
//					});
//			}
//
//		});


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gestion_claves, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.ok) {
            Collections.sort(posClavesMarcadas);
            String clavesVuelta = "";
            for (int valor: posClavesMarcadas) {
                clavesVuelta += bdatos.claves.get(valor) + ",";
            }
            if (clavesVuelta.length() > 1) clavesVuelta = clavesVuelta.substring(0,clavesVuelta.length()-1);
            Intent intent = new Intent();
            intent.putExtra("claves", clavesVuelta);
            setResult(1, intent);
            finish();
            return true;
        }

        if (id == R.id.limpiar) {
            posClavesMarcadas.clear();

            return true;
        }

        if (id == R.id.cancel) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
