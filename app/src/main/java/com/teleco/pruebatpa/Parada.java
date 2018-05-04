package com.teleco.pruebatpa;

public class Parada {
    private Integer idParada;
    private Integer idMunicipio;
    private Integer idConsorcio;
    private Integer idNucleo;
    private String nombre;
    private double latitud;
    private double longitud;

    public Parada (Integer idP, Integer idM,Integer idN, Integer idC, String nom, double lat, double longi){
        idParada = idP;
        idMunicipio = idM;
        idNucleo = idN;
        idConsorcio = idC;
        nombre = nom;
        latitud = lat;
        longitud = longi;
    }

    public Integer getIdParada() {
        return idParada;
    }

    public Integer getIdMunicipio() {
        return idMunicipio;
    }

    public Integer getIdConsorcio() {
        return idConsorcio;
    }

    public Integer getIdNucleo() {
        return idNucleo;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public String getNombre() {
        return nombre;
    }


}
