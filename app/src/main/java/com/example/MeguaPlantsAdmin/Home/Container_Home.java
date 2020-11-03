package com.example.MeguaPlantsAdmin.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.MeguaPlantsAdmin.Home.Fragmentos.Buscar_fragment;
import com.example.MeguaPlantsAdmin.Home.Fragmentos.Home_fragemt;
import com.example.MeguaPlantsAdmin.Home.Fragmentos.Perfil_fragment;
import com.example.MeguaPlantsAdmin.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Container_Home extends AppCompatActivity {
    BottomNavigationView bottom_bar;
    boolean to_be_home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottom_bar= findViewById(R.id.bottom_bar);
        inicializar();
    }

    private void inicializar() {

        bottom_bar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.item_home:
                            Home_fragemt home = new Home_fragemt();
                            add_fragment(home);
                            Toast.makeText(Container_Home.this,"Home",Toast.LENGTH_LONG).show();
                            to_be_home=true;

                            break;
                        case R.id.item_profile:
                            Perfil_fragment perfil = new Perfil_fragment();
                            add_fragment(perfil);
                            Toast.makeText(Container_Home.this,"profile",Toast.LENGTH_LONG).show();
                            to_be_home=false;
                            break;
                        case R.id.item_search:
                            Buscar_fragment buscar = new Buscar_fragment();
                                 add_fragment(buscar);
                            Toast.makeText(Container_Home.this,"buscar ",Toast.LENGTH_LONG).show();
                            to_be_home=false;
                            break;

                    }

                return true ;
            }
        });
        bottom_bar.setSelectedItemId(R.id.item_home);
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

            bottom_bar.setSelectedItemId(R.id.item_home); //display the News fragment
        }
    }
}