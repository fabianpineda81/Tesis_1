package com.example.MeguaAdmin.imagen_pantalla_completa;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.MeguaAdmin.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_imagen_completa#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_imagen_completa extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ImageView imageView;
    private ProgressBar progressBar;

    // TODO: Rename and change types of parameters
    private String link;
    private String mParam2;

    public Fragment_imagen_completa() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.

     * @return A new instance of fragment fragment_prueba.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_imagen_completa newInstance(String param1) {
        Fragment_imagen_completa fragment = new Fragment_imagen_completa();
        Bundle args = new Bundle();
        args.putString("link", param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            link = getArguments().getString("link");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_prueba, container, false);
       imageView=view.findViewById(R.id.imagen_fragment);
       progressBar= view.findViewById(R.id.progressBar_imagen);
       montar_imagen(view);
        return view;
    }

    private void montar_imagen(View view) {

        Glide.with(view).load(link).addListener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                progressBar.setVisibility(View.INVISIBLE);
                return false;
            }


        }).into(imageView);

    }


}