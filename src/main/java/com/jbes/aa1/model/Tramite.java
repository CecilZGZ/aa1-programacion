package com.jbes.aa1.model;

import java.time.LocalDate;

public class Tramite {
    private String tipo; //Obligatorio
    private String veterinaria;
    private float coste;
    private boolean tieneCita; //Obligatorio
    private LocalDate fechaCita;

    public Tramite(String tipo, boolean tieneCita) {
        this.tipo = tipo;
        this.tieneCita = tieneCita;
    }

    public Tramite(String tipo, String veterinaria, float coste, boolean tieneCita, LocalDate fechaCita) {
        this.tipo = tipo;
        this.veterinaria = veterinaria;
        this.coste = coste;
        this.tieneCita = tieneCita;
        this.fechaCita = fechaCita;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getVeterinaria() {
        return veterinaria;
    }

    public void setVeterinaria(String veterinaria) {
        this.veterinaria = veterinaria;
    }

    public float getCoste() {
        return coste;
    }

    public void setCoste(float coste) {
        this.coste = coste;
    }

    public boolean isTieneCita() {
        return tieneCita;
    }

    public void setTieneCita(boolean tieneCita) {
        this.tieneCita = tieneCita;
    }

    public LocalDate getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(LocalDate fechaCita) {
        this.fechaCita = fechaCita;
    }
}
