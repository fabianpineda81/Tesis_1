package com.example.MeguaPlantsAdmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.common.FirebaseMLException;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.automl.FirebaseAutoMLRemoteModel;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;
import com.google.firebase.ml.vision.label.FirebaseVisionOnDeviceAutoMLImageLabelerOptions;

import java.util.List;

public class Leer extends AppCompatActivity {
    private static final int CONSTANTE_IMAGEN_ESCOGIDA_MODELO =1 ;
    Button btn_agregrar,btn_reconocer;
ImageView imagen;
    FirebaseVisionImage image;
    FirebaseVisionImageLabeler labeler = FirebaseVision.getInstance().getCloudImageLabeler();
    FirebaseVisionImageLabeler mio;
    FirebaseAutoMLRemoteModel remoteModel = new FirebaseAutoMLRemoteModel.Builder("Flores").build();
    FirebaseModelManager modelManager= FirebaseModelManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leer);
        inicializar();
    }

    private void inicializar() {
        btn_agregrar= findViewById(R.id.btn_agrear_leer);
        btn_reconocer= findViewById(R.id.btn_reconocer);
        imagen= findViewById(R.id.img_reconocer);

        btn_agregrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent.createChooser(intent,"Selecione aplicacion"), CONSTANTE_IMAGEN_ESCOGIDA_MODELO);
            }
        });


        btn_reconocer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagen.setDrawingCacheEnabled(true);
                imagen.buildDrawingCache();
                Bitmap bitmap= imagen.getDrawingCache();
                image = FirebaseVisionImage.fromBitmap(bitmap);

                mio.processImage(image).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
                    @Override
                    public void onSuccess(List<FirebaseVisionImageLabel> labels) {
                        for (FirebaseVisionImageLabel label: labels) {
                            String text = label.getText();
                            String entityId = label.getEntityId();
                            float confidence = label.getConfidence();
                            Log.e("prueba de label","etiqueta: "+text+"  fialidad"+confidence);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("prueba de label","Fallo");
                        e.printStackTrace();
                    }
                });
                //imagen.setDrawingCacheEnabled(false);

            }
        });

        createNotificationChannel();

        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder()
                .requireWifi()
                .build();


       modelManager.download(remoteModel, conditions)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        crear_notificacion("termino de descargar","termino de descargar");
                    }
                });


       modelManager.isModelDownloaded(remoteModel).addOnSuccessListener(new OnSuccessListener<Boolean>() {
           @Override
           public void onSuccess(Boolean descargado) {
                 if(descargado){
                     FirebaseVisionOnDeviceAutoMLImageLabelerOptions optionsBuilder = new FirebaseVisionOnDeviceAutoMLImageLabelerOptions.Builder(remoteModel)
                             .setConfidenceThreshold(0.0f)  // Evaluate your model in the Firebase console
                             // to determine an appropriate value.
                             .build();
                     try {
                         mio = FirebaseVision.getInstance().getOnDeviceAutoMLImageLabeler(optionsBuilder);
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