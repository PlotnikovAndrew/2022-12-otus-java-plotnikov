package ru.otus.Interface;

import ru.otus.Rubles;

import java.util.Map;
import java.util.NavigableMap;

public interface ATM {


    int checkBalance();

    void putMoney(Map<Rubles, Integer> putMoneyMap);

    NavigableMap<Rubles, Integer> getOutMoney(int value);
}
