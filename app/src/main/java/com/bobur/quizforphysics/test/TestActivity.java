package com.bobur.quizforphysics.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.bobur.quizforphysics.R;
import com.bobur.quizforphysics.history.SolvedTestCache;
import com.bobur.quizforphysics.test.model.ReviewItem;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    // ===== UI =====
    private TextView tvQuestion, tvTopicName;
    private Button btnNext;

    private LinearLayout layoutTF, layoutSingle, layoutFill;

    private RadioGroup rgTF, rgSingle;
    private RadioButton rbTFTrue, rbTFFalse;
    private RadioButton rb1, rb2, rb3, rb4;

    private EditText etAnswer;

    // ===== DATA =====
    private List<Question> questions = new ArrayList<>();
    private int index = 0;
    private int score = 0;
    private int topicId;

    private ArrayList<ReviewItem> reviewList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        // ===== INTENT =====
        topicId = getIntent().getIntExtra("topicId", -1);
        String topicName = getIntent().getStringExtra("topicName");

        Log.d("TOPIC_CHECK", "topicId = " + topicId);

        // ===== VIEW BIND =====
        tvTopicName = findViewById(R.id.tvTopicName);
        tvQuestion = findViewById(R.id.tvQuestion);
        btnNext = findViewById(R.id.btnNext);

        layoutTF = findViewById(R.id.layoutTF);
        layoutSingle = findViewById(R.id.layoutSingle);
        layoutFill = findViewById(R.id.layoutFill);

        rgTF = findViewById(R.id.radioGroupTF);
        rgSingle = findViewById(R.id.radioGroup);

        rbTFTrue = findViewById(R.id.rbTF1);
        rbTFFalse = findViewById(R.id.rbTF2);

        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        rb4 = findViewById(R.id.rb4);

        etAnswer = findViewById(R.id.etAnswer);

        tvTopicName.setText(topicName);

        // ===== LOAD QUESTIONS =====
        String fileName = "topic" + topicId + ".json";
        List<Question> all = QuestionDataLoader.loadAll(this, fileName);

        // 🔥 MUHIM O‘ZGARISH: faqat shu yerda test tuziladi
        questions = TestBlueprint.buildFixedTest(all, topicId);

        if (questions == null || questions.isEmpty()) {
            Toast.makeText(this, "Savollar topilmadi!", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        showQuestion();

        btnNext.setOnClickListener(v -> {
            checkAnswerAndSaveReview();
            index++;

            if (index < questions.size()) {
                showQuestion();
            } else {
                // ===============================
                // 🔥 TESTNI TARIXGA SAQLASH
                // ===============================
                SolvedTestCache.SolvedTest solved = new SolvedTestCache.SolvedTest();

                solved.subject = topicName;
                solved.correct = score;
                solved.incorrect = questions.size() - score;
                solved.date = android.text.format.DateFormat
                        .format("yyyy-MM-dd HH:mm",
                                System.currentTimeMillis()).toString();

                // 🔹 Tahlil uchun savollar va javoblar
                solved.questions = new ArrayList<>();
                solved.userAnswers = new ArrayList<>();
                solved.correctAnswers = new ArrayList<>();

                for (ReviewItem item : reviewList) {
                    solved.questions.add(item.getQuestion());
                    solved.userAnswers.add(item.getUserAnswer());
                    solved.correctAnswers.add(item.getCorrectAnswer());
                }

                SolvedTestCache.getInstance().add(solved);


                // ✅ Tarixni doimiy saqlash (SharedPreferences)
                com.bobur.quizforphysics.history.HistoryStorage.save(
                        getApplicationContext(),
                        SolvedTestCache.getInstance().getAll()
                );



                // ===============================
                // 🔹 OLDINGI OQIM O‘ZGARMAYDI
                // ===============================
                Intent i = new Intent(this, ResultActivity.class);
                i.putExtra("score", score);
                i.putExtra("total", questions.size());
                i.putExtra("topicId", topicId);
                i.putExtra("topicName", topicName);
                i.putParcelableArrayListExtra("reviewList", reviewList);
                startActivity(i);
                finish();
            }
        });
    }

    // ================= SHOW QUESTION =================
    private void showQuestion() {

        hideAll();

        // 🔥 TO‘LIQ RESET (6 ta variant muammosi SHU YERDA YOPILADI)
        rgTF.clearCheck();
        rgSingle.clearCheck();

        rb1.setText("");
        rb2.setText("");
        rb3.setText("");
        rb4.setText("");

        Question q = questions.get(index);
        tvQuestion.setText((index + 1) + ". " + q.getQuestionText());

        switch (q.getType()) {

            case TRUE_FALSE:
                layoutTF.setVisibility(View.VISIBLE);
                rbTFTrue.setText("To‘g‘ri");
                rbTFFalse.setText("Noto‘g‘ri");
                break;

            case SINGLE_CHOICE:
                layoutSingle.setVisibility(View.VISIBLE);

                List<String> o = q.getOptions();
                if (o != null && o.size() == 4) {
                    rb1.setText(o.get(0));
                    rb2.setText(o.get(1));
                    rb3.setText(o.get(2));
                    rb4.setText(o.get(3));
                } else {
                    Log.e("TEST", "Variantlar soni noto‘g‘ri: " +
                            (o == null ? "null" : o.size()));
                }
                break;

            case FILL_BLANK:
            case SHORT_ANSWER:
                layoutFill.setVisibility(View.VISIBLE);
                etAnswer.setText("");
                break;
        }
    }

    // ================= CHECK ANSWER + SAVE REVIEW =================
    private void checkAnswerAndSaveReview() {

        Question q = questions.get(index);

        String userAnswer = "-";
        String correctAnswer = getCorrectAnswerForReview(q);
        boolean isCorrect = false;

        switch (q.getType()) {

            case TRUE_FALSE:
                int tfId = rgTF.getCheckedRadioButtonId();
                if (tfId != -1) {
                    RadioButton rb = findViewById(tfId);
                    userAnswer = rb.getText().toString();
                    isCorrect = userAnswer.equalsIgnoreCase(correctAnswer);
                    if (isCorrect) score++;
                }
                break;

            case SINGLE_CHOICE:
                int id = rgSingle.getCheckedRadioButtonId();
                if (id != -1) {
                    RadioButton rb = findViewById(id);
                    userAnswer = rb.getText().toString();
                    isCorrect = userAnswer.equalsIgnoreCase(correctAnswer);
                    if (isCorrect) score++;
                }
                break;

            case FILL_BLANK:
            case SHORT_ANSWER:
                String ans = etAnswer.getText().toString().trim();
                userAnswer = ans.isEmpty() ? "-" : ans;

                if (!ans.isEmpty() && correctAnswer != null) {
                    isCorrect = ans.equalsIgnoreCase(correctAnswer);
                }

                if (isCorrect) score++;
                break;
        }

        reviewList.add(new ReviewItem(
                q.getQuestionText(),
                userAnswer,
                correctAnswer,
                isCorrect
        ));
    }

    // ================= CORRECT ANSWER =================
    private String getCorrectAnswerForReview(Question q) {
        Log.d("ANS_DEBUG",
                "options=" + q.getOptions() +
                        " correct=" + q.getCorrectAnswers());

        if (q == null) return "";

        switch (q.getType()) {

            case TRUE_FALSE:
            case FILL_BLANK:
            case SHORT_ANSWER:
                return q.getCorrectAnswerText() == null ? "" : q.getCorrectAnswerText();

            case SINGLE_CHOICE:
                List<String> opt = q.getOptions();
                List<Integer> correct = q.getCorrectAnswers();

                if (opt == null || correct == null || correct.isEmpty()) return "";

                int idx = correct.get(0) - 1; // JSON 1..4
                if (idx < 0 || idx >= opt.size()) return "";

                return opt.get(idx);
        }

        return "";
    }

    private void hideAll() {
        layoutTF.setVisibility(View.GONE);
        layoutSingle.setVisibility(View.GONE);
        layoutFill.setVisibility(View.GONE);
    }
}
