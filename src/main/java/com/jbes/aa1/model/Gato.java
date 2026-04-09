package com.jbes.aa1.model;


import java.time.LocalDate;

public class Gato {
    private String nombre; //Obligatorio
    private int chip; //Obligatorio
    private float peso;
    private boolean vacunado;
    private LocalDate fechaNacimiento;

    public Gato(String nombre, int chip) {
        this.nombre = nombre;
        this.chip = chip;
    }

    public Gato(String nombre, int chip, float peso, boolean vacunado, LocalDate fechaNacimiento) {
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

    public boolean isVacunado() {
        return vacunado;
    }

    public void setVacunado(boolean vacunado) {
        this.vacunado = vacunado;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}


