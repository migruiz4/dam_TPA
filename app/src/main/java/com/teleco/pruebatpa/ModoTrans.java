package com.teleco.pruebatpa;

public class ModoTrans {
    private Integer idModo;
    private Integer idConsorcio;
    private String desc;

    public ModoTrans(Integer idM, Integer idC, String d)
    {
        idModo = idM;
        idConsorcio = idM;
        desc = d;
    }

    public Integer getIdConsorcio() {
        return idConsorcio;
    }

    public Integer getIdModo() {
        return idModo;
    }

    public String getDesc() {
        return desc;
    }
}
