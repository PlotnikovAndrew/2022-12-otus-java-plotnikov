package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.TypeVariable;
import java.util.*;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();
    private final NavigableMap<Integer, List<Method>> methodByOrder = new TreeMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        checkConfigClass(configClass);
        for (Method method : configClass.getMethods()) {
            if (method.isAnnotationPresent(AppComponent.class)) {

                String methodName = method.getAnnotation(AppComponent.class).name();
                int order = method.getAnnotation(AppComponent.class).order();

                methodByOrder.computeIfAbsent(order, v -> new ArrayList<>()).add(method);
                appComponentsByName.put(methodName, null);
            }
        }

        for (int i = 0; i <= methodByOrder.lastKey(); i++) {
            for (Method method : methodByOrder.get(i)) {
                Class[] parameterTypes = method.getParameterTypes();
//                Class[] parameterClass = new Class[objects.length];
                Object[] parametersObjects = new Object[parameterTypes.length];
                for (Object o : parameterTypes) {
                    method.getClass().getConstructor();
                }
            }

        }

        for (int i = 0; i <= methodByOrder.lastKey(); i++) {
            appComponents.addAll(methodByOrder.get(i));
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass){
        return null;
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return null;
    }
}
