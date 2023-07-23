package com.example.activehealthfitness.AgeCalculator;

import java.util.Calendar;

public class SaveAgeModel {
    private final String name;
    private final String age;
    private final String birthDate;
    private final String upComingBirthDate;
    ////////////////////////////////////////////////////////////
    Calendar birthDay;
    int birthDayYear;
    int birthDayMonth;
    int birthDayDate;
    //////////////////////////////////////////////////////////

    public SaveAgeModel(String name, String age, String birthDate, String upComingBirthDate, Calendar birthDay, int birthDayYear, int birthDayMonth, int birthDayDate) {
        this.name = name;
        this.age = age;
        this.birthDate = birthDate;
        this.upComingBirthDate = upComingBirthDate;
        this.birthDay = birthDay;
        this.birthDayYear = birthDayYear;
        this.birthDayMonth = birthDayMonth;
        this.birthDayDate = birthDayDate;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getUpComingBirthDate() {
        return upComingBirthDate;
    }

    public Calendar getBirthDay() {
        return birthDay;
    }

    public int getBirthDayYear() {
        return birthDayYear;
    }

    public int getBirthDayMonth() {
        return birthDayMonth;
    }

    public int getBirthDayDate() {
        return birthDayDate;
    }
}
