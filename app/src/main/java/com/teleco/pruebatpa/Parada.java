package com.teleco.pruebatpa;

public class Parada {
    private Integer idParada;
    private Integer idMunicipio;
    private Integer idConsorcio;
    private String nombre;
    private double latitud;
    private double longitud;

    public Parada (Integer idP, Integer idM,Integer idC, String nom, double lat, double longi){
        idParada = idP;
        idMunicipio = idM;
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
