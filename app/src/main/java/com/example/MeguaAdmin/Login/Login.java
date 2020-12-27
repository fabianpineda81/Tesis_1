package com.example.MeguaAdmin.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.MeguaAdmin.R;
import com.google.android.material.textfield.TextInputEditText;


public class Login extends AppCompatActivity {
 private ProgressBar progressBar ;
    private TextInputEditText user_name, password;
    private TextView crear_cuenta ;
    private Button button_login;
    private Modelo_Login modelo_login;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inicializar();

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user_name.getText().toString().equals("")&& password.getText().toString().equals("")) {
                    Mostrar_mensaje("Hey ingresa tus datos");
                } else if (user_name.getText().toString().equals("")){
                    Mostrar_mensaje("Ingresa tu usuario");
                } else if (password.getText().toString().equals("")){
                    Mostrar_mensaje("Ingresa La contraseña");
                } else {
                    modelo_login.loguearse(user_name.getText().toString(),password.getText().toString(),Login.this);

                }

            }
        });
        crear_cuenta=findViewById(R.id.create_here);
        crear_cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this,Crear_cuenta.class);
                startActivity(i);

            }
        });


    }

    private void Mostrar_mensaje(String mensaje ) {
        Toast.makeText(Login.this,mensaje,Toast.LENGTH_LONG).show();



    }

    private void inicializar() {
        progressBar = findViewById(R.id.progress_bar_login);
        progressBar.setVisibility(View.GONE);
        user_name= findViewById(R.id.username);
        password= findViewById(R.id.password);
        button_login= findViewById(R.id.login);
        modelo_login= new Modelo_Login(this );




    }
    public void disable_input() {
        // des hablitamos los imput
        user_name.setEnabled(false );
        password.setEnabled(false);
        button_login.setEnabled(false);

    }
    public void enable_input() {
        // aca habilitamos los imput
        user_name.setEnabled(true );
        password.setEnabled(true);
        button_login.setEnabled(true);
    }
    public void show_progress_bar() {
        // cambiamos la visibilidad del progress bar
        progressBar.setVisibility(View.VISIBLE);
    }
    public void hide_progress_bar() {
        // cambiamos la visibilidad del progress bar
        progressBar.setVisibility(View.GONE);
    }

}