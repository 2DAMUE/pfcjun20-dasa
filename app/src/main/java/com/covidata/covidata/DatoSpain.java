package com.covidata.covidata;

public class DatoSpain {

    String fecha;
    String confirmados;
    String fallecidos;
    String recuperados;

    public DatoSpain(String fecha, String confirmados, String fallecidos, String recuperados) {
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

    public String getConfirmados() {
        return confirmados;
    }

    public void setConfirmados(String confirmados) {
        this.confirmados = confirmados;
    }

    public String getFallecidos() {
        return fallecidos;
    }

    public void setFallecidos(String fallecidos) {
        this.fallecidos = fallecidos;
    }

    public String getRecuperados() {
        return recuperados;
    }

    public void setRecuperados(String recuperados) {
        this.recuperados = recuperados;
    }
}
