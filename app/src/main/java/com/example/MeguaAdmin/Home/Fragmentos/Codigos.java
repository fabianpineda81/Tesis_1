package com.example.MeguaAdmin.Home.Fragmentos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.MeguaAdmin.R;
import com.example.MeguaAdmin.herramientas.Manejador_toolbar;
import com.example.MeguaAdmin.herramientas.Modelo_codigo;
import com.example.MeguaAdmin.herramientas.My_aplicacion;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Codigos extends AppCompatActivity {
    ListView listView ;
    private TextInputLayout container_new_codigo;
    private Button crear_codigo;
    private EditText new_codigo;
    My_aplicacion my_aplicacion;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ArrayList<String> codigos;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigos);


        inicializar();
        mostrar_codigos();
    }

    public void mostrar_codigos() {
        listView = findViewById(R.id.codigos_disponibles);
        my_aplicacion =(My_aplicacion) getApplication();
         codigos= obtener_codigos(my_aplicacion.getCodigos());
         arrayAdapter= new ArrayAdapter(this,android.R.layout.simple_list_item_1,codigos);
        listView.setAdapter(arrayAdapter);
    }

    private ArrayList<String> obtener_codigos(ArrayList<Modelo_codigo> modelo_codigos) {

        ArrayList<Modelo_codigo> codigos= my_aplicacion.getCodigos();
        ArrayList<String> codigos_String=new ArrayList<>();

        for(Modelo_codigo codigo:codigos){
            codigos_String.add(codigo.getCodigo());
        }
        return codigos_String;
    }

    private void inicializar() {
        Toolbar toolbar=findViewById(R.id.toolbar);
        Manejador_toolbar.showToolbar("Codigos de ingreso",true,this,toolbar);
        crear_codigo= findViewById(R.id.crear_codigo);
        container_new_codigo=findViewById(R.id.container_new_codigo);
        new_codigo= findViewById(R.id.new_codigo);
        crear_codigo.setOnClickListener(crear_onclick_montar_codigo());
        cambiar_onchage_codigo();

    }

    private void cambiar_onchage_codigo() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        myRef.child("codigos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                codigos.clear();
                ArrayList<Modelo_codigo> modelo_codigos=new ArrayList<>();
                if(snapshot.exists()){
                    for(DataSnapshot codigo:snapshot.getChildren()){
                        Modelo_codigo  modelo_codigo= codigo.getValue(Modelo_codigo.class);
                        if(modelo_codigo.isHabilidato()){
                            modelo_codigos.add(modelo_codigo);
                            codigos.add(modelo_codigo.getCodigo());
                            Log.e("HOLA","HOLA");
                        }


                    }
                }
                my_aplicacion.setCodigos(modelo_codigos);
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private View.OnClickListener crear_onclick_montar_codigo() {
        View.OnClickListener onClickListener= new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(new_codigo.getText().length()<7){
                    container_new_codigo.setError("Debe ser mayor a 6 caracteres ");

                }else{
                    container_new_codigo.setErrorEnabled(false);
                    Modelo_codigo modelo_codigo= new Modelo_codigo(true,new_codigo.getText().toString());
                    modelo_codigo.subir_firebase();
                }

            }
        };
        return onClickListener;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}