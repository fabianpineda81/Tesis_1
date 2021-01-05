package com.example.MeguaAdmin.Home;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.MeguaAdmin.R;
import com.example.MeguaAdmin.herramientas.Constantes;
import com.example.MeguaAdmin.herramientas.Manejador_dialogos;
import com.example.MeguaAdmin.plantas.reconocimiento_planta.Leer2;

public class Instrucciones_identificar extends AppCompatActivity {
    Manejador_dialogos manejador_dialogos;
    Button identificar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrucciones_identificar);
        inicializar();
        showToolbar("Instrucciones",true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
    private void inicializar() {
        identificar=findViewById(R.id.identificar_instrucciones);
        identificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leer();
            }
        });
    }


    private void leer() {

        //  Dialog dialogo =manejador_dialogos.crear_dialogo_escoger_imagen(this);
        //dialogo.show();
        manejador_dialogos= new Manejador_dialogos(this,R.layout.boottom_sheet_escoger_imagenest);
        manejador_dialogos.show(getSupportFragmentManager(),"home");
    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        Uri uri;
        Intent leer2= new Intent(this, Leer2.class);
        Log.e("activity home","llego al onactivity");
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

    public void showToolbar(String tittle,boolean upButton){
        //  aca declaramos una variable toolbar y traemos el tooblar de view
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);


        //  aca enviamos el soporte el toolbar para asi personalizarlo
        setSupportActionBar(toolbar);

        //se le pone el titulo
        getSupportActionBar().setTitle(tittle);

        //se le pone el boton de regreso (hay que configurar la jerarquia )
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);


    }
}