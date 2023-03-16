package ru.otus;

import ru.otus.Interface.Vault;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class VaultImpl implements Vault {

    private NavigableMap<Rubles, Integer> cellsForMoney = new TreeMap<>();
    private int moneyInVault = 0;
    private int smallestBanknotesValue;

    public VaultImpl(Map<Rubles, Integer> initialBanknotesInVault) {
        for (Rubles rubles : Rubles.values()) {
            this.cellsForMoney.put(rubles, 0);
        }
        for (Rubles rubles : initialBanknotesInVault.keySet()) {
            this.cellsForMoney.put(rubles, initialBanknotesInVault.get(rubles));
        }
        this.countBalance();
        this.searchSmallestBanknoteInVault();
    }

    @Override
    public NavigableMap<Rubles, Integer> getCellsForMoney() {
        return this.cellsForMoney;
    }

    @Override
    public int getMoneyInVault() {
        return moneyInVault;
    }

    @Override
    public int getSmallestBanknotesValue() {
        return smallestBanknotesValue;
    }

    @Override
    public void countBalance() {
        this.moneyInVault = 0;
        for (Rubles rubles : cellsForMoney.keySet()) {
            moneyInVault += rubles.getValue() * cellsForMoney.get(rubles);
        }
    }

    @Override
    public void searchSmallestBanknoteInVault() {
        this.smallestBanknotesValue = Rubles.RUB_5000.getValue();
        for (Rubles rubles : cellsForMoney.keySet()) {
            if (rubles.getValue() < smallestBanknotesValue && cellsForMoney.get(rubles) > 0) {
                smallestBanknotesValue = rubles.getValue();
            }
        }
    }


}
