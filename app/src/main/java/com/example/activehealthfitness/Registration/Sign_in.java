package com.example.activehealthfitness.Registration;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.activehealthfitness.ExerciseDatabase.DBHelper;
import com.example.activehealthfitness.MainActivity;
import com.example.activehealthfitness.R;

public class Sign_in extends AppCompatActivity {

    private EditText userNameEditTextLogin;
    private EditText passwordEditTextLogin;
    TextView signInButton,signupButton,forgotPasswordButton;
    private CheckBox checkBox;
    private ImageView imageViewHide;
    private ImageView imageViewEye;
    private DBHelper dbHelper;

    ////////////////////////////////////////////////////
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    ////////////////////////////////////////////////////

    private String userName; // pass userName to next activity when value not stored in sharedPreferences
    private String userNameSharedPreferences; // pass userName to next activity when value stored in sharedPreferences

    @Override
    protected void onResume() {
        super.onResume();
        signInButton.setEnabled(true);
        signupButton.setEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        dbHelper = new DBHelper(this);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            getWindow().setNavigationBarColor(getResources().getColor(R.color.notification_bar));
        }

        userNameEditTextLogin=findViewById(R.id.usernameEditText_login);
        passwordEditTextLogin=findViewById(R.id.passwordEditText_login);
        signInButton=findViewById(R.id.sign_in);
        signupButton=findViewById(R.id.sign_up);
        forgotPasswordButton=findViewById(R.id.forgot_password);
        checkBox = findViewById(R.id.checkbox);
        imageViewHide = findViewById(R.id.imageViewA_sign_in);
        imageViewEye = findViewById(R.id.imageViewB_sign_in);

        // For SharedPreferences
        sp = getSharedPreferences("Data", MODE_PRIVATE);
        editor = sp.edit();
        boolean login=sp.getBoolean("IS LOGGED IN",false);
        if (login){
            accessSharedPreferenceValue(); // Access value from sharedPreferences
            Intent intent=new Intent(Sign_in.this, MainActivity.class);
            intent.putExtra("name",userNameSharedPreferences);
            startActivity(intent);
            finish();
        }

        imageViewHide.setOnClickListener(view -> {
            imageViewHide.setVisibility(View.INVISIBLE);
            imageViewEye.setVisibility(View.VISIBLE);
            passwordEditTextLogin.setInputType(InputType.TYPE_CLASS_TEXT);
        });

        imageViewEye.setOnClickListener(view -> {
            imageViewEye.setVisibility(View.INVISIBLE);
            imageViewHide.setVisibility(View.VISIBLE);
            passwordEditTextLogin.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        });
        signInButton.setOnClickListener(view -> {
            signInButton.setEnabled(false);
            userName = userNameEditTextLogin.getText().toString();
            String password = passwordEditTextLogin.getText().toString();
            if (userName.equals("")) {
                buttonEnable();
                userNameEditTextLogin.setError("Please enter username");
                userNameEditTextLogin.requestFocus();
            } else if (password.equals("")) {
                buttonEnable();
                passwordEditTextLogin.setError("Please enter password");
                passwordEditTextLogin.requestFocus();
            } else {
                try {
                    boolean validate = dbHelper.checkUsernamePassword(userName, password);
                    if (validate) { // true
                        // For SharedPreferences
                        if (checkBox.isChecked()) {
                            editor.putString("username", userName);
                            editor.putString("password", password);
                            editor.putBoolean("IS LOGGED IN", true);
                            editor.apply();
                            ///////////////////////////////////////////////////////////////////////
                            accessSharedPreferenceValue();// Access value from sharedPreferences
                            Intent intent=new Intent(Sign_in.this, MainActivity.class);
                            intent.putExtra("name",userNameSharedPreferences);
                            startActivity(intent);
                            finish();
                        } else {
                            //accessSharedPreferenceValue();// Access value from sharedPreferences
                            Intent intent=new Intent(Sign_in.this, MainActivity.class);
                            intent.putExtra("name",userName); // if user not click checkbox then intent username
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        showDialogueIncorrect("Please Enter Valid Username or Password");
                    }
                } catch (Exception e) {
                    showDialogueIncorrect("Please Enter Valid Username or Password");
                }
            }

        });

        signupButton.setOnClickListener(view -> {
            signupButton.setEnabled(false);
            startActivity(new Intent(Sign_in.this, Sign_up.class));
        });

        forgotPasswordButton.setOnClickListener(view -> startActivity(new Intent(Sign_in.this, Forgot_Password.class)));
    }

    private void accessSharedPreferenceValue(){
        //String userName = sp.getString("username", "");
        userNameSharedPreferences = sp.getString("username", "");
    }

    public void showDialogueIncorrect(String message) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.incorrect_dailoge_box);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        TextView textViewBmiDesc =dialog.findViewById(R.id.incorrect_des);
        textViewBmiDesc.setText(message);
        TextView okay =dialog.findViewById(R.id.incorrect_ok);
        okay.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
        buttonEnable();
    }
    private void buttonEnable() {
        new Handler().postDelayed(() -> {
            signInButton.setEnabled(true);
            signupButton.setEnabled(true);
        }, 1000);
    }
}