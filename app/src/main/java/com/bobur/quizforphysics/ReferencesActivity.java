package com.bobur.quizforphysics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ReferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_references);

        RecyclerView recyclerView = findViewById(R.id.recyclerReferences);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Reference> list = new ArrayList<>();

        try {
            InputStream is = getAssets().open("references.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String json = new String(buffer, "UTF-8");
            JSONObject obj = new JSONObject(json);
            JSONArray arr = obj.getJSONArray("references");

            for (int i = 0; i < arr.length(); i++) {
                JSONObject item = arr.getJSONObject(i);
                list.add(new Reference(item.getString("text")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        ReferenceAdapter adapter = new ReferenceAdapter(list);
        recyclerView.setAdapter(adapter);
    }
}
