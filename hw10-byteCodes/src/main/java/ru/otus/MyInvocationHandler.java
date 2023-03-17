package ru.otus;

import ru.otus.annotations.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;

public class MyInvocationHandler implements InvocationHandler {

    private final MyLoggingImpl myLoggingClass;
    private final HashMap<Method, Boolean> loggingMethodsMap = new HashMap<>();

    public MyInvocationHandler(MyLoggingImpl myLoggingClass) {
        this.myLoggingClass = myLoggingClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (this.loggingMethodsMap.isEmpty()) {
            this.searchLoggingMethods();
        }
        if (loggingMethodsMap.get(method)) {
            this.logging(method, args);
        }

        return method.invoke(myLoggingClass, args);
    }

    private void logging(Method myMethod, Object[] args) {
        System.out.println("method name: " + myMethod.getName() + " " + Arrays.toString(myMethod.getParameterTypes()));
        for (int i = 0; i < args.length; i++) {
            System.out.println((i) + " param: " + args[i]);
        }
    }

    private void searchLoggingMethods() throws NoSuchMethodException {
        Class<?> clazz = myLoggingClass.getClass();
        for (Method method : clazz.getDeclaredMethods()) {
            Class<?> clazzInterface = MyLoggingInterface.class;
            Method methodThisInterface = clazzInterface.getDeclaredMethod(method.getName(), method.getParameterTypes());
            if (method.isAnnotationPresent(Log.class)) {
                loggingMethodsMap.put(methodThisInterface, true);
            } else {
                loggingMethodsMap.put(methodThisInterface, false);
            }
        }
    }
}
