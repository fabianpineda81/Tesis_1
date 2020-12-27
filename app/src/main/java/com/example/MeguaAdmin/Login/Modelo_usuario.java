package com.example.MeguaAdmin.Login;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.example.MeguaAdmin.MainActivity;
import com.example.MeguaAdmin.herramientas.My_aplicacion;
import com.example.MeguaAdmin.plantas.Modelo_planta;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Modelo_usuario {
    private  String id;
    private String username;
    private String correo;
    private String contra;
    private ArrayList<String> plantas_favoritas;
    private ArrayList<String >plantas_vistas;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mydbref = database.getReference();

    public Modelo_usuario() {
    }

    public void montar_firebase(final Activity activity){
         database = FirebaseDatabase.getInstance();
         mydbref = database.getReference();
         //this.id =mydbref.push().getKey();

        mydbref.child("usuarios").child(this.id).setValue(this).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if(activity!=null){
                    Log.e("MODELO_USUARIO","USUARIO MONTADO CON EXITO");
                    ir_home(activity);
                }
            }
        });

    }
    public Modelo_usuario(String id , String username, String correo, String contra) {
        this.id= id ;
        this.username = username;
        this.correo = correo;
        this.contra = contra;
    }

    public void agregar_planta_favorita(String id_planta){
            if(plantas_favoritas==null){
                plantas_favoritas=new ArrayList<>();
            }
            plantas_favoritas.add(id_planta);
      actualizar_plantas_favoritas_firebase();


    }

    public void eliminar_planta_favorita(String id_planta){
        if(plantas_favoritas==null){
            plantas_favoritas=new ArrayList<>();
        }
        plantas_favoritas.add(id_planta);

       for( int i=0 ; i<plantas_favoritas.size();i++){
           String id= plantas_favoritas.get(i);
           if(id.equalsIgnoreCase(id_planta)){
               plantas_favoritas.remove(i);
               i--;
           }
       }


        actualizar_plantas_favoritas_firebase();

    }

    private void actualizar_plantas_favoritas_firebase(){
        Map<String,Object> modificacion = new HashMap<>();
        modificacion.put("plantas_favoritas",plantas_favoritas);
        mydbref.child("usuarios").child(this.id).updateChildren(modificacion);
    }
    private void actualizar_plantas_visitadas_firebase(){
        Map<String,Object> modificacion = new HashMap<>();
        modificacion.put("plantas_vistas",plantas_vistas);
        mydbref.child("usuarios").child(this.id).updateChildren(modificacion);
    }

    public  Boolean is_favorita(String id_planta){
    Boolean resulado= false ;
    if(plantas_favoritas!=null){
        for (String id :plantas_favoritas ) {
            if(id.equalsIgnoreCase(id_planta)){
                resulado=true ;
                break;
            }
        }
        Log.e("IS FAVORITE",""+resulado);
    }

    return resulado;
    };

    private void ir_home(Activity activity) {
        Intent i = new Intent(activity, MainActivity.class);
        activity.startActivity(i);
        Log.e("IR HOME","MODELO USUARIO");
    }

    public void agregar_planta_buscada(String id ){

        if(plantas_vistas== null){
            plantas_vistas= new ArrayList<>();
        }
        // 4 son el maximo de plantas ultimamente visitadas
        if(plantas_vistas.size()>=4){
            plantas_vistas.remove(3);
        }
        plantas_vistas.add(0,id);
        actualizar_plantas_visitadas_firebase();
        Log.e("PLANTA VISITADA","PLATAN VISITADA "+id);


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public ArrayList<String> getPlantas_favoritas() {
        return plantas_favoritas;
    }

    public void setPlantas_favoritas(ArrayList<String> plantas_favoritas) {
        this.plantas_favoritas = plantas_favoritas;
    }

    public ArrayList<String> getPlantas_vistas() {
        return plantas_vistas;
    }

    public void setPlantas_vistas(ArrayList<String> plantas_vistas) {
        this.plantas_vistas = plantas_vistas;
    }

    public ArrayList<Modelo_planta> obtener_plantas_favoritas(My_aplicacion my_aplicacion) {
    ArrayList<Modelo_planta> resultado = new ArrayList<>();
    ArrayList<Modelo_planta> plantas= my_aplicacion.getPlantas();
    if(plantas_favoritas!=null){
        for (String id : plantas_favoritas){
            for (Modelo_planta planta:plantas){
                if(id.equalsIgnoreCase(planta.getNombre_cientifico())){
                    resultado.add(planta);
                }
            }
        }
    }

    return  resultado;
    }

    public  ArrayList<Modelo_planta> obtener_plantas_visitadas(My_aplicacion my_aplicacion){
        ArrayList<Modelo_planta> resultado = new ArrayList<>();
        ArrayList<Modelo_planta> plantas= my_aplicacion.getPlantas();

    if(plantas_vistas!=null){
        for (String id : plantas_vistas){
            for (Modelo_planta planta:plantas){
                if(id.equalsIgnoreCase(planta.getNombre_cientifico())){
                    resultado.add(planta);
                }
            }
        }
    }

        return  resultado;
    }
}



