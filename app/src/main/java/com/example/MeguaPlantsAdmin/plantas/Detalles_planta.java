package com.example.MeguaPlantsAdmin.plantas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.MeguaPlantsAdmin.Modelo_planta;
import com.example.MeguaPlantsAdmin.R;
import com.example.MeguaPlantsAdmin.pruebas.View_pager_adapter;
import com.example.MeguaPlantsAdmin.pruebas.Viewpager_prueba;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Detalles_planta extends AppCompatActivity {
    private ImageView imagen_modelo,imagen_muestra_modelo, imagen_muestra_1,imagen_muestra_2,imagen_muestra_3;
    Modelo_planta modelo_planta;
    ProgressBar progressbar_imagen_muestra_modelo,progressbar_imagen_muestra_1,progressbar_imagen_muestra_2,progressbar_imagen_muestra_3;
    View.OnClickListener onClickListener;
    CardView cardView_modelo,cardView_muestra1,cardView_muestra2,cardView_muestra3;
    TextView nombre_planta,descripcion_planta ;
    private static final int CONSTANTE_TOMAR_FOTO =1 ;
    private static final int CONSTANTE_ESCOGER_IMAGEN =2 ;
    String[] opciones_imagenes= {"Escoger galeria ","Tomar una foto"};
    String ruta_obsoluta;
    File archivo_foto= null ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_planta);
        imagen_modelo= findViewById(R.id.datelles_imagen_modelo);
         modelo_planta = getIntent().getParcelableExtra("planta");


      Glide.with(this).load(modelo_planta.getLink_imagen_modelo()).into(imagen_modelo);
        showToolbar(modelo_planta.getNombre(),true);
        inicializar();
        mostrar_imagenes_plantas();

    }



    private void inicializar() {
        nombre_planta= findViewById(R.id.nombre_planta);
        descripcion_planta= findViewById(R.id.descripcion_planta);
        imagen_muestra_modelo= findViewById(R.id.imagen_muestra_modelo);
        imagen_muestra_1= findViewById(R.id.imagen_muestra_1);
        imagen_muestra_2= findViewById(R.id.imagen_muestra_2);
        imagen_muestra_3= findViewById(R.id.imagen_muestra_3);
        progressbar_imagen_muestra_modelo= findViewById(R.id.progressBar_imagen_muestra_modelo);
        progressbar_imagen_muestra_1= findViewById(R.id.progressBar_imagen_muestra_1);
        progressbar_imagen_muestra_2= findViewById(R.id.progressBar_imagen_muestra_2);
        progressbar_imagen_muestra_3= findViewById(R.id.progressBar_imagen_muestra_3);
        cardView_muestra1= findViewById(R.id.card_view_muestra_1);
        cardView_muestra1= findViewById(R.id.card_view_muestra_2);
        cardView_muestra1= findViewById(R.id.card_view_muestra_3);
        cardView_modelo= findViewById(R.id.card_view_muestra_modelo);
        onClickListener= new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Detalles_planta.this,Viewpager_prueba.class);
                int pos =0 ;
                switch (view.getId()){
                    case R.id.imagen_muestra_modelo:
                        pos= 0 ;
                        break;
                    case R.id.imagen_muestra_1:
                        pos= 1 ;
                        break;
                    case R.id.imagen_muestra_2:
                        pos= 2 ;
                        break;
                    case R.id.imagen_muestra_3:
                        pos= 3 ;
                        break;
                }
                i.putExtra("pos",pos);

                i.putExtra("planta",modelo_planta);
                startActivity(i);
            }
        };
        imagen_muestra_modelo.setOnClickListener(onClickListener);
        imagen_muestra_1.setOnClickListener(onClickListener);
        imagen_muestra_2.setOnClickListener(onClickListener);
        imagen_muestra_3.setOnClickListener(onClickListener);
        nombre_planta.setText(modelo_planta.getNombre());
        descripcion_planta.setText(modelo_planta.getDescripcion());


    }

    private void mostrar_imagenes_plantas() {
        mostrar_planta(imagen_muestra_modelo,modelo_planta.getLink_imagen_modelo(),progressbar_imagen_muestra_modelo);
        mostrar_planta(imagen_muestra_1,modelo_planta.getLink_imagen_muestra_1(),progressbar_imagen_muestra_1);
        mostrar_planta(imagen_muestra_2,modelo_planta.getLink_imagen_muestra_2(),progressbar_imagen_muestra_2);
        mostrar_planta(imagen_muestra_3,modelo_planta.getLink_imagen_muestra_3(),progressbar_imagen_muestra_3);
    }

    private void mostrar_planta(ImageView imagen, String link, final ProgressBar progressBar) {
        Glide.with(this).load(link).addListener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                progressBar.setVisibility(View.INVISIBLE);
                return false;
            }
        }).into(imagen);
    }

    public void showToolbar(String tittle,boolean upButton){
        //  aca declaramos una variable toolbar y traemos el tooblar de view
       Toolbar toolbar = findViewById(R.id.toolbar_detalles);

        //  aca enviamos el soporte el toolbar para asi personalizarlo
            setSupportActionBar(toolbar);

        //se le pone el titulo


        //se le pone el boton de regreso (hay que configurar la jerarquia )
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

        toolbar.inflateMenu(R.menu.menu_tolbar);
        toolbar.setTitle(modelo_planta.getNombre());


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_tolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.reconocer_tolbar:
                Dialog dialogo = crear_dialogo_escoger_imagen();
                dialogo.show();
                return true;

        }
        return false;
    }

    public Dialog crear_dialogo_escoger_imagen() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this );
        builder.setTitle("escoger foto ").setItems(opciones_imagenes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i == 0){
                    seleccionar_imagen();
                }else{

                    tomar_foto_1();

                }
            }
        });
        return  builder.create();
    }



    private void seleccionar_imagen() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Selecione aplicacion"), CONSTANTE_ESCOGER_IMAGEN);

    }

    private void tomar_foto_1() {

        tomar_foto();

    }






    public void tomar_foto(){
        // prende la camara
        archivo_foto= null ;
        Intent intent_tomar_foto= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent_tomar_foto.resolveActivity(getPackageManager())!=null){

            try {
                archivo_foto= crear_archivo_foto();


            }catch (Exception e ){
                e.printStackTrace();
            }
        }else{

            Toast.makeText(this,"dio null",Toast.LENGTH_LONG);

        }

        if(archivo_foto!=null ){
            Uri url_foto = FileProvider.getUriForFile(this,"com.example.MeguaPlantsAdmin",archivo_foto);
            Log.d("Home_fragment","photo_uri:"+ url_foto);
            intent_tomar_foto.putExtra(MediaStore.EXTRA_OUTPUT,url_foto);


            startActivityForResult(intent_tomar_foto,CONSTANTE_TOMAR_FOTO);
        }

    }



    private File crear_archivo_foto() throws IOException {

        String time_stamp=new SimpleDateFormat("yyyyMMdd_HH-mm-ss").format(new Date());

        String nombre_imagen="JPEG_"+time_stamp;


        File storage_dir=getExternalFilesDir(Environment.DIRECTORY_PICTURES);



        File archivo_foto= File.createTempFile(nombre_imagen,".jpg",storage_dir);



        Toast.makeText(this,storage_dir.toString(),Toast.LENGTH_LONG);
        ruta_obsoluta=archivo_foto.getAbsolutePath();

        return  archivo_foto;
    }





    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        Uri uri;
        Intent leer2= new Intent(this, Leer2.class);
        if ( resultCode == RESULT_OK) {

            switch (requestCode){
                case CONSTANTE_ESCOGER_IMAGEN:
                    uri= data.getData();
                    leer2.putExtra("uri",uri.toString());
                    startActivity(leer2);
                    break;

                case CONSTANTE_TOMAR_FOTO:
                    uri= Uri.fromFile(archivo_foto);
                    leer2.putExtra("uri",uri.toString());
                    startActivity(leer2);
                    // asi estaba

                    //leer2.putExtra("ruta_imagen",ruta_obsoluta);
                    //startActivity(leer2);

                    break;
            }





        }
    }

}