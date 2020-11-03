package com.example.MeguaPlantsAdmin;

import android.app.Activity;
import android.graphics.Picture;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


import java.util.ArrayList;

public class Adater_recycler_plantas extends RecyclerView.Adapter<Adater_recycler_plantas.Planta_View_holder> {
    private ArrayList<Modelo_planta> plantas;
    private int recurso;
    private Activity activity;



    public Adater_recycler_plantas(ArrayList<Modelo_planta> plantas, int recurso, Activity activity) {
        this.plantas = plantas;
        this.recurso = recurso;
        this.activity = activity;
    }

    @NonNull
    @Override
    public Planta_View_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(recurso,parent,false);
        return new Planta_View_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Planta_View_holder holder, int position) {
        Modelo_planta planta= plantas.get(position);
        Log.e("holder",""+plantas.size());
        holder.nombre.setText(planta.getNombre());
        holder.numero_me_gusta.setText("0");
        holder.me_gusta.setChecked(true);
        holder.carta_planta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity.getBaseContext(),"click carta platan",Toast.LENGTH_LONG).show();
            }
        });

        holder.carta_planta.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(activity.getBaseContext(),"long click carta platan",Toast.LENGTH_LONG).show();
                return false;
            }
        });

        Glide.with(activity.getBaseContext()).load(planta.getLink_imagen_modelo()).into(holder.imagen);
    }

    @Override
    public int getItemCount() {
        return  plantas.size();
    }

    public  class Planta_View_holder extends RecyclerView.ViewHolder {
        private ImageView imagen;
        private TextView nombre,numero_me_gusta;
        private CheckBox me_gusta;
        private CardView carta_planta;

        public Planta_View_holder(@NonNull View itemView) {
            super(itemView);
            imagen= itemView.findViewById(R.id.card_picture);
            nombre=itemView.findViewById(R.id.card_text_nombre_planta);
            me_gusta=itemView.findViewById(R.id.card_like);
            numero_me_gusta= itemView.findViewById(R.id.card_numero_me_gusta);
            carta_planta=itemView.findViewById(R.id.card_planta);


        }


    }
}
