package ru.otus;

import ru.otus.annotations.*;

public class MyLoggingImpl implements MyLoggingInterface {

    @Override
    @Log
    public void calculation(int x) {
        System.out.println(x);
    }

    @Override
//    @Log
    public void calculation(int x, int y) {
        System.out.println(x + " " + y);
    }

    @Override
//    @Log
    public void calculation(int x, int y, int z) {
        System.out.println(x + " " + y + " " + z);
    }

    @Override
//    @Log
    public void calculation(int x, int y, String z) {
        System.out.println(x + " " + y + " " + z);
    }
}
