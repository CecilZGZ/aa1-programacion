package com.jbes.aa1.model;


import java.time.LocalDate;
import java.io.Serializable;

public class Gato implements Serializable {
    private String nombre; //Obligatorio
    private int chip; //Obligatorio
    private float peso;
    private Boolean vacunado;
    private LocalDate fechaNacimiento;

    public Gato(String nombre, int chip) {
        this.nombre = nombre;
        this.chip = chip;
    }

    public Gato(String nombre, int chip, float peso, Boolean vacunado, LocalDate fechaNacimiento) {
        this.nombre = nombre;
        this.chip = chip;
        this.peso = peso;
        this.vacunado = vacunado;
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getChip() {
        return chip;
    }

    public void setChip(int chip) {
        this.chip = chip;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public Boolean getVacunado() {
        return vacunado;
    }

    public void setVacunado(Boolean vacunado) {
        this.vacunado = vacunado;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}


