package com.teleco.pruebatpa;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class ListaActivityParadas extends Activity implements DatePickerDialog.OnDateSetListener {
    private ListView lista;
    private MiBaseDatos MDB;
    private Integer idConsorcio;
    private Integer idMunicipio;
    private Integer idPar;
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        pasar_siguiente_actividad(idPar, year, month, dayOfMonth);
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
            lista = (ListView) findViewById(R.id.lista);
            lista.setAdapter(new AdaptadorLista(this, R.layout.parada, lista_paradas) {
                @Override
                public void onEntrada(Object entrada, View view) {
                    if (entrada != null) {
                        TextView texto_parada = (TextView) view.findViewById(R.id.id_parada_nom);
                        if (texto_parada != null)
                            texto_parada.setText(((Parada)entrada).getNombre());

                        TextView texto_ID = (TextView) view.findViewById(R.id.parada_ID);
                        if (texto_ID != null)
                            texto_ID.setText(Integer.toString(((Parada) entrada).getIdParada()));

                    }
                }
            });
            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    TextView textoID = (TextView) view.findViewById(R.id.parada_ID);
                    idPar = Integer.parseInt(textoID.getText().toString());
                    datePickerDialog.show();
                }
            });
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
