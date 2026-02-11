package com.bobur.quizforphysics.history;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HistoryStorage {

    private static final String PREF_NAME = "history_pref";
    private static final String KEY_TESTS = "solved_tests_json";

    private static SharedPreferences prefs(Context c) {
        return c.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // ✅ Saqlash (cache -> prefs)
    public static void save(Context c, List<SolvedTestCache.SolvedTest> tests) {
        Gson gson = new Gson();
        String json = gson.toJson(tests);
        prefs(c).edit().putString(KEY_TESTS, json).apply();
    }

    // ✅ Yuklash (prefs -> list)
    public static ArrayList<SolvedTestCache.SolvedTest> load(Context c) {
        String json = prefs(c).getString(KEY_TESTS, null);
        if (json == null || json.trim().isEmpty()) return new ArrayList<>();

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<SolvedTestCache.SolvedTest>>() {}.getType();

        ArrayList<SolvedTestCache.SolvedTest> list = gson.fromJson(json, type);
        return list == null ? new ArrayList<>() : list;
    }

    // ✅ Tozalash
    public static void clear(Context c) {
        prefs(c).edit().remove(KEY_TESTS).apply();
    }
}
