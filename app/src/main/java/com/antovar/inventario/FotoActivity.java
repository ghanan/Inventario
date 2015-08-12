package com.antovar.inventario;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


public class FotoActivity extends Activity {
    static int TOMA_FOTO = 1;
    private ImageView cuadro;
    Bitmap bitMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto);
        cuadro = (ImageView) findViewById(R.id.fotoView);
        toma_foto();
    }

    private void toma_foto() {
        Intent camaraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camaraIntent, TOMA_FOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == TOMA_FOTO && resultCode== RESULT_OK && intent != null){
            Bundle extras = intent.getExtras();
            bitMap = (Bitmap) extras.get("data");
            cuadro.setImageBitmap(bitMap);
        }
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
