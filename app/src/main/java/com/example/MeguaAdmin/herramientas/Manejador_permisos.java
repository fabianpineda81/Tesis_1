package com.example.MeguaAdmin.herramientas;

import android.Manifest;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class Manejador_permisos {

    AppCompatActivity activity ;

    public Manejador_permisos(AppCompatActivity activity) {
        this.activity = activity;
    }

    public boolean verificar_permiso() {

        int verificarPermisoReadContacts = ContextCompat
                .checkSelfPermission(activity , Manifest.permission.READ_EXTERNAL_STORAGE);
        if(verificarPermisoReadContacts!= PackageManager.PERMISSION_GRANTED){

            if(activity.shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){
                //pedir el permiso

                activity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constantes.CONSTANTE_PERMISO_READ);


            }else{
                Toast.makeText(activity,"por faovr acepte",Toast.LENGTH_LONG).show();
                activity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constantes.CONSTANTE_PERMISO_READ);
            }

                return true;
        }else{

            return  true ;
        }

    }
    public boolean verificar_permiso2() {

        int verificarPermisoReadContacts = ContextCompat
                .checkSelfPermission(activity , Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(verificarPermisoReadContacts!= PackageManager.PERMISSION_GRANTED){

            if(activity.shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                //pedir el permiso

                activity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 23);


            }else{
                Toast.makeText(activity,"por faovr acepte",Toast.LENGTH_LONG).show();
                activity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 23);
            }

            return true;
        }else{
            Toast.makeText(activity,"esta todo correcto",Toast.LENGTH_LONG).show();
            return  true ;
        }

    }

}
