package com.example.MeguaAdmin.plantas.new_plant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.MeguaAdmin.herramientas.Constantes;
import com.example.MeguaAdmin.herramientas.Manejador_permisos;
import com.example.MeguaAdmin.herramientas.Manejador_toolbar;
import com.example.MeguaAdmin.R;
import com.example.MeguaAdmin.herramientas.Modelo_uri;
import com.example.MeguaAdmin.plantas.Modelo_planta;


import java.util.ArrayList;

public class New_plant_container extends AppCompatActivity {

    View.OnClickListener onClick_agregar;

    ImageView imagen_muestra_1,imagen_muestra_2,imagen_muestra_3,imagen_modelo;
   public Button btn_postar,btn_siguiente,btn_atras;
    EditText txt_nombre,txt_nombre_cientifico,txt_descripcion,txt_caracteristicas;



    Manejador_permisos manejador_permisos;
    New_plant_manager new_plant_manager ;
    Toolbar toolbar;
    Datos_generales datos_generales = new Datos_generales();
    Caractaresticas caractaresticas = new Caractaresticas();
    public  Imagenes imagenes = new Imagenes();
   public Modelo_planta planta;


    private ArrayList<New_plant_fragment> fragmentos = new ArrayList<>();
    private View.OnClickListener siguiete_fragemento;
    private View.OnClickListener montar_planta;
    private  View.OnClickListener modificar_planta;
    boolean postear= false;

    int pos=0;
    int limite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_plant);
         planta = getIntent().getParcelableExtra("planta");
        inicializar();




        Manejador_toolbar.showToolbar("Home",true,this,toolbar);


    }


    public  void  cambiar_fragmento(boolean avanzar) {

        if(avanzar){
            if(pos+1<=limite){
                siguiente_framento();

            }
        }else{
            if(pos-1>=0){
                anterior_framento();

            }
        }


    }

    private void cambiar_funcion_siguiente() {

        if(postear){
            btn_siguiente.setOnClickListener(siguiete_fragemento);
            btn_siguiente.setText("Siguiente");
        }

    }

    private void cambiar_funcion_postear() {
        if(pos+1==limite){
            if(planta!=null){
                btn_siguiente.setText("Modificar");
                btn_siguiente.setOnClickListener(modificar_planta);
            }else{
                btn_siguiente.setOnClickListener(montar_planta);
                btn_siguiente.setText("Terminar");
            }

            postear=true;

        }


    }

    public  void  siguiente_framento(){
         FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.fade_out);
        New_plant_fragment fragment_actual = fragmentos.get(pos);
        New_plant_fragment fragment_siguente= fragmentos.get(pos+1);
        if(fragment_actual.verificar_campos()){
            if (fragment_siguente.isAdded()) {
                Log.e(" agregado",""+fragment_siguente.isAdded());
                transaction
                        .hide(fragment_actual)
                        .show(fragment_siguente);
            } else {
                Log.e(" agregado",""+fragment_siguente.isAdded());
                transaction
                        .hide(fragment_actual)
                        .add(R.id.new_plant_container_layout, fragment_siguente,"fragmento"+pos);
            }

            transaction.addToBackStack(null).commit();
            pos++;
            cambiar_funcion_postear();
        }



    }

    public  void anterior_framento(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.slide_out);
        Fragment fragment_actual = fragmentos.get(pos);
        Fragment fragment_anterior= fragmentos.get(pos-1);
        transaction.hide(fragment_actual).show(fragment_anterior).addToBackStack(null).commit();
        pos--;
        cambiar_funcion_siguiente();

    }
    public  void agregar_fragmento(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.fade_out);
        transaction.add(R.id.new_plant_container_layout,fragmentos.get(0),"fragmento1").commit();
    }








    private void inicializar() {

        manejador_permisos= new Manejador_permisos(this);
        manejador_permisos.verificar_permiso();



        siguiete_fragemento=crear_onclick_siguiente_fragemto();
        montar_planta= crear_onclick_posterar();
        modificar_planta= crear_onclick_modificar_planta();

        toolbar = findViewById(R.id.toolbar);
         new_plant_manager= new New_plant_manager(this);
        btn_siguiente= findViewById(R.id.siguiente);
        btn_atras= findViewById(R.id.atras);
        btn_siguiente.setOnClickListener(siguiete_fragemento);
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiar_fragmento(false);
            }
        });
        llenar_array_fragments();
        agregar_fragmento();





    }
    private boolean verificar_ultimo_fragemento(){

        New_plant_fragment new_plant_fragment =fragmentos.get(fragmentos.size()-1);
                return new_plant_fragment.verificar_campos();
    }


    private View.OnClickListener crear_onclick_siguiente_fragemto() {
        return  new  View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiar_fragmento(true);
            }
        };
    }

    private View.OnClickListener crear_onclick_modificar_planta(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nombre,descripcion,datos_interes,nombre_cientifico,familia,genero,tipo_planta,altura,diametro_copa,diametro_flor,floracion,epoca;
                nombre=datos_generales.getNombre();
                descripcion=datos_generales.getDescripcion();
                datos_interes=datos_generales.getDatos_interes();
                Log.e("New _plant container   ","resultado :"+datos_interes);
                nombre_cientifico=caractaresticas.getNombre_cientifico();
                familia=caractaresticas.getFamilia();
                genero=caractaresticas.getGenero();
                tipo_planta=caractaresticas.getTipo_planta();
                altura=caractaresticas.getAltura();
                diametro_copa=caractaresticas.getDiametro_copa();
                diametro_flor=caractaresticas.getDiametro_flor();
                floracion=caractaresticas.getFloracion();
                epoca=caractaresticas.getEpoca();


                Modelo_planta planta_nueva=new Modelo_planta(nombre,nombre_cientifico,descripcion,datos_interes,familia,genero,tipo_planta,altura,diametro_copa,diametro_flor,floracion,epoca);

                planta_nueva.setLink_imagen_modelo(planta.getLink_imagen_modelo());
                planta_nueva.setLink_imagen_muestra_1(planta.getLink_imagen_muestra_1());
                planta_nueva.setLink_imagen_muestra_2(planta.getLink_imagen_muestra_2());
                planta_nueva.setLink_imagen_muestra_3(planta.getLink_imagen_muestra_3());
                planta_nueva.setLink_imagen_muestra_4(planta.getLink_imagen_muestra_4());

                Modifi_plant_manager  modifi_plant_manager= new Modifi_plant_manager(New_plant_container.this,planta,planta_nueva);
                modifi_plant_manager.set_imagenes(imagenes.modelo_uris);
                modifi_plant_manager.modificar_planta();


            }
        } ;

    }
    private View.OnClickListener crear_onclick_posterar(){

        View.OnClickListener postear= new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(verificar_ultimo_fragemento()){
                    String nombre,descripcion,datos_interes,nombre_cientifico,familia,genero,tipo_planta,altura,diametro_copa,diametro_flor,floracion,epoca;
                    Modelo_uri imagen_modelo, imagen_muestra_1,imagen_muestra_2,imagen_muestra_3,imagen_muestra_4;
                    nombre=datos_generales.getNombre();
                    descripcion=datos_generales.getDescripcion();
                    datos_interes=datos_generales.getDatos_interes();
                    nombre_cientifico=caractaresticas.getNombre_cientifico();
                    familia=caractaresticas.getFamilia();
                    genero=caractaresticas.getGenero();
                    tipo_planta=caractaresticas.getTipo_planta();
                    altura=caractaresticas.getAltura();
                    diametro_copa=caractaresticas.getDiametro_copa();
                    diametro_flor=caractaresticas.getDiametro_flor();
                    floracion=caractaresticas.getFloracion();
                    epoca=caractaresticas.getEpoca();

                    Modelo_planta planta=new Modelo_planta(nombre,nombre_cientifico,descripcion,datos_interes,familia,genero,tipo_planta,altura,diametro_copa,diametro_flor,floracion,epoca);


                    new_plant_manager.setModelo_planta(planta);
                    new_plant_manager.setImagen_modelo(imagenes.getUri_imagen_modelo());
                    new_plant_manager.setImagen_muestra1(imagenes.getUri_imagen_muestra_1());
                    new_plant_manager.setImagen_muestra2(imagenes.getUri_imagen_muestra_2());
                    new_plant_manager.setImagen_muestra3(imagenes.getUri_imagen_muestra_3());
                    new_plant_manager.setImagen_muestra4(imagenes.getUri_imagen_muestra_4());

                    new_plant_manager.montar_imagenes();

                    //Modelo_planta planta= //new Modelo_planta(nombre,nombre_cientifico,);



                    Toast.makeText(New_plant_container.this,"Montar_imagen",Toast.LENGTH_LONG).show();
                }

            }
        };


        return postear;
    }
    private void llenar_array_fragments() {
        datos_generales = Datos_generales.newInstance(planta);
        caractaresticas = Caractaresticas.newInstance(planta);
        imagenes =Imagenes.newInstance(planta);

        fragmentos.add(datos_generales);
        fragmentos.add(caractaresticas);
        fragmentos.add(imagenes);
        limite=fragmentos.size();


    }

   /* private boolean verificar_permiso() {
        int verificarPermisoReadContacts = ContextCompat
                .checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(verificarPermisoReadContacts!= PackageManager.PERMISSION_GRANTED){

            if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){
                //pedir el permiso
                Toast.makeText(this,"acptelo por favor:(",Toast.LENGTH_LONG).show();
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constantes.CONSTANTE_PERMISO_READ);


            }else{

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constantes.CONSTANTE_PERMISO_READ);
            }


        }else{
            return  true ;
        }
        return false;
    }*/;




    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}