package com.example.MeguaAdmin.herramientas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.MeguaAdmin.R;
import com.example.MeguaAdmin.plantas.Modelo_planta;
import com.example.MeguaAdmin.plantas.new_plant.New_plant_container;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class Botton_sheet_configuracion_plant extends BottomSheetDialogFragment {
Modelo_planta planta;
Activity activity;


    public Botton_sheet_configuracion_plant(Modelo_planta planta,Activity activity) {
        this.planta =planta;
        this.activity=activity;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.botton_sheet_configurar_plant,container,false);
        inicializar_configurar_plant(v);
        return v;
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
                Manejador_dialogos  manejador_dialogos = new Manejador_dialogos();
                manejador_dialogos.mostrar_dialogo_eliminar(activity,planta);
                dismiss();
            }
        });
        modificar_plant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, New_plant_container.class);
                i.putExtra("planta",planta);
                startActivity(i);
                Log.e("nose","modificar plant");
                dismiss();
            }
        });
    }
}
