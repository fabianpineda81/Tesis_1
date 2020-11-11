package com.example.MeguaPlantsAdmin;


import android.os.Parcel;
import android.os.Parcelable;

public class Modelo_planta implements Parcelable {
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

    protected Modelo_planta(Parcel in) {
        link_imagen_modelo = in.readString();
        link_imagen_muestra_1 = in.readString();
        link_imagen_muestra_2 = in.readString();
        link_imagen_muestra_3 = in.readString();
        nombre = in.readString();
        nombre_cientifico = in.readString();
        caracteristicas = in.readString();
        descripcion = in.readString();
    }

    public static final Creator<Modelo_planta> CREATOR = new Creator<Modelo_planta>() {
        @Override
        public Modelo_planta createFromParcel(Parcel in) {
            return new Modelo_planta(in);
        }

        @Override
        public Modelo_planta[] newArray(int size) {
            return new Modelo_planta[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(link_imagen_modelo);
        parcel.writeString(link_imagen_muestra_1);
        parcel.writeString(link_imagen_muestra_2);
        parcel.writeString(link_imagen_muestra_3);
        parcel.writeString(nombre);
        parcel.writeString(nombre_cientifico);
        parcel.writeString(caracteristicas);
        parcel.writeString(descripcion);
    }
}
