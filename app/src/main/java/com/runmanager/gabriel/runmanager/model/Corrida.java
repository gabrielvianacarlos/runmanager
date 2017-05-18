package com.runmanager.gabriel.runmanager.model;

import java.io.Serializable;

/**
 * Created by gabriel on 13/05/17.
 */

public class Corrida implements Serializable{
    private String distancia;
    private String dataProgramada;
    private boolean corridaFeita;
    private int id;

    public Corrida(String distancia, String dataProgramada, boolean corridaFeita) {
        this.distancia = distancia;
        this.dataProgramada = dataProgramada;
        this.corridaFeita = corridaFeita;
    }

    public String getDistancia() { return distancia; }

    public String getDataProgramada() {
        return dataProgramada;
    }

    public boolean isCorridaFeita() {
        return corridaFeita;
    }

    public void setCorridaFeita(boolean corridaFeita) {
        this.corridaFeita = corridaFeita;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public void setDataProgramada(String dataProgramada) {
        this.dataProgramada = dataProgramada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
