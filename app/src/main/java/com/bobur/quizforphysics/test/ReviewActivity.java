package com.bobur.quizforphysics.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.bobur.quizforphysics.R;
import com.bobur.quizforphysics.other.AiChatBottomSheet;
import com.bobur.quizforphysics.test.model.ReviewItem;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {

    private ArrayList<ReviewItem> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        TextView tv = findViewById(R.id.tvReview);
        FloatingActionButton fab = findViewById(R.id.btnAskAI);

        list = getIntent().getParcelableArrayListExtra("reviewList");

        if (list == null || list.isEmpty()) {
            tv.setText("Tahlil uchun ma’lumot topilmadi.");
            return;
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {
            ReviewItem item = list.get(i);

            sb.append(i + 1).append(") ")
                    .append(item.getQuestion() == null ? "-" : item.getQuestion())
                    .append("\n");

            sb.append("Sizning javob: ")
                    .append(item.getUserAnswer() == null ? "-" : item.getUserAnswer())
                    .append("\n");

            sb.append("To‘g‘ri javob: ")
                    .append(item.getCorrectAnswer() == null ? "-" : item.getCorrectAnswer())
                    .append("\n");

            sb.append("Natija: ")
                    .append(item.isCorrect() ? "✅ To‘g‘ri" : "❌ Noto‘g‘ri")
                    .append("\n\n");
        }

        tv.setText(sb.toString());

        fab.setOnClickListener(v -> {
            String context =
                    "Men test tahlilidaman. Quyidagi natijani tushuntirib bering:\n\n"
                            + tv.getText().toString();

            AiChatBottomSheet sheet =
                    AiChatBottomSheet.newInstance(context);

            sheet.show(getSupportFragmentManager(), "AiChatBottomSheet");
        });
    }
}
