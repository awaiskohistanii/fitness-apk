package com.example.activehealthfitness.WeeklyChallenge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.activehealthfitness.ExerciseDatabase.DBHelper;
import com.example.activehealthfitness.ExerciseDatabase.SchemaContract;
import com.example.activehealthfitness.MainActivity;
import com.example.activehealthfitness.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class StartThirtyDayExercise extends AppCompatActivity {
    int day1;
    int day2;
    int day3;
    int day4;
    int day5;
    int day6;
    int day7;
    ///////////////////////////////////////////////
    ProgressBar progressBar;
    Toolbar toolbar;
    // For Database ++++++++++++++++++++++++++
    int currentFlag;
    private DBHelper dbHelper;
    // For Database ++++++++++++++++++++++++++
    MediaPlayer mediaPlayer, mediaPlayerMessage, exerciseNameMediaPlayer; // For Music
    LottieAnimationView lottieAnimationView;
    FloatingActionButton floatingActionButton, floatingActionButtonSkip, floatingActionButtonPrevious;
    private static final long START_TIME_IN_MILLIS = 15000; // 5000=5 sec also 10000=10 sec
    private TextView mTextViewCounterDown, textViewExName;
    private CountDownTimer mCounterDownTimer;
    private boolean mTimerRunning; // boolean default value is false
    private long mTImerLeftInMillis = START_TIME_IN_MILLIS;
    String title;
    int firstExerciseAnimation;
    int secondExerciseAnimation;
    int thirdExerciseAnimation;
    int fourthExerciseAnimation;
    int fifthExerciseAnimation;
    int firstExerciseNameVoice;
    int secondExerciseNameVoice;
    int thirdExerciseNameVoice;
    int fourthExerciseNameVoice;
    int fifthExerciseNameVoice;
    String firstExerciseName;
    String secondExerciseName;
    String thirdExerciseName;
    String fourthExerciseName;
    String fifthExerciseName;
    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    Dialog dialog;

    // This Function "onCreate" Run First
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_thirty_day_exercise);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            getWindow().setNavigationBarColor(getResources().getColor(R.color.card_color));
        }

        title = getIntent().getStringExtra("title");

        // For Database ++++++++++++++++++++++++++
        dbHelper = new DBHelper(this);
        displayDatabaseInfo();
        // +++++++++++++++++++++++++++++++++++++++

        // &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
        switch (title) {
            case "Day 1":
                currentFlag = day1;
                break;
            case "Day 2":
                currentFlag = day2;
                break;
            case "Day 3":
                currentFlag = day3;
                break;
            case "Day 4":
                currentFlag = day4;
                break;
            case "Day 5":
                currentFlag = day5;
                break;
            case "Day 6":
                currentFlag = day6;
                break;
            default:
                currentFlag = day7;
                break;
        }
        // &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
        textViewExName = findViewById(R.id.weekly_start_exe_name);
        mTextViewCounterDown = findViewById(R.id.weekly_text_view_counter);
        floatingActionButton = findViewById(R.id.weekly_start_pause_btn);
        lottieAnimationView = findViewById(R.id.weekly_start_exe_animation);
        progressBar = findViewById(R.id.weekly_start_progress_bar);

        ////////////////////////////////////////////////////////////////////////////////////////////
        floatingActionButtonSkip = findViewById(R.id.weekly_skip_next_btn);
        floatingActionButtonPrevious = findViewById(R.id.weekly_skip_previous_btn);
        // Skip Next Exercise Button
        floatingActionButtonSkip.setOnClickListener(view -> {
            currentFlag++;
            finishExercise(currentFlag);
        });
        // Skip Previous Exercise Button
        floatingActionButtonPrevious.setOnClickListener(view -> {
            if (currentFlag <= 0) {
                Toast.makeText(StartThirtyDayExercise.this, "No Skip Previous", Toast.LENGTH_SHORT).show();
            } else {
                currentFlag--;
                finishExercise(currentFlag);
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////// for ToolBar ///////////////////////
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            // for back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(title);
        }
        ////////////////// for ToolBar ///////////////////////

        // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%55

        firstExerciseAnimation = getIntent().getIntExtra("firstExerciseAnimation", 0);
        secondExerciseAnimation = getIntent().getIntExtra("secondExerciseAnimation", 0);
        thirdExerciseAnimation = getIntent().getIntExtra("thirdExerciseAnimation", 0);
        fourthExerciseAnimation = getIntent().getIntExtra("fourthExerciseAnimation", 0);
        fifthExerciseAnimation = getIntent().getIntExtra("fifthExerciseAnimation", 0);
        firstExerciseName = getIntent().getStringExtra("firstExerciseName");
        secondExerciseName = getIntent().getStringExtra("secondExerciseName");
        thirdExerciseName = getIntent().getStringExtra("thirdExerciseName");
        fourthExerciseName = getIntent().getStringExtra("fourthExerciseName");
        fifthExerciseName = getIntent().getStringExtra("fifthExerciseName");

        firstExerciseNameVoice = getIntent().getIntExtra("firstExerciseNameVoice",0);
        secondExerciseNameVoice = getIntent().getIntExtra("secondExerciseNameVoice",0);
        thirdExerciseNameVoice = getIntent().getIntExtra("thirdExerciseNameVoice",0);
        fourthExerciseNameVoice = getIntent().getIntExtra("fourthExerciseNameVoice",0);
        fifthExerciseNameVoice = getIntent().getIntExtra("fifthExerciseNameVoice",0);

        /////////// Prepare and Create Music Player ////////////////////
        mediaPlayer = MediaPlayer.create(this, R.raw.onemin);
        mediaPlayerMessage = MediaPlayer.create(this, R.raw.message);
        /////////// Prepare and Create Music Player ////////////////////

        // For Database ++++++++++++++++++++++++++
        dbHelper = new DBHelper(this);
        displayDatabaseInfo();
        // +++++++++++++++++++++++++++++++++++++++

        ////////////////// Play Pause Button //////////////////
        floatingActionButton.setOnClickListener(view -> {
            // ||||||||||||||||| Skip and Previous Btn ||||||||||||||||||||||
            floatingActionButtonSkip.setVisibility(View.INVISIBLE);
            floatingActionButtonPrevious.setVisibility(View.INVISIBLE);
            // ||||||||||||||||| Skip and Previous Btn ||||||||||||||||||||||
            if (currentFlag == 0) {
                exerciseStart(firstExerciseAnimation, "1. " + firstExerciseName);
            } else if (currentFlag == 1) {
                exerciseStart(secondExerciseAnimation, "2. " + secondExerciseName);
            } else if (currentFlag == 2) {
                exerciseStart(thirdExerciseAnimation, "3. " + thirdExerciseName);
            } else if (currentFlag == 3) {
                exerciseStart(fourthExerciseAnimation, "4. " + fourthExerciseName);
            } else if (currentFlag == 4) {
                exerciseStart(fifthExerciseAnimation, "5. " + fifthExerciseName);
            } else {
                completeExercise();
            }
        });
    }

    // The Function "onStart" Run After "onCreate"
    // This Function "onResume" Run After "onStart"
    @Override
    protected void onResume() {
        super.onResume();
        // ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
        if (currentFlag >= 0 && currentFlag < 5) {
            floatingActionButtonSkip.setVisibility(View.VISIBLE);
            floatingActionButtonPrevious.setVisibility(View.VISIBLE);

        } else {
            floatingActionButtonSkip.setVisibility(View.INVISIBLE);
            floatingActionButtonPrevious.setVisibility(View.INVISIBLE);
        }
        // ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
        progressBar.setProgress(currentFlag);
        if (currentFlag == 0) {
            dialogBox("First Exercise is " + firstExerciseName,firstExerciseNameVoice);
            firstTimeIntro(firstExerciseAnimation, "1. " + firstExerciseName);
        } else if (currentFlag == 1) {
            dialogBox("Next Exercise is " + secondExerciseName,secondExerciseNameVoice);
            firstTimeIntro(secondExerciseAnimation, "2. " + secondExerciseName);
        } else if (currentFlag == 2) {
            dialogBox("Next Exercise is " + thirdExerciseName,thirdExerciseNameVoice);
            firstTimeIntro(thirdExerciseAnimation, "3. " + thirdExerciseName);
        } else if (currentFlag == 3) {
            dialogBox("Next Exercise is " + fourthExerciseName,fourthExerciseNameVoice);
            firstTimeIntro(fourthExerciseAnimation, "4. " + fourthExerciseName);
        } else if (currentFlag == 4) {
            dialogBox("Last Exercise is " + fifthExerciseName,fifthExerciseNameVoice);
            firstTimeIntro(fifthExerciseAnimation, "5. " + fifthExerciseName);
        } else {
            completeExercise();
        }
    }

    public void dialogBox(String bmiDesc,int exNameVoice){
        dialog=new Dialog(StartThirtyDayExercise.this);
        dialog.setContentView(R.layout.bmi_dialogue_box);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        TextView heading=(TextView) dialog.findViewById(R.id.heading);
        heading.setText(title);
        TextView textViewBmi=(TextView) dialog.findViewById(R.id.bmi_show);
        TextView textViewBmiDesc=(TextView) dialog.findViewById(R.id.bmi_des);
        textViewBmi.setText("Exercise Detail");
        textViewBmiDesc.setText(bmiDesc);

        exerciseNameMediaPlayer=MediaPlayer.create(this,exNameVoice);
        exerciseNameMediaPlayer.start();


        TextView okay=(TextView) dialog.findViewById(R.id.bmi_ok);
        okay.setOnClickListener(view -> {
            releaseExerciseNameMediaPlayer();
            dialog.dismiss();
        });

        dialog.show();

        new Handler().postDelayed(() -> {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }, 3000);
    }

    public void releaseExerciseNameMediaPlayer(){
        if (exerciseNameMediaPlayer != null) {
            exerciseNameMediaPlayer.release();
            exerciseNameMediaPlayer = null;
        }
    }

    // This Function "onPause" Run After "onResume"
    @Override
    protected void onPause() {
        super.onPause();
        if (dialog != null && dialog.isShowing()) {
            releaseExerciseNameMediaPlayer();
            dialog.dismiss();
        }
        pauseTimer();
    }

    public void exerciseStart(int mAnimation, String exName) {
        lottieAnimationView.setAnimation(mAnimation);
        lottieAnimationView.playAnimation();
        textViewExName.setText(exName);

        if (mTimerRunning) {
            pauseTimer();
        } else {
            startTimer();
        }
    }

    public void completeExercise() {
        if (currentFlag > 4) {
            lottieAnimationView.setAnimation(R.raw.relax);
            lottieAnimationView.playAnimation();
            mTextViewCounterDown.setText("Rest");
            textViewExName.setText("Complete Exercise");
            floatingActionButton.setImageResource(R.drawable.compl_foreground);
        }
    }

    private void pauseTimer() {
        // ||||||||||||||||| Skip and Previous Btn ||||||||||||||||||||||||||
        floatingActionButtonSkip.setVisibility(View.VISIBLE);
        floatingActionButtonPrevious.setVisibility(View.VISIBLE);
        // ||||||||||||||||| Skip and Previous Btn ||||||||||||||||||||||||||
        if (mCounterDownTimer != null) {
            mCounterDownTimer.cancel();
        }
        mTimerRunning = false;
        floatingActionButton.setImageResource(R.drawable.play_foreground);
        lottieAnimationView.pauseAnimation();
        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    private void startTimer() {
        mTimerRunning = true;
        floatingActionButton.setImageResource(R.drawable.pause_foreground);
        mediaPlayer.start();

        mCounterDownTimer = new CountDownTimer(mTImerLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTImerLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                currentFlag++;
                finishExercise(currentFlag);
            }
        }.start();
    }

    public void finishExercise(int update) {
        ///////////Music Player////////////////////
        releaseMediaPlayer();
        mediaPlayerMessage.start();
        ///////////Music Player////////////////////
        resetTimer();
        mTimerRunning = false;
        floatingActionButton.setImageResource(R.drawable.play_foreground);
        mTextViewCounterDown.setText("Take Rest");
        // For Database ++++++++++++++++++++++++++
        updateData(update); // Method Call and send value For Update
        displayDatabaseInfo(); // Method Call For Fetching value from database and set currentFlag variable
        // +++++++++++++++++++++++++++++++++++++++
        onResume(); // For Next Exercise Dialogue Box
        completeExercise(); // Check Completion Exercise
    }

    public void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            mediaPlayer = MediaPlayer.create(this, R.raw.onemin);
        }
    }

    public void firstTimeIntro(int animation, String exName) {
        lottieAnimationView.setAnimation(animation);
        textViewExName.setText(exName);
    }



    private void resetTimer() {
        mTImerLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
    }

    private void updateCountDownText() {
        int minutes = (int) (mTImerLeftInMillis / 1000) / 60;
        int seconds = (int) (mTImerLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        mTextViewCounterDown.setText(timeLeftFormatted);
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
            /*
            int idColumnIndex = cursor.getColumnIndex(SchemaContract.CounterEntry._ID);
            int flagColumnIndex = cursor.getColumnIndex(SchemaContract.CounterEntry.COLUMN_FLAG);
            */
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
                /////////////////////////////////////////
            }
        }
    }

    // This User define function is used for update value from database
    private void updateData(int updateValue) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        //cv.put("abs_flag", updateValue);
        switch (title) {
            case "Day 1":
                cv.put("day_one_flag", updateValue);
                break;
            case "Day 2":
                cv.put("day_two_flag", updateValue);
                break;
            case "Day 3":
                cv.put("day_three_flag", updateValue);
                break;
            case "Day 4":
                cv.put("day_four_flag", updateValue);
                break;
            case "Day 5":
                cv.put("day_five_flag", updateValue);
                break;
            case "Day 6":
                cv.put("day_six_flag", updateValue);
                break;
            default:
                cv.put("day_seven_flag", updateValue);
                break;
        }
        db.update(SchemaContract.CounterEntry.TABLE_NAME, cv, SchemaContract.CounterEntry.COLUMN_USER_NAME + "=?", new String[]{MainActivity.userNameSharedPreferencesValue});
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