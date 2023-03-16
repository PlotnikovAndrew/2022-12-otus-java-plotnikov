package ru.otus;

import ru.otus.unit.ATMTests;

import java.util.HashMap;
import java.util.NavigableMap;
import java.util.TreeMap;

public class AllTests {
    public static void main(String[] args){
        ATMTests tests = new ATMTests();

        HashMap<Rubles, Integer> initial = new HashMap<>();
        initial.put(Rubles.RUB_50, 0);
        initial.put(Rubles.RUB_100, 32);
        initial.put(Rubles.RUB_200, 124);
        initial.put(Rubles.RUB_500, 15);
        initial.put(Rubles.RUB_1000, 3000);
        initial.put(Rubles.RUB_2000, 132);
        initial.put(Rubles.RUB_5000, 64);

        VaultImpl vault = new VaultImpl(initial);

        HashMap<Rubles, Integer> dop = new HashMap<>();
        dop.put(Rubles.RUB_50, 145);
        dop.put(Rubles.RUB_100, 0);
        dop.put(Rubles.RUB_200, 250);
        dop.put(Rubles.RUB_500, 12);
        dop.put(Rubles.RUB_1000, 14);
        dop.put(Rubles.RUB_2000, 2);
        dop.put(Rubles.RUB_5000, 8);

        NavigableMap<Rubles, Integer> correctPutMap = new TreeMap<>();
        correctPutMap.put(Rubles.RUB_50, 145);
        correctPutMap.put(Rubles.RUB_100, 32);
        correctPutMap.put(Rubles.RUB_200, 250+124);
        correctPutMap.put(Rubles.RUB_500, 12+15);
        correctPutMap.put(Rubles.RUB_1000, 14+3000);
        correctPutMap.put(Rubles.RUB_2000, 2+132);
        correctPutMap.put(Rubles.RUB_5000, 8+64);

        NavigableMap<Rubles, Integer> correctGetOutMap = new TreeMap<>();
        correctGetOutMap.put(Rubles.RUB_5000, 1);
        correctGetOutMap.put(Rubles.RUB_2000, 2);
        correctGetOutMap.put(Rubles.RUB_1000, 1);
        correctGetOutMap.put(Rubles.RUB_500, 1);
        correctGetOutMap.put(Rubles.RUB_200, 1);
        correctGetOutMap.put(Rubles.RUB_100, 1);

        tests.correctCheckBalance(vault, 3619500);
        tests.correctPutMoney(vault, correctPutMap, dop);
        tests.correctGetOutMoney(vault, correctGetOutMap, 8800);

    }
}
