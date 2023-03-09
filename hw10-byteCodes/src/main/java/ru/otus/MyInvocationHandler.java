package ru.otus;

import ru.otus.annotations.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class MyInvocationHandler implements InvocationHandler {

    private final MyLoggingImpl myLoggingClass;

    public MyInvocationHandler(MyLoggingImpl myLoggingClass){
        this.myLoggingClass = myLoggingClass;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Method myMethod = this.getMethodImpl(method);

        this.logging(myMethod, args);

        return method.invoke(myLoggingClass, args);
    }

    private void logging(Method myMethod, Object[] args){
        if (myMethod.isAnnotationPresent(Log.class)){
            System.out.println("method name: " + myMethod.getName() + " " + Arrays.toString(myMethod.getParameterTypes()));
            for (int i = 0; i < args.length; i++){
                System.out.println((i) + " param: " + args[i]);
            }
        }
    }

    private Method getMethodImpl(Method method) throws NoSuchMethodException {
        Class<?> clazz = myLoggingClass.getClass();
        return clazz.getDeclaredMethod(method.getName(),method.getParameterTypes());
    }

}
