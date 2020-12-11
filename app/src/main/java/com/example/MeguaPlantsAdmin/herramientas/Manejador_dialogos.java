package com.example.MeguaPlantsAdmin.herramientas;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.MeguaPlantsAdmin.plantas.new_plant.New_plant_container;

import java.io.File;

public class Manejador_dialogos {
    File foto;

    String[] opciones_imagenes= {"Escoger galeria ","Tomar una foto"};





    public Dialog Crear_dialogo_escoger_imagen_new_plant(final int  id_boton, final Activity activity ) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("escoger foto ").setItems(opciones_imagenes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i == 0){
                    Manejador_seleccionador_imagenes manejador_seleccionador_imagenes= new Manejador_seleccionador_imagenes(activity);
                    manejador_seleccionador_imagenes.seleccionar_imagen_new_plant(id_boton);
                    }else{
                    Manejador_camara manejador_camara= new Manejador_camara();
                 foto =manejador_camara.tomar_foto_new_plant(id_boton,activity);
                }
            }
        });

        return  builder.create();
    }



    public Dialog crear_dialogo_escoger_imagen(final Activity activity) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity );
        builder.setTitle("escoger foto ").setItems(opciones_imagenes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i == 0){
                 Manejador_seleccionador_imagenes manejador_seleccionador_imagenes= new Manejador_seleccionador_imagenes(activity);
                 manejador_seleccionador_imagenes.seleccionar_imagen(activity);
                }else{
                  Manejador_camara manejador_camara=new Manejador_camara();
                  foto =manejador_camara.tomar_foto(activity);

                }
            }
        });
        return  builder.create();
    }

    public Dialog crear_dialogo_escoger_imagen(final Fragment fragment) {

        AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getActivity() );
        builder.setTitle("escoger foto ").setItems(opciones_imagenes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i == 0){
                    Manejador_seleccionador_imagenes manejador_seleccionador_imagenes= new Manejador_seleccionador_imagenes(fragment.getActivity());
                    manejador_seleccionador_imagenes.seleccionar_imagen(fragment);
                }else{
                    Manejador_camara manejador_camara=new Manejador_camara();
                  foto=  manejador_camara.tomar_foto(fragment);

                }
            }
        });
        return  builder.create();
    }

    public File getFoto() {
        return foto;
    }


}
