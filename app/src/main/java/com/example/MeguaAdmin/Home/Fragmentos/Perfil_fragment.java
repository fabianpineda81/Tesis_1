package com.example.MeguaAdmin.Home.Fragmentos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.MeguaAdmin.Login.Login;
import com.example.MeguaAdmin.Login.Modelo_Login;
import com.example.MeguaAdmin.Login.Modelo_usuario;
import com.example.MeguaAdmin.R;
import com.example.MeguaAdmin.herramientas.My_aplicacion;
import com.example.MeguaAdmin.plantas.Adater_recycler_plantas;
import com.example.MeguaAdmin.plantas.Modelo_planta;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Perfil_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Perfil_fragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView recyclerView2;
    Adater_recycler_plantas adater_recycler_plantas2;
    Adater_recycler_plantas adater_recycler_plantas;
    ArrayList<Modelo_planta> plantas;
    ArrayList<Modelo_planta> plantas2;

    Modelo_usuario  usuario;
    My_aplicacion  my_aplicacion;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Perfil_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Perfil_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Perfil_fragment newInstance(String param1, String param2) {
        Perfil_fragment fragment = new Perfil_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_perfil_fragment, container, false);
            showToolbar("Mi perfil",false , view);
            inicializar(view);
        return  view;
    }

    private void inicializar(View view) {
        my_aplicacion=(My_aplicacion)getActivity().getApplication();
        usuario=my_aplicacion.getUsuario();
        recyclerView= view.findViewById(R.id.recycler_plantas_favoritas_perfil);
        recyclerView2= view.findViewById(R.id.recycler_plantas_vsitadas_perfil);
        plantas= usuario.obtener_plantas_favoritas(my_aplicacion);
        plantas2= usuario.obtener_plantas_visitadas(my_aplicacion);





        LinearLayoutManager manager = new GridLayoutManager(getContext(),2);
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);

        adater_recycler_plantas= new Adater_recycler_plantas(plantas,R.layout.layout_carta_planta,getActivity(),usuario);

        recyclerView.setAdapter(adater_recycler_plantas);
        adater_recycler_plantas.notifyDataSetChanged();




        LinearLayoutManager manager2 = new GridLayoutManager(getContext(),2);
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView2.setLayoutManager(manager2);

        adater_recycler_plantas2= new Adater_recycler_plantas(plantas2,R.layout.layout_carta_planta,getActivity(),usuario);

        recyclerView2.setAdapter(adater_recycler_plantas2);
        adater_recycler_plantas2.notifyDataSetChanged();
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
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_mi_perfil, menu);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.cerrar_cession:
                Modelo_Login  modelo_login=new Modelo_Login(getActivity());
                modelo_login.cerrar_seccion_correo();
                break;

        }
        return true;
    }
}