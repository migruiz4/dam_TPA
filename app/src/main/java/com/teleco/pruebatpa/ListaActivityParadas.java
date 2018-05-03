package com.teleco.pruebatpa;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListaActivityParadas extends Activity {
    private ListView lista;
    private MiBaseDatos MDB;
    private Integer idConsorcio;
    private Integer idMunicipio;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        Intent esteIntent = getIntent();
        idConsorcio = esteIntent.getIntExtra("idConsorcio", 0);
        idMunicipio = esteIntent.getIntExtra("idMunicipio", 0);
        if(idConsorcio != 0 && idMunicipio != 0) {
            MDB = new MiBaseDatos(getApplicationContext());
            ArrayList<Municipio> lista_municipios = MDB.getMunicipiosByConsorcio(idConsorcio);
            lista = (ListView) findViewById(R.id.lista);
            lista.setAdapter(new AdaptadorLista(this, R.layout.municipio, lista_municipios) {
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
}
