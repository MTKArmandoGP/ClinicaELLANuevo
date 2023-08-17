package com.example.clinicaelaa_finalproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.clinicaelaa_finalproject.ListAdapter;
import com.example.clinicaelaa_finalproject.ListElement;
import com.example.clinicaelaa_finalproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class DatesFragment_Usuarios extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public DatesFragment_Usuarios() {
        // Required empty public constructor
    }

    public static DatesFragment_Usuarios newInstance(String param1, String param2) {
        DatesFragment_Usuarios fragment = new DatesFragment_Usuarios();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private List<ListElement> elements;
    private List<ListElement> mData;
    private RecyclerView recyclerView;
    private String usuario, password;
    private Context context;
    private ListAdapter listAdapter;

    private int id_Paciente;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
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
        View view = inflater.inflate(R.layout.fragment_dates__usuarios, container, false);

        recyclerView = view.findViewById(R.id.RecyclerCitas);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        SharedPreferences preferences = context.getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        usuario = preferences.getString("usuario", "");
        password = preferences.getString("password", "");

        setupRecyclerView(); // Llama al método para configurar el RecyclerView

        fetchData();

        FloatingActionButton btnAddDate = view.findViewById(R.id.add_date);
        btnAddDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.replace(R.id.frameLayoutUsuarios, FragmentMakeDate.class, null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    private void fetchData() {
        String url = getString(R.string.url)+"obtenerDatos.php";

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
                            id_Paciente = jsonObject.getInt("id");
                            System.out.println("EL ID DEL PACIENTE ES: " + id_Paciente);
                            init();
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

    private void init() {
        String url = getString(R.string.url)+"consultaCitas.php";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);

                        // Verificar si la respuesta es válida y contiene citas
                        if (jsonResponse.has("success") && jsonResponse.getBoolean("success")) {
                            JSONArray citasArray = jsonResponse.getJSONArray("citas");
                            List<ListElement> citas = new ArrayList<>();
                            List<Integer> citaIds = new ArrayList<>(); // Lista para almacenar los ID de las citas

                            for (int i = 0; i < citasArray.length(); i++) {
                                JSONObject citaObj = citasArray.getJSONObject(i);
                                int idCita = citaObj.getInt("id_cita"); // Obtener el ID de la cita del JSON
                                String nombreDoctor = citaObj.getString("nombre_doctor");
                                String hora = citaObj.getString("hora");
                                String fecha = citaObj.getString("fecha");
                                citas.add(new ListElement(idCita, nombreDoctor, hora, fecha));
                                citaIds.add(idCita); // Agregar el ID de la cita a la lista
                            }

                            updateRecyclerView(citas);
                        } else {
                            updateRecyclerView(new ArrayList<>());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    error.printStackTrace();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id_paciente", String.valueOf(id_Paciente));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(request);
    }

    private void updateRecyclerView(List<ListElement> citas) {
        elements = citas;
        mData = new ArrayList<>(citas); // Asigna los datos actualizados a mData
        listAdapter.setItems(mData);
        listAdapter.notifyDataSetChanged();
    }

    private void onItemClick(int citaId) {
        final ListElement[] clickedItem = {null};
        for (ListElement item : mData) {
            if (item.getIdCita() == citaId) {
                clickedItem[0] = item;
                break;
            }
        }

        if (clickedItem[0] != null) {
            CharSequence[] options = {"Eliminar", "Modificar"};
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Selecciona qué es lo que quieres hacer");
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
                        showDeleteConfirmationDialog(clickedItem[0]);
                    } else if (which == 1) {
                        openModifyFragment(clickedItem[0].getIdCita());
                    }
                }
            });
            builder.show();
        }
    }

    private void showDeleteConfirmationDialog(ListElement item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Confirmación de Eliminación");
        builder.setMessage("¿Estás seguro de que quieres eliminarla?");
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                eliminarCita(item.getIdCita());
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    private void openModifyFragment(int citaId) {
        FragmentMakeDate fragmentMakeDate = FragmentMakeDate.newInstance(citaId);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.replace(R.id.frameLayoutUsuarios, fragmentMakeDate, null);
        fragmentTransaction.commit();
    }

    private void setupRecyclerView() {
        listAdapter = new ListAdapter(new ArrayList<ListElement>(), getContext());

        recyclerView.setAdapter(listAdapter);
        listAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int citaId) {
                DatesFragment_Usuarios.this.onItemClick(citaId);
            }
        });

    }

    private void eliminarCita(int idCita) {
        String url = getString(R.string.url)+"eliminarCita.php";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        String message = jsonResponse.getString("message");

                        if (success) {
                            // The appointment was successfully deleted
                            // Perform any necessary actions, such as updating the RecyclerView
                            fetchData(); // Fetch updated data
                        } else {
                            // An error occurred while deleting the appointment
                            // Handle the error, show an error message, etc.
                            Log.e("EliminarCita", "Error deleting appointment: " + message);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("EliminarCita", "Error parsing JSON response: " + e.getMessage());
                    }
                },
                error -> {
                    error.printStackTrace();
                    Log.e("EliminarCita", "Error in request: " + error.getMessage());
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id_cita", String.valueOf(idCita));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(request);
    }

}
