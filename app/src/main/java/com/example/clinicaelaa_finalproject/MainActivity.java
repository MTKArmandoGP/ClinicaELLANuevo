package com.example.clinicaelaa_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
                boolean sesion = preferences.getBoolean("sesion", false);
                if (sesion) {
                    // Obtener el rol de usuario guardado en las preferencias
                    int rolUsuario = preferences.getInt("rol_usuario", 0);

                    // Lanzar una actividad diferente según el rol de usuario
                    switch (rolUsuario) {
                        case 1: // Admin
                            startActivity(new Intent(MainActivity.this, pantalla_principal_Doctores_activity.class));
                            break;
                        case 2: // Paciente
                            startActivity(new Intent(MainActivity.this, pantalla_principal_usuarios_activity.class));
                            break;
                        default:
                            startActivity(new Intent(MainActivity.this, Login_Activity.class));
                            break;
                    }
                } else {
                    startActivity(new Intent(MainActivity.this, Login_Activity.class));
                }

                finish();
            }
        }, 2000);

    }
}