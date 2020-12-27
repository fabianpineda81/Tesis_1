package com.example.MeguaAdmin.plantas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.MeguaAdmin.R;
import com.example.MeguaAdmin.herramientas.Botton_sheet_configuracion_plant;
import com.example.MeguaAdmin.herramientas.Constantes;
import com.example.MeguaAdmin.herramientas.Manejador_Glide;
import com.example.MeguaAdmin.herramientas.Manejador_camara;
import com.example.MeguaAdmin.herramientas.Manejador_dialogos;
import com.example.MeguaAdmin.herramientas.Manejador_seleccionador_imagenes;
import com.example.MeguaAdmin.herramientas.Manejador_toolbar;
import com.example.MeguaAdmin.imagen_pantalla_completa.Viewpager_imagen_completa;
import com.example.MeguaAdmin.plantas.reconocimiento_planta.Leer2;

public class Detalles_planta extends AppCompatActivity {
    private ImageView imagen_modelo,imagen_muestra_4, imagen_muestra_1,imagen_muestra_2,imagen_muestra_3;
    Modelo_planta modelo_planta;
    ProgressBar progressbar_imagen_muestra_4,progressbar_imagen_muestra_1,progressbar_imagen_muestra_2,progressbar_imagen_muestra_3;
    View.OnClickListener onclick_imagen_pantalla_completatener;
    CardView cardView_modelo,cardView_muestra1,cardView_muestra2,cardView_muestra3;



    Manejador_dialogos  manejador_dialogos= new Manejador_dialogos();
    String[] opciones_imagenes= {"Escoger galeria ","Tomar una foto"};


    Manejador_camara manejador_camara;
    Manejador_seleccionador_imagenes manejador_seleccionador_imagenes;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_planta);
        imagen_modelo= findViewById(R.id.datelles_imagen_modelo);
         modelo_planta = getIntent().getParcelableExtra("planta");

        Manejador_Glide.Montar_imagen(this,imagen_modelo,modelo_planta.getLink_imagen_modelo());

        inicializar();

        mostrar_imagenes_plantas();
        mostrar_informacion_planta();

    }

    private void mostrar_informacion_planta() {
        TextView nombre_planta,descripcion_planta,datos_interes,nombre_cientifico,familia,genero,tipo_planta,altura,diametro_copa,diametro_flor,floracion,epoca ;
        nombre_planta= findViewById(R.id.nombre_planta);
        descripcion_planta= findViewById(R.id.descripcion_planta);
        datos_interes=findViewById(R.id.datos_inteneres_planta);
        nombre_cientifico=findViewById(R.id.nombre_cientifico_planta);
        familia=findViewById(R.id.familia_planta);
        genero=findViewById(R.id.genero_planta);
        tipo_planta=findViewById(R.id.tipo_planta_planta);
        altura=findViewById(R.id.altura_planta);
        diametro_copa=findViewById(R.id.diametro_copa_planta);
        diametro_flor=findViewById(R.id.diametro_flor_planta);
        floracion=findViewById(R.id.floracion_planta);
        epoca=findViewById(R.id.epoca_planta);
        nombre_planta.setText(modelo_planta.getNombre());
        descripcion_planta.setText(modelo_planta.getDescripcion());
        datos_interes.setText(modelo_planta.getDatos_interes());
        nombre_cientifico.setText(modelo_planta.getNombre_cientifico());
        familia.setText(modelo_planta.getFamilia());
        genero.setText(modelo_planta.getGenero());
        tipo_planta.setText(modelo_planta.getTipo_planta());
        altura.setText(modelo_planta.getAltura());
        diametro_copa.setText(modelo_planta.getDiametro_copa());
        diametro_flor.setText(modelo_planta.getDiametro_flor());
        floracion.setText(modelo_planta.getFloracion());
        epoca.setText(modelo_planta.getEpoca());
    }


    private void mostrar_imagenes_plantas() {
        Manejador_Glide.Montar_imagen_progressbar(this,modelo_planta.getLink_imagen_muestra_4(),imagen_muestra_4,progressbar_imagen_muestra_4);
        Manejador_Glide.Montar_imagen_progressbar(this,modelo_planta.getLink_imagen_muestra_1(),imagen_muestra_1,progressbar_imagen_muestra_1);
        Manejador_Glide.Montar_imagen_progressbar(this,modelo_planta.getLink_imagen_muestra_2(),imagen_muestra_2,progressbar_imagen_muestra_2);
        Manejador_Glide.Montar_imagen_progressbar(this,modelo_planta.getLink_imagen_muestra_3(),imagen_muestra_3,progressbar_imagen_muestra_3);
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

                manejador_dialogos= new Manejador_dialogos(this,R.layout.boottom_sheet_escoger_imagenest);
                manejador_dialogos.show(getSupportFragmentManager(),"home");
                return true;

            case R.id.configuracion_tolbar:
                Botton_sheet_configuracion_plant configuracion_plant=new Botton_sheet_configuracion_plant(modelo_planta,this);
                configuracion_plant.show(getSupportFragmentManager(),"home  ");
                return true;

        }
        return false;
    }



    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        Uri uri;
        Intent leer2= new Intent(this, Leer2.class);
        if ( resultCode == RESULT_OK) {

            switch (requestCode){
                case Constantes.CONSTANTE_ESCOGER_IMAGEN:
                    uri= data.getData();
                    leer2.putExtra("uri",uri.toString());
                    startActivity(leer2);
                    break;

                case Constantes.CONSTANTE_TOMAR_FOTO:
                    uri= Uri.fromFile(manejador_dialogos.getFoto());
                    leer2.putExtra("uri",uri.toString());
                    startActivity(leer2);
                    // asi estaba

                    //leer2.putExtra("ruta_imagen",ruta_obsoluta);
                    //startActivity(leer2);

                    break;
            }





        }
    }

    private void inicializar() {
        toolbar = findViewById(R.id.toolbar_detalles);
        manejador_camara= new Manejador_camara();
        manejador_seleccionador_imagenes= new Manejador_seleccionador_imagenes(this);

        imagen_muestra_4= findViewById(R.id.imagen_muestra_4);
        imagen_muestra_1= findViewById(R.id.imagen_muestra_1);
        imagen_muestra_2= findViewById(R.id.imagen_muestra_2);
        imagen_muestra_3= findViewById(R.id.imagen_muestra_3);
        progressbar_imagen_muestra_4= findViewById(R.id.progressBar_imagen_muestra_4);
        progressbar_imagen_muestra_1= findViewById(R.id.progressBar_imagen_muestra_1);
        progressbar_imagen_muestra_2= findViewById(R.id.progressBar_imagen_muestra_2);
        progressbar_imagen_muestra_3= findViewById(R.id.progressBar_imagen_muestra_3);
        cardView_muestra1= findViewById(R.id.card_view_muestra_1);
        cardView_muestra1= findViewById(R.id.card_view_muestra_2);
        cardView_muestra1= findViewById(R.id.card_view_muestra_3);
        cardView_modelo= findViewById(R.id.card_view_muestra_modelo);

        onclick_imagen_pantalla_completatener= new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Detalles_planta.this, Viewpager_imagen_completa.class);
                int pos =0 ;
                switch (view.getId()){
                    case R.id.imagen_muestra_4:
                        pos= 3 ;
                        break;
                    case R.id.imagen_muestra_1:
                        pos= 0 ;
                        break;
                    case R.id.imagen_muestra_2:
                        pos= 1 ;
                        break;
                    case R.id.imagen_muestra_3:
                        pos= 2 ;
                        break;
                }
                i.putExtra("pos",pos);

                i.putExtra("planta",modelo_planta);
                startActivity(i);
            }
        };
        imagen_muestra_4.setOnClickListener(onclick_imagen_pantalla_completatener);
        imagen_muestra_1.setOnClickListener(onclick_imagen_pantalla_completatener);
        imagen_muestra_2.setOnClickListener(onclick_imagen_pantalla_completatener);
        imagen_muestra_3.setOnClickListener(onclick_imagen_pantalla_completatener);

        Manejador_toolbar.showToolbar(modelo_planta.getNombre(),true,this,toolbar);

    }


}