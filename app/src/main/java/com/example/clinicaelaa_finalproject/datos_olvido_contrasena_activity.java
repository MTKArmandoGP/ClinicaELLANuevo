package com.example.clinicaelaa_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class datos_olvido_contrasena_activity extends AppCompatActivity {

    Button guardarCambios;

    private TextInputEditText txtPassword;
    private TextInputEditText txtConfirmPassword;
    private TextWatcher passwordWatcher;

    private TextInputLayout passwordLayout;
    private TextInputLayout confirmPasswordLayout;
    private TextInputEditText codigoConfirmacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_olvido_contrasena);

        guardarCambios = findViewById(R.id.btn_guardarContraseña);

        txtPassword = findViewById(R.id.txtcontraseña_recuperar_contrasena);
        txtConfirmPassword = findViewById(R.id.txtconcontraseña_recuperar_contrasena);
        passwordLayout = findViewById(R.id.contraseña_recuperar_contrasena);
        confirmPasswordLayout = findViewById(R.id.concontraseña_recuperar_contrasena);

        guardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarContrasenas();
            }
        });
    }
    private void validarContrasenas() {
        String password = txtPassword.getText().toString().trim();
        String confirmPassword = txtConfirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(password)) {
            passwordLayout.setError("Ingrese una contraseña");
            return;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            confirmPasswordLayout.setError("Confirme su contraseña");
            return;
        }

        if (password.length() < 6) {
            passwordLayout.setError("La contraseña debe tener al menos 6 caracteres");
            return;
        }

        if (!password.matches(".*[A-Z].*")) {
            passwordLayout.setError("La contraseña debe contener al menos una letra mayúscula");
            return;
        }

        if (!password.matches(".*[!@#$%^&*()\\-_+].*")) {
            passwordLayout.setError("La contraseña debe contener al menos un símbolo");
            return;
        }

        if (password.equals(confirmPassword)) {
            // Si las contraseñas coinciden
            guardarCambios();
        } else {
            // Si las contraseñas no coinciden
            confirmPasswordLayout.setError("Las contraseñas no coinciden");
            confirmPasswordLayout.setErrorTextColor(ColorStateList.valueOf(Color.RED));
        }
    }

    private class UpdatePasswordTask extends AsyncTask<Void, Void, String> {

        private String password;
        private String verificationCode;

        public UpdatePasswordTask(String password, String verificationCode) {
            this.password = password;
            this.verificationCode = verificationCode;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(getString(R.string.url)+"restablecercontrasena.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                String postData = "codigo=" + URLEncoder.encode(verificationCode, "UTF-8")
                        + "&contrasena=" + URLEncoder.encode(password, "UTF-8");

                DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                outputStream.writeBytes(postData);
                outputStream.flush();
                outputStream.close();

                int responseCode = connection.getResponseCode();
                StringBuilder response = new StringBuilder();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    reader.close();
                }

                connection.disconnect();

                return response.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();

                if (result.contains("actualizado correctamente")) {
                    Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    finish();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void guardarCambios() {
        String password = txtPassword.getText().toString().trim();
        codigoConfirmacion=findViewById(R.id.txtcodigo_recuperar_contrasena);
        String verificationCode = codigoConfirmacion.getText().toString(); // Replace with your verification code

        if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(verificationCode)) {
            UpdatePasswordTask task = new UpdatePasswordTask(password, verificationCode);
            task.execute();
        }
    }



}