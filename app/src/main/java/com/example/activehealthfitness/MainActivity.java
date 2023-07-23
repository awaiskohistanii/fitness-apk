package com.example.activehealthfitness;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.activehealthfitness.ExerciseDatabase.DBHelper;
import com.example.activehealthfitness.ExerciseDatabase.SchemaContract;
import com.example.activehealthfitness.Fragments.ViewPagerAdapter;
import com.example.activehealthfitness.Registration.Sign_in;
import com.example.activehealthfitness.Setting.Setting;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DBHelper dbHelper;
    ////////////////// For Logout //////////////////////
    SharedPreferences sp;
    private SharedPreferences.Editor editor;

    ////////////////////////////////////////////////////
    public static String userNameSharedPreferencesValue;
    private String profileName;
    Dialog dialog;

    @Override
    protected void onResume() {
        super.onResume();
        fetchName(userNameSharedPreferencesValue);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);

        userNameSharedPreferencesValue = getIntent().getStringExtra("name"); // get intent value
        fetchName(userNameSharedPreferencesValue);

        // Initialize the navigation drawer
        navigationView = findViewById(R.id.navigation_drawer);
        // Get a reference to the header view
        View headerView = navigationView.getHeaderView(0);
        TextView textViewFullName = headerView.findViewById(R.id.profile_name);
        textViewFullName.setText(profileName);

        // For Log out
        sp = getSharedPreferences("Data", MODE_PRIVATE);
        editor = sp.edit();

        // For Disable DarkMode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // For Navigation Mobile Background Color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.card_color));
        }

        toolbar = findViewById(R.id.main_toolbar);
        tabLayout = findViewById(R.id.tab);
        viewPager = findViewById(R.id.viewpager);
        drawerLayout = findViewById(R.id.drawer);

        // For Toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Active Fitness");
        }

        // For ViewPager
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        // For Showing Drawer
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.OpenDrawer, R.string.CloseDrawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // For Navigation Menu
        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.drawer_home) {
                MainActivity.this.gotoTabLayout(0);
            } else if (item.getItemId() == R.id.Workout) {
                MainActivity.this.gotoTabLayout(0);
            } else if (item.getItemId() == R.id.age_cal) {
                MainActivity.this.gotoTabLayout(1);
            } else if (item.getItemId() == R.id.bmi) {
                MainActivity.this.gotoTabLayout(2);
            } else if (item.getItemId() == R.id.setting) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, Setting.class));
            } else if (item.getItemId() == R.id.rateus) {
                Toast.makeText(MainActivity.this, "Rate us", Toast.LENGTH_SHORT).show();
            } else if (item.getItemId() == R.id.share) {
                Toast.makeText(MainActivity.this, "Share", Toast.LENGTH_SHORT).show();
            } else if (item.getItemId() == R.id.logout) {
                MainActivity.this.showDialogue("Logout", "Confirm LogOut", "Are you sure, You want to LogOut ?");
            } else {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL,
                        new String[]{"awaiskohistanii@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Active Health And Fitness");
                intent.putExtra(Intent.EXTRA_TEXT, "" + MainActivity.this.getEmailBody());
                MainActivity.this.startActivity(Intent.createChooser(intent, "Send Email"));
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return false;
        });
    }

    // Method for access tabLayout from drawer nav
    public void gotoTabLayout(int i) {
        TabLayout.Tab tab = tabLayout.getTabAt(i);
        if (tab != null) {
            tab.select();
        }
    }

    //    To manage the TabLayout fragment and Drawer backstack
    @Override
    public void onBackPressed() {
        int currentTab = viewPager.getCurrentItem();
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else if (currentTab > 0) {
            viewPager.setCurrentItem(currentTab - 1);
        } else {
//            super.onBackPressed();
            showDialogue("Exit", "Confirm Exit", "Are you sure, You want to exit ?");
        }
    }

    private void showDialogue(String code, String title, String des) {
        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.exit_dialogue_box);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        TextView textViewTitle = dialog.findViewById(R.id.exit_dialogue_title);
        TextView textViewDes = dialog.findViewById(R.id.exit_dialogue_Desc);
        textViewTitle.setText(title);
        textViewDes.setText(des);
        TextView yes = dialog.findViewById(R.id.exit_yes);
        yes.setOnClickListener(view -> {
            if (code.equals("Exit")) {
                dialog.dismiss();
                MainActivity.super.onBackPressed();
            } else if (code.equals("Logout")) {
                editor.clear();
                editor.apply();
                finish();
                startActivity(new Intent(MainActivity.this, Sign_in.class));
                dialog.dismiss();
            }
        });
        TextView no = dialog.findViewById(R.id.exit_no);
        no.setOnClickListener(view -> dialog.dismiss());

        dialog.show();
    }

    // for nav Feedback btn
    private String getEmailBody() {
        String body = "";
        String product = Build.PRODUCT;
        String model = Build.MODEL;
        String brand = Build.BRAND;
        String type = Build.TYPE;
        String versionAndroid = Build.VERSION.RELEASE;
        String language = Locale.getDefault().getDisplayLanguage();
        int versionCode = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;

        body += "Product: " + product + "\n";
        body += "Model: " + model + "\n";
        body += "Brand: " + brand + "\n";
        body += "Type: " + type + "\n";
        body += "Version Android: " + versionAndroid + "\n";
        body += "Language: " + language + "\n";
        body += "Version Code: " + versionCode + "\n";
        body += "Version Name: " + versionName + "\n";
        return body;
    }

    // Fetching From Database
    private void fetchName(String SharedPreferenceValue) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                SchemaContract.CounterEntry._ID,
                SchemaContract.CounterEntry.COLUMN_FULL_NAME,
                // Select full Name from table
        };

        String selection = SchemaContract.CounterEntry.COLUMN_USER_NAME + "=?"; // where UserName=?
        String[] selectionArgs = new String[]{SharedPreferenceValue}; // UserName=SharedPreferenceValue

        try (Cursor c = db.query(
                SchemaContract.CounterEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        )) {
            int flagColumnIndexArm = c.getColumnIndex(SchemaContract.CounterEntry.COLUMN_FULL_NAME);
            while (c.moveToNext()) {
                profileName = c.getString(flagColumnIndexArm);
            }
        }
    }
}