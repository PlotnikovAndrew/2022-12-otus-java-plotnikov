package ru.otus;

import ru.otus.Interface.Vault;

import java.util.*;

public class ATM implements ru.otus.Interface.ATM {

    private final Vault vault;

    public ATM(Vault vault) {
        this.vault = vault;
    }

    @Override
    public void checkBalance() {
        System.out.println("Balance now: " + this.vault.getMoneyInVault());
    }

    @Override
    public void putMoney(Map<Rubles, Integer> putMoneyMap) {
        System.out.print("before putMoney: ");
        this.printCellsForMoney();
        for (Rubles rubles : putMoneyMap.keySet()) {
            Integer numberOfBanknotes = this.vault.getCellsForMoney().get(rubles);
            Integer addBanknotes = putMoneyMap.get(rubles);
            if (addBanknotes < 0) {
                throw new RuntimeException("Negative number of banknotes");
            }
            this.vault.getCellsForMoney().put(rubles, numberOfBanknotes + addBanknotes);
        }
        this.vault.countBalance();
        this.vault.searchSmallestBanknoteInVault();
        System.out.print("after putMoney: ");
        this.printCellsForMoney();
    }

    @Override
    public void getOutMoney(int value) {

        this.validationCheck(value);

        System.out.print("before getOutMoney: ");
        this.printCellsForMoney();

        NavigableSet<Rubles> rublesSet = this.vault.getCellsForMoney().descendingKeySet();
        List<Rubles> rublesList = new ArrayList<>(rublesSet);
        List<Integer> numbersOfBanknotesList = this.createAndGetNumbersOfBanknotesList(rublesList);

        int val = value;
        for (int i = 0; i < numbersOfBanknotesList.size(); i++) {
            if (val < rublesList.get(i).getValue() || numbersOfBanknotesList.get(i) == 0) {
                continue;
            }
            val = getOutBanknotesOneDenomination(val, rublesList.get(i), numbersOfBanknotesList.get(i));
        }
        this.vault.countBalance();
        this.vault.searchSmallestBanknoteInVault();
        this.checkBalance();

        System.out.print("after getOutMoney: ");
        this.printCellsForMoney();
    }

    private int getOutBanknotesOneDenomination(int value, Rubles rubles, int numberOfBanknotes) {
        int i = 0;
        while (value >= rubles.getValue() && numberOfBanknotes > 0) {
            value = value - rubles.getValue();
            numberOfBanknotes--;
            i++;
        }
        this.vault.getCellsForMoney().put(rubles, numberOfBanknotes);
        System.out.printf("Get %d banknotes, value %d rubles", i, rubles.getValue());
        System.out.println(" ");
        return value;
    }

    private void validationCheck(int value) {
        if (value > this.vault.getMoneyInVault()) {
            throw new RuntimeException("Not enough money in ATM");
        }
        if ((value % this.vault.getSmallestBanknotesValue()) != 0) {
            throw new RuntimeException("The amount must be a multiple of " + this.vault.getSmallestBanknotesValue());
        }
        if (value <= 0) {
            throw new RuntimeException("Bad Request");
        }
    }

    private List<Integer> createAndGetNumbersOfBanknotesList(List<Rubles> rublesList) {
        List<Integer> numbersOfBanknotesList = new ArrayList<>();
        for (Rubles rubles : rublesList) {
            int numbersOfBanknote = this.vault.getCellsForMoney().get(rubles);
            numbersOfBanknotesList.add(numbersOfBanknote);
        }
        return numbersOfBanknotesList;
    }

    private void printCellsForMoney() {
        System.out.println(this.vault.getCellsForMoney());
    }
}
