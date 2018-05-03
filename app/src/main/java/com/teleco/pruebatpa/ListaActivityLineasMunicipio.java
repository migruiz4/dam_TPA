package com.teleco.pruebatpa;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.concurrent.Semaphore;

public class ListaActivityLineasMunicipio extends Activity{
    private ListView lista;
    private MiBaseDatos MDB;
    private Integer idConsorcio;
    private Integer idMunicipio;
    private ArrayList<Linea> lista_lineas;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        Intent esteIntent = getIntent();
        idConsorcio = esteIntent.getIntExtra("idConsorcio", 0);
        idMunicipio = esteIntent.getIntExtra("idMunicipio", 0);
        if(idConsorcio != 0 && idMunicipio != 0) {
            MDB = new MiBaseDatos(getApplicationContext());
            ArrayList<Nucleo> lista_nucleos = MDB.getNucleosByMunicipio(idConsorcio, idMunicipio);
            lista_lineas = new ArrayList<Linea> ();

            for(Nucleo i : lista_nucleos){

                pideLineas(i.getIdNucleo().toString());

            }
        }
        else
            Log.e("Lista", "consorcio no recibido");
    }

    public void pasar_siguiente_actividad(Integer idMunicipio){
        Intent intent = new Intent(this, ListActivity.class);
        //finish();  //Descomentar para destruir esta actividad antes de comenzar la siguiente
        intent.putExtra("idMunicipio", idMunicipio);
        intent.putExtra("idConsorcio", idConsorcio);
        startActivity(intent);
    }

    public void respuesta_rest(){

        lista = (ListView) findViewById(R.id.lista);
        lista.setAdapter(new AdaptadorLista(this, R.layout.municipio, lista_lineas) {
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null) {
                    TextView texto_municipio = (TextView) view.findViewById(R.id.id_municipio_nom);
                    if (texto_municipio != null)
                        texto_municipio.setText(((Municipio)entrada).getDatos());

                    TextView texto_ID = (TextView) view.findViewById(R.id.municipio_ID);
                    if (texto_ID != null)
                        texto_ID.setText(Integer.toString(((Municipio) entrada).getIdMunicipio()));

                }
            }
        });
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                TextView textoID = (TextView) view.findViewById(R.id.municipio_ID);
                Integer idMun = Integer.parseInt(textoID.getText().toString());
                pasar_siguiente_actividad(idMun);
            }
        });
    }

    public void pideLineas(String idNucleo){

            final String URL = "http://api.ctan.es/v1/Consorcios/"+ idConsorcio.toString() +"/nucleos/"+ idNucleo + "/lineas";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,URL, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {

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
                                    //lista_lineas.add(new Linea(id_linea,idConsorcio));
                                }
                            }
                        }
                    }
                    catch (JSONException e){
                        Log.d("BBDD", e.toString());
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("BBDD", error.toString());
                    //rellenar_lineas(idConsorcio, sem);
                }
            });
            // add the request object to the queue to be executed
            PruebaTPAApplication.getInstance().getRequestQueue().add(request);
        }
    }
