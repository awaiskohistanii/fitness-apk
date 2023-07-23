package com.example.activehealthfitness.AgeCalculator;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.activehealthfitness.AgeCalculator.SaveAgeModel;
import com.example.activehealthfitness.R;

import java.util.ArrayList;
import java.util.List;

public class SaveAgeAdapter extends ArrayAdapter<SaveAgeModel> {
    TextView textViewName,textViewAge,textViewDOB,textViewUpBirth;
    public SaveAgeAdapter(Activity context, ArrayList<SaveAgeModel> saveAgeModels) {
        super(context, 0, saveAgeModels);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.save_card_listview_layout, parent, false);
        }

        // For Showing List View in Reverse Order
        List<SaveAgeModel> reversedList = new ArrayList<>(getCount());
        for (int i = getCount() - 1; i >= 0; i--) {
            reversedList.add(getItem(i));
        }
        SaveAgeModel saveAgeModel=reversedList.get(position);

        //SaveAgeModel saveAgeModel=getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        textViewName =listItemView.findViewById(R.id.save_cal_name);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        textViewName.setText(saveAgeModel.getName());

        // Find the TextView in the list_item.xml layout with the ID version_number
        textViewAge =listItemView.findViewById(R.id.save_cal_age);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        textViewAge.setText(saveAgeModel.getAge());

        textViewDOB =listItemView.findViewById(R.id.save_cal_dob);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        textViewDOB.setText(saveAgeModel.getBirthDate());

        textViewUpBirth =listItemView.findViewById(R.id.save_cal_up_birth);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        textViewUpBirth.setText(saveAgeModel.getUpComingBirthDate());


        return listItemView;
    }
}
