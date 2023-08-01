package com.example.clinicaelaa_finalproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login_Activity extends AppCompatActivity {

    Button btnRegistrar, btnIngresar;

    EditText edtUsuario,edtPassword;

    String usuario,password;
    TextView olvidoContraseña;

    private int idUsuario;

    String response;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnRegistrar = findViewById(R.id.btnregistrar_login);
        olvidoContraseña = findViewById((R.id.tv_olvido_contrasena));
        btnIngresar = findViewById(R.id.btn_ingresar_login);


        //Relación de las cajas del texto
        edtUsuario = findViewById(R.id.txtusuario_login);
        edtPassword=findViewById(R.id.txtcontraseña_login);

        recuperarPreferencias();

        //Evento clic del boton de Ingresar
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario=edtUsuario.getText().toString();
                password=edtPassword.getText().toString();

                if(!usuario.isEmpty() && !password.isEmpty()){

                    validarUsuario("http://192.168.90.201/proyecto_clinicaELAA/validar_usuario.php");

                }else{
                    Toast.makeText(Login_Activity.this, "Ingresa tus datos", Toast.LENGTH_SHORT).show();
                    edtUsuario.setError("Ingresa tu usuario");
                    edtUsuario.requestFocus();
                    edtPassword.setError("Ingresa tu contraseña");
                    edtPassword.requestFocus();
                }
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAdmin = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentAdmin);
                finish();
            }
        });
        olvidoContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAdmin = new Intent(getApplicationContext(), olvido_contrasena_activity.class);
                startActivity(intentAdmin);
                finish();
            }
        });


    }

    //Método para validar los usuarios en la base de datos.
    private void validarUsuario(String URL){
        StringRequest stringRequest= new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Login_Activity.this.response = response;

                if(!response.isEmpty()){


                    // Obtener el valor del campo rol_usuario de la respuesta
                    int rolUsuario = obtenerRolUsuario(response);
                    guardarPreferencias();

                    // Lanzar una actividad diferente según el rol de usuario
                    switch (rolUsuario) {
                        case 1: // Admin
                            Intent intentAdmin = new Intent(getApplicationContext(), pantalla_principal_Doctores_activity.class);
                            startActivity(intentAdmin);
                            break;
                        case 2: // Paciente
                            Intent intentPaciente = new Intent(getApplicationContext(), pantalla_principal_usuarios_activity.class);
                            startActivity(intentPaciente);
                            break;
                        default:
                            Toast.makeText(Login_Activity.this, "Rol de usuario no válido", Toast.LENGTH_SHORT).show();
                            break;
                    }

                    finish();
                } else {
                    Toast.makeText(Login_Activity.this, "Usuario o Contraseña incorrectos", Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Login_Activity.this, error.toString(), Toast.LENGTH_SHORT).show();
                System.out.println(error.toString());

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros= new HashMap<String,String>();
                parametros.put("usuario",usuario);
                parametros.put("password",password);
                return parametros;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private int obtenerRolUsuario(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            int rolUsuario = jsonObject.getInt("rol_usuario");
            return rolUsuario;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }


    private void guardarPreferencias() {
        SharedPreferences preferences = getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("usuario", usuario);
        editor.putString("password", password);
        editor.putBoolean("sesion", true);


        // Obtener el valor del campo "rol_usuario" de la respuesta del servidor
        int rolUsuario = obtenerRolUsuario(response);

        // Guardar el valor del campo "rol_usuario"
        editor.putInt("rol_usuario", rolUsuario);

        editor.apply();
    }

    private void recuperarPreferencias(){
        SharedPreferences preferences=getSharedPreferences("preferenciasLogin",Context.MODE_PRIVATE);

    }

}