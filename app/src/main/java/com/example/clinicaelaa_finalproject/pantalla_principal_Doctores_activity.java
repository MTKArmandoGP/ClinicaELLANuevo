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
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class pantalla_principal_Doctores_activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private int selectedTab=1;
    private String usuario,usuarioConsulta;
    private String password,passwordConsulta;
    private String foto;
    private int id;
    private String nombre;
    private String apellidos;
    private String correo;
    private String numero;
    private String rol;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    // Método para realizar la solicitud de red y obtener los datos
    private void fetchData() {
        String url = getString(R.string.url)+"obtenerDatos.php";

        OkHttpClient client = new OkHttpClient();

        // Crear el cuerpo de la solicitud POST con los parámetros requeridos
        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("usuario", usuario)
                .add("password", password);
        RequestBody requestBody = formBuilder.build();

        Request request = new Request.Builder()
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
            public void onResponse(Call call, Response response) throws IOException {
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
                                    // Actualizar las vistas con los datos obtenidos
                                    updateViews();
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





    // Método para actualizar las vistas con los datos obtenidos
    private void updateViews() {
        // Obtener referencias a las vistas
        View headerView = navigationView.getHeaderView(0);
        ImageView navHeaderImage = headerView.findViewById(R.id.IvfotoPerfil_nav);
        TextView nombreUsuario = headerView.findViewById(R.id.tvUsuario_nav);
        TextView correoUsuario = headerView.findViewById(R.id.tvcorreoUsurio_nav);

        // Actualizar las vistas con los datos
        nombreUsuario.setText(nombre + " " + apellidos);
        correoUsuario.setText(correo);

        System.out.println(foto);

        // Utilizar el objeto Target personalizado para cargar la imagen sin usar la memoria caché
        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                // Cargar la imagen en la ImageView
                navHeaderImage.setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                // Manejar el fallo de carga de la imagen si es necesario
                Log.e("UpdateViews", "Error al cargar la imagen: " + e.getMessage());
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                // Realizar alguna acción de preparación de carga de la imagen si es necesario
                // Por ejemplo, mostrar un indicador de carga o una imagen de carga predeterminada
            }
        };

        // Cargar la imagen sin usar la memoria caché
        Picasso.get()
                .load(foto + "?timestamp=" + System.currentTimeMillis())
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(target);

        // Imprimir los datos en la consola
        System.out.println("DATOOOOS OBTENIDOOOOS");
        System.out.println(id + " " + nombre + " " + apellidos + " " + correo + " " + numero + " " + password + " " + rol + " " + usuario + " " + foto);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal_doctores);


        SharedPreferences preferences = getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        usuario = preferences.getString("usuario", "");
        password = preferences.getString("password", "");

        fetchData();


        final LinearLayout homeLayout= findViewById(R.id.homeLayout);
        final LinearLayout medicineLayout=findViewById(R.id.medicineLayout);
        final LinearLayout datesLayout=findViewById(R.id.datesLayout);
        final LinearLayout recipesLayout=findViewById(R.id.recipesLayout);
        final LinearLayout settingsLayout=findViewById(R.id.settingsLayout);
        final LinearLayout bottom=findViewById(R.id.bottom);

        //Enlace de cada uno de las vistas de Lottie
        final LottieAnimationView medicineView=findViewById(R.id.medicineView);
        final LottieAnimationView datesView=findViewById(R.id.datesView);
        final LottieAnimationView homeanimationView=findViewById(R.id.homeanimationView);
        final LottieAnimationView recipesView=findViewById(R.id.recipesView);
        final LottieAnimationView settingsView=findViewById(R.id.settingsView);

        //Enlace de cada uno de los campos de texto del menú
        final TextView medicineTxt=findViewById(R.id.medicineTxt);
        final TextView datesTxt=findViewById(R.id.datesTxt);
        final TextView homeTxt=findViewById(R.id.homeTxt);
        final TextView recipesTxt=findViewById(R.id.recipesTxt);
        final TextView settingsTxt=findViewById(R.id.settingsTxt);

        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.frameLayoutDoctores,HomeFragment_Doctores.class,null)
                .commit();

        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.frameLayoutDoctores,HomeFragment_Doctores.class,null)
                        .commit();
                if(selectedTab!=1){


                    //Se colocan invisibles los textos
                    medicineTxt.setVisibility(View.GONE);
                    datesTxt.setVisibility(View.GONE);
                    recipesTxt.setVisibility(View.GONE);
                    settingsTxt.setVisibility(View.GONE);

                    //Se cancelan las animaciones
                    medicineView.cancelAnimation();
                    datesView.cancelAnimation();
                    recipesView.cancelAnimation();
                    settingsView.cancelAnimation();

                    //Se quitan los fondos de cada uno de los linear layout
                    medicineLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    recipesLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    settingsLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    datesLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));


                    //Se destaca el elemento seleccionado
                    homeLayout.setBackgroundResource(R.drawable.background_item_menu);
                    homeTxt.setVisibility(View.VISIBLE);
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f,1.f,1f, Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    homeLayout.startAnimation(scaleAnimation);
                    homeanimationView.playAnimation();

                    selectedTab=1;

                }

            }
        });

        medicineLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.frameLayoutDoctores,FarmaciaFragment_Doctores.class,null)
                        .commit();

                if(selectedTab!=2){


                    //Se colocan invisibles los textos
                    homeTxt.setVisibility(View.GONE);
                    datesTxt.setVisibility(View.GONE);
                    recipesTxt.setVisibility(View.GONE);
                    settingsTxt.setVisibility(View.GONE);

                    //Se cancelan las animaciones
                    homeanimationView.cancelAnimation();
                    datesView.cancelAnimation();
                    recipesView.cancelAnimation();
                    settingsView.cancelAnimation();

                    //Se quitan los fondos de cada uno de los linear layout
                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    recipesLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    settingsLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    datesLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));


                    //Se destaca el elemento seleccionado
                    medicineLayout.setBackgroundResource(R.drawable.background_item_menu);
                    medicineTxt.setVisibility(View.VISIBLE);
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f,1.f,1f, Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    medicineLayout.startAnimation(scaleAnimation);
                    medicineView.playAnimation();

                    selectedTab=2;

                }

            }
        });

        datesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.frameLayoutDoctores,DatesFragment_Doctores.class,null)
                        .commit();

                if(selectedTab!=3){


                    //Se colocan invisibles los textos
                    homeTxt.setVisibility(View.GONE);
                    medicineTxt.setVisibility(View.GONE);
                    recipesTxt.setVisibility(View.GONE);
                    settingsTxt.setVisibility(View.GONE);

                    //Se cancelan las animaciones
                    homeanimationView.cancelAnimation();
                    medicineView.cancelAnimation();
                    recipesView.cancelAnimation();
                    settingsView.cancelAnimation();

                    //Se quitan los fondos de cada uno de los linear layout
                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    recipesLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    settingsLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    medicineLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));


                    //Se destaca el elemento seleccionado
                    datesLayout.setBackgroundResource(R.drawable.background_item_menu);
                    datesTxt.setVisibility(View.VISIBLE);
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f,1.f,1f, Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    datesLayout.startAnimation(scaleAnimation);
                    datesView.playAnimation();

                    selectedTab=3;

                }

            }
        });

        recipesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.frameLayoutDoctores,RecipesFragment_Doctores.class,null)
                        .commit();

                if(selectedTab!=4){


                    //Se colocan invisibles los textos
                    homeTxt.setVisibility(View.GONE);
                    medicineTxt.setVisibility(View.GONE);
                    datesTxt.setVisibility(View.GONE);
                    settingsTxt.setVisibility(View.GONE);

                    //Se cancelan las animaciones
                    homeanimationView.cancelAnimation();
                    medicineView.cancelAnimation();
                    datesView.cancelAnimation();
                    settingsView.cancelAnimation();

                    //Se quitan los fondos de cada uno de los linear layout
                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    datesLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    settingsLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    medicineLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));


                    //Se destaca el elemento seleccionado
                    recipesLayout.setBackgroundResource(R.drawable.background_item_menu);
                    recipesTxt.setVisibility(View.VISIBLE);
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f,1.f,1f, Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    recipesLayout.startAnimation(scaleAnimation);
                    recipesView.playAnimation();

                    selectedTab=4;

                }

            }
        });


        settingsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.frameLayoutDoctores,SettingsFragment_Doctores.class,null)
                        .commit();

                if(selectedTab!=5){


                    //Se colocan invisibles los textos
                    homeTxt.setVisibility(View.GONE);
                    medicineTxt.setVisibility(View.GONE);
                    datesTxt.setVisibility(View.GONE);
                    recipesTxt.setVisibility(View.GONE);

                    //Se cancelan las animaciones
                    homeanimationView.cancelAnimation();
                    medicineView.cancelAnimation();
                    datesView.cancelAnimation();
                    recipesView.cancelAnimation();

                    //Se quitan los fondos de cada uno de los linear layout
                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    datesLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    recipesLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    medicineLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));


                    //Se destaca el elemento seleccionado
                    settingsLayout.setBackgroundResource(R.drawable.background_item_menu);
                    settingsTxt.setVisibility(View.VISIBLE);
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f,1.f,1f, Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    settingsLayout.startAnimation(scaleAnimation);
                    settingsView.playAnimation();

                    selectedTab=5;

                }

            }
        });

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_nav,R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutDoctores,new HomeFragment_Doctores()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //Enlace de cada uno de los LinearLayouts necesarios
        final LinearLayout homeLayout= findViewById(R.id.homeLayout);
        final LinearLayout medicineLayout=findViewById(R.id.medicineLayout);
        final LinearLayout datesLayout=findViewById(R.id.datesLayout);
        final LinearLayout recipesLayout=findViewById(R.id.recipesLayout);
        final LinearLayout settingsLayout=findViewById(R.id.settingsLayout);
        final LinearLayout bottom=findViewById(R.id.bottom);

        //Enlace de cada uno de las vistas de Lottie
        final LottieAnimationView medicineView=findViewById(R.id.medicineView);
        final LottieAnimationView datesView=findViewById(R.id.datesView);
        final LottieAnimationView homeanimationView=findViewById(R.id.homeanimationView);
        final LottieAnimationView recipesView=findViewById(R.id.recipesView);
        final LottieAnimationView settingsView=findViewById(R.id.settingsView);

        //Enlace de cada uno de los campos de texto del menú
        final TextView medicineTxt=findViewById(R.id.medicineTxt);
        final TextView datesTxt=findViewById(R.id.datesTxt);
        final TextView homeTxt=findViewById(R.id.homeTxt);
        final TextView recipesTxt=findViewById(R.id.recipesTxt);
        final TextView settingsTxt=findViewById(R.id.settingsTxt);

        switch (item.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.frameLayoutDoctores,HomeFragment_Doctores.class,null)
                        .commit();

                //Se colocan invisibles los textos
                medicineTxt.setVisibility(View.GONE);
                datesTxt.setVisibility(View.GONE);
                recipesTxt.setVisibility(View.GONE);
                settingsTxt.setVisibility(View.GONE);

                //Se cancelan las animaciones
                medicineView.cancelAnimation();
                datesView.cancelAnimation();
                recipesView.cancelAnimation();
                settingsView.cancelAnimation();

                //Se quitan los fondos de cada uno de los linear layout
                medicineLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                recipesLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                settingsLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                datesLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));


                //Se destaca el elemento seleccionado
                homeLayout.setBackgroundResource(R.drawable.background_item_menu);
                homeTxt.setVisibility(View.VISIBLE);
                ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f,1.f,1f, Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f);
                scaleAnimation.setDuration(200);
                scaleAnimation.setFillAfter(true);
                homeLayout.startAnimation(scaleAnimation);
                homeanimationView.playAnimation();

                selectedTab=1;
                break;
            case R.id.nav_medicine:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutDoctores,new FarmaciaFragment_Doctores()).commit();

                //Se colocan invisibles los textos
                homeTxt.setVisibility(View.GONE);
                datesTxt.setVisibility(View.GONE);
                recipesTxt.setVisibility(View.GONE);
                settingsTxt.setVisibility(View.GONE);

                //Se cancelan las animaciones
                homeanimationView.cancelAnimation();
                datesView.cancelAnimation();
                recipesView.cancelAnimation();
                settingsView.cancelAnimation();

                //Se quitan los fondos de cada uno de los linear layout
                homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                recipesLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                settingsLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                datesLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));


                //Se destaca el elemento seleccionado
                medicineLayout.setBackgroundResource(R.drawable.background_item_medicine);
                medicineTxt.setVisibility(View.VISIBLE);
                scaleAnimation = new ScaleAnimation(0.8f,1.0f,1.f,1f, Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f);
                scaleAnimation.setDuration(200);
                scaleAnimation.setFillAfter(true);
                medicineLayout.startAnimation(scaleAnimation);
                medicineView.playAnimation();

                selectedTab=2;
                break;
            case R.id.nav_dates:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutDoctores,new DatesFragment_Doctores()).commit();


                //Se colocan invisibles los textos
                homeTxt.setVisibility(View.GONE);
                medicineTxt.setVisibility(View.GONE);
                recipesTxt.setVisibility(View.GONE);
                settingsTxt.setVisibility(View.GONE);

                //Se cancelan las animaciones
                homeanimationView.cancelAnimation();
                medicineView.cancelAnimation();
                recipesView.cancelAnimation();
                settingsView.cancelAnimation();

                //Se quitan los fondos de cada uno de los linear layout
                homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                recipesLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                settingsLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                medicineLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));


                //Se destaca el elemento seleccionado
                datesLayout.setBackgroundResource(R.drawable.background_item_dates);
                datesTxt.setVisibility(View.VISIBLE);
                scaleAnimation = new ScaleAnimation(0.8f,1.0f,1.f,1f, Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f);
                scaleAnimation.setDuration(200);
                scaleAnimation.setFillAfter(true);
                datesLayout.startAnimation(scaleAnimation);
                datesView.playAnimation();

                selectedTab=3;
                break;
            case R.id.nav_recipes:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutDoctores,new RecipesFragment_Doctores()).commit();


                //Se colocan invisibles los textos
                homeTxt.setVisibility(View.GONE);
                medicineTxt.setVisibility(View.GONE);
                datesTxt.setVisibility(View.GONE);
                settingsTxt.setVisibility(View.GONE);

                //Se cancelan las animaciones
                homeanimationView.cancelAnimation();
                medicineView.cancelAnimation();
                datesView.cancelAnimation();
                settingsView.cancelAnimation();

                //Se quitan los fondos de cada uno de los linear layout
                homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                datesLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                settingsLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                medicineLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));


                //Se destaca el elemento seleccionado
                recipesLayout.setBackgroundResource(R.drawable.background_item_recipes);
                recipesTxt.setVisibility(View.VISIBLE);
                scaleAnimation = new ScaleAnimation(0.8f,1.0f,1.f,1f, Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f);
                scaleAnimation.setDuration(200);
                scaleAnimation.setFillAfter(true);
                recipesLayout.startAnimation(scaleAnimation);
                recipesView.playAnimation();

                selectedTab=4;
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutDoctores,new SettingsFragment_Doctores()).commit();


                //Se colocan invisibles los textos
                homeTxt.setVisibility(View.GONE);
                medicineTxt.setVisibility(View.GONE);
                datesTxt.setVisibility(View.GONE);
                recipesTxt.setVisibility(View.GONE);

                //Se cancelan las animaciones
                homeanimationView.cancelAnimation();
                medicineView.cancelAnimation();
                datesView.cancelAnimation();
                recipesView.cancelAnimation();

                //Se quitan los fondos de cada uno de los linear layout
                homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                datesLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                recipesLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                medicineLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));


                //Se destaca el elemento seleccionado
                settingsLayout.setBackgroundResource(R.drawable.background_item_settings);
                settingsTxt.setVisibility(View.VISIBLE);
                scaleAnimation = new ScaleAnimation(0.8f,1.0f,1.f,1f, Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f);
                scaleAnimation.setDuration(200);
                scaleAnimation.setFillAfter(true);
                settingsLayout.startAnimation(scaleAnimation);
                settingsView.playAnimation();

                selectedTab=5;
                break;
            case R.id.nav_herramientas:
                Intent intent = new Intent(this, Herramientas_activity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.nav_perfil:
                Intent intent2 = new Intent(this, Mi_Perfil_Activity.class);
                startActivity(intent2);
                finish();
                break;
            case R.id.nav_cambiar_contrasena:
                Intent intent3 = new Intent(this, olvido_contrasena_activity.class);
                startActivity(intent3);
                finish();
                break;
            case R.id.nav_logout:
                cerrarSesion();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }




    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }



    private void cerrarSesion() {

        // Limpiar preferencias
        SharedPreferences preferences = getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();

        // Lanzar la actividad de inicio de sesión
        Intent intent = new Intent(this, Login_Activity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }
}