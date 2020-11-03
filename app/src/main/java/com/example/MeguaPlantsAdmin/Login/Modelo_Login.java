package com.example.MeguaPlantsAdmin.Login;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.MeguaPlantsAdmin.Home.Container_Home;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Modelo_Login {

    private FirebaseAuth Auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private Login activity_login;




    public Modelo_Login(Login activity) {
        this.activity_login=activity;
        Auth = FirebaseAuth.getInstance();


    }
    public void crear_usuario_correo(String correo,String contra){
    Auth.createUserWithEmailAndPassword(correo, contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()){
                Log.d("login", "createUserWithEmail:success");
                ir_home();
            }else{
                Toast.makeText(activity_login.getBaseContext(), "No se pudo crear el usuario ", Toast.LENGTH_SHORT).show();

            }
        }
    });

    }

    public void  loguearse(String correo , String contra){
        activity_login.disable_input();
        activity_login.show_progress_bar();
        Auth.signInWithEmailAndPassword(correo,contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    activity_login.hide_progress_bar();
                    ir_home();

                }else{
                    activity_login.hide_progress_bar();
                    activity_login.enable_input();
                Toast.makeText(activity_login.getBaseContext(),"datos arroneos",Toast.LENGTH_LONG);

                }
            }
        });




    }


    // aca hacemos el sig in con facebook recibimos las credenciales de token





    private void ir_home() {
        Intent i = new Intent(activity_login, Container_Home.class);
        activity_login.startActivity(i);
    }



}
