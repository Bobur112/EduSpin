package com.bobur.quizforphysics.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestBlueprint {

    public static List<Question> buildFixedTest(List<Question> all, int topicId) {

        List<Question> q1_30 = new ArrayList<>();
        List<Question> q31_60 = new ArrayList<>();
        List<Question> q61_80 = new ArrayList<>();
        List<Question> q81_100 = new ArrayList<>();

        for (Question q : all) {
            if (q.getTopicId() != topicId) continue;

            int id = q.getQuestionId();
            if (id >= 1 && id <= 30) q1_30.add(q);
            else if (id >= 31 && id <= 60) q31_60.add(q);
            else if (id >= 61 && id <= 80) q61_80.add(q);
            else if (id >= 81 && id <= 100) q81_100.add(q);
        }

        Collections.shuffle(q1_30);
        Collections.shuffle(q31_60);
        Collections.shuffle(q61_80);
        Collections.shuffle(q81_100);

        List<Question> result = new ArrayList<>();

        result.addAll(q1_30.subList(0, Math.min(3, q1_30.size())));
        result.addAll(q31_60.subList(0, Math.min(3, q31_60.size())));
        result.addAll(q61_80.subList(0, Math.min(2, q61_80.size())));
        result.addAll(q81_100.subList(0, Math.min(2, q81_100.size())));

        Collections.shuffle(result);
        return result;
    }
}
