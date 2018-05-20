package com.teleco.pruebatpa;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class ListaActivityParadas extends Activity implements DatePickerDialog.OnDateSetListener {
    private RecyclerView lista;
    private MiBaseDatos MDB;
    private Integer idConsorcio;
    private Integer idMunicipio;
    private Integer idPar;
    private RecyclerView.LayoutManager mLayoutManager;
    private AdaptadorRecycler myAdapter;

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        pasar_siguiente_actividad(idPar, year, month+1, dayOfMonth);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        Intent esteIntent = getIntent();
        Calendar newDate = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, ListaActivityParadas.this, newDate.get(Calendar.YEAR), newDate.get(Calendar.MONTH), newDate.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        idConsorcio = esteIntent.getIntExtra("idConsorcio", 0);
        idMunicipio = esteIntent.getIntExtra("idMunicipio", 0);
        if(idConsorcio != 0 && idMunicipio != 0) {
            MDB = new MiBaseDatos(getApplicationContext());
            ArrayList<Parada> lista_paradas = MDB.getParadasByMunicipio(idConsorcio, idMunicipio);
            lista = (RecyclerView) findViewById(R.id.lista);
            lista.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            lista.setLayoutManager(mLayoutManager);
            myAdapter = new AdaptadorRecycler(lista_paradas, R.layout.parada, new OnItemClickListener() {
                @Override
                public void onItemClick(Object item) {
                    idPar = ((Parada)item).getIdParada();
                    datePickerDialog.show();
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

    public void pasar_siguiente_actividad(Integer idParada, int anio, int mes, int dia){
        Intent intent = new Intent(this, ActivityServiciosParada.class);
        //finish();  //Descomentar para destruir esta actividad antes de comenzar la siguiente
        intent.putExtra("idMunicipio", idMunicipio);
        intent.putExtra("idConsorcio", idConsorcio);
        intent.putExtra("idParada", idParada);
        intent.putExtra("Año", anio);
        intent.putExtra("Mes", mes);
        intent.putExtra("Dia", dia);
        startActivity(intent);
    }
}