package com.bobur.quizforphysics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;

import com.bobur.quizforphysics.history.SolvedTestCache;
import com.bobur.quizforphysics.other.TopicsTestActivity;
import com.bobur.quizforphysics.other.HistoryActivity;
import com.bobur.quizforphysics.other.VideoListActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CardView cvStartQuiz = findViewById(R.id.cvTopicsTest);
        CardView cvRule = findViewById(R.id.cvRule);
        CardView cvHistory = findViewById(R.id.cvHistory);
        CardView cvReferences = findViewById(R.id.cvEditPassword); // eski ID
        CardView cvSuggestions = findViewById(R.id.cvLogout);     // eski ID
        CardView cvVideos = findViewById(R.id.cvVideos);

        cvStartQuiz.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, TopicsTestActivity.class))
        );
// ✅ Tarixni qayta yuklash (app qayta ochilganda ham tarix qolsin)
        SolvedTestCache.getInstance().setAll(
                com.bobur.quizforphysics.history.HistoryStorage.load(getApplicationContext())
        );

        cvVideos.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, VideoListActivity.class))
        );

        cvRule.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, RuleActivity.class))
        );

        cvHistory.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, HistoryActivity.class))
        );

        cvReferences.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, ReferencesActivity.class))
        );

        cvSuggestions.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, SuggestionsActivity.class))
        );
    }
}
