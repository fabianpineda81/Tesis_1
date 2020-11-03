package com.example.MeguaPlantsAdmin;



public class Modelo_planta {
    private String link_imagen_modelo;
    private String link_imagen_muestra_1;
    private String link_imagen_muestra_2;
    private String link_imagen_muestra_3;

    public Modelo_planta() {

    }

    private String nombre,nombre_cientifico,caracteristicas,descripcion ;

    public Modelo_planta(String nombre, String nombre_cientifico, String caracteristicas, String descripcion) {
        this.nombre = nombre;
        this.nombre_cientifico = nombre_cientifico;
        this.caracteristicas = caracteristicas;
        this.descripcion = descripcion;
    }

    public String getLink_imagen_modelo() {
        return link_imagen_modelo;
    }

    public void setLink_imagen_modelo(String link_imagen_modelo) {
        this.link_imagen_modelo = link_imagen_modelo;
    }

    public String getLink_imagen_muestra_1() {
        return link_imagen_muestra_1;
    }

    public void setLink_imagen_muestra_1(String link_imagen_muestra_1) {
        this.link_imagen_muestra_1 = link_imagen_muestra_1;
    }

    public String getLink_imagen_muestra_2() {
        return link_imagen_muestra_2;
    }

    public void setLink_imagen_muestra_2(String link_imagen_muestra_2) {
        this.link_imagen_muestra_2 = link_imagen_muestra_2;
    }

    public String getLink_imagen_muestra_3() {
        return link_imagen_muestra_3;
    }

    public void setLink_imagen_muestra_3(String link_imagen_muestra_3) {
        this.link_imagen_muestra_3 = link_imagen_muestra_3;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre_cientifico() {
        return nombre_cientifico;
    }

    public void setNombre_cientifico(String nombre_cientifico) {
        this.nombre_cientifico = nombre_cientifico;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
