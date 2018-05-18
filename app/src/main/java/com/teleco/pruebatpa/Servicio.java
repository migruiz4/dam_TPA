package com.teleco.pruebatpa;

public class Servicio {
    private Integer idParada;
    private Integer idMunicipio;
    private Integer idConsorcio;
    private Integer idLinea;
    private String hora;
    private String codigo;
    private String nombre;
    private Integer sentido;
    private String destino;
    private Integer tipo;

    public Servicio (Integer idP, Integer idM, Integer idC, Integer idL, String h, String nom, String cod, Integer sent, String dest, Integer t){
        idParada = idP;
        idMunicipio = idM;
        idConsorcio = idC;
        idLinea = idL;
        nombre = nom;
        hora = h;
        codigo = cod;
        sentido = sent;
        destino = dest;
        tipo = t;
    }

    public Integer getIdConsorcio() {
        return idConsorcio;
    }

    public Integer getIdMunicipio() {
        return idMunicipio;
    }

    public Integer getIdParada() {
        return idParada;
    }

    public Integer getIdLinea() {
        return idLinea;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public Integer getSentido() {
        return sentido;
    }

    public String getHora() {
        return hora;
    }

    public String getDestino() {
        return destino;
    }

    public Integer getTipo() {
        return tipo;
    }

}
