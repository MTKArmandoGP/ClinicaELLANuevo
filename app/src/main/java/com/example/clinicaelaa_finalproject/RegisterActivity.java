package com.example.clinicaelaa_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RegisterActivity extends AppCompatActivity {

    private int selectedTab=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final LinearLayout homeLayout=findViewById(R.id.homeLayout);
        final LinearLayout likeLayout=findViewById(R.id.calculadoraLayout);

        final ImageView homeImage=findViewById(R.id.homeImage);
        final ImageView likeImage=findViewById(R.id.calculadoraImage);

        final TextView homeTxt=findViewById(R.id.homeTxt);
        final TextView likeTxt=findViewById(R.id.calculadoraTxt);
        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.registro,registro_usuarios.class,null)
                .commit();

        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.registro,registro_usuarios.class,null)
                        .commit();

                if(selectedTab != 1){
                    likeTxt.setVisibility(View.GONE);

                    likeImage.setImageResource(R.drawable.equipomedico);

                    likeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    homeTxt.setVisibility(View.VISIBLE);
                    homeImage.setImageResource(R.drawable.trabajoenequipo);
                    homeLayout.setBackgroundResource(R.drawable.background_item_menu);

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f,1.f,1f, Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);

                    homeLayout.startAnimation(scaleAnimation);

                    selectedTab=1;

                }


            }
        });
        likeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.registro,registro_doctores.class,null)
                        .commit();

                if(selectedTab != 2){
                    homeTxt.setVisibility(View.GONE);

                    homeImage.setImageResource(R.drawable.trabajoenequipo);

                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    likeTxt.setVisibility(View.VISIBLE);
                    likeImage.setImageResource(R.drawable.equipomedico);
                    likeLayout.setBackgroundResource(R.drawable.background_item_menu);

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f,1.f,1f, Animation.RELATIVE_TO_SELF,1.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);

                    likeLayout.startAnimation(scaleAnimation);

                    selectedTab=2;

                }

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
}