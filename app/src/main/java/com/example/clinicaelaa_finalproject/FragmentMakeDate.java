package com.example.clinicaelaa_finalproject;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class FragmentMakeDate extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    private List<String> nombresApellidosList = new ArrayList<>();
    private List<Integer> idDoctoresList = new ArrayList<>();
    private Spinner spDoctors;

    private String usuario, password;

    private int selectedDoctorId, id_Paciente;
    private ArrayAdapter<String> adapter;
    private Context context;

    private int idCitaModificar; // Variable para almacenar el ID de cita a modificar

    private int citaId;

    public FragmentMakeDate() {
        // Required empty public constructor
    }

    public static FragmentMakeDate newInstance(int citaId) {
        FragmentMakeDate fragment = new FragmentMakeDate();
        Bundle args = new Bundle();
        args.putInt("citaId", citaId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            citaId = getArguments().getInt("citaId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_make_date, container, false);

        SharedPreferences preferences = context.getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        usuario = preferences.getString("usuario", "");
        password = preferences.getString("password", "");

        fetchData();

        Button btnAltaCita = view.findViewById(R.id.btnAgendarCita);
        Button btnCancel = view.findViewById(R.id.btnCancelar);
        spDoctors = view.findViewById(R.id.sp_doctores);

        obtenerDoctores();

        spDoctors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDoctorId = idDoctoresList.get(position);
                Log.d("ID del Doctor", String.valueOf(selectedDoctorId));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedDoctorId = -1;
            }
        });

        EditText selectDate = view.findViewById(R.id.fecha_Citas);
        EditText selectHour = view.findViewById(R.id.hora_Citas);

        // Configuración del selector de fecha
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(selectDate);
            }
        });

        // Configuración del selector de hora
        selectHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(selectHour);
            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedDoctorIndex = spDoctors.getSelectedItemPosition();
                selectedDoctorId = idDoctoresList.get(selectedDoctorIndex);
                Log.d("Selected Doctor ID", String.valueOf(selectedDoctorId));

                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.replace(R.id.frameLayoutUsuarios, DatesFragment_Usuarios.class, null);
                fragmentTransaction.commit();
            }
        });

        btnAltaCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedDoctorIndex = spDoctors.getSelectedItemPosition();
                selectedDoctorId = idDoctoresList.get(selectedDoctorIndex);
                String fecha = selectDate.getText().toString();
                String hora = selectHour.getText().toString();

                if (selectedDoctorId == -1) {
                    Toast.makeText(context, "Seleccione un doctor", Toast.LENGTH_SHORT).show();
                } else if (fecha.isEmpty() || hora.isEmpty()) {
                    Toast.makeText(context, "Ingrese la fecha y la hora de la cita", Toast.LENGTH_SHORT).show();
                } else {
                    // Validar la fecha seleccionada
                    Calendar currentCalendar = Calendar.getInstance();
                    int currentYear = currentCalendar.get(Calendar.YEAR);
                    int currentMonth = currentCalendar.get(Calendar.MONTH);
                    int currentDayOfMonth = currentCalendar.get(Calendar.DAY_OF_MONTH);

                    int year = Integer.parseInt(fecha.split("-")[0]);
                    int month = Integer.parseInt(fecha.split("-")[1]) - 1;
                    int dayOfMonth = Integer.parseInt(fecha.split("-")[2]);

                    Calendar selectedCalendar = Calendar.getInstance();
                    selectedCalendar.set(year, month, dayOfMonth);

                    if (selectedCalendar.compareTo(currentCalendar) < 0) {
                        Toast.makeText(context, "La fecha seleccionada ya ha pasado", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Mostrar diálogo de confirmación
                    AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                    builder.setTitle("Confirmación");
                    builder.setMessage("¿Desea agendar la cita?");
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (idCitaModificar != 0) {
                                // Modificación de la cita
                                modificarCita(idCitaModificar, selectedDoctorId, fecha, hora);
                            } else {
                                // Alta de la cita
                                altaCita(selectedDoctorId, id_Paciente, fecha, hora);
                            }
                        }
                    });
                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Acción al cancelar el diálogo
                        }
                    });
                    builder.create().show();
                }
            }
        });

        // Verificar si se está modificando una cita existente
        if (citaId != 0) {
            idCitaModificar = citaId;
            btnAltaCita.setText("Modificar Cita");
        } else {
            idCitaModificar = 0;
            btnAltaCita.setText("Agendar Cita");
        }

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

    // Method to show the time picker dialog
    private void showTimePickerDialog(EditText editText) {
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                String selectedTime = hourOfDay + ":" + minute;
                editText.setText(selectedTime);
            }
        }, hourOfDay, minute, true);

        timePickerDialog.show();
    }

    private void obtenerDoctores() {
        String url = getString(R.string.url)+"obtenerDoctores.php";
        JsonRequest<JSONObject> request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean success = response.getBoolean("success");
                            if (success) {
                                JSONArray usuariosArray = response.getJSONArray("usuarios");

                                for (int i = 0; i < usuariosArray.length(); i++) {
                                    JSONObject usuario = usuariosArray.getJSONObject(i);
                                    int idDoctor = usuario.getInt("id_usuario");
                                    String nombre = usuario.getString("nombre_usuario");
                                    String apellidos = usuario.getString("apellidos_usuario");
                                    String nombreApellidos = nombre + " " + apellidos;
                                    nombresApellidosList.add(nombreApellidos);
                                    idDoctoresList.add(idDoctor);
                                }

                                adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, nombresApellidosList);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spDoctors.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(context, "Error en la solicitud HTTP", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    public void setIdCitaModificar(int idCita) {
        this.idCitaModificar = idCita;
    }

    private void altaCita(int idDoctor, int idPaciente, String fecha, String hora) {
        String url = getString(R.string.url)+"altacita.php";

        // Construir los parámetros de la solicitud POST
        Map<String, String> params = new HashMap<>();
        params.put("id_doctor", String.valueOf(idDoctor));
        params.put("id_paciente", String.valueOf(idPaciente));
        params.put("fecha", fecha);
        params.put("hora", hora);

        // Crear la solicitud HTTP POST
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                // Alta exitosa
                                Toast.makeText(getActivity(), "Cita Agendada", Toast.LENGTH_SHORT).show();
                                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.setReorderingAllowed(true);
                                fragmentTransaction.replace(R.id.frameLayoutUsuarios, DatesFragment_Usuarios.class, null);
                                fragmentTransaction.commit();
                                // Realizar cualquier otra acción necesaria
                            } else {
                                // Error en el alta
                                Toast.makeText(getActivity(), "Cita no Agendada", Toast.LENGTH_SHORT).show();
                                // Realizar cualquier otra acción necesaria
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getActivity(), "Error en la solicitud HTTP", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };

        // Agregar la solicitud a la cola de solicitudes de Volley
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    private void modificarCita(int idCita, int nuevoIdDoctor, String nuevaFecha, String nuevaHora) {
        String url = getString(R.string.url)+"modificarCita.php";

        // Construir los parámetros de la solicitud POST
        Map<String, String> params = new HashMap<>();
        params.put("id_cita", String.valueOf(idCita));
        params.put("nuevo_id_doctor", String.valueOf(nuevoIdDoctor));
        params.put("nueva_fecha", nuevaFecha);
        params.put("nueva_hora", nuevaHora);

        // Crear la solicitud HTTP POST
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                // Modificación exitosa
                                Toast.makeText(getActivity(), "Cita modificada", Toast.LENGTH_SHORT).show();
                                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.setReorderingAllowed(true);
                                fragmentTransaction.replace(R.id.frameLayoutUsuarios, DatesFragment_Usuarios.class, null);
                                fragmentTransaction.commit();
                                // Realizar cualquier otra acción necesaria
                            } else {
                                // Error en la modificación
                                Toast.makeText(getActivity(), "Error al modificar la cita", Toast.LENGTH_SHORT).show();
                                // Realizar cualquier otra acción necesaria
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getActivity(), "Error en la solicitud HTTP", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };

        // Agregar la solicitud a la cola de solicitudes de Volley
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
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
