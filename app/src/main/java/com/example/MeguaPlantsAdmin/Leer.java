package com.example.MeguaPlantsAdmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestFutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ml.common.FirebaseMLException;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.automl.FirebaseAutoMLRemoteModel;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;
import com.google.firebase.ml.vision.label.FirebaseVisionOnDeviceAutoMLImageLabelerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Leer extends AppCompatActivity {
    private static final int CONSTANTE_IMAGEN_ESCOGIDA_MODELO =1 ;

ImageView imagen;
    RecyclerView recyclerView;
    FirebaseVisionImage image;
    FirebaseVisionImageLabeler labeler = FirebaseVision.getInstance().getCloudImageLabeler();
    FirebaseVisionImageLabeler mio;
    FirebaseAutoMLRemoteModel remoteModel = new FirebaseAutoMLRemoteModel.Builder("Flores").build();
    FirebaseModelManager modelManager= FirebaseModelManager.getInstance();
    FirebaseDatabase database ;
    DatabaseReference myRef ;
    Adater_recycler_plantas adater_recycler_plantas;
    ArrayList<Modelo_planta> plantas= new ArrayList<>();
    String nombre_cientifico_planta="";
    Modelo_planta modelo_planta ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leer);
        inicializar();



            cargar_imagen();





    }

    private void cargar_imagen()  {
        if(getIntent().hasExtra("ruta_imagen")){
            String url= getIntent().getExtras().getString("ruta_imagen");
            Glide.with(this).load(url).into(imagen);


        }else{

            if (getIntent().hasExtra("uri")){

                String uri_string = getIntent().getExtras().getString("uri");
                Uri uri=Uri.parse(uri_string);

                Log.e("leer",uri.toString());
                //esto para comverti a bitmap
                //Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                //Glide.with(this).load(bitmap).into(imagen);
                Glide.with(this).load(uri).addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                        return false;
                    }
                }).into(imagen);



            }else{
                Log.e("leer","no llego el extra");
            }
        }

    }

    private void reconocer_imagen() {

        imagen.setDrawingCacheEnabled(true);
        imagen.buildDrawingCache();
        Bitmap bitmap= imagen.getDrawingCache();
        image = FirebaseVisionImage.fromBitmap(bitmap);

        mio.processImage(image).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
            @Override
            public void onSuccess(List<FirebaseVisionImageLabel> labels) {
                for (FirebaseVisionImageLabel label: labels) {
                    String labelText = label.getText();
                    String entityId = label.getEntityId();
                    float confidence = label.getConfidence();
                    Log.e("prueba de label","etiqueta: "+labelText+"  fialidad"+confidence);
                    if(confidence>0.6){
                        nombre_cientifico_planta=labelText;
                    }
                }
                buscar_planta();
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("prueba de label","Fallo");
                e.printStackTrace();
            }
        });



    }

    private void buscar_planta() {
        database= FirebaseDatabase.getInstance();
        myRef= database.getReference();
        myRef.child("plantas").child(nombre_cientifico_planta).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                modelo_planta= snapshot.getValue(Modelo_planta.class);
//                Log.e("nombre planta  ",modelo_planta.getNombre_cientifico());
                plantas.add(modelo_planta);
                adater_recycler_plantas.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        cargar_recycler_view();
    }

    private void cargar_recycler_view() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(manager);

        myRef.child("plantas").child(nombre_cientifico_planta);

        adater_recycler_plantas= new Adater_recycler_plantas(plantas,R.layout.layout_carta_planta,this);

        recyclerView.setAdapter(adater_recycler_plantas);
    }

    private void inicializar() {
        recyclerView = findViewById(R.id.recycler_picture_leer);

        imagen= findViewById(R.id.img_reconocer);

        createNotificationChannel();
            // creamos las condiciones de descargas
        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder()
                .requireWifi()
                .build();

        // ahora al model manager le pasamos el modelo y las condiciones para hacer la descarga
       modelManager.download(remoteModel, conditions)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        crear_notificacion("termino de descargar","termino de descargar");
                    }
                });

            // aca miramos si esta descargado el modelo
       modelManager.isModelDownloaded(remoteModel).addOnSuccessListener(new OnSuccessListener<Boolean>() {
           @Override
           public void onSuccess(Boolean descargado) {
                 if(descargado){
                     FirebaseVisionOnDeviceAutoMLImageLabelerOptions optionsBuilder = new FirebaseVisionOnDeviceAutoMLImageLabelerOptions.Builder(remoteModel)
                             .setConfidenceThreshold(0.0f)  // Evaluate your model in the Firebase console
                             // to determine an appropriate value.
                             .build();

                     try {
                         // aca le pasamos el modelo al lebeler
                         mio = FirebaseVision.getInstance().getOnDeviceAutoMLImageLabeler(optionsBuilder);
                         reconocer_imagen();
                     } catch (FirebaseMLException e) {
                         e.printStackTrace();
                     }
                 }
           }
       });





    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode==CONSTANTE_IMAGEN_ESCOGIDA_MODELO){
          Uri uri= data.getData();

            Glide.with(this).load(uri).into(imagen);

        }
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
            NotificationManager notificationManager =getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }

    private void  crear_notificacion(String titulo ,String mensaje) {

        NotificationCompat.Builder notificacion   =  new NotificationCompat.Builder(getBaseContext(),"hola")
                .setSmallIcon(R.drawable.ic_home)
                .setContentTitle(titulo)
                .setContentText(mensaje)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(mensaje));

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(000,notificacion.build());


    }
}