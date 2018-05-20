package com.teleco.pruebatpa;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.Semaphore;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class ListaActivityLineasMunicipio extends Activity implements DatePickerDialog.OnDateSetListener{
    private RecyclerView lista;
    private MiBaseDatos MDB;
    private Integer idConsorcio;
    private Integer idMunicipio;
    private Integer idLinea;
    private ArrayList<Linea> lista_lineas;
    private LinearLayoutManager mLayoutManager;
    private AdaptadorRecycler myAdapter;

    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        pasar_siguiente_actividad(idLinea, year, month, dayOfMonth);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        Intent esteIntent = getIntent();Calendar newDate = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, ListaActivityLineasMunicipio.this, newDate.get(Calendar.YEAR), newDate.get(Calendar.MONTH), newDate.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        idConsorcio = esteIntent.getIntExtra("idConsorcio", 0);
        idMunicipio = esteIntent.getIntExtra("idMunicipio", 0);
        if(idConsorcio != 0 && idMunicipio != 0) {
            MDB = new MiBaseDatos(getApplicationContext());
            ArrayList<Nucleo> lista_nucleos = MDB.getNucleosByMunicipio(idConsorcio, idMunicipio);
            lista_lineas = new ArrayList<Linea> ();

            for(Nucleo i : lista_nucleos){

                pideLineas(i.getIdNucleo().toString());

            }

            lista = (RecyclerView) findViewById(R.id.lista);
            lista.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            lista.setLayoutManager(mLayoutManager);
            myAdapter = new AdaptadorRecycler(lista_lineas, R.layout.linea, new OnItemClickListener() {
                @Override
                public void onItemClick(Object item) {
                    idLinea = ((Linea) item).getIdLinea();
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

    public void pasar_siguiente_actividad(Integer idLin, Integer anio, Integer mes, Integer dia){
        Intent intent = new Intent(this, ActivityHorariosLinea.class);
        //finish();  //Descomentar para destruir esta actividad antes de comenzar la siguiente
        intent.putExtra("idMunicipio", idMunicipio);
        intent.putExtra("idConsorcio", idConsorcio);
        intent.putExtra("idLinea", idLin);
        intent.putExtra("Año", anio);
        intent.putExtra("Mes", mes);
        intent.putExtra("Dia", dia);
        startActivity(intent);
    }

    public void respuesta_rest(JSONObject response) throws JSONException{
        JSONArray array = response.getJSONArray("lineas");
        JSONObject lineas = null;

        if (array.length() > 0) {
            for (int i = 0; i < array.length(); i++) {
                lineas = array.getJSONObject(i);
                int id_linea = lineas.getInt("idLinea");
                int id_modo = lineas.getInt("idModo");
                String cod = lineas.getString("codigo");
                String nombre = lineas.getString("nombre");
                String opera = lineas.getString("operadores");
                if (id_linea != 0) {
                    lista_lineas.add(new Linea(id_linea,idConsorcio, cod, nombre, opera));
                }
            }
            lista.getAdapter().notifyDataSetChanged();
        }

    }

    public void pideLineas(final String idNucleo){

            final String URL = "http://api.ctan.es/v1/Consorcios/"+ idConsorcio.toString() +"/nucleos/"+ idNucleo + "/lineas";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,URL, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        respuesta_rest(response);

                    }
                    catch (JSONException e){
                        Log.d("Lineas", e.toString());
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Lineas", error.toString());
                    pideLineas(idNucleo);
                }
            });
            // add the request object to the queue to be executed
            PruebaTPAApplication.getInstance().getRequestQueue().add(request);
        }
    }
