package com.covidata.covidata;

public class Articulo {

    String nombre;
    int imagen;
    String pdf;

    public Articulo(String nombre, int imagen, String pdf) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.pdf = pdf;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }
}
