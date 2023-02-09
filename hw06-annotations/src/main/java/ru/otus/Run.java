package ru.otus;

import ru.otus.annotations.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Run {

    private Class<?> clazz;
    private ArrayList<Method> beforeMethods = new ArrayList<>();
    private ArrayList<Method> testMethods = new ArrayList<>();
    private ArrayList<Method> afterMethods = new ArrayList<>();
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private int runTests = 0;
    private int testPassed = 0;
    private int testFailed = 0;

    public Run(Class<?> clazz) {
        this.clazz = clazz;

        this.splitMethods();

        for (Method method : testMethods) {
            runTests++;
            executeTest(method);
        }

        System.out.printf("Launched %d tests," + ANSI_GREEN + " %d test passed," + ANSI_RED + " %d test failed" + ANSI_RESET, runTests, testPassed, testFailed);
    }

    private void splitMethods() {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(After.class)) {
                afterMethods.add(method);
            } else if (method.isAnnotationPresent(Before.class)) {
                beforeMethods.add(method);
            } else if (method.isAnnotationPresent(Test.class)) {
                testMethods.add(method);
            }
        }
    }

    private void executeTest(Method testMethod) {

        Object obj = this.getNewInstance();

        try {
            for (Method methodBefore : beforeMethods) {
                this.callMethod(methodBefore, obj);
            }

            this.callMethod(testMethod, obj);
            System.out.println(testMethod.getName() + ": " + ANSI_GREEN + "Passed" + ANSI_RESET);
            testPassed++;

        } catch (Exception ex) {
            System.out.println(testMethod.getName() + ": " + ANSI_RED + "Failed");
            System.out.println("Massage of Exception: " + ex.getMessage() + ANSI_RESET);
            testFailed++;
        } finally {
            for (Method methodAfter : afterMethods) {
                this.callMethod(methodAfter, obj);
            }
        }
    }

    private Object getNewInstance() {
        Object obj;
        try {
            Constructor<?> constructor = clazz.getConstructor();
            obj = constructor.newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    private void callMethod(Method method, Object newInstance) {
        try {
            method.invoke(newInstance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
