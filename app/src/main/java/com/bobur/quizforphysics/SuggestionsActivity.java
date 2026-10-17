package com.bobur.quizforphysics;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SuggestionsActivity extends AppCompatActivity {

    // Tokenlar local.properties dan BuildConfig orqali o'qiladi (APK ichiga tushadi, lekin
    // version control ga chiqmaydi — bu minimal xavfsizlik darajasi)
    private static final String BOT_TOKEN = BuildConfig.TELEGRAM_BOT_TOKEN;
    private static final String CHAT_ID   = BuildConfig.TELEGRAM_CHAT_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions);

        EditText etFullName   = findViewById(R.id.etFullName);
        EditText etSuggestion = findViewById(R.id.etSuggestion);
        Button   btnSend      = findViewById(R.id.btnSend);

        btnSend.setOnClickListener(view -> {

            String fullName   = etFullName.getText().toString().trim();
            String suggestion = etSuggestion.getText().toString().trim();

            if (fullName.isEmpty()) {
                Toast.makeText(this, "Ism va sharifingizni kiriting", Toast.LENGTH_SHORT).show();
                return;
            }

            if (suggestion.isEmpty()) {
                Toast.makeText(this, "Taklif yozing", Toast.LENGTH_SHORT).show();
                return;
            }

            String telegramMessage =
                    "📩 Yangi taklif\n\n" +
                    "👤 Ism-sharif: " + fullName + "\n" +
                    "🕒 Sana: " + DateFormat.getDateTimeInstance().format(new Date()) + "\n\n" +
                    "💡 Taklif:\n" + suggestion;

            sendToTelegram(telegramMessage);
            saveSuggestionLocally(fullName, suggestion);

            Toast.makeText(this, "Taklif yuborildi va saqlandi ✅", Toast.LENGTH_SHORT).show();
            etSuggestion.setText("");
        });
    }

    // ===================== TELEGRAM =====================
    private void sendToTelegram(String message) {
        new Thread(() -> {
            try {
                String encodedText = URLEncoder.encode(message, "UTF-8");

                String urlString =
                        "https://api.telegram.org/bot" + BOT_TOKEN +
                        "/sendMessage?chat_id=" + CHAT_ID +
                        "&text=" + encodedText;

                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                int responseCode = conn.getResponseCode();
                conn.disconnect();

                runOnUiThread(() -> {
                    if (responseCode == 200) {
                        Toast.makeText(this, "Telegramga yuborildi ✅", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Telegram xato: " + responseCode, Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (Exception e) {
                runOnUiThread(() ->
                        Toast.makeText(this, "Telegramga ulanishda xato ❌", Toast.LENGTH_SHORT).show()
                );
            }
        }).start();
    }

    // ===================== LOCAL SAQLASH =====================
    private void saveSuggestionLocally(String fullName, String suggestion) {
        SharedPreferences prefs = getSharedPreferences("suggestions", MODE_PRIVATE);

        Set<String> list = new HashSet<>(prefs.getStringSet("list", new HashSet<>()));

        String record =
                "👤 " + fullName + "\n" +
                "🕒 " + DateFormat.getDateTimeInstance().format(new Date()) + "\n" +
                "💡 " + suggestion;

        list.add(record);
        prefs.edit().putStringSet("list", list).apply();
    }
}
