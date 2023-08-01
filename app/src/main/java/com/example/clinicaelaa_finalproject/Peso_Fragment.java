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
 * Use the {@link Peso_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Peso_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Peso_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Peso_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Peso_Fragment newInstance(String param1, String param2) {
        Peso_Fragment fragment = new Peso_Fragment();
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
    String resCalculo;
    double calculo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_peso_, container, false);

        Button btnregresar=view.findViewById(R.id.btn_RegresarConversor);
        Button btnCalcular=view.findViewById(R.id.btn_calcular);


        spinner1=(Spinner) view.findViewById(R.id.sp_unidad1);
        spinner2=(Spinner) view.findViewById(R.id.sp_unidad2);
        Valor1=(EditText) view.findViewById(R.id.txtvalor1);
        resultado=(TextView) view.findViewById(R.id.tv_resultado);

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
                Conversion();
            }
        });

        return view;
    }

    public void Conversion() {
        double valor=Float.parseFloat(Valor1.getText().toString());
        //String seleccion=spinner1.getSelectedItem().toString();
        int posicion=spinner1.getSelectedItemPosition();
        switch (posicion){
            //HECTOGRAMOS
            case 0:
                int posicion2=spinner2.getSelectedItemPosition();
                switch (posicion2){
                    case 0:
                        //1 hectogramo= 10 Decagramos
                        resCalculo=String.valueOf(valor);
                        resultado.setText(resCalculo+" Hectogramos");
                        break;
                    case 1:
                        //1 hectogramo= 10 Decagramos
                        calculo= valor*10;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Decagramos"));
                        break;
                    case 2:
                        calculo= valor*100;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Gramos"));
                        break;
                    case 3:
                        calculo= valor*1000;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Decigramos"));
                        break;
                    case 4:
                        calculo= valor*10000;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Centigramos"));
                        break;
                    case 5:
                        calculo= valor*100000;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Miligramos"));
                        break;
                    case 6:
                        calculo= valor * 0.1;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Kilogramos"));
                        break;
                    case 7:
                        calculo= valor*3.5274;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Onzas"));
                        break;
                    case 8:
                        calculo= valor*0.220462;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Libras"));
                        break;
                }
                break;
            //DECAGRAMOS
            case 1:
                posicion2=spinner2.getSelectedItemPosition();
                switch (posicion2){
                    case 0:
                        calculo= valor*0.1;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Hectogramos"));
                        break;
                    case 1:
                        resCalculo=String.valueOf(valor);
                        resultado.setText((resCalculo+" Decagramos"));
                        break;
                    case 2:
                        calculo= valor*10;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Gramos"));
                        break;
                    case 3:
                        calculo= valor*100;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Decigramos"));
                        break;
                    case 4:
                        calculo= valor*1000;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Centigramos"));
                        break;
                    case 5:
                        calculo= valor*10000;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Miligramos"));
                        break;
                    case 6:
                        calculo= valor * 0.01;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Kilogramos"));
                        break;
                    case 7:
                        calculo= valor*0.35274;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Onzas"));
                        break;
                    case 8:
                        calculo= valor*0.0220462;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Libras"));
                        break;
                }

                break;
            //GRAMOS
            case 2:
                posicion2=spinner2.getSelectedItemPosition();
                switch (posicion2){
                    case 0:
                        calculo= valor*0.01;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Hectogramos"));
                        break;
                    case 1:
                        calculo= valor*0.1;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Decagramos"));
                        break;
                    case 2:
                        calculo= valor*1;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Gramos"));
                        break;
                    case 3:
                        calculo= valor*10;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Decigramos"));
                        break;
                    case 4:
                        calculo= valor*100;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Centigramos"));
                        break;
                    case 5:
                        calculo= valor*1000;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Miligramos"));
                        break;
                    case 6:
                        calculo= valor * 0.001;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Kilogramos"));
                        break;
                    case 7:
                        calculo= valor*0.035274;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Onzas"));
                        break;
                    case 8:
                        calculo= valor*0.00220462;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Libras"));
                        break;
                }

                break;
            //Decigramos
            case 3:
                posicion2=spinner2.getSelectedItemPosition();
                switch (posicion2){
                    case 0:
                        calculo= valor*0.001;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Hectogramos"));
                        break;
                    case 1:
                        calculo= valor*0.01;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Decagramos"));
                        break;
                    case 2:
                        calculo= valor*0.1;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Gramos"));
                        break;
                    case 3:
                        calculo= valor*1;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Decigramos"));
                        break;
                    case 4:
                        calculo= valor*10;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Centigramos"));
                        break;
                    case 5:
                        calculo= valor*100;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Miligramos"));
                        break;
                    case 6:
                        calculo= valor * 0.0001;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Kilogramos"));
                        break;
                    case 7:
                        calculo= valor*0.0035274;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Onzas"));
                        break;
                    case 8:
                        calculo= valor*0.000220462;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Libras"));
                        break;
                }

                break;
            //Centigramos
            case 4:
                posicion2=spinner2.getSelectedItemPosition();
                switch (posicion2){
                    case 0:
                        calculo= valor*0.0001;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Hectogramos"));
                        break;
                    case 1:
                        calculo= valor*1000;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Decagramos"));
                        break;
                    case 2:
                        calculo= valor*0.01;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Gramos"));
                        break;
                    case 3:
                        calculo= valor*0.1;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Decigramos"));
                        break;
                    case 4:
                        calculo= valor*1;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Centigramos"));
                        break;
                    case 5:
                        calculo= valor*10;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Miligramos"));
                        break;
                    case 6:
                        calculo= valor * 1e-5;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Kilogramos"));
                        break;
                    case 7:
                        calculo= valor*0.00035274;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Onzas"));
                        break;
                    case 8:
                        calculo= valor*(2.20462e-5);
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Libras"));
                        break;
                }

                break;
            //Miligramos
            case 5:
                posicion2=spinner2.getSelectedItemPosition();
                switch (posicion2){
                    case 0:
                        calculo= valor*1e-5;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Hectogramos"));
                        break;
                    case 1:
                        calculo= valor*1e-4;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Decagramos"));
                        break;
                    case 2:
                        calculo= valor*0.001;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Gramos"));
                        break;
                    case 3:
                        calculo= valor*0.01;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Decigramos"));
                        break;
                    case 4:
                        calculo= valor*0.1;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Centigramos"));
                        break;
                    case 5:
                        calculo= valor*1;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Miligramos"));
                        break;
                    case 6:
                        calculo= valor * 1e-6;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Kilogramos"));
                        break;
                    case 7:
                        calculo= valor*3.5274e-5;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Onzas"));
                        break;
                    case 8:
                        calculo= valor*(2.20462e-6);
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Libras"));
                        break;
                }

                break;
            //Kilogramos
            case 6:
                posicion2=spinner2.getSelectedItemPosition();
                switch (posicion2){
                    case 0:
                        calculo= valor*10;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Hectogramos"));
                        break;
                    case 1:
                        calculo= valor*100;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Decagramos"));
                        break;
                    case 2:
                        calculo= valor*1000;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Gramos"));
                        break;
                    case 3:
                        calculo= valor*10000;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Decigramos"));
                        break;
                    case 4:
                        calculo= valor*100000;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Centigramos"));
                        break;
                    case 5:
                        calculo= valor*1000000;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Miligramos"));
                        break;
                    case 6:
                        calculo= valor * 1;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Kilogramos"));
                        break;
                    case 7:
                        calculo= valor*35.274;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Onzas"));
                        break;
                    case 8:
                        calculo= valor*2.20462;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Libras"));
                        break;
                }

                break;
            //Onzas
            case 7:
                posicion2=spinner2.getSelectedItemPosition();
                switch (posicion2){
                    case 0:
                        calculo= valor*0.283495;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Hectogramos"));
                        break;
                    case 1:
                        calculo= valor*2.83495;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Decagramos"));
                        break;
                    case 2:
                        calculo= valor*28.3495;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Gramos"));
                        break;
                    case 3:
                        calculo= valor*283.495;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Decigramos"));
                        break;
                    case 4:
                        calculo= valor*2834.95;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Centigramos"));
                        break;
                    case 5:
                        calculo= valor*28349.5;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Miligramos"));
                        break;
                    case 6:
                        calculo= valor * 0.0283495;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Kilogramos"));
                        break;
                    case 7:
                        calculo= valor*1;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Onzas"));
                        break;
                    case 8:
                        calculo= valor*0.0625;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Libras"));
                        break;
                }

                break;
            //Libras
            case 8:
                posicion2=spinner2.getSelectedItemPosition();
                switch (posicion2){
                    case 0:
                        calculo= valor*4.53592;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Hectogramos"));
                        break;
                    case 1:
                        calculo= valor*45.3592;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Decagramos"));
                        break;
                    case 2:
                        calculo= valor*453.592;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Gramos"));
                        break;
                    case 3:
                        calculo= valor*4535.92;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Decigramos"));
                        break;
                    case 4:
                        calculo= valor*45359.2;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Centigramos"));
                        break;
                    case 5:
                        calculo= valor*453592;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Miligramos"));
                        break;
                    case 6:
                        calculo= valor * 0.453592;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Kilogramos"));
                        break;
                    case 7:
                        calculo= valor*16;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Onzas"));
                        break;
                    case 8:
                        calculo= valor*1;
                        resCalculo=String.valueOf(calculo);
                        resultado.setText((resCalculo+" Libras"));
                        break;
                }

                break;


        }


    }

}