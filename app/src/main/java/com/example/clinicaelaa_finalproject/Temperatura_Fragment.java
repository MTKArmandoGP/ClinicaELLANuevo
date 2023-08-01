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
 * Use the {@link Temperatura_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Temperatura_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Temperatura_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Temperatura_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Temperatura_Fragment newInstance(String param1, String param2) {
        Temperatura_Fragment fragment = new Temperatura_Fragment();
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

    private Spinner spinner1,spinner2;
    private EditText Valor1;
    private TextView resultado;
    private String resCalculo;
    private double calculo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_temperatura_, container, false);

        Button btnregresar=view.findViewById(R.id.btn_RegresarConversor);
        Button btnCalcular=view.findViewById(R.id.btn_calcular);


        spinner1=(Spinner) view.findViewById(R.id.sp_unidadTemp);
        spinner2=(Spinner) view.findViewById(R.id.sp_unidadTemp2);
        Valor1=(EditText) view.findViewById(R.id.txt_valorTemp);
        resultado=(TextView) view.findViewById(R.id.tv_resultadoTemp);

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
                conversionTemp();
            }
        });

        return view;
    }

    public void conversionTemp(){
        double valortemp=Float.parseFloat(Valor1.getText().toString());
        int posiciontemp=spinner1.getSelectedItemPosition();
        switch (posiciontemp){

            case 0:
                int posiciontem2=spinner2.getSelectedItemPosition();
                switch (posiciontem2){
                    case 0:
                        calculo= valortemp*1;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Fahrenheit"));
                        break;
                    case 1:
                        calculo= (valortemp-32) * 5/9 ;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Celsius"));
                        break;
                    case 2:
                        calculo= (valortemp-32) * 5/9 + 273.15 ;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Kelvin"));
                        break;
                }
                break;
            case 1:
                posiciontem2=spinner2.getSelectedItemPosition();
                switch (posiciontem2){
                    case 0:
                        calculo= (valortemp * 9/5) + 32;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Fahrenheit"));
                        break;
                    case 1:
                        calculo= valortemp ;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Celsius"));
                        break;
                    case 2:
                        calculo= valortemp + 273.15 ;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Kelvin"));
                        break;
                }
                break;
            case 2:
                posiciontem2=spinner2.getSelectedItemPosition();
                switch (posiciontem2){
                    case 0:
                        calculo= (valortemp - 273.15) * 9/5 + 32 ;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Fahrenheit"));
                        break;
                    case 1:
                        calculo= valortemp - 273.15 ;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Celsius"));
                        break;
                    case 2:
                        calculo= valortemp;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Kelvin"));
                        break;
                }
                break;

        }
    }
}