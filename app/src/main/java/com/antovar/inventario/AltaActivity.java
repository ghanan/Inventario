package com.antovar.inventario;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.widget.*;

public class AltaActivity extends Activity implements OnItemSelectedListener {

	final static int EDICION_CLAVES = 1;
	final static int SELEC_REGISTRO = 2;
	final static int HACER_FOTO = 3;
    private Menu elMenu;
    boolean alta;
    final static boolean NUEVO = true;
    final static boolean MODIFICAR = false;
    final static boolean BORRAR = true;
    private ArrayAdapter adapta_cuarto;
    private ArrayAdapter adapta_mueble;
    private ArrayAdapter adapta_cuerpo;
    private ArrayAdapter adapta_hueco;
    private ArrayAdapter adapta_fila_col;
    protected BDatos bdatos;
    private EditText nombre;
    private EditText nota;
    private TextView claves;
    private String cuarto = "";
    private String mueble = "";
    private String cuerpo = "";
    private String hueco = "";
    private String fila_col = "";
    private TextView foto;
    private ImageView view_foto;
    private Uri foto_uri;
    private String foto_fichero = "";
    public Spinner desple_cuarto;
    public Spinner desple_mueble;
    public Spinner desple_cuerpo;
    public Spinner desple_hueco;
    public Spinner desple_fila_col;

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
		this.claves = (TextView) findViewById(R.id.claves);
        this.foto = (TextView) findViewById(R.id.foto);
        this.view_foto = (ImageView) findViewById(R.id.view_foto);
        desple_cuarto = (Spinner) findViewById(R.id.desplegable_cuarto);
        desple_cuarto.setOnItemSelectedListener(this);
		//ArrayAdapter < String > adapta_cuarto =
//		adapta_cuarto = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bdatos.cuartos);
		adapta_cuarto = new ArrayAdapter<String>(this, R.layout.spinner_item, bdatos.cuartos);
//        adapta_cuarto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapta_cuarto.setDropDownViewResource(R.layout.desplegable_item);
        desple_cuarto.setAdapter(adapta_cuarto);

        desple_mueble = (Spinner) findViewById(R.id.desplegable_mueble);
        desple_mueble.setOnItemSelectedListener(this);
        adapta_mueble = new ArrayAdapter<String>(this, R.layout.spinner_item, bdatos.muebles);
        adapta_mueble.setDropDownViewResource(R.layout.desplegable_item);
        desple_mueble.setAdapter(adapta_mueble);

        desple_cuerpo = (Spinner) findViewById(R.id.desplegable_cuerpo);
        desple_cuerpo.setOnItemSelectedListener(this);
        adapta_cuerpo = new ArrayAdapter<String>(this, R.layout.spinner_item, bdatos.cuerpos);
        adapta_cuerpo.setDropDownViewResource(R.layout.desplegable_item);
        desple_cuerpo.setAdapter(adapta_cuerpo);

        desple_hueco = (Spinner) findViewById(R.id.desplegable_hueco);
        desple_hueco.setOnItemSelectedListener(this);
        adapta_hueco = new ArrayAdapter<String>(this, R.layout.spinner_item, bdatos.huecos);
        adapta_hueco.setDropDownViewResource(R.layout.desplegable_item);
        desple_hueco.setAdapter(adapta_hueco);

        desple_fila_col = (Spinner) findViewById(R.id.desplegable_fila_col);
        desple_fila_col.setOnItemSelectedListener(this);
        adapta_fila_col = new ArrayAdapter<String>(this, R.layout.spinner_item, bdatos.fila_cols);
        adapta_fila_col.setDropDownViewResource(R.layout.desplegable_item);
        desple_fila_col.setAdapter(adapta_fila_col);
	}

    @Override
	public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        if (parent.getId() == R.id.desplegable_cuarto) {
//            if (item.equals("NUEVO")) nuevo_sitio("cuarto");
            if (item.equals(getString(R.string.nuevo))) nuevo_sitio("cuarto");
            else cuarto = item;
        } else if (parent.getId() == R.id.desplegable_mueble) {
            if (item.equals(getString(R.string.nuevo))) nuevo_sitio("mueble");
            else mueble = item;
        } else if (parent.getId() == R.id.desplegable_cuerpo) {
            if (item.equals(getString(R.string.nuevo))) nuevo_sitio("cuerpo");
            else mueble = item;
        } else if (parent.getId() == R.id.desplegable_hueco) {
            if (item.equals(getString(R.string.nuevo))) nuevo_sitio("hueco");
            else hueco = item;
        } else {
            if (item.equals(getString(R.string.nuevo))) nuevo_sitio("fila_col");
            else fila_col = item;
        }
	}

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

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
        } else if (requestCode == SELEC_REGISTRO && resultCode == 1) {
            muestra_selecionado();
        } else if (requestCode == HACER_FOTO && resultCode == 1) {
            if (!foto_fichero.equals("")) bdatos.borra_foto(foto_fichero);
            foto_fichero = data.getExtras().getString("foto");
            //foto_fichero = "/storage/sdcard/InventarioCasa/151515151515.jpg";
            view_foto.setImageURI(Uri.parse("file://" + foto_fichero));
            foto_uri = Uri.parse("file://" + foto_fichero);
            view_foto.setImageURI(foto_uri);
            foto.setText(foto_uri.getLastPathSegment());
        }
    }

    private void rellena_pantalla() {
        limpia_pantalla(!BORRAR);
        nombre.setText(bdatos.aNombre.get(bdatos.registro));
        nota.setText(bdatos.aNota.get(bdatos.registro));
        claves.setText(bdatos.aClaves.get(bdatos.registro));
        desple_cuarto.setSelection(adapta_cuarto.getPosition(bdatos.aCuarto.get(bdatos.registro)));
        desple_mueble.setSelection(adapta_mueble.getPosition(bdatos.aMueble.get(bdatos.registro)));
        desple_cuerpo.setSelection(adapta_cuerpo.getPosition(bdatos.aCuerpo.get(bdatos.registro)));
        desple_hueco.setSelection(adapta_hueco.getPosition(bdatos.aHueco.get(bdatos.registro)));
        desple_fila_col.setSelection(adapta_fila_col.getPosition(bdatos.aFila_col.get(bdatos.registro)));
        if (!bdatos.aFoto.get(bdatos.registro).equals(".")) {
            foto.setText(bdatos.aFoto.get(bdatos.registro));
            System.out.println("file:/" + bdatos.dir + "/" + bdatos.aFoto.get(bdatos.registro));
            view_foto.setImageURI(Uri.parse("file://" + bdatos.dir + "/" + bdatos.aFoto.get(bdatos.registro)));
        }
        nombre.requestFocus();
    }

    private void limpia_pantalla(boolean borrar) {
        nombre.setText("");
        nota.setText("");
        claves.setText("");
        desple_cuarto.setSelection(0);
        desple_mueble.setSelection(0);
        desple_cuerpo.setSelection(0);
        desple_hueco.setSelection(0);
        desple_fila_col.setSelection(0);
        view_foto.setImageResource(0);
        foto.setText(".");
        if (borrar) {
            if (!foto_fichero.equals("")) {
                bdatos.borra_foto(foto_fichero);
            }
        }
        foto_fichero = "";
        nombre.requestFocus();
    }

    private void grabar(boolean nuevo) {
        if (this.nombre.getText().toString().equals("")) {
//            Toast.makeText(this, "Falta nombre", Toast.LENGTH_LONG).show();
            Toast.makeText(this, getString(R.string.msg_nombreVacio), Toast.LENGTH_LONG).show();
            return;
        }
//		String lclaves = claves.getText().toString();
        String linea = nombre.getText() + bdatos.FS + nota.getText() + bdatos.FS
                + cuarto + bdatos.FS + mueble + bdatos.FS + cuerpo + bdatos.FS + hueco + ";"
                + fila_col + bdatos.FS + claves.getText().toString() + bdatos.FS + foto.getText();
//        if (bdatos.alta(nombre.getText() + ";" + nota.getText() + ";" + cuarto + ";"
//			+ mueble + ";" + cuerpo + ";" + hueco + ";" + fila_col + ";" + lclaves + ";.")) {
        if (nuevo) {
            if (bdatos.alta(linea)) {
                limpia_pantalla(!BORRAR);
                return;
            }
        } else if (bdatos.modificar(linea, foto.getText().toString(), MODIFICAR)) {
            //limpia_pantalla();
            return;
        }
        Toast.makeText(this, bdatos.log, Toast.LENGTH_LONG).show();
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
            .setTitle(getString(R.string.intro_nuevo))
            //.setMessage("introduce nuevo "+sitio)
                .setView(entrada)
            .setPositiveButton(getString(R.string.boton_aceptar), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    if (sitio.equals("cuarto")) {
                        cuarto = entrada.getText().toString();
                        bdatos.add_valor(desple_cuarto, bdatos.cuartos, cuarto);
                        desple_cuarto.setSelection(adapta_cuarto.getPosition(cuarto));
                        //System.out.println(bdatos.cuartos.indexOf(cuarto));
                        //System.out.println(adapta_cuarto.getPosition(cuarto));
                        //desple_cuarto.setTop(bdatos.cuartos.indexOf(cuarto));
                        //desple_cuarto.setSelection(bdatos.cuartos.indexOf(cuarto));
                    } else if (sitio.equals("mueble")) {
                        mueble = entrada.getText().toString();
                        bdatos.add_valor(desple_mueble, bdatos.muebles, mueble);
                        desple_mueble.setSelection(adapta_mueble.getPosition(mueble));
                    } else if (sitio.equals("cuerpo")) {
                        cuerpo = entrada.getText().toString();
                        bdatos.add_valor(desple_cuerpo, bdatos.cuerpos, cuerpo);
                        desple_cuerpo.setSelection(adapta_cuerpo.getPosition(cuerpo));
                    } else if (sitio.equals("hueco")) {
                        hueco = entrada.getText().toString();
                        bdatos.add_valor(desple_hueco, bdatos.huecos, hueco);
                        desple_hueco.setSelection(adapta_hueco.getPosition(hueco));
                    } else {
                        fila_col = entrada.getText().toString();
                        bdatos.add_valor(desple_fila_col, bdatos.fila_cols, fila_col);
                        desple_fila_col.setSelection(adapta_fila_col.getPosition(fila_col));
                    }
                }})
            .setNegativeButton(getString(R.string.boton_cancelar), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    if (sitio.equals("cuarto")) desple_cuarto.setSelection(0);
                    else if (sitio.equals("mueble")) desple_mueble.setSelection(0);
                    else if (sitio.equals("cuerpo")) desple_cuerpo.setSelection(0);
                    else if (sitio.equals("hueco")) desple_hueco.setSelection(0);
                    else desple_fila_col.setSelection(0);
                }
            })
            .show();
        //return sitio;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (alta) getMenuInflater().inflate(R.menu.menu_alta, menu);
        else getMenuInflater().inflate(R.menu.menu_buscar, menu);
        elMenu = menu;
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.grabar || id == R.id.anadir) this.grabar(NUEVO);
        else if (id == R.id.regrabar) { grabar(MODIFICAR); finish(); }
        else if (id == R.id.buscar) this.buscar();
        else if (id == R.id.borrar) borrar();
        else if (id == R.id.limpiar) limpia_pantalla(BORRAR);
        else if (id == R.id.foto) foto();
        else if (id == R.id.sinfoto) quita_foto();
        else {
            if (!foto_fichero.equals("")) bdatos.borra_foto(foto_fichero);
            finish();
        }
        return true;
    }

    private void foto() {
        Intent intentFoto = new Intent(AltaActivity.this, FotoActivity.class);
        startActivityForResult(intentFoto, HACER_FOTO);
    }

    private void quita_foto() {
        if (foto.getText().equals(".")) return;
        if (foto_fichero.equals("")) { // está mostrando foto original
            foto.setText(".");
            view_foto.setImageResource(0);
        } else { // mostrando foto recién hecha
            bdatos.borra_foto(foto_fichero);
            if (bdatos.aFoto.get(bdatos.registro).equals(".")) { // sin foto originalmente
                foto.setText(".");
                view_foto.setImageResource(0);
            } else { // había foto original
                foto.setText(bdatos.aFoto.get(bdatos.registro));
                view_foto.setImageURI(Uri.parse("file:// " + bdatos.dir + "/" + bdatos.aFoto.get(bdatos.registro)));
            }
        }
    }

    private void buscar() {
        bdatos.posRegistrosSelec.clear();
        if (!this.claves.getText().toString().equals("")) selecciona_por_claves();
        if (!nombre.getText().toString().equals("")) seleccionar(bdatos.aNombre, nombre.getText().toString().toLowerCase());
        if (!nota.getText().toString().equals("")) seleccionar(bdatos.aNota, nota.getText().toString().toLowerCase());
        if (!cuarto.equals("")) seleccionar(bdatos.aCuarto, cuarto);
        if (!mueble.equals("")) seleccionar(bdatos.aMueble, mueble);
        if (!hueco.equals("")) seleccionar(bdatos.aHueco, hueco);
        if (!fila_col.equals("")) seleccionar(bdatos.aFila_col, fila_col);
        if (bdatos.posRegistrosSelec.size() == 0) mensaje_no_hay();
        else if (bdatos.posRegistrosSelec.size() == 1) {
            bdatos.registro = bdatos.posRegistrosSelec.get(0);
            muestra_selecionado();
        } else {
            Intent intentRegistros = new Intent(AltaActivity.this, ListaRegistrosActivity.class);
            startActivityForResult(intentRegistros, SELEC_REGISTRO);
        }
    }

    private void seleccionar(ArrayList campo, String cadena) {
        if (bdatos.posRegistrosSelec.size() == 0) {
            for (int i = 0; i < campo.size(); i++) {
                if (campo.get(i).toString().toLowerCase().contains(cadena))
                    bdatos.posRegistrosSelec.add(i);
            }
        } else {
            int i = bdatos.posRegistrosSelec.size() - 1;
            while (i >= 0) {
                if (!campo.get(bdatos.posRegistrosSelec.get(i)).toString().toLowerCase().contains(cadena))
                    bdatos.posRegistrosSelec.remove(i);
                i--;
            }
        }
    }

    private void muestra_selecionado() {
        elMenu.clear();
        getMenuInflater().inflate(R.menu.menu_modificar, elMenu);
        rellena_pantalla();
    }

    private void borrar() {
        //if (!foto_fichero.equals("")) bdatos.borra_foto(foto_fichero);
        if (bdatos.modificar("", "", BORRAR)) limpia_pantalla(BORRAR);
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
            for (String cla: this.claves.getText().toString().split(bdatos.CS)) {
                if (Arrays.binarySearch(claves, cla) < 0) {
                    todas = false;
                    break;
                }
            }
            if (todas) bdatos.posRegistrosSelec.add(i);
        }
    }

    private void mensaje_no_hay() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.msg_no_registros))
                .setPositiveButton(R.string.boton_aceptar, null)
                .show();
    }
}
