package com.example.MeguaPlantsAdmin.plantas;

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
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.MeguaPlantsAdmin.Modelo_planta;
import com.example.MeguaPlantsAdmin.R;
import com.example.MeguaPlantsAdmin.plantas.Adater_recycler_plantas;
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
    Reconocedor_plata reconocedor_plata;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leer);
        inicializar();
        try {
            cargar_imagen();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(bitmap!=null) {
           // reconocedor_plata = new Reconocedor_plata(recyclerView, this, bitmap);
        }else{
           // reconocedor_plata = new Reconocedor_plata(recyclerView, this, imagen);
        }
    }
    private void inicializar() {
        recyclerView = findViewById(R.id.recycler_picture_leer);

        imagen= findViewById(R.id.img_reconocer);

        createNotificationChannel();

    }

    private void cargar_imagen() throws IOException {
        if(getIntent().hasExtra("ruta_imagen")){
            String url= getIntent().getExtras().getString("ruta_imagen");
             bitmap=null;//BitmapFactory.decodeFile(url);

            Glide.with(this).load(url).into(imagen);
        }else{

            if (getIntent().hasExtra("uri")){

                String uri_string = getIntent().getExtras().getString("uri");
                Uri uri=Uri.parse(uri_string);

                Log.e("leer",uri.toString());
                //esto para comverti a bitmap
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                //Glide.with(this).load(bitmap).into(imagen);

                Glide.with(this).load(uri).into(imagen);



            }else{
                Log.e("leer","no llego el extra");
            }
        }

    }






    //metodo local

    //metodo local

        // metodo local
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode==CONSTANTE_IMAGEN_ESCOGIDA_MODELO){
          Uri uri= data.getData();

            Glide.with(this).load(uri).into(imagen);

        }
    }
            //metodo notificaciones
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
    //metodo notificaciones

}