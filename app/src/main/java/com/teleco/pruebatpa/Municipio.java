package com.teleco.pruebatpa;

public class Municipio {
    private Integer idMunicipio;
    private Integer idConsorcio;
    private String datos;

    public Municipio(Integer idM, Integer idC, String d){
        idMunicipio = idM;
        idConsorcio = idC;
        datos = d;
    }

    public Integer getIdMunicipio() {
        return idMunicipio;
    }

    public Integer getIdConsorcio() {
        return idConsorcio;
    }

    public String getDatos() {
        return datos;
    }
}
