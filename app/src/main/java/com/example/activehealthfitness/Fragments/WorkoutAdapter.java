package com.example.activehealthfitness.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.activehealthfitness.BottomSheet.Bottom_PopUp_Fragment;
import com.example.activehealthfitness.Exercises.Abs_Workout;
import com.example.activehealthfitness.Exercises.Arm_Workout;
import com.example.activehealthfitness.Exercises.Chest_Workout;
import com.example.activehealthfitness.Exercises.Leg_Workout;
import com.example.activehealthfitness.Exercises.Shoulder_Workout;
import com.example.activehealthfitness.R;
import com.example.activehealthfitness.WeeklyChallenge.ThirtyDaysChallenge;

import java.util.ArrayList;

public class WorkoutAdapter extends ArrayAdapter<WorkoutModel> implements View.OnClickListener {
    ImageView exImageView;
    TextView exInstTextView;
    TextView exStartTextView;

    public WorkoutAdapter(Activity context, ArrayList<WorkoutModel> workoutModels) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, workoutModels);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_workout_fragment, parent, false);
        }

        WorkoutModel currentWorkoutModel = getItem(position);

        exImageView = listItemView.findViewById(R.id.ex_image_btn);
        exImageView.setImageResource(currentWorkoutModel.getExImage());
        exImageView.setTag(position); // set tag to store the position of the item
        exImageView.setOnClickListener(this); // set onClickListener for the item

        TextView exNameTextView = listItemView.findViewById(R.id.ex_name_btn);
        exNameTextView.setText(currentWorkoutModel.getExName());

        ProgressBar exProgressBar = listItemView.findViewById(R.id.ex_progress_bar);
        TextView exProgressBarTextView = listItemView.findViewById(R.id.ex_progress_text);
        if ((position == 5)) {
            exProgressBar.setVisibility(View.GONE);
            exProgressBarTextView.setVisibility(View.GONE);
        } else {
            exProgressBar.setVisibility(View.VISIBLE);
            exProgressBar.setProgress(currentWorkoutModel.getExProgressbar());
            exProgressBarTextView.setVisibility(View.VISIBLE);
            String val = String.valueOf(currentWorkoutModel.getExProgressbarText());
            switch (val) {
                case "0":
                    exProgressBarTextView.setText(R.string.progress_0);
                    break;
                case "1":
                    exProgressBarTextView.setText(R.string.progress_12);
                    break;
                case "2":
                    exProgressBarTextView.setText(R.string.progress_25);
                    break;
                case "3":
                    exProgressBarTextView.setText(R.string.progress_38);
                    break;
                case "4":
                    exProgressBarTextView.setText(R.string.progress_50);
                    break;
                case "5":
                    exProgressBarTextView.setText(R.string.progress_62);
                    break;
                case "6":
                    exProgressBarTextView.setText(R.string.progress_75);
                    break;
                case "7":
                    exProgressBarTextView.setText(R.string.progress_88);
                    break;
                default:
                    exProgressBarTextView.setText(R.string.progress_100);
                    break;
            }
        }

        TextView exDesTextView = listItemView.findViewById(R.id.ex_des);
        exDesTextView.setText(currentWorkoutModel.getExDes());

        exInstTextView = listItemView.findViewById(R.id.ex_inst_btn);
        exInstTextView.setText(currentWorkoutModel.getExInstruction());
        exInstTextView.setTag(position); // set tag to store the position of the item
        exInstTextView.setOnClickListener(this); // set onClickListener for the item

        exStartTextView = listItemView.findViewById(R.id.ex_start_btn);
        exStartTextView.setText(currentWorkoutModel.getExStart());
        exStartTextView.setTag(position); // set tag to store the position of the item
        exStartTextView.setOnClickListener(this); // set onClickListener for the item

        return listItemView;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ex_inst_btn) {
            view.setEnabled(false);
            isButtonEnable(view);
            int position = (int) view.getTag(); // get the position from the tag
            switch (position) {
                case 0:
                    bottomPopUp(getContext().getString(R.string.arm_workout), getContext().getString(R.string.arm_inst)); // send vale to func
                    break;
                case 1:
                    bottomPopUp(getContext().getString(R.string.shoulder_workout), getContext().getString(R.string.shoulder_inst)); // send vale to func
                    break;
                case 2:
                    bottomPopUp(getContext().getString(R.string.abs_workout), getContext().getString(R.string.abs_inst)); // send vale to func
                    break;
                case 3:
                    bottomPopUp(getContext().getString(R.string.chest_workout), getContext().getString(R.string.chest_inst)); // send vale to func
                    break;
                case 4:
                    bottomPopUp(getContext().getString(R.string.leg_workout), getContext().getString(R.string.leg_inst)); // send vale to func
                    break;
                default:
                    bottomPopUp(getContext().getString(R.string.full_body_workout), getContext().getString(R.string.full_body_inst)); // send vale to func
                    break;
            }
        } else if (id == R.id.ex_start_btn || id == R.id.ex_image_btn) {
            view.setEnabled(false);
            isButtonEnable(view);
            int position = (int) view.getTag(); // get the position from the tag
            Context context = view.getContext();
            switch (position) {
                case 0:
                    context.startActivity(new Intent(context, Arm_Workout.class));
                    break;
                case 1:
                    context.startActivity(new Intent(context, Shoulder_Workout.class));
                    break;
                case 2:
                    context.startActivity(new Intent(context, Abs_Workout.class));
                    break;
                case 3:
                    context.startActivity(new Intent(context, Chest_Workout.class));
                    break;
                case 4:
                    context.startActivity(new Intent(context, Leg_Workout.class));
                    break;
                default:
                    context.startActivity(new Intent(context, ThirtyDaysChallenge.class));
                    break;
            }
        }
    }

    public void bottomPopUp(String name, String inst) {
        if (getContext() != null) {
            Bottom_PopUp_Fragment bottom_popUp_fragment = new Bottom_PopUp_Fragment();
            Bundle bundle = new Bundle();
            bundle.putString("name", name);
            bundle.putString("inst", inst);
            bottom_popUp_fragment.setArguments(bundle);
            bottom_popUp_fragment.show(((AppCompatActivity) getContext()).getSupportFragmentManager(), bottom_popUp_fragment.getTag());
        }
    }

    // When call this function then the handler run after every 1 sec
    public void isButtonEnable(View view) {
        new Handler().postDelayed(() -> view.setEnabled(true), 1000);
    }
}
