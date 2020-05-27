package com.covidata.covidata;

public class DatoGlobal {

    String fecha;
    int confirmados, fallecidos, recuperados;

    public DatoGlobal(String fecha, int confirmados, int fallecidos, int recuperados) {
        this.fecha = fecha;
        this.confirmados = confirmados;
        this.fallecidos = fallecidos;
        this.recuperados = recuperados;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getConfirmados() {
        return confirmados;
    }

    public void setConfirmados(int confirmados) {
        this.confirmados = confirmados;
    }

    public int getFallecidos() {
        return fallecidos;
    }

    public void setFallecidos(int fallecidos) {
        this.fallecidos = fallecidos;
    }

    public int getRecuperados() {
        return recuperados;
    }

    public void setRecuperados(int recuperados) {
        this.recuperados = recuperados;
    }

}
