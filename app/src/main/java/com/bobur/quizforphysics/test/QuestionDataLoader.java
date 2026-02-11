package com.bobur.quizforphysics.test;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class QuestionDataLoader {

    public static List<Question> loadAll(Context context, String fileName) {

        List<Question> list = new ArrayList<>();

        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String jsonString = new String(buffer, "UTF-8");

            JSONArray arr = new JSONArray(jsonString);

            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                Question q = JsonParser.parseQuestion(obj);
                if (q != null) list.add(q);
            }

        } catch (Exception e) {
            Log.e("JSON", "XATO", e);
        }

        Log.d("JSON", "JAMI SAVOLLAR = " + list.size());
        return list;
    }
}
