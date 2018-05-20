package com.teleco.pruebatpa;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class ActivityServiciosParada extends Activity {
    private Integer idConsorcio;
    private Integer idMunicipio;
    private Integer idParada;
    private Integer anio;
    private Integer mes;
    private Integer dia;
    private Integer hora;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView lista;
    private AdaptadorRecycler myAdapter;
    private EndlessRecyclerViewScrollListener scrollListener;
    private ArrayList<Servicio> lista_servicios;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        Intent esteIntent = getIntent();
        idConsorcio = esteIntent.getIntExtra("idConsorcio", 0);
        idMunicipio = esteIntent.getIntExtra("idMunicipio", 0);
        idParada = esteIntent.getIntExtra("idParada", 0);
        anio = esteIntent.getIntExtra("Año", 0);
        mes = esteIntent.getIntExtra("Mes", 0);
        dia = esteIntent.getIntExtra("Dia", 0);
        lista_servicios = new ArrayList<Servicio> ();

        if(idConsorcio != 0 && idMunicipio != 0 && idParada != 0) {
            lista = (RecyclerView) findViewById(R.id.lista);
            lista.setHasFixedSize(false);
            mLayoutManager = new LinearLayoutManager(this);
            lista.setLayoutManager(mLayoutManager);

            myAdapter = new AdaptadorRecycler(lista_servicios, R.layout.servicio, new OnItemClickListener() {
                @Override
                public void onItemClick(Object item) {
                    //
                }
            });
            ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(myAdapter);
            scaleAdapter.setFirstOnly(false);
            scaleAdapter.setDuration(80);
            AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(scaleAdapter);
            alphaAdapter.setFirstOnly(false);
            alphaAdapter.setDuration(50);
            lista.setAdapter(alphaAdapter);
            hora = 6;
            consultaServicios(idConsorcio, idParada, anio, mes, dia, hora);

            scrollListener = new EndlessRecyclerViewScrollListener((LinearLayoutManager) mLayoutManager) {
                @Override
                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                    // Triggered only when new data needs to be appended to the list
                    // Add whatever code is needed to append new items to the bottom of the list
                    if (hora <= 23) {
                        hora++;
                        consultaServicios(idConsorcio, idParada, anio, mes, dia, hora);
                    }
                }
            };
            lista.addOnScrollListener(scrollListener);
        }
        else
            Log.e("Lista", "consorcio no recibido");
    }
    public void respuesta_rest(JSONObject response) throws JSONException{

        JSONArray array = response.getJSONArray("servicios");
        JSONObject servicio = null;
        response.getString("horaFin");
        if (array.length() > 0) {
            for (int i = 0; i < array.length(); i++) {
                servicio = array.getJSONObject(i);
                int id_linea = servicio.getInt("idLinea");
                String hora = servicio.getString("servicio");
                String cod = servicio.getString("linea");
                String nombre = servicio.getString("nombre");
                Integer sentido = servicio.getInt("sentido");
                String destino = servicio.getString("destino");
                Integer tipo = servicio.getInt("tipo"); //0 subida/bajada 1 solo subida 2 solo bajada
                if (id_linea != 0) {
                    Servicio serv = new Servicio(idParada, idMunicipio, idConsorcio, id_linea, hora, nombre, cod, sentido, destino, tipo);
                    lista_servicios.add(serv);
                    lista.getAdapter().notifyItemInserted(lista_servicios.size());
                }
            }
        }
        Log.d("HOLA", lista_servicios.toString());
    }
    public void consultaServicios(final Integer idConsorcio, final Integer idParada, final Integer año, final Integer mes, final Integer dia, final Integer hora) {

        final String URL = "http://api.ctan.es/v1/Consorcios/"+ idConsorcio.toString() +"/paradas/"+ idParada.toString() + "/servicios?horaIni=" +
                String.format("%02d", dia) + "-" + String.format("%02d", mes) + "-" + año.toString()+ "+" + String.format("%02d", hora) + ":00" ;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    respuesta_rest(response);
                    //lista.getAdapter().notifyDataSetChanged();
                }
                catch (JSONException e){
                    Log.d("Servicio", e.toString());
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Servicio", error.toString());
                consultaServicios(idConsorcio, idParada, anio, mes, dia, hora);
            }
        });
        // add the request object to the queue to be executed
        PruebaTPAApplication.getInstance().getRequestQueue().add(request);
    }
}
