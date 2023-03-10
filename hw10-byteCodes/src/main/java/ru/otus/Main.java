package ru.otus;

public class Main {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        MyLoggingInterface testLogging = new Ioc().createMyClass();
        testLogging.calculation(1);
        testLogging.calculation(2, 3);
        testLogging.calculation(4, 5, 6);
        testLogging.calculation(4, 5, "GoodJob");
        long delta = System.currentTimeMillis() - startTime;
        System.out.println("spend msec:" + delta + ", sec:" + (delta / 1000));
    }
}
