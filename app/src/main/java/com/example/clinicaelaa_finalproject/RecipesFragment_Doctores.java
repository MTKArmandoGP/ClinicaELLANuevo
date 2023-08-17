package com.example.clinicaelaa_finalproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class RecipesFragment_Doctores extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public RecipesFragment_Doctores() {
        // Required empty public constructor
    }

    public static RecipesFragment_Doctores newInstance(String param1, String param2) {
        RecipesFragment_Doctores fragment = new RecipesFragment_Doctores();
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
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    List<ListElement_Recetas> elements;
    RecyclerView recyclerView;
    ListAdapter_Recetas listAdapter;
    private Context context;
    private String usuario, password;
    private int id_Doctor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipes__doctores, container, false);
        FloatingActionButton btnAddReceta = view.findViewById(R.id.add_receta);

        SharedPreferences preferences = context.getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        usuario = preferences.getString("usuario", "");
        password = preferences.getString("password", "");

        fetchData();

        fetchRecetasFromAPI();

        recyclerView = view.findViewById(R.id.RecyclerRecetas);

        btnAddReceta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.replace(R.id.frameLayoutDoctores, new FragmentMakeReceta());
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
                            id_Doctor = jsonObject.getInt("id");
                            System.out.println("EL ID DEL PACIENTE ES: " + id_Doctor);

                            // Call fetchRecetasFromAPI() here, after getting the id_Doctor
                            fetchRecetasFromAPI();
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

    private void fetchRecetasFromAPI() {
        String url = getString(R.string.url)+"consultarRecetas.php";

        OkHttpClient client = new OkHttpClient();

        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("idDoctor", String.valueOf(id_Doctor));
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
                        JSONObject jsonResponse = new JSONObject(responseData);
                        boolean success = jsonResponse.getBoolean("success");

                        if (success) {
                            JSONArray recetasArray = jsonResponse.getJSONArray("recetas");

                            elements = new ArrayList<>();

                            for (int i = 0; i < recetasArray.length(); i++) {
                                JSONObject recetaObject = recetasArray.getJSONObject(i);
                                int idReceta = recetaObject.getInt("idReceta");
                                String nombrePaciente = recetaObject.getString("nombrePaciente");
                                String fecha = recetaObject.getString("fecha");

                                elements.add(new ListElement_Recetas(idReceta, nombrePaciente, fecha));
                            }

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    listAdapter = new ListAdapter_Recetas(elements, getContext());
                                    recyclerView.setHasFixedSize(true);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                    recyclerView.setAdapter(listAdapter);

                                    // Set item click listener here after initializing the adapter
                                    listAdapter.setOnItemClickListener(new ListAdapter_Recetas.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(int idReceta) {
                                            openFragmentMakeReceta(idReceta);
                                        }
                                    });
                                }
                            });
                        } else {
                            String message = jsonResponse.getString("message");
                            Log.e("FetchData", "API response error: " + message);
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

    private void openFragmentMakeReceta(int idReceta) {
        FragmentMakeReceta fragment = FragmentMakeReceta.newInstance(idReceta);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.replace(R.id.frameLayoutDoctores, fragment);
        fragmentTransaction.commit();
    }

}
