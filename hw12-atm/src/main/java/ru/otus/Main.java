package ru.otus;


import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        VaultATMImpl q = new VaultATMImpl();
        System.out.println("====================");
        q.putMoney(Rubles.RUB_50,500);
        System.out.println("====================");

        HashMap<Rubles, Integer> u = new HashMap<>();
        u.put(Rubles.RUB_50, 1);
        u.put(Rubles.RUB_100, 1);
        u.put(Rubles.RUB_200, 1);
        u.put(Rubles.RUB_500, 1);
        u.put(Rubles.RUB_1000, 1);
        u.put(Rubles.RUB_2000, 1);
        u.put(Rubles.RUB_5000, 1);

        q.putMoney(u);

        q.checkBalance();


    }
}
