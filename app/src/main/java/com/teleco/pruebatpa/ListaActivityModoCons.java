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

public class ListaActivityModoCons extends Activity {
    private ListView lista;
    private MiBaseDatos MDB;
    private Integer idConsorcio;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        Intent esteIntent = getIntent();
        idConsorcio = esteIntent.getIntExtra("Consorcio", 0);
        if(idConsorcio != 0) {
            MDB = new MiBaseDatos(getApplicationContext());
            ArrayList<ModoTrans> lista_modos = MDB.getModosTransporte(idConsorcio);
            lista = (ListView) findViewById(R.id.lista);
            lista.setAdapter(new AdaptadorLista(this, R.layout.municipio, lista_modos) {
                @Override
                public void onEntrada(Object entrada, View view) {
                    if (entrada != null) {
                        TextView texto_municipio = (TextView) view.findViewById(R.id.id_municipio_nom);
                        if (texto_municipio != null)
                            texto_municipio.setText(((ModoTrans)entrada).getDesc());

                        TextView texto_ID = (TextView) view.findViewById(R.id.modo_ID);
                        if (texto_ID != null)
                            texto_ID.setText(Integer.toString(((ModoTrans) entrada).getIdModo()));

                    }
                }
            });
            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    TextView textoID = (TextView) view.findViewById(R.id.modo_ID);
                    Integer modo = Integer.parseInt(textoID.getText().toString());
                    pasar_siguiente_actividad(modo);
                }
            });
        }
        else
            Log.e("Lista", "consorcio no recibido");
    }

    public void pasar_siguiente_actividad(Integer idModo){
        Intent intent = new Intent(this, ListActivity.class);
        //finish();  //Descomentar para destruir esta actividad antes de comenzar la siguiente
        intent.putExtra("idModo", idModo);
        intent.putExtra("idConsorcio", idConsorcio);
        startActivity(intent);
    }
}
