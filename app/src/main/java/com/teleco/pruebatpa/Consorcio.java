package com.teleco.pruebatpa;

public class Consorcio {
    private Integer idConsorcio;
    private String nombre;
    private String nombreCorto;
    public Consorcio(Integer idCons, String nom, String nomCorto){
        idConsorcio = idCons;
        nombre = nom;
        nombreCorto = nomCorto;
    }

    public Integer getIdConsorcio() {
        return idConsorcio;
    }

    public String getNombre(){
        return nombre;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }
}

