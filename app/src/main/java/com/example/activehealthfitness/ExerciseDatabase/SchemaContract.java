package com.example.activehealthfitness.ExerciseDatabase;

import android.provider.BaseColumns;

public final class SchemaContract {

    public SchemaContract() {
    }
    public static final class CounterEntry implements BaseColumns {

        // Schema for Table Name

        public static final String TABLE_NAME = "Registration";
        // Schema for Table Columns
        public  static final String _ID=BaseColumns._ID;

        // User Data
        public static final String COLUMN_FULL_NAME = "Full_Name";
        public static final String COLUMN_USER_NAME = "User_Name";
        public static final String COLUMN_PASSWORD = "Password";
        public static final String COLUMN_OLD_BMI = "Old_BMI";
        public static final String COLUMN_NEW_BMI = "New_BMI";

        // Exercises
        public  static final String COLUMN_FLAG="abs_flag";
        public  static final String COLUMN_FLAG_ARM="arm_flag";
        public  static final String COLUMN_FLAG_CHEST="chest_flag";
        public  static final String COLUMN_FLAG_LEG="leg_flag";
        public  static final String COLUMN_FLAG_SHOULDER="shoulder_flag";

        // Weekly Challenges
        public  static final String COLUMN_FLAG_DAY_1="day_one_flag";
        public  static final String COLUMN_FLAG_DAY_2="day_two_flag";
        public  static final String COLUMN_FLAG_DAY_3="day_three_flag";
        public  static final String COLUMN_FLAG_DAY_4="day_four_flag";
        public  static final String COLUMN_FLAG_DAY_5="day_five_flag";
        public  static final String COLUMN_FLAG_DAY_6="day_six_flag";
        public  static final String COLUMN_FLAG_DAY_7="day_seven_flag";

        // Schema for initialize Column flag variable
        public  static final int FLAG_VARIABLE=0;
        public  static final int FLAG_VARIABLE_ARM=0;
        public  static final int FLAG_VARIABLE_CHEST=0;
        public  static final int FLAG_VARIABLE_LEG=0;
        public  static final int FLAG_VARIABLE_SHOULDER=0;
        public  static final int FLAG_VARIABLE_DAY_1=0;
        public  static final int FLAG_VARIABLE_DAY_2=0;
        public  static final int FLAG_VARIABLE_DAY_3=0;
        public  static final int FLAG_VARIABLE_DAY_4=0;
        public  static final int FLAG_VARIABLE_DAY_5=0;
        public  static final int FLAG_VARIABLE_DAY_6=0;
        public  static final int FLAG_VARIABLE_DAY_7=0;

    }

    // Define the second table
    public static final class Table2 implements BaseColumns {
        public static final String TABLE_NAME_AGE_CAL = "Age_Cal_Record";
        public  static final String AGE_CAL_ID=BaseColumns._ID;
        public static final String COLUMN_AGE_CAL_NAME = "Name";
        public static final String COLUMN_AGE_CAL_AGE = "Age";
        public static final String COLUMN_AGE_CAL_DOB = "DOB";
        public static final String COLUMN_AGE_CAL_UP_BD = "BD";

        public static final String COLUMN_AGE_CAL_BIRTH_DAY = "birth_day";
        public static final String COLUMN_AGE_CAL_BIRTH_YEAR = "birth_year";
        public static final String COLUMN_AGE_CAL_BIRTH_MONTH = "birth_month";
        public static final String COLUMN_AGE_CAL_BIRTH_DATE = "birth_date";

    }
}
