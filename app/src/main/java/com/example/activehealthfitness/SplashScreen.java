package com.example.activehealthfitness;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.example.activehealthfitness.Registration.Sign_in;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {

    LinearLayout linearLayoutSplashFooter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //        For Lottie Animation Color Change
        LottieAnimationView animationView = findViewById(R.id.animation_view);
        animationView.addValueCallback(
                new KeyPath("**"),
                LottieProperty.COLOR_FILTER,
                frameInfo -> new PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
        );

        //        For Mobile Navigation Button Background Color Change
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.notification_bar));
        }

        //        For Animation
        linearLayoutSplashFooter = findViewById(R.id.splash_footer);
        Animation alpha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
        linearLayoutSplashFooter.startAnimation(alpha);

        new Handler().postDelayed(() -> {
            Intent intent=new Intent(SplashScreen.this, Sign_in.class);
            startActivity(intent);
            finish();
        },4000);
    }
}