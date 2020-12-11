package com.example.MeguaPlantsAdmin.herramientas;

import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.MeguaPlantsAdmin.R;
import com.example.MeguaPlantsAdmin.plantas.new_plant.New_plant_container;

public class Manejador_seleccionador_imagenes {


    private Activity activity;

    public Manejador_seleccionador_imagenes(Activity activity) {
        this.activity = activity;

    }


    public void seleccionar_imagen(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
       activity.startActivityForResult(intent.createChooser(intent,"Selecione aplicacion"), Constantes.CONSTANTE_ESCOGER_IMAGEN);

    }

    public void seleccionar_imagen(Fragment fragment) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        fragment.startActivityForResult(intent.createChooser(intent,"Selecione aplicacion"), Constantes.CONSTANTE_ESCOGER_IMAGEN);

    }

    public void seleccionar_imagen_new_plant(int id_boton) {

        switch (id_boton){

            case R.id.btn_agregar_imagen_modelo:

                abrir_seleccionador_imagenes_new_plant((Constantes.CONSTANTE_IMAGEN_ESCOGIDA_MODELO));

                break;
            case R.id.btn_agregar_imagen1:

                abrir_seleccionador_imagenes_new_plant((Constantes.CONSTANTE_IMAGEN_ESCOGIDA_MUESTRA_1));
                break;

            case R.id.btn_agregar_imagen2:
                abrir_seleccionador_imagenes_new_plant((Constantes.CONSTANTE_IMAGEN_ESCOGIDA_MUESTRA_2));

                break;
            case R.id.btn_agregar_imagen3:
                abrir_seleccionador_imagenes_new_plant(Constantes.CONSTANTE_IMAGEN_ESCOGIDA_MUESTRA_3);

                break;
            case R.id.btn_agregar_imagen4:
                abrir_seleccionador_imagenes_new_plant(Constantes.CONSTANTE_IMAGEN_ESCOGIDA_MUESTRA_4);
        }



    }

    public void abrir_seleccionador_imagenes_new_plant(int constante) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        New_plant_container new_plant_container= (New_plant_container) activity;

        new_plant_container.imagenes.startActivityForResult(intent.createChooser(intent,"Selecione aplicacion"), constante);
    }



}
