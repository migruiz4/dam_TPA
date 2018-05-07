package com.teleco.pruebatpa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Semaphore;


public class MiBaseDatos extends SQLiteOpenHelper {

    private static final int VERSION_BASEDATOS = 1;
    private static final String NOMBRE_BASEDATOS = "tpapp.db";

    private static final String CONSORCIOS_TABLE = "Consorcios";
    private static final String CONSORCIOS_ID = "idConsorcios";
    private static final String CONSORCIOS_NOMBRE = "nombre";
    private static final String CONSORCIOS_NOMBRECORTO = "nombreCorto";

    private static final String CONSORCIOS_TABLE_CREATE = "create table if not exists "
            + CONSORCIOS_TABLE + " ("
            + CONSORCIOS_ID + " integer, "
            + CONSORCIOS_NOMBRE + " text not null, "
            + CONSORCIOS_NOMBRECORTO + " text not null, "
            + " PRIMARY KEY(" + CONSORCIOS_ID + "));";

    private static final String MODO_TABLE = "Modo";
    private static final String MODO_ID = "idModo";
    private static final String MODO_DESC = "descModo";

    private static final String MODO_TABLE_CREATE = "create table if not exists "
            + MODO_TABLE + " ("
            + MODO_ID + " integer not null, "
            + CONSORCIOS_ID + " integer not null, "
            + MODO_DESC + " text not null, "
            + " PRIMARY KEY(" + MODO_ID + ", " + CONSORCIOS_ID +")," +
            "  FOREIGN KEY("+ CONSORCIOS_ID +") REFERENCES "+ CONSORCIOS_TABLE + "(" + CONSORCIOS_ID + ") ON DELETE NO ACTION ON UPDATE NO ACTION);";

    private static final String MUNICIPIO_TABLE = "Municipio";
    private static final String MUNICIPIO_ID = "idMunicipio";
    private static final String MUNICIPIO_DATOS = "datosMunicipio";

    private static final String MUNICIPIO_TABLE_CREATE = "create table if not exists "
            + MUNICIPIO_TABLE + " ("
            + MUNICIPIO_ID + " integer not null, "
            + CONSORCIOS_ID + " integer not null, "
            + MUNICIPIO_DATOS + " text not null, "
            + " PRIMARY KEY(" + MUNICIPIO_ID + ", " + CONSORCIOS_ID +")," +
            "  FOREIGN KEY("+ CONSORCIOS_ID +") REFERENCES "+ CONSORCIOS_TABLE + "(" + CONSORCIOS_ID + ") ON DELETE NO ACTION ON UPDATE NO ACTION);";

    private static final String FRECUENCIA_TABLE = "Frecuencia";
    private static final String FRECUENCIA_ID = "idFrecuencia";
    private static final String FRECUENCIA_COD = "codigoFrecuencia";
    private static final String FRECUENCIA_NOMBRE = "nombreFrecuencia";

    private static final String FRECUENCIA_TABLE_CREATE = "create table if not exists "
            + FRECUENCIA_TABLE + " ("
            + FRECUENCIA_ID + " integer not null, "
            + CONSORCIOS_ID + " integer not null, "
            + FRECUENCIA_COD + " text not null, "
            + FRECUENCIA_NOMBRE + " text not null, "
            + " PRIMARY KEY(" + FRECUENCIA_ID + ", " + CONSORCIOS_ID +")," +
            "  FOREIGN KEY("+ CONSORCIOS_ID +") REFERENCES "+ CONSORCIOS_TABLE + "(" + CONSORCIOS_ID + ") ON DELETE NO ACTION ON UPDATE NO ACTION);";


    private static final String NUCLEO_TABLE = "Nucleo";
    private static final String NUCLEO_ID = "idNucleo";
    private static final String NUCLEO_NOMBRE = "nombreNucleo";

    private static final String NUCLEO_TABLE_CREATE = "create table if not exists "
            + NUCLEO_TABLE + " ("
            + NUCLEO_ID + " integer not null, "
            + CONSORCIOS_ID + " integer not null, "
            + MUNICIPIO_ID + " integer not null, "
            + NUCLEO_NOMBRE + " text not null, "
            + " PRIMARY KEY(" + NUCLEO_ID + ", " + CONSORCIOS_ID +", " + MUNICIPIO_ID +")," +
            "  FOREIGN KEY("+ MUNICIPIO_ID +", "+ CONSORCIOS_ID +") REFERENCES "+ MUNICIPIO_TABLE + "(" +  MUNICIPIO_ID +", "+ CONSORCIOS_ID + ") ON DELETE NO ACTION ON UPDATE NO ACTION);";

    private static final String PARADA_TABLE = "Parada";
    private static final String PARADA_ID = "idParada";
    private static final String PARADA_NOMBRE = "nombreParada";
    private static final String PARADA_LAT = "latitudParada";
    private static final String PARADA_LONG = "longitudParada";

    private static final String PARADA_TABLE_CREATE = "create table if not exists "
            + PARADA_TABLE + " ("
            + PARADA_ID + " integer not null, "
            + MUNICIPIO_ID + " integer not null, "
            + NUCLEO_ID + " integer not null, "
            + CONSORCIOS_ID + " integer not null, "
            + PARADA_NOMBRE + " text not null, "
            + PARADA_LAT + " float not null, "
            + PARADA_LONG + " float not null, "
            + " PRIMARY KEY(" + PARADA_ID + ", " + CONSORCIOS_ID +", "+ MUNICIPIO_ID+", " + NUCLEO_ID + ")," +
            "  FOREIGN KEY("+ NUCLEO_ID +") REFERENCES "+ NUCLEO_TABLE + "(" + NUCLEO_ID +") ON DELETE NO ACTION ON UPDATE NO ACTION,"+
            "  FOREIGN KEY("+ MUNICIPIO_ID +", "+ CONSORCIOS_ID+") REFERENCES "+ MUNICIPIO_TABLE + "(" + MUNICIPIO_ID +", "+ CONSORCIOS_ID + ") ON DELETE NO ACTION ON UPDATE NO ACTION);";

    private static final String LINEA_TABLE = "Linea";
    private static final String LINEA_ID = "idLinea";
    private static final String LINEA_CODIGO = "codigoLinea";
    private static final String LINEA_OPERA = "operadoresLinea";
    private static final String LINEA_NOMBRE = "nombreLinea";

    private static final String LINEA_TABLE_CREATE = "create table if not exists "
            + LINEA_TABLE + " ("
            + LINEA_ID + " integer not null, "
            + CONSORCIOS_ID + " integer not null, "
            + MODO_ID + " integer not null, "
            + LINEA_CODIGO + " text not null, "
            + LINEA_NOMBRE + " text not null, "
            + LINEA_OPERA + " text not null, "
            + " PRIMARY KEY(" + LINEA_ID + ", " + CONSORCIOS_ID +", "+ MODO_ID+ ")," +
            "  FOREIGN KEY("+ MODO_ID +", "+ CONSORCIOS_ID+") REFERENCES "+ MODO_TABLE + "(" + MODO_ID +", "+ CONSORCIOS_ID + ") ON DELETE NO ACTION ON UPDATE NO ACTION);";

    //tabla que guarda la fecha en la que la bbdd fue actualizada, para al principio de cada ejecución ejecute isUpdated() en sustitución de isEmpty()
    private static final String ACT_TABLE = "tablaAct";
    private static final String ACT_ID = "idAct";
    private static final String ACT_FECHA = "fechaAct";

    private static final String ACT_TABLE_CREATE = "create table if not exists "
     + ACT_TABLE + " ("
     + ACT_ID + " integer primary key autoincrement, "
     + ACT_FECHA + " int not null);";


    public MiBaseDatos(Context context) {
        super(context, NOMBRE_BASEDATOS, null, VERSION_BASEDATOS);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CONSORCIOS_TABLE_CREATE);
        db.execSQL(MUNICIPIO_TABLE_CREATE);
        db.execSQL(NUCLEO_TABLE_CREATE);
        db.execSQL(PARADA_TABLE_CREATE);
        db.execSQL(MODO_TABLE_CREATE);
        db.execSQL(FRECUENCIA_TABLE_CREATE);
        db.execSQL(LINEA_TABLE_CREATE);
        db.execSQL(ACT_TABLE_CREATE);
        Log.d("BBDD","Tablas creadas");
    }

    public void creaBBDD(){
        SQLiteDatabase db = getReadableDatabase();

        db.execSQL(CONSORCIOS_TABLE_CREATE);
        db.execSQL(MUNICIPIO_TABLE_CREATE);
        db.execSQL(NUCLEO_TABLE_CREATE);
        db.execSQL(PARADA_TABLE_CREATE);
        db.execSQL(MODO_TABLE_CREATE);
        db.execSQL(FRECUENCIA_TABLE_CREATE);
        db.execSQL(LINEA_TABLE_CREATE);
        db.execSQL(ACT_TABLE_CREATE);

        db.close();
        Log.d("BBDD","Tablas creadas");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //La función isUpdated() comprueba si la tabla está rellena correctamente o no comprobando si hay entradas en la tabla de actualizaciones
    //Podría sustituirse por isUpdated, para comprobar la diferencia entre la fecha de última actualización y la actual
    // y borrara la base de datos en caso de que necesitase actualización
    public boolean needUpdate(){
        boolean empty;
        SQLiteDatabase db = getReadableDatabase();
        String[] valores_recuperar = {ACT_ID, ACT_FECHA};
        Cursor c = db.query(ACT_TABLE, valores_recuperar, null , null, null, null, null, null);
        Log.d("BBDD", "Numero de filas en Tabla Actualizaciones ="+ c.getCount());
        if(c.getCount() == 0) {
            borrarBBDD();
            creaBBDD();
            empty = true;
        }else{
            empty = false;
            c.close();
        }
        db.close();
        return empty;
    }

    public boolean rellenar(){
        final Semaphore sem  = new Semaphore(0);
        boolean error = false;
        Log.d("BBDD","Procedemos a rellenar las tablas");
        try {
            Thread t= new Thread() {
                public void run()    {
                rellenar_consorcios(sem);
                }
            };
            t.start();
            sem.acquire();
        }catch (Exception e){
            Log.d("BBDD", e.toString());
        }
        SQLiteDatabase db = getReadableDatabase();
        String[] valores_recuperar = {CONSORCIOS_ID};
        final Cursor c = db.query(CONSORCIOS_TABLE, valores_recuperar, null, null, null, null, null, null);
        c.moveToFirst();
        if(c.getCount() != 0){
        do {
            try{
            Integer num_Consorcio = c.getInt(0);
            Log.d("BBDD", "Rellenando consorcio " + num_Consorcio.toString());
            Thread a= new Thread() {
                    public void run()    {
                        rellenar_municipios(c.getInt(0), sem);
                    }
                };
            a.start();
            sem.acquire();

            Thread b= new Thread() {
                public void run()    {
                    rellenar_nucleos(c.getInt(0), sem);
                }
            };
            b.start();
            sem.acquire();

            Thread z= new Thread() {
                public void run()    {
                    rellenar_paradas(c.getInt(0), sem);
                }
            };
            z.start();
            sem.acquire();
            Thread d= new Thread() {
                public void run()    {
                    rellenar_modos(c.getInt(0), sem);
                }
            };
            d.start();
            sem.acquire();
            Thread e= new Thread() {
                public void run()    {
                    rellenar_frecuencias(c.getInt(0), sem);
                }
            };
            e.start();
            sem.acquire();
            Thread f= new Thread() {
                public void run()    {
                    rellenar_lineas(c.getInt(0), sem);
                }
            };
            f.start();
            sem.acquire();
            }catch (Exception e){
                Log.d("BBDD", e.toString());
            }
        } while (c.moveToNext());}
        else
            error = true;
        db.close();
        c.close();

        SQLiteDatabase db2 = getReadableDatabase();
        ContentValues valores = new ContentValues();
        Date fecha = new Date();
        valores.put(ACT_FECHA, fecha.getTime());
        db2.insert(ACT_TABLE, null, valores);
        db2.close();


        Log.d("BBDD", "¿Hay error?: " + error);
        return error;
    }

    private void rellenar_consorcios(final Semaphore sem){
        final String URL = "http://api.ctan.es/v1/Consorcios/7/consorcios";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("consorcios");
                    JSONObject cons= null;
                    SQLiteDatabase db = getWritableDatabase();

                    if (db != null) {
                        if (array.length() > 0)
                        {
                            for (int i = 0; i < array.length(); i++) {
                                cons = array.getJSONObject(i);
                                int id = cons.getInt("idConsorcio");
                                String nombre = cons.getString("nombre");
                                String nombreCorto = cons.getString("nombreCorto");
                                if(id!=0) {
                                    ContentValues valores = new ContentValues();
                                    valores.put(CONSORCIOS_ID, id);
                                    valores.put(CONSORCIOS_NOMBRE, nombre);
                                    valores.put(CONSORCIOS_NOMBRECORTO, nombreCorto);
                                    db.insert(CONSORCIOS_TABLE, null, valores);
                                }
                            }
                            Log.d("BBDD","Tabla Consorcios rellena");
                            sem.release();
                        }

                        db.close();
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
                rellenar_consorcios(sem);
            }
        });
        // add the request object to the queue to be executed
        PruebaTPAApplication.getInstance().getRequestQueue().add(request);
    }

    private void rellenar_municipios(final Integer idConsorcio, final Semaphore sem){

        final String URL = "http://api.ctan.es/v1/Consorcios/"+ idConsorcio.toString() +"/municipios";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray array = response.getJSONArray("municipios");
                    JSONObject mun= null;
                    SQLiteDatabase db = getWritableDatabase();

                    if (db != null) {
                        if (array.length() > 0)
                        {
                            for (int i = 0; i < array.length(); i++) {
                                mun = array.getJSONObject(i);
                                int id = mun.getInt("idMunicipio");
                                String datos = mun.getString("datos");
                                if(id!=0) {
                                    ContentValues valores = new ContentValues();
                                    valores.put(MUNICIPIO_ID,id);
                                    valores.put(CONSORCIOS_ID, idConsorcio);
                                    valores.put(MUNICIPIO_DATOS, datos);
                                    db.insert(MUNICIPIO_TABLE, null, valores);
                                }
                            }
                            sem.release();
                            Log.d("BBDD","Tabla Municipios rellena");
                        }
                        db.close();
                    }
                }
                catch (JSONException e){
                    Log.d("BBDD", e.toString());
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                rellenar_municipios(idConsorcio, sem);
                Log.d("BBDD", error.toString());
            }
        });
        // add the request object to the queue to be executed
        PruebaTPAApplication.getInstance().getRequestQueue().add(request);
    }

    private void rellenar_nucleos(final Integer idConsorcio, final Semaphore sem){

        final String URL = "http://api.ctan.es/v1/Consorcios/"+ idConsorcio.toString() +"/nucleos";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int id_mun;
                    JSONArray array = response.getJSONArray("nucleos");
                    JSONObject nuc= null;
                    SQLiteDatabase db = getWritableDatabase();

                    if (db != null) {
                        if (array.length() > 0)
                        {
                            for (int i = 0; i < array.length(); i++) {

                                nuc = array.getJSONObject(i);
                                int id_nucleo = nuc.getInt("idNucleo");
                                if(nuc.getString("idMunicipio") == "null")
                                   id_mun = 0;
                                else
                                    id_mun = nuc.getInt("idMunicipio");
                                String nombre = nuc.getString("nombre");
                                if(id_nucleo !=0 && id_mun != 0) {
                                    ContentValues valores = new ContentValues();
                                    valores.put(NUCLEO_ID, id_nucleo);
                                    valores.put(MUNICIPIO_ID,id_mun);
                                    valores.put(CONSORCIOS_ID, idConsorcio);
                                    valores.put(NUCLEO_NOMBRE, nombre);
                                    db.insert(NUCLEO_TABLE, null, valores);
                                }
                            }
                            sem.release();
                            Log.d("BBDD","Tabla Nucleos rellena");
                        }

                        db.close();
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
                rellenar_nucleos(idConsorcio, sem);
            }
        });
        // add the request object to the queue to be executed
        PruebaTPAApplication.getInstance().getRequestQueue().add(request);
    }

    private void rellenar_paradas(final Integer idConsorcio, final Semaphore sem) {

        final String URL = "http://api.ctan.es/v1/Consorcios/"+ idConsorcio.toString() +"/paradas";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    double latitud;
                    double longitud;
                    JSONArray array = response.getJSONArray("paradas");
                    JSONObject par= null;
                    SQLiteDatabase db = getWritableDatabase();

                    if (db != null) {
                        if (array.length() > 0)
                        {
                            for (int i = 0; i < array.length(); i++) {

                                par = array.getJSONObject(i);
                                int id_par = par.getInt("idParada");
                                int id_mun = par.getInt("idMunicipio");
                                int id_nuc = par.getInt("idNucleo");
                                String nombre = par.getString("nombre");
                                if(isDouble(par.getString("latitud"))) {
                                    latitud = par.getDouble("latitud");
                                }
                                else {
                                    latitud = 0;
                                    id_par = 0;
                                }
                                if(isDouble(par.getString("longitud")))
                                {
                                    longitud = par.getDouble("longitud");
                                }else {

                                    longitud = 0;
                                    id_par = 0;
                                }
                                if(id_par !=0) {
                                    ContentValues valores = new ContentValues();
                                    valores.put(PARADA_ID,id_par);
                                    valores.put(MUNICIPIO_ID,id_mun);
                                    valores.put(CONSORCIOS_ID, idConsorcio);
                                    valores.put(PARADA_NOMBRE, nombre);
                                    valores.put(PARADA_LAT, latitud);
                                    valores.put(PARADA_LONG, longitud);
                                    valores.put(NUCLEO_ID, id_nuc);
                                    db.insert(PARADA_TABLE, null, valores);
                                }
                            }
                            sem.release();
                            Log.d("BBDD","Tabla Paradas rellena");
                        }

                        db.close();
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
                rellenar_paradas(idConsorcio, sem);
            }
        });
        // add the request object to the queue to be executed
        PruebaTPAApplication.getInstance().getRequestQueue().add(request);
    }

    private void rellenar_modos(final Integer idConsorcio, final Semaphore sem){

        final String URL = "http://api.ctan.es/v1/Consorcios/"+ idConsorcio.toString() +"/modostransporte/?lang=ES";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray array = response.getJSONArray("modosTransporte");
                    JSONObject trans= null;
                    SQLiteDatabase db = getWritableDatabase();

                    if (db != null) {
                        if (array.length() > 0)
                        {
                            for (int i = 0; i < array.length(); i++) {
                                trans = array.getJSONObject(i);
                                int id_modo = trans.getInt("idModo");
                                String desc = trans.getString("descripcion");
                                if(id_modo !=0) {
                                    ContentValues valores = new ContentValues();
                                    valores.put(MODO_ID,id_modo);
                                    valores.put(CONSORCIOS_ID, idConsorcio);
                                    valores.put(MODO_DESC, desc);
                                    db.insert(MODO_TABLE, null, valores);
                                }
                        }
                            sem.release();
                            Log.d("BBDD","Tabla Modos rellena");
                        }
                        db.close();
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
                rellenar_modos(idConsorcio, sem);
            }
        });
        // add the request object to the queue to be executed
        PruebaTPAApplication.getInstance().getRequestQueue().add(request);
    }

    private void rellenar_frecuencias(final Integer idConsorcio, final Semaphore sem){

        final String URL = "http://api.ctan.es/v1/Consorcios/"+ idConsorcio.toString() +"/frecuencias";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray array = response.getJSONArray("frecuencias");
                    JSONObject frec= null;
                    SQLiteDatabase db = getWritableDatabase();

                    if (db != null) {
                        if (array.length() > 0)
                        {
                            for (int i = 0; i < array.length(); i++) {
                                frec = array.getJSONObject(i);
                                int id_frec = frec.getInt("idFreq");
                                String codigo = frec.getString("codigo");
                                String nombre =  frec.getString("nombre");
                                if(id_frec !=0) {
                                    ContentValues valores = new ContentValues();
                                    valores.put(FRECUENCIA_ID,id_frec);
                                    valores.put(CONSORCIOS_ID, idConsorcio);
                                    valores.put(FRECUENCIA_COD, codigo);
                                    valores.put(FRECUENCIA_NOMBRE, nombre);
                                    db.insert(FRECUENCIA_TABLE, null, valores);
                                }
                            }
                            sem.release();
                            Log.d("BBDD","Tabla Frecuencias rellena");
                        }
                        db.close();
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
                rellenar_frecuencias(idConsorcio, sem);
            }
        });
        // add the request object to the queue to be executed
        PruebaTPAApplication.getInstance().getRequestQueue().add(request);
    }

    private void rellenar_lineas(final Integer idConsorcio, final Semaphore sem){

        final String URL = "http://api.ctan.es/v1/Consorcios/"+ idConsorcio.toString() +"/lineas";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray array = response.getJSONArray("lineas");
                    JSONObject lineas= null;
                    SQLiteDatabase db = getWritableDatabase();

                    if (db != null) {
                        if (array.length() > 0)
                        {
                            for (int i = 0; i < array.length(); i++) {
                                lineas = array.getJSONObject(i);
                                int id_linea = lineas.getInt("idLinea");
                                int id_modo = lineas.getInt("idModo");
                                String cod = lineas.getString("codigo");
                                String nombre = lineas.getString("nombre");
                                String opera = lineas.getString("operadores");
                                if(id_linea !=0) {
                                    ContentValues valores = new ContentValues();
                                    valores.put(LINEA_ID,id_linea);
                                    valores.put(MODO_ID, id_modo);
                                    valores.put(CONSORCIOS_ID, idConsorcio);
                                    valores.put(LINEA_NOMBRE, nombre);
                                    valores.put(LINEA_CODIGO, cod);
                                    valores.put(LINEA_OPERA, opera);
                                    db.insert(LINEA_TABLE, null, valores);
                                }
                            }
                            sem.release();
                            Log.d("BBDD","Tabla Lineas rellena");
                        }

                        db.close();
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
                rellenar_lineas(idConsorcio, sem);
            }
        });
        // add the request object to the queue to be executed
        PruebaTPAApplication.getInstance().getRequestQueue().add(request);
    }

    private void  borrarBBDD() {
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS " +LINEA_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " +FRECUENCIA_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " +MODO_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " +PARADA_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " +NUCLEO_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " +MUNICIPIO_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " +CONSORCIOS_TABLE);

        db.close();
    }
        public void borradoTotal() {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("DROP TABLE IF EXISTS " +ACT_TABLE);
            db.close();

            borrarBBDD();
        }

    public ArrayList<Consorcio> getConsorcios() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Consorcio> lista_consorcios = new ArrayList<Consorcio>();
        String[] valores_recuperar = {CONSORCIOS_ID, CONSORCIOS_NOMBRE, CONSORCIOS_NOMBRECORTO};
        Cursor c = db.query(CONSORCIOS_TABLE, valores_recuperar, null, null, null, null, null, null);
        if(c.getCount() != 0) {
            c.moveToFirst();
            do {
                Consorcio cons = new Consorcio(c.getInt(0), c.getString(1), c.getString(2));
                lista_consorcios.add(cons);
            } while (c.moveToNext());
        }
        db.close();
        c.close();
        return lista_consorcios;
    }

    public ArrayList<Municipio> getMunicipiosByConsorcio(Integer idConsorcio){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Municipio> lista_mun = new ArrayList<Municipio>();
        String[] valores_recuperar = {MUNICIPIO_ID, MUNICIPIO_DATOS};
        String[] valores_where = {idConsorcio.toString()};
        Cursor c = db.query(MUNICIPIO_TABLE, valores_recuperar, CONSORCIOS_ID +"=?", valores_where, null, null, MUNICIPIO_ID, null);
        if(c.getCount() != 0) {
            c.moveToFirst();
            do {
                Municipio mun = new Municipio(c.getInt(0), idConsorcio, c.getString(1));
                lista_mun.add(mun);
            } while (c.moveToNext());
        }
        db.close();
        c.close();
        return lista_mun;
    }

    public ArrayList<Parada> getParadasByMunicipio(Integer idConsorcio, Integer idMunicipio){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Parada> lista_paradas = new ArrayList<Parada>();
        String[] valores_recuperar = {PARADA_ID, NUCLEO_ID, PARADA_NOMBRE, PARADA_LAT, PARADA_LONG};
        String[] valores_where = {idConsorcio.toString(), idMunicipio.toString()};
        Cursor c = db.query(PARADA_TABLE, valores_recuperar, CONSORCIOS_ID +"=? AND "+ MUNICIPIO_ID+"=?", valores_where, null, null, null, null);
        if(c.getCount() != 0) {
            c.moveToFirst();
            do {
                Parada parada = new Parada(c.getInt(0),idMunicipio, c.getInt(1), idConsorcio, c.getString(2), c.getDouble(3), c.getDouble(4));
                lista_paradas.add(parada);
            } while (c.moveToNext());
        }
        db.close();
        c.close();
        return lista_paradas;
    }

    public ArrayList<ModoTrans> getModosTransporte(Integer idConsorcio) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<ModoTrans> lista_modos = new ArrayList<ModoTrans>();
        String[] valores_recuperar = {MODO_ID, MODO_DESC};
        String[] valores_where = {idConsorcio.toString()};
        Cursor c = db.query(MODO_TABLE, valores_recuperar, CONSORCIOS_ID +"=?", valores_where, null, null, MODO_ID, null);
        if(c.getCount() != 0) {
            c.moveToFirst();
            do {
                ModoTrans modo = new ModoTrans(c.getInt(0), idConsorcio, c.getString(1));
                lista_modos.add(modo);
            } while (c.moveToNext());
        }
        db.close();
        c.close();
        return lista_modos;
    }

    public ArrayList<Linea> getLineasByConsorcio(Integer idConsorcio){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Linea> lista_lineas = new ArrayList<Linea>();
        String[] valores_recuperar = {LINEA_ID, LINEA_CODIGO, LINEA_NOMBRE, LINEA_OPERA};
        String[] valores_where = {idConsorcio.toString()};
        Cursor c = db.query(LINEA_TABLE, valores_recuperar, CONSORCIOS_ID +"=?", valores_where, null, null, MODO_ID, null);
        if(c.getCount() != 0) {
            c.moveToFirst();
            do {
                Linea linea = new Linea(c.getInt(0), idConsorcio, c.getString(1), c.getString(2), c.getString(3));
                lista_lineas.add(linea);
            } while (c.moveToNext());
        }
        db.close();
        c.close();
        return lista_lineas;
    }

    public ArrayList<Nucleo> getNucleosByMunicipio(Integer idConsorcio, Integer idMunicipio){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Nucleo> lista_nucleos = new ArrayList<Nucleo>();
        String[] valores_recuperar = {NUCLEO_ID, NUCLEO_NOMBRE};
        String[] valores_where = {idConsorcio.toString(), idMunicipio.toString()};
        Cursor c = db.query(NUCLEO_TABLE, valores_recuperar, CONSORCIOS_ID+"=? and "+MUNICIPIO_ID+"=?", valores_where, null, null, null, null);
        if(c.getCount() != 0) {
            c.moveToFirst();
            do {
                Nucleo nucleo = new Nucleo(c.getInt(0), idMunicipio, idConsorcio, c.getString(1));
                lista_nucleos.add(nucleo);
            } while (c.moveToNext());
        }
        db.close();
        c.close();
        return lista_nucleos;
    }

    public ArrayList<Linea> getLineasByModo(Integer idConsorcio, Integer idModo){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Linea> lista_lineas = new ArrayList<Linea>();
        String[] valores_recuperar = {LINEA_ID, LINEA_CODIGO, LINEA_NOMBRE, LINEA_OPERA};
        String[] valores_where = {idConsorcio.toString(), idModo.toString()};
        Cursor c = db.query(LINEA_TABLE, valores_recuperar, CONSORCIOS_ID+"=? and "+MODO_ID+"=?", valores_where, null, null, null, null);
        if(c.getCount() != 0) {
            c.moveToFirst();
            do {
                Linea linea = new Linea(c.getInt(0), idConsorcio, c.getString(1), c.getString(2), c.getString(3));
                lista_lineas.add(linea);
            } while (c.moveToNext());
        }
        db.close();
        c.close();
        return lista_lineas;
    }

    public ArrayList<Linea> getLineasByCodigo(Integer idConsorcio, String codigo){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Linea> lista_lineas = new ArrayList<Linea>();
        String[] valores_recuperar = {LINEA_ID, LINEA_CODIGO, LINEA_NOMBRE, LINEA_OPERA};
        String[] valores_where = {idConsorcio.toString(), codigo};
        Cursor c = db.query(LINEA_TABLE, valores_recuperar, CONSORCIOS_ID+"=? and "+LINEA_CODIGO+"=?", valores_where, null, null, MODO_ID, null);
        if(c.getCount() != 0) {
            c.moveToFirst();
            do {
                Linea linea = new Linea(c.getInt(0), idConsorcio, c.getString(1), c.getString(2), c.getString(3));
                lista_lineas.add(linea);
            } while (c.moveToNext());
        }
        db.close();
        c.close();
        return lista_lineas;
    }

}
