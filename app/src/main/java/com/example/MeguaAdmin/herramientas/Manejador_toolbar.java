package com.example.MeguaAdmin.herramientas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.MeguaAdmin.R;

public class Manejador_toolbar {





    public static void showToolbar(String tittle, boolean upButton,AppCompatActivity activity,Toolbar toolbar) {
        //  aca declaramos una variable toolbar y traemos el tooblar de view


        //  aca enviamos el soporte el toolbar para asi personalizarlo
       activity.setSupportActionBar(toolbar);

        //se le pone el titulo


        //se le pone el boton de regreso (hay que configurar la jerarquia )
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);


        toolbar.setTitle(tittle);


    }




}