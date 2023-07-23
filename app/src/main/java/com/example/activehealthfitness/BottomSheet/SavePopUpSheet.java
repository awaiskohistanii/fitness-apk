package com.example.activehealthfitness.BottomSheet;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.activehealthfitness.ExerciseDatabase.DBHelper;
import com.example.activehealthfitness.Fragments.AgeCalculatorFragment;
import com.example.activehealthfitness.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class SavePopUpSheet extends BottomSheetDialogFragment {
    EditText editTextName;
    TextView textViewSaveButton;
    ImageView imageViewCloseSaveSheet;
    String year;
    String dob;
    String upDob;
    DBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_save_pop_up_sheet, container, false);

        dbHelper = new DBHelper(getActivity());
        editTextName = view.findViewById(R.id.cal_save_EditText);
        imageViewCloseSaveSheet = view.findViewById(R.id.close_save_bottom_sheet);
        textViewSaveButton = view.findViewById(R.id.cal_save_btn);

        if (getArguments() != null) {
            year = getArguments().getString("year");
            dob = getArguments().getString("dob");
            upDob = getArguments().getString("up_dob");
        }

        textViewSaveButton.setOnClickListener(view1 -> {

            textViewSaveButton.setEnabled(false);
            instButtonEnable();
            String name = editTextName.getText().toString();

            if (name.equals("")) {
                showDialogueIncorrect("Please Enter Name");
            } else {
                boolean validateUsername = dbHelper.checkSaveUsername(name);
                if (!validateUsername) {
                    long birthDateMillis = AgeCalculatorFragment.birthDate.getTimeInMillis();
                    boolean insert = dbHelper.insertAgeCalData(name, year, dob, upDob, birthDateMillis, AgeCalculatorFragment.birthYear, AgeCalculatorFragment.birthMonth + 1, AgeCalculatorFragment.birthDay);
                    if (insert) { // if true
                        dismiss();
                    } else {
                        showDialogueIncorrect("Failed To Saved Record");
                    }
                } else {
                    showDialogueIncorrect("Please chose another Name");
                }
            }
        });

        imageViewCloseSaveSheet.setOnClickListener(view12 -> dismiss());

        return view;
    }

    // When call this function then the handler run after every 1 sec
    public void instButtonEnable() {
        new Handler().postDelayed(() -> textViewSaveButton.setEnabled(true), 1000);
    }

    public void showDialogueIncorrect(String bmiDesc) {
        Dialog dialog = new Dialog(getActivity());
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