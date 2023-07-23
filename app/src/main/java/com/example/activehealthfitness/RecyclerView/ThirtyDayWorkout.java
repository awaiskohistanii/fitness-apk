package com.example.activehealthfitness.RecyclerView;

public class ThirtyDayWorkout {
    private final String day;
    private final int progress;

    public ThirtyDayWorkout(String day,  int progress) {
        this.day = day;
        this.progress = progress;
    }
    public String getDay() {
        return day;
    }
    public int getProgress() {
        return progress;
    }
}
