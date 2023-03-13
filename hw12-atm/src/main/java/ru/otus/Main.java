package ru.otus;


import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        VaultATMImpl q = new VaultATMImpl();
        System.out.println("====================");
//        q.putMoney(Rubles.RUB_50,-100);
//        System.out.println("====================");

//        HashMap<Rubles, Integer> u = new HashMap<>();
//        u.put(Rubles.RUB_50, 9000);
//        u.put(Rubles.RUB_100, 1);
//        u.put(Rubles.RUB_200, 1);
//        u.put(Rubles.RUB_500, 1);
//        u.put(Rubles.RUB_1000, 1);
//        u.put(Rubles.RUB_2000, 1);
//        u.put(Rubles.RUB_5000, 8);

//        q.getOutMoney(100);
//
//        q.checkBalance();

//        System.out.println("====================");

//        q.getOutMoney(651);
//        q.logic(54500, Rubles.RUB_5000,u.get(Rubles.RUB_5000));
        q.getOutMoney(400350);
        q.getOutMoney(484650);


    }
}
