package com.example.MeguaPlantsAdmin.plantas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.MeguaPlantsAdmin.Camara_manager;
import com.example.MeguaPlantsAdmin.Modelo_planta;
import com.example.MeguaPlantsAdmin.New_plant_manager;
import com.example.MeguaPlantsAdmin.R;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class New_plant extends AppCompatActivity {
    private static final int CONSTANTE_FOTO_IMAGEN_MUESTRA_FOTO1 = 1,CONSTANTE_FOTO_IMAGEN_MUESTRA_FOTO2 = 2,CONSTANTE_FOTO_IMAGEN_MUESTRA_FOTO3 = 3, CONSTANTE_FOTO_IMAGEN_MODELO=4,CONSTANTE_IMAGEN_ESCOGIDA_MODELO=5,CONSTANTE_IMAGEN_ESCOGIDA_MUESTRA_1=6,CONSTANTE_IMAGEN_ESCOGIDA_MUESTRA_2=7,CONSTANTE_IMAGEN_ESCOGIDA_MUESTRA_3=8;
    View.OnClickListener onClick_agregar;
    ImageButton btn_imangen_muestra_1,btn_imangen_muestra_2,btn_imangen_muestra_3,btn_imagen_modelo;
    String[] opciones_imagenes= {"Escoger galeria ","Tomar una foto"};
    ImageView imagen_muestra_1,imagen_muestra_2,imagen_muestra_3,imagen_modelo;
    String ruta_obsoluta_imagen_modelo,ruta_obsoluta_imagen_muestra_1,ruta_obsoluta_imagen_muestra_2,ruta_obsoluta_imagen_muestra_3,ruta_obsoluta;
    Button btn_postar;
    EditText txt_nombre,txt_nombre_cientifico,txt_descripcion,txt_caracteristicas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_plant);
        inicializar();

    }

    private void inicializar() {
        showToolbar("Home",true);
        btn_imangen_muestra_1= findViewById(R.id.btn_agregar_imagen1);
        btn_imangen_muestra_2= findViewById(R.id.btn_agregar_imagen2);
        btn_imangen_muestra_3= findViewById(R.id.btn_agregar_imagen3);
        btn_imagen_modelo= findViewById(R.id.btn_agregar_imagen_modelo);
        imagen_muestra_1=findViewById(R.id.imagen_muestra_1);
        imagen_muestra_2=findViewById(R.id.imagen_muestra_2);
        imagen_muestra_3=findViewById(R.id.imagen_muestra_3);
        imagen_modelo=findViewById(R.id.imagen_modelo);
        btn_postar= findViewById(R.id.postear);
        txt_nombre=findViewById(R.id.new_plant_nombre);
        txt_nombre_cientifico=findViewById(R.id.new_plant_nombre_cientifico);
        txt_caracteristicas=findViewById(R.id.new_plant_caractersiticas);
        txt_descripcion=findViewById(R.id.new_plant_descripcion);

        btn_postar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre_cienteifico=txt_nombre_cientifico.getText().toString();
                String nombre=txt_nombre.getText().toString();
                String caracteristicas=txt_caracteristicas.getText().toString();
                String descripcion=txt_descripcion.getText().toString();

                Modelo_planta modelo_planta= new Modelo_planta(nombre,nombre_cienteifico,caracteristicas,descripcion);
                New_plant_manager new_plant_manager= new New_plant_manager(modelo_planta,imagen_modelo,imagen_muestra_1,imagen_muestra_2,imagen_muestra_3,New_plant.this);
            }
        });
        onClick_agregar= new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Crear_dialogo_escoger_imagen(view.getId()).show();
            }
        };
        btn_imangen_muestra_1.setOnClickListener(onClick_agregar);
        btn_imangen_muestra_2.setOnClickListener(onClick_agregar);
        btn_imangen_muestra_3.setOnClickListener(onClick_agregar);
        btn_imagen_modelo.setOnClickListener(onClick_agregar);
    }


    public Dialog Crear_dialogo_escoger_imagen( final int  id_boton ) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this );
        builder.setTitle("escoger foto ").setItems(opciones_imagenes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i == 0){
                    seleccionar_imagen(id_boton);
                }else{

                    tomar_foto_1(id_boton);

                }
            }
        });
        return  builder.create();
    }

    private void seleccionar_imagen(int id_boton) {
        Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        switch (id_boton){

            case R.id.btn_agregar_imagen_modelo:
                startActivityForResult(intent.createChooser(intent,"Selecione aplicacion"), CONSTANTE_IMAGEN_ESCOGIDA_MODELO);
                break;
            case R.id.btn_agregar_imagen1:
                startActivityForResult(intent.createChooser(intent,"Selecione aplicacion"), CONSTANTE_IMAGEN_ESCOGIDA_MUESTRA_1);
                break;

            case R.id.btn_agregar_imagen2:
                startActivityForResult(intent.createChooser(intent,"Selecione aplicacion"), CONSTANTE_IMAGEN_ESCOGIDA_MUESTRA_2);
                break;
            case R.id.btn_agregar_imagen3:
                startActivityForResult(intent.createChooser(intent,"Selecione aplicacion"), CONSTANTE_IMAGEN_ESCOGIDA_MUESTRA_3);
                break;
            }


    }

    private void tomar_foto_1(int  id_boton) {
        switch (id_boton){
            case R.id.btn_agregar_imagen1:
                Toast.makeText(getBaseContext(), "escoger foto imagen 1",Toast.LENGTH_LONG).show();
                tomar_foto(CONSTANTE_FOTO_IMAGEN_MUESTRA_FOTO1);
                break;
            case R.id.btn_agregar_imagen2:
                tomar_foto(CONSTANTE_FOTO_IMAGEN_MUESTRA_FOTO2);
                Toast.makeText(getBaseContext(), "escoger foto imagen 2",Toast.LENGTH_LONG).show();
                break;
            case  R.id.btn_agregar_imagen3:
                tomar_foto(CONSTANTE_FOTO_IMAGEN_MUESTRA_FOTO3);
                Toast.makeText(getBaseContext(), "escoger foto imagen 3",Toast.LENGTH_LONG).show();
                break;
            case  R.id.btn_agregar_imagen_modelo:
                tomar_foto(CONSTANTE_FOTO_IMAGEN_MODELO);
                break;
        }



    }



    public void tomar_foto(int constante_imagen){
        // prende la camara
        File archivo_foto= null ;
        Intent intent_tomar_foto= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent_tomar_foto.resolveActivity(getPackageManager())!=null){

            try {
                archivo_foto= crear_archivo_foto();


            }catch (Exception e ){
                e.printStackTrace();
            }
        }else{

            Toast.makeText(getBaseContext(),"dio null",Toast.LENGTH_LONG);

        }

        if(archivo_foto!=null ){
            Uri url_foto = FileProvider.getUriForFile(getBaseContext(),"com.example.MeguaPlantsAdmin",archivo_foto);
            Log.d("Home_fragment","photo_uri:"+ url_foto);
            intent_tomar_foto.putExtra(MediaStore.EXTRA_OUTPUT,url_foto);


            startActivityForResult(intent_tomar_foto,constante_imagen);
        }

    }



    private File crear_archivo_foto() throws IOException {

        String time_stamp=new SimpleDateFormat("yyyyMMdd_HH-mm-ss").format(new Date());

        String nombre_imagen="JPEG_"+time_stamp;


        File storage_dir=getExternalFilesDir(Environment.DIRECTORY_PICTURES);



        File archivo_foto= File.createTempFile(nombre_imagen,".jpg",storage_dir);



        Toast.makeText(getBaseContext(),storage_dir.toString(),Toast.LENGTH_LONG);
        ruta_obsoluta=archivo_foto.getAbsolutePath();

        return  archivo_foto;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        Uri uri;
        if ( resultCode == RESULT_OK) {
            switch (requestCode){
            case CONSTANTE_FOTO_IMAGEN_MUESTRA_FOTO1 :

                Glide.with(this).load(ruta_obsoluta).into(imagen_muestra_1);


                ruta_obsoluta_imagen_muestra_1=ruta_obsoluta;


                break;
            case CONSTANTE_FOTO_IMAGEN_MUESTRA_FOTO2 :
                Glide.with(this).load(ruta_obsoluta).into(imagen_muestra_2);
                ruta_obsoluta_imagen_muestra_2=ruta_obsoluta;

                break;

               case CONSTANTE_FOTO_IMAGEN_MUESTRA_FOTO3 :
                   Glide.with(this).load(ruta_obsoluta).into(imagen_muestra_3);
                   ruta_obsoluta_imagen_muestra_3=ruta_obsoluta;

               break;

                case CONSTANTE_FOTO_IMAGEN_MODELO:
                    Glide.with(this).load(ruta_obsoluta).into(imagen_modelo);
                    ruta_obsoluta_imagen_modelo=ruta_obsoluta;
                  break;

                case CONSTANTE_IMAGEN_ESCOGIDA_MODELO:
                     uri= data.getData();

                    Glide.with(this).load(uri).into(imagen_modelo);

                    break;
                case CONSTANTE_IMAGEN_ESCOGIDA_MUESTRA_1:
                     uri= data.getData();

                    Glide.with(this).load(uri).into(imagen_muestra_1);

                    break;
                case CONSTANTE_IMAGEN_ESCOGIDA_MUESTRA_2:
                    uri= data.getData();

                    Glide.with(this).load(uri).into(imagen_muestra_2);

                    break;

                case CONSTANTE_IMAGEN_ESCOGIDA_MUESTRA_3:
                    uri= data.getData();

                    Glide.with(this).load(uri).into(imagen_muestra_3);

                    break;


            }





        }
    }


    public void showToolbar(String tittle,boolean upButton ){
        //  aca declaramos una variable toolbar y traemos el tooblar de view
        Toolbar toolbar= findViewById(R.id.toolbar);


        //  aca enviamos el soporte el toolbar para asi personalizarlo
        setSupportActionBar(toolbar);

        //se le pone el titulo
        getSupportActionBar().setTitle(tittle);

        //se le pone el boton de regreso (hay que configurar la jerarquia )
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }




}