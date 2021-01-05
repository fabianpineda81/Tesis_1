package com.example.MeguaAdmin.herramientas;

import android.app.Application;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.MeguaAdmin.Home.Fragmentos.Codigos;
import com.example.MeguaAdmin.Login.Modelo_Login;
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
    ArrayList<Modelo_codigo> codigos;





    public  void inicializar (final MainActivity mainActivity){
        Log.e("My aplication","inicializar");
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mAuth= FirebaseAuth.getInstance();
        user_firebase =mAuth.getCurrentUser();
        buscar_codigos();

        if(user_firebase!=null){
            myRef.child("usuarios").child(user_firebase.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        usuario = snapshot.getValue(Modelo_usuario.class);
                        buscar_plantas(mainActivity);
                        Log.e("my aplicatios","usuario encontrado ");

                    }else {

                        Log.e("my aplicatios","sanapshot null ");
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



    private void buscar_codigos() {
            codigos=new ArrayList<>();
        myRef.child("codigos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    for(DataSnapshot codigo:snapshot.getChildren()){
                        Modelo_codigo modelo_codigo = codigo.getValue(Modelo_codigo.class);
                        if(modelo_codigo.isHabilidato()){
                            codigos.add(modelo_codigo);
                        }

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public  void buscar_plantas(final MainActivity mainActivity){
        Log.e("MY PLAICATION","BUSCANDO PLANTAS");


          listaner  = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(plantas==null){
                    plantas=new ArrayList<>();

                    mainActivity.verificar_usuario();
                }

                if(snapshot.exists()){
                        plantas.clear();

                    for(DataSnapshot snapshot_planta: snapshot.getChildren()){
                        Log.e("plantas firebase",snapshot_planta.toString());
                        Modelo_planta planta= snapshot_planta.getValue(Modelo_planta.class);
                        plantas.add(planta);



                    }



                }


              //  myRef.child("plantas").addValueEventListener(listaner);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        myRef.child("plantas").addValueEventListener(listaner);

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

    public ArrayList<Modelo_codigo> getCodigos() {
        return codigos;
    }

    public void setCodigos(ArrayList<Modelo_codigo> codigos) {
        this.codigos = codigos;
    }

    public boolean verificar_codigo(String codigo){
        Boolean resultado = false ;
        for(Modelo_codigo modelo_codigo:codigos){
            codigo.trim();
            if(modelo_codigo.getCodigo().equalsIgnoreCase(codigo)){
                Log.e("CODIGO A verificar",modelo_codigo.getId());
                return modelo_codigo.isHabilidato();
            }
        }
        return resultado;
    }

    public void eliminar_codigo(String codigo) {
        for(Modelo_codigo modelo_codigo:codigos){
            codigo.trim();
            if(modelo_codigo.getCodigo().equalsIgnoreCase(codigo)){
                myRef.child("codigos").child(modelo_codigo.getId()).removeValue();
                Log.e("CODIGO A ELIMINAR",modelo_codigo.getId());
            }
        }
    }
}
