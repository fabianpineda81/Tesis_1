package com.example.MeguaAdmin.plantas.new_plant;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.MeguaAdmin.herramientas.Constantes;
import com.example.MeguaAdmin.herramientas.Manejador_Bitmap;
import com.example.MeguaAdmin.herramientas.Manejador_notificaciones;
import com.example.MeguaAdmin.herramientas.Modelo_uri;
import com.example.MeguaAdmin.plantas.Modelo_planta;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class New_plant_manager {
    Modelo_planta modelo_planta;
    Modelo_uri imagen_modelo, imagen_muestra1, imagen_muestra2,imagen_muestra3,imagen_muestra4;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef=storage.getReference("imagenes");;
    UploadTask uploadTask;
    Boolean subiento_archivos= false ;
    Activity activity;
    int conn=1;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mydbref = database.getReference();
    Manejador_Bitmap manejador_bitmap= new Manejador_Bitmap();
    private Manejador_notificaciones notificaciones;

    public New_plant_manager(Activity activity) {
        this.activity = activity;
        notificaciones= new Manejador_notificaciones(activity);


    }

    public New_plant_manager(Modelo_planta modelo_planta, Activity activity) {
        this.modelo_planta = modelo_planta;

        this.activity=activity;
        notificaciones= new Manejador_notificaciones(activity);

    }


    public void  montar_imagenes(){

       montar_imagen1(imagen_modelo,"Imagen_modelo");


    }

    private void montar_imagen(Modelo_uri New_imagen,String nombre_imagen){
        final StorageReference foto_referecia= storageRef.child(this.modelo_planta.getNombre_cientifico()+"/"+nombre_imagen);
        Bitmap bitmap;


        if(New_imagen.is_foto()){
            bitmap=manejador_bitmap.rotar_foto(New_imagen.getUri(),activity);
        }else{
            bitmap=manejador_bitmap.rotar_imagen(New_imagen.getUri(),activity);
        }


        byte[] foto_bytes = manejador_bitmap.de_bitmap_a_byte_array_comprees(bitmap);
        uploadTask= foto_referecia.putBytes(foto_bytes);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> urltask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw task.getException();
                        }

                        return  foto_referecia.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        Uri des_uri= task.getResult();
                        Log.d("URL OJALA",des_uri.toString());


                    }
                });
            }
        });

    }

    private void  montar_imagen1(Modelo_uri New_imagen, String nombre_imagen){


        final StorageReference foto_referecia= storageRef.child(this.modelo_planta.getNombre_cientifico()+"/"+nombre_imagen);


        //uploadTask=foto_referecia.putFile(New_imagen);

        //foto_referecia.putFile(New_imagen);

        Bitmap bitmap=null;
        if(New_imagen.is_foto()){
            bitmap=manejador_bitmap.rotar_foto(New_imagen.getUri(),activity);
        }else{


       bitmap=manejador_bitmap.rotar_imagen(New_imagen.getUri(),activity);


        }





        byte[] foto_byte= manejador_bitmap.de_bitmap_a_byte_array_comprees(bitmap);


        uploadTask= foto_referecia.putBytes(foto_byte);



        uploadTask.addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(,e.getMessage(),Toast.LENGTH_LONG);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> urltask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw task.getException();
                        }

                        return  foto_referecia.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        Uri des_uri= task.getResult();
                        Log.d("URL OJALA",des_uri.toString());

                        switch (conn){
                            case 1:
                                conn++;

                                modelo_planta.setLink_imagen_modelo(des_uri.toString());
                                montar_imagen1(imagen_muestra1, Constantes.IMAGEN_MUESTRA1);

                                break;


                            case 2:
                               conn++;
                                modelo_planta.setLink_imagen_muestra_1(des_uri.toString());
                                montar_imagen1(imagen_muestra2,Constantes.IMAGEN_MUESTRA2);
                                break;
                            case 3:
                                conn++;
                                modelo_planta.setLink_imagen_muestra_2(des_uri.toString());

                                montar_imagen1(imagen_muestra3,Constantes.IMAGEN_MUESTRA3);

                                break;
                            case 4:
                                conn++;
                                modelo_planta.setLink_imagen_muestra_3(des_uri.toString());

                                montar_imagen1(imagen_muestra4,Constantes.IMAGEN_MUESTRA4);

                                break;
                            case 5 :
                                modelo_planta.setLink_imagen_muestra_4(des_uri.toString());
                               notificaciones.crear_notificacion("Planta montada","carga completa");
                                //mydbref.child("plantas").child(modelo_planta.getNombre_cientifico()).setValue(modelo_planta);
                                modelo_planta.montar_firebase(activity);
                               conn=1;
                                break;

                        }





                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                if(!subiento_archivos){
                    notificaciones.crear_notificacion("Subiendo archivos","progreso");
                    subiento_archivos=true;
                }else{
                    if(taskSnapshot.getTotalByteCount()>0){
                        int porcentaje = (int) ((100*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount());
                       notificaciones.crear_notificacion_barra("Progreso","foto",porcentaje);
                    }
                }
            }
        }) ;



    }





    public Modelo_planta getModelo_planta() {
        return modelo_planta;
    }

    public void setModelo_planta(Modelo_planta modelo_planta) {
        this.modelo_planta = modelo_planta;
    }

    public Modelo_uri getImagen_modelo() {
        return imagen_modelo;
    }

    public void setImagen_modelo(Modelo_uri imagen_modelo) {
        this.imagen_modelo = imagen_modelo;
    }

    public Modelo_uri getImagen_muestra1() {
        return imagen_muestra1;
    }

    public void setImagen_muestra1(Modelo_uri imagen_muestra1) {
        this.imagen_muestra1 = imagen_muestra1;
    }

    public Modelo_uri getImagen_muestra2() {
        return imagen_muestra2;
    }

    public void setImagen_muestra2(Modelo_uri imagen_muestra2) {
        this.imagen_muestra2 = imagen_muestra2;
    }

    public Modelo_uri getImagen_muestra3() {
        return imagen_muestra3;
    }

    public void setImagen_muestra3(Modelo_uri imagen_muestra3) {
        this.imagen_muestra3 = imagen_muestra3;
    }

    public Modelo_uri getImagen_muestra4() {
        return imagen_muestra4;
    }

    public void setImagen_muestra4(Modelo_uri imagen_muestra4) {
        this.imagen_muestra4 = imagen_muestra4;
    }
}
