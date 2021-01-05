package com.example.MeguaAdmin.plantas.reconocimiento_planta;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.MeguaAdmin.Home.Instrucciones_identificar;
import com.example.MeguaAdmin.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.io.IOException;

public class Leer2 extends AppCompatActivity {
   public ImageView imagen;

  public   BottomSheetBehavior bottomSheetBehavior;
    public  LinearLayout container_progress_bar;
   public Reconocedor_plata reconocedor_plata;
    RecyclerView recyclerView;
    Uri uri;
    CardView planta_no_encontrada;
    private static final int CONSTANTE_IMAGEN_ESCOGIDA_MODELO =1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leer2);
        inicializar();
        try {
            cargar_imagen();
        } catch (IOException e) {
            e.printStackTrace();
        }


        createNotificationChannel();
    }

    private void inicializar() {

        View bottom_sheet= findViewById(R.id.bottom_sheet_resultados);
        bottomSheetBehavior= BottomSheetBehavior.from(bottom_sheet);
        container_progress_bar= findViewById(R.id.progressBar_container_reconocimiento);
        planta_no_encontrada= findViewById(R.id.card_platna_no_encontrada);
        planta_no_encontrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Leer2.this, Instrucciones_identificar.class);
                startActivity(i);
            }
        });


       recyclerView = findViewById(R.id.recycler_picture_leer);

        imagen= findViewById(R.id.img_reconocer2);

        createNotificationChannel();

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if(newState ==BottomSheetBehavior.STATE_HIDDEN){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    private void cargar_imagen() throws IOException {


            if (getIntent().hasExtra("uri")){
                String uri_string = getIntent().getExtras().getString("uri");
                uri=Uri.parse(uri_string);

                Glide.with(this).load(uri).addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        reconocedor_plata = new Reconocedor_plata(Leer2.this,uri);
                        return false;
                    }
                }).into(imagen);


            }else{
                Log.e("leer","no llego el extra");
            }
        }




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
}