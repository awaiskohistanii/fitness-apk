package com.example.activehealthfitness.ExerciseDetail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.activehealthfitness.R;

public class Exercise_Detail extends AppCompatActivity {


    Toolbar toolbar;
    LottieAnimationView lottieAnimationView;
    TextView textViewName, textViewDes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            getWindow().setNavigationBarColor(getResources().getColor(R.color.card_color));
        }

        // for ToolBar
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            // for back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            String title = getIntent().getStringExtra("name");
            getSupportActionBar().setTitle(title);
        }


        lottieAnimationView = findViewById(R.id.detail_exercise_animation);
        textViewName = findViewById(R.id.detail_exercise_name);
        textViewDes = findViewById(R.id.detail_exercise_desc);

        int animation = getIntent().getIntExtra("animation", 0);
        lottieAnimationView.setAnimation(animation);
        lottieAnimationView.playAnimation();

        String name = getIntent().getStringExtra("name");
        textViewName.setText(name);

        String desc = getIntent().getStringExtra("desc");
        textViewDes.setText(desc);

    }

    // For Toolbar Back Button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}