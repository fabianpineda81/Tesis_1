package com.example.MeguaPlantsAdmin.plantas;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.MeguaPlantsAdmin.Modelo_planta;
import com.example.MeguaPlantsAdmin.R;
import com.example.MeguaPlantsAdmin.plantas.New_plant;
import com.google.android.gms.auth.api.signin.internal.Storage;
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

import java.io.ByteArrayOutputStream;

public class New_plant_manager {
    Modelo_planta modelo_planta;
    ImageView imagen_modelo, imagen_muestra1, imagen_muestra2,imagen_muestra3;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef;
    UploadTask uploadTask;
    Boolean subiento_archivos= false ;
    Activity activity;
    int conn=1;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mydbref = database.getReference();



    public New_plant_manager(Modelo_planta modelo_planta, ImageView imagen_modelo, ImageView imagen_muestra1, ImageView imagen_muestra2, ImageView imagen_muestra3,Activity activity) {
        this.modelo_planta = modelo_planta;
        this.imagen_modelo = imagen_modelo;
        this.imagen_muestra1 = imagen_muestra1;
        this.imagen_muestra2 = imagen_muestra2;
        this.imagen_muestra3 = imagen_muestra3;
        this.activity=activity;
        createNotificationChannel();
        storageRef = storage.getReference("imagenes");
        montar_imagenes();


    }

   private void  montar_imagenes(){

       montar_imagen(imagen_modelo,"Imagen_modelo");


    }

    private void  montar_imagen(ImageView New_imagen,String nombre_imagen){

        New_imagen.setDrawingCacheEnabled(true);
        New_imagen.buildDrawingCache();
        Bitmap bitmap= New_imagen.getDrawingCache();


        ByteArrayOutputStream baos= new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);



        byte[] foto_byte= baos.toByteArray();



        final StorageReference foto_referecia= storageRef.child(this.modelo_planta.getNombre_cientifico()+"/"+nombre_imagen);

        uploadTask=foto_referecia.putBytes(foto_byte);



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
                                montar_imagen(imagen_muestra1,"imagen muestra");

                                break;


                            case 2:
                               conn++;
                                modelo_planta.setLink_imagen_muestra_1(des_uri.toString());
                                montar_imagen(imagen_muestra2,"imagen muestra2");
                                break;
                            case 3:
                                conn++;
                                modelo_planta.setLink_imagen_muestra_2(des_uri.toString());

                                montar_imagen(imagen_muestra3,"imagen muestra3");

                                break;
                            case 4 :
                                modelo_planta.setLink_imagen_muestra_3(des_uri.toString());
                                crear_notificacion("Planta montada","carga completa");
                                mydbref.child("plantas").child(modelo_planta.getNombre_cientifico()).setValue(modelo_planta);
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
                    crear_notificacion("Subiendo archivos","progreso");
                    subiento_archivos=true;
                }else{
                    if(taskSnapshot.getTotalByteCount()>0){
                        int porcentaje = (int) ((100*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount());
                        crear_notificacion_barra("Progreso","foto",porcentaje);
                    }
                }
            }
        }) ;



    }

    private void montar_planta(Modelo_planta modelo_planta) {

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name ="canal";
            String description = "no se";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("hola", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager =activity.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }

    private void  crear_notificacion(String titulo ,String mensaje) {

        NotificationCompat.Builder notificacion   =  new NotificationCompat.Builder(activity.getBaseContext(),"hola")
                .setSmallIcon(R.drawable.ic_home)
                .setContentTitle(titulo)
                .setContentText(mensaje)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(mensaje));

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(activity);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(000,notificacion.build());


    }

    private void crear_notificacion_barra(String titulo ,String mensaje,int progreso) {

        NotificationCompat.Builder notificacion   =  new NotificationCompat.Builder(activity,"hola")
                .setSmallIcon(R.drawable.ic_home)
                .setContentTitle(titulo)
                .setContentText(mensaje)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(mensaje ))
                .setProgress(100, progreso,false);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(activity);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(000,notificacion.build());



    }







}
