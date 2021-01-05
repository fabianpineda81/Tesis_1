package com.example.MeguaAdmin.herramientas;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.MeguaAdmin.Home.Container_Home;
import com.example.MeguaAdmin.R;
import com.example.MeguaAdmin.plantas.Modelo_planta;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.dialog.MaterialDialogs;

import java.io.File;

public class Manejador_dialogos extends BottomSheetDialogFragment {
    File foto;
    View v;
    private Activity activity=null;
    private Fragment fragment=null;
    private int recurso;


    public Manejador_dialogos(Fragment fragment, int recurso) {
        this.fragment = fragment;
        this.recurso = recurso;
    }



    public Manejador_dialogos(Activity activity,int recurso) {
        this.activity = activity;
        this.recurso= recurso;
    }

    public Manejador_dialogos() {
    }

    String[] opciones_imagenes= {"Escoger galeria ","Tomar una foto"};





    public Dialog Crear_dialogo_escoger_imagen_new_plant(final int  id_boton, final Activity activity ) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("escoger foto ").setItems(opciones_imagenes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i == 0){
                    Manejador_seleccionador_imagenes manejador_seleccionador_imagenes= new Manejador_seleccionador_imagenes(activity);
                    manejador_seleccionador_imagenes.seleccionar_imagen_new_plant(id_boton);
                    }else{
                    Manejador_camara manejador_camara= new Manejador_camara();
                 foto =manejador_camara.tomar_foto_new_plant(id_boton,activity);
                }
            }
        });

        return  builder.create();
    }



    public Dialog crear_dialogo_escoger_imagen(final Activity activity) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity );
        builder.setTitle("escoger foto ").setItems(opciones_imagenes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i == 0){
                 Manejador_seleccionador_imagenes manejador_seleccionador_imagenes= new Manejador_seleccionador_imagenes(activity);
                 manejador_seleccionador_imagenes.seleccionar_imagen(activity);
                }else{
                  Manejador_camara manejador_camara=new Manejador_camara();
                  foto =manejador_camara.tomar_foto(activity);

                }
            }
        });
        return  builder.create();
    }

    public Dialog crear_dialogo_escoger_imagen(final Fragment fragment) {

        AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getActivity() );
        builder.setTitle("escoger foto ").setItems(opciones_imagenes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i == 0){
                    Manejador_seleccionador_imagenes manejador_seleccionador_imagenes= new Manejador_seleccionador_imagenes(fragment.getActivity());
                    manejador_seleccionador_imagenes.seleccionar_imagen(fragment);
                }else{
                    Manejador_camara manejador_camara=new Manejador_camara();
                  foto=  manejador_camara.tomar_foto(fragment);

                }
            }
        });
        return  builder.create();
    }

    public File getFoto() {
        return foto;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(recurso,container,false);
         escoger_inicializador(recurso);
        return v;
    }


    private void escoger_inicializador(int recurso){
        switch (recurso){
            case R.layout.boottom_sheet_escoger_imagenest:
                inicializar_escoger_imagen(v);
                break;

            case R.layout.botton_sheet_configurar_plant:
                inicializar_configurar_plant(v);
                break;
        }
    }

    private void inicializar_configurar_plant(View view) {
    TextView agregar_plant= view.findViewById(R.id.agregar_planta_bottom_sheet);
    TextView eliminar_plant=view.findViewById(R.id.eliminar_planta_bottom_sheet);
    TextView modificar_plant=view.findViewById(R.id.modificar_planta_bottom_sheet);
    agregar_plant.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.e("nose","agregar plant");
            dismiss();

        }
    });

    eliminar_plant.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.e("nose","eliminar_plant");
            dismiss();
        }
    });
    modificar_plant.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.e("nose","modificar plant");
            dismiss();
        }
    });
    }


    private   void inicializar_escoger_imagen(View v ){
        TextView escoger_imagen=v.findViewById(R.id.escoger_imagen_bottom_sheet);
        TextView tomar_foto=v.findViewById(R.id.tomar_foto_bottom_sheet);

        escoger_imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Manejador_seleccionador_imagenes manejador_seleccionador_imagenes= new Manejador_seleccionador_imagenes(activity);
                if(activity!=null){
                    manejador_seleccionador_imagenes.seleccionar_imagen(activity);
                }else{
                    manejador_seleccionador_imagenes.seleccionar_imagen(fragment);
                }
                dismiss();
            }
        });

        tomar_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Manejador_camara manejador_camara=new Manejador_camara();

                if(activity!=null){
                    foto =manejador_camara.tomar_foto(activity);
                }else{
                    foto =manejador_camara.tomar_foto(fragment);
                }
                dismiss();



            }
        });

    }


    public void mostrar_dialogo_eliminar(final Activity activity, final Modelo_planta planta){
        new  MaterialAlertDialogBuilder(activity);
      AlertDialog alertDialog=  new MaterialAlertDialogBuilder(activity)
                .setTitle("Eliminar Planta")
                .setMessage("¿Desea eliminar la "+planta.getNombre()+"?")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(activity,"Cancilado",Toast.LENGTH_LONG).show();

                    }
                })
        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                 new Modelo_planta().eliminar_firebase(planta.getNombre_cientifico());
                Toast.makeText(activity,"Eliminado con exito",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(activity, Container_Home.class);
                planta.eliminar_firebase();
                activity.startActivity(intent);


            }
        }).show();



    }








}
