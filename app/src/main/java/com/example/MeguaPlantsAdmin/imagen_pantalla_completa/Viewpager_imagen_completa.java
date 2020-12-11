package com.example.MeguaPlantsAdmin.imagen_pantalla_completa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.MeguaPlantsAdmin.plantas.Modelo_planta;
import com.example.MeguaPlantsAdmin.R;

import java.util.ArrayList;

public class Viewpager_imagen_completa extends AppCompatActivity {
    private FragmentStateAdapter pagerAdapter;
    private ViewPager2 viewPager2;
    private ArrayList<String> links;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager2);
        Modelo_planta modelo_planta = getIntent().getParcelableExtra("planta");
        links=obtener_links(modelo_planta);
        viewPager2 = findViewById(R.id.pager);

        int pos = getIntent().getExtras().getInt("pos");
        pagerAdapter= new View_pager_imagen_completa_adapter(this,links);
        viewPager2.setAdapter(pagerAdapter);
        viewPager2.setCurrentItem(pos,false);


    }

    private ArrayList<String> obtener_links(Modelo_planta modelo_planta) {
        ArrayList<String> resultados= new ArrayList<>();
        resultados.add(modelo_planta.getLink_imagen_muestra_1());
        resultados.add(modelo_planta.getLink_imagen_muestra_2());
        resultados.add(modelo_planta.getLink_imagen_muestra_3());
        resultados.add(modelo_planta.getLink_imagen_muestra_4());
        return  resultados;
    }
}