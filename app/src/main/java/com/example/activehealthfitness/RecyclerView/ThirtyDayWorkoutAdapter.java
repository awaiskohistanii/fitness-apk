package com.example.activehealthfitness.RecyclerView;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.activehealthfitness.R;

import java.util.ArrayList;

public class ThirtyDayWorkoutAdapter extends ArrayAdapter<ThirtyDayWorkout> {
    TextView textViewDay, textViewProgressText;
    ProgressBar progressBar;

    public ThirtyDayWorkoutAdapter(Activity context, ArrayList<ThirtyDayWorkout> thirtyDayWorkouts) {
        super(context, 0, thirtyDayWorkouts);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.listview_thirty_day_challenge, parent, false);
        }

        ThirtyDayWorkout currentAndroidExercise = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        textViewDay = listItemView.findViewById(R.id.day);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        textViewDay.setText(currentAndroidExercise.getDay());

        // Find the TextView in the list_item.xml layout with the ID version_number
        textViewProgressText = listItemView.findViewById(R.id.day_progress_text);
        progressBar = listItemView.findViewById(R.id.day_progress_bar);

        int a = currentAndroidExercise.getProgress();
        String b = Integer.toString(a);
        textViewProgressText.setText(b + "/5");
        progressBar.setProgress(currentAndroidExercise.getProgress());


        return listItemView;
    }
}
