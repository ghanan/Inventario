package com.antovar.inventario;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.view.MenuItem;

import java.util.ArrayList;
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
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, bdatos.claves);
        listClaves.setAdapter(adapter);

        listClaves.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (posClavesMarcadas.contains(position)) {
                    view.setBackgroundColor(0xFFFFFFFF);
                    eliminaMarcada(position);
                } else {
                    view.setBackgroundColor(0xFFA0A0A0);
                    posClavesMarcadas.add(position);
                }
            }
        });

        marcaLasPasadas();

    }


    // para llevar el control de las seleccionadas
    private void array_con_posiciones_de_clavesOri(String sClaves) {
        //private List<String> lClaves = sClaves.split(",");
        for (String valor: sClaves.split(bdatos.CS)) {
            int pos = bdatos.aClaves.indexOf(valor);
            System.out.println(pos);
            posClavesMarcadas.add(pos);
        }
    }

    private void marcaLasPasadas() {
        for (Integer valor: posClavesMarcadas) {

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
