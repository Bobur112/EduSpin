package com.bobur.quizforphysics.test;

import java.util.ArrayList;
import java.util.List;

public class Question {

    public enum QuestionType {
        TRUE_FALSE,
        SINGLE_CHOICE,
        FILL_BLANK,
        SHORT_ANSWER
    }

    private int topicId;
    private int questionId;

    private String questionText;
    private QuestionType type;

    // ===== SINGLE_CHOICE =====
    private List<String> options;
    private List<Integer> correctAnswers; // 0-based index

    // ===== TEXT ANSWER =====
    private String correctAnswerText;

    // =====================================================
    // 🔹 KONSTRUKTOR: SINGLE_CHOICE / TRUE_FALSE
    // =====================================================
    public Question(
            int topicId,
            int questionId,
            String questionText,
            List<String> options,
            List<Integer> correctAnswers,
            QuestionType type
    ) {
        this.topicId = topicId;
        this.questionId = questionId;
        this.questionText = questionText;
        this.type = type;

        // 🔒 null bo‘lmasin
        this.options = (options != null) ? options : new ArrayList<>();
        this.correctAnswers = (correctAnswers != null) ? correctAnswers : new ArrayList<>();

        this.correctAnswerText = null;
    }

    // =====================================================
    // 🔹 KONSTRUKTOR: FILL_BLANK / SHORT_ANSWER / TRUE_FALSE(TEXT)
    // =====================================================
    public Question(
            int topicId,
            int questionId,
            String questionText,
            String correctAnswerText,
            QuestionType type
    ) {
        this.topicId = topicId;
        this.questionId = questionId;
        this.questionText = questionText;
        this.type = type;

        this.correctAnswerText = correctAnswerText;

        // 🔒 SINGLE_CHOICE ishlatilmasa ham null bo‘lmasin
        this.options = new ArrayList<>();
        this.correctAnswers = new ArrayList<>();
    }

    // ================= GETTERLAR =================

    public int getTopicId() {
        return topicId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public QuestionType getType() {
        return type;
    }

    // ===== SINGLE_CHOICE =====

    public List<String> getOptions() {
        return options;
    }

    public List<Integer> getCorrectAnswers() {
        return correctAnswers;
    }

    // ===== TEXT ANSWER =====

    public String getCorrectAnswerText() {
        return correctAnswerText;
    }

    // ================= DEBUG (ixtiyoriy, foydali) =================
    @Override
    public String toString() {
        return "Question{" +
                "topicId=" + topicId +
                ", questionId=" + questionId +
                ", type=" + type +
                ", questionText='" + questionText + '\'' +
                ", options=" + options +
                ", correctAnswers=" + correctAnswers +
                ", correctAnswerText='" + correctAnswerText + '\'' +
                '}';
    }
}
