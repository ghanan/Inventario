package com.antovar.inventario;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class FotoActivity extends Activity {
    static int TOMA_FOTO = 1;
    protected BDatos bdatos;
    private ImageView cuadro;
    private Bitmap bitMap;
    private String nombre_foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto);
        cuadro = (ImageView) findViewById(R.id.fotoView);
        toma_foto();
    }

    private void toma_foto() {
        nombre_foto = bdatos.dir + nombreFoto();
        File la_foto = new File(nombre_foto);
        try {
            la_foto.createNewFile();
        } catch (IOException ex) {
            Toast.makeText(this, "Error al crear fichero foto: " + ex, Toast.LENGTH_LONG).show();
        }
        Uri uri_foto = Uri.fromFile(la_foto);
//        Intent camara_Intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        Intent camara_Intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camara_Intent.putExtra(MediaStore.EXTRA_OUTPUT, uri_foto);
        startActivityForResult(camara_Intent, TOMA_FOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == TOMA_FOTO && resultCode== RESULT_OK && intent != null){
            Bundle extras = intent.getExtras();
            bitMap = (Bitmap) extras.get("data");
            cuadro.setImageBitmap(bitMap);
        }
    }

    private String nombreFoto() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yymmddhhmmss");
        return dateFormat.format(new Date()) + ".jpg";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_foto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.cancelar) {
            finish();
        }

//        return super.onOptionsItemSelected(item);
        return true;
    }
}
