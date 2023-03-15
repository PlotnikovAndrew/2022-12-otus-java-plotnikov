package ru.otus;

import ru.otus.Interface.VaultATM;

import java.util.*;

public class VaultATMImpl implements VaultATM {

    private final NavigableMap<Rubles, Integer> cellsForMoney = new TreeMap<>();
    private int moneyInVault = 0;
    private int smallestBanknotesValue;

    public VaultATMImpl(Map<Rubles, Integer> initialBanknotesInVault) {
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
    public void checkBalance() {
        System.out.println("Balance now: " + moneyInVault);
    }

    @Override
    public void putMoney(Map<Rubles, Integer> putMoneyMap) {
        System.out.print("before putMoney: ");
        this.printCellsForMoney();
        for (Rubles rubles : putMoneyMap.keySet()) {
            Integer numberOfBanknotes = cellsForMoney.get(rubles);
            Integer addBanknotes = putMoneyMap.get(rubles);
            if (addBanknotes < 0) {
                throw new RuntimeException("Negative number of banknotes");
            }
            cellsForMoney.put(rubles, numberOfBanknotes + addBanknotes);
        }
        this.countBalance();
        this.searchSmallestBanknoteInVault();
        System.out.print("after putMoney: ");
        this.printCellsForMoney();
    }

    @Override
    public void getOutMoney(int value) {

        this.validationCheck(value);

        System.out.print("before getOutMoney: ");
        this.printCellsForMoney();

        NavigableSet<Rubles> rublesSet = cellsForMoney.descendingKeySet();
        List<Rubles> rublesList = new ArrayList<>(rublesSet);
        List<Integer> numbersOfBanknotesList = this.createAndGetNumbersOfBanknotesList(rublesList);

        int val = value;
        for (int i = 0; i < numbersOfBanknotesList.size(); i++) {
            if (val < rublesList.get(i).getValue() || numbersOfBanknotesList.get(i) == 0) {
                continue;
            }
            val = getOutBanknotesOneDenomination(val, rublesList.get(i), numbersOfBanknotesList.get(i));
        }
        this.countBalance();
        this.searchSmallestBanknoteInVault();
        this.checkBalance();

        System.out.print("after getOutMoney: ");
        this.printCellsForMoney();
    }

    private void countBalance() {
        moneyInVault = 0;
        for (Rubles rubles : cellsForMoney.keySet()) {
            moneyInVault += rubles.getValue() * cellsForMoney.get(rubles);
        }
    }

    private void searchSmallestBanknoteInVault() {
        smallestBanknotesValue = Rubles.RUB_5000.getValue();
        for (Rubles rubles : cellsForMoney.keySet()) {
            if (rubles.getValue() < smallestBanknotesValue && cellsForMoney.get(rubles) > 0) {
                smallestBanknotesValue = rubles.getValue();
            }
        }
    }

    public int getOutBanknotesOneDenomination(int value, Rubles rubles, int numberOfBanknotes) {
        int i = 0;
        while (value >= rubles.getValue() && numberOfBanknotes > 0) {
            value = value - rubles.getValue();
            numberOfBanknotes--;
            i++;
        }
        cellsForMoney.put(rubles, numberOfBanknotes);
        System.out.printf("Get %d banknotes, value %d rubles", i, rubles.getValue());
        System.out.println(" ");
        return value;
    }

    private void validationCheck(int value) {
        if (value > this.moneyInVault) {
            throw new RuntimeException("Not enough money in the account");
        }
        if ((value % smallestBanknotesValue) != 0) {
            throw new RuntimeException("The amount must be a multiple of " + smallestBanknotesValue);
        }
        if (value <= 0) {
            throw new RuntimeException("Bad Request");
        }
    }

    private List<Integer> createAndGetNumbersOfBanknotesList(List<Rubles> rublesList) {
        List<Integer> numbersOfBanknotesList = new ArrayList<>();
        for (Rubles rubles : rublesList) {
            int numbersOfBanknote = cellsForMoney.get(rubles);
            numbersOfBanknotesList.add(numbersOfBanknote);
        }
        return numbersOfBanknotesList;
    }

    private void printCellsForMoney() {
        System.out.println(cellsForMoney);
    }

}
