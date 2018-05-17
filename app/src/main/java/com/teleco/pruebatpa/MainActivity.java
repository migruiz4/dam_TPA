package com.teleco.pruebatpa;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.widget.Toolbar;

import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.List;
import java.util.ArrayList;

public class MainActivity extends Activity {
    private MiBaseDatos MDB;
    private Spinner mySpinner;
    ArrayList<String> arrayListConsorcios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar barra_menu = (Toolbar) findViewById(R.id.my_toolbar);
        setActionBar(barra_menu);

        mySpinner = (Spinner) findViewById(R.id.spinner);
        arrayListConsorcios = new ArrayList<String>();
        arrayListConsorcios.add("Seleccione un Consorcio");

        ImageView img= (ImageView) findViewById(R.id.foto_boton1);
        img.setImageResource(R.drawable.my_image);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String> (this,R.layout.spinner,arrayListConsorcios);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mySpinner.setAdapter(myAdapter);

        MDB = new MiBaseDatos(getApplicationContext());

        MDB.creaBBDD();
        if(MDB.needUpdate()){
            final ProgressDialog ringProgressDialog = ProgressDialog.show(this, "Descargando datos", "Por favor espere...", true);
            //you usually don't want the user to stop the current process, and this will make sure of that
            ringProgressDialog.setCancelable(false);

            Thread t= new Thread() {
                public void run()    {
                    MDB.rellenar();
                    Log.d("BBDD","Base de datos rellena");
                    ringProgressDialog.cancel();
                    tablaRellena();
                }
            };
            t.start();
        }
        else{
            tablaRellena();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal_desplegable, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tablaRellena()
    {

        ArrayList<Consorcio> lista_consorcios = MDB.getConsorcios();
        for(Consorcio cons : lista_consorcios)
            arrayListConsorcios.add(cons.getNombre());
        mySpinner.getSelectedItemPosition();
    }

    public void boton_ConsultaParadas(View view){
        if(mySpinner.getSelectedItemPosition() == 0)
        {
            Toast.makeText(getApplicationContext(),
                    "Debe seleccionar un consorcio previamente", Toast.LENGTH_SHORT).show();
        }else{
            Log.d("Cons", Integer.toString(mySpinner.getSelectedItemPosition()));
            Intent intent = new Intent(this, ListaActivityMunicipiosParadas.class);
            intent.putExtra("Consorcio", mySpinner.getSelectedItemPosition());
            startActivity(intent);
        }

     }

    public void boton_ConsultaModo(View view){
        if(mySpinner.getSelectedItemPosition() == 0)
        {
            Toast.makeText(getApplicationContext(),
                    "Debe seleccionar un consorcio previamente", Toast.LENGTH_SHORT).show();
        }else{
            Log.d("Cons", Integer.toString(mySpinner.getSelectedItemPosition()));
            Intent intent = new Intent(this, ListaActivityModoCons.class);
            intent.putExtra("Consorcio", mySpinner.getSelectedItemPosition());
            startActivity(intent);
        }
    }

    public void boton_ConsultaOrigDest (View view){
        if(mySpinner.getSelectedItemPosition() == 0)
        {
            Toast.makeText(getApplicationContext(),
                    "Debe seleccionar un consorcio previamente", Toast.LENGTH_SHORT).show();
        }else{
            Log.d("Cons", Integer.toString(mySpinner.getSelectedItemPosition()));
            Intent intent = new Intent(this, ListaActivityMunicipiosOrigen.class);
            intent.putExtra("Consorcio", mySpinner.getSelectedItemPosition());
            startActivity(intent);
        }
    }
}

