package ru.otus.unit;

import ru.otus.ATMImpl;
import ru.otus.Interface.ATM;
import ru.otus.Interface.Vault;
import ru.otus.Rubles;

import java.util.Map;
import java.util.NavigableMap;

import static ru.otus.assertions.Assertions.assertCheckBalance;
import static ru.otus.assertions.Assertions.assertCorrectGetPutMoney;

public class ATMTests {
    public void correctCheckBalance(Vault vault, Integer correctBalance) {
        String scenario = "Тест на корректный вывод баланса";

        ATM atm = new ATMImpl(vault);
        Integer expectedBalance = atm.checkBalance();
        try {
            assertCheckBalance(expectedBalance, correctBalance);
            System.out.printf("%s: passed", scenario);
            System.out.println();
        } catch (AssertionError ex) {
            System.err.printf("%s: fails with message %s", scenario, ex.getMessage());
            System.out.println();
        }
    }

    public void correctGetOutMoney(Vault vault, NavigableMap<Rubles, Integer> correctReleasedBanknotes, Integer value){
        String scenario = "Тест на корректный вывод средств";

        ATM atm = new ATMImpl(vault);
        NavigableMap<Rubles, Integer> expectedReleasedBanknotes;
        expectedReleasedBanknotes = atm.getOutMoney(value);

        try {
            assertCorrectGetPutMoney(expectedReleasedBanknotes, correctReleasedBanknotes);
            System.out.printf("%s: passed", scenario);
            System.out.println();
        } catch (AssertionError ex) {
            System.err.printf("%s: fails with message %s", scenario, ex.getMessage());
            System.out.println();
        }
    }

    public void correctPutMoney(Vault vault, NavigableMap<Rubles, Integer> correctReleasedBanknotes, Map<Rubles, Integer> newBanknotes){
        String scenario = "Тест на корректное пополнение средств";

        ATM atm = new ATMImpl(vault);
        atm.putMoney(newBanknotes);
        NavigableMap<Rubles, Integer> expectedReleasedBanknotes;
        expectedReleasedBanknotes = vault.getCellsForMoney();


        try {
            assertCorrectGetPutMoney(expectedReleasedBanknotes, correctReleasedBanknotes);
            System.out.printf("%s: passed", scenario);
            System.out.println();
        } catch (AssertionError ex) {
            System.err.printf("%s: fails with message %s", scenario, ex.getMessage());
            System.out.println();
        }
    }
}
