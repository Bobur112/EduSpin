package com.bobur.quizforphysics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bobur.quizforphysics.other.Constants;
import com.bobur.quizforphysics.other.Utils;

import java.util.Calendar;

public class FinalResultActivity extends AppCompatActivity {

    private TextView tvSubject, tvCorrect, tvIncorrect, tvEarned, tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_result);

        Intent intent = getIntent();

        int correctAnswer = intent.getIntExtra(Constants.CORRECT, 0);
        int incorrectAnswer = intent.getIntExtra(Constants.INCORRECT, 0);
        String subject = intent.getStringExtra(Constants.SUBJECT);

        int earnedPoints =
                (correctAnswer * Constants.CORRECT_POINT)
                        - (incorrectAnswer * Constants.INCORRECT_POINT);

        // 🔹 View'lar
        tvSubject = findViewById(R.id.textView16);
        tvCorrect = findViewById(R.id.textView19);
        tvIncorrect = findViewById(R.id.textView27);
        tvEarned = findViewById(R.id.textView28);
        tvDate = findViewById(R.id.textView30);

        // 🔹 Ma’lumotlarni chiqarish
        tvSubject.setText(subject);
        tvCorrect.setText(String.valueOf(correctAnswer));
        tvIncorrect.setText(String.valueOf(incorrectAnswer));
        tvEarned.setText(String.valueOf(earnedPoints));
        tvDate.setText(Utils.formatDate(Calendar.getInstance().getTimeInMillis()));

        // 🔹 Tugmalar
        findViewById(R.id.imageViewFinalResultQuiz).setOnClickListener(v -> goHome());
        findViewById(R.id.btnFinishQuiz).setOnClickListener(v -> goHome());
    }

    private void goHome() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        goHome();
    }
}
