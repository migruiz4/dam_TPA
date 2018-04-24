package com.teleco.pruebatpa;

public class Nucleo {
    private Integer idNucleo;
    private Integer idConsorcio;
    private Integer idMunicipio;
    private String nombre;

    public Nucleo(Integer idN, Integer idM, Integer idC, String nom){
        idNucleo = idN;
        idConsorcio = idC;
        idMunicipio = idM;
        nombre = nom;
    }

    public Integer getIdNucleo() {
        return idNucleo;
    }

    public Integer getIdMunicipio() {
        return idMunicipio;
    }

    public Integer getIdConsorcio() {
        return idConsorcio;
    }

    public String getNombre() {
        return nombre;
    }
}
