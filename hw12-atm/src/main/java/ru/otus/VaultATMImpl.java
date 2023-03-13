package ru.otus;

import ru.otus.Interface.VaultATM;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class VaultATMImpl implements VaultATM {

    private final Map<Rubles, Integer> cellsForMoney = new TreeMap<>();
    private int moneyInVault = 0;
    private int smallestBanknotesValue;


    public VaultATMImpl() {
        for (Rubles rub : Rubles.values()) {
            cellsForMoney.put(rub, 100);
        }
        this.countBalance();
        this.searchSmallestBanknoteInVault();
        /*TODO
        System.out.println(moneyInVault);
        System.out.println(cellsForMoney);
        System.out.println(smallestBanknotesValue);
        */
    }


    @Override
    public void checkBalance() {
        System.out.println(moneyInVault);
    }

    @Override
    public void putMoney(HashMap<Rubles, Integer> putMoneyMap) {
        for (Rubles rubles : putMoneyMap.keySet()) {
            Integer numberOfBanknotes = cellsForMoney.get(rubles);
            Integer addBanknotes = putMoneyMap.get(rubles);
            cellsForMoney.put(rubles, numberOfBanknotes + addBanknotes);
            System.out.println(cellsForMoney);
        }
        this.countBalance();
        this.searchSmallestBanknoteInVault();
    }

    @Override
    public void putMoney(Rubles rubles, int numberOfBanknotes) {
        HashMap<Rubles, Integer> putMoneyMap = new HashMap<>();
        putMoneyMap.put(rubles, numberOfBanknotes);
        this.putMoney(putMoneyMap);
    }

    @Override
    public void getOutMoney(int value) {
        if (value > this.moneyInVault){
            throw new RuntimeException("Not enough money in the account");
        }
        if ((value % smallestBanknotesValue) != 0){
            throw new RuntimeException("The amount must be a multiple of " + smallestBanknotesValue);
        }

    }

    private void countBalance(){
        moneyInVault = 0;
        for (Rubles rubles : cellsForMoney.keySet()){
            moneyInVault += rubles.getValue() * cellsForMoney.get(rubles);
        }
    }

    private void searchSmallestBanknoteInVault(){
        smallestBanknotesValue = Rubles.RUB_5000.getValue();
        for (Rubles rubles : cellsForMoney.keySet()){
            if (rubles.getValue() < smallestBanknotesValue && cellsForMoney.get(rubles) > 0){
                smallestBanknotesValue = rubles.getValue();
            }
        }
        //TODO
        System.out.println(smallestBanknotesValue);
    }
}
