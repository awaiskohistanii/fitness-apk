package com.example.activehealthfitness.AgeCalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.activehealthfitness.ExerciseDatabase.DBHelper;
import com.example.activehealthfitness.ExerciseDatabase.SchemaContract;
import com.example.activehealthfitness.MainActivity;
import com.example.activehealthfitness.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SaveAge extends AppCompatActivity {

    Toolbar toolbar;
    DBHelper dbHelper;
    ArrayList<SaveAgeModel> saveAgeModels;
    SaveAgeAdapter saveAgeAdapter;
    ListView listView;
    Calendar currentDate;
    int currentYear;
    int currentMonth;
    int currentDay;
    Calendar birthDay;
    int birthYear;
    int birthMonth;
    int birthDate;
    String birthDateFormattedDate;
    String name;

    @Override
    protected void onResume() {
        super.onResume();
        displaySaveAgeDatabaseInfo();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_age);
        dbHelper = new DBHelper(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.card_color));
        }

        // for ToolBar
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            // for back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Age Calculator");
        }

        LottieAnimationView emptyLottieAnimationView=findViewById(R.id.empty_list_anim);
        displaySaveAgeDatabaseInfo(); // For Save Age Listview
        listView.setEmptyView(emptyLottieAnimationView);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            // get value from model
            // For Showing List View in Reverse Order
            int reverseIndex = adapterView.getCount() - i - 1;
            SaveAgeModel saveAgeModel = (SaveAgeModel) adapterView.getItemAtPosition(reverseIndex);
            name = saveAgeModel.getName();
            birthDateFormattedDate = saveAgeModel.getBirthDate();
            birthDay = saveAgeModel.getBirthDay();
            birthYear = saveAgeModel.getBirthDayYear();
            birthMonth = saveAgeModel.getBirthDayMonth();
            birthDate = saveAgeModel.getBirthDayDate();
//                name=saveAgeModels.get(i).getName(); if we not showing reverse
            currentDate();
            calculateAge(currentDate, birthDay);
            recreate();
        });
    }

    private void displaySaveAgeDatabaseInfo() {
        saveAgeModels = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                SchemaContract.Table2.COLUMN_AGE_CAL_NAME,
                SchemaContract.Table2.COLUMN_AGE_CAL_AGE,
                SchemaContract.Table2.COLUMN_AGE_CAL_DOB,

                SchemaContract.Table2.COLUMN_AGE_CAL_BIRTH_DAY,
                SchemaContract.Table2.COLUMN_AGE_CAL_BIRTH_MONTH,
                SchemaContract.Table2.COLUMN_AGE_CAL_BIRTH_YEAR,
                SchemaContract.Table2.COLUMN_AGE_CAL_BIRTH_DATE,

                SchemaContract.Table2.COLUMN_AGE_CAL_UP_BD
        };


        try (Cursor cursor = db.query(
                SchemaContract.Table2.TABLE_NAME_AGE_CAL + "_" + MainActivity.userNameSharedPreferencesValue,
                projection,
                null,
                null,
                null,
                null,
                null
        )) {
            // fetch Value from table ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            int nameColumnIndex = cursor.getColumnIndex(SchemaContract.Table2.COLUMN_AGE_CAL_NAME);
            int ageColumnIndex = cursor.getColumnIndex(SchemaContract.Table2.COLUMN_AGE_CAL_AGE);
            int dobColumnIndex = cursor.getColumnIndex(SchemaContract.Table2.COLUMN_AGE_CAL_DOB);
            int bdColumnIndex = cursor.getColumnIndex(SchemaContract.Table2.COLUMN_AGE_CAL_UP_BD);
            int birthColumnIndex = cursor.getColumnIndex(SchemaContract.Table2.COLUMN_AGE_CAL_BIRTH_DAY);

            int birthYearColumnIndex = cursor.getColumnIndex(SchemaContract.Table2.COLUMN_AGE_CAL_BIRTH_YEAR);
            int birthMonthColumnIndex = cursor.getColumnIndex(SchemaContract.Table2.COLUMN_AGE_CAL_BIRTH_MONTH);
            int birthDateColumnIndex = cursor.getColumnIndex(SchemaContract.Table2.COLUMN_AGE_CAL_BIRTH_DATE);

            while (cursor.moveToNext()) {
                String name = cursor.getString(nameColumnIndex);
                String age = cursor.getString(ageColumnIndex);
                String dob = cursor.getString(dobColumnIndex);
                String bd = cursor.getString(bdColumnIndex);
                long BD = cursor.getLong(birthColumnIndex);
                Calendar birthDay = Calendar.getInstance();
                birthDay.setTimeInMillis(BD);
                int birthYear = cursor.getInt(birthYearColumnIndex);
                int birthMonth = cursor.getInt(birthMonthColumnIndex);
                int birthDate = cursor.getInt(birthDateColumnIndex);

                saveAgeModels.add(new SaveAgeModel(name, age, dob, bd, birthDay, birthYear, birthMonth, birthDate));
            }
        }
        saveAgeAdapter = new SaveAgeAdapter(this, saveAgeModels);
        listView = findViewById(R.id.save_age_listview);
        listView.setAdapter(saveAgeAdapter);
    }

    public void currentDate() {
        // Get the current date
        currentDate = Calendar.getInstance();
        // Set the initial date to the current date
        currentYear = currentDate.get(Calendar.YEAR);
        currentMonth = currentDate.get(Calendar.MONTH) + 1;
        currentDay = currentDate.get(Calendar.DAY_OF_MONTH);
    }

    // For Toolbar Back Button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void calculateAge(Calendar currentDate, Calendar birthDay) {
        // Calculate the difference between the birth date and the current date
        long differenceInMillis = currentDate.getTimeInMillis() - birthDay.getTimeInMillis();

        // Calculate the age in years, months, weeks, days, hours, and minutes
        int years = (int) (differenceInMillis / (1000L * 60 * 60 * 24 * 365));
        int months = (int) (differenceInMillis / (1000L * 60 * 60 * 24 * 30));
        int weeks = (int) (differenceInMillis / (1000L * 60 * 60 * 24 * 7));
        int days = (int) (differenceInMillis / (1000L * 60 * 60 * 24));
        int hours = (int) (differenceInMillis / (1000L * 60 * 60));
        int minutes = (int) (differenceInMillis / (1000L * 60));

        int cd = currentDay;
        int cm = currentMonth;
        int cy = currentYear;
        Date now = new Date(cy, cm, cd);

        int bd = birthDate;
        int bm = birthMonth;
        int by = birthYear;
        Date dob = new Date(by, bm, bd);


        if (dob.after(now)) {
            showDialogueIncorrect("You are not born yet !\nPlease select correct data.");
        } else {

            int month[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
            if (bd > cd) {
                cd = cd + month[bm - 1];
                cm = cm - 1;
            }
            if (bm > cm) {
                cy = cy - 1;
                cm = cm + 12;
            }
            int calDate = cd - bd;
            int calMonth = cm - bm;
            int calYear = cy - by;

            Intent intent = new Intent(this, AgeCalculatorDetail.class);
            //Bundle bundle=new Bundle();
            intent.putExtra("title", "Save Age");
            intent.putExtra("name", name);
            intent.putExtra("date_of_birth", birthDateFormattedDate);
            intent.putExtra("year", calYear);
            intent.putExtra("month", calMonth);
            intent.putExtra("day", calDate);
            /////////////////////////////////////////
            intent.putExtra("total_years", years);
            intent.putExtra("total_months", months);
            intent.putExtra("total_weeks", weeks);
            intent.putExtra("total_days", days);
            intent.putExtra("total_hours", hours);
            intent.putExtra("total_minutes", minutes);

            intent.putExtra("current_year", currentYear);
            intent.putExtra("current_month", currentMonth + 1);
            intent.putExtra("current_day", currentDay);
            intent.putExtra("birth_month", birthMonth);
            intent.putExtra("birth_day", birthDate);
            intent.putExtra("birth_year", birthYear);

            startActivity(intent);
        }
    }

    public void showDialogueIncorrect(String bmiDesc) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.incorrect_dailoge_box);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        TextView textViewBmiDesc = dialog.findViewById(R.id.incorrect_des);
        textViewBmiDesc.setText(bmiDesc);
        TextView okay = dialog.findViewById(R.id.incorrect_ok);
        okay.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }
}