package com.bobur.quizforphysics.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bobur.quizforphysics.MainActivity;
import com.bobur.quizforphysics.R;
import com.bobur.quizforphysics.other.HistoryManager;
import com.bobur.quizforphysics.other.TopicsTestActivity;
import com.bobur.quizforphysics.test.model.ReviewItem;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    private TextView tvResult;
    private Button btnRetry, btnTopics, btnMain, btnAnalysis;
    private int topicId;
    private String topicName;

    private ArrayList<ReviewItem> reviewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvResult = findViewById(R.id.tvResult);
        btnRetry = findViewById(R.id.btnRetry);
        btnTopics = findViewById(R.id.btnTopics);
        btnMain = findViewById(R.id.btnMain);
        btnAnalysis = findViewById(R.id.btnAnalysis);

        // ✅ INTENT ma’lumotlari
        int score = getIntent().getIntExtra("score", 0);
        int total = getIntent().getIntExtra("total", 0);

        topicId = getIntent().getIntExtra("topicId", -1);
        topicName = getIntent().getStringExtra("topicName");


        reviewList = (ArrayList<ReviewItem>) getIntent().getSerializableExtra("reviewList");

        if (topicName == null) topicName = "Mavzu";

        tvResult.setText("📘 " + topicName + "\nNatija: " + score + " / " + total);

        // ✅ Foiz
        int percent = (total == 0) ? 0 : (int) ((score * 100f) / total);

        // ✅ Natijani topicId bo‘yicha saqlaymiz (ENG MUHIM)
        SharedPreferences prefs = getSharedPreferences("progress", MODE_PRIVATE);
        prefs.edit().putInt("topic_percent_" + topicId, percent).apply();

        // ✅ Agar 60% bo‘lsa keyingi mavzuni ochamiz
        if (topicId != -1 && percent >= 60) {
            int nextId = topicId + 1;
            prefs.edit().putBoolean("topic_unlocked_" + nextId, true).apply();
        }

        // 🔹 Tarixga saqlash (sizdagi eski logika qoladi)
        HistoryManager.addHistory(this, topicName, score, total);

        // 🔹 Retry
        btnRetry.setOnClickListener(v -> {
            Intent retryIntent = new Intent(ResultActivity.this, TestActivity.class);
            retryIntent.putExtra("topicId", topicId);
            retryIntent.putExtra("topicName", topicName);
            startActivity(retryIntent);
            finish();
        });

        // 🔹 Mavzular ro‘yxati
        btnTopics.setOnClickListener(v -> {
            startActivity(new Intent(ResultActivity.this, TopicsTestActivity.class));
            finish();
        });

        // 🔹 Asosiy oyna
        btnMain.setOnClickListener(v -> {
            Intent mainIntent = new Intent(ResultActivity.this, MainActivity.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mainIntent);
            finish();
        });

        // 🔹 Tahlil (reviewList bo‘lsa)
        btnAnalysis.setOnClickListener(v -> {
            if (reviewList == null || reviewList.isEmpty()) {
                Toast.makeText(this, "Tahlil topilmadi", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(ResultActivity.this, ReviewActivity.class);
            intent.putExtra("reviewList", reviewList);
            startActivity(intent);
        });
    }
}
