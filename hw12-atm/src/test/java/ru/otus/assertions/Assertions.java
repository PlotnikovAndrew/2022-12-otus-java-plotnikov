package ru.otus.assertions;

import ru.otus.Rubles;

import java.util.NavigableMap;

public class Assertions {
    public static void assertCheckBalance(Integer expectedBalance, Integer correctBalance){
        if(!expectedBalance.equals(correctBalance)){
            throw new AssertionError(String.format("Expected %s, return %s", correctBalance, expectedBalance));
        }
    }
    public static void assertCorrectGetPutMoney(NavigableMap<Rubles, Integer> expectedReleasedBanknotes, NavigableMap<Rubles, Integer> correctReleasedBanknotes){
        if(!expectedReleasedBanknotes.equals(correctReleasedBanknotes)){
            throw new AssertionError(String.format("Expected %s, return %s", correctReleasedBanknotes, expectedReleasedBanknotes));
        }
    }
}
