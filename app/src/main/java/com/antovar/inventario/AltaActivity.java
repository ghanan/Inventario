package com.antovar.inventario;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Environment;


public class AltaActivity extends Activity implements OnItemSelectedListener {

    EditText nombre;
    EditText nota;
    EditText claves;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta);

        this.nombre = (EditText) findViewById(R.id.nombre);
        this.nota = (EditText) findViewById(R.id.nota);
        this.claves = (EditText) findViewById(R.id.claves);
        Spinner desple_cuarto = (Spinner) findViewById(R.id.desplegable_cuarto);
        desple_cuarto.setOnItemSelectedListener(this);
        final String[] cuartos =
			new String[]{"Salon", "Cocina", "Cuarto_pequeño", "Pasillo", "Baño_pequeño", "Baño_grande", "Cuarto_grande"};
		ArrayAdapter < String > adapta_cuarto =
			new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cuartos);
        adapta_cuarto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        desple_cuarto.setAdapter(adapta_cuarto);

        Spinner desple_mueble = (Spinner) findViewById(R.id.desplegable_mueble);
        desple_mueble.setOnItemSelectedListener(this);
        final String[] muebles =
			new String[]{"Frente_sofa", "TV", "Tras_Camilla"};
        ArrayAdapter<String> adapta_mueble =
			new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, muebles);
        adapta_mueble.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        desple_mueble.setAdapter(adapta_mueble);

        Spinner desple_cuerpo = (Spinner) findViewById(R.id.desplegable_cuerpo);
        desple_cuerpo.setOnItemSelectedListener(this);
        final String[] cuerpos =
			new String[]{"", "Cuerpo-a1", "Cuerpo-a2", "Cuerpo-a3", "Cuerpo-b1", "Cuerpo-b2"};
        ArrayAdapter<String> adapta_cuerpo =
			new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cuerpos);
        adapta_cuerpo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        desple_cuerpo.setAdapter(adapta_cuerpo);

        Spinner desple_hueco = (Spinner) findViewById(R.id.desplegable_hueco);
        desple_hueco.setOnItemSelectedListener(this);
        final String[] huecos =
			new String[]{"", "cajón", "estante", "puerta-f1c1", "puerta-f1c2", "puerta-f2c1"};
        ArrayAdapter<String> adapta_hueco =
			new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, huecos);
        adapta_hueco.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        desple_hueco.setAdapter(adapta_hueco);

        Spinner desple_fila_col = (Spinner) findViewById(R.id.desplegable_fila_col);
        desple_fila_col.setOnItemSelectedListener(this);
        final String[] fila_cols =
			new String[]{"", "f1c1", "f2c1", "f3c1", "f1c2", "f2c2"};
        ArrayAdapter<String> adapta_fila_col =
			new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fila_cols);
        adapta_fila_col.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        desple_fila_col.setAdapter(adapta_fila_col);

	}

	@Override
	public void onItemSelected (AdapterView<?> parent, View view,int position, long id){
		String item = parent.getItemAtPosition(position).toString();
//      if (parent.getId() == R.id.desplegable_cuarto) {}
//      Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

	}

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    public void onButtonClick(View v) {
        /*System.out.println("clickado");*/
        if (v.getId() == R.id.botonGrabar) {
            this.grabar();
        } else if (v.getId() == R.id.botonCancelar) {
            System.out.println("cancelar");
            this.nombre.setText("");
            this.nota.setText("");
            this.claves.setText("");
//              Intent pantalla = new Intent(this, AltaActivity.class);
//              startActivity(pantalla);
        } else {
            // foto
        }
    }

    private void grabar() {
        if (this.nombre.getText().toString().equals("")) {
            Toast.makeText(this, R.string.msg_nombreVacio, Toast.LENGTH_LONG).show();
            return;
        }
//              if (!isExternalStorageWritable()) {
//              Toast.makeText(this, R.string.msg_sin_almacenamiento, Toast.LENGTH_LONG).show();
//                      return;
//              }
//              if (this.claves.getText().toString().equals("")) return;

    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

}
