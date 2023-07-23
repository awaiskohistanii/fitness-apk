package com.example.activehealthfitness.Fragments;

public class WorkoutModel {
    private final int exImage;
    private final int exProgressbar;
    private final int exProgressbarText;
    private final String exName;
    private final String exDes;
    private final String exInstruction;
    private final String exStart;

    public WorkoutModel(int exImage, int exProgressbar, int exProgressbarText, String exName, String exDes, String exInstruction, String exStart) {
        this.exImage = exImage;
        this.exProgressbar = exProgressbar;
        this.exProgressbarText = exProgressbarText;
        this.exName = exName;
        this.exDes = exDes;
        this.exInstruction = exInstruction;
        this.exStart = exStart;
    }

    public int getExImage() {
        return exImage;
    }

    public int getExProgressbar() {
        return exProgressbar;
    }

    public int getExProgressbarText() {
        return exProgressbarText;
    }

    public String getExName() {
        return exName;
    }

    public String getExDes() {
        return exDes;
    }

    public String getExInstruction() {
        return exInstruction;
    }

    public String getExStart() {
        return exStart;
    }
}
