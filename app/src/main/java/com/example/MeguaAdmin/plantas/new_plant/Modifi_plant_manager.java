package com.example.MeguaAdmin.plantas.new_plant;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.MeguaAdmin.Home.Container_Home;
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

import java.util.ArrayList;


public class Modifi_plant_manager {
    Modelo_planta planta_vieja;
    Modelo_planta planta_nueva;

    ArrayList<Modelo_uri> modelo_uris=new ArrayList<>();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef=storage.getReference("imagenes");;
    UploadTask uploadTask;
    Boolean subiento_archivos= false ;
    Activity activity;
    int conn=0;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mydbref = database.getReference();
    Manejador_Bitmap manejador_bitmap= new Manejador_Bitmap();
    private Manejador_notificaciones notificaciones;


    public Modifi_plant_manager(Activity activity,Modelo_planta planta_vieja,Modelo_planta planta_nueva) {
        this.activity = activity;
        this.planta_nueva= planta_nueva;
        this.planta_vieja=planta_vieja;
        notificaciones=new Manejador_notificaciones(activity);

    }

    public void set_imagenes(ArrayList<Modelo_uri> imagenes){

        modelo_uris=imagenes;
    }



    public void  modificar_planta(){


        if(modelo_uris.size()==0){
            planta_vieja.modificar_plant(planta_nueva);
            Toast.makeText(activity,"Planta modificada correctamente",Toast.LENGTH_LONG).show();
            Log.e("Modifi_plant","modifico la planta  "+planta_nueva.getDatos_interes());
            ir_home();
        }else{
            montar_imagen1(modelo_uris.get(0),modelo_uris.get(0).getNombre());
        }


}

    private void ir_home() {
        Intent i = new Intent(activity, Container_Home.class);
        activity.startActivity(i);
    }

    private void  montar_imagen1(Modelo_uri New_imagen, final String nombre_imagen ){


        final StorageReference foto_referecia= storageRef.child(this.planta_nueva.getNombre_cientifico()+"/"+nombre_imagen);


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


                        switch (modelo_uris.get(conn).getNombre()){
                            case Constantes.IMAGEN_MODELO:
                                planta_nueva.setLink_imagen_modelo(des_uri.toString());
                                break;
                            case Constantes.IMAGEN_MUESTRA1:
                                planta_nueva.setLink_imagen_muestra_1(des_uri.toString());
                                break;
                            case Constantes.IMAGEN_MUESTRA2:
                                planta_nueva.setLink_imagen_muestra_2(des_uri.toString());
                                break;
                            case Constantes.IMAGEN_MUESTRA3:
                                planta_nueva.setLink_imagen_muestra_3(des_uri.toString());
                                break;
                            case Constantes.IMAGEN_MUESTRA4:
                                planta_nueva.setLink_imagen_muestra_4(des_uri.toString());
                                break;
                            default:
                                Log.e("MODIFI","NO SE FUE NINGUNA OP");
                                break;
                        }
                        conn++;
                       if(modelo_uris.size()>conn){
                         montar_imagen1(modelo_uris.get(conn),modelo_uris.get(conn).getNombre());

                       }else{
                           planta_vieja.modificar_plant(planta_nueva);
                           Toast.makeText(activity,"Planta modificada correctamente",Toast.LENGTH_LONG).show();
                           ir_home();
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
}
