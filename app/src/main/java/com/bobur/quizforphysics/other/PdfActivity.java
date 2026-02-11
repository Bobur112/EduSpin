package com.bobur.quizforphysics.other;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.bobur.quizforphysics.R;

public class PdfActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        // PDF view
        PDFView pdfView = findViewById(R.id.pdfView);

        // RuleActivity’dan PDF nomini olish
        String pdfName = getIntent().getStringExtra("pdfName");
        if (pdfName != null) {
            pdfView.fromAsset(pdfName)
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableDoubletap(true)
                    .spacing(10)
                    .load();
        }
        // AI Chat tugmasi (past o‘ngda)
        FloatingActionButton aiBtn = findViewById(R.id.btn_ai_chat);
        aiBtn.setOnClickListener(v -> {
            AiChatBottomSheet sheet = new AiChatBottomSheet();
            sheet.show(getSupportFragmentManager(), "AI_CHAT");
        });

    }
}
