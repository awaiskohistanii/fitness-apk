package com.example.activehealthfitness.Registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.activehealthfitness.ExerciseDatabase.DBHelper;
import com.example.activehealthfitness.ExerciseDatabase.SchemaContract;
import com.example.activehealthfitness.R;

public class Forgot_Password extends AppCompatActivity {
    EditText userNameEditTextForgotPassword, passwordEditTextForgotPassword, conformPasswordEditTextForgotPassword;
    TextView resetPasswordButton;
    private ImageView imageViewHide;
    private ImageView imageViewEye;
    DBHelper dbHelper;


    @Override
    protected void onResume() {
        super.onResume();
        resetPasswordButton.setEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        dbHelper = new DBHelper(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.notification_bar));
        }

        userNameEditTextForgotPassword = findViewById(R.id.usernameEditText_forgot_pass);
        passwordEditTextForgotPassword = findViewById(R.id.passwordEditText_forgot_pass);
        conformPasswordEditTextForgotPassword = findViewById(R.id.confirmPasswordEditText_forgot_pass);
        resetPasswordButton = findViewById(R.id.reset_password_btn);
        imageViewHide = findViewById(R.id.imageViewA_forgot_pass);
        imageViewEye = findViewById(R.id.imageViewB_forgot_pass);


        imageViewHide.setOnClickListener(view -> {
            imageViewHide.setVisibility(View.INVISIBLE);
            imageViewEye.setVisibility(View.VISIBLE);
            passwordEditTextForgotPassword.setInputType(InputType.TYPE_CLASS_TEXT);
            conformPasswordEditTextForgotPassword.setInputType(InputType.TYPE_CLASS_TEXT);
        });

        imageViewEye.setOnClickListener(view -> {
            imageViewEye.setVisibility(View.INVISIBLE);
            imageViewHide.setVisibility(View.VISIBLE);
            passwordEditTextForgotPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            conformPasswordEditTextForgotPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        });

        resetPasswordButton.setOnClickListener(view -> {
            resetPasswordButton.setEnabled(true);
            String username = userNameEditTextForgotPassword.getText().toString();
            String password = passwordEditTextForgotPassword.getText().toString();
            String confirmPassword = conformPasswordEditTextForgotPassword.getText().toString();

            if (username.equals("")) {
                buttonEnable();
                userNameEditTextForgotPassword.setError("Please enter username.");
                userNameEditTextForgotPassword.requestFocus();
            } else {
                if (password.equals("")) {
                    buttonEnable();
                    passwordEditTextForgotPassword.setError("Please Enter Password");
                    passwordEditTextForgotPassword.requestFocus();
                } else {
                    String message = Sign_up.isValidPassword(password);
                    if (message == null) {
                        if (password.equals(confirmPassword)) {
                            boolean validateUserName = dbHelper.checkUsername(username);
                            if (validateUserName) {
                                updatePassword(username, password);
                                finish();
                            } else {
                                buttonEnable();
                                userNameEditTextForgotPassword.setError("Username not exist");
                                userNameEditTextForgotPassword.requestFocus();
                            }
                        } else {
                            buttonEnable();
                            conformPasswordEditTextForgotPassword.setError("Password do not Match");
                            conformPasswordEditTextForgotPassword.requestFocus();
                        }
                    } else {
                        buttonEnable();
                        passwordEditTextForgotPassword.setError(message);
                        passwordEditTextForgotPassword.requestFocus();
                    }
                }
            }
        });
    }

    public void buttonEnable() {
        new Handler().postDelayed(() -> resetPasswordButton.setEnabled(true), 1000);
    }

    private void updatePassword(String username, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        //cv.put("abs_flag", updateValue);
        cv.put(SchemaContract.CounterEntry.COLUMN_PASSWORD, password);
        db.update(SchemaContract.CounterEntry.TABLE_NAME, cv, SchemaContract.CounterEntry.COLUMN_USER_NAME + "=?", new String[]{username});
    }
}