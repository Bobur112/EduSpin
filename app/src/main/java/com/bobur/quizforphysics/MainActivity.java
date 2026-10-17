package com.bobur.quizforphysics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.bobur.quizforphysics.history.HistoryStorage;
import com.bobur.quizforphysics.history.SolvedTestCache;
import com.bobur.quizforphysics.other.HistoryActivity;
import com.bobur.quizforphysics.other.TopicsTestActivity;
import com.bobur.quizforphysics.other.VideoListActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        // Tarixni ilovani ochganda yuklab olamiz
        SolvedTestCache.getInstance().setAll(
                HistoryStorage.load(getApplicationContext())
        );

        CardView cvStartQuiz    = findViewById(R.id.cvTopicsTest);
        CardView cvRule         = findViewById(R.id.cvRule);
        CardView cvHistory      = findViewById(R.id.cvHistory);
        CardView cvReferences   = findViewById(R.id.cvReferences);
        CardView cvSuggestions  = findViewById(R.id.cvSuggestions);
        CardView cvVideos       = findViewById(R.id.cvVideos);

        cvStartQuiz.setOnClickListener(v ->
                startActivity(new Intent(this, TopicsTestActivity.class))
        );

        cvVideos.setOnClickListener(v ->
                startActivity(new Intent(this, VideoListActivity.class))
        );

        cvRule.setOnClickListener(v ->
                startActivity(new Intent(this, RuleActivity.class))
        );

        cvHistory.setOnClickListener(v ->
                startActivity(new Intent(this, HistoryActivity.class))
        );

        cvReferences.setOnClickListener(v ->
                startActivity(new Intent(this, ReferencesActivity.class))
        );

        cvSuggestions.setOnClickListener(v ->
                startActivity(new Intent(this, SuggestionsActivity.class))
        );
    }
}
