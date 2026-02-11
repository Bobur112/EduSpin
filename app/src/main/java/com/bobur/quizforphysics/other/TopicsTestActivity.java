package com.bobur.quizforphysics.other;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.bobur.quizforphysics.R;
import com.bobur.quizforphysics.test.TestActivity;

import java.util.ArrayList;

public class TopicsTestActivity extends AppCompatActivity {

    ArrayList<String> allTopics = new ArrayList<>();
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics_test);

        ListView listView = findViewById(R.id.listViewTopicsTest);
        prefs = getSharedPreferences("progress", MODE_PRIVATE);

        // 🔹 Barcha mavzular
        allTopics.add("1-mavzu: Qattiq jismlarning magnit xususiyatliga qarab bo‘linishi. Atomlar va ularning magnit momentlari");
        allTopics.add("2-mavzu: Paramagnetizm va Ferromagnetizm. Stoner modeli. Kyuri temperaturasi");
        allTopics.add("3-mavzu: Magnit yarimo‘tkazgichlar. Zener p–d almashuvi");
        allTopics.add("4-mavzu: Spin kiritish va relaksatsiya mexanizmlari");
        allTopics.add("5-mavzu: Anomal va Spin-Hall effektlar");
        allTopics.add("6-mavzu: Magnit yarimo‘tkazgichlar optikasi. Xanle effekti");
        allTopics.add("7-mavzu: Bipolyar spintronik qurilmalar");
        allTopics.add("8-mavzu: Ikki o‘lchovli magnit yarimo‘tkazgichlar");
        allTopics.add("9-mavzu: Termospintronika va Spin Zeebek effekti");
        allTopics.add("10-mavzu: Magnit kvant nuqtalar va kvant qurilmalar");

        // 🔹 Adapter
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, allTopics);
        listView.setAdapter(adapter);

        // 🔹 Mavzu bosilganda
        listView.setOnItemClickListener((parent, view, position, id) -> {

            if (position == 0) {
                openTest(allTopics.get(position), position);
            } else {
                int clickedTopicId = position + 1;
                boolean unlocked = prefs.getBoolean("topic_unlocked_" + clickedTopicId, false);

                if (unlocked) {
                    openTest(allTopics.get(position), position);
                } else {
                    Toast.makeText(
                            this,
                            "❌ Oldingi testni kamida 60% bilan tugating!",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
    }

    // 🔹 Testni ochish (FAQAT SHU ISH)
    private void openTest(String topicName, int position) {
        Intent intent = new Intent(this, TestActivity.class);

        int topicId = position + 1;

        intent.putExtra("topicId", topicId);
        intent.putExtra("topicName", topicName);

        startActivity(intent);
    }
}
