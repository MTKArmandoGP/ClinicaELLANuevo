package com.example.clinicaelaa_finalproject;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentMakeReceta#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMakeReceta extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentMakeReceta() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentMakeReceta.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMakeReceta newInstance(String param1, String param2) {
        FragmentMakeReceta fragment = new FragmentMakeReceta();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private Context context;
    private String usuario, password;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private EditText editTextFecha;
    private EditText editTextNombre;
    private EditText editTextPeso;
    private EditText editTextEstatura;
    private EditText editTextIMC;
    private EditText editTextTemperatura;
    private EditText editTextPresion;
    private EditText editTextDescripcion;
    private RadioGroup radioGroupSexo;
    private Button buttonGenerarReceta;
    private int id_Doctor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_make_receta, container, false);

        SharedPreferences preferences = context.getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        usuario = preferences.getString("usuario", "");
        password = preferences.getString("password", "");

        fetchData();

        editTextFecha = view.findViewById(R.id.fecha_receta);
        editTextNombre = view.findViewById(R.id.Recetas_Nombre);
        editTextPeso = view.findViewById(R.id.Recetas_peso);
        editTextEstatura = view.findViewById(R.id.Recetas_estatura);
        editTextIMC = view.findViewById(R.id.Recetas_imc);
        editTextTemperatura = view.findViewById(R.id.Recetas_temperatura);
        editTextPresion = view.findViewById(R.id.Recetas_presion);
        editTextDescripcion = view.findViewById(R.id.descripcion_receta);
        radioGroupSexo = view.findViewById(R.id.opciones_sexo);
        buttonGenerarReceta = view.findViewById(R.id.btnGenerarReceta);

        editTextFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(editTextFecha);
            }
        });

        buttonGenerarReceta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generarReceta();
            }
        });

        return view;
    }

    private void showDatePickerDialog(EditText editText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                editText.setText(selectedDate);
            }
        }, year, month, dayOfMonth);

        datePickerDialog.show();
    }

    private void generarReceta() {
        String URL = "http://192.168.3.10/proyecto_clinicaELAA/addReceta.php"; // Update with your PHP API URL

        String fecha = editTextFecha.getText().toString().trim();
        String nombre = editTextNombre.getText().toString().trim();
        String peso = editTextPeso.getText().toString().trim();
        String estatura = editTextEstatura.getText().toString().trim();
        String imc = editTextIMC.getText().toString().trim();
        String temperatura = editTextTemperatura.getText().toString().trim();
        String presion = editTextPresion.getText().toString().trim();
        String descripcion = editTextDescripcion.getText().toString().trim();

        final String[] sexo = {""};
        int selectedId = radioGroupSexo.getCheckedRadioButtonId();
        if (selectedId == R.id.radio_masculino) {
            sexo[0] = "M";
        } else if (selectedId == R.id.radio_femenino) {
            sexo[0] = "F";
        }

        if (fecha.isEmpty() || nombre.isEmpty() || peso.isEmpty() || estatura.isEmpty()
                || imc.isEmpty() || temperatura.isEmpty() || presion.isEmpty() || descripcion.isEmpty()) {
            // Si algún campo está vacío, mostramos un mensaje de error
            Toast.makeText(getActivity(), "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                Toast.makeText(getActivity(), "Receta generada exitosamente", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Error al generar la receta", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Error al procesar la respuesta", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getActivity(), "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idDoctor", Integer.toString(id_Doctor)); // Update with the correct parameter name in the PHP API
                params.put("nombre", nombre);
                params.put("peso", peso);
                params.put("estatura", estatura);
                params.put("imc", imc);
                params.put("temperatura", temperatura);
                params.put("presion", presion);
                params.put("descripcion_tratamiento", descripcion); // Update with the correct parameter name in the PHP API
                params.put("sexo", sexo[0]);
                params.put("fecha", fecha);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void fetchData() {
        String url = "http://192.168.3.10/proyecto_clinicaELAA/obtenerDatos.php"; // Update with your PHP API URL

        OkHttpClient client = new OkHttpClient();

        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("usuario", usuario)
                .add("password", password);
        RequestBody requestBody = formBuilder.build();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e("FetchData", "Error en la solicitud: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    Log.d("FetchData", "Respuesta del servidor: " + responseData);

                    try {
                        JSONArray jsonArray = new JSONArray(responseData);
                        if (jsonArray.length() > 0) {
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            id_Doctor = jsonObject.getInt("id");
                            System.out.println("EL ID DEL PACIENTE ES: " + id_Doctor);
                        } else {
                            Log.e("FetchData", "No se encontraron resultados en el JSON.");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("FetchData", "Error al analizar la respuesta JSON: " + e.getMessage());
                    }
                } else {
                    Log.e("FetchData", "Error en la respuesta del servidor: " + response.code());
                }
            }
        });
    }
}