package com.example.MeguaAdmin.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.MeguaAdmin.Home.Fragmentos.Home_fragment;
import com.example.MeguaAdmin.Home.Fragmentos.Plants__fragment;
import com.example.MeguaAdmin.Home.Fragmentos.Perfil_fragment;
import com.example.MeguaAdmin.R;
import com.example.MeguaAdmin.herramientas.Modelo_uri;
import com.example.MeguaAdmin.plantas.Modelo_planta;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Container_Home extends AppCompatActivity {
    BottomNavigationView bottom_bar;
    boolean to_be_home;
    SearchView.OnQueryTextListener onQueryTextListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottom_bar= findViewById(R.id.bottom_bar);

        inicializar();
        onQueryTextListener= new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                try {

                }catch (Exception e){
                    e.printStackTrace();
                }
                return false;
            }
        };

    }




    private void inicializar() {


        bottom_bar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.item_plants:
                            Plants__fragment plants = new Plants__fragment();
                            add_fragment(plants);

                            to_be_home=true;

                            break;
                        case R.id.item_profile:
                            Perfil_fragment perfil = new Perfil_fragment();
                            add_fragment(perfil);

                            to_be_home=false;
                            break;
                        case R.id.item_home:
                             Home_fragment home= new Home_fragment();
                                 add_fragment(home);

                            to_be_home=false;
                            break;

                    }

                return true ;
            }
        });
        bottom_bar.setSelectedItemId(R.id.item_plants);
    }




    private void add_fragment(Fragment fragment){
        // este metodo permite remplazar un fragment por otro () siempre hay que hacer un comit
        getSupportFragmentManager().beginTransaction().replace(R.id.container_layout,fragment).

                // el setTrasition envia una animacion
                        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null).commit();

    }
    public void onBackPressed() {
        if (!to_be_home) { //if the current view is not the News fragment

            bottom_bar.setSelectedItemId(R.id.item_plants); //display the News fragment
        }
    }

    private ArrayList<Modelo_planta> filtro(ArrayList<Modelo_planta> plantas,String palabra){
        ArrayList<Modelo_planta> resultador = null ;
        return null ;

    }


}