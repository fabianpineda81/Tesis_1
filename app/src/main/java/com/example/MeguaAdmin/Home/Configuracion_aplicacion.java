package com.example.MeguaAdmin.Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.MeguaAdmin.Home.Fragmentos.Codigos;
import com.example.MeguaAdmin.R;
import com.example.MeguaAdmin.herramientas.Manejador_toolbar;

public class Configuracion_aplicacion extends AppCompatActivity {
    LinearLayout contenedor_configurar_planta;
    LinearLayout contenedor_configuar_codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion_aplicacion);

        inicializar();
    }

    private void inicializar() {
        Toolbar toolbar=findViewById(R.id.toolbar);
        Manejador_toolbar.showToolbar("Configuracion",true,this,toolbar);
        contenedor_configurar_planta= findViewById(R.id.contenedor_configurar_planta);
        contenedor_configuar_codigo=findViewById(R.id.contenedor_configurar_codigos);
        contenedor_configuar_codigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Configuracion_aplicacion.this, Codigos.class);
                startActivity(i);
            }
        });
        contenedor_configurar_planta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Configuracion_aplicacion.this, Container_Home.class);
                startActivity(i);
                Toast.makeText(Configuracion_aplicacion.this,"Eliga la planta a configurar",Toast.LENGTH_LONG).show();
            }
        });

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }



}