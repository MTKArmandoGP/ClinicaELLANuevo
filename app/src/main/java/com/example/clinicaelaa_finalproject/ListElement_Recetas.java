package com.example.clinicaelaa_finalproject;

public class ListElement_Recetas {

    public String PacienteRecetas,FechaRecetas;
    private int idReceta;

    public ListElement_Recetas(int idReceta,String pacienteRecetas, String fechaRecetas) {
        PacienteRecetas = pacienteRecetas;
        this.idReceta = idReceta;
        FechaRecetas = fechaRecetas;
    }

    public int getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(int idReceta) {
        this.idReceta = idReceta;
    }

    public String getPacienteRecetas() {
        return PacienteRecetas;
    }

    public void setPacienteRecetas(String pacienteRecetas) {
        PacienteRecetas = pacienteRecetas;
    }

    public String getFechaRecetas() {
        return FechaRecetas;
    }

    public void setFechaRecetas(String fechaRecetas) {
        FechaRecetas = fechaRecetas;
    }
}
