package com.example.MeguaAdmin.herramientas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.MeguaAdmin.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.File;

public class Botton_sheet extends BottomSheetDialogFragment {
    int recurso;
    View v ;
    File foto;

    public Botton_sheet(int recurso) {
        this.recurso = recurso;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         v = inflater.inflate(R.layout.boottom_sheet_escoger_imagenest,container,false);
         inicializar_escoger_imagen(v);
        return v;
    }

    public  void inicializar_escoger_imagen(View v ){
        TextView escoger_imagen=v.findViewById(R.id.escoger_imagen_bottom_sheet);
        TextView tomar_foto=v.findViewById(R.id.tomar_foto_bottom_sheet);

        escoger_imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"escoger imagen",Toast.LENGTH_LONG).show();
            }
        });

        tomar_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"tomar foto",Toast.LENGTH_LONG).show();
            }
        });

    }


}
