package com.example.MeguaAdmin.herramientas;

import android.net.Uri;

public class Modelo_uri {
    private boolean is_foto;
    private Uri uri;
    private  String nombre;

    public Modelo_uri(boolean is_foto, Uri uri) {
        this.is_foto = is_foto;
        this.uri = uri;
    }

    public Modelo_uri(boolean is_foto, Uri uri, String nombre) {
        this.is_foto = is_foto;
        this.uri = uri;
        this.nombre = nombre;
    }

    public void setIs_foto(boolean is_foto) {
        this.is_foto = is_foto;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public  boolean is_foto(){
        return  this.is_foto;
    }



    public Uri getUri() {
        return uri;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

