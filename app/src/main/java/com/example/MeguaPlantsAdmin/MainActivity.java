package com.example.MeguaPlantsAdmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.MeguaPlantsAdmin.Home.Container_Home;
import com.example.MeguaPlantsAdmin.Login.Login;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth= FirebaseAuth.getInstance();
        authStateListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user =mAuth.getCurrentUser();
                if(user!= null ){
                    ir_home();
                }else{
                    ir_login();
                }
            }
        };
            mAuth.addAuthStateListener(authStateListener);
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