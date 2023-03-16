package ru.otus;

import ru.otus.Interface.Vault;

import java.util.*;

public class ATMImpl implements ru.otus.Interface.ATM {

    private final Vault vault;

    public ATMImpl(Vault vault) {
        this.vault = vault;
    }

    @Override
    public int checkBalance() {
        System.out.println("Balance now: " + this.vault.getMoneyInVault());
        return this.vault.getMoneyInVault();
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
    }

    @Override
    public NavigableMap<Rubles, Integer> getOutMoney(int value) {
        this.validationCheck(value);

        NavigableSet<Rubles> rublesSet = this.vault.getCellsForMoney().descendingKeySet();
        List<Rubles> rublesList = new ArrayList<>(rublesSet);
        List<Integer> numbersOfBanknotesList = this.createAndGetNumbersOfBanknotesList(rublesList);

        int val = value;
        NavigableMap<Rubles, Integer> releasedAllBanknotes = new TreeMap<>();
        for (int i = 0; i < numbersOfBanknotesList.size(); i++) {
            if (val < rublesList.get(i).getValue() || numbersOfBanknotesList.get(i) == 0) {
                continue;
            }
            HashMap<Rubles, Integer> releasedOneDenominationBanknote = getOutBanknotesOneDenomination(val, rublesList.get(i), numbersOfBanknotesList.get(i));
            for (Rubles rubles : releasedOneDenominationBanknote.keySet()) {
                releasedAllBanknotes.put(rubles, releasedOneDenominationBanknote.get(rubles));
                val -= rubles.getValue() * releasedOneDenominationBanknote.get(rubles);
            }
        }
        this.vault.countBalance();
        this.vault.searchSmallestBanknoteInVault();

        System.out.println(releasedAllBanknotes);

        return releasedAllBanknotes;
    }

    private HashMap<Rubles, Integer> getOutBanknotesOneDenomination(int value, Rubles rubles, int numberOfBanknotesInVault) {
        int releasedBanknotes = 0;

        while (value >= rubles.getValue() && numberOfBanknotesInVault > 0) {
            value = value - rubles.getValue();
            numberOfBanknotesInVault--;
            releasedBanknotes++;
        }

        this.vault.getCellsForMoney().put(rubles, numberOfBanknotesInVault);

        HashMap<Rubles, Integer> releasedOneDenominationBanknote = new HashMap<>();
        releasedOneDenominationBanknote.put(rubles, releasedBanknotes);
        return releasedOneDenominationBanknote;
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
