package com.example.activehealthfitness.Fragments;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.EditText;
import android.widget.TextView;

import com.example.activehealthfitness.ExerciseDatabase.DBHelper;
import com.example.activehealthfitness.ExerciseDatabase.SchemaContract;
import com.example.activehealthfitness.MainActivity;
import com.example.activehealthfitness.R;
import com.example.activehealthfitness.Tips.Tips;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CheckFitnessFragment extends Fragment {

    EditText bmiAge, bmiWeight, bmiFeet, bmiInches;
    TextView bmiCalculate;
    String oldBMI,newBMI,bmiRoundedNumberString;
    TextView textViewOldBMI,textViewNewBMI;
    CardView cardViewBMIParentOldNew,cardViewOld;
    String currentTime;
    private DBHelper dbHelper;

    public CheckFitnessFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchBMIFromDB();
        updateBMIText();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_check_fitness, container, false);

        bmiAge = view.findViewById(R.id.bmi_age);
        bmiWeight = view.findViewById(R.id.bmi_weight);
        bmiFeet = view.findViewById(R.id.bmi_feet);
        bmiInches = view.findViewById(R.id.bmi_inche);
        bmiCalculate = view.findViewById(R.id.bmi_cal);
        textViewOldBMI=view.findViewById(R.id.old_bmi_textview);
        textViewNewBMI=view.findViewById(R.id.new_bmi_textview);
        cardViewBMIParentOldNew=view.findViewById(R.id.bmi_parent_old_new_card_view);
        cardViewOld=view.findViewById(R.id.old_card_view);

        dbHelper=new DBHelper(getActivity());

        fetchBMIFromDB();
        updateBMIText();

        bmiCalculate.setOnClickListener(view1 -> {
            // convert string value into float
            try {
                float age = Float.parseFloat(bmiAge.getText().toString());
                float kg = Float.parseFloat(bmiWeight.getText().toString());
                float ft = Float.parseFloat(bmiFeet.getText().toString());
                float in = Float.parseFloat(bmiInches.getText().toString());

                // cal height
                // 1 foot = 0.3048 meters
                // 1 inch = 0.0254 meters
                Double height = (ft * 0.3048 + in * 0.0254);

                // cal bmi
                // BMI = weight (kg) ÷ height * height (in to meters)
                Double bmi = kg / (height * height);
                DecimalFormat df = new DecimalFormat("#.##"); // format to two decimal places
                Double bmiRoundedNumber = Double.parseDouble(df.format(bmi));
                bmiRoundedNumberString=Double.toString(bmiRoundedNumber);
                if (bmi < 18.5) {
                    //Toast.makeText(getActivity(), "You are underweight, you should focus on gaining weight." + roundedNumber, Toast.LENGTH_SHORT).show();
                    showDialogue(bmiRoundedNumber,getString(R.string.under_weight));
                } else if (bmi >= 18.5 && bmi < 25) {
                    //Toast.makeText(getActivity(), "Your weight is normal, keep up the good work! : " + roundedNumber, Toast.LENGTH_SHORT).show();
                    showDialogue(bmiRoundedNumber,getString(R.string.normal));
                } else if (bmi >= 25 && bmi < 30) {
                    //Toast.makeText(getActivity(), "You are overweight, you should focus on losing weight. : " + roundedNumber, Toast.LENGTH_SHORT).show();
                    showDialogue(bmiRoundedNumber,getString(R.string.over_weight));
                } else {
                    //Toast.makeText(getActivity(), "You are obese, you should consult with a doctor or a nutritionist. : " + roundedNumber, Toast.LENGTH_SHORT).show();
                    showDialogue(bmiRoundedNumber,getString(R.string.obese));
                }
            } catch (NumberFormatException e) {
                showDialogueIncorrect("Please Enter All Field ");
            }
        });
        return view;
    }

    public void showDialogue(Double bmi,String bmiDesc){
        Dialog dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.bmi_dialogue_box_with_save_btn);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        TextView heading=(TextView) dialog.findViewById(R.id.heading_bmi_dialogue);
        heading.setText("BMI Result");
        TextView textViewBmi=(TextView) dialog.findViewById(R.id.bmi_show_bmi_dialogue);
        TextView textViewBmiDesc=(TextView) dialog.findViewById(R.id.bmi_des_bmi_dialogue);
        textViewBmi.setText("BMI = "+bmi);
        textViewBmiDesc.setText(bmiDesc);
        TextView okay=(TextView) dialog.findViewById(R.id.bmi_ok_bmi_dialogue);
        okay.setOnClickListener(view -> dialog.dismiss());
        TextView save=(TextView) dialog.findViewById(R.id.bmi_save_bmi_dialogue);
        save.setOnClickListener(view -> {
            fetchBMIFromDB();
            update(bmiRoundedNumberString);
            fetchBMIFromDB();
            updateBMIText();
            dialog.dismiss();
        });
        dialog.show();
    }

    public void showDialogueIncorrect(String bmiDesc){
        Dialog dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.incorrect_dailoge_box);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        TextView textViewBmiDesc=(TextView) dialog.findViewById(R.id.incorrect_des);
        textViewBmiDesc.setText(bmiDesc);
        TextView okay=(TextView) dialog.findViewById(R.id.incorrect_ok);
        okay.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.bmi_tips,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private  boolean isMenuItemEnabled=true; // global variable
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();

        if (isMenuItemEnabled){
        if (id==R.id.bmi_tip)
        {
            isMenuItemEnabled=false;
            Intent intent=new Intent(getActivity(), Tips.class);
            intent.putExtra("tips", "Bmi Tips");
            startActivity(intent);
            isMenuItemEnabled(); // solve double click problem
        }
        }

        return super.onOptionsItemSelected(item);
    }

    public void isMenuItemEnabled() {
        new Handler().postDelayed(() -> isMenuItemEnabled=true, 1000);
    }

    private void fetchBMIFromDB(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                SchemaContract.CounterEntry.COLUMN_OLD_BMI,
                SchemaContract.CounterEntry.COLUMN_NEW_BMI
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
            int oldBMIColumnIndex = cursor.getColumnIndex(SchemaContract.CounterEntry.COLUMN_OLD_BMI);
            int newBMIColumnIndex = cursor.getColumnIndex(SchemaContract.CounterEntry.COLUMN_NEW_BMI);
            while (cursor.moveToNext()) {
                oldBMI = cursor.getString(oldBMIColumnIndex);
                newBMI = cursor.getString(newBMIColumnIndex);

            }
        }
    }
    private void updateBMIText() {
        if (!oldBMI.equals("") && !newBMI.equals("")) {
            cardViewBMIParentOldNew.setVisibility(View.VISIBLE);
            cardViewOld.setVisibility(View.VISIBLE);
            textViewOldBMI.setText("Old BMI : " + oldBMI);
            textViewNewBMI.setText("Your BMI : " + newBMI);
        } else if (oldBMI.equals("") && !newBMI.equals("")) {
            cardViewBMIParentOldNew.setVisibility(View.VISIBLE);
            textViewNewBMI.setText("Your BMI : " + newBMI);
        }
        if (oldBMI.equals("") && newBMI.equals("")) {
            cardViewOld.setVisibility(View.GONE);
            cardViewBMIParentOldNew.setVisibility(View.GONE);
        }
    }

    private void update(String currentBMI) {
        showTime();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        //cv.put("abs_flag", updateValue);
        cv.put(SchemaContract.CounterEntry.COLUMN_OLD_BMI, newBMI);
        cv.put(SchemaContract.CounterEntry.COLUMN_NEW_BMI, currentBMI+"\n( on "+AgeCalculatorFragment.CurrentFormattedDate+" at "+currentTime+")");
        db.update(SchemaContract.CounterEntry.TABLE_NAME, cv, SchemaContract.CounterEntry.COLUMN_USER_NAME + "=?", new String[]{MainActivity.userNameSharedPreferencesValue});
    }

    public void showTime() {
        // Get current time in AM/PM format
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
        currentTime = dateFormat.format(calendar.getTime());
    }

}