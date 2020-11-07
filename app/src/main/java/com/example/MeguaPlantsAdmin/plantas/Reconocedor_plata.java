package com.example.MeguaPlantsAdmin.plantas;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.MeguaPlantsAdmin.Modelo_planta;
import com.example.MeguaPlantsAdmin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Reconocedor_plata {
    Adater_recycler_plantas adater_recycler_plantas;
    Bitmap imagen ;
    ImageView imagen_View =null ;
    FirebaseVisionImage image;
    FirebaseVisionImageLabeler mio;
    String nombre_cientifico_planta="";
    FirebaseDatabase database ;
    DatabaseReference myRef ;
    Modelo_planta modelo_planta;
    ArrayList<Modelo_planta> plantas= new ArrayList<>();
    RecyclerView recyclerView;
    Boolean descargado = false;
    Activity activity;
    BottomSheetBehavior bottomSheetBehavior=null;
    Uri uri;


    FirebaseAutoMLRemoteModel remoteModel = new FirebaseAutoMLRemoteModel.Builder("Flores").build();
    FirebaseModelManager modelManager= FirebaseModelManager.getInstance();


    public Reconocedor_plata(RecyclerView recyclerView, Activity activity,  BottomSheetBehavior bottomSheetBehavior, Uri uri,ImageView imagen_View) {
        this.recyclerView = recyclerView;
        this.activity = activity;
        this.imagen_View =imagen_View;
        this.bottomSheetBehavior = bottomSheetBehavior;
        this.uri=uri;
        descargar_modelo();
    }




    private void descargar_modelo() {
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
                    } catch (FirebaseMLException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private void reconocer_imagen() throws IOException {
        if(imagen_View!=null) {
            imagen_View.setDrawingCacheEnabled(true);
            imagen_View.buildDrawingCache();
             imagen = imagen_View.getDrawingCache();
               ByteArrayOutputStream baos= new ByteArrayOutputStream();
           imagen.compress(Bitmap.CompressFormat.JPEG,100,baos);
            Log.e("roconocedor","bit map null");
        }
        image = FirebaseVisionImage.fromBitmap(imagen);

       // image=FirebaseVisionImage.fromFilePath(activity,uri);

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
        LinearLayoutManager manager = new LinearLayoutManager(activity);
        manager.setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(manager);

        myRef.child("plantas").child(nombre_cientifico_planta);

        adater_recycler_plantas= new Adater_recycler_plantas(plantas, R.layout.layout_carta_planta,activity);

        recyclerView.setAdapter(adater_recycler_plantas);
        //if(bottomSheetBehavior!=null){
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
      //  }
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
    //metodo notificaciones
    private void  crear_notificacion(String titulo ,String mensaje) {

        NotificationCompat.Builder notificacion   =  new NotificationCompat.Builder(activity,"hola")
                .setSmallIcon(R.drawable.ic_home)
                .setContentTitle(titulo)
                .setContentText(mensaje)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(mensaje));

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(activity);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(000,notificacion.build());


    }

}
