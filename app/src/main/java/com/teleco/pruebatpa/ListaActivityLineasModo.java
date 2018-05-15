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

public class ListaActivityLineasModo extends Activity implements DatePickerDialog.OnDateSetListener {
    private RecyclerView lista;
    private MiBaseDatos MDB;
    private Integer idConsorcio;
    private Integer idModo;
    private Integer idLinea;
    private LinearLayoutManager mLayoutManager;

    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        pasar_siguiente_actividad(idLinea, year, month, dayOfMonth);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        Intent esteIntent = getIntent();
        Calendar newDate = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, ListaActivityLineasModo.this, newDate.get(Calendar.YEAR), newDate.get(Calendar.MONTH), newDate.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        idConsorcio = esteIntent.getIntExtra("idConsorcio", 0);
        idModo = esteIntent.getIntExtra("idModo", 0);
        if(idConsorcio != 0 && idModo != 0) {
            MDB = new MiBaseDatos(getApplicationContext());
            ArrayList<Linea> lista_lineas = MDB.getLineasByModo(idConsorcio, idModo);
            lista = (RecyclerView) findViewById(R.id.lista);
            lista.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            lista.setLayoutManager(mLayoutManager);
            lista.setAdapter(new AdaptadorRecycler(lista_lineas, R.layout.linea, new OnItemClickListener() {
                @Override
                public void onItemClick(Object item) {
                    idLinea = ((Linea) item).getIdLinea();
                    datePickerDialog.show();
                }
            }));

        }
        else
            Log.e("Lista", "consorcio no recibido");
    }

    public void pasar_siguiente_actividad(Integer idLin, int anio, int mes, int dia){
        Intent intent = new Intent(this, ListaActivityHorariosLineaModo.class);
        //finish();  //Descomentar para destruir esta actividad antes de comenzar la siguiente
        intent.putExtra("idModo", idModo);
        intent.putExtra("idConsorcio", idConsorcio);
        intent.putExtra("idLinea", idLin);
        intent.putExtra("Año", anio);
        intent.putExtra("Mes", mes);
        intent.putExtra("Dia", dia);
        startActivity(intent);
    }
}
