package com.example.MeguaAdmin.herramientas;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Modelo_codigo {
    String id ;
    Boolean habilidato ;
    String codigo;

    public Modelo_codigo(Boolean habilidato, String codigo) {
        this.habilidato = habilidato;
        this.codigo = codigo;
    }

    public Modelo_codigo() {
    }

    public Boolean isHabilidato() {
        return habilidato;
    }

    public void setHabilidato(Boolean habilidato) {
        this.habilidato = habilidato;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void subir_firebase() {
        FirebaseDatabase  database = FirebaseDatabase.getInstance();
        DatabaseReference mydbref = database.getReference();
        this.id = mydbref.child("codigos").push().getKey();
        mydbref.child("codigos").child(this.id).setValue(this);

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
