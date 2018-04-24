package com.teleco.pruebatpa;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;

import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class MainActivity extends Activity {
    private MiBaseDatos MDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cargando);
        MDB = new MiBaseDatos(getApplicationContext());

        MDB.creaBBDD();
        if(MDB.isEmpty()){
            Thread t= new Thread() {
                public void run()    {
                    MDB.rellenar();
                    Log.d("BBDD","Base de datos rellena");
                    tablaRellena();
                }
            };
            t.start();
        }
        else
            tablaRellena();
    }
    public void tablaRellena()
    {
        setContentView(R.layout.activity_main);
    }

    public void boton_ConsultaParadas(View view){
        Intent intent = new Intent(this, ListaActivity.class);
        startActivity(intent);
     }

    public void boton_ConsultaModo(View view){

    }

    public void boton_ConsultaOrigDest (View view){

    }
}

