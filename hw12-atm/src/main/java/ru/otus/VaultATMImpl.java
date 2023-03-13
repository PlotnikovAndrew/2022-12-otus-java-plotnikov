package ru.otus;

import ru.otus.Interface.VaultATM;

import java.util.HashMap;
import java.util.Map;

public class VaultATMImpl implements VaultATM {

    private final Map<Rubles, Integer> cellsForMoney = new HashMap<>();
    private int moneyInVault = 0;


    public VaultATMImpl() {
        for (Rubles rub : Rubles.values()) {
            cellsForMoney.put(rub, 100);
        }
        this.countBalance();
        System.out.println(moneyInVault);
        System.out.println(cellsForMoney);
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
    }

    @Override
    public void putMoney(Rubles rubles, int numberOfBanknotes) {
        HashMap<Rubles, Integer> putMoneyMap = new HashMap<>();
        putMoneyMap.put(rubles, numberOfBanknotes);
        this.putMoney(putMoneyMap);
    }

    @Override
    public void getOutMoney(int value) {
//        if
    }

    private void countBalance(){
        moneyInVault = 0;
        for (Rubles rubles : cellsForMoney.keySet()){
            moneyInVault += rubles.getValue() * cellsForMoney.get(rubles);
        }
    }
}
