package ru.otus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Ioc {

    public MyLoggingInterface createMyClass() {
        InvocationHandler handler = new MyInvocationHandler(new MyLoggingImpl());
        return (MyLoggingInterface) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{MyLoggingInterface.class}, handler);
    }
}
