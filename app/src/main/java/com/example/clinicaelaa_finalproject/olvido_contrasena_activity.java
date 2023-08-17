package com.example.clinicaelaa_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class olvido_contrasena_activity extends AppCompatActivity {

    private Button enviarCorreo,regresar;
    private TextInputEditText emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvido_contrasena);

        enviarCorreo = findViewById(R.id.btn_enviar_correo);
        emailEditText = findViewById(R.id.txtemail_recuperar_contraseña);
        enviarCorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarEmail();
            }
        });
        FloatingActionButton regresar = findViewById(R.id.btn_regresar_olvido_contraseña);
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
            }
        });
    }


    private void validarEmail() {
        String email = emailEditText.getText().toString().trim();

        // Expresión regular para verificar el formato del correo electrónico
        String emailPattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        // Compila el patrón de expresión regular
        Pattern pattern = Pattern.compile(emailPattern);

        // Comprueba si el correo electrónico cumple con el formato requerido
        Matcher matcher = pattern.matcher(email);

        if (email.isEmpty() || !matcher.matches()) {
            // El correo electrónico está vacío o no cumple con el formato requerido
            emailEditText.setError("Ingresa un correo electrónico válido");
            emailEditText.requestFocus();
        } else {
            enviarCorreo(email);
            Toast.makeText(olvido_contrasena_activity.this, "Tu código se envio correctamente", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), datos_olvido_contrasena_activity.class);
            startActivity(intent);
            finish();
        }
    }

    private void enviarCorreo(final String email) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // URL of the PHP file
                    URL url = new URL(getString(R.string.url)+"olvidocontrasena.php");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);

                    // Parameters to send to the PHP file
                    String parameters = "correo=" + email;

                    // Send the POST request
                    OutputStream outputStream = connection.getOutputStream();
                    outputStream.write(parameters.getBytes());
                    outputStream.flush();
                    outputStream.close();

                    // Get the response from the PHP file
                    int responseCode = connection.getResponseCode();
                    // Process the response as needed

                    connection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }



}