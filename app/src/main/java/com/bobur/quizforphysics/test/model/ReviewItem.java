package com.bobur.quizforphysics.test.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ReviewItem implements Parcelable {

    private String question;
    private String userAnswer;
    private String correctAnswer;
    private boolean correct;

    public ReviewItem(String question, String userAnswer, String correctAnswer, boolean correct) {
        this.question = question;
        this.userAnswer = userAnswer;
        this.correctAnswer = correctAnswer;
        this.correct = correct;
    }

    protected ReviewItem(Parcel in) {
        question = in.readString();
        userAnswer = in.readString();
        correctAnswer = in.readString();
        correct = in.readByte() != 0;
    }

    public static final Creator<ReviewItem> CREATOR = new Creator<ReviewItem>() {
        @Override
        public ReviewItem createFromParcel(Parcel in) {
            return new ReviewItem(in);
        }

        @Override
        public ReviewItem[] newArray(int size) {
            return new ReviewItem[size];
        }
    };

    // ✅ GETTERLAR (SENGA KERAK BO‘LGANLARI)
    public String getQuestion() {
        return question;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public boolean isCorrect() {
        return correct;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeString(userAnswer);
        dest.writeString(correctAnswer);
        dest.writeByte((byte) (correct ? 1 : 0));
    }
}
