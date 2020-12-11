package com.example.MeguaPlantsAdmin.Home.Fragmentos;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.MeguaPlantsAdmin.herramientas.Constantes;
import com.example.MeguaPlantsAdmin.herramientas.Manejador_dialogos;
import com.example.MeguaPlantsAdmin.plantas.Adater_recycler_plantas;
import com.example.MeguaPlantsAdmin.plantas.Modelo_planta;
import com.example.MeguaPlantsAdmin.plantas.reconocimiento_planta.Leer2;
import com.example.MeguaPlantsAdmin.plantas.new_plant.New_plant_container;
import com.example.MeguaPlantsAdmin.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home_fragemt#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home_fragemt extends Fragment {


    FloatingActionButton btn_agregar,btn_leer;
    RecyclerView recyclerView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    ArrayList<Modelo_planta> plantas= new ArrayList<>();
    Adater_recycler_plantas adater_recycler_plantas;
    String[] opciones_imagenes= {"Escoger galeria ","Tomar una foto"};
    String ruta_obsoluta;
    File archivo_foto= null ;
    Manejador_dialogos manejador_dialogos= new Manejador_dialogos();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private  View view;

    public Home_fragemt() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home_fragemt.
     */
    // TODO: Rename and change types and number of parameters
    public static Home_fragemt newInstance(String param1, String param2) {
        Home_fragemt fragment = new Home_fragemt();
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
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_home_fragemt, container, false);
            showToolbar("Home",false,view);
            inicializar(view);

        return view;

    }

    private void inicializar(View view) {
        this.view=view;
        btn_agregar=view.findViewById(R.id.btn_agregar);
        recyclerView= view.findViewById(R.id.recycler_picture_home);
        btn_leer=view.findViewById(R.id.btn_leer);
        btn_leer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leer();

            }
        });

        btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ir_agregar = new Intent(getContext(),New_plant_container.class);
                startActivity(ir_agregar);
            }
        });

        myRef.child("plantas").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                plantas.clear();
                if(snapshot.exists()){


                    for(DataSnapshot snapshot_planta: snapshot.getChildren()){
                        Log.e("plantas firebase",snapshot_planta.toString());
                        Modelo_planta  planta= snapshot_planta.getValue(Modelo_planta.class);
                        plantas.add(planta);


                    }

            }
                adater_recycler_plantas.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(manager);

         adater_recycler_plantas= new Adater_recycler_plantas(plantas,R.layout.layout_carta_planta,getActivity());

        recyclerView.setAdapter(adater_recycler_plantas);


    }

    private void leer() {

        Dialog dialogo =manejador_dialogos.crear_dialogo_escoger_imagen(this);
        dialogo.show();

    }


    public void showToolbar(String tittle,boolean upButton, View view ){
        //  aca declaramos una variable toolbar y traemos el tooblar de view
        Toolbar toolbar=(Toolbar) view.findViewById(R.id.toolbar);


        //  aca enviamos el soporte el toolbar para asi personalizarlo
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        //se le pone el titulo
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(tittle);

        //se le pone el boton de regreso (hay que configurar la jerarquia )
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);


    }



    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        Uri uri;
        Intent leer2= new Intent(getActivity(), Leer2.class);
        Log.e("activity home","llego al onactivity");
        if ( resultCode == getActivity().RESULT_OK) {

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
}