package com.bobur.quizforphysics.test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {

    public static Question parseQuestion(JSONObject obj) throws JSONException {

        // ================= BASIC FIELDS =================
        int topicId = obj.optInt("topicId", 0);
        int questionId = obj.optInt("questionId", 0);

        // Savol matni (har xil nomlar bo‘lishi mumkin)
        String questionText = obj.optString("question",
                obj.optString("text",
                        obj.optString("questionText", null)));

        if (questionText == null || questionText.trim().isEmpty()) {
            throw new JSONException("Savol matni topilmadi");
        }

        Question.QuestionType type =
                Question.QuestionType.valueOf(obj.getString("type"));

        // ================= TRUE / FALSE =================
        if (type == Question.QuestionType.TRUE_FALSE) {

            Object correctObj = obj.opt("correct");
            boolean correctBool = false;

            if (correctObj instanceof Boolean) {
                correctBool = (Boolean) correctObj;
            }
            else if (correctObj instanceof String) {
                String s = ((String) correctObj).trim().toLowerCase();
                // "to'g'ri" yoki "true" bo'lsa true, qolganlar false
                correctBool = s.equals("to’g’ri")
                        || s.equals("to'g'ri")
                        || s.equals("togri")
                        || s.equals("true");
            }

            // UI bilan mos bo‘lsin
            String correctAnswerText = correctBool ? "To‘g‘ri" : "Noto‘g‘ri";

            return new Question(
                    topicId,
                    questionId,
                    questionText,
                    correctAnswerText,
                    type
            );
        }


        // ================= SINGLE CHOICE =================
        if (type == Question.QuestionType.SINGLE_CHOICE) {

            // -------- OPTIONS (options / answers) --------
            List<String> options = new ArrayList<>();

            JSONArray optArr = obj.optJSONArray("options");
            if (optArr == null) optArr = obj.optJSONArray("answers");

            if (optArr != null) {
                for (int i = 0; i < optArr.length(); i++) {
                    options.add(optArr.optString(i));
                }
            }

            // -------- CORRECT ANSWER --------
            List<Integer> correctAnswers = new ArrayList<>();
            Integer idx = null;

            Object correctObj = obj.opt("correct");
            if (correctObj == null) correctObj = obj.opt("correctAnswer");
            if (correctObj == null) correctObj = obj.opt("correctText");

            // 1️⃣ agar number bo‘lsa (index)
            if (correctObj instanceof Number) {
                idx = ((Number) correctObj).intValue();
            }

            // 2️⃣ agar string bo‘lsa
            else if (correctObj instanceof String) {
                String s = ((String) correctObj).trim();

                // string raqam bo‘lsa
                if (s.matches("\\d+")) {
                    idx = Integer.parseInt(s);
                }
                // aks holda matn deb qaraymiz
                else {
                    for (int i = 0; i < options.size(); i++) {
                        if (options.get(i).equalsIgnoreCase(s)) {
                            idx = i;
                            break;
                        }
                    }
                }
            }

            // -------- SAFETY CHECK --------
            if (idx != null && idx >= 0 && idx < options.size()) {
                correctAnswers.add(idx);
            }

            return new Question(
                    topicId,
                    questionId,
                    questionText,
                    options,
                    correctAnswers,
                    type
            );
        }

        // ================= FILL / SHORT ANSWER =================
        Object correctObj =
                obj.opt("correct");

        if (correctObj == null) correctObj = obj.opt("correctAnswer");
        if (correctObj == null) correctObj = obj.opt("answer");
        if (correctObj == null) correctObj = obj.opt("textAnswer");

        String correctAnswerText =
                correctObj != null ? String.valueOf(correctObj).trim() : "";

        return new Question(
                topicId,
                questionId,
                questionText,
                correctAnswerText,
                type
        );

    }
}
