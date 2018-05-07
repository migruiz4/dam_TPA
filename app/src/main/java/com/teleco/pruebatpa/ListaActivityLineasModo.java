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

public class ListaActivityLineasModo extends Activity implements DatePickerDialog.OnDateSetListener {
    private ListView lista;
    private MiBaseDatos MDB;
    private Integer idConsorcio;
    private Integer idModo;
    private Integer idLinea;

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
            lista = (ListView) findViewById(R.id.lista);
            lista.setAdapter(new AdaptadorLista(this, R.layout.linea, lista_lineas) {
                @Override
                public void onEntrada(Object entrada, View view) {
                    if (entrada != null) {
                        TextView texto_linea = (TextView) view.findViewById(R.id.id_linea_nom);
                        if (texto_linea != null)
                            texto_linea.setText(((Linea)entrada).getNombre());

                        TextView texto_ID = (TextView) view.findViewById(R.id.linea_ID);
                        if (texto_ID != null)
                            texto_ID.setText(Integer.toString(((Linea) entrada).getIdLinea()));

                        TextView texto_codigo = (TextView) view.findViewById(R.id.codigo_linea);
                        if (texto_codigo != null)
                            texto_codigo.setText(((Linea) entrada).getCodigo());

                        TextView texto_operador = (TextView) view.findViewById(R.id.id_operador_id);
                        if (texto_operador != null)
                            texto_operador.setText(((Linea) entrada).getOperador());

                    }
                }
            });
            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    TextView textoID = (TextView) view.findViewById(R.id.linea_ID);
                    idLinea = Integer.parseInt(textoID.getText().toString());
                    datePickerDialog.show();
                }
            });
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
