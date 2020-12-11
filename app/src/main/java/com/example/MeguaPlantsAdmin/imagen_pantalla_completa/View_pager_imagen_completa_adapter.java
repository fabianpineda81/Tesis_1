package com.example.MeguaPlantsAdmin.imagen_pantalla_completa;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class View_pager_imagen_completa_adapter extends FragmentStateAdapter {
    ArrayList<String> links;

    boolean primera=true ;
    public View_pager_imagen_completa_adapter(@NonNull FragmentActivity fragmentActivity, ArrayList<String> links) {
        super(fragmentActivity);
        this.links=links;

    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // pasa hacer frames distintos
        // switch (position)

            Fragment fragment = Fragment_imagen_completa.newInstance(links.get(position));

        //Bundle bundle= new Bundle();
        //bundle.putString("link","hola");
        //bundle.putInt("object",position+1);

        return  fragment;

    }

    @Override
    public int getItemCount() {
        return links.size();
    }


}
