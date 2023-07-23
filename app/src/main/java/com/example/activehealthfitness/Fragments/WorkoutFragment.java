package com.example.activehealthfitness.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.activehealthfitness.ExerciseDatabase.DBHelper;
import com.example.activehealthfitness.ExerciseDatabase.SchemaContract;
import com.example.activehealthfitness.MainActivity;
import com.example.activehealthfitness.R;
import com.example.activehealthfitness.Tips.Tips;

import java.util.ArrayList;

public class WorkoutFragment extends Fragment {
    int abs_progressValue, chest_progressValue, arm_progressValue, leg_progressValue, shoulder_progressValue;
    private DBHelper dbHelper;
    View view;
    private boolean isMenuItemEnabled = true;

    public WorkoutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchValueFromDatabase();
        listItem();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_workout, container, false);
        dbHelper = new DBHelper(getActivity());
        fetchValueFromDatabase();
        // Create List View Adapter
        listItem();
        return view;
    }

    private void listItem() {
        ArrayList<WorkoutModel> workoutModels = new ArrayList<>();
        workoutModels.add(new WorkoutModel(R.drawable.arm, arm_progressValue, arm_progressValue, "Arm Workout", getString(R.string.arm_des), "INSTRUCTION", "START"));
        workoutModels.add(new WorkoutModel(R.drawable.shoulder, shoulder_progressValue, shoulder_progressValue, "Shoulder Workout", getString(R.string.shoulder_des), "INSTRUCTION", "START"));
        workoutModels.add(new WorkoutModel(R.drawable.abs, abs_progressValue, abs_progressValue, "Abs Workout", getString(R.string.abs_des), "INSTRUCTION", "START"));
        workoutModels.add(new WorkoutModel(R.drawable.chest, chest_progressValue, chest_progressValue, "Chest Workout", getString(R.string.chest_des), "INSTRUCTION", "START"));
        workoutModels.add(new WorkoutModel(R.drawable.leg, leg_progressValue, leg_progressValue, "Leg Workout", getString(R.string.leg_des), "INSTRUCTION", "START"));
        workoutModels.add(new WorkoutModel(R.drawable.fullbody, 0, 0, "Full Body", getString(R.string.full_body_des), "INSTRUCTION", "START"));

        WorkoutAdapter workoutAdapter = new WorkoutAdapter(getActivity(), workoutModels);
        ListView listView =view.findViewById(R.id.listview_workout);
        listView.setAdapter(workoutAdapter);
    }

    // This Function is used for fetch data from Database
    private void fetchValueFromDatabase() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                SchemaContract.CounterEntry._ID,
                SchemaContract.CounterEntry.COLUMN_FLAG,
                SchemaContract.CounterEntry.COLUMN_FLAG_CHEST,
                SchemaContract.CounterEntry.COLUMN_FLAG_ARM,
                SchemaContract.CounterEntry.COLUMN_FLAG_LEG,
                SchemaContract.CounterEntry.COLUMN_FLAG_SHOULDER
                // select * from table
        };

        String selection = SchemaContract.CounterEntry.COLUMN_USER_NAME + "=?"; // where USER NAME=username
        String[] selectionArgs = new String[]{MainActivity.userNameSharedPreferencesValue};

        try (Cursor cursor = db.query(
                SchemaContract.CounterEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        )) {
            // fetch Value index from table
            // int idColumnIndex = cursor.getColumnIndex(SchemaContract.CounterEntry._ID);
            int flagColumnIndexAbs = cursor.getColumnIndex(SchemaContract.CounterEntry.COLUMN_FLAG);
            int flagColumnIndexChest = cursor.getColumnIndex(SchemaContract.CounterEntry.COLUMN_FLAG_CHEST);
            int flagColumnIndexArm = cursor.getColumnIndex(SchemaContract.CounterEntry.COLUMN_FLAG_ARM);
            int flagColumnIndexLeg = cursor.getColumnIndex(SchemaContract.CounterEntry.COLUMN_FLAG_LEG);
            int flagColumnIndexShoulder = cursor.getColumnIndex(SchemaContract.CounterEntry.COLUMN_FLAG_SHOULDER);
            while (cursor.moveToNext()) {
                // fetch value  of ID and Flag then stored in this two variable
                // int currentID = cursor.getInt(idColumnIndex);
                abs_progressValue = cursor.getInt(flagColumnIndexAbs);
                chest_progressValue = cursor.getInt(flagColumnIndexChest);
                arm_progressValue = cursor.getInt(flagColumnIndexArm);
                leg_progressValue = cursor.getInt(flagColumnIndexLeg);
                shoulder_progressValue = cursor.getInt(flagColumnIndexShoulder);
            }
        }
        // When the block is exited, the cursor's close() method is automatically called,
        // which releases its associated resources.
    }

    /// For Menu
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.bmi_tips, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (isMenuItemEnabled) {
            if (id == R.id.bmi_tip) {
                isMenuItemEnabled = false;
                Intent intent = new Intent(getActivity(), Tips.class);
                intent.putExtra("tips", "Workout Tips");
                startActivity(intent);
                isMenuItemEnabled(); // solve double click problem
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void isMenuItemEnabled() {
        new Handler().postDelayed(() -> isMenuItemEnabled = true, 1000);
    }

}