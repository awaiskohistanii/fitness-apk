package com.example.activehealthfitness.Fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.activehealthfitness.AgeCalculator.AgeCalculatorDetail;
import com.example.activehealthfitness.R;
import com.example.activehealthfitness.AgeCalculator.SaveAge;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AgeCalculatorFragment extends Fragment {

    TextView textViewCurrentDate;
    TextView selectDateButton, calBtn;

    CardView cardViewBtn;

    public static Calendar birthDate;
    public static String birthDateFormattedDate;
    public static int birthYear;
    public static int birthMonth;
    public static int birthDay;
    ///////////////////////////////
    Calendar currentDate;
    int currentYear;
    int currentMonth;
    int currentDay;
    /////////////////////////////

    public static String CurrentFormattedDate;

    public AgeCalculatorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_age_calculator, container, false);

        textViewCurrentDate = view.findViewById(R.id.current_date);

        ///////////////////////////////////current Date//////////////////////////////////
        Calendar calendar = Calendar.getInstance();
        // Create a SimpleDateFormat object to format the date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy EEEE", Locale.getDefault());
        // Use the SimpleDateFormat object to format the date and day
        CurrentFormattedDate = dateFormat.format(calendar.getTime());
        // Display the date and day in a TextView
        textViewCurrentDate.setText(CurrentFormattedDate);
        /////////////////////////////////////////////////////////////////////////////////
        selectDateButton = view.findViewById(R.id.select_date);
        calBtn = view.findViewById(R.id.calculator_btn);
        cardViewBtn = view.findViewById(R.id.card_view_btn);

        selectDateButton.setOnClickListener(view1 -> AgeCalculatorFragment.this.showDatePickerDialog());

        calBtn.setOnClickListener(view12 -> {
            calBtn.setEnabled(false);
            try {
                calculateAge(currentDate, birthDate);
            } catch (Exception e) {
                showDialogueIncorrect("Please Enter Data !");
            }
            calButtonEnable();
        });
        return view;
    }

    public void calButtonEnable() {
        new Handler().postDelayed(() -> calBtn.setEnabled(true), 1000);
    }

    public void showDialogueIncorrect(String bmiDesc) {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.incorrect_dailoge_box);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        TextView textViewBmiDesc = (TextView) dialog.findViewById(R.id.incorrect_des);
        textViewBmiDesc.setText(bmiDesc);


        TextView okay = (TextView) dialog.findViewById(R.id.incorrect_ok);
        okay.setOnClickListener(view -> dialog.dismiss());

        dialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        calBtn.setEnabled(true);
    }

    private void showDatePickerDialog() {
        // Get the current date
        currentDate = Calendar.getInstance();
        // Set the initial date to the current date
        currentYear = currentDate.get(Calendar.YEAR);
        currentMonth = currentDate.get(Calendar.MONTH);
        currentDay = currentDate.get(Calendar.DAY_OF_MONTH);

        // Create a DatePickerDialog to select the birth date
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),
                (datePicker, year, month, dayOfMonth) -> {
                    // Create a Calendar object with the birth date
                    birthDate = Calendar.getInstance();
                    birthDate.set(year, month, dayOfMonth);
                    birthYear = year;
                    birthMonth = month;
                    birthDay = dayOfMonth;

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy EEEE", Locale.getDefault());
                    // Use the SimpleDateFormat object to format the date and day
                    birthDateFormattedDate = dateFormat.format(birthDate.getTime());
                    selectDateButton.setText(birthDateFormattedDate);
                    cardViewBtn.setVisibility(View.VISIBLE);
                },
                currentYear, currentMonth, currentDay);

        // Show the DatePickerDialog
        datePickerDialog.show();
    }


    //*******************************************************************8
    private void calculateAge(Calendar currentDate, Calendar birthDate) {
        // Calculate the difference between the birth date and the current date
        long differenceInMillis = currentDate.getTimeInMillis() - birthDate.getTimeInMillis();

        // Calculate the age in years, months, weeks, days, hours, and minutes
        int years = (int) (differenceInMillis / (1000L * 60 * 60 * 24 * 365));
        int months = (int) (differenceInMillis / (1000L * 60 * 60 * 24 * 30));
        int weeks = (int) (differenceInMillis / (1000L * 60 * 60 * 24 * 7));
        int days = (int) (differenceInMillis / (1000L * 60 * 60 * 24));
        int hours = (int) (differenceInMillis / (1000L * 60 * 60));
        int minutes = (int) (differenceInMillis / (1000L * 60));
        /////////////////////////////////////////////////////////////////

        int cd = currentDay;
        int cm = currentMonth;
        int cy = currentYear;
        Date now = new Date(cy, cm, cd);

        int bd = birthDay;
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

            ////////////////////////////////////////////////////////////////

            Intent intent = new Intent(getActivity(), AgeCalculatorDetail.class);
            //Bundle bundle=new Bundle();
            intent.putExtra("title", "Cal Age");
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
            intent.putExtra("birth_month", birthMonth + 1);
            intent.putExtra("birth_day", birthDay);
            intent.putExtra("birth_year", birthYear);

            startActivity(intent);
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.save_age, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private boolean isMenuItemEnabled = true; // global variable

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (isMenuItemEnabled) {
            if (id == R.id.save_age) {
                isMenuItemEnabled = false;
                startActivity(new Intent(getActivity(), SaveAge.class));
                isMenuItemEnabled(); // solve double click problem
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void isMenuItemEnabled() {
        new Handler().postDelayed(() -> isMenuItemEnabled = true, 1000);
    }
}