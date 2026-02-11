package com.bobur.quizforphysics.other;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistoryManager {

    private static final String PREF_NAME = "quiz_history";
    private static final String KEY_HISTORY = "history_list";

    // 🔹 Natijani saqlash (eski yozuvlar turadi, yangi tepasiga tushadi)
    public static void addHistory(Context context, String topic, int score, int total) {
        if (topic == null) topic = "Noma’lum mavzu";

        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String oldHistory = prefs.getString(KEY_HISTORY, "");

        // Sana va vaqt
        String time = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(new Date());
        int percent = (int) ((score * 100.0f) / total);

        // 🧾 Yangi yozuv formati
        String newEntry = "📘 " + topic +
                "\n✅ To‘g‘ri javoblar: " + score + " / " + total +
                "\n⭐ Ball: " + percent + "%" +
                "\n📅 Sana: " + time;

        // 🔹 Yangi yozuvni eng tepasiga qo‘shamiz
        String updated = oldHistory.isEmpty() ? newEntry : newEntry + ";;" + oldHistory;

        prefs.edit().putString(KEY_HISTORY, updated).apply();
    }

    // 🔹 Tarixni olish (tepadan pastga)
    public static List<String> getHistory(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String data = prefs.getString(KEY_HISTORY, "");
        List<String> historyList = new ArrayList<>();

        if (!data.isEmpty()) {
            String[] items = data.split(";;");
            for (String item : items) {
                historyList.add(item);
            }
        }
        return historyList;
    }

    // 🔹 Tarixni tozalash
    public static void clearHistory(Context context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
                .edit()
                .clear()
                .apply();
    }
}
