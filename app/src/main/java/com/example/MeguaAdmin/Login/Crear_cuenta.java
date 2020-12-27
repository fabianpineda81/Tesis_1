package com.example.MeguaAdmin.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.MeguaAdmin.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Crear_cuenta extends AppCompatActivity {
    private TextInputEditText username, contraseña, contraseña2,codigo, correo;
    private TextInputLayout username_container,contraseña_container,contraseña2_container, codigo_container,correo_continer;
    private MaterialButton btn_crear_cuenta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);
        inicializar();
    }

    private void inicializar() {
        username= findViewById(R.id.username);
        correo= findViewById(R.id.correo);
        contraseña= findViewById(R.id.contraseña);
        contraseña2=findViewById(R.id.contraseña2);
        codigo=findViewById(R.id.codigo);

        username_container= findViewById(R.id.username_container);
        correo_continer= findViewById(R.id.correo_container);
        contraseña_container= findViewById(R.id.contraseña_container);
        contraseña2_container=findViewById(R.id.contraseña2_container);
        codigo_container=findViewById(R.id.codigo_container);

        btn_crear_cuenta= findViewById(R.id.crear_cuenta);
        btn_crear_cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validacion()){
                    Modelo_Login modelo_login = new Modelo_Login(Crear_cuenta.this);
                    Log.e("CREAR_CUENTA","creando cuenta");
                    modelo_login.crear_usuario_correo(correo.getText().toString(),contraseña.getText().toString(),username.getText().toString(),null);
                    btn_crear_cuenta.setEnabled(false);
                    Toast.makeText(Crear_cuenta.this,"Usuario creado correctamente ", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(Crear_cuenta.this,"algo falta ", Toast.LENGTH_LONG).show();
                }
            }
        });







    }



    private boolean validacion() {
        Boolean resultado = true ;

        if(TextUtils.isEmpty(username.getText())){
            insertar_error_vacio(username,username_container,"Texto obligatorio");
            resultado=false;
        }
        if(TextUtils.isEmpty(correo.getText())){
            insertar_error_vacio(correo,correo_continer,"Texto obligatorio");
            resultado=false;
        }

        if(TextUtils.isEmpty(contraseña.getText())){
            contraseña_container.setError("Texto obligatorio");
            contraseña.addTextChangedListener(crear_error_contra_1());
            resultado=false;
        }
        if(TextUtils.isEmpty(contraseña2.getText())){
            contraseña2_container.setError("Texto obligatorio");
            contraseña2.addTextChangedListener(crear_error_contra_2());
            resultado=false;
        }
        if(TextUtils.isEmpty(codigo.getText())){
            insertar_error_vacio(codigo,codigo_container,"Texto obligatorio");
            resultado=false;
        }

        if(!contraseña.getText().toString().equals(contraseña2.getText().toString())){
            //contraseña2_container.setError("contraseñas distintas");
            contraseña.addTextChangedListener(crear_error_contra_1());
            contraseña2.addTextChangedListener(crear_error_contra_2());
            insertar_error(contraseña2,contraseña2_container,"Contraseñas distintas");
            resultado=false;
        }
      return resultado;
    }


    public void   insertar_error_vacio(EditText editText, final TextInputLayout textInputLayout, String error ){

        // nombre.setError("no se");
        // container.setBackgroundColor(getResources().getColor(R.color.design_default_color_error));
        textInputLayout.setError(error);

        editText.addTextChangedListener(crear_error_vacio(editText,textInputLayout));


    }



    public void   insertar_error(EditText editText, final TextInputLayout textInputLayout,String error ){



        textInputLayout.setError(error);

        //contraseña2.addTextChangedListener(crear_error_contra_diferentes());


    }
    private TextWatcher crear_error_contra_1() {

        TextWatcher error_distintos= new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                if(i2>0){
                    contraseña_container.setErrorEnabled(false);
                    Log.e("Login",contraseña.getText()+"++"+ contraseña2.getText());
                    if (contraseña.getText().toString().equals(contraseña2.getText().toString())) {
                        contraseña2_container.setErrorEnabled(false);
                    } else {
                        contraseña2_container.setErrorEnabled(true);
                        contraseña2_container.setError("Contraseñas distintas");
                    }
                }else{
                    contraseña_container.setError("Campo obligatorio");

                }



            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        return error_distintos;

    }


    private TextWatcher crear_error_contra_2() {

        TextWatcher error_distintos= new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                if(i2>0){
                    Log.e("Login",contraseña.getText()+"++"+ contraseña2.getText());
                    if (contraseña.getText().toString().equals(contraseña2.getText().toString())) {
                        contraseña2_container.setErrorEnabled(false);
                    } else {
                        contraseña2_container.setErrorEnabled(true);
                        contraseña2_container.setError("Contraseñas distintas");
                    }
                }else{
                    contraseña2_container.setError("Campo obligatorio");

                }




            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        return error_distintos;

    }


    private TextWatcher crear_error_vacio(TextView textView, final TextInputLayout textInputLayout){
        TextWatcher error_vacio= new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (i2 > 0) {
                    textInputLayout.setErrorEnabled(false);
                } else {
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError("campo obligatorio");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        return error_vacio;

    }
}

