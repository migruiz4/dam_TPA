package com.teleco.pruebatpa;

import java.util.ArrayList;

public class InfoHorarioLinea {

    private String tipo_horario;
    private String observaciones;
    private ArrayList<String> informacion_linea;
    private String frecuencia;

    public String getTipo_horario() {
        return tipo_horario;
    }

    public void setTipo_horario(String tipo_horario) {
        this.tipo_horario = tipo_horario;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public ArrayList<String> getInformacion_linea() {
        return informacion_linea;
    }

    public void setInformacion_linea(ArrayList<String> informacion_linea) {
        this.informacion_linea = informacion_linea;
    }
}
