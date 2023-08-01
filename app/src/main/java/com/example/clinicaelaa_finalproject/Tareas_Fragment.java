package com.example.clinicaelaa_finalproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Tareas_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tareas_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Tareas_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tareas_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Tareas_Fragment newInstance(String param1, String param2) {
        Tareas_Fragment fragment = new Tareas_Fragment();
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
    List<String> data = new ArrayList<>();

    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_tareas_, container, false);
        data.add("Elemento 1");
        data.add("Elemento 2");
        data.add("Elemento 3");

        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new RecyclerViewAdapter(getContext(), data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        FloatingActionButton FABButton = view.findViewById(R.id.fab_btn_add);

        FABButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un diálogo con un EditText para agregar una tarea nuevo
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Agregar tarea");

                final EditText input = new EditText(getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // Configurar el OnClickListener para el botón Agregar
                builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newItemDescription = input.getText().toString();

                        // Agregar un nuevo item a la lista de datos del RecyclerViewAdapter
                        data.add(newItemDescription);

                        // Notificar al RecyclerViewAdapter que se ha agregado un nuevo item
                        adapter.notifyItemInserted(data.size() - 1);

                        Toast.makeText(getContext(), "Tarea agregada", Toast.LENGTH_SHORT).show();

                        dialog.dismiss();

                    }
                });

                // Configurar el OnClickListener para el botón Cancelar
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                // Mostrar el diálogo al usuario
                builder.show();
            }
        });

        return view;
    }
}