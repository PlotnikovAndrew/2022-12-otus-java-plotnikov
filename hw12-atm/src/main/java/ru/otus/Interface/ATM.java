package ru.otus.Interface;

import ru.otus.Rubles;

import java.util.Map;

public interface ATM {


    void checkBalance();

    void putMoney(Map<Rubles, Integer> putMoneyMap);

    void getOutMoney(int value);
}
