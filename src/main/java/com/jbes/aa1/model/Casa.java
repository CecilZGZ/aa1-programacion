package com.jbes.aa1.model;

import java.time.LocalDate;

public class Casa {
    private String dueno; //Obligatorio
    private int numeroGatos;
    private float valoracion;
    private boolean huecoDisponible; //Obligatorio
    private LocalDate fechaInscripcion;


    public Casa(String dueño, boolean huecoDisponible) {
        this.dueno = dueño;
        this.huecoDisponible = huecoDisponible;
    }


    public Casa(String dueño, int numeroGatos, float valoracion, boolean huecoDisponible, LocalDate fechaInscripcion) {
        this.dueno = dueño;
        this.numeroGatos = numeroGatos;
        this.valoracion = valoracion;
        this.huecoDisponible = huecoDisponible;
        this.fechaInscripcion = fechaInscripcion;
    }

    public String getDueno() {
        return dueno;
    }

    public void setDueno(String dueno) {
        this.dueno = dueno;
    }

    public int getNumeroGatos() {
        return numeroGatos;
    }

    public void setNumeroGatos(int numeroGatos) {
        this.numeroGatos = numeroGatos;
    }

    public float getValoracion() {
        return valoracion;
    }

    public void setValoracion(float valoracion) {
        this.valoracion = valoracion;
    }

    public boolean isHuecoDisponible() {
        return huecoDisponible;
    }

    public void setHuecoDisponible(boolean huecoDisponible) {
        this.huecoDisponible = huecoDisponible;
    }

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDate fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }
}
