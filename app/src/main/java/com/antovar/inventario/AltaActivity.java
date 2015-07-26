package com.antovar.inventario;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.Toast;
//import android.support.v7.app.AppCompatActivity;

import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.widget.*;

public class AltaActivity extends Activity implements OnItemSelectedListener {

	final static int EDICION_CLAVES = 1;
    boolean alta;
    protected BDatos bdatos;
    EditText nombre;
    EditText nota;
    TextView claves;
	String cuarto = "";
	String mueble = "";
	String cuerpo = "";
	String hueco = "";
	String fila_col = "";
    public Spinner desple_cuarto;
    public Spinner desple_mueble;
    public Spinner desple_cuerpo;
    public Spinner desple_hueco;
    public Spinner desple_fila_col;
    private List<Integer> posRegistrosSelec = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta);
        Bundle extras = getIntent().getExtras();
        alta = extras.getBoolean("alta");
        bdatos = (BDatos)getApplicationContext();
        //bdatos = (BDatos)getApplication();
        this.nombre = (EditText) findViewById(R.id.nombre);
        this.nota = (EditText) findViewById(R.id.nota);
        //this.claves = (EditText) findViewById(R.id.claves);
		this.claves = (TextView) findViewById(R.id.claves);
        desple_cuarto = (Spinner) findViewById(R.id.desplegable_cuarto);
        desple_cuarto.setOnItemSelectedListener(this);
        //final String[] cuartos =
		//	new String[]{"Salon", "Cocina", "Cuarto_pequeño", "Pasillo", "Baño_pequeño", "Baño_grande", "Cuarto_grande"};
		ArrayAdapter < String > adapta_cuarto =
			new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bdatos.cuartos);
        adapta_cuarto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        desple_cuarto.setAdapter(adapta_cuarto);

        desple_mueble = (Spinner) findViewById(R.id.desplegable_mueble);
        desple_mueble.setOnItemSelectedListener(this);
//        final String[] muebles =
//			new String[]{"Frente_sofa", "TV", "Tras_Camilla"};
        ArrayAdapter<String> adapta_mueble =
			new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bdatos.muebles);
        adapta_mueble.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        desple_mueble.setAdapter(adapta_mueble);

        desple_cuerpo = (Spinner) findViewById(R.id.desplegable_cuerpo);
        desple_cuerpo.setOnItemSelectedListener(this);
//        final String[] cuerpos =
//			new String[]{"", "Cuerpo-a1", "Cuerpo-a2", "Cuerpo-a3", "Cuerpo-b1", "Cuerpo-b2"};
        ArrayAdapter<String> adapta_cuerpo =
			new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bdatos.cuerpos);
        adapta_cuerpo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        desple_cuerpo.setAdapter(adapta_cuerpo);

        desple_hueco = (Spinner) findViewById(R.id.desplegable_hueco);
        desple_hueco.setOnItemSelectedListener(this);
//        final String[] huecos =
//			new String[]{"", "cajón", "estante", "puerta-f1c1", "puerta-f1c2", "puerta-f2c1"};
        ArrayAdapter<String> adapta_hueco =
			new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bdatos.huecos);
        adapta_hueco.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        desple_hueco.setAdapter(adapta_hueco);

        desple_fila_col = (Spinner) findViewById(R.id.desplegable_fila_col);
        desple_fila_col.setOnItemSelectedListener(this);
//        final String[] fila_cols =
//			new String[]{"", "f1c1", "f2c1", "f3c1", "f1c2", "f2c2"};
        ArrayAdapter<String> adapta_fila_col =
			new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bdatos.fila_cols);
        adapta_fila_col.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        desple_fila_col.setAdapter(adapta_fila_col);
	}

	@Override
	public void onItemSelected (AdapterView<?> parent, View view,int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        if (parent.getId() == R.id.desplegable_cuarto) {
            if (item.equals("NUEVO")) nuevo_sitio("cuarto");
            else cuarto = item;
        } else if (parent.getId() == R.id.desplegable_mueble) {
            if (item.equals("NUEVO")) nuevo_sitio("mueble");
            else mueble = item;
        } else if (parent.getId() == R.id.desplegable_cuerpo) {
            if (item.equals("NUEVO")) nuevo_sitio("cuerpo");
            else mueble = item;
        } else if (parent.getId() == R.id.desplegable_hueco) {
            if (item.equals("NUEVO")) nuevo_sitio("hueco");
            else hueco = item;
        } else {
            if (item.equals("NUEVO")) nuevo_sitio("fila_col");
            else fila_col = item;
        }
    //    Toast.makeText(parent.getContext(), "cuarto: " + cuarto, Toast.LENGTH_LONG).show();
    //    System.out.println("onItem: "+cuarto+" "+mueble);
	}

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

/*
    public void onButtonClick(View v) {
        */
/*System.out.println("clickado");*//*

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
*/

    public void onClavesClick(View v) {
        //pasar claves actuales y recibir las deseadas
        Intent intentClaves = new Intent(AltaActivity.this, GestionClavesActivity.class);
		//intenClaves.putExtra(Intent.EXTRA_TEXT, bdatos.getCampo(bdatos.iFILA_COL));
//        intentClaves.putExtra("clavesOri", bdatos.getCampo(bdatos.iCLAVES));
        intentClaves.putExtra("clavesOri", claves.getText());
		startActivityForResult(intentClaves, EDICION_CLAVES);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EDICION_CLAVES && resultCode == 1) {
            String nuevasClaves = data.getExtras().getString("claves");
            claves.setText(nuevasClaves);
        }
    }

    private void grabar() {
        if (this.nombre.getText().toString().equals("")) {
            Toast.makeText(this, "Falta nombre", Toast.LENGTH_LONG).show();
            return;
        }
		String lclaves = claves.getText().toString();
//		if (lclaves.equals("")) lclaves = ".";
        if (bdatos.alta(nombre.getText() + ";" + nota.getText() + ";" + cuarto + ";"
			+ mueble + ";" + cuerpo + ";" + hueco + ";" + fila_col + ";" + lclaves + ";.")) {
        	nombre.setText("");
			nota.setText("");
			nombre.requestFocus();
		} else Toast.makeText(this, bdatos.log, Toast.LENGTH_LONG).show();
//        try {
//            fw = new FileWriter(fichero, true);
//        } catch (IOException e) {
//            Toast.makeText(this, R.string.msg_error_crear_fwriter, Toast.LENGTH_LONG).show();
//            return;
//        }
//        try {
//            Toast.makeText(this, "escribiendo", Toast.LENGTH_LONG).show();
//            fw.append(this.nombre.getText().toString());
//        } catch (IOException e) {
//            Toast.makeText(this, R.string.msg_error_escribiendo, Toast.LENGTH_LONG).show();
//            return;
//        }
    }

    private void nuevo_sitio(final String sitio) {
        final EditText entrada = new EditText(this);
        entrada.setText("");
        new AlertDialog.Builder(this)
            .setTitle("Nuevo sitio")
            .setMessage("introduce nuevo "+sitio)
            .setView(entrada)
            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    if (sitio.equals("cuarto")) {
                        cuarto = entrada.getText().toString();
                        bdatos.add_valor(desple_cuarto, bdatos.cuartos, cuarto);
                    } else if (sitio.equals("mueble")) {
                        mueble = entrada.getText().toString();
                        bdatos.add_valor(desple_mueble, bdatos.muebles, mueble);
                    } else if (sitio.equals("cuerpo")) {
                        cuerpo = entrada.getText().toString();
                        bdatos.add_valor(desple_cuerpo, bdatos.cuerpos, cuerpo);
                    } else if (sitio.equals("hueco")) {
                        hueco = entrada.getText().toString();
                        bdatos.add_valor(desple_hueco, bdatos.huecos, hueco);
                    } else {
                        fila_col = entrada.getText().toString();
                        bdatos.add_valor(desple_fila_col, bdatos.fila_cols, fila_col);
                    }
                }})
            .setNegativeButton("Cancelar", null)
            .show();
        //return sitio;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (alta) getMenuInflater().inflate(R.menu.menu_alta, menu);
        else getMenuInflater().inflate(R.menu.menu_buscar, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.grabar) this.grabar();
        else if (id == R.id.limpiar) {
            this.nombre.setText("");
            this.nota.setText("");
            this.claves.setText("");
        } else if (id == R.id.volver) finish();
        else if (id == R.id.buscar) this.buscar();
        else {
            //foto
        }
        return true;
    }

    private void buscar() {
        posRegistrosSelec.clear();
        if (!this.claves.getText().toString().equals("")) selecciona_por_claves();
        if (posRegistrosSelec.size() == 0) mensaje_no_hay();
    }

    private void selecciona_por_claves() {
        String sClaves;
        String[] claves;
        boolean todas;
        for (int i=0; i<bdatos.aClaves.size(); i++) {
            sClaves = bdatos.aClaves.get(i);
            if (sClaves.equals("")) continue;
            claves = sClaves.split(bdatos.CS);
            Arrays.sort(claves);
            todas = true;
            for (String cla : this.claves.getText().toString().split(bdatos.CS)) {
                if (Arrays.binarySearch(claves, cla) < 0) {
                    todas = false;
                    break;
                }
            }
            if (todas) posRegistrosSelec.add(i);
            if (todas) System.out.println(i + " " + sClaves);
        }
    }

    private void mensaje_no_hay() {

    }
}
