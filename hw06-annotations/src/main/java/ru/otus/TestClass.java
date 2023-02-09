package ru.otus;

import ru.otus.annotations.*;

public class TestClass {
    @Before
    public void firstBefore() {
        System.out.println("First before");
    }

    @Before
    public void secondBefore() {
        System.out.println("Second before");
    }

    @Test
    public void firstTest() {
        System.out.println("First test");
//        throw new RuntimeException("RuntimeException firstTest");
    }

    @Test
    public void secondTest() {
        System.out.println("Second test");
//        throw new RuntimeException("RuntimeException secondTest");
    }

    @Test
    public void thirdTest() {
        System.out.println("Third test");
//        throw new RuntimeException("RuntimeException thirdTest");
    }

    @After
    public void firstAfter() {
        System.out.println("First after");
    }

    @After
    public void secondAfter() {
        System.out.println("Second after");
    }
}
