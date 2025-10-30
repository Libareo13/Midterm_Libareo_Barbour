package com.example.midterm_libareo_barbour;

import java.util.ArrayList;
import java.util.List;

public class DataStore {
    public static List<String> currentTable = new ArrayList<>();
    public static List<Integer> historyNumbers = new ArrayList<>();

    public static void addNumberToHistory(int number) {
        if (!historyNumbers.contains(number)) {
            historyNumbers.add(number);
        }
    }
}
