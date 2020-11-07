package com.example.MeguaPlantsAdmin.plantas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
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
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.MeguaPlantsAdmin.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Leer2 extends AppCompatActivity {
    ImageView imagen;
   Bitmap bitmap;
    BottomSheetBehavior bottomSheetBehavior;
    Button colapsar,expandir;
    Reconocedor_plata reconocedor_plata;
    RecyclerView recyclerView;
    Uri uri;
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

      //  if(bitmap!=null) {
           // reconocedor_plata = new Reconocedor_plata(recyclerView, this, bottomSheetBehavior,uri,imagen);
        //  }else{
        //  reconocedor_plata = new Reconocedor_plata(recyclerView, this, bottomSheetBehavior,uri);
        //}

        createNotificationChannel();
    }

    private void inicializar() {
        //inicializar el botton sheet
        View bottom_sheet= findViewById(R.id.bottom_sheet_resultados);
        bottomSheetBehavior= BottomSheetBehavior.from(bottom_sheet);
        colapsar= findViewById(R.id.colapsar);
        expandir= findViewById(R.id.expandir);
        colapsar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
        expandir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
       recyclerView = findViewById(R.id.recycler_picture_leer);

        imagen= findViewById(R.id.img_reconocer2);

        createNotificationChannel();

    }

    private void cargar_imagen() throws IOException {
        if(getIntent().hasExtra("ruta_imagen")){
            String url= getIntent().getExtras().getString("ruta_imagen");
             uri = Uri.parse(url);
          //  bitmap=null;//BitmapFactory.decodeFile(url);

            Glide.with(this).load(uri).into(imagen);
        }else{

            if (getIntent().hasExtra("uri")){

                String uri_string = getIntent().getExtras().getString("uri");
                uri=Uri.parse(uri_string);

                Log.e("leer",uri.toString());
                //esto para comverti a bitmap

                //bitmap= MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                //con esto de volta la imagen con las fotos que se toma con la camara
                //ByteArrayOutputStream baos= new ByteArrayOutputStream();

                //bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
                // Glide.with(this).load(bitmap).into(imagen);
                Glide.with(this).load(uri).addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        reconocedor_plata = new Reconocedor_plata(recyclerView, Leer2.this, bottomSheetBehavior,uri,imagen);
                        return false;
                    }
                }).into(imagen);




            }else{
                Log.e("leer","no llego el extra");
            }
        }
        //imagen.setLayoutParams(new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.WRAP_CONTENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT));


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