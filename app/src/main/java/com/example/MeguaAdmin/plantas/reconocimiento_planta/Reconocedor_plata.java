package com.example.MeguaAdmin.plantas.reconocimiento_planta;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.MeguaAdmin.Login.Modelo_usuario;
import com.example.MeguaAdmin.herramientas.Manejador_notificaciones;
import com.example.MeguaAdmin.herramientas.My_aplicacion;
import com.example.MeguaAdmin.plantas.Modelo_planta;
import com.example.MeguaAdmin.R;
import com.example.MeguaAdmin.plantas.Adater_recycler_plantas;
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
    ArrayList<String> nombres_plantas=new ArrayList<>();
    FirebaseDatabase database ;
    DatabaseReference myRef ;
    Modelo_planta modelo_planta;
    ArrayList<Modelo_planta> plantas= new ArrayList<>();
    RecyclerView recyclerView;
    Boolean descargado = false;
    Leer2 activity;
    BottomSheetBehavior bottomSheetBehavior=null;
    Uri uri;
    Manejador_notificaciones   notificaciones;


    FirebaseAutoMLRemoteModel remoteModel = new FirebaseAutoMLRemoteModel.Builder("Flores_20201215142631").build();
    FirebaseModelManager modelManager= FirebaseModelManager.getInstance();


    public Reconocedor_plata(Leer2 activity, Uri uri) {
        this.recyclerView = activity.recyclerView;
        this.activity = activity;
        this.imagen_View =activity.imagen;
        this.bottomSheetBehavior = activity.bottomSheetBehavior;
        this.uri=uri;

        descargar_modelo();
        notificaciones= new Manejador_notificaciones(activity);
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
                       notificaciones.crear_notificacion("termino de descargar","termino de descargar");
                        FirebaseVisionOnDeviceAutoMLImageLabelerOptions optionsBuilder = new FirebaseVisionOnDeviceAutoMLImageLabelerOptions.Builder(remoteModel)
                                .setConfidenceThreshold(0.0f)  // Evaluate your model in the Firebase console
                                // to determine an appropriate value.
                                .build();

                        try {
                            // aca le pasamos el modelo al lebeler
                            Log.e("descargar modelo ","modelo descargado");
                            mio = FirebaseVision.getInstance().getOnDeviceAutoMLImageLabeler(optionsBuilder);
                            reconocer_imagen();
                        } catch (FirebaseMLException | IOException e) {
                            e.printStackTrace();
                        }

                    }
                });

        // aca miramos si esta descargado el modelo
        modelManager.isModelDownloaded(remoteModel).addOnSuccessListener(new OnSuccessListener<Boolean>() {
            @Override
            public void onSuccess(Boolean descargado) {
                if(descargado){
                Log.e(" si esta descargado","esta descargado ");
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
                    if(confidence>0.3){
                        nombres_plantas.add(labelText);

                    }
                }
                buscar_plantas();
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("prueba de label","Fallo");
                e.printStackTrace();
            }
        });



    }
    private void buscar_plantas() {
        database= FirebaseDatabase.getInstance();
        myRef= database.getReference();
        My_aplicacion my_aplicacion = (My_aplicacion)activity.getApplication();
        Modelo_planta planta= null;

        for (String nombre_cientifico:nombres_plantas) {
            planta=my_aplicacion.buscar_planta(nombre_cientifico);
            if(planta!=null){

                    plantas.add(planta);


            }


        }

        cargar_recycler_view();
    }



    private void cargar_recycler_view() {
        LinearLayoutManager manager = new LinearLayoutManager(activity);
        manager.setOrientation(RecyclerView.VERTICAL);
        Modelo_usuario usuario = ((My_aplicacion)activity.getApplication()).getUsuario();

        recyclerView.setLayoutManager(manager);
        //myRef.child("plantas").child(nombre_cientifico_planta);

        adater_recycler_plantas= new Adater_recycler_plantas(plantas, R.layout.layout_carta_planta,activity,usuario);

        recyclerView.setAdapter(adater_recycler_plantas);
        //if(bottomSheetBehavior!=null){
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        activity.container_progress_bar.setVisibility(View.GONE);


      //  }
    }

    //metodo notificaciones


}
