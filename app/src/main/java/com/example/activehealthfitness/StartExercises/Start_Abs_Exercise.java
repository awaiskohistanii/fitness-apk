package com.example.activehealthfitness.StartExercises;

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

public class Start_Abs_Exercise extends AppCompatActivity {
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

    Dialog dialog;

    // This Function "onCreate" Run First
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_abs_exercise);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.card_color));
        }

        textViewExName = findViewById(R.id.start_exe_name);
        mTextViewCounterDown = findViewById(R.id.text_view_counter);
        floatingActionButton = findViewById(R.id.start_pause_btn);
        lottieAnimationView = findViewById(R.id.start_exe_animation);
        progressBar = findViewById(R.id.start_progress_bar);
        ////////////////////////////////////////////////////////////////////////////////////////////
        floatingActionButtonSkip = findViewById(R.id.skip_next_btn);
        floatingActionButtonPrevious = findViewById(R.id.skip_previous_btn);
        // Skip Next Exercise Button
        floatingActionButtonSkip.setOnClickListener(view -> {
            currentFlag++;
            finishExercise(currentFlag);
        });
        // Skip Previous Exercise Button
        floatingActionButtonPrevious.setOnClickListener(view -> {
            if (currentFlag <= 0) {
                Toast.makeText(Start_Abs_Exercise.this, "No Skip Previous", Toast.LENGTH_SHORT).show();
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
            getSupportActionBar().setTitle("Abs");
        }
        ////////////////// for ToolBar ///////////////////////

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
                exerciseStart(R.raw.jumping_jack, "1. Jumping Jack");
            } else if (currentFlag == 1) {
                exerciseStart(R.raw.inchworm, "2. Inchworm");
            } else if (currentFlag == 2) {
                exerciseStart(R.raw.reverse_crunches, "3. Reverse Crunches");
            } else if (currentFlag == 3) {
                exerciseStart(R.raw.wide_arm_push_up, "4. Wide Arm Push Ups");
            } else if (currentFlag == 4) {
                exerciseStart(R.raw.seated_abs_circles, "5. Seated Abs Circles");
            } else if (currentFlag == 5) {
                exerciseStart(R.raw.staggered_push_ups, "6. Staggered Push Ups");
            } else if (currentFlag == 6) {
                exerciseStart(R.raw.squat_kicks, "7. Squat Kicks");
            } else if (currentFlag == 7) {
                exerciseStart(R.raw.squat_reach, "8. Squat Reach");
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
        if (currentFlag >= 0 && currentFlag < 8) {
            floatingActionButtonSkip.setVisibility(View.VISIBLE);
            floatingActionButtonPrevious.setVisibility(View.VISIBLE);

        } else {
            floatingActionButtonSkip.setVisibility(View.INVISIBLE);
            floatingActionButtonPrevious.setVisibility(View.INVISIBLE);
        }
        // ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
        progressBar.setProgress(currentFlag);
        if (currentFlag == 0) {
            dialogBox("First Exercise is Jumping Jack", R.raw.jumping_jack_voice);
            firstTimeIntro(R.raw.jumping_jack, "1. Jumping Jack");
        } else if (currentFlag == 1) {
            dialogBox("Next Exercise is inchworm", R.raw.inchworm_voice);
            firstTimeIntro(R.raw.inchworm, "2. inchworm");
        } else if (currentFlag == 2) {
            dialogBox("Next Exercise is Reverse Crunches", R.raw.reverse_crunches_voice);
            firstTimeIntro(R.raw.reverse_crunches, "3. Reverse Crunches");
        } else if (currentFlag == 3) {
            dialogBox("Next Exercise is Wide Arm Push Up", R.raw.wide_arm_push_ups_voice);
            firstTimeIntro(R.raw.wide_arm_push_up, "4. Wide Arm Push Ups");
        } else if (currentFlag == 4) {
            dialogBox("Next Exercise is Seated Abs Circle", R.raw.seated_abs_circles_voice);
            firstTimeIntro(R.raw.seated_abs_circles, "5. Seated Abs Circles");
        } else if (currentFlag == 5) {
            dialogBox("Next Exercise is Staggered Push Up", R.raw.straggered_push_ups_voice);
            firstTimeIntro(R.raw.staggered_push_ups, "6. Staggered Push Ups");
        } else if (currentFlag == 6) {
            dialogBox("Next Exercise is Squats Kicks", R.raw.squat_kicks_voice);
            firstTimeIntro(R.raw.squat_kicks, "7. Squat Kicks");
        } else if (currentFlag == 7) {
            dialogBox("Last Exercise is Squat Reach", R.raw.squat_reach_voice);
            firstTimeIntro(R.raw.squat_reach, "8. Squat Reach");
        } else {
            completeExercise();
        }
    }

    public void dialogBox(String bmiDesc, int exNameVoice) {
        dialog = new Dialog(Start_Abs_Exercise.this);
        dialog.setContentView(R.layout.bmi_dialogue_box);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        exerciseNameMediaPlayer = MediaPlayer.create(this, exNameVoice);
        exerciseNameMediaPlayer.start();

        TextView heading = (TextView) dialog.findViewById(R.id.heading);
        heading.setText("Abs Exercise");
        TextView textViewBmi = (TextView) dialog.findViewById(R.id.bmi_show);
        TextView textViewBmiDesc = (TextView) dialog.findViewById(R.id.bmi_des);
        textViewBmi.setText("Exercise Detail");
        textViewBmiDesc.setText(bmiDesc);


        TextView okay = (TextView) dialog.findViewById(R.id.bmi_ok);
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

    public void releaseExerciseNameMediaPlayer() {
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
        if (currentFlag > 7) {
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
        if (mediaPlayer.isPlaying()) {
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
                SchemaContract.CounterEntry.COLUMN_FLAG
        };

        String selection = SchemaContract.CounterEntry.COLUMN_USER_NAME + "=?"; // where USER NAME=username
        String[] selectionArgs = new String[]{MainActivity.userNameSharedPreferencesValue};
        /////////////////////////////////////////////////////////////////////////////////////////

        try (Cursor cursor = db.query(
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
            while (cursor.moveToNext()) {
                // value of ID and Flag stored in this two variable
                //int currentID = cursor.getInt(idColumnIndex);
                currentFlag = cursor.getInt(flagColumnIndex);
            }
        }
    }

    // This User define function is used for update value from database
    private void updateData(int updateValue) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        //cv.put("abs_flag", updateValue);
        cv.put(SchemaContract.CounterEntry.COLUMN_FLAG, updateValue);
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