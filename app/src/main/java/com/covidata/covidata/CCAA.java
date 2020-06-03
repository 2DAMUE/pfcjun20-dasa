package com.covidata.covidata;

import java.io.Serializable;

public class CCAA implements Serializable {

    String nombre,iso;
    int imagen;

    public CCAA(String nombre, String iso, int imagen) {
        this.nombre = nombre;
        this.iso = iso;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}
