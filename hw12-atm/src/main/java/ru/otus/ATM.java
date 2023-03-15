package ru.otus;

import ru.otus.Interface.VaultATM;

import java.util.Map;

public class ATM {

    private final VaultATM vault;

    public ATM(VaultATM vault){
        this.vault = vault;
    }

    public void checkBalance(){
        vault.checkBalance();
    }

    public void putIn(Map<Rubles, Integer> putInMap){
        vault.putMoney(putInMap);
    }

    public void getOut(){

    }
}
