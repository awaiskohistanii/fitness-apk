package com.example.activehealthfitness.AgeCalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.activehealthfitness.ExerciseDatabase.DBHelper;
import com.example.activehealthfitness.ExerciseDatabase.SchemaContract;
import com.example.activehealthfitness.Fragments.AgeCalculatorFragment;
import com.example.activehealthfitness.MainActivity;
import com.example.activehealthfitness.R;
import com.example.activehealthfitness.BottomSheet.SavePopUpSheet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AgeCalculatorDetail extends AppCompatActivity {
    Toolbar toolbar;
    TextView textViewDateOfBirth, textViewYear, textViewMonth, textViewDate,
            years, months, weeks, days, hours, minutes,
            textViewAfter10Year, textViewAfter15Year,
            oneYear, twoYear, threeYear, fourYear, fiveYear,
            oneYearUpComing, twoYearUpComing, threeYearUpComing, fourYearUpComing, fiveYearUpComing,
            zodiacName, zodiacDuration;
    FloatingActionButton floatingActionButtonSaveAge, floatingActionButtonDeleteAge;
    String oneYearCompleteUpcomingDate;
    String totalYears;

    ImageView zodiacImageView;
    String name;
    Dialog dialog;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age_calculator_detail);
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


        textViewDateOfBirth = findViewById(R.id.date_of_birth);
        textViewYear = findViewById(R.id.years);
        textViewMonth = findViewById(R.id.months);
        textViewDate = findViewById(R.id.days);
        years = findViewById(R.id.total_year);
        months = findViewById(R.id.total_month);
        weeks = findViewById(R.id.total_week);
        days = findViewById(R.id.total_days);
        hours = findViewById(R.id.total_hour);
        minutes = findViewById(R.id.total_minute);

        textViewAfter10Year = findViewById(R.id.ten_years);
        textViewAfter15Year = findViewById(R.id.fifteen_year);

        oneYear = findViewById(R.id.one_year);
        twoYear = findViewById(R.id.two_year);
        threeYear = findViewById(R.id.three_year);
        fourYear = findViewById(R.id.four_year);
        fiveYear = findViewById(R.id.five_year);

        oneYearUpComing = findViewById(R.id.one_year_next_birthday);
        twoYearUpComing = findViewById(R.id.two_year_next_birthday);
        threeYearUpComing = findViewById(R.id.three_year_next_birthday);
        fourYearUpComing = findViewById(R.id.four_year_next_birthday);
        fiveYearUpComing = findViewById(R.id.five_year_next_birthday);


        zodiacName = findViewById(R.id.zodiac_name);
        zodiacImageView = findViewById(R.id.zodiac_pic);
        zodiacDuration = findViewById(R.id.zodiac_duration);

        //////////////////////////////////Save/////////////////////////////////////////////////////
        floatingActionButtonSaveAge = findViewById(R.id.age_save);
        floatingActionButtonDeleteAge = findViewById(R.id.age_delete);
        name = getIntent().getStringExtra("name");
        String title = getIntent().getStringExtra("title");
        if (title.equals("Save Age")) {
            floatingActionButtonSaveAge.setVisibility(View.INVISIBLE);
            floatingActionButtonDeleteAge.setOnClickListener(view -> showDialogue("Confirm Delete !", "Are you sure you want to Delete ?"));

        } else {
            floatingActionButtonDeleteAge.setVisibility(View.INVISIBLE);
            floatingActionButtonSaveAge.setOnClickListener(view -> {
                floatingActionButtonSaveAge.setEnabled(false);
                bottomPopUp();
                floatingSaveButtonEnable();
            });
        }
        //////////////////////////////////Save/////////////////////////////////////////////////////

        String dateOfBirth = getIntent().getStringExtra("date_of_birth");
        int year = getIntent().getIntExtra("year", 0);
        int month = getIntent().getIntExtra("month", 0);
        int day = getIntent().getIntExtra("day", 0);

        int currentYear = getIntent().getIntExtra("current_year", 0);
        int currentMonth = getIntent().getIntExtra("current_month", 0);
        int currentDay = getIntent().getIntExtra("current_day", 0);

        int birthYear = getIntent().getIntExtra("birth_year", 0);
        int birthMonth = getIntent().getIntExtra("birth_month", 0);
        int birthDay = getIntent().getIntExtra("birth_day", 0);

        totalYears = Integer.toString(getIntent().getIntExtra("total_years", 0));
        String totalMonths = Integer.toString(getIntent().getIntExtra("total_months", 0));
        String totalWeeks = Integer.toString(getIntent().getIntExtra("total_weeks", 0));
        String totalDays = Integer.toString(getIntent().getIntExtra("total_days", 0));
        String totalHours = Integer.toString(getIntent().getIntExtra("total_hours", 0));
        String totalMinutes = Integer.toString(getIntent().getIntExtra("total_minutes", 0));

        textViewDateOfBirth.setText(dateOfBirth);

        textViewYear.setText(String.valueOf(year));
        textViewMonth.setText(String.valueOf(month));
        textViewDate.setText(String.valueOf(day));

        years.setText(totalYears);
        months.setText(totalMonths);
        weeks.setText(totalWeeks);
        days.setText(totalDays);
        hours.setText(totalHours);
        minutes.setText(totalMinutes);


        int afterTenYear = year + 10;
        int afterFifteenYear = year + 15;
        textViewAfter10Year.setText(String.valueOf(afterTenYear));
        textViewAfter15Year.setText(String.valueOf(afterFifteenYear));


        int whenOneYear = year + 1;
        int whenTwoYear = year + 2;
        int whenThreeYear = year + 3;
        int whenFourYear = year + 4;
        int whenFiveYear = year + 5;

        oneYear.setText(String.valueOf(whenOneYear));
        oneYear.append(" Years");
        twoYear.setText(String.valueOf(whenTwoYear));
        twoYear.append(" Years");
        threeYear.setText(String.valueOf(whenThreeYear));
        threeYear.append(" Years");
        fourYear.setText(String.valueOf(whenFourYear));
        fourYear.append(" Years");
        fiveYear.setText(String.valueOf(whenFiveYear));
        fiveYear.append(" Years");


        int oneYearUpComingBirthDay = currentYear + 1;
        int twoYearUpComingBirthDay = currentYear + 2;
        int threeYearUpComingBirthDay = currentYear + 3;
        int fourYearUpComingBirthDay = currentYear + 4;
        int fiveYearUpComingBirthDay = currentYear + 5;

        if (((birthMonth == currentMonth) && (birthDay >= currentDay)) || ((birthMonth > currentMonth))) {

            oneYearCompleteUpcomingDate = birthDay + "-" + birthMonth + "-" + currentYear;
            oneYearUpComing.setText(oneYearCompleteUpcomingDate);
            String twoYearCompleteUpcomingDate = birthDay + "-" + birthMonth + "-" + oneYearUpComingBirthDay;
            twoYearUpComing.setText(twoYearCompleteUpcomingDate);
            String threeYearCompleteUpcomingDate = birthDay + "-" + birthMonth + "-" + twoYearUpComingBirthDay;
            threeYearUpComing.setText(threeYearCompleteUpcomingDate);
            String fourYearCompleteUpcomingDate = birthDay + "-" + birthMonth + "-" + threeYearUpComingBirthDay;
            fourYearUpComing.setText(fourYearCompleteUpcomingDate);
            String fiveYearCompleteUpcomingDate = birthDay + "-" + birthMonth + "-" + fourYearUpComingBirthDay;
            fiveYearUpComing.setText(fiveYearCompleteUpcomingDate);
        } else {
            oneYearCompleteUpcomingDate = birthDay + "-" + birthMonth + "-" + oneYearUpComingBirthDay;
            oneYearUpComing.setText(oneYearCompleteUpcomingDate);
            String twoYearCompleteUpcomingDate = birthDay + "-" + birthMonth + "-" + twoYearUpComingBirthDay;
            twoYearUpComing.setText(twoYearCompleteUpcomingDate);
            String threeYearCompleteUpcomingDate = birthDay + "-" + birthMonth + "-" + threeYearUpComingBirthDay;
            threeYearUpComing.setText(threeYearCompleteUpcomingDate);
            String fourYearCompleteUpcomingDate = birthDay + "-" + birthMonth + "-" + fourYearUpComingBirthDay;
            fourYearUpComing.setText(fourYearCompleteUpcomingDate);
            String fiveYearCompleteUpcomingDate = birthDay + "-" + birthMonth + "-" + fiveYearUpComingBirthDay;
            fiveYearUpComing.setText(fiveYearCompleteUpcomingDate);
        }

        if (birthMonth == 1) {
            checkZodiac(getString(R.string.january_name), R.drawable.capricorn, getString(R.string.january_duration));
        } else if (birthMonth == 2) {
            checkZodiac(getString(R.string.february_name), R.drawable.aquarius, getString(R.string.february_duration));
        } else if (birthMonth == 3) {
            checkZodiac(getString(R.string.march_name), R.drawable.pisces, getString(R.string.march_duration));
        } else if (birthMonth == 4) {
            checkZodiac(getString(R.string.april_name), R.drawable.aries, getString(R.string.april_duration));
        } else if (birthMonth == 5) {
            checkZodiac(getString(R.string.may_name), R.drawable.taurus, getString(R.string.may_duration));
        } else if (birthMonth == 6) {
            checkZodiac(getString(R.string.june_name), R.drawable.gemini, getString(R.string.june_duration));
        } else if (birthMonth == 7) {
            checkZodiac(getString(R.string.july_name), R.drawable.cancer, getString(R.string.july_duration));
        } else if (birthMonth == 8) {
            checkZodiac(getString(R.string.august_name), R.drawable.leo, getString(R.string.august_duration));
        } else if (birthMonth == 9) {
            checkZodiac(getString(R.string.september_name), R.drawable.virgo, getString(R.string.september_duration));
        } else if (birthMonth == 10) {
            checkZodiac(getString(R.string.october_name), R.drawable.libra, getString(R.string.october_duration));
        } else if (birthMonth == 11) {
            checkZodiac(getString(R.string.november_name), R.drawable.scorpius, getString(R.string.november_duration));
        } else if (birthMonth == 12) {
            checkZodiac(getString(R.string.december_name), R.drawable.sagittarius, getString(R.string.december_duration));
        }

    }

    public void checkZodiac(String name, int image, String duration) {
        zodiacName.setText(name);
        zodiacImageView.setImageResource(image);
        zodiacDuration.setText(duration);
    }

    public void floatingSaveButtonEnable() {
        new Handler().postDelayed(() -> floatingActionButtonSaveAge.setEnabled(true), 1000);
    }

    public void bottomPopUp() {
        SavePopUpSheet savePopUpSheet = new SavePopUpSheet();
        Bundle bundle = new Bundle();
        bundle.putString("year", totalYears);
        bundle.putString("dob", AgeCalculatorFragment.birthDateFormattedDate);
        bundle.putString("up_dob", oneYearCompleteUpcomingDate);
        savePopUpSheet.setArguments(bundle);
        savePopUpSheet.show(this.getSupportFragmentManager(), savePopUpSheet.getTag());
    }

    public void showDialogue(String title, String des) {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.exit_dialogue_box);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        TextView textViewTitle = (TextView) dialog.findViewById(R.id.exit_dialogue_title);
        textViewTitle.setText(title);
        TextView textViewDesc = (TextView) dialog.findViewById(R.id.exit_dialogue_Desc);
        textViewDesc.setText(des);
        TextView yes = (TextView) dialog.findViewById(R.id.exit_yes);
        yes.setOnClickListener(view -> {
            deleteSaveAge();
            dialog.dismiss();
            finish();
        });
        TextView no = (TextView) dialog.findViewById(R.id.exit_no);
        no.setOnClickListener(view -> dialog.dismiss());

        dialog.show();
    }

    public void deleteSaveAge() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String whereClause = SchemaContract.Table2.COLUMN_AGE_CAL_NAME + "=?";
        String[] whereArgs = new String[]{name};
        db.delete(
                SchemaContract.Table2.TABLE_NAME_AGE_CAL + "_" + MainActivity.userNameSharedPreferencesValue, // table name
                whereClause, // WHERE clause
                whereArgs // WHERE clause arguments
        );
        db.close();
        finish();
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