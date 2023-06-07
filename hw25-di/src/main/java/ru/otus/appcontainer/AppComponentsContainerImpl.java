package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.Method;
import java.util.*;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) throws Exception {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) throws Exception {
        checkConfigClass(configClass);

        var configClassNewInstance = configClass.getDeclaredConstructor().newInstance();
        List<Method> methodList = Arrays.stream(configClass.getMethods())
                .filter(method -> method.isAnnotationPresent(AppComponent.class))
                .sorted(Comparator.comparing(method -> method.getAnnotation(AppComponent.class).order()))
                .toList();

        for (Method method : methodList) {
            String componentName = method.getAnnotation(AppComponent.class).name();
            if (appComponentsByName.containsKey(componentName)) {
                throw new RuntimeException("The name already exists in the context");
            }
            Object[] args = new Object[method.getParameters().length];
            for (int i = 0; i < args.length; i++) {
                args[i] = getAppComponent(method.getParameters()[i].getType());
            }
            Object component = method.invoke(configClassNewInstance, args);
            appComponents.add(component);
            appComponentsByName.put(componentName, component);
        }
    }


    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) throws Exception {
        List<Object> objectList = new ArrayList<>();
        for (Object component : appComponents) {
            if (componentClass.isAssignableFrom(component.getClass())) {
                objectList.add(component);
            }
        }
        if (objectList.size() > 1) {
            throw new RuntimeException(String.format("%s more component instances found", componentClass.getName()));
        }
        if (objectList.size() == 0) {
            throw new RuntimeException(String.format("%s component instance not found", componentClass.getName()));
        }
        return (C) objectList.get(0);
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        if (!appComponentsByName.containsKey(componentName)) {
            throw new RuntimeException("No given name in context");
        }
        return (C) appComponentsByName.get(componentName);
    }
}