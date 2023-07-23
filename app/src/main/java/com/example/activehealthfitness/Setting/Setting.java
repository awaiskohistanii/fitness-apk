package com.example.activehealthfitness.Setting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.activehealthfitness.ExerciseDatabase.DBHelper;
import com.example.activehealthfitness.ExerciseDatabase.SchemaContract;
import com.example.activehealthfitness.MainActivity;
import com.example.activehealthfitness.R;

public class Setting extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    TextView allResetBtn, absResetBtn, chestResetBtn, armResetBtn, legResetBtn, shoulderResetBtn, bmiResetBtn;
    Dialog dialog;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.card_color));
        }

        // for ToolBar
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            // for back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Setting");
        }

        allResetBtn = findViewById(R.id.reset_all_btn);
        absResetBtn = findViewById(R.id.reset_abs_btn);
        chestResetBtn = findViewById(R.id.reset_chest_btn);
        armResetBtn = findViewById(R.id.reset_arm_btn);
        legResetBtn = findViewById(R.id.reset_leg_btn);
        shoulderResetBtn = findViewById(R.id.reset_shoulder_btn);
        bmiResetBtn = findViewById(R.id.reset_bmi_btn);


        allResetBtn.setOnClickListener(this);
        absResetBtn.setOnClickListener(this);
        chestResetBtn.setOnClickListener(this);
        armResetBtn.setOnClickListener(this);
        legResetBtn.setOnClickListener(this);
        shoulderResetBtn.setOnClickListener(this);
        bmiResetBtn.setOnClickListener(this);


    }

    // For Toolbar Back Button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        dbHelper = new DBHelper(this);
        int id = view.getId();
        if (id == R.id.reset_all_btn) {
            showDialogue(0,"Confirm Reset !","Are you sure you want to reset ?");
        } else if (id == R.id.reset_abs_btn) {
            showDialogue(0, "abs_flag","Confirm Reset !","Are you sure you want to reset ?");
        } else if (id == R.id.reset_chest_btn) {
            showDialogue(0, "chest_flag","Confirm Reset !","Are you sure you want to reset ?");
        } else if (id == R.id.reset_arm_btn) {
            showDialogue(0, "arm_flag","Confirm Reset !","Are you sure you want to reset ?");
        } else if (id == R.id.reset_leg_btn) {
            showDialogue(0, "leg_flag","Confirm Reset !","Are you sure you want to reset ?");
        } else if (id == R.id.reset_shoulder_btn) {
            showDialogue(0, "shoulder_flag","Confirm Reset !","Are you sure you want to reset ?");
        } else {
            showDialogue("Confirm Reset !","Are you sure you want to reset ?");
        }

    }

    public void showDialogue(int value, String name,String title, String desc) {
        dialog = new Dialog(Setting.this);
        dialog.setContentView(R.layout.exit_dialogue_box);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        TextView textViewTitle =dialog.findViewById(R.id.exit_dialogue_title);
        textViewTitle.setText(title);
        TextView textViewDesc =dialog.findViewById(R.id.exit_dialogue_Desc);
        textViewDesc.setText(desc);
        TextView yes =dialog.findViewById(R.id.exit_yes);
        yes.setOnClickListener(view -> {
            updateData(value, name);
            dialog.dismiss();
            finish();
        });
        TextView no =dialog.findViewById(R.id.exit_no);
        no.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }

    public void showDialogue(String title,String desc) {
        dialog = new Dialog(Setting.this);
        dialog.setContentView(R.layout.exit_dialogue_box);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        TextView textViewTitle =dialog.findViewById(R.id.exit_dialogue_title);
        textViewTitle.setText(title);
        TextView textViewDesc =dialog.findViewById(R.id.exit_dialogue_Desc);
        textViewDesc.setText(desc);
        TextView yes = dialog.findViewById(R.id.exit_yes);
        yes.setOnClickListener(view -> {
            updateDataBMI();
            dialog.dismiss();
            finish();
        });
        TextView no =dialog.findViewById(R.id.exit_no);
        no.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }

    public void showDialogue(int value,String title,String desc) {
        dialog = new Dialog(Setting.this);
        dialog.setContentView(R.layout.exit_dialogue_box);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        TextView textViewTitle =dialog.findViewById(R.id.exit_dialogue_title);
        textViewTitle.setText(title);
        TextView textViewDesc =dialog.findViewById(R.id.exit_dialogue_Desc);
        textViewDesc.setText(desc);
        TextView yes =dialog.findViewById(R.id.exit_yes);
        yes.setOnClickListener(view -> {
            updateDataAll(value);
            dialog.dismiss();
            finish();
        });
        TextView no =dialog.findViewById(R.id.exit_no);
        no.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }

    private void updateData(int updateValue, String colName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(colName, updateValue);
        db.update(SchemaContract.CounterEntry.TABLE_NAME, cv, SchemaContract.CounterEntry.COLUMN_USER_NAME + "=?", new String[]{MainActivity.userNameSharedPreferencesValue});
    }

    private void updateDataBMI() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SchemaContract.CounterEntry.COLUMN_OLD_BMI, "");
        cv.put(SchemaContract.CounterEntry.COLUMN_NEW_BMI, "");
        db.update(SchemaContract.CounterEntry.TABLE_NAME, cv, SchemaContract.CounterEntry.COLUMN_USER_NAME + "=?", new String[]{MainActivity.userNameSharedPreferencesValue});
    }

    private void updateDataAll(int updateValue) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("abs_flag", updateValue);
        cv.put("arm_flag", updateValue);
        cv.put("chest_flag", updateValue);
        cv.put("leg_flag", updateValue);
        cv.put("shoulder_flag", updateValue);
        cv.put("day_one_flag", updateValue);
        cv.put("day_two_flag", updateValue);
        cv.put("day_three_flag", updateValue);
        cv.put("day_four_flag", updateValue);
        cv.put("day_five_flag", updateValue);
        cv.put("day_six_flag", updateValue);
        cv.put("day_seven_flag", updateValue);
        db.update(SchemaContract.CounterEntry.TABLE_NAME, cv, SchemaContract.CounterEntry.COLUMN_USER_NAME + "=?", new String[]{MainActivity.userNameSharedPreferencesValue});
    }
}