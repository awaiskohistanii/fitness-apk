package com.example.activehealthfitness.Tips;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.activehealthfitness.R;

import java.util.ArrayList;

public class Tips extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView imageViewTipClose;

    @Override
    protected void onResume() {
        super.onResume();
        imageViewTipClose.setEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.card_color));
        }

        recyclerView = findViewById(R.id.recyclerview);


        String tip = getIntent().getStringExtra("tips");
        ArrayList<TipsModel> list = new ArrayList<>();
        if (tip.equals("Bmi Tips")) {
            list.add(new TipsModel("BMI Category : 1", getString(R.string.under_weight_category_duration), getString(R.string.under_weight_category_tips)));
            list.add(new TipsModel("BMI Category : 2", getString(R.string.normal_category_duration), getString(R.string.normal_category_tips)));
            list.add(new TipsModel("BMI Category : 3", getString(R.string.over_weight_category_duration), getString(R.string.over_weight_category_tips)));
            list.add(new TipsModel("BMI Category : 4", getString(R.string.obese_category_duration), getString(R.string.obese_category_tips)));
        } else if (tip.equals("Workout Tips")) {
            list.add(new TipsModel("Tip : 1", getString(R.string.tip1_name), getString(R.string.tip1_desc)));
            list.add(new TipsModel("Tip : 2", getString(R.string.tip2_name), getString(R.string.tip2_desc)));
            list.add(new TipsModel("Tip : 3", getString(R.string.tip3_name), getString(R.string.tip3_desc)));
            list.add(new TipsModel("Tip : 4", getString(R.string.tip4_name), getString(R.string.tip4_desc)));
            list.add(new TipsModel("Tip : 5", getString(R.string.tip5_name), getString(R.string.tip5_desc)));
            list.add(new TipsModel("Tip : 6", getString(R.string.tip6_name), getString(R.string.tip6_desc)));
            list.add(new TipsModel("Tip : 7", getString(R.string.tip7_name), getString(R.string.tip7_desc)));
            list.add(new TipsModel("Tip : 8", getString(R.string.tip8_name), getString(R.string.tip8_desc)));
            list.add(new TipsModel("Tip : 9", getString(R.string.tip9_name), getString(R.string.tip9_desc)));

        }


        TipsAdapter adapter = new TipsAdapter(list, this);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        imageViewTipClose = findViewById(R.id.tip_close_btn);
        imageViewTipClose.setOnClickListener(view -> {
            imageViewTipClose.setEnabled(false);
            finish();
        });


    }
}