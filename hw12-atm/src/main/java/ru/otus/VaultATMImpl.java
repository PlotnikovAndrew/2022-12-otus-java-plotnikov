package ru.otus;

import ru.otus.Interface.VaultATM;

import java.util.*;

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
        //TODO
//        System.out.println(moneyInVault);
//        System.out.println(cellsForMoney);
//        System.out.println(smallestBanknotesValue);
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

        List<Integer> numbersOfBanknotesList = (ArrayList<Integer>) makeArrayList(cellsForMoney.values());
        List<Rubles> rublesOfValueList = (ArrayList<Rubles>) makeArrayList(cellsForMoney.keySet());
        //TODO
        System.out.println(numbersOfBanknotesList);
        System.out.println(rublesOfValueList);
        int val = value;
        for(int i = numbersOfBanknotesList.size()-1; i >= 0; i--){
            val = logic(val, rublesOfValueList.get(i), numbersOfBanknotesList.get(i));
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

    public int logic(int value, Rubles rubles, int numberOfBanknotes){
//        Collection<Integer> s = cellsForMoney.values();
        while (value >= rubles.getValue() && numberOfBanknotes > 0){
            value = value - rubles.getValue();
            numberOfBanknotes--;
            //TODO
            System.out.println("Осталось выдать: " + value);
            System.out.println("Остальсь банкнот: " + numberOfBanknotes + " " + rubles.getValue());
        }
        return value;
    }

    private List<?> makeArrayList(Collection<?> collection){
        List objectArrayList = new ArrayList<>(collection.size());
        for (var i : collection){
            objectArrayList.add(i);
//            System.out.println(objectArrayList);
        }
        return objectArrayList;
    }
}
