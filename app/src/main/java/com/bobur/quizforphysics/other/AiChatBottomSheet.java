package com.bobur.quizforphysics.other;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.bobur.quizforphysics.BuildConfig;
import com.bobur.quizforphysics.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AiChatBottomSheet extends BottomSheetDialogFragment {

    private EditText etQuestion;
    private TextView tvChat;
    private Button btnSend;

    private static final MediaType JSON = MediaType.parse("application/json");

    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build();

    // ================== VIEW ==================
    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bottomsheet_ai_chat, container, false);

        Log.d("OPENAI_KEY_TEST", "KEY = " + BuildConfig.OPENAI_API_KEY);


        etQuestion = view.findViewById(R.id.et_question);
        tvChat = view.findViewById(R.id.tv_response);
        btnSend = view.findViewById(R.id.btn_send);

        btnSend.setOnClickListener(v -> send());

        return view;
    }

    // ================== BOTTOMSHEET HEIGHT / POSITION ==================
    @Override
    public void onStart() {
        super.onStart();

        View view = getView();
        if (view == null) return;

        View parent = (View) view.getParent();
        if (parent == null) return;

        parent.post(() -> {
            if (!isAdded()) return;

            int screenHeight = requireContext().getResources().getDisplayMetrics().heightPixels;

            // 🔥 75% balandlik: teparoq ko‘tariladi, pastda bo‘shliq qolmaydi
            ViewGroup.LayoutParams params = parent.getLayoutParams();
            params.height = (int) (screenHeight * 0.75);
            parent.setLayoutParams(params);

            BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(parent);
            behavior.setPeekHeight(params.height);
            behavior.setSkipCollapsed(true);
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        });
    }

    // ================== SEND ==================
    private void send() {
        String question = etQuestion.getText().toString().trim();
        if (question.isEmpty()) return;

        appendUser(question);

        // 🔥 Yuborgandan keyin input bo‘shasin
        etQuestion.setText("");
        etQuestion.requestFocus();

        appendSystem("⏳ AI yozmoqda...");

        sendToChatGPT(question);
    }

    // ================== CHAT UI HELPERS ==================
    private void appendUser(String text) {
        tvChat.append("\n\n🧑‍💻 Siz:\n" + text);
        scrollDown();
    }

    private void appendAi(String text) {
        tvChat.append("\n\n🤖 AI:\n" + text);
        scrollDown();
    }

    private void appendSystem(String text) {
        tvChat.append("\n\n" + text);
        scrollDown();
    }

    private void scrollDown() {
        tvChat.post(() -> {
            if (tvChat.getLayout() == null) return;
            int scrollAmount =
                    tvChat.getLayout().getLineTop(tvChat.getLineCount()) - tvChat.getHeight();
            tvChat.scrollTo(0, Math.max(scrollAmount, 0));
        });
    }

    // ================== API ==================
    private void sendToChatGPT(String question) {
        try {
            // context bo‘lsa promptga qo‘shamiz
            String context = "";
            Bundle args = getArguments();
            if (args != null) context = args.getString("context", "");

            String prompt = (context == null || context.trim().isEmpty())
                    ? question
                    : "Kontekst:\n" + context + "\n\nSavol:\n" + question;

            JSONObject json = new JSONObject();
            json.put("model", "gpt-4o-mini");

            JSONArray input = new JSONArray();
            JSONObject msg = new JSONObject();
            msg.put("role", "user");

            JSONArray content = new JSONArray();
            JSONObject c = new JSONObject();
            c.put("type", "input_text");
            c.put("text", prompt);
            content.put(c);

            msg.put("content", content);
            input.put(msg);
            json.put("input", input);

            RequestBody body = RequestBody.create(json.toString(), JSON);

            Request request = new Request.Builder()
                    .url("https://api.openai.com/v1/responses")
                    .addHeader("Authorization", "Bearer " + BuildConfig.OPENAI_API_KEY)
                    .post(body)
                    .build();

            new Thread(() -> {
                try {
                    Response response = client.newCall(request).execute();
                    String raw = response.body() != null ? response.body().string() : "";

                    if (!isAdded()) return;

                    if (!response.isSuccessful()) {
                        runUi("❌ Xato: " + shortError(raw));
                        return;
                    }

                    String answer = extractAnswer(raw);
                    runUi(answer.isEmpty() ? "Javob topilmadi" : answer);

                } catch (Exception e) {
                    runUi("❌ Xato: " + e.getMessage());
                }
            }).start();

        } catch (Exception e) {
            appendSystem("❌ JSON xato: " + e.getMessage());
        }
    }

    private void runUi(String text) {
        if (!isAdded() || getActivity() == null) return;
        requireActivity().runOnUiThread(() -> appendAi(text));
    }

    private String shortError(String raw) {
        if (raw == null) return "Noma'lum";
        raw = raw.trim();
        if (raw.length() > 180) return raw.substring(0, 180) + "...";
        return raw;
    }

    // ================== RESPONSES API PARSE ==================
    private String extractAnswer(String raw) {
        try {
            JSONObject obj = new JSONObject(raw);

            JSONObject err = obj.optJSONObject("error");
            if (err != null) {
                return "❌ Xato: " + err.optString("message", "Noma'lum");
            }

            JSONArray output = obj.optJSONArray("output");
            if (output == null) return "";

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < output.length(); i++) {
                JSONObject item = output.optJSONObject(i);
                if (item == null) continue;
                if (!"message".equals(item.optString("type"))) continue;

                JSONArray content = item.optJSONArray("content");
                if (content == null) continue;

                for (int j = 0; j < content.length(); j++) {
                    JSONObject part = content.optJSONObject(j);
                    if (part == null) continue;

                    if ("output_text".equals(part.optString("type"))) {
                        sb.append(part.optString("text", ""));
                    }
                }
            }

            return sb.toString();

        } catch (Exception e) {
            return "❌ Parse xato: " + e.getMessage();
        }
    }

    // ================== FACTORY METHODS ==================
    public static AiChatBottomSheet newInstance(String context) {
        AiChatBottomSheet sheet = new AiChatBottomSheet();
        Bundle b = new Bundle();
        b.putString("context", context);
        sheet.setArguments(b);
        return sheet;
    }

    public static AiChatBottomSheet newInstance() {
        return new AiChatBottomSheet();
    }
}
