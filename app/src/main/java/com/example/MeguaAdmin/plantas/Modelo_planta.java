package com.example.MeguaAdmin.plantas;


import android.app.Activity;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.MeguaAdmin.Home.Container_Home;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Modelo_planta implements Parcelable  {
    private String id;
    private String link_imagen_modelo;
    private String link_imagen_muestra_1;
    private String link_imagen_muestra_2;
    private String link_imagen_muestra_3;
    private String link_imagen_muestra_4;
    private String nombre,nombre_cientifico,descripcion,datos_interes,familia,genero,tipo_planta,altura,diametro_copa,diametro_flor,floracion,epoca;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();


    public Modelo_planta() {
    }

    public Modelo_planta( String nombre, String nombre_cientifico, String descripcion, String datos_interes, String familia, String genero, String tipo_planta, String altura, String diametro_copa, String diametro_flor, String floracion, String epoca) {
        this.nombre = nombre;
        this.nombre_cientifico = nombre_cientifico;
        this.descripcion = descripcion;
        this.datos_interes = datos_interes;
        this.familia = familia;
        this.genero= genero;
        this.tipo_planta = tipo_planta;
        this.altura = altura;
        this.diametro_copa = diametro_copa;
        this.diametro_flor = diametro_flor;
        this.floracion = floracion;
        this.epoca = epoca;
    }


    protected Modelo_planta(Parcel in) {
        id=in.readString();
        link_imagen_modelo = in.readString();
        link_imagen_muestra_1 = in.readString();
        link_imagen_muestra_2 = in.readString();
        link_imagen_muestra_3 = in.readString();
        link_imagen_muestra_4 = in.readString();
        nombre = in.readString();
        nombre_cientifico = in.readString();
        descripcion = in.readString();
        datos_interes = in.readString();
        familia = in.readString();
        genero = in.readString();
        tipo_planta = in.readString();
        altura = in.readString();
        diametro_copa = in.readString();
        diametro_flor = in.readString();
        floracion = in.readString();
        epoca = in.readString();

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

    public void montar_firebase(final Activity activity){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        this.id= myRef.push().getKey();
         myRef.child("plantas").child(this.id).setValue(this).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 e.printStackTrace();
             }
         }).addOnCompleteListener(new OnCompleteListener<Void>() {
             @Override
             public void onComplete(@NonNull Task<Void> task) {
                 ir_home(activity);
             }
         });




    }

    private void ir_home(Activity activity) {
        Intent i = new Intent(activity, Container_Home.class);
        activity.startActivity(i);
    }

    public  void eliminar_firebase(String id){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child("plantas").child(id).removeValue();
    }
    public  void eliminar_firebase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child("plantas").child(this.id).removeValue();
    }

    public void modificar_plant(Modelo_planta planta_nueva){


    Map<String,Object> modificacion= new HashMap<>();
    modificacion.put("nombre",planta_nueva.getNombre());
    modificacion.put("nombre_cientifico",planta_nueva.getNombre_cientifico());
    modificacion.put("tipo_planta",planta_nueva.getTipo_planta());
    modificacion.put("altura",planta_nueva.getAltura());
    modificacion.put("datos_interes",planta_nueva.getDatos_interes());
    modificacion.put("descripcion",planta_nueva.getDescripcion());
    modificacion.put("diametro_copa",planta_nueva.getDiametro_copa());
    modificacion.put("diametro_flor",planta_nueva.getDiametro_flor());
    modificacion.put("epoca",planta_nueva.getEpoca());
    modificacion.put("familia",planta_nueva.getFamilia());
    modificacion.put("floracion",planta_nueva.getFloracion());
    modificacion.put("genero",planta_nueva.getGenero());
    modificacion.put("link_imagen_modelo",planta_nueva.getLink_imagen_modelo());
    modificacion.put("link_imagen_muestra_1",planta_nueva.getLink_imagen_muestra_1());
    modificacion.put("link_imagen_muestra_2",planta_nueva.getLink_imagen_muestra_2());
    modificacion.put("link_imagen_muestra_3",planta_nueva.getLink_imagen_muestra_3());
    modificacion.put("link_imagen_muestra_4",planta_nueva.getLink_imagen_muestra_4());
  myRef.child("plantas").child(this.id).updateChildren(modificacion);
  Log.e("MODIFICAR PLANTA","ID  "+id);
    String nombre_cientifico1=String.valueOf(planta_nueva.getNombre_cientifico());
    String nombre_cientifico2=String.valueOf(this.getNombre_cientifico());


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

    public String getLink_imagen_muestra_4() {
        return link_imagen_muestra_4;
    }

    public void setLink_imagen_muestra_4(String link_imagen_muestra_4) {
        this.link_imagen_muestra_4 = link_imagen_muestra_4;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDatos_interes() {
        return datos_interes;
    }

    public void setDatos_interes(String datos_interes) {
        this.datos_interes = datos_interes;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getTipo_planta() {
        return tipo_planta;
    }

    public void setTipo_planta(String tipo_planta) {
        this.tipo_planta = tipo_planta;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getDiametro_copa() {
        return diametro_copa;
    }

    public void setDiametro_copa(String diametro_copa) {
        this.diametro_copa = diametro_copa;
    }

    public String getDiametro_flor() {
        return diametro_flor;
    }

    public void setDiametro_flor(String diametro_flor) {
        this.diametro_flor = diametro_flor;
    }

    public String getFloracion() {
        return floracion;
    }

    public void setFloracion(String floracion) {
        this.floracion = floracion;
    }

    public String getEpoca() {
        return epoca;
    }

    public void setEpoca(String epoca) {
        this.epoca = epoca;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(link_imagen_modelo);
        parcel.writeString(link_imagen_muestra_1);
        parcel.writeString(link_imagen_muestra_2);
        parcel.writeString(link_imagen_muestra_3);
        parcel.writeString(link_imagen_muestra_4);
        parcel.writeString(nombre);
        parcel.writeString(nombre_cientifico);
        parcel.writeString(descripcion);
        parcel.writeString(datos_interes);
        parcel.writeString(familia);
        parcel.writeString(genero);
        parcel.writeString(tipo_planta);
        parcel.writeString(altura);
        parcel.writeString(diametro_copa);
        parcel.writeString(diametro_flor);
        parcel.writeString(floracion);
        parcel.writeString(epoca);
    }
}
