package com.bobur.quizforphysics.other;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.bobur.quizforphysics.R;
import com.bobur.quizforphysics.history.SolvedTestCache;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TestAnalysisActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 🔥 ReviewActivity layoutidan foydalanamiz
        setContentView(R.layout.activity_review);

        TextView tv = findViewById(R.id.tvReview);
        FloatingActionButton fab = findViewById(R.id.btnAskAI);

        int index = getIntent().getIntExtra("index", -1);
        if (index == -1) {
            tv.setText("Xatolik: test topilmadi.");
            return;
        }

        SolvedTestCache.SolvedTest test =
                SolvedTestCache.getInstance().get(index);

        if (test == null) {
            tv.setText("Xatolik: test topilmadi.");
            return;
        }

        // 🔥 Tahlilni chiroyli ko‘rinishda yig‘amiz
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < test.questions.size(); i++) {

            String question = test.questions.get(i);
            String userAnswer = test.userAnswers.get(i);
            String correctAnswer = test.correctAnswers.get(i);

            sb.append(i + 1).append(") ")
                    .append(question == null ? "-" : question)
                    .append("\n");

            sb.append("Sizning javob: ")
                    .append(userAnswer == null ? "-" : userAnswer)
                    .append("\n");

            sb.append("To‘g‘ri javob: ")
                    .append(correctAnswer == null ? "-" : correctAnswer)
                    .append("\n");

            sb.append("Natija: ")
                    .append(userAnswer != null && userAnswer.equalsIgnoreCase(correctAnswer)
                            ? "✅ To‘g‘ri"
                            : "❌ Noto‘g‘ri")
                    .append("\n\n");
        }

        tv.setText(sb.toString());

        // 🔥 AI Chat BOT – History’dan kirganda ham ishlaydi
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
