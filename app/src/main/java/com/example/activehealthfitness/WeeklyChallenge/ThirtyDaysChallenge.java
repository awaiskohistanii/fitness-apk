package com.example.activehealthfitness.WeeklyChallenge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.activehealthfitness.ExerciseDatabase.DBHelper;
import com.example.activehealthfitness.ExerciseDatabase.SchemaContract;
import com.example.activehealthfitness.MainActivity;
import com.example.activehealthfitness.R;
import com.example.activehealthfitness.RecyclerView.ThirtyDayWorkout;
import com.example.activehealthfitness.RecyclerView.ThirtyDayWorkoutAdapter;

import java.util.ArrayList;

public class ThirtyDaysChallenge extends AppCompatActivity {

    private DBHelper dbHelper;
    ProgressBar progressBar;
    TextView textViewProgress,textViewDaysLeft;
    int day1;
    int day2;
    int day3;
    int day4;
    int day5;
    int day6;
    int day7;
    Toolbar toolbar;
    ListView listView;
    ArrayList<ThirtyDayWorkout> thirtyDayWorkouts;
    ThirtyDayWorkoutAdapter thirtyDayWorkoutAdapter;

    @Override
    protected void onResume() {
        super.onResume();

        // For Database ++++++++++++++++++++++++++
        dbHelper = new DBHelper(this);
        displayDatabaseInfo();
        // +++++++++++++++++++++++++++++++++++++++
        checkProgress();

        /////// for listview //
        thirtyDayWorkouts.clear();
        initializeListFirstWeak();
        thirtyDayWorkoutAdapter.notifyDataSetChanged();
        /////// for listview //

        listView.setEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thirty_days_challenge);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            getWindow().setNavigationBarColor(getResources().getColor(R.color.card_color));
        }

        // For Database ++++++++++++++++++++++++++
        dbHelper = new DBHelper(this);
        displayDatabaseInfo();
        // +++++++++++++++++++++++++++++++++++++++

        textViewProgress=findViewById(R.id.thirty_progress_text);
        textViewDaysLeft=findViewById(R.id.thirty_days_left_text);
        progressBar=findViewById(R.id.thirty_progress_bar);
        checkProgress();


        // for ToolBar
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            // for back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Weekly Challenge");
        }

        ///////////////////////////////////// Weak 1 //////////////////////////////////////////////
        thirtyDayWorkouts = new ArrayList<>();
        initializeListFirstWeak();
        thirtyDayWorkoutAdapter = new ThirtyDayWorkoutAdapter(this, thirtyDayWorkouts);
        listView = findViewById(R.id.thirty_day_listview_1);
        listView.setAdapter(thirtyDayWorkoutAdapter);


        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            if (i == 0) {
                if (day1>=0){
                    listView.setEnabled(false);
                    Intent intent = new Intent(ThirtyDaysChallenge.this, DayExercises.class);
                    intent.putExtra("title", "Day 1");
                    // Intent Animation
                    intent.putExtra("firstExerciseAnimation", R.raw.jumping_jack);
                    intent.putExtra("secondExerciseAnimation", R.raw.inchworm);
                    intent.putExtra("thirdExerciseAnimation", R.raw.reverse_crunches);
                    intent.putExtra("fourthExerciseAnimation", R.raw.wide_arm_push_up);
                    intent.putExtra("fifthExerciseAnimation", R.raw.seated_abs_circles);
                    // Intent Voice
                    intent.putExtra("firstExerciseNameVoice", R.raw.jumping_jack_voice);
                    intent.putExtra("secondExerciseNameVoice", R.raw.inchworm_voice);
                    intent.putExtra("thirdExerciseNameVoice", R.raw.reverse_crunches_voice);
                    intent.putExtra("fourthExerciseNameVoice", R.raw.wide_arm_push_ups_voice);
                    intent.putExtra("fifthExerciseNameVoice", R.raw.seated_abs_circles_voice);
                    // Intent Name
                    intent.putExtra("firstExerciseName", "Jumping Jack");
                    intent.putExtra("secondExerciseName", "Inchworm");
                    intent.putExtra("thirdExerciseName", "Reverse Crunches");
                    intent.putExtra("fourthExerciseName", "Wide Arm Push Up");
                    intent.putExtra("fifthExerciseName", "Seated Abs Circles");
                    // Intent Desc
                    intent.putExtra("firstExerciseDuration", "60 Sec");
                    intent.putExtra("secondExerciseDuration", "X20");
                    intent.putExtra("thirdExerciseDuration", "X30");
                    intent.putExtra("fourthExerciseDuration", "X30");
                    intent.putExtra("fifthExerciseDuration", "60 Sec");
                    startActivity(intent);
                }

            } else if (i == 1) {
                if (day1==5){
                    listView.setEnabled(false);
                    Intent intent = new Intent(ThirtyDaysChallenge.this, DayExercises.class);
                    intent.putExtra("title", "Day 2");
                    intent.putExtra("firstExerciseAnimation", R.raw.staggered_push_ups);
                    intent.putExtra("secondExerciseAnimation", R.raw.squat_kicks);
                    intent.putExtra("thirdExerciseAnimation", R.raw.squat_reach);
                    intent.putExtra("fourthExerciseAnimation", R.raw.wide_arm_push_up);
                    intent.putExtra("fifthExerciseAnimation", R.raw.seated_abs_circles);
                    // Intent Voice
                    intent.putExtra("firstExerciseNameVoice",R.raw.straggered_push_ups_voice);
                    intent.putExtra("secondExerciseNameVoice",R.raw.squat_kicks_voice);
                    intent.putExtra("thirdExerciseNameVoice",R.raw.squat_reach_voice);
                    intent.putExtra("fourthExerciseNameVoice",R.raw.wide_arm_push_ups_voice);
                    intent.putExtra("fifthExerciseNameVoice",R.raw.punches_voice);
                    // Intent Name
                    intent.putExtra("firstExerciseName", "Staggered Push Ups");
                    intent.putExtra("secondExerciseName", "Squat Kicks");
                    intent.putExtra("thirdExerciseName", "Squat Reach");
                    intent.putExtra("fourthExerciseName", "Wide Arm Push Up");
                    intent.putExtra("fifthExerciseName", "Dumbbell Punches");
                    // Intent Desc
                    intent.putExtra("firstExerciseDuration", "X30");
                    intent.putExtra("secondExerciseDuration", "X30");
                    intent.putExtra("thirdExerciseDuration", "X30");
                    intent.putExtra("fourthExerciseDuration", "X30");
                    intent.putExtra("fifthExerciseDuration", "60 Sec");
                    startActivity(intent);
                } else {
                    Toast.makeText(ThirtyDaysChallenge.this, "Please Complete Previous Exercise", Toast.LENGTH_SHORT).show();
                }

            }else if (i == 2) {

                if (day2==5) {
                    listView.setEnabled(false);
                    Intent intent = new Intent(ThirtyDaysChallenge.this, DayExercises.class);
                    intent.putExtra("title", "Day 3");
                    // Intent Animation
                    intent.putExtra("firstExerciseAnimation", R.raw.staggered_push_ups);
                    intent.putExtra("secondExerciseAnimation", R.raw.shoulder_stretch);
                    intent.putExtra("thirdExerciseAnimation", R.raw.military_push_ups);
                    intent.putExtra("fourthExerciseAnimation", R.raw.reverse_crunches);
                    intent.putExtra("fifthExerciseAnimation", R.raw.seated_abs_circles);
                    // Intent Voice
                    intent.putExtra("firstExerciseNameVoice",R.raw.straggered_push_ups_voice);
                    intent.putExtra("secondExerciseNameVoice",R.raw.shoulder_stretch_voice);
                    intent.putExtra("thirdExerciseNameVoice",R.raw.military_push_ups_voice);
                    intent.putExtra("fourthExerciseNameVoice",R.raw.reverse_crunches_voice);
                    intent.putExtra("fifthExerciseNameVoice",R.raw.jumping_jack_voice);
                    // Intent Name
                    intent.putExtra("firstExerciseName", "Staggered Push Ups");
                    intent.putExtra("secondExerciseName", "shoulder Stretch");
                    intent.putExtra("thirdExerciseName", "Military Push Ups");
                    intent.putExtra("fourthExerciseName", "Reverse Crunches");
                    intent.putExtra("fifthExerciseName", "Jumping Jack");
                    // Intent Desc
                    intent.putExtra("firstExerciseDuration", "X30");
                    intent.putExtra("secondExerciseDuration", "60 Sec");
                    intent.putExtra("thirdExerciseDuration", "X30");
                    intent.putExtra("fourthExerciseDuration", "X30");
                    intent.putExtra("fifthExerciseDuration", "60 Sec");
                    startActivity(intent);
                }else {
                    Toast.makeText(ThirtyDaysChallenge.this, "Please Complete Previous Exercise", Toast.LENGTH_SHORT).show();
                }
            }else if (i == 3) {
                if (day3==5) {
                    listView.setEnabled(false);
                    Intent intent = new Intent(ThirtyDaysChallenge.this, DayExercises.class);
                    intent.putExtra("title", "Day 4");
                    // intent Animation
                    intent.putExtra("firstExerciseAnimation", R.raw.squat_reach);
                    intent.putExtra("secondExerciseAnimation", R.raw.punches);
                    intent.putExtra("thirdExerciseAnimation", R.raw.military_push_ups);
                    intent.putExtra("fourthExerciseAnimation", R.raw.jumping_jack);
                    intent.putExtra("fifthExerciseAnimation", R.raw.t_plank_excercise);
                    // Intent Voice
                    intent.putExtra("firstExerciseNameVoice",R.raw.squat_reach_voice);
                    intent.putExtra("secondExerciseNameVoice",R.raw.punches_voice);
                    intent.putExtra("thirdExerciseNameVoice",R.raw.military_push_ups_voice);
                    intent.putExtra("fourthExerciseNameVoice",R.raw.jumping_jack_voice);
                    intent.putExtra("fifthExerciseNameVoice",R.raw.t_plank_exercise_voice);
                    // Intent Name
                    intent.putExtra("firstExerciseName", "Squat Reach");
                    intent.putExtra("secondExerciseName", "Dumbbell Punches");
                    intent.putExtra("thirdExerciseName", "Military Push Ups");
                    intent.putExtra("fourthExerciseName", "Jumping Jack");
                    intent.putExtra("fifthExerciseName", "T Plank");
                    // Intent Desc
                    intent.putExtra("firstExerciseDuration", "X30");
                    intent.putExtra("secondExerciseDuration", "60 Sec");
                    intent.putExtra("thirdExerciseDuration", "X30");
                    intent.putExtra("fourthExerciseDuration", "60 Sec");
                    intent.putExtra("fifthExerciseDuration", "X30");
                    startActivity(intent);
                } else {
                    Toast.makeText(ThirtyDaysChallenge.this, "Please Complete Previous Exercise", Toast.LENGTH_SHORT).show();
                }
            }else if (i == 4) {
                if (day4==5) {
                    listView.setEnabled(false);
                    Intent intent = new Intent(ThirtyDaysChallenge.this, DayExercises.class);
                    intent.putExtra("title", "Day 5");
                    // Intent Animation
                    intent.putExtra("firstExerciseAnimation", R.raw.staggered_push_ups);
                    intent.putExtra("secondExerciseAnimation", R.raw.shoulder_stretch);
                    intent.putExtra("thirdExerciseAnimation", R.raw.inchworm);
                    intent.putExtra("fourthExerciseAnimation", R.raw.squat_reach);
                    intent.putExtra("fifthExerciseAnimation", R.raw.seated_abs_circles);
                    // Intent Voice
                    intent.putExtra("firstExerciseNameVoice",R.raw.straggered_push_ups_voice);
                    intent.putExtra("secondExerciseNameVoice",R.raw.shoulder_stretch_voice);
                    intent.putExtra("thirdExerciseNameVoice",R.raw.inchworm_voice);
                    intent.putExtra("fourthExerciseNameVoice",R.raw.squat_reach_voice);
                    intent.putExtra("fifthExerciseNameVoice",R.raw.seated_abs_circles_voice);
                    // Intent Name
                    intent.putExtra("firstExerciseName", "Staggered push Ups");
                    intent.putExtra("secondExerciseName", "Shoulder Stretch");
                    intent.putExtra("thirdExerciseName", "Inchworm");
                    intent.putExtra("fourthExerciseName", "squat Reach");
                    intent.putExtra("fifthExerciseName", "Seated Abs Circles");
                    // Intent Desc
                    intent.putExtra("firstExerciseDuration", "X30");
                    intent.putExtra("secondExerciseDuration", "60 Sec");
                    intent.putExtra("thirdExerciseDuration", "X30");
                    intent.putExtra("fourthExerciseDuration", "X30");
                    intent.putExtra("fifthExerciseDuration", "60 Sec");
                    startActivity(intent);
                } else {
                    Toast.makeText(ThirtyDaysChallenge.this, "Please Complete Previous Exercise", Toast.LENGTH_SHORT).show();
                }
            }else if (i == 5) {
                if (day5==5) {
                    listView.setEnabled(false);
                    Intent intent = new Intent(ThirtyDaysChallenge.this, DayExercises.class);
                    intent.putExtra("title", "Day 6");
                    // Intent Animation
                    intent.putExtra("firstExerciseAnimation", R.raw.reverse_crunches);
                    intent.putExtra("secondExerciseAnimation", R.raw.seated_abs_circles);
                    intent.putExtra("thirdExerciseAnimation", R.raw.jumping_squats);
                    intent.putExtra("fourthExerciseAnimation", R.raw.squat_reach);
                    intent.putExtra("fifthExerciseAnimation", R.raw.single_leg_hip_rotation);
                    // Intent Voice
                    intent.putExtra("firstExerciseNameVoice",R.raw.reverse_crunches_voice);
                    intent.putExtra("secondExerciseNameVoice",R.raw.seated_abs_circles_voice);
                    intent.putExtra("thirdExerciseNameVoice",R.raw.jumping_squats_voice);
                    intent.putExtra("fourthExerciseNameVoice",R.raw.squat_reach_voice);
                    intent.putExtra("fifthExerciseNameVoice",R.raw.single_leg_hip_voice);
                    // Intent Name
                    intent.putExtra("firstExerciseName", "Reverse Crunches");
                    intent.putExtra("secondExerciseName", "Seated Abs Circles");
                    intent.putExtra("thirdExerciseName", "jumping Squats");
                    intent.putExtra("fourthExerciseName", "Squat Reach");
                    intent.putExtra("fifthExerciseName", "Single Leg Hip");
                    // Intent Desc
                    intent.putExtra("firstExerciseDuration", "X30");
                    intent.putExtra("secondExerciseDuration", "60 Sec");
                    intent.putExtra("thirdExerciseDuration", "X30");
                    intent.putExtra("fourthExerciseDuration", "X30");
                    intent.putExtra("fifthExerciseDuration", "X30");
                    startActivity(intent);
                } else {
                    Toast.makeText(ThirtyDaysChallenge.this, "Please Complete Previous Exercise", Toast.LENGTH_SHORT).show();
                }
            }else {
                if (day6==5) {
                    listView.setEnabled(false);
                    Intent intent = new Intent(ThirtyDaysChallenge.this, DayExercises.class);
                    intent.putExtra("title", "Day 7");
                    // Intent Animation
                    intent.putExtra("firstExerciseAnimation", R.raw.wide_arm_push_up);
                    intent.putExtra("secondExerciseAnimation", R.raw.t_plank_excercise);
                    intent.putExtra("thirdExerciseAnimation", R.raw.reverse_crunches);
                    intent.putExtra("fourthExerciseAnimation", R.raw.military_push_ups);
                    intent.putExtra("fifthExerciseAnimation", R.raw.single_leg_hip_rotation);
                    // Intent Voice
                    intent.putExtra("firstExerciseNameVoice",R.raw.wide_arm_push_ups_voice);
                    intent.putExtra("secondExerciseNameVoice",R.raw.t_plank_exercise_voice);
                    intent.putExtra("thirdExerciseNameVoice",R.raw.reverse_crunches_voice);
                    intent.putExtra("fourthExerciseNameVoice",R.raw.military_push_ups_voice);
                    intent.putExtra("fifthExerciseNameVoice",R.raw.single_leg_hip_voice);
                    // Intent Name
                    intent.putExtra("firstExerciseName", "Wide Arm Push Ups");
                    intent.putExtra("secondExerciseName", "T Plank");
                    intent.putExtra("thirdExerciseName", "Reverse Crunches");
                    intent.putExtra("fourthExerciseName", "Military Push Ups");
                    intent.putExtra("fifthExerciseName", "Single Leg Hip");
                    // Intent Desc
                    intent.putExtra("firstExerciseDuration", "X30");
                    intent.putExtra("secondExerciseDuration", "X30");
                    intent.putExtra("thirdExerciseDuration", "X30");
                    intent.putExtra("fourthExerciseDuration", "X30");
                    intent.putExtra("fifthExerciseDuration", "X30");
                    startActivity(intent);
                }else {
                    Toast.makeText(ThirtyDaysChallenge.this, "Please Complete Previous Exercise", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void initializeListFirstWeak() {
        thirtyDayWorkouts.add(new ThirtyDayWorkout("Day 1", day1));
        thirtyDayWorkouts.add(new ThirtyDayWorkout("Day 2", day2));
        thirtyDayWorkouts.add(new ThirtyDayWorkout("Day 3", day3));
        thirtyDayWorkouts.add(new ThirtyDayWorkout("Day 4", day4));
        thirtyDayWorkouts.add(new ThirtyDayWorkout("Day 5", day5));
        thirtyDayWorkouts.add(new ThirtyDayWorkout("Day 6", day6));
        thirtyDayWorkouts.add(new ThirtyDayWorkout("Day 7", day7));

    }

    //++++++++++++++++++++++++++++++++++++++++++++++ Database +++++++++++++++++++++++++++++++++++++++
    /////////////// This Function is used for fetch data from Database /////////////////////
    private void displayDatabaseInfo() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                SchemaContract.CounterEntry._ID,
                SchemaContract.CounterEntry.COLUMN_FLAG,
                /////////////////////////////////////////////
                SchemaContract.CounterEntry.COLUMN_FLAG_DAY_1,
                SchemaContract.CounterEntry.COLUMN_FLAG_DAY_2,
                SchemaContract.CounterEntry.COLUMN_FLAG_DAY_3,
                SchemaContract.CounterEntry.COLUMN_FLAG_DAY_4,
                SchemaContract.CounterEntry.COLUMN_FLAG_DAY_5,
                SchemaContract.CounterEntry.COLUMN_FLAG_DAY_6,
                SchemaContract.CounterEntry.COLUMN_FLAG_DAY_7
                ////////////////////////////////////////////
        };
        String selection = SchemaContract.CounterEntry.COLUMN_USER_NAME + "=?"; // where USER NAME=username
        String[] selectionArgs = new String[]{MainActivity.userNameSharedPreferencesValue};

        try(Cursor cursor = db.query(
                SchemaContract.CounterEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        )) {
            // fetch Value from table ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            int idColumnIndex = cursor.getColumnIndex(SchemaContract.CounterEntry._ID);
            int flagColumnIndex = cursor.getColumnIndex(SchemaContract.CounterEntry.COLUMN_FLAG);
            ////////////////////////////////////////////
            int flagColumnIndexDay1 = cursor.getColumnIndex(SchemaContract.CounterEntry.COLUMN_FLAG_DAY_1);
            int flagColumnIndexDay2 = cursor.getColumnIndex(SchemaContract.CounterEntry.COLUMN_FLAG_DAY_2);
            int flagColumnIndexDay3 = cursor.getColumnIndex(SchemaContract.CounterEntry.COLUMN_FLAG_DAY_3);
            int flagColumnIndexDay4 = cursor.getColumnIndex(SchemaContract.CounterEntry.COLUMN_FLAG_DAY_4);
            int flagColumnIndexDay5 = cursor.getColumnIndex(SchemaContract.CounterEntry.COLUMN_FLAG_DAY_5);
            int flagColumnIndexDay6 = cursor.getColumnIndex(SchemaContract.CounterEntry.COLUMN_FLAG_DAY_6);
            int flagColumnIndexDay7 = cursor.getColumnIndex(SchemaContract.CounterEntry.COLUMN_FLAG_DAY_7);

            ////////////////////////////////////////////
            while (cursor.moveToNext()) {
                // value of ID and Flag stored in this two variable
                day1 = cursor.getInt(flagColumnIndexDay1);
                day2 = cursor.getInt(flagColumnIndexDay2);
                day3 = cursor.getInt(flagColumnIndexDay3);
                day4 = cursor.getInt(flagColumnIndexDay4);
                day5 = cursor.getInt(flagColumnIndexDay5);
                day6 = cursor.getInt(flagColumnIndexDay6);
                day7 = cursor.getInt(flagColumnIndexDay7);
            }
        }
    }

    public void checkProgress(){

        if (day1==5)
        {
            setProgressBar(14,"14%","06 Days Left");
        }
        if (day2==5)
        {
            setProgressBar(28,"28%","05 Days Left");
        }
        if (day3==5)
        {
            setProgressBar(42,"42%","04 Days Left");
        }
        if (day4==5)
        {
            setProgressBar(56,"56%","03 Days Left");
        }
        if (day5==5)
        {
            setProgressBar(70,"70%","02 Days Left");
        }
        if (day6==5)
        {
            setProgressBar(84,"84%","01 Days Left");
        }
        if (day7==5)
        {
            setProgressBar(100,"100%","0 Days Left");
        }
    }

    private  void setProgressBar(int progressVal,String progressText,String daysLeft){
        progressBar.setProgress(progressVal);
        textViewProgress.setText(progressText);
        textViewDaysLeft.setText(daysLeft);
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