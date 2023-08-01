package com.example.clinicaelaa_finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.RequestQueue;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class Herramientas_activity extends AppCompatActivity {

    private int selectedTab=1;

    private DrawerLayout drawerLayout;
    private static final int REQUEST_IMAGE_GALLERY = 1;
    private static final int REQUEST_IMAGE_CAMERA = 2;
    private RequestQueue requestQueue;
    private String usuario,usuarioConsulta;
    private String password,passwordConsulta;
    private String foto;
    private int id;
    private String nombre;
    private String apellidos;
    private String correo;
    private String numero;
    private String rol;
    private void fetchData() {
        String url = "http://192.168.159.201/proyecto_clinicaELAA/obtenerDatos.php";

        OkHttpClient client = new OkHttpClient();

        // Crear el cuerpo de la solicitud POST con los parámetros requeridos
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
            public void onFailure(okhttp3.Call call, IOException e) {
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

                            // Obtener los datos necesarios del JSON y asignarlos a las variables
                            id = jsonObject.getInt("id");
                            nombre = jsonObject.getString("nombre");
                            apellidos = jsonObject.getString("apellidos");
                            correo = jsonObject.getString("correo");
                            numero = jsonObject.getString("numero");
                            passwordConsulta = jsonObject.getString("password");
                            rol = jsonObject.getString("rol");
                            usuarioConsulta = jsonObject.getString("usuario");
                            foto = jsonObject.getString("foto");

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                }
                            });
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_herramientas);

        FloatingActionButton regresar = findViewById(R.id.btn_regresar_herramientas);

        SharedPreferences preferences = getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        usuario = preferences.getString("usuario", "");
        password = preferences.getString("password", "");

        fetchData();

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rol.equals("1")){

                    Intent intent = new Intent(getApplicationContext(), pantalla_principal_Doctores_activity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    finish();

                }else{

                    Intent intent = new Intent(getApplicationContext(), pantalla_principal_usuarios_activity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    finish();
                }
            }
        });

        final LinearLayout calculatorLayout= findViewById(R.id.calculatorLayout);
        final LinearLayout tasksLayout=findViewById(R.id.tasksLayout);
        final LinearLayout conversorLayout=findViewById(R.id.conversorLayout);
        final LinearLayout bottom=findViewById(R.id.bottom_herramientas);

        final LottieAnimationView calculatorView=findViewById(R.id.calculatorView);
        final LottieAnimationView tasksView=findViewById(R.id.tasksView);
        final LottieAnimationView conversorView=findViewById(R.id.conversorView);

        final TextView calculatorTxt=findViewById(R.id.calculatorTxt);
        final TextView tasksTxt=findViewById(R.id.tasksTxt);
        final TextView conversorTxt=findViewById(R.id.conversorTxt);

        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.frameLayoutHerramientas,Calculadora_Fragment.class,null)
                .commit();

        calculatorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.frameLayoutHerramientas,Calculadora_Fragment.class,null)
                        .commit();
                if(selectedTab!=1){

                    bottom.setBackgroundResource(R.color.home);

                    //Se colocan invisibles los textos
                    tasksTxt.setVisibility(View.GONE);
                    conversorTxt.setVisibility(View.GONE);

                    //Se cancelan las animaciones
                    tasksView.cancelAnimation();
                    conversorView.cancelAnimation();

                    //Se quitan los fondos de cada uno de los linear layout
                    tasksLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    conversorLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));


                    //Se destaca el elemento seleccionado
                    calculatorLayout.setBackgroundResource(R.drawable.background_item_menu);
                    calculatorTxt.setVisibility(View.VISIBLE);
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f,1.f,1f, Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    calculatorLayout.startAnimation(scaleAnimation);
                    calculatorView.playAnimation();

                    selectedTab=1;

                }
            }
        });
        tasksLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.frameLayoutHerramientas,Tareas_Fragment.class,null)
                        .commit();

                if(selectedTab!=2){

                    bottom.setBackgroundResource(R.color.medicine);

                    //Se colocan invisibles los textos
                    calculatorTxt.setVisibility(View.GONE);
                    conversorTxt.setVisibility(View.GONE);

                    //Se cancelan las animaciones
                    calculatorView.cancelAnimation();
                    conversorView.cancelAnimation();

                    //Se quitan los fondos de cada uno de los linear layout
                    calculatorLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    conversorLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));


                    //Se destaca el elemento seleccionado
                    tasksLayout.setBackgroundResource(R.drawable.background_item_medicine);
                    tasksTxt.setVisibility(View.VISIBLE);
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f,1.f,1f, Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    tasksLayout.startAnimation(scaleAnimation);
                    tasksView.playAnimation();

                    selectedTab=2;

                }

            }
        });
        conversorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.frameLayoutHerramientas,Conversor_Fragment.class,null)
                        .commit();

                if(selectedTab!=3){

                    bottom.setBackgroundResource(R.color.dates);

                    //Se colocan invisibles los textos
                    calculatorTxt.setVisibility(View.GONE);
                    tasksTxt.setVisibility(View.GONE);

                    //Se cancelan las animaciones
                    calculatorView.cancelAnimation();
                    tasksView.cancelAnimation();

                    //Se quitan los fondos de cada uno de los linear layout
                    calculatorLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    tasksLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));


                    //Se destaca el elemento seleccionado
                    conversorLayout.setBackgroundResource(R.drawable.background_item_dates);
                    conversorTxt.setVisibility(View.VISIBLE);
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f,1.f,1f, Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    conversorLayout.startAnimation(scaleAnimation);
                    conversorView.playAnimation();

                    selectedTab=3;

                }

            }
        });



    }

}