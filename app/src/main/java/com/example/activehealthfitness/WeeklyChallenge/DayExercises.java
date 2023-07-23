package com.example.activehealthfitness.WeeklyChallenge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.activehealthfitness.R;
import com.example.activehealthfitness.RecyclerView.ExerciseWorkout;
import com.example.activehealthfitness.RecyclerView.ExerciseWorkoutAdapter;

import java.util.ArrayList;

public class DayExercises extends AppCompatActivity {
    Toolbar toolbar;
    ListView listView;
    TextView startExerciseBtn;

    @Override
    protected void onResume() {
        super.onResume();
        startExerciseBtn.setEnabled(true);
        listView.setEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_exercises);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.card_color));
        }

        String title = getIntent().getStringExtra("title");
        // for ToolBar
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            // for back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(title);
        }

        int firstExerciseAnimation = getIntent().getIntExtra("firstExerciseAnimation", 0);
        int secondExerciseAnimation = getIntent().getIntExtra("secondExerciseAnimation", 0);
        int thirdExerciseAnimation = getIntent().getIntExtra("thirdExerciseAnimation", 0);
        int fourthExerciseAnimation = getIntent().getIntExtra("fourthExerciseAnimation", 0);
        int fifthExerciseAnimation = getIntent().getIntExtra("fifthExerciseAnimation", 0);
        String firstExerciseName = getIntent().getStringExtra("firstExerciseName");
        String secondExerciseName = getIntent().getStringExtra("secondExerciseName");
        String thirdExerciseName = getIntent().getStringExtra("thirdExerciseName");
        String fourthExerciseName = getIntent().getStringExtra("fourthExerciseName");
        String fifthExerciseName = getIntent().getStringExtra("fifthExerciseName");
        int firstExerciseNameVoice = getIntent().getIntExtra("firstExerciseNameVoice", 0);
        int secondExerciseNameVoice = getIntent().getIntExtra("secondExerciseNameVoice", 0);
        int thirdExerciseNameVoice = getIntent().getIntExtra("thirdExerciseNameVoice", 0);
        int fourthExerciseNameVoice = getIntent().getIntExtra("fourthExerciseNameVoice", 0);
        int fifthExerciseNameVoice = getIntent().getIntExtra("fifthExerciseNameVoice", 0);
        String firstExerciseDuration = getIntent().getStringExtra("firstExerciseDuration");
        String secondExerciseDuration = getIntent().getStringExtra("secondExerciseDuration");
        String thirdExerciseDuration = getIntent().getStringExtra("thirdExerciseDuration");
        String fourthExerciseDuration = getIntent().getStringExtra("fourthExerciseDuration");
        String fifthExerciseDuration = getIntent().getStringExtra("fifthExerciseDuration");

        ////////////////////////////////////////////////////////////////////////////////////////////////

        // Create an ArrayList of AndroidFlavor objects
        ArrayList<ExerciseWorkout> exerciseWorkout = new ArrayList<>();

        exerciseWorkout.add(new ExerciseWorkout(firstExerciseName, firstExerciseDuration, firstExerciseAnimation));
        exerciseWorkout.add(new ExerciseWorkout(secondExerciseName, secondExerciseDuration, secondExerciseAnimation));
        exerciseWorkout.add(new ExerciseWorkout(thirdExerciseName, thirdExerciseDuration, thirdExerciseAnimation));
        exerciseWorkout.add(new ExerciseWorkout(fourthExerciseName, fourthExerciseDuration, fourthExerciseAnimation));
        exerciseWorkout.add(new ExerciseWorkout(fifthExerciseName, fifthExerciseDuration, fifthExerciseAnimation));


        ExerciseWorkoutAdapter exerciseWorkoutAdapter = new ExerciseWorkoutAdapter(this, exerciseWorkout);
        listView = findViewById(R.id.listview);
        listView.setAdapter(exerciseWorkoutAdapter);

        startExerciseBtn = findViewById(R.id.start_exercise_btn);
        startExerciseBtn.setOnClickListener(view -> {
            startExerciseBtn.setEnabled(false);
            Intent intent = new Intent(new Intent(DayExercises.this, StartThirtyDayExercise.class));
            intent.putExtra("title", title);
            intent.putExtra("firstExerciseAnimation", firstExerciseAnimation);
            intent.putExtra("secondExerciseAnimation", secondExerciseAnimation);
            intent.putExtra("thirdExerciseAnimation", thirdExerciseAnimation);
            intent.putExtra("fourthExerciseAnimation", fourthExerciseAnimation);
            intent.putExtra("fifthExerciseAnimation", fifthExerciseAnimation);
            intent.putExtra("firstExerciseName", firstExerciseName);
            intent.putExtra("secondExerciseName", secondExerciseName);
            intent.putExtra("thirdExerciseName", thirdExerciseName);
            intent.putExtra("fourthExerciseName", fourthExerciseName);
            intent.putExtra("fifthExerciseName", fifthExerciseName);

            intent.putExtra("firstExerciseNameVoice", firstExerciseNameVoice);
            intent.putExtra("secondExerciseNameVoice", secondExerciseNameVoice);
            intent.putExtra("thirdExerciseNameVoice", thirdExerciseNameVoice);
            intent.putExtra("fourthExerciseNameVoice", fourthExerciseNameVoice);
            intent.putExtra("fifthExerciseNameVoice", fifthExerciseNameVoice);

            startActivity(intent);
        });
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