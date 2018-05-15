package com.teleco.pruebatpa;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListaActivityModoCons extends Activity {
    private RecyclerView lista;
    private MiBaseDatos MDB;
    private Integer idConsorcio;
    private LinearLayoutManager mLayoutManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        Intent esteIntent = getIntent();
        idConsorcio = esteIntent.getIntExtra("Consorcio", 0);
        if(idConsorcio != 0) {
            MDB = new MiBaseDatos(getApplicationContext());
            ArrayList<ModoTrans> lista_modos = MDB.getModosTransporte(idConsorcio);
            lista = (RecyclerView) findViewById(R.id.lista);
            lista.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            lista.setLayoutManager(mLayoutManager);
            lista.setAdapter(new AdaptadorRecycler(lista_modos, R.layout.modo, new OnItemClickListener() {
                @Override
                public void onItemClick(Object item) {
                    Integer modo = ((ModoTrans) item).getIdModo();
                    pasar_siguiente_actividad(modo);
                }
            }));
        }
        else
            Log.e("Lista", "consorcio no recibido");
    }

    public void pasar_siguiente_actividad(Integer idModo){
        Intent intent = new Intent(this, ListaActivityLineasModo.class);
        //finish();  //Descomentar para destruir esta actividad antes de comenzar la siguiente
        intent.putExtra("idModo", idModo);
        intent.putExtra("idConsorcio", idConsorcio);
        startActivity(intent);
    }
}
