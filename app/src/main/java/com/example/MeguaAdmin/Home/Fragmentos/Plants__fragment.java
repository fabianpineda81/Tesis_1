package com.example.MeguaAdmin.Home.Fragmentos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import com.example.MeguaAdmin.Login.Modelo_usuario;
import com.example.MeguaAdmin.herramientas.Constantes;
import com.example.MeguaAdmin.herramientas.Manejador_dialogos;
import com.example.MeguaAdmin.herramientas.My_aplicacion;
import com.example.MeguaAdmin.plantas.Adater_recycler_plantas;
import com.example.MeguaAdmin.plantas.Modelo_planta;
import com.example.MeguaAdmin.plantas.reconocimiento_planta.Leer2;
import com.example.MeguaAdmin.plantas.new_plant.New_plant_container;
import com.example.MeguaAdmin.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Plants__fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Plants__fragment extends Fragment {


    FloatingActionButton btn_agregar,btn_leer;
    RecyclerView recyclerView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    ArrayList<Modelo_planta> plantas;
    Adater_recycler_plantas adater_recycler_plantas;
    String[] opciones_imagenes= {"Escoger galeria ","Tomar una foto"};
    String ruta_obsoluta;
    File archivo_foto= null ;
    Manejador_dialogos manejador_dialogos= new Manejador_dialogos();
    SearchView.OnQueryTextListener onQueryTextListener;
    My_aplicacion my_aplicacion;
    Modelo_usuario usuario;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private  View view;

    public Plants__fragment() {
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
    public static Plants__fragment newInstance(String param1, String param2) {
        Plants__fragment fragment = new Plants__fragment();
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
        setHasOptionsMenu(true);

        return view;



    }


    private void inicializar(View view) {
        this.view=view;
        my_aplicacion= (My_aplicacion) getActivity().getApplication();
        plantas=my_aplicacion.getPlantas();
        usuario= my_aplicacion.getUsuario();

        Log.e("USUARIO",usuario.getCorreo());



        onQueryTextListener= new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String  palabra) {

              adater_recycler_plantas.getFilter().filter(palabra);
                return false;
            }
        };




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


        // pasa este metodo a Adapter_recyclre_plantas


        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);

        adater_recycler_plantas= new Adater_recycler_plantas(plantas,R.layout.layout_carta_planta,getActivity(),usuario);

        recyclerView.setAdapter(adater_recycler_plantas);
        adater_recycler_plantas.notifyDataSetChanged();
        my_aplicacion.eliminar_lisener();






    }

    private void leer() {

      //  Dialog dialogo =manejador_dialogos.crear_dialogo_escoger_imagen(this);
      //dialogo.show();
             manejador_dialogos= new Manejador_dialogos(this,R.layout.boottom_sheet_escoger_imagenest);
             manejador_dialogos.show(getFragmentManager(),"home");
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_buscador, menu);
        MenuItem menuItem = menu.findItem(R.id.buscardor);


        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(onQueryTextListener);


        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                //  adater_recycler_plantas.set_arraylist(plantas);
                //  adater_recycler_plantas.notifyDataSetChanged();
                Log.e("hola", "hola");
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                //adater_recycler_plantas.set_arraylist(plantas);
                // adater_recycler_plantas.notifyDataSetChanged();
                Log.e("hola2", "hola2");
                return true;
            }
        });
    }


    private ArrayList<Modelo_planta> filtro (ArrayList<Modelo_planta> plantas,String palabra ){
       return null;
    }
}



