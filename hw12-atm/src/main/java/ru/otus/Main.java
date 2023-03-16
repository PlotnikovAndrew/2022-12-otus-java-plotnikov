package ru.otus;


import java.util.HashMap;

public class Main {
    public static void main(String[] args) {

        HashMap<Rubles, Integer> initial = new HashMap<>();
        initial.put(Rubles.RUB_50, 0);
        initial.put(Rubles.RUB_100, 2312);
        initial.put(Rubles.RUB_200, 1);
        initial.put(Rubles.RUB_500, 0);
        initial.put(Rubles.RUB_1000, 5000);
        initial.put(Rubles.RUB_2000, 1);
        initial.put(Rubles.RUB_5000, 8);

        VaultImpl vault = new VaultImpl(initial);

        ATM q = new ATM(vault);
        q.getOutMoney(5222250);

        HashMap<Rubles, Integer> dop = new HashMap<>();
        dop.put(Rubles.RUB_50, 145);
        dop.put(Rubles.RUB_100, 0);
        dop.put(Rubles.RUB_200, 250);
        dop.put(Rubles.RUB_500, 12);
        dop.put(Rubles.RUB_1000, 14);
        dop.put(Rubles.RUB_2000, 2);
        dop.put(Rubles.RUB_5000, 8);

        q.putMoney(dop);

    }
}
