package com.example.MeguaAdmin.Home.Fragmentos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.MeguaAdmin.Home.Container_Home;
import com.example.MeguaAdmin.Home.Instrucciones_identificar;
import com.example.MeguaAdmin.R;
import com.example.MeguaAdmin.herramientas.Constantes;
import com.example.MeguaAdmin.herramientas.Manejador_dialogos;
import com.example.MeguaAdmin.plantas.reconocimiento_planta.Leer2;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home_fragment extends Fragment {
        Manejador_dialogos manejador_dialogos;
        LinearLayout identificar;
        LinearLayout ver_plantas;
        MaterialCardView instruciones_home;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Home_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Home_fragment newInstance(String param1, String param2) {
        Home_fragment fragment = new Home_fragment();
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
        View view=inflater.inflate(R.layout.fragment_home_fragment, container, false);
        inicializar(view);
        return view;
    }

    private void inicializar(View view) {
        identificar = view.findViewById(R.id.identificar_home);
        ver_plantas= view.findViewById(R.id.ver_plantas_home);
        ver_plantas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), Container_Home.class);
                startActivity(i);
            }
        });
        identificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leer();
            }
        });
        instruciones_home= view.findViewById(R.id.instruciones_home);
        instruciones_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), Instrucciones_identificar.class);
                startActivity(i);
            }
        });
    }

    private void leer() {

        //  Dialog dialogo =manejador_dialogos.crear_dialogo_escoger_imagen(this);
        //dialogo.show();
        manejador_dialogos= new Manejador_dialogos(this,R.layout.boottom_sheet_escoger_imagenest);
        manejador_dialogos.show(getFragmentManager(),"home");
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
}