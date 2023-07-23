package com.example.activehealthfitness.RecyclerView;

public class ExerciseWorkout {
    private final String mName;
    private final String mTime;
    private final int mImage;

    public ExerciseWorkout(String vName, String vTime, int vImage) {
        mName = vName;
        mTime = vTime;
        mImage = vImage;
    }

    public String getmName() {
        return mName;
    }

    public String getmTime() {
        return mTime;
    }

    public int getmImage() {
        return mImage;
    }
}
