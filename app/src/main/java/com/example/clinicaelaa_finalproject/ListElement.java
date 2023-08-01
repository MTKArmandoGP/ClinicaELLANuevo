package com.example.clinicaelaa_finalproject;

public class ListElement {

    public int idCita; // Agrega un campo para almacenar el ID de la cita
    public String Doctor;
    public String Hora;

    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public String Fecha;

    public ListElement(int idCita, String doctor, String hora, String fecha) {
        this.idCita = idCita;
        Doctor = doctor;
        Hora = hora;
        Fecha = fecha;
    }

    public String getDoctor() {
        return "Doctor(a): " +Doctor;
    }

    public void setDoctor(String doctor) {
        Doctor = doctor;
    }

    public String getHora() {
        return "Hora: " +Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }
}
