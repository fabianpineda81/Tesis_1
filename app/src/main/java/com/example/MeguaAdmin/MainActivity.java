package com.example.MeguaAdmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.MeguaAdmin.Home.Container_Home;
import com.example.MeguaAdmin.Login.Login;
import com.example.MeguaAdmin.Login.Modelo_Login;
import com.example.MeguaAdmin.Login.Modelo_usuario;
import com.example.MeguaAdmin.herramientas.Constantes;
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

                    Modelo_usuario usuario = my_aplicacion.getUsuario();

                    if(usuario.getRol().equalsIgnoreCase(Constantes.ROL_ADMINISTRADOR)){
                        Log.e("OJO","NOS VAMOS AL HOME ");
                        ir_home();
                    }else{
                        Toast.makeText(MainActivity.this,"Solo usuarios administradores",Toast.LENGTH_LONG).show();
                        Modelo_Login modelo_login= new Modelo_Login(MainActivity.this);
                        modelo_login.cerrar_seccion_correo();
                        ir_login();
                    }




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