package com.example.MeguaAdmin.Login;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.MeguaAdmin.Home.Container_Home;
import com.example.MeguaAdmin.MainActivity;
import com.example.MeguaAdmin.herramientas.Constantes;
import com.example.MeguaAdmin.herramientas.My_aplicacion;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Modelo_Login {

    private FirebaseAuth Auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private  Activity activity;





    public Modelo_Login(Activity activity) {
        Auth = FirebaseAuth.getInstance();
        this.activity= activity;
    }
    public void crear_usuario_correo(final String correo, final String contra, final String username, final ProgressBar progressBar){
    Auth.createUserWithEmailAndPassword(correo, contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()){
                Log.d("login", "createUserWithEmail:success");
                String id = task.getResult().getUser().getUid();
                Log.e("ID_USUARIO","usuario creado   "+id);
                crear_usuario_data_base(id,correo,contra,username);
                if(progressBar!=null){
                    progressBar.setVisibility(View.INVISIBLE);
                }


            }else{
                Toast.makeText(activity.getBaseContext(), "No se pudo crear el usuario ", Toast.LENGTH_SHORT).show();

            }
        }
    });

    }

    private void crear_usuario_data_base(String id , String correo,String contra,String username) {
        Modelo_usuario usuario=  new Modelo_usuario(id,username,correo,contra, Constantes.ROL_ADMINISTRADOR);
        usuario.montar_firebase(activity);
    }

    public void  loguearse(String correo , String contra, final Login activity_login){
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


                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                Toast.makeText(activity_login.getBaseContext(),"datos arroneos",Toast.LENGTH_LONG).show();
            }
        });




    }


    // aca hacemos el sig in con facebook recibimos las credenciales de token



    public void cerrar_seccion_correo(){
        Auth.signOut();
        My_aplicacion my_aplicacion=(My_aplicacion) activity.getApplication();
        my_aplicacion.setPlantas(null);
        ir_login();
    }

    private void ir_home() {
        Intent i = new Intent(activity, MainActivity.class);
        activity.startActivity(i);
    }

    private  void  ir_login(){
        Intent intent= new Intent(activity,Login.class);
        activity    .startActivity(intent);

    }







}
