package com.covidata.covidata;

public class Articulo {

    String titulo;
    String autor;
    String url;
    int imagen;

    public Articulo(String titulo, String autor, String url, int imagen) {
        this.titulo = titulo;
        this.autor = autor;
        this.url = url;
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}
