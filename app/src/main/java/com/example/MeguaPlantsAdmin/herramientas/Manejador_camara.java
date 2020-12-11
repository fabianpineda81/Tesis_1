package com.example.MeguaPlantsAdmin.herramientas;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.MeguaPlantsAdmin.R;
import com.example.MeguaPlantsAdmin.plantas.new_plant.New_plant_container;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Manejador_camara {

    String ruta_obsoluta;





    public File tomar_foto_new_plant(int  id_boton,Activity activity) {
        File foto=null;
        switch (id_boton){
            case R.id.btn_agregar_imagen1:
                Toast.makeText(activity, "escoger foto imagen 1",Toast.LENGTH_LONG).show();
                foto=abrir_camara_new_plant(Constantes.CONSTANTE_FOTO_IMAGEN_MUESTRA_FOTO1,activity);
                break;
            case R.id.btn_agregar_imagen2:
                foto=abrir_camara_new_plant(Constantes.CONSTANTE_FOTO_IMAGEN_MUESTRA_FOTO2,activity);
                Toast.makeText(activity, "escoger foto imagen 2",Toast.LENGTH_LONG).show();
                break;
            case  R.id.btn_agregar_imagen3:
                foto=abrir_camara_new_plant(Constantes.CONSTANTE_FOTO_IMAGEN_MUESTRA_FOTO3,activity);
                Toast.makeText(activity, "escoger foto imagen 3",Toast.LENGTH_LONG).show();
                break;

            case R.id.btn_agregar_imagen4:
                foto=abrir_camara_new_plant(Constantes.CONSTANTE_FOTO_IMAGEN_MUESTRA_FOTO4,activity);
                Toast.makeText(activity, "tomar foto 4",Toast.LENGTH_LONG).show();

                break;
            case  R.id.btn_agregar_imagen_modelo:
                foto= abrir_camara_new_plant(Constantes.CONSTANTE_FOTO_IMAGEN_MODELO,activity);
                break;
        }


        return  foto;
    }



    private File abrir_camara_new_plant(int constante_imagen,Activity activity){
        // prende la camara
       File  archivo_foto= null ;
        Intent intent_tomar_foto= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent_tomar_foto.resolveActivity(activity.getPackageManager())!=null){

            try {
                archivo_foto= crear_archivo_foto(activity);


            }catch (Exception e ){
                e.printStackTrace();
            }
        }else{

            Toast.makeText(activity,"dio null",Toast.LENGTH_LONG);

        }

        if(archivo_foto!=null ){
            Uri url_foto = FileProvider.getUriForFile(activity,"com.example.MeguaPlantsAdmin",archivo_foto);
            Log.d("Home_fragment","photo_uri:"+ url_foto);
            intent_tomar_foto.putExtra(MediaStore.EXTRA_OUTPUT,url_foto);

            New_plant_container new_plant_container= (New_plant_container) activity;
            new_plant_container.imagenes.startActivityForResult(intent_tomar_foto,constante_imagen);
        }
        return archivo_foto;

    }



    public File tomar_foto(Activity activity){
        // prende la camara
       File archivo_foto= null ;
        Intent intent_tomar_foto= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent_tomar_foto.resolveActivity(activity.getPackageManager())!=null){

            try {
                archivo_foto= crear_archivo_foto(activity);


            }catch (Exception e ){
                e.printStackTrace();
            }
        }else{

            Toast.makeText(activity,"dio null",Toast.LENGTH_LONG);

        }

        if(archivo_foto!=null ){
            Uri url_foto = FileProvider.getUriForFile(activity,"com.example.MeguaPlantsAdmin",archivo_foto);
            Log.d("Home_fragment","photo_uri:"+ url_foto);
            intent_tomar_foto.putExtra(MediaStore.EXTRA_OUTPUT,url_foto);


            activity.startActivityForResult(intent_tomar_foto,Constantes.CONSTANTE_TOMAR_FOTO);
        }
        return  archivo_foto;
    }


    public File tomar_foto(Fragment fragment){
        // prende la camara
      File  archivo_foto= null ;
        Intent intent_tomar_foto= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent_tomar_foto.resolveActivity(fragment.getActivity().getPackageManager())!=null){

            try {
                archivo_foto= crear_archivo_foto(fragment.getActivity());


            }catch (Exception e ){
                e.printStackTrace();
            }
        }else{

            Toast.makeText(fragment.getActivity(),"dio null",Toast.LENGTH_LONG);

        }

        if(archivo_foto!=null ){
            Uri url_foto = FileProvider.getUriForFile(fragment.getActivity(),"com.example.MeguaPlantsAdmin",archivo_foto);
            Log.d("Home_fragment","photo_uri:"+ url_foto);
            intent_tomar_foto.putExtra(MediaStore.EXTRA_OUTPUT,url_foto);


            fragment.startActivityForResult(intent_tomar_foto,Constantes.CONSTANTE_TOMAR_FOTO);
        }

        return archivo_foto;

    }




    // mover a clase camara
    private File crear_archivo_foto(Activity activity) throws IOException {
        File archivo_foto= null;
        String time_stamp=new SimpleDateFormat("yyyyMMdd_HH-mm-ss").format(new Date());

        String nombre_imagen="JPEG_"+time_stamp;


        File storage_dir= activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);



        archivo_foto= File.createTempFile(nombre_imagen,".jpg",storage_dir);



        Toast.makeText(activity,storage_dir.toString(),Toast.LENGTH_LONG);
        ruta_obsoluta=archivo_foto.getAbsolutePath();

        return  archivo_foto;
    }


}
