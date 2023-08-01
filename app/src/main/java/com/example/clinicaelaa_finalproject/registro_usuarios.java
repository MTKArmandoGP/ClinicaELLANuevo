package com.example.clinicaelaa_finalproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link registro_usuarios#newInstance} factory method to
 * create an instance of this fragment.
 */
public class registro_usuarios extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public registro_usuarios() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment registro_usuarios.
     */
    // TODO: Rename and change types and number of parameters
    public static registro_usuarios newInstance(String param1, String param2) {
        registro_usuarios fragment = new registro_usuarios();
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
        View view= inflater.inflate(R.layout.fragment_registro_usuarios, container, false);

        // Obtener referencia al botón de envío
        Button btnEnviar = view.findViewById(R.id.btn_registrar);

        // Agregar un listener al botón de envío
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí puedes realizar la lógica para enviar los datos al servidor PHP
                enviarDatosAlServidor();
            }
        });
        return view;
    }

    private void enviarDatosAlServidor() {
        // Obtener los valores de los campos de entrada del usuario
        TextInputEditText txtNombre = getView().findViewById(R.id.txtnombre_register_usuarios);
        TextInputEditText txtApellido = getView().findViewById(R.id.txtapellido_register_usuarios);
        TextInputEditText txtCorreo = getView().findViewById(R.id.txtemail_register_usuarios);
        TextInputEditText txtTelefono = getView().findViewById(R.id.txttelefono_register_usuarios);
        TextInputEditText txtUsuario = getView().findViewById(R.id.txtusuario_register_usuarios);
        TextInputEditText txtContraseña = getView().findViewById(R.id.txtcontraseña_register_usuarios);

        String nombre = txtNombre.getText().toString();
        String apellido = txtApellido.getText().toString();
        String correo = txtCorreo.getText().toString();
        String telefono = txtTelefono.getText().toString();
        String usuario = txtUsuario.getText().toString();
        String contraseña = txtContraseña.getText().toString();

        // Verificar que los campos no estén vacíos
        if (nombre.isEmpty()) {
            txtNombre.setError("Ingrese su nombre(s)");
            txtNombre.requestFocus();
            return;
        }

        if (apellido.isEmpty()) {
            txtApellido.setError("Ingrese su apellido(s)");
            txtApellido.requestFocus();
            return;
        }

        if (correo.isEmpty()) {
            txtCorreo.setError("Ingrese un correo");
            txtCorreo.requestFocus();
            return;
        } else {
            // Expresión regular para validar el formato del correo electrónico
            String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{3,}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(correo);
            if (!matcher.matches()) {
                txtCorreo.setError("Ingrese un correo válido");
                txtCorreo.requestFocus();
                return;
            }
        }

        if (telefono.isEmpty()) {
            txtTelefono.setError("Ingrese un número de teléfono");
            txtTelefono.requestFocus();
            return;
        }

        if (usuario.isEmpty()) {
            txtUsuario.setError("Ingrese un usuario");
            txtUsuario.requestFocus();
            return;
        }

        if (contraseña.isEmpty()) {
            txtContraseña.setError("Ingrese una contraseña");
            txtContraseña.requestFocus();
            return;
        } else if (contraseña.length() < 6) {
            txtContraseña.setError("La contraseña debe tener al menos 6 caracteres");
            txtContraseña.requestFocus();
            return;
        } else if (!contraseña.matches(".*[A-Z].*")) {
            txtContraseña.setError("La contraseña debe contener al menos una letra mayúscula");
            txtContraseña.requestFocus();
            return;
        } else if (!contraseña.matches(".*[!@#$%^&*()\\-_+].*")) {
            txtContraseña.setError("La contraseña debe contener al menos un símbolo");
            txtContraseña.requestFocus();
            return;
        }

        // Ejecutar AsyncTask para realizar la solicitud HTTP POST en un hilo secundario
        new EnviarDatosAsyncTask().execute(nombre, apellido, correo, telefono, usuario, contraseña);

    }

    private class EnviarDatosAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String nombre = params[0];
            String apellido = params[1];
            String correo = params[2];
            String telefono = params[3];
            String usuario = params[4];
            String contraseña = params[5];

            // Construir los parámetros a enviar
            String parametros = "nombre_usuario=" + nombre +
                    "&apellidos_usuario=" + apellido +
                    "&correo_usuario=" + correo +
                    "&numero_usuario=" + telefono +
                    "&password_usuario=" + contraseña +
                    "&rol_usuario=2" +
                    "&usuario_usuario=" + usuario;

            try {
                // Realizar la solicitud HTTP POST en el hilo secundario
                URL url = new URL("http://192.168.90.201/proyecto_clinicaELAA/registrar_usuarios.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(parametros.getBytes());
                outputStream.flush();
                outputStream.close();

                // Leer la respuesta del servidor
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                reader.close();

                // Retornar la respuesta del servidor
                return stringBuilder.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // Mostrar mensaje de éxito o error utilizando Toast
            if (result != null) {
                Toast.makeText(getContext(), "Datos enviados correctamente", Toast.LENGTH_SHORT).show();
                // Guardar las credenciales del usuario en SharedPreferences
                Intent intent = new Intent(getContext(), Login_Activity.class);
                startActivity(intent);
                getActivity().finish();
            } else {
                Toast.makeText(getContext(), "Error al enviar los datos", Toast.LENGTH_SHORT).show();
            }
        }
    }


}