package com.example.MeguaAdmin.herramientas;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.MeguaAdmin.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.File;

public class Manejador_dialogos_new_plant extends BottomSheetDialogFragment {
    File foto;
    View v;
    private Activity activity=null;
    private Fragment fragment=null;
    private int recurso;
    private  int id_boton;



    public Manejador_dialogos_new_plant(Activity activity, int recurso,int id_boton) {
        this.activity = activity;
        this.recurso= recurso;
        this.id_boton=id_boton;
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
        }
    }
    
    
    private   void inicializar_escoger_imagen(View v ){
        TextView escoger_imagen=v.findViewById(R.id.escoger_imagen_bottom_sheet);
        TextView tomar_foto=v.findViewById(R.id.tomar_foto_bottom_sheet);

        escoger_imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Manejador_seleccionador_imagenes manejador_seleccionador_imagenes= new Manejador_seleccionador_imagenes(activity);
                manejador_seleccionador_imagenes.seleccionar_imagen_new_plant(id_boton);
                dismiss();

            }
        });

        tomar_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Manejador_camara manejador_camara= new Manejador_camara();
                foto =manejador_camara.tomar_foto_new_plant(id_boton,activity);
                dismiss();
            }
        });

    }

    public File getFoto() {
        return foto;
    }

}
