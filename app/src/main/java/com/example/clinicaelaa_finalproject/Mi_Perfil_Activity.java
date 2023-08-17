package com.example.clinicaelaa_finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.yalantis.ucrop.UCrop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import android.Manifest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class Mi_Perfil_Activity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_GALLERY = 1;
    private static final int REQUEST_IMAGE_CAMERA = 2;
    private ImageView imageView;
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
    private Button modificar, guardar, cambiarCon;
    private EditText txtnombre,txtApellidos,txtCorreo,txtUsuario;

    private void fetchData() {
        String url = getString(R.string.url)+"obtenerDatos.php";

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
        ImageView navHeaderImage = findViewById(R.id.FotoPerfil);
        TextView nombreUsuario = findViewById(R.id.UsuarioPerfil);
        TextView correoUsuario = findViewById(R.id.CorreoPerfil);
        txtnombre=findViewById(R.id.txtnombre_perfil);
        txtApellidos=findViewById(R.id.txtapellidos_perfil);
        txtCorreo=findViewById(R.id.txtcorreo_perfil);
        txtUsuario=findViewById(R.id.txtusuario_perfil);

        // Actualizar las vistas con los datos
        nombreUsuario.setText(usuarioConsulta);
        correoUsuario.setText(correo);
        txtnombre.setText(nombre);
        txtApellidos.setText(apellidos);
        txtCorreo.setText(correo);
        txtUsuario.setText(usuarioConsulta);

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
        setContentView(R.layout.activity_mi_perfil);

        SharedPreferences preferences = getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        usuario = preferences.getString("usuario", "");
        password = preferences.getString("password", "");
        SharedPreferences.Editor editor = preferences.edit();

        fetchData();
        txtnombre = findViewById(R.id.txtnombre_perfil);
        txtApellidos = findViewById(R.id.txtapellidos_perfil);
        txtUsuario = findViewById(R.id.txtusuario_perfil);
        txtCorreo = findViewById(R.id.txtcorreo_perfil);

        txtnombre.addTextChangedListener(new CharacterLimitTextWatcher(txtnombre, 25));
        txtApellidos.addTextChangedListener(new CharacterLimitTextWatcher(txtApellidos, 25));
        txtUsuario.addTextChangedListener(new CharacterLimitTextWatcher(txtUsuario, 25));
        txtCorreo.addTextChangedListener(new CharacterLimitTextWatcher(txtCorreo, 60));


        modificar=findViewById(R.id.button_perfil);
        guardar=findViewById(R.id.guardar_perfil);
        cambiarCon=findViewById(R.id.Cambiar_contrasena);

        FloatingActionButton regresar = findViewById(R.id.btn_regresar_miPerfil);
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

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Validar que todos los campos estén llenos
                if (areAllFieldsFilled()) {
                    // Llamar al método para actualizar el perfil
                    updateProfile();

                    editor.putString("usuario", txtUsuario.getText().toString());

                    // Reiniciar la actividad para aplicar los cambios
                    recreate();
                } else {
                    Toast.makeText(Mi_Perfil_Activity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                }

            }
        });

        imageView = findViewById(R.id.FotoPerfil);
        requestQueue = Volley.newRequestQueue(this);

        imageView = findViewById(R.id.FotoPerfil);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });

        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txtnombre.setFocusableInTouchMode(true);
                txtApellidos.setFocusableInTouchMode(true);
                txtUsuario.setFocusableInTouchMode(true);
                txtCorreo.setFocusableInTouchMode(true);

            }
        });

        cambiarCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), olvido_contrasena_activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
            }
        });

    }

    private void updateProfile() {
        String url = getString(R.string.url)+"actualizar.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Manejar la respuesta del servidor
                Toast.makeText(Mi_Perfil_Activity.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Manejar el error de la solicitud
                Toast.makeText(Mi_Perfil_Activity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @NonNull
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Parámetros de la solicitud POST
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(id)); // Convertir el ID a String
                params.put("nombre", txtnombre.getText().toString());
                params.put("apellidos", txtApellidos.getText().toString());
                params.put("correo", txtCorreo.getText().toString());
                params.put("usuario", txtUsuario.getText().toString());
                return params;
            }
        };

        // Agregar la solicitud a la cola de solicitudes
        requestQueue.add(request);
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

    private boolean areAllFieldsFilled() {
        boolean isNombreEmpty = false;
        boolean isApellidosEmpty = false;
        boolean isCorreoEmpty = false;
        boolean isUsuarioEmpty = false;

        String nombre = txtnombre.getText().toString().trim();
        String apellidos = txtApellidos.getText().toString().trim();
        String correo = txtCorreo.getText().toString().trim();
        String usuario = txtUsuario.getText().toString().trim();

        // Verificar si los campos están vacíos
        if (nombre.isEmpty()) {
            txtnombre.setError("Campo requerido");
            isNombreEmpty = true;
        }

        if (apellidos.isEmpty()) {
            txtApellidos.setError("Campo requerido");
            isApellidosEmpty = true;
        }

        if (correo.isEmpty()) {
            txtCorreo.setError("Campo requerido");
            isCorreoEmpty = true;
        }

        if (usuario.isEmpty()) {
            txtUsuario.setError("Campo requerido");
            isUsuarioEmpty = true;
        }

        // Mostrar un mensaje de error si hay campos vacíos
        if (isNombreEmpty || isApellidosEmpty || isCorreoEmpty || isUsuarioEmpty) {
            Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Verificar el formato del correo electrónico
        if (!isValidEmail(correo)) {
            txtCorreo.setError("Por favor, ingresa un correo electrónico válido");
            return false;
        }

        return true;
    }


    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (!email.matches(emailRegex)) {
            txtCorreo.setError("Por favor, ingresa un correo electrónico válido");
            return false;
        }
        return true;
    }






    private void openImagePicker() {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // Si los permisos están concedidos, abrir la galería o la cámara
            showImagePickerDialog();
        } else {
            // Si los permisos no están concedidos, solicitarlos al usuario
            ActivityCompat.requestPermissions(this, permissions, REQUEST_IMAGE_GALLERY);
        }
    }

    private void showImagePickerDialog() {
        // Mostrar un diálogo para que el usuario seleccione la fuente de la imagen (galería o cámara)
        // Aquí debes implementar el código para mostrar el diálogo y manejar la selección del usuario

        // Por ejemplo, puedes usar un AlertDialog para mostrar las opciones:
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleccionar imagen");
        builder.setItems(new CharSequence[]{"Galería", "Cámara"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        // Abrir la galería
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, REQUEST_IMAGE_GALLERY);
                        break;
                    case 1:
                        // Abrir la cámara
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, REQUEST_IMAGE_CAMERA);
                        break;
                }
            }
        });
        builder.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_GALLERY && data != null) {
                Uri imageUri = data.getData();

                UCrop.Options options = new UCrop.Options();
                options.setCircleDimmedLayer(true);
                options.setShowCropFrame(true);
                options.setToolbarColor(ContextCompat.getColor(this, R.color.md_theme_light_onPrimary));
                options.setStatusBarColor(ContextCompat.getColor(this, R.color.md_theme_light_onPrimary));
                options.setCompressionFormat(Bitmap.CompressFormat.PNG);
                options.setCompressionQuality(100);

                UCrop uCrop = UCrop.of(imageUri, Uri.fromFile(new File(getCacheDir(), "cropped_image")))
                        .withOptions(options)
                        .withAspectRatio(1, 1)
                        .withMaxResultSize(500, 500);

                uCrop.start(this);
            } else if (requestCode == UCrop.REQUEST_CROP && data != null) {
                final Uri resultUri = UCrop.getOutput(data);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
                    imageView.setImageBitmap(bitmap);

                    // Subir la imagen al servidor
                    uploadImage(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_IMAGE_CAMERA && data != null) {
                // Aquí puedes manejar la imagen capturada por la cámara si deseas
            } else if (resultCode == UCrop.RESULT_ERROR) {
                final Throwable cropError = UCrop.getError(data);
                // Manejar el error de recorte de imagen
            }
        }
    }

    //CAMBIOOOOOOOOOOOOOOOOOOOOOOOOO222222

    private void uploadImage(Bitmap bitmap) {
        // Codificar la imagen en Base64
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        final String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        String name = nombre; // Reemplaza con los datos reales
        String email = correo; // Reemplaza con los datos reales

        String url = getString(R.string.url)+"subirImagenUsuario.php";

        // Crear una solicitud POST usando StringRequest de Volley
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Manejar la respuesta del servidor
                Toast.makeText(Mi_Perfil_Activity.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Manejar el error de la solicitud
                Toast.makeText(Mi_Perfil_Activity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println("ERROOOOOOOR:   "+error.getMessage());
            }
        }) {
            @NonNull
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Parámetros de la solicitud POST
                Map<String, String> params = new HashMap<>();
                params.put("foto", encodedImage);
                params.put("nombre", name);
                params.put("correo", email);
                return params;
            }
        };

        // Agregar la solicitud a la cola de solicitudes
        requestQueue.add(request);

        // Mostrar la imagen redonda en el ImageView
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        roundedBitmapDrawable.setCircular(true);
        imageView.setImageDrawable(roundedBitmapDrawable);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_IMAGE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso de la galería concedido, abrir la galería o la cámara
                showImagePickerDialog();
            } else {
                // Permiso de la galería denegado, mostrar un mensaje o realizar alguna acción adicional si es necesario
                Toast.makeText(this, "Permiso de la galería denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}