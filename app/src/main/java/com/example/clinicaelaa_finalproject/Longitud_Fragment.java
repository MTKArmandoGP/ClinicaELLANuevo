package com.example.clinicaelaa_finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Longitud_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Longitud_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Longitud_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Longitud_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Longitud_Fragment newInstance(String param1, String param2) {
        Longitud_Fragment fragment = new Longitud_Fragment();
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

    private EditText Valor1;
    private TextView resultado;
    private String resCalculo;
    private double calculo;
    private Spinner spinner1,spinner2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_longitud_, container, false);

        Button btnregresar=view.findViewById(R.id.btn_RegresarConversor);

        Button btnCalcular=view.findViewById(R.id.btn_calcular);

        spinner1=(Spinner) view.findViewById(R.id.sp_unidadlong1);
        spinner2=(Spinner) view.findViewById(R.id.sp_unidadlong2);
        Valor1=(EditText) view.findViewById(R.id.txt_valorLong);
        resultado=(TextView) view.findViewById(R.id.tv_resultadolong);

        btnregresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.replace(R.id.frameLayoutHerramientas, Conversor_Fragment.class, null);
                fragmentTransaction.commit();
            }
        });

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                conversionLongitud();
            }
        });

        return view;

    }

    public void conversionLongitud(){
        double valor=Float.parseFloat(Valor1.getText().toString());
        int posicion=spinner1.getSelectedItemPosition();
        switch (posicion){
            case 0:
                int posicion2=spinner2.getSelectedItemPosition();
                switch (posicion2){
                    case 0:
                        calculo= valor;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Metros"));
                        break;
                    case 1:
                        calculo= valor*10;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Decimetros"));
                        break;
                    case 2:
                        calculo= valor*100;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Centimetros"));
                        break;
                    case 3:
                        calculo= valor*1000;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Milimetros"));
                        break;
                    case 4:
                        calculo= valor*0.1;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Decametros"));
                        break;
                    case 5:
                        calculo= valor*0.01;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Hectómetros"));
                        break;
                    case 6:
                        calculo= valor*0.001;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Kilómetros"));
                        break;
                }
                break;
            case 1:
                posicion2=spinner2.getSelectedItemPosition();
                switch (posicion2){
                    case 0:
                        calculo= valor;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Metros"));
                        break;
                    case 1:
                        calculo= valor*10;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Decimetros"));
                        break;
                    case 2:
                        calculo= valor*100;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Centimetros"));
                        break;
                    case 3:
                        calculo= valor*1000;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Milimetros"));
                        break;
                    case 4:
                        calculo= valor*0.1;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Decametros"));
                        break;
                    case 5:
                        calculo= valor*0.01;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Hectómetros"));
                        break;
                    case 6:
                        calculo= valor*0.001;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Kilómetros"));
                        break;
                }
                break;
            case 2:
                posicion2=spinner2.getSelectedItemPosition();
                switch (posicion2){
                    case 0:
                        calculo= valor*0.01;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Metros"));
                        break;
                    case 1:
                        calculo= valor*0.1;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Decimetros"));
                        break;
                    case 2:
                        calculo= valor;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Centimetros"));
                        break;
                    case 3:
                        calculo= valor*10;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Milimetros"));
                        break;
                    case 4:
                        calculo= valor*0.001;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Decametros"));
                        break;
                    case 5:
                        calculo= valor*0.0001;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Hectómetros"));
                        break;
                    case 6:
                        calculo= valor*1e-5;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Kilómetros"));
                        break;
                }
                break;
            case 3:
                posicion2=spinner2.getSelectedItemPosition();
                switch (posicion2){
                    case 0:
                        calculo= valor*0.001;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Metros"));
                        break;
                    case 1:
                        calculo= valor*0.01;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Decimetros"));
                        break;
                    case 2:
                        calculo= valor*0.1;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Centimetros"));
                        break;
                    case 3:
                        calculo= valor;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Milimetros"));
                        break;
                    case 4:
                        calculo= valor*0.0001;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Decametros"));
                        break;
                    case 5:
                        calculo= valor*1e-5;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Hectómetros"));
                        break;
                    case 6:
                        calculo= valor*1e-6;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Kilómetros"));
                        break;
                }
                break;
            case 4:
                posicion2=spinner2.getSelectedItemPosition();
                switch (posicion2){
                    case 0:
                        calculo= valor*10;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Metros"));
                        break;
                    case 1:
                        calculo= valor*100;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Decimetros"));
                        break;
                    case 2:
                        calculo= valor*1000;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Centimetros"));
                        break;
                    case 3:
                        calculo= valor*10000;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Milimetros"));
                        break;
                    case 4:
                        calculo= valor;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Decametros"));
                        break;
                    case 5:
                        calculo= valor*0.1;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Hectómetros"));
                        break;
                    case 6:
                        calculo= valor*0.01;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Kilómetros"));
                        break;
                }
                break;
            case 5:
                posicion2=spinner2.getSelectedItemPosition();
                switch (posicion2){
                    case 0:
                        calculo= valor*100;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Metros"));
                        break;
                    case 1:
                        calculo= valor*1000;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Decimetros"));
                        break;
                    case 2:
                        calculo= valor*10000;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Centimetros"));
                        break;
                    case 3:
                        calculo= valor*100000;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Milimetros"));
                        break;
                    case 4:
                        calculo= valor*10;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Decametros"));
                        break;
                    case 5:
                        calculo= valor;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Hectómetros"));
                        break;
                    case 6:
                        calculo= valor*0.1;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Kilómetros"));
                        break;
                }
                break;
            case 6:
                posicion2=spinner2.getSelectedItemPosition();
                switch (posicion2){
                    case 0:
                        calculo= valor*1000;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Metros"));
                        break;
                    case 1:
                        calculo= valor*10000;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Decimetros"));
                        break;
                    case 2:
                        calculo= valor*100000;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Centimetros"));
                        break;
                    case 3:
                        calculo= valor*1000000;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Milimetros"));
                        break;
                    case 4:
                        calculo= valor*100;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Decametros"));
                        break;
                    case 5:
                        calculo= valor*10;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Hectómetros"));
                        break;
                    case 6:
                        calculo= valor;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Kilómetros"));
                        break;
                }
                break;
        }

    }
}