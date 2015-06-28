package com.antovar.inventario;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.view.MenuItem;


public class GestionClavesActivity extends Activity {
    protected BDatos bdatos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gestion_claves);
		
		final ListView listview = (ListView) findViewById(R.id.listViewClaves);
		final ArrayAdapter adapter = new ArrayAdapter(this,
			android.R.layout.simple_list_item_1, bdatos.claves);
		listview.setAdapter(adapter);
		
//		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//				@Override
//				public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
//					final String item = (String) parent.getItemAtPosition(position);
//					view.animate().setDuration(2000).alpha(0)
//						.withEndAction(new Runnable() {
//							@Override
//							public void run() {
//								list.remove(item);
//								adapter.notifyDataSetChanged();
//								view.setAlpha(1);
//							}
//						});
//				}
//
//		});
    }

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
