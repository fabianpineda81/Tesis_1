package com.example.MeguaPlantsAdmin.plantas.reconocimiento_planta;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.MeguaPlantsAdmin.R;

import java.io.IOException;

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