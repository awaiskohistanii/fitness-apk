package com.example.activehealthfitness.Exercises;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.activehealthfitness.ExerciseDetail.Exercise_Detail;
import com.example.activehealthfitness.R;
import com.example.activehealthfitness.RecyclerView.ExerciseWorkout;
import com.example.activehealthfitness.RecyclerView.ExerciseWorkoutAdapter;
import com.example.activehealthfitness.StartExercises.Start_Leg_Exercise;

import java.util.ArrayList;

public class Leg_Workout extends AppCompatActivity {
    Toolbar toolbar;
    TextView startExerciseBtn;
    ListView listView;

    @Override
    protected void onResume() {
        super.onResume();
        listView.setEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leg_workout);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.card_color));
        }

        // for ToolBar
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            // for back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Leg");
        }

        // Create an ArrayList of AndroidFlavor objects
        ArrayList<ExerciseWorkout> exerciseWorkout = new ArrayList<>();

        exerciseWorkout.add(new ExerciseWorkout("Squat Kicks", "X30", R.raw.squat_kicks));
        exerciseWorkout.add(new ExerciseWorkout("Reverse Crunches", "X30", R.raw.reverse_crunches));
        exerciseWorkout.add(new ExerciseWorkout("Inchworm", "X20", R.raw.inchworm));
        exerciseWorkout.add(new ExerciseWorkout("Jumping jack", "60 Sec", R.raw.jumping_jack));
        exerciseWorkout.add(new ExerciseWorkout("Seated Abs Circle", "60 Sec", R.raw.seated_abs_circles));
        exerciseWorkout.add(new ExerciseWorkout("jumping Squats", "X30", R.raw.jumping_squats));
        exerciseWorkout.add(new ExerciseWorkout("Squat Reach", "X30", R.raw.squat_reach));
        exerciseWorkout.add(new ExerciseWorkout("Single Leg Hip", "X30", R.raw.single_leg_hip_rotation));

        ExerciseWorkoutAdapter exerciseWorkoutAdapter = new ExerciseWorkoutAdapter(this, exerciseWorkout);
        listView = findViewById(R.id.listview);
        listView.setAdapter(exerciseWorkoutAdapter);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            listView.setEnabled(false);
            if (i == 0) {
                Intent intent = new Intent(Leg_Workout.this, Exercise_Detail.class);
                intent.putExtra("animation", R.raw.squat_kicks);
                intent.putExtra("name", "Squat Kicks");
                intent.putExtra("desc", getString(R.string.squat_kicks));
                startActivity(intent);
            } else if (i == 1) {
                Intent intent = new Intent(Leg_Workout.this, Exercise_Detail.class);
                intent.putExtra("animation", R.raw.reverse_crunches);
                intent.putExtra("name", "Reverse Crunches");
                intent.putExtra("desc", getString(R.string.reverse_crunch));
                startActivity(intent);
            } else if (i == 2) {
                Intent intent = new Intent(Leg_Workout.this, Exercise_Detail.class);
                intent.putExtra("animation", R.raw.inchworm);
                intent.putExtra("name", "Inchworm");
                intent.putExtra("desc", getString(R.string.inchworm));
                startActivity(intent);
            } else if (i == 3) {
                Intent intent = new Intent(Leg_Workout.this, Exercise_Detail.class);
                intent.putExtra("animation", R.raw.jumping_jack);
                intent.putExtra("name", "Jumping jack");
                intent.putExtra("desc", getString(R.string.jumping_jack));
                startActivity(intent);
            } else if (i == 4) {
                Intent intent = new Intent(Leg_Workout.this, Exercise_Detail.class);
                intent.putExtra("animation", R.raw.seated_abs_circles);
                intent.putExtra("name", "Seated Abs Circle");
                intent.putExtra("desc", getString(R.string.seated_abs_circle));
                startActivity(intent);
            } else if (i == 5) {
                Intent intent = new Intent(Leg_Workout.this, Exercise_Detail.class);
                intent.putExtra("animation", R.raw.jumping_squats);
                intent.putExtra("name", "jumping Squats");
                intent.putExtra("desc", getString(R.string.jumping_squats));
                startActivity(intent);
            } else if (i == 6) {
                Intent intent = new Intent(Leg_Workout.this, Exercise_Detail.class);
                intent.putExtra("animation", R.raw.squat_reach);
                intent.putExtra("name", "Squat Reach");
                intent.putExtra("desc", getString(R.string.squat_reach));
                startActivity(intent);
            } else {
                Intent intent = new Intent(Leg_Workout.this, Exercise_Detail.class);
                intent.putExtra("animation", R.raw.single_leg_hip_rotation);
                intent.putExtra("name", "Single Leg Hip");
                intent.putExtra("desc", getString(R.string.single_leg_hip));
                startActivity(intent);
            }
        });

        startExerciseBtn = findViewById(R.id.start_exercise_btn);
        startExerciseBtn.setOnClickListener(view -> startActivity(new Intent(Leg_Workout.this, Start_Leg_Exercise.class)));
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