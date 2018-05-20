package com.teleco.pruebatpa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class ActivityHorariosLinea extends Activity {
    private Integer idConsorcio;
    private Integer idModo;
    private Integer idLinea;
    private Integer anio;
    private Integer mes;
    private Integer dia;
    private Integer hora;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView lista;
    private AdaptadorRecycler myAdapter;
    private ArrayList<InfoHorarioLinea> lista_horarios;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        Intent esteIntent = getIntent();
        idConsorcio = esteIntent.getIntExtra("idConsorcio", 0);
        idModo = esteIntent.getIntExtra("idModo", 0);
        idLinea = esteIntent.getIntExtra("idLinea", 0);
        anio = esteIntent.getIntExtra("Año", 0);
        mes = esteIntent.getIntExtra("Mes", 0);
        dia = esteIntent.getIntExtra("Dia", 0);
        lista_horarios = new ArrayList<InfoHorarioLinea>();

        if(idConsorcio != 0 && idModo != 0 && idLinea != 0) {
            lista = (RecyclerView) findViewById(R.id.lista);
            lista.setHasFixedSize(false);
            mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
            lista.setLayoutManager(mLayoutManager);

            myAdapter = new AdaptadorRecycler(lista_horarios, R.layout.horarios_linea, new OnItemClickListener() {
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
            consultaHorarios(idConsorcio, idLinea, anio, mes, dia);
        }
        else
            Log.e("Lista", "consorcio no recibido");
    }
    public void respuesta_rest(JSONObject response) throws JSONException {

        ArrayList<String> nombre_ida = new ArrayList<String>();
        ArrayList<String> nombre_vuelta = new ArrayList<String>();
        ArrayList<String[]> horariosIda = new ArrayList<String[]>();
        ArrayList<String[]> horariosVuelta = new ArrayList<String[]>();
        JSONArray array = response.getJSONArray("planificadores");
        JSONObject planificador = null;
        JSONObject bloque = null;
        JSONObject horario = null;
        if (array.length() > 0) {
            for (int i = 0; i < array.length(); i++) {
                planificador = array.getJSONObject(i);
                JSONArray bloques_ida = planificador.getJSONArray("bloquesIda");
                JSONArray bloques_vuelta = planificador.getJSONArray("bloquesVuelta");
                JSONArray horario_ida = planificador.getJSONArray("horarioIda");
                JSONArray horario_vuelta = planificador.getJSONArray("horarioVuelta");

                if (bloques_ida.length() > 0)
                {    for (int j = 0; j < bloques_ida.length(); j++) {
                        bloque = bloques_ida.getJSONObject(j);
                        if (bloque.getString("nombre").equals("Frecuencia")) {
                            //No hacemos nada
                        } else if (bloque.getString("nombre").equals("Observaciones")){
                            //No hacemos nada
                        } else {
                            nombre_ida.add(bloque.getString("nombre"));
                        }
                    }

                    //HORARIOS
                    if(horario_ida.length() > 0) {
                        for (int j = 0; j < horario_ida.length(); j++) {
                            horario = horario_ida.getJSONObject(j);
                            JSONArray horasIda = horario.getJSONArray("horas");
                            String observaciones = horario.getString("observaciones");
                            String frecuencia = horario.getString("frecuencia");
                            ArrayList<String> info = new ArrayList<String>();
                            String[] horas_ida = new String[horasIda.length()];
                            for(int k = 0; k < horasIda.length(); k++) {
                                horas_ida[k] = horasIda.getString(k);
                                info.add(nombre_ida.get(k) + " -> " + horas_ida[k]);
                            }
                            InfoHorarioLinea horario_linea_ida = new InfoHorarioLinea();
                            horario_linea_ida.setObservaciones(observaciones);
                            horario_linea_ida.setFrecuencia(frecuencia);
                            horario_linea_ida.setInformacion_linea(info);
                            horario_linea_ida.setTipo_horario("IDA");
                            lista_horarios.add(horario_linea_ida);
                        }
                    }
                    //////////
                }

                if (bloques_vuelta.length() > 0)
                {    for (int j = 0; j < bloques_vuelta.length(); j++) {
                    bloque = bloques_vuelta.getJSONObject(j);
                    if (bloque.getString("nombre").equals("Frecuencia")) {
                        //No hacemos nada
                    } else if (bloque.getString("nombre").equals("Observaciones")){
                        //No hacemos nada
                    } else {
                        nombre_vuelta.add(bloque.getString("nombre"));
                    }
                }
                    //HORARIOS
                    if(horario_vuelta.length() > 0) {
                        for (int j = 0; j < horario_vuelta.length(); j++) {
                            horario = horario_vuelta.getJSONObject(j);
                            JSONArray horasVuelta = horario.getJSONArray("horas");
                            String observaciones = horario.getString("observaciones");
                            String frecuencia = horario.getString("frecuencia");
                            ArrayList<String> info = new ArrayList<String>();
                            String[] horas_vuelta = new String[horasVuelta.length()];
                            for(int k = 0; k < horasVuelta.length(); k++) {
                                horas_vuelta[k] = horasVuelta.getString(k);
                                info.add(nombre_ida.get(k) + " -> " + horas_vuelta[k]);
                            }
                            InfoHorarioLinea horario_linea_vuelta = new InfoHorarioLinea();
                            horario_linea_vuelta.setObservaciones(observaciones);
                            horario_linea_vuelta.setFrecuencia(frecuencia);
                            horario_linea_vuelta.setInformacion_linea(info);
                            horario_linea_vuelta.setTipo_horario("VUELTA");
                            lista_horarios.add(horario_linea_vuelta);
                        }
                    }
                    //////////
                }
                /*
                if (bloques_vuelta.length() > 0)
                {       for (int j = 0; j < bloques_vuelta.length(); j++) {
                        bloque = bloques_vuelta.getJSONObject(j);
                        if (bloque.getString("nombre").equals("Frecuencia")) {
                            //No hacemos nada
                        } else if (bloque.getString("nombre").equals("Observaciones")){
                            //No hacemos nada
                        } else {
                            nombre_vuelta.add(bloque.getString("nombre"));
                        }
                    }
                    horario_linea.setInformacion_linea(nombre_vuelta);
                    horario_linea.setTipo_horario("VUELTA");
                }


                if(horario_vuelta.length() > 0) {
                    for (int j = 0; j < horario_vuelta.length(); j++) {
                        horario = horario_vuelta.getJSONObject(j);
                        JSONArray horasVuelta = horario.getJSONArray("horas");
                        String[] horas_vuelta = new String[horasVuelta.length()];
                        for(int l = 0; l < horasVuelta.length(); l++)
                            horas_vuelta[l] = horasVuelta.getString(l);
                        horariosVuelta.add(horas_vuelta);
                    }
                    horarioFinal.setHorasVuelta(horariosVuelta);
                }
                lista_horarios.add(horarioFinal);
                *//*
                for (String msg : horarioFinal.getBloquesIda()){
                    Log.d("HORARIO_DEBUG",msg);
                }
                for (String msg : horarioFinal.getBloquesVuelta()){
                    Log.d("HORARIO_DEBUG",msg);
                }
                for (String msg[] : horarioFinal.getHorasIda()){
                    for (String m : msg) {
                        Log.d("HORARIO_DEBUG", m);
                    }
                }
                for (String msg[] : horarioFinal.getHorasVuelta()){
                    for (String m : msg) {
                        Log.d("HORARIO_DEBUG", m);
                    }
                }
                */
            }
        }
    }

    public void consultaHorarios(final Integer idConsorcio, final Integer idLinea, final Integer año, final Integer mes, final Integer dia) {
        final String URL = "http://api.ctan.es/v1/Consorcios/" +idConsorcio.toString() +"/horarios_lineas?dia="+ dia.toString()
                +"&lang=ES&linea="+idLinea.toString()+"&mes=" + mes.toString();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    respuesta_rest(response);
                    lista.getAdapter().notifyDataSetChanged();
                }
                catch (JSONException e){
                    Log.d("Servicio", e.toString());
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Servicio", error.toString());
                consultaHorarios(idConsorcio, idLinea, anio, mes, dia);
            }
        });
        // add the request object to the queue to be executed
        PruebaTPAApplication.getInstance().getRequestQueue().add(request);
    }
}
