package com.example.activehealthfitness.ExerciseDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.activehealthfitness.MainActivity;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "FitnessApp.db";

    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_COUNTER_TABLE = ("CREATE TABLE " + SchemaContract.CounterEntry.TABLE_NAME +
                "(" + SchemaContract.CounterEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                SchemaContract.CounterEntry.COLUMN_FULL_NAME + " TEXT NOT NULL," +
                SchemaContract.CounterEntry.COLUMN_USER_NAME + " TEXT NOT NULL," +
                SchemaContract.CounterEntry.COLUMN_PASSWORD + " TEXT NOT NULL," +

                SchemaContract.CounterEntry.COLUMN_OLD_BMI + " TEXT NOT NULL," +
                SchemaContract.CounterEntry.COLUMN_NEW_BMI + " TEXT NOT NULL," +

                SchemaContract.CounterEntry.COLUMN_FLAG + " INTEGER NOT NULL," +
                SchemaContract.CounterEntry.COLUMN_FLAG_ARM + " INTEGER NOT NULL," +
                SchemaContract.CounterEntry.COLUMN_FLAG_CHEST + " INTEGER NOT NULL," +
                SchemaContract.CounterEntry.COLUMN_FLAG_LEG + " INTEGER NOT NULL," +
                SchemaContract.CounterEntry.COLUMN_FLAG_SHOULDER + " INTEGER NOT NULL," +

                SchemaContract.CounterEntry.COLUMN_FLAG_DAY_1 + " INTEGER NOT NULL," +
                SchemaContract.CounterEntry.COLUMN_FLAG_DAY_2 + " INTEGER NOT NULL," +
                SchemaContract.CounterEntry.COLUMN_FLAG_DAY_3 + " INTEGER NOT NULL," +
                SchemaContract.CounterEntry.COLUMN_FLAG_DAY_4 + " INTEGER NOT NULL," +
                SchemaContract.CounterEntry.COLUMN_FLAG_DAY_5 + " INTEGER NOT NULL," +
                SchemaContract.CounterEntry.COLUMN_FLAG_DAY_6 + " INTEGER NOT NULL," +
                SchemaContract.CounterEntry.COLUMN_FLAG_DAY_7 + " INTEGER NOT NULL " + ")");

        db.execSQL(SQL_CREATE_COUNTER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // For Age Calculator
    public void createAgeSaveTable(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        String SQL_CREATE_AGE_CAL = ("CREATE TABLE " + SchemaContract.Table2.TABLE_NAME_AGE_CAL + "_" + username +
                "(" + SchemaContract.Table2.AGE_CAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                SchemaContract.Table2.COLUMN_AGE_CAL_NAME + " TEXT NOT NULL," +
                SchemaContract.Table2.COLUMN_AGE_CAL_AGE + " TEXT NOT NULL," +
                SchemaContract.Table2.COLUMN_AGE_CAL_DOB + " TEXT NOT NULL," +

                SchemaContract.Table2.COLUMN_AGE_CAL_BIRTH_DAY + " INTEGER NOT NULL," +
                SchemaContract.Table2.COLUMN_AGE_CAL_BIRTH_YEAR + " INTEGER NOT NULL," +
                SchemaContract.Table2.COLUMN_AGE_CAL_BIRTH_MONTH + " INTEGER NOT NULL," +
                SchemaContract.Table2.COLUMN_AGE_CAL_BIRTH_DATE + " INTEGER NOT NULL," +

                SchemaContract.Table2.COLUMN_AGE_CAL_UP_BD + " TEXT NOT NULL " + ")");
        db.execSQL(SQL_CREATE_AGE_CAL);
    }

    // For Insert Data into Database
    public boolean insertRecord(String fullName, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        // Insert User Data Values
        contentValues.put(SchemaContract.CounterEntry.COLUMN_FULL_NAME, fullName);
        contentValues.put(SchemaContract.CounterEntry.COLUMN_USER_NAME, username);
        contentValues.put(SchemaContract.CounterEntry.COLUMN_PASSWORD, password);

        contentValues.put(SchemaContract.CounterEntry.COLUMN_OLD_BMI, "");
        contentValues.put(SchemaContract.CounterEntry.COLUMN_NEW_BMI, "");

        // Insert Exercise Values
        contentValues.put(SchemaContract.CounterEntry.COLUMN_FLAG, SchemaContract.CounterEntry.FLAG_VARIABLE); // for abs
        contentValues.put(SchemaContract.CounterEntry.COLUMN_FLAG_ARM, SchemaContract.CounterEntry.FLAG_VARIABLE_ARM); // for arm
        contentValues.put(SchemaContract.CounterEntry.COLUMN_FLAG_CHEST, SchemaContract.CounterEntry.FLAG_VARIABLE_CHEST); // for chest
        contentValues.put(SchemaContract.CounterEntry.COLUMN_FLAG_LEG, SchemaContract.CounterEntry.FLAG_VARIABLE_LEG); // for leg
        contentValues.put(SchemaContract.CounterEntry.COLUMN_FLAG_SHOULDER, SchemaContract.CounterEntry.FLAG_VARIABLE_SHOULDER); // for shoulder

        // Insert Weekly Challenge Values
        contentValues.put(SchemaContract.CounterEntry.COLUMN_FLAG_DAY_1, SchemaContract.CounterEntry.FLAG_VARIABLE_DAY_1); // for day1
        contentValues.put(SchemaContract.CounterEntry.COLUMN_FLAG_DAY_2, SchemaContract.CounterEntry.FLAG_VARIABLE_DAY_2); // for day2
        contentValues.put(SchemaContract.CounterEntry.COLUMN_FLAG_DAY_3, SchemaContract.CounterEntry.FLAG_VARIABLE_DAY_3); // for day3
        contentValues.put(SchemaContract.CounterEntry.COLUMN_FLAG_DAY_4, SchemaContract.CounterEntry.FLAG_VARIABLE_DAY_4); // for day4
        contentValues.put(SchemaContract.CounterEntry.COLUMN_FLAG_DAY_5, SchemaContract.CounterEntry.FLAG_VARIABLE_DAY_5); // for day5
        contentValues.put(SchemaContract.CounterEntry.COLUMN_FLAG_DAY_6, SchemaContract.CounterEntry.FLAG_VARIABLE_DAY_6); // for day6
        contentValues.put(SchemaContract.CounterEntry.COLUMN_FLAG_DAY_7, SchemaContract.CounterEntry.FLAG_VARIABLE_DAY_7); // for day7

        long r = db.insert(SchemaContract.CounterEntry.TABLE_NAME, null, contentValues);
        db.close(); // Close the cursor to free up resources
        return (r >= 0);
    }

    // For Registration , in which we  Check Email in the database is already exist or not.
    public boolean checkUsername(String userName) {
        // create or open database to read from it
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                SchemaContract.CounterEntry._ID,
                SchemaContract.CounterEntry.COLUMN_USER_NAME
                // Select email from table
        };

        String selection = SchemaContract.CounterEntry.COLUMN_USER_NAME + "=?"; // where USER NAME=username
        String[] selectionArgs = new String[]{userName};

        Cursor c = db.query(
                SchemaContract.CounterEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        // getCount() return the number of rows in the cursor
        boolean result = c.getCount() > 0;
        c.close(); // Close the cursor to free up resources
        return result;
    }


    // Check User name for Age Calculator
    public boolean checkSaveUsername(String name) {
        // create or open database to read from it
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                SchemaContract.Table2.AGE_CAL_ID,
                SchemaContract.Table2.COLUMN_AGE_CAL_NAME
                // Select email from table
        };

        String selection = SchemaContract.Table2.COLUMN_AGE_CAL_NAME + "=?"; // where USER NAME=username
        String[] selectionArgs = new String[]{name};

        Cursor c = db.query(
                SchemaContract.Table2.TABLE_NAME_AGE_CAL + "_" + MainActivity.userNameSharedPreferencesValue,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        // getCount() return the number of rows in the cursor
        boolean result = c.getCount() > 0;
        c.close(); // Close the cursor to free up resources
        return result;
    }

    // For Sign in , in which we Check Email in the database is exist or not.
    public boolean checkUsernamePassword(String userName, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                SchemaContract.CounterEntry._ID,
                SchemaContract.CounterEntry.COLUMN_USER_NAME,
                SchemaContract.CounterEntry.COLUMN_PASSWORD
                // Select email and password from table
        };

        String selection = SchemaContract.CounterEntry.COLUMN_USER_NAME + "=? AND " +
                SchemaContract.CounterEntry.COLUMN_PASSWORD + "=?";

        String[] selectionArgs = new String[]{userName, password};

        Cursor c = db.query(
                SchemaContract.CounterEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        boolean result = c.getCount() > 0;
        c.close(); // Close the cursor to free up resources
        return result;
    }


    // ************
    public boolean insertAgeCalData(String name, String age, String dob, String upBd, long BD, int birthYear, int birthMonth, int birthDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SchemaContract.Table2.COLUMN_AGE_CAL_NAME, name);
        contentValues.put(SchemaContract.Table2.COLUMN_AGE_CAL_AGE, age);
        contentValues.put(SchemaContract.Table2.COLUMN_AGE_CAL_DOB, dob);

        contentValues.put(SchemaContract.Table2.COLUMN_AGE_CAL_BIRTH_DAY, BD);
        contentValues.put(SchemaContract.Table2.COLUMN_AGE_CAL_BIRTH_YEAR, birthYear);
        contentValues.put(SchemaContract.Table2.COLUMN_AGE_CAL_BIRTH_MONTH, birthMonth);
        contentValues.put(SchemaContract.Table2.COLUMN_AGE_CAL_BIRTH_DATE, birthDate);

        contentValues.put(SchemaContract.Table2.COLUMN_AGE_CAL_UP_BD, upBd);

        long r = db.insert(SchemaContract.Table2.TABLE_NAME_AGE_CAL + "_" + MainActivity.userNameSharedPreferencesValue, null, contentValues);
        db.close(); // Close the cursor to free up resources
        return (r >= 0);
    }
}
