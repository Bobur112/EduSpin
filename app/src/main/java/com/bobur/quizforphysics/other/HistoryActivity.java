package com.bobur.quizforphysics.other;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.bobur.quizforphysics.R;
import com.bobur.quizforphysics.history.SolvedTestCache;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private List<SolvedTestCache.SolvedTest> tests;
    private ArrayList<String> displayList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ListView listView = findViewById(R.id.listViewHistory);
        Button btnClear = findViewById(R.id.btnClearHistory);

        // 🔥 ASOSIY MANBA – cache
        tests = SolvedTestCache.getInstance().getAll();
        displayList = new ArrayList<>();

        if (tests.isEmpty()) {
            displayList.add("📭 Hozircha testlar yo‘q");
        } else {
            for (SolvedTestCache.SolvedTest test : tests) {
                displayList.add(
                        test.subject + "\n"
                                + "To‘g‘ri: " + test.correct
                                + " | Noto‘g‘ri: " + test.incorrect
                                + "\nSana: " + test.date
                );
            }
        }

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                displayList
        );
        listView.setAdapter(adapter);

        // 🔥 RO‘YXAT BOSILGANDA
        listView.setOnItemClickListener((parent, view, position, id) -> {

            // Agar bo‘sh holat bo‘lsa – hech narsa qilmaymiz
            if (tests.isEmpty()) return;

            Log.e("HISTORY_CLICK", "position=" + position);

            Intent intent = new Intent(
                    HistoryActivity.this,
                    TestAnalysisActivity.class
            );

            // 🔥 MUHIM: aynan qaysi test ekanini yuboramiz
            intent.putExtra("index", position);
            startActivity(intent);
        });

        // 🔥 TARIXNI TOZALASH
        btnClear.setOnClickListener(v -> {
            SolvedTestCache.getInstance().clear();
            com.bobur.quizforphysics.history.HistoryStorage.clear(getApplicationContext());
            tests.clear();
            displayList.clear();
            displayList.add("📭 Tarix tozalandi");
            adapter.notifyDataSetChanged();
        });
    }
}
