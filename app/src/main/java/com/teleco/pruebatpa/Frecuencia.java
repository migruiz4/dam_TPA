package com.teleco.pruebatpa;

public class Frecuencia {
    private Integer idFrecuencia;
    private Integer idConsorcio;
    private String codigo;
    private String nombre;

    public Frecuencia(Integer idF, Integer idC, String c, String nom){
        idFrecuencia = idF;
        idConsorcio = idC;
        codigo = c;
        nombre = nom;
    }

    public Integer getIdFrecuencia() {
        return idFrecuencia;
    }

    public Integer getIdConsorcio() {
        return idConsorcio;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }
}
