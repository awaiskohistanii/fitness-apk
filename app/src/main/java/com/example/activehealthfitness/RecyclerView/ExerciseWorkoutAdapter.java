package com.example.activehealthfitness.RecyclerView;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.airbnb.lottie.LottieAnimationView;
import com.example.activehealthfitness.R;

import java.util.ArrayList;

public class ExerciseWorkoutAdapter extends ArrayAdapter<ExerciseWorkout> {

    TextView nameTextView,numberTextView;
    LottieAnimationView lottieAnimationView;
    public ExerciseWorkoutAdapter(Activity context, ArrayList<ExerciseWorkout> exerciseWorkouts) {
        super(context, 0, exerciseWorkouts);
    }

    @NonNull
    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.listview_workout, parent, false);
        }

        ExerciseWorkout currentAndroidExercise = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        nameTextView =listItemView.findViewById(R.id.name_item);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        nameTextView.setText(currentAndroidExercise.getmName());

        // Find the TextView in the list_item.xml layout with the ID version_number
        numberTextView =listItemView.findViewById(R.id.time_item);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        numberTextView.setText(currentAndroidExercise.getmTime());


        lottieAnimationView =listItemView.findViewById(R.id.animationView_item);
//        lottieAnimationView.setAnimation(R.raw.jumping);
        lottieAnimationView.setAnimation(currentAndroidExercise.getmImage());
        lottieAnimationView.playAnimation();

        return listItemView;
    }
}


