package com.example.activehealthfitness.Registration;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.activehealthfitness.ExerciseDatabase.DBHelper;
import com.example.activehealthfitness.R;

public class Sign_up extends AppCompatActivity {
    private EditText fullNameEditTextReg;
    private EditText userNameEditTextReg;
    private EditText passwordEditTextReg;
    private EditText confirmPasswordEditText;
    private TextView registerButton;
    TextView signInButton;
    private ImageView imageViewHide;
    private ImageView imageViewEye;
    private DBHelper dbHelper;

    @Override
    protected void onResume() {
        super.onResume();
        registerButton.setEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        dbHelper = new DBHelper(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.notification_bar));
        }
        fullNameEditTextReg = findViewById(R.id.fullNameEditText_reg);
        userNameEditTextReg = findViewById(R.id.usernameEditText_reg);
        passwordEditTextReg = findViewById(R.id.passwordEditText_reg);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText_reg);
        registerButton = findViewById(R.id.register);
        signInButton = findViewById(R.id.sign_in_reg);
        imageViewHide = findViewById(R.id.imageViewA_sign_up);
        imageViewEye = findViewById(R.id.imageViewB_sign_up);


        imageViewHide.setOnClickListener(view -> {
            imageViewHide.setVisibility(View.INVISIBLE);
            imageViewEye.setVisibility(View.VISIBLE);
            passwordEditTextReg.setInputType(InputType.TYPE_CLASS_TEXT);
            confirmPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        });

        imageViewEye.setOnClickListener(view -> {
            imageViewEye.setVisibility(View.INVISIBLE);
            imageViewHide.setVisibility(View.VISIBLE);
            passwordEditTextReg.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            confirmPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        });

        registerButton.setOnClickListener(view -> {
            registerButton.setEnabled(false);
            String fullName = fullNameEditTextReg.getText().toString();
            String userName = userNameEditTextReg.getText().toString();
            String password = passwordEditTextReg.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();

            if (isValidFullName(fullName)) {
                if (isValidUsername(userName)) {
                    if (password.equals("")) {
                        buttonEnable();
                        passwordEditTextReg.setError("Please Enter Password");
                        passwordEditTextReg.requestFocus();
                    } else {
                        String message = isValidPassword(password);
                        if (message == null) {
                            if (password.equals(confirmPassword)) {
                                boolean validateUsername = dbHelper.checkUsername(userName);
                                if (!validateUsername) { // if false
                                    dbHelper.createAgeSaveTable(userName);
                                    boolean insert = dbHelper.insertRecord(fullName, userName, password);
                                    if (insert) { // if true
                                        finish();
                                    } else {
                                        Toast.makeText(Sign_up.this, "Failed To Saved Record", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    messageUserNameEditTextReg("Username already exist!\nPlease chose another username");
                                }
                            } else {
                                buttonEnable();
                                confirmPasswordEditText.setError("Password do not Match");
                                confirmPasswordEditText.requestFocus();
                            }
                        } else {
                            buttonEnable();
                            passwordEditTextReg.setError(message);
                            passwordEditTextReg.requestFocus();
                        }
                    }
                }
            }
        });

        signInButton.setOnClickListener(view -> onBackPressed());
    }

    private boolean isValidFullName(String fullName) {
        if (fullName.equals("")) {
            messageFullNameEditTextReg("Please enter full name.");
            return false;
        } else if (fullName.startsWith(" ") || fullName.endsWith(" ")) {
            messageFullNameEditTextReg("Invalid Full name, Space not allowed in starts or ends.");
            return false;
        } else if (!fullName.matches("^[a-zA-Z]+([\\s?][a-zA-Z]+)*$")) {
            messageFullNameEditTextReg("Invalid Full name, Only alphabetic characters and a single space allowed b/w character.");
            return false;
        } else if (fullName.length() > 15) {
            messageFullNameEditTextReg("Full name should be less than 15 characters");
            return false;
        }
        return true;
    }

    private void messageFullNameEditTextReg(String message) {
        buttonEnable();
        fullNameEditTextReg.setError(message);
        fullNameEditTextReg.requestFocus();
    }

    private void messageUserNameEditTextReg(String message) {
        buttonEnable();
        userNameEditTextReg.setError(message);
        userNameEditTextReg.requestFocus();
    }

    private boolean isValidUsername(String username) {
        if (username.equals("")) {
            messageUserNameEditTextReg("Please enter user name");
            return false;
            // the regular expression ^(?!sqlite_)[a-zA-Z_][a-zA-Z0-9_]{0,62}$
            // can be simplified by using the \w shorthand character class,
            // which matches any word character (letter, digit, or underscore).
        } else if (!username.matches("^(?!sqlite_)\\w{1,63}$")) {
            messageUserNameEditTextReg("Username can only contain letters, numbers, and single underscore.");
            return false;
        } else if (username.length() < 2) {
            messageUserNameEditTextReg("Username should be at least 3 characters long");
            return false;
        } else if (username.length() > 10) {
            messageUserNameEditTextReg("Username is too long, maximum length is 10 characters");
            return false;
        }
        return true;
    }

    public static String isValidPassword(String password) {
        // Minimum length of 8 characters
        if (password.length() < 8) {
            return "Password should be at least 8 characters long";
        }
        // At least one uppercase letter
        if (!password.matches(".*[A-Z].*")) {
            return "Password should contain at least one uppercase letter";
        }
        // At least one lowercase letter
        if (!password.matches(".*[a-z].*")) {
            return "Password should contain at least one lowercase letter";
        }
        //".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*"
        // At least one special character
        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*")) {
            return "Password should contain at least one special character";
        }
        return null;
    }

    private void buttonEnable() {
        new Handler().postDelayed(() -> registerButton.setEnabled(true), 1000);
    }
}