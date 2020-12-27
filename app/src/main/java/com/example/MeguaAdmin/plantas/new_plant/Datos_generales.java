package com.example.MeguaAdmin.plantas.new_plant;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.MeguaAdmin.R;
import com.example.MeguaAdmin.plantas.Modelo_planta;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Datos_generales#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Datos_generales extends New_plant_fragment {
    TextView nombre,descripcion,datos_interes;
    TextInputLayout container_nombre,container_descripcion,container_datos_interes;
    View view;
    Modelo_planta planta;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    private String mParam2;

    public Datos_generales() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment fragmento_1.
     */
    // TODO: Rename and change types and number of parameters
    public static Datos_generales newInstance(Modelo_planta planta) {
        Datos_generales fragment = new Datos_generales();
        Bundle args = new Bundle();
        args.putParcelable("planta",planta);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            planta = getArguments().getParcelable("planta");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragmento_1, container, false);

        inicializar();
        return view;
    }
    private void  inicializar(){
        nombre=view.findViewById(R.id.new_plant_nombre);
        descripcion=view.findViewById(R.id.new_plant_descripcion);
        datos_interes= view.findViewById(R.id.new_plant_datos_interes);


        container_nombre= view.findViewById(R.id.new_plant_nombre_container);
        container_datos_interes=view.findViewById(R.id.new_plant_datos_interes_container);
        container_descripcion=view.findViewById(R.id.new_plant_descripcion_container);

        if(planta!=null){
            nombre.setText(planta.getNombre());
            descripcion.setText(planta.getDescripcion());
            datos_interes.setText(planta.getDatos_interes());
        }
    }

    public String getNombre() {


        return nombre.getText().toString();

    }

    public String getDescripcion() {

        return descripcion.getText().toString();
    }

    public String  getDatos_interes() {

        return datos_interes.getText().toString();
    }

    public boolean verificar_campos(){
        Boolean completados=true;
        if(TextUtils.isEmpty(nombre.getText())){
            crear_error(nombre,container_nombre);
            completados=false;
        }

        if(TextUtils.isEmpty(descripcion.getText())){
            crear_error(descripcion,container_descripcion);
            completados=false;
        }

        if(TextUtils.isEmpty(datos_interes.getText())){
            crear_error(datos_interes,container_datos_interes);
            completados=false;
        }

        return completados;
    }





    public void   crear_error(TextView textView, final TextInputLayout textInputLayout){

      // nombre.setError("no se");
       // container.setBackgroundColor(getResources().getColor(R.color.design_default_color_error));
       textInputLayout.setError("Campo obligatorio");

        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.e("editex","enteros"+i+" "+i1+" "+i2 );
                if(i2>0){
                    textInputLayout.setErrorEnabled(false);
                }else{
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError("campo obligatorio");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }


}