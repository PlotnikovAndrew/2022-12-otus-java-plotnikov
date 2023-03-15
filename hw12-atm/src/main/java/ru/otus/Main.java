package ru.otus;


import java.util.HashMap;

public class Main {
    public static void main(String[] args) {


//        q.putMoney(Rubles.RUB_50,-100);
//        System.out.println("====================");

        HashMap<Rubles, Integer> initial = new HashMap<>();
        initial.put(Rubles.RUB_50, 0);
        initial.put(Rubles.RUB_100, 2312);
//        initial.put(Rubles.RUB_200, 1);
        initial.put(Rubles.RUB_500, 0);
        initial.put(Rubles.RUB_1000, 5000);
//        initial.put(Rubles.RUB_2000, 1);
        initial.put(Rubles.RUB_5000, 8);

        VaultATMImpl q = new VaultATMImpl(initial);
        q.getOutMoney(5005000);

        HashMap<Rubles, Integer> dop = new HashMap<>();
        dop.put(Rubles.RUB_50, 145);
        dop.put(Rubles.RUB_100, 0);
        dop.put(Rubles.RUB_200, 250);
        dop.put(Rubles.RUB_500, 12);
        dop.put(Rubles.RUB_1000, 14);
        dop.put(Rubles.RUB_2000, 2);
        dop.put(Rubles.RUB_5000, 8);

        q.putMoney(dop);

//        q.getOutMoney(100);
//
//        q.checkBalance();

//        System.out.println("====================");

//        q.getOutMoney(651);
//        q.logic(54500, Rubles.RUB_5000,initial.get(Rubles.RUB_5000));



    }
}
