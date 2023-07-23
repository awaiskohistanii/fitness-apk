package com.example.activehealthfitness.Fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            return new WorkoutFragment();
        } else if (position == 1) {
            return new AgeCalculatorFragment();
        } else {
            return new CheckFitnessFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "WORKOUT";
        } else if (position == 1) {
            return "Age Cal";
        } else {
            return "BMI Cal";
        }
    }
}
