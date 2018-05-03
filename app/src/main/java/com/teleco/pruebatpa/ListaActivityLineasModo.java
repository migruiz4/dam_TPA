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

public class ListaActivityLineasModo extends Activity {
    private ListView lista;
    private MiBaseDatos MDB;
    private Integer idConsorcio;
    private Integer idModo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        Intent esteIntent = getIntent();
        idConsorcio = esteIntent.getIntExtra("idConsorcio", 0);
        idModo = esteIntent.getIntExtra("idModo", 0);
        if(idConsorcio != 0 && idModo != 0) {
            MDB = new MiBaseDatos(getApplicationContext());
            ArrayList<Linea> lista_municipios = MDB.getLineasByModo(idConsorcio, idModo);
            lista = (ListView) findViewById(R.id.lista);
            lista.setAdapter(new AdaptadorLista(this, R.layout.linea, lista_municipios) {
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
                    TextView textoID = (TextView) view.findViewById(R.id.municipio_ID);
                    Integer idMun = Integer.parseInt(textoID.getText().toString());
                    pasar_siguiente_actividad(idMun);
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
