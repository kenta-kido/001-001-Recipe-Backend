package com.example.backend.util;
import java.util.HashSet;
import java.util.Set;

public class TrigramSimilarity {
    public static double calculate(String s1, String s2) {
        Set<String> trigrams1 = generateTrigrams(s1.toLowerCase());
        Set<String> trigrams2 = generateTrigrams(s2.toLowerCase());

        Set<String> intersection = new HashSet<>(trigrams1);
        intersection.retainAll(trigrams2);

        double similarity = (double) intersection.size() / Math.max(trigrams1.size(), trigrams2.size());
        return similarity;
    }

    private static Set<String> generateTrigrams(String input) {
        Set<String> trigrams = new HashSet<>();
        for (int i = 0; i < input.length() - 2; i++) {
            trigrams.add(input.substring(i, i + 3));
        }
        return trigrams;
    }
}
