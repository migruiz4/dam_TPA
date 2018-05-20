package com.teleco.pruebatpa;

import java.util.ArrayList;

public class Horario {
    private Integer idLinea;
    private Integer idConsorcio;
    private Integer idModo;
    private Integer dia;
    private Integer mes;
    private ArrayList<String> nucleosIda;
    private ArrayList<String> nucleosVuelta;
    private ArrayList<String[]> horasIda;
    private ArrayList<String[]> horasVuelta;


    public Horario (Integer idC, Integer idM, Integer idL, Integer day, Integer month){
        idLinea = idL;
        idConsorcio = idC;
        idModo = idM;
        dia = day;
        mes = month;
    }

    public void setHorasIda(ArrayList<String[]> horasIda) {
        this.horasIda = horasIda;
    }

    public void setHorasVuelta(ArrayList<String[]> horasVuelta) {
        this.horasVuelta = horasVuelta;
    }

    public void setNucleosIda(ArrayList<String> nucleosIda) {
        this.nucleosIda = nucleosIda;
    }

    public void setNucleosVuelta(ArrayList<String> nucleosVuelta) {
        this.nucleosVuelta = nucleosVuelta;
    }

    public Integer getIdLinea() {
        return idLinea;
    }

    public Integer getIdConsorcio() {
        return idConsorcio;
    }

    public Integer getIdModo() {
        return idModo;
    }

    public ArrayList<String> getNucleosIda() {
        return nucleosIda;
    }

    public ArrayList<String> getNucleosVuelta() {
        return nucleosVuelta;
    }

    public ArrayList<String[]> getHorasIda() {
        return horasIda;
    }

    public ArrayList<String[]> getHorasVuelta() {
        return horasVuelta;
    }

    public Integer getDia() {
        return dia;
    }

    public Integer getMes() {
        return mes;
    }
}
