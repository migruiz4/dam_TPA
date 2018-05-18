package com.teleco.pruebatpa;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.FlipInBottomXAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class ListaActivityMunicipiosParadas extends Activity {
    private MiBaseDatos MDB;
    private Integer idConsorcio;
    private ArrayList<Municipio> lista_municipios;
    private RecyclerView lista;
    private RecyclerView.LayoutManager mLayoutManager;
    private AdaptadorRecycler myAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        Intent esteIntent = getIntent();
        idConsorcio = esteIntent.getIntExtra("Consorcio", 0);
        if(idConsorcio != 0) {
            MDB = new MiBaseDatos(getApplicationContext());
            lista_municipios = MDB.getMunicipiosByConsorcio(idConsorcio);

            lista = (RecyclerView) findViewById(R.id.lista);
            lista.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            lista.setLayoutManager(mLayoutManager);
            myAdapter = new AdaptadorRecycler(lista_municipios, R.layout.municipio, new OnItemClickListener() {
                @Override
                public void onItemClick(Object item) {
                    Integer idMun = ((Municipio)item).getIdMunicipio();
                    pasar_siguiente_actividad(idMun);
                }
            });
            ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(myAdapter);
            scaleAdapter.setFirstOnly(false);
            scaleAdapter.setDuration(80);
            AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(scaleAdapter);
            alphaAdapter.setFirstOnly(false);
            alphaAdapter.setDuration(50);
            lista.setAdapter(alphaAdapter);

        }
        else
            Log.e("Lista", "consorcio no recibido");
    }

    public void pasar_siguiente_actividad(Integer idMunicipio){
        Intent intent = new Intent(this, ListaActivityParadas.class);
        //finish();  //Descomentar para destruir esta actividad antes de comenzar la siguiente
        intent.putExtra("idMunicipio", idMunicipio);
        intent.putExtra("idConsorcio", idConsorcio);
        startActivity(intent);
    }
}

