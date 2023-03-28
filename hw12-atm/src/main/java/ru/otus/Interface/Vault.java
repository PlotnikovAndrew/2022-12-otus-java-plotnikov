package ru.otus.Interface;

import ru.otus.Rubles;

import java.util.NavigableMap;

public interface Vault {
    NavigableMap<Rubles, Integer> getCellsForMoney();

    int getMoneyInVault();

    int getSmallestBanknotesValue();

    void countBalance();

    void searchSmallestBanknoteInVault();
}
