package com.example.MeguaAdmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.MeguaAdmin.Home.Container_Home;
import com.example.MeguaAdmin.Login.Login;
import com.example.MeguaAdmin.herramientas.My_aplicacion;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    My_aplicacion my_aplicacion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth= FirebaseAuth.getInstance();



       my_aplicacion = (My_aplicacion) this.getApplication();
        Log.e("MAIN ACTIVITY","en el main activity");
           my_aplicacion.inicializar(this);





    }




    public void verificar_usuario() {
        authStateListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user =mAuth.getCurrentUser();

                if(user!= null ){
                    Log.e("OJO","NOS VAMOS AL HOME ");
                    ir_home();


                }else{
                    Log.e("OJO","NOS VAMOS AL LOGON ");
                    ir_login();

                }
            }
        };
        mAuth.addAuthStateListener(authStateListener);
        mAuth.removeAuthStateListener(authStateListener);
    }

    private void ir_home() {
        Intent i = new Intent(MainActivity.this, Container_Home.class);
        startActivity(i);
    }
    private void ir_login() {
        Intent i = new Intent(MainActivity.this, Login.class);
        startActivity(i);
    }
}