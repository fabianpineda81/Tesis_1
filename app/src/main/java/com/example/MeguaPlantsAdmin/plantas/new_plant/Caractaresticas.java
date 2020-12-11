package com.example.MeguaPlantsAdmin.plantas.new_plant;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.MeguaPlantsAdmin.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Caractaresticas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Caractaresticas extends New_plant_fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View view;
    EditText nombre_cientifico,familia,genero,tipo_planta,altura,diametro_copa,diametro_flor,floracion,epoca;
    TextInputLayout nombre_cientifico_container,familia_container,genero_container,tipo_planta_container,altura_container,diametro_copa_container,diametro_flor_container,floracion_container,epoca_container;

    ArrayList<EditText> editTexts= new ArrayList<>();
    ArrayList<TextInputLayout> textInputLayouts=new ArrayList<>();


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Caractaresticas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragmento_2.
     */
    // TODO: Rename and change types and number of parameters
    public static Caractaresticas newInstance(String param1, String param2) {
        Caractaresticas fragment = new Caractaresticas();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_fragmento_2, container, false);
        inicializar();
        return  view;
    }

    private void inicializar() {
        nombre_cientifico=view.findViewById(R.id.new_plant_nombre_cientifico);
        familia=view.findViewById(R.id.new_plant_familia);
        genero=view.findViewById(R.id.new_plant_genero);
        tipo_planta=view.findViewById(R.id.new_plant_tipo_planta);
        diametro_copa=view.findViewById(R.id.new_plant_diametro_copa);
        altura=view.findViewById(R.id.new_plant_altura);
        diametro_flor=view.findViewById(R.id.new_plant_diametro_flor);
        floracion=view.findViewById(R.id.new_plant_floracion);
        epoca=view.findViewById(R.id.new_plant_epoca);

        editTexts.add(nombre_cientifico);
        editTexts.add(familia);
        editTexts.add(genero);
        editTexts.add(tipo_planta);
        editTexts.add(diametro_copa);
        editTexts.add(altura);
        editTexts.add(diametro_flor);
        editTexts.add(floracion);
        editTexts.add(epoca);

        nombre_cientifico_container=view.findViewById(R.id.new_plant_nombre_cientifico_container);
        familia_container=view.findViewById(R.id.new_plant_familia_container);
        genero_container=view.findViewById(R.id.new_plant_genero_container);
        tipo_planta_container=view.findViewById(R.id.new_plant_tipo_planta_container);
        diametro_copa_container=view.findViewById(R.id.new_plant_diametro_copa_container);
        altura_container=view.findViewById(R.id.new_plant_altura_container);
        diametro_flor_container=view.findViewById(R.id.new_plant_diametro_flor_container);
        floracion_container=view.findViewById(R.id.new_plant_floracion_container);
        epoca_container=view.findViewById(R.id.new_plant_epoca_container);

        textInputLayouts.add(nombre_cientifico_container);
        textInputLayouts.add(familia_container);
        textInputLayouts.add(genero_container);
        textInputLayouts.add(tipo_planta_container);
        textInputLayouts.add(diametro_copa_container);
        textInputLayouts.add(altura_container);
        textInputLayouts.add(diametro_flor_container);
        textInputLayouts.add(floracion_container);
        textInputLayouts.add(epoca_container);

    }

    public String  getNombre_cientifico() {

        return nombre_cientifico.getText().toString();
    }

    public String  getFamilia() {

        return familia.getText().toString();
    }

    public String  getGenero() {

        return genero.getText().toString();

    }

    public String  getTipo_planta() {

        return tipo_planta.getText().toString();
    }

    public String  getAltura() {


        return altura.getText().toString();
    }

    public String getDiametro_copa() {


        return diametro_copa.getText().toString();
    }

    public String  getDiametro_flor() {

        return diametro_flor.getText().toString();
    }

    public String  getFloracion() {


        return floracion.getText().toString();
    }

    public String  getEpoca() {

        return epoca.getText().toString();
    }

    @Override
    public boolean verificar_campos() {
        boolean completado = true;
        for(int i =0; i<editTexts.size();i++){
            if(TextUtils.isEmpty(editTexts.get(i).getText())){
                completado=false;
                crear_error(editTexts.get(i),textInputLayouts.get(i));
            }
        }

        return completado;
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