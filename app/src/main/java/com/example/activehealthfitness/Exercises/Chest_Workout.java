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
import com.example.activehealthfitness.StartExercises.Start_Chest_Exercise;

import java.util.ArrayList;

public class Chest_Workout extends AppCompatActivity {
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
        setContentView(R.layout.activity_chest_workout);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            getWindow().setNavigationBarColor(getResources().getColor(R.color.card_color));
        }

        // for ToolBar
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            // for back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Chest");
        }

        // Create an ArrayList of AndroidFlavor objects
        ArrayList<ExerciseWorkout> exerciseWorkout = new ArrayList<>();

        exerciseWorkout.add(new ExerciseWorkout("Wide Arm Push Up", "X30", R.raw.wide_arm_push_up));
        exerciseWorkout.add(new ExerciseWorkout("Dumbbell Punches", "60 Sec", R.raw.punches));
        exerciseWorkout.add(new ExerciseWorkout("Staggered Push Up", "X30", R.raw.staggered_push_ups));
        exerciseWorkout.add(new ExerciseWorkout("Shoulder Stretch", "60 Sec", R.raw.shoulder_stretch));
        exerciseWorkout.add(new ExerciseWorkout("Military Push Up", "X30", R.raw.military_push_ups));
        exerciseWorkout.add(new ExerciseWorkout("Reverse Crunches", "X30", R.raw.reverse_crunches));
        exerciseWorkout.add(new ExerciseWorkout("Jumping Jack", "60 Sec", R.raw.jumping_jack));
        exerciseWorkout.add(new ExerciseWorkout("Squat Reach", "X30", R.raw.squat_reach));

        ExerciseWorkoutAdapter exerciseWorkoutAdapter = new ExerciseWorkoutAdapter(this, exerciseWorkout);
        listView = findViewById(R.id.listview);
        listView.setAdapter(exerciseWorkoutAdapter);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            listView.setEnabled(false);
            if (i==0)
            {
                Intent intent = new Intent(Chest_Workout.this, Exercise_Detail.class);
                intent.putExtra("animation", R.raw.wide_arm_push_up);
                intent.putExtra("name", "Wide Arm Push Up");
                intent.putExtra("desc", getString(R.string.wide_arm_push_up));
                startActivity(intent);
            } else if (i==1)
            {
                Intent intent = new Intent(Chest_Workout.this, Exercise_Detail.class);
                intent.putExtra("animation", R.raw.punches);
                intent.putExtra("name", "Dumbbell Punches");
                intent.putExtra("desc", getString(R.string.dumbbell_punches));
                startActivity(intent);
            } else if (i==2)
            {
                Intent intent = new Intent(Chest_Workout.this, Exercise_Detail.class);
                intent.putExtra("animation", R.raw.staggered_push_ups);
                intent.putExtra("name", "Staggered Push Up");
                intent.putExtra("desc", getString(R.string.staggered_push_up));
                startActivity(intent);
            } else if (i==3)
            {
                Intent intent = new Intent(Chest_Workout.this, Exercise_Detail.class);
                intent.putExtra("animation", R.raw.shoulder_stretch);
                intent.putExtra("name", "Shoulder Stretch");
                intent.putExtra("desc", getString(R.string.shoulder_stretch));
                startActivity(intent);
            } else if (i==4)
            {
                Intent intent = new Intent(Chest_Workout.this, Exercise_Detail.class);
                intent.putExtra("animation", R.raw.military_push_ups);
                intent.putExtra("name", "Military Push Up");
                intent.putExtra("desc", getString(R.string.military_push_up));
                startActivity(intent);
            } else if (i==5)
            {
                Intent intent = new Intent(Chest_Workout.this, Exercise_Detail.class);
                intent.putExtra("animation", R.raw.reverse_crunches);
                intent.putExtra("name", "Reverse Crunches");
                intent.putExtra("desc", getString(R.string.reverse_crunch));
                startActivity(intent);
            } else if (i==6)
            {
                Intent intent = new Intent(Chest_Workout.this, Exercise_Detail.class);
                intent.putExtra("animation", R.raw.jumping_jack);
                intent.putExtra("name", "Jumping Jack");
                intent.putExtra("desc", getString(R.string.jumping_jack));
                startActivity(intent);
            } else
            {
                Intent intent = new Intent(Chest_Workout.this, Exercise_Detail.class);
                intent.putExtra("animation", R.raw.squat_reach);
                intent.putExtra("name", "Squat Reach");
                intent.putExtra("desc", getString(R.string.squat_reach));
                startActivity(intent);
            }
        });

        startExerciseBtn = findViewById(R.id.start_exercise_btn);
        startExerciseBtn.setOnClickListener(view -> startActivity(new Intent(Chest_Workout.this, Start_Chest_Exercise.class)));
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