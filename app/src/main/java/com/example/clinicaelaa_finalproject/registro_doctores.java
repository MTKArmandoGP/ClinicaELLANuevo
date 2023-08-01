package com.example.clinicaelaa_finalproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link registro_doctores#newInstance} factory method to
 * create an instance of this fragment.
 */
public class registro_doctores extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public registro_doctores() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment registro_doctores.
     */
    // TODO: Rename and change types and number of parameters
    public static registro_doctores newInstance(String param1, String param2) {
        registro_doctores fragment = new registro_doctores();
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
        View view = inflater.inflate(R.layout.fragment_registro_doctores, container, false);

        // Obtener referencias a los elementos de la interfaz
        TextInputEditText nombreEditText = view.findViewById(R.id.txtnombre_register_doctores);
        TextInputEditText apellidosEditText = view.findViewById(R.id.txtapellido_register_doctores);
        TextInputEditText correoEditText = view.findViewById(R.id.txtemail_register_doctores);
        TextInputEditText numeroEditText = view.findViewById(R.id.txttelefono_register_doctores);
        TextInputEditText passwordEditText = view.findViewById(R.id.txtcontraseña_register_doctores);
        TextInputEditText rolEditText = view.findViewById(R.id.txtcedula_register_doctores);
        TextInputEditText usuarioEditText = view.findViewById(R.id.txtusuario_register_doctores);
        Button registrarButton = view.findViewById(R.id.btn_registrar_doctores);

        // Manejar el evento de clic en el botón de registro
        registrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = nombreEditText.getText().toString();
                String apellidos = apellidosEditText.getText().toString();
                String correo = correoEditText.getText().toString();
                String numero = numeroEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String rol = "1";
                String cedula = rolEditText.getText().toString();
                String usuario = usuarioEditText.getText().toString();


                if (nombre.isEmpty()) {
                    nombreEditText.setError("Ingrese su nombre");
                    return;
                }

                if (apellidos.isEmpty()) {
                    apellidosEditText.setError("Ingrese sus apellidos");
                    return;
                }

                if (correo.isEmpty()) {
                    correoEditText.setError("Ingrese un correo");
                    correoEditText.requestFocus();
                    return;
                } else {
                    // Expresión regular para validar el formato del correo electrónico
                    String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{3,}$";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(correo);
                    if (!matcher.matches()) {
                        correoEditText.setError("Ingrese un correo válido");
                        correoEditText.requestFocus();
                        return;
                    }
                }

                if (numero.isEmpty()) {
                    numeroEditText.setError("Ingrese su número");
                    return;
                }

                if (password.isEmpty()) {
                    passwordEditText.setError("Ingrese una contraseña");
                    passwordEditText.requestFocus();
                    return;
                } else if (password.length() < 6) {
                    passwordEditText.setError("La contraseña debe tener al menos 6 caracteres");
                    passwordEditText.requestFocus();
                    return;
                } else if (!password.matches(".*[A-Z].*")) {
                    passwordEditText.setError("La contraseña debe contener al menos una letra mayúscula");
                    passwordEditText.requestFocus();
                    return;
                } else if (!password.matches(".*[!@#$%^&*()\\-_+].*")) {
                    passwordEditText.setError("La contraseña debe contener al menos un símbolo");
                    passwordEditText.requestFocus();
                    return;
                }

                if (cedula.isEmpty()) {
                    rolEditText.setError("Ingrese su cédula");
                    return;
                }

                if (usuario.isEmpty()) {
                    usuarioEditText.setError("Ingrese su usuario");
                    return;
                }

                // Verificar el valor de la cédula
                if (cedula.equals("123456")) {
                    // Enviar los datos de registro al servidor
                    enviarDatosRegistro(nombre, apellidos, correo, numero, password, rol, usuario);
                }else{
                    // Mostrar un Toast indicando que la cédula no es válida
                    Toast.makeText(getContext(), "La clave de Registro no es Valida", Toast.LENGTH_SHORT).show();
                    return;
                }


            }
        });

        return view;
    }

    // Método para enviar los datos de registro al servidor
    private void enviarDatosRegistro(String nombre, String apellidos, String correo, String numero, String password, String rol, String usuario) {
        String url = "http://192.168.90.168/proyecto_clinicaELAA/registrar_usuarios.php"; // Reemplaza con la URL de tu servidor y el archivo PHP
        String postData = "nombre_usuario=" + nombre +
                "&apellidos_usuario=" + apellidos +
                "&correo_usuario=" + correo +
                "&numero_usuario=" + numero +
                "&password_usuario=" + password +
                "&rol_usuario=" + rol +
                "&usuario_usuario=" + usuario;

        new RegistroTask().execute(url, postData);
    }

    // Tarea asincrónica para enviar la solicitud HTTP POST
    private class RegistroTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            String postData = params[1];

            try {
                URL urlObj = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                // Enviar los datos
                DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                outputStream.write(postData.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
                outputStream.close();

                // Leer la respuesta
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                }

                bufferedReader.close();
                inputStream.close();
                connection.disconnect();

                return response.toString();
            } catch (IOException e) {
                Log.e("Registro", "Error en la conexión: " + e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                // Procesar la respuesta JSON del servidor
                try {
                    JSONObject responseJson = new JSONObject(result);
                    boolean success = responseJson.getBoolean("success");
                    String message = responseJson.getString("message");

                    // Hacer algo con la respuesta del servidor
                    if (success) {
                        // Registro exitoso
                        Log.i("Registro", message);
                        Toast.makeText(getContext(), "Datos Ingresados Exitosamente", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), Login_Activity.class);
                        startActivity(intent);
                        getActivity().finish();
                    } else {
                        // Error en el registro
                        Log.e("Registro", message);
                        Toast.makeText(getContext(), "Error al enviar los datos", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Log.e("Registro", "Error al procesar la respuesta JSON: " + e.getMessage());
                }
            } else {
                Log.e("Registro", "No se recibió respuesta del servidor");
            }
        }
    }

}