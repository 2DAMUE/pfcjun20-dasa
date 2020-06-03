package com.covidata.covidata;

public class DatoCCAA {

    String fecha;
    int confirmados,fallecidos,recuperados;
    int confirmadoDiario,fallecidosDiario,recuperadosDiario;

    public DatoCCAA(String fecha, int confirmados, int fallecidos, int recuperados, int confirmadoDiario, int fallecidosDiario, int recuperadosDiario) {
        this.fecha = fecha;
        this.confirmados = confirmados;
        this.fallecidos = fallecidos;
        this.recuperados = recuperados;
        this.confirmadoDiario = confirmadoDiario;
        this.fallecidosDiario = fallecidosDiario;
        this.recuperadosDiario = recuperadosDiario;
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

    public int getConfirmadoDiario() {
        return confirmadoDiario;
    }

    public void setConfirmadoDiario(int confirmadoDiario) {
        this.confirmadoDiario = confirmadoDiario;
    }

    public int getFallecidosDiario() {
        return fallecidosDiario;
    }

    public void setFallecidosDiario(int fallecidosDiario) {
        this.fallecidosDiario = fallecidosDiario;
    }

    public int getRecuperadosDiario() {
        return recuperadosDiario;
    }

    public void setRecuperadosDiario(int recuperadosDiario) {
        this.recuperadosDiario = recuperadosDiario;
    }
}
