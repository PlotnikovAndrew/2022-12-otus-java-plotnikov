package ru.otus.Interface;

import ru.otus.Rubles;

import java.util.HashMap;

public interface VaultATM {


    void checkBalance();

    void putMoney(HashMap<Rubles, Integer> putMoneyMap);
    void putMoney(Rubles rubles, int numberOfBanknotes);
    void getOutMoney(int value);
}
