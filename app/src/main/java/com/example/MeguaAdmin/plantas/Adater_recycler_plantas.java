package com.example.MeguaAdmin.plantas;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.MeguaAdmin.Login.Modelo_usuario;
import com.example.MeguaAdmin.R;


import java.util.ArrayList;
import java.util.List;

public class Adater_recycler_plantas extends RecyclerView.Adapter<Adater_recycler_plantas.Planta_View_holder> implements Filterable {
   private  ArrayList<Modelo_planta> plantas;
    private ArrayList<Modelo_planta> todas_planta;
    private int recurso;
    private Activity activity;
    private Modelo_usuario usuario;



    public Adater_recycler_plantas(ArrayList<Modelo_planta> plantas, int recurso, Activity activity, Modelo_usuario usuario) {
        todas_planta = plantas;
        this.plantas= plantas;
        this.recurso = recurso;
        this.activity = activity;
        this.usuario=usuario;
        Log.e("Filtro",""+todas_planta.size());

    }

    @NonNull
    @Override
    public Planta_View_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(recurso,parent,false);
        return new Planta_View_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Planta_View_holder holder, int position) {
        final Modelo_planta planta= plantas.get(position);
        Log.e("HOlDER",planta.getId());
        Log.e("holder",""+plantas.size());
        holder.nombre.setText(planta.getNombre());
        holder.numero_me_gusta.setText("0");
        holder.me_gusta.setChecked(usuario.is_favorita(planta.getNombre_cientifico()));
        holder.me_gusta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox=(CheckBox)view;
                if(checkBox.isChecked()){
                    usuario.agregar_planta_favorita(planta.getNombre_cientifico());
                }else{
                    usuario.eliminar_planta_favorita(planta.getNombre_cientifico());
                }
            }
        });
        holder.carta_planta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity.getBaseContext(),"click carta platan",Toast.LENGTH_LONG).show();
                Intent i = new Intent(activity.getBaseContext(), Detalles_planta.class);
                i.putExtra("planta", planta);
                usuario.agregar_planta_buscada(planta.getNombre_cientifico());
                activity.startActivity(i);
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


    @Override
    public Filter getFilter() {
        Log.e("Filtro",""+todas_planta.size());
        return lista_filtrada;

    }

    private Filter lista_filtrada= new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<Modelo_planta>resultados=new ArrayList<>();
           // plantas.clear();

            if(charSequence==null ||charSequence.length()==0){
                    resultados.addAll(todas_planta);


            }else{
              String  palabra=charSequence.toString().toLowerCase().trim();

                for (Modelo_planta planta:todas_planta) {
                    String nombre= planta.getNombre().toLowerCase();
                    String nombre_cientifico=planta.getNombre_cientifico().toLowerCase();


                    if(nombre.contains(palabra)|| nombre_cientifico.contains(palabra)){
                        resultados.add(planta);

                    }


                }


            }
            FilterResults results=new FilterResults();

            results.values=resultados;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
          
            plantas=(ArrayList<Modelo_planta>)filterResults.values;


            notifyDataSetChanged();
        }
    };


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

    public  void set_arraylist(ArrayList<Modelo_planta> plantas){
        this.plantas=plantas;
        this.todas_planta=plantas;
        notifyDataSetChanged();
    }


}
