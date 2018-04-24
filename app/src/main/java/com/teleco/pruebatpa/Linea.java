package com.teleco.pruebatpa;

public class Linea {
    private Integer idLinea;
    private Integer idConsorcio;
    private String codigo;
    private String nombre;
    private String operador;

    public Linea (Integer idL, Integer idC, String c, String nom, String op){
        idLinea = idL;
        idConsorcio = idC;
        codigo = c;
        nombre = nom;
        operador = op;
    }

    public Integer getIdLinea() {
        return idLinea;
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

    public String getOperador() {
        return operador;
    }
}
