package com.bobur.quizforphysics.history;

import java.util.ArrayList;
import java.util.List;

public class SolvedTestCache {

    private static SolvedTestCache instance;

    // ✅ Bitta yechilgan test modeli
    public static class SolvedTest {
        public String subject;                 // mavzu nomi
        public int correct;                    // to‘g‘ri
        public int incorrect;                  // noto‘g‘ri
        public String date;                    // sana (string)

        public ArrayList<String> questions;       // savollar
        public ArrayList<String> userAnswers;     // foydalanuvchi javobi (MATN)
        public ArrayList<String> correctAnswers;  // to‘g‘ri javob (MATN)
    }

    private final List<SolvedTest> list = new ArrayList<>();

    private SolvedTestCache() {}

    public static synchronized SolvedTestCache getInstance() {
        if (instance == null) {
            instance = new SolvedTestCache();
        }
        return instance;
    }

    // ✅ Yangi testni qo‘shish
    public void add(SolvedTest test) {
        if (test == null) return;
        list.add(test);
    }

    // ✅ Hammasini olish
    public List<SolvedTest> getAll() {
        return list;
    }

    // ✅ Index bo‘yicha olish
    public SolvedTest get(int index) {
        if (index < 0 || index >= list.size()) return null;
        return list.get(index);
    }

    // ✅ Tarixni tozalash
    public void clear() {
        list.clear();
    }

    // ✅ Nechta test borligini bilish
    public int size() {
        return list.size();
    }

    // 🔥 BUTUN RO‘YXATNI ALMASHTIRIB QO‘YISH
    public void setAll(List<SolvedTest> newList) {
        list.clear();
        if (newList != null) list.addAll(newList);
    }

}
