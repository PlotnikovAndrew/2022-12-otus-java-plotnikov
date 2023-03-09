package ru.otus;

public class Main {
    public static void main(String[] args){

        MyLoggingInterface testLogging = new Ioc().createMyClass();
        testLogging.calculation(1);
        testLogging.calculation(2, 3);
        testLogging.calculation(4, 5, 6);
        testLogging.calculation(4, 5, "GoodJob");
    }
}
