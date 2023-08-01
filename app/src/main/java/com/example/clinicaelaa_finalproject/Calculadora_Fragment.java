package com.example.clinicaelaa_finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.mozilla.javascript.Scriptable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Calculadora_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Calculadora_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Calculadora_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Calculadora_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Calculadora_Fragment newInstance(String param1, String param2) {
        Calculadora_Fragment fragment = new Calculadora_Fragment();
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

    private TextView tv_Operacion,tv_Resultado;

    private Button btn_0,btn_1,btn_2,btn_3,btn_4,btn_5,btn_6,btn_7,btn_8,btn_9,
            btn_rest,btn_sum,btn_porcentaje,btn_div,btn_mult,btn_igual,btn_parentesis,
            btn_c,btn_del,btn_punto;

    String data;
    private boolean decimal=false;

    private boolean tipoPar=false;

    private boolean bandera=false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_calculadora_, container, false);
        tv_Operacion=(TextView) view.findViewById(R.id.tv_operacion);
        tv_Resultado=(TextView) view.findViewById(R.id.tv_resultado);
        btn_0=(Button) view.findViewById(R.id.btn_zero);
        btn_1=(Button) view.findViewById(R.id.btn_uno);
        btn_2=(Button) view.findViewById(R.id.btn_dos);
        btn_3=(Button) view.findViewById(R.id.btn_tres);
        btn_4=(Button) view.findViewById(R.id.btn_cuatro);
        btn_5=(Button) view.findViewById(R.id.btn_cinco);
        btn_6=(Button) view.findViewById(R.id.btn_seis);
        btn_7=(Button) view.findViewById(R.id.btn_siete);
        btn_8=(Button) view.findViewById(R.id.btn_ocho);
        btn_9=(Button) view.findViewById(R.id.btn_nueve);
        btn_rest=(Button) view.findViewById(R.id.btn_menos);
        btn_sum=(Button) view.findViewById(R.id.btn_mas);
        btn_porcentaje=(Button) view.findViewById(R.id.btn_porcentajes);
        btn_div=(Button) view.findViewById(R.id.btn_dividir);
        btn_mult=(Button) view.findViewById(R.id.btn_multiplicar);
        btn_igual=(Button) view.findViewById(R.id.btn_igual);
        btn_parentesis=(Button) view.findViewById(R.id.btn_parentesis);
        btn_c=(Button) view.findViewById(R.id.btn_c);
        btn_del=(Button) view.findViewById(R.id.btn_eliminar);
        btn_punto=(Button) view.findViewById(R.id.btn_puntos);
        //FUNCIONAMIENTO
        decimal=false;
        tipoPar=false;
        bandera=true;
        btn_0.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                data = tv_Operacion.getText().toString();
                tv_Operacion.setText(data + "0");
                tipoPar=true;
            }


        });

        btn_1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                data = tv_Operacion.getText().toString();
                tv_Operacion.setText(data + "1");
                tipoPar=true;
            }


        });

        btn_2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                data = tv_Operacion.getText().toString();
                tv_Operacion.setText(data + "2");
                tipoPar=true;
            }


        });
        btn_3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                data = tv_Operacion.getText().toString();
                tv_Operacion.setText(data + "3");
                tipoPar=true;
            }


        });
        btn_4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                data = tv_Operacion.getText().toString();
                tv_Operacion.setText(data + "4");
                tipoPar=true;
            }


        });
        btn_5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                data = tv_Operacion.getText().toString();
                tv_Operacion.setText(data + "5");
                tipoPar=true;
            }


        });
        btn_6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                data = tv_Operacion.getText().toString();
                tv_Operacion.setText(data + "6");
                tipoPar=true;
            }


        });
        btn_7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                data = tv_Operacion.getText().toString();
                tv_Operacion.setText(data + "7");
                tipoPar=true;
            }


        });
        btn_8.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                data = tv_Operacion.getText().toString();
                tv_Operacion.setText(data + "8");
                tipoPar=true;
            }


        });
        btn_9.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                data = tv_Operacion.getText().toString();
                tv_Operacion.setText(data + "9");
                tipoPar=true;

            }


        });

        btn_c.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                tv_Operacion.setText("");
                tv_Resultado.setText("0");
                decimal=false;
                tipoPar=false;
                bandera=true;
            }


        });

        btn_punto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (!decimal) {
                    data = tv_Operacion.getText().toString();
                    tv_Operacion.setText(data + ".");
                }
                decimal = true;
            }


        });
        btn_sum.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                data = tv_Operacion.getText().toString();
                tv_Operacion.setText(data + "+");
                decimal = false;
                tipoPar=false;
            }


        });
        btn_rest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                data = tv_Operacion.getText().toString();
                tv_Operacion.setText(data + "-");
                decimal = false;
                tipoPar=false;
            }


        });
        btn_porcentaje.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                data = tv_Operacion.getText().toString();
                tv_Operacion.setText(data + "%");
                decimal = false;
                tipoPar=false;
            }


        });
        btn_mult.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                data = tv_Operacion.getText().toString();
                tv_Operacion.setText(data + "*");
                decimal = false;
                tipoPar=false;
            }


        });

        btn_div.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                data = tv_Operacion.getText().toString();
                tv_Operacion.setText(data + "/");
                decimal = false;
                tipoPar=false;


            }


        });

        btn_del.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String text = tv_Operacion.getText().toString();
                if (text.length() > 1) {
                    text = text.substring(0, text.length() - 1);
                    tv_Operacion.setText(text);
                    if (text.charAt(text.length() - 1) == '.') {
                        decimal = true;
                    } else {
                        decimal = false;
                    }
                } else {
                    tv_Operacion.setText("");
                    decimal = false;
                    tipoPar=false;
                    bandera=true;
                }
            }


        });
        btn_parentesis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tipoPar==true) {
                    if (bandera == true) {
                        data = tv_Operacion.getText().toString();
                        tv_Operacion.setText(data + "*(");
                        bandera = false;
                    } else {
                        data = tv_Operacion.getText().toString();
                        tv_Operacion.setText(data + ")");
                        bandera = true;
                    }
                }else {
                    if (bandera == true) {
                        data = tv_Operacion.getText().toString();
                        tv_Operacion.setText(data + "(");
                        bandera = false;
                    } else {
                        data = tv_Operacion.getText().toString();
                        tv_Operacion.setText(data + ")");
                        bandera = true;
                    }
                }
                decimal = false;
            }
        });



        btn_igual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    data = tv_Operacion.getText().toString();

                    data = data.replaceAll("×", "*");
                    data = data.replaceAll("%", "/100");
                    data = data.replaceAll("÷", "/");

                    org.mozilla.javascript.Context rhino = org.mozilla.javascript.Context.enter();
                    rhino.setOptimizationLevel(-1);

                    String resultadoFinal = "";

                    Scriptable scriptable = rhino.initStandardObjects();
                    resultadoFinal = rhino.evaluateString(scriptable, data, "Javascript", 1, null).toString();

                    tv_Resultado.setText(resultadoFinal);
                    decimal = false;
                }catch (Exception e){
                    tv_Resultado.setText("Formato Invalido");
                }
            }
        });

        return view;
    }
}