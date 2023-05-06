package ru.otus.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;

/**
 * "Разбирает" объект на составные части
 */
public interface EntityClassMetaData<T> {

    String getName();

    Constructor<T> getConstructor() throws NoSuchMethodException;

    //Поле Id должно определять по наличию аннотации Id
    //Аннотацию @Id надо сделать самостоятельно
    Field getIdField() throws IllegalAccessException;

    List<Field> getAllFields();

    List<Field> getFieldsWithoutId();
}
