package com.example.MeguaAdmin.herramientas;

import android.app.Application;
import android.util.Log;
import android.view.Display;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.MeguaAdmin.Login.Modelo_usuario;
import com.example.MeguaAdmin.MainActivity;
import com.example.MeguaAdmin.R;
import com.example.MeguaAdmin.plantas.Adater_recycler_plantas;
import com.example.MeguaAdmin.plantas.Modelo_planta;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class My_aplicacion extends Application {
    RecyclerView recyclerView;
     ArrayList<Modelo_planta> plantas= null;
    FirebaseUser user_firebase;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;
    Modelo_usuario usuario;
    ValueEventListener listaner;





    public  void inicializar (final MainActivity mainActivity){
        Log.e("My aplication","inicializar");
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mAuth= FirebaseAuth.getInstance();
        user_firebase =mAuth.getCurrentUser();

        if(user_firebase!=null){
            myRef.child("usuarios").child(user_firebase.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        usuario = snapshot.getValue(Modelo_usuario.class);
                        Log.e("MY APLICATION","ENCONTRADO ");
                        buscar_plantas(mainActivity);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else{
            Log.e("MAIN ACTIVITY","NO HAY USUARIO");
            mainActivity.verificar_usuario();
        }




    }

    public  void buscar_plantas(final MainActivity mainActivity){
        Log.e("MY PLAICATION","BUSCANDO PLANTAS");


          listaner  = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(plantas==null){
                    plantas=new ArrayList<>();
                    if(snapshot.exists()){


                        for(DataSnapshot snapshot_planta: snapshot.getChildren()){
                            Log.e("plantas firebase",snapshot_planta.toString());
                            Modelo_planta planta= snapshot_planta.getValue(Modelo_planta.class);
                            plantas.add(planta);



                       }



                    }
                    mainActivity.verificar_usuario();
                }


              //  myRef.child("plantas").addValueEventListener(listaner);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        myRef.child("plantas").addListenerForSingleValueEvent(listaner);

    }
    public  void  eliminar_lisener(){
        //myRef.child("plantas").removeEventListener(listaner);

    }

    public Modelo_planta buscar_planta(String nombre_cientifico){
        for(Modelo_planta planta:plantas){
            if(planta.getNombre_cientifico().equalsIgnoreCase(nombre_cientifico)){
                return planta;
            }
        }
        return  null ;
    }


    public void setPlantas(ArrayList<Modelo_planta> plantas) {
        this.plantas = plantas;
    }

    public ArrayList<Modelo_planta> getPlantas() {
        return plantas;
    }

    public Modelo_usuario getUsuario() {
        return usuario;
    }
}
