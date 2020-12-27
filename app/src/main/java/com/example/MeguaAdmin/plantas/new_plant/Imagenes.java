package com.example.MeguaAdmin.plantas.new_plant;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.MeguaAdmin.R;
import com.example.MeguaAdmin.herramientas.Constantes;
import com.example.MeguaAdmin.herramientas.Manejador_Glide;
import com.example.MeguaAdmin.herramientas.Manejador_dialogos_new_plant;
import com.example.MeguaAdmin.herramientas.Modelo_uri;
import com.example.MeguaAdmin.plantas.Modelo_planta;

import java.io.File;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Imagenes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Imagenes extends New_plant_fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    private String mParam1;
    private String mParam2;
    private View view;
    public ImageView imagen_modelo,imagen_muestra_1,imagen_muestra_2,imagen_muestra_3,imagen_muestra_4;
    ImageButton btn_imangen_muestra_1,btn_imangen_muestra_2,btn_imangen_muestra_3,btn_imangen_muestra_4,btn_imagen_modelo;
   //New_plant_container new_plant_container;
    Manejador_dialogos_new_plant manejador_dialogos;
    Modelo_uri uri_imagen_modelo=null, uri_imagen_muestra_1=null,uri_imagen_muestra_2=null,uri_imagen_muestra_3=null,uri_imagen_muestra_4=null;
    Activity activity;
    Modelo_planta planta;
    Boolean modificar= false ;
    Modifi_plant_manager modifi_plant_manager;
    public  ArrayList<Modelo_uri> modelo_uris= new ArrayList<>();

    public Imagenes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     *
     * @return A new instance of fragment Fragment0_3.
     */
    // TODO: Rename and change types and number of parameters
    public static Imagenes newInstance(Modelo_planta planta) {
        Imagenes fragment = new Imagenes();
        Bundle args = new Bundle();
        args.putParcelable("planta",planta);

        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       if(getArguments()!=null ){
           planta=getArguments().getParcelable("planta");

       }




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_fragment0_3, container, false);
        inicializar(view);

        return view;


    }

    private void inicializar(View view) {
         activity= this.getActivity();

        imagen_modelo= view.findViewById(R.id.new_plant_imagen_modelo);
        imagen_muestra_1= view.findViewById(R.id.new_plant_imagen_muestra_1);
        imagen_muestra_2= view.findViewById(R.id.new_plant_imagen_muestra_2);
        imagen_muestra_3= view.findViewById(R.id.new_plant_imagen_muestra_3);
        imagen_muestra_4= view.findViewById(R.id.new_plant_imagen_muestra_4);

        inicializar_botones_agregar(view);
        if(planta==null){
            Manejador_Glide.Montar_imagen(getActivity(),imagen_modelo,R.drawable.ic_sombrero_de_la_graduacion);
            Manejador_Glide.Montar_imagen(getActivity(),imagen_muestra_1,R.drawable.ic_sombrero_de_la_graduacion);
            Manejador_Glide.Montar_imagen(getActivity(),imagen_muestra_2,R.drawable.ic_sombrero_de_la_graduacion);
            Manejador_Glide.Montar_imagen(getActivity(),imagen_muestra_3,R.drawable.ic_sombrero_de_la_graduacion);
            Manejador_Glide.Montar_imagen(getActivity(),imagen_muestra_4,R.drawable.ic_sombrero_de_la_graduacion);
        }else{
            Manejador_Glide.Montar_imagen(getActivity(),imagen_modelo,planta.getLink_imagen_modelo());
            Manejador_Glide.Montar_imagen(getActivity(),imagen_muestra_1,planta.getLink_imagen_muestra_1());
            Manejador_Glide.Montar_imagen(getActivity(),imagen_muestra_2,planta.getLink_imagen_muestra_2());
            Manejador_Glide.Montar_imagen(getActivity(),imagen_muestra_3,planta.getLink_imagen_muestra_3());
            Manejador_Glide.Montar_imagen(getActivity(),imagen_muestra_4,planta.getLink_imagen_muestra_4());
        }

        //New_plant_container new_plant_container= (New_plant_container) getActivity();



    }

    private void inicializar_botones_agregar(View view) {

        View.OnClickListener onClickListener_btn_agregar= new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manejador_dialogos=new Manejador_dialogos_new_plant(activity,R.layout.boottom_sheet_escoger_imagenest,view.getId());
                manejador_dialogos.show(getFragmentManager(),"imagenes");



            }
        };
        btn_imagen_modelo= view.findViewById(R.id.btn_agregar_imagen_modelo);
        btn_imangen_muestra_1=view.findViewById(R.id.btn_agregar_imagen1);
        btn_imangen_muestra_2=view.findViewById(R.id.btn_agregar_imagen2);
        btn_imangen_muestra_3=view.findViewById(R.id.btn_agregar_imagen3);
        btn_imangen_muestra_4=view.findViewById(R.id.btn_agregar_imagen4);

        btn_imagen_modelo.setOnClickListener(onClickListener_btn_agregar);
        btn_imangen_muestra_1.setOnClickListener(onClickListener_btn_agregar);
        btn_imangen_muestra_2.setOnClickListener(onClickListener_btn_agregar);
        btn_imangen_muestra_3.setOnClickListener(onClickListener_btn_agregar);
        btn_imangen_muestra_4.setOnClickListener(onClickListener_btn_agregar);




    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri;
        Modelo_uri modelo_uri;

        if ( resultCode == RESULT_OK) {

            switch (requestCode){
                case Constantes.CONSTANTE_FOTO_IMAGEN_MUESTRA_FOTO1:

                    uri_imagen_muestra_1 =procesar_foto(true , manejador_dialogos.getFoto(),imagen_muestra_1,Constantes.IMAGEN_MUESTRA1);




                    break;

                case Constantes.CONSTANTE_FOTO_IMAGEN_MUESTRA_FOTO2 :
                   uri_imagen_muestra_2 =procesar_foto(true , manejador_dialogos.getFoto(),imagen_muestra_2,Constantes.IMAGEN_MUESTRA2);



                    break;

                case Constantes.CONSTANTE_FOTO_IMAGEN_MUESTRA_FOTO3 :
                    uri_imagen_muestra_3 =procesar_foto(true , manejador_dialogos.getFoto(),imagen_muestra_3,Constantes.IMAGEN_MUESTRA3);


                    break;
                case Constantes.CONSTANTE_FOTO_IMAGEN_MUESTRA_FOTO4 :
                    uri_imagen_muestra_4 =procesar_foto(true , manejador_dialogos.getFoto(),imagen_muestra_4,Constantes.IMAGEN_MUESTRA4);


                    break;

                case Constantes.CONSTANTE_FOTO_IMAGEN_MODELO:

                    uri_imagen_modelo =procesar_foto(true , manejador_dialogos.getFoto(),imagen_modelo,Constantes.IMAGEN_MODELO);


                    break;

                case Constantes.CONSTANTE_IMAGEN_ESCOGIDA_MODELO:
                    uri= data.getData();
                    uri_imagen_modelo= procesar_imagen_escogiada(false,uri,imagen_modelo,Constantes.IMAGEN_MODELO);



                    break;
                case Constantes.CONSTANTE_IMAGEN_ESCOGIDA_MUESTRA_1:

                    uri= data.getData();
                    uri_imagen_muestra_1= procesar_imagen_escogiada(false,uri,imagen_muestra_1,Constantes.IMAGEN_MUESTRA1);

                    break;
                case Constantes.CONSTANTE_IMAGEN_ESCOGIDA_MUESTRA_2:
                    uri= data.getData();
                    uri_imagen_muestra_2= procesar_imagen_escogiada(false,uri,imagen_muestra_2,Constantes.IMAGEN_MUESTRA2);


                    break;

                case Constantes.CONSTANTE_IMAGEN_ESCOGIDA_MUESTRA_3:
                    uri= data.getData();
                    uri_imagen_muestra_3= procesar_imagen_escogiada(false,uri,imagen_muestra_3,Constantes.IMAGEN_MUESTRA3);


                    break;

                  case Constantes.CONSTANTE_IMAGEN_ESCOGIDA_MUESTRA_4:
                      uri= data.getData();

                      uri_imagen_muestra_4= procesar_imagen_escogiada(false,uri,imagen_muestra_4,Constantes.IMAGEN_MUESTRA4);
                      break;


            }
            Log.e("llego","llego hasta aca ");


        }
    }
    private Modelo_uri procesar_foto(boolean foto , File archivo_foto, ImageView imageView,String nombre ) {
        Uri uri= Uri.fromFile(archivo_foto);
        Manejador_Glide.Montar_imagen(getActivity(),imageView,uri);
        Modelo_uri imagen= new Modelo_uri(true , uri,nombre);
        if(planta!=null){
            modelo_uris.add(imagen);
        }
        return  imagen;
    }


    private Modelo_uri procesar_imagen_escogiada(boolean foto , Uri uri,ImageView imageView,String nombre){

        Manejador_Glide.Montar_imagen(getActivity(),imageView,uri);
        Modelo_uri imagen= new Modelo_uri(false , uri,nombre);
            if(planta!=null){
                modelo_uris.add(imagen);
            }
        return imagen;
    }

    public Modelo_uri getUri_imagen_modelo() {
        return uri_imagen_modelo;
    }

    public Modelo_uri getUri_imagen_muestra_1() {
        return uri_imagen_muestra_1;
    }

    public Modelo_uri getUri_imagen_muestra_2() {
        return uri_imagen_muestra_2;
    }

    public Modelo_uri getUri_imagen_muestra_3() {
        return uri_imagen_muestra_3;
    }

    public Modelo_uri getUri_imagen_muestra_4() {
        return uri_imagen_muestra_4;
    }

    @Override
    public boolean verificar_campos() {
        Boolean completado = true ;

        if(uri_imagen_modelo==null){
            completado=false ;
        }

        if(uri_imagen_muestra_1==null){
            completado=false ;
        }
        if(uri_imagen_muestra_2==null){
            completado=false ;
        }
        if(uri_imagen_muestra_3==null){
            completado=false ;
        }
        if(uri_imagen_muestra_4==null){
            completado=false ;
        }

        if(!completado){
            Toast.makeText(getActivity(),"Imagenes obligatorias",Toast.LENGTH_LONG).show();
        }
        return completado;

    }
}