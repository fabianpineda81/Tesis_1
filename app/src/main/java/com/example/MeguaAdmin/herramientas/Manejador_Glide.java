package com.example.MeguaAdmin.herramientas;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class Manejador_Glide {

    public  static  void Montar_imagen( Activity activity, ImageView imageView, String link) {
        Glide.with(activity).load(link).into(imageView);

    }
    public  static  void Montar_imagen( Activity activity, ImageView imageView, Uri link) {
        Glide.with(activity).load(link).into(imageView);

    }

    public  static  void Montar_imagen( Activity activity, ImageView imageView, int recurso) {
        Glide.with(activity).load(recurso).into(imageView);


    }

    public  static  void Montar_imagen_progressbar(Activity activity, String link, ImageView imageView, final ProgressBar progressBar){
        Glide.with(activity).load(link).addListener(new RequestListener<Drawable>() {
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
