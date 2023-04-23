package ru.otus.mapper;

import ru.otus.annotations.ConstructorJdbc;
import ru.otus.annotations.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData {

    private final Class<T> clazz;
    private Field idField;
    private Constructor<?> constructorJdbc;
    private final List<Field> allFields = new ArrayList<>();
    private final List<Field> fieldsWithoutId = new ArrayList<>();

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String getName() {
        return this.clazz.getSimpleName().toLowerCase();
    }

    @Override
    public Constructor<?> getConstructor() {
        if (constructorJdbc == null){
            Constructor<?>[] constructorArray = clazz.getConstructors();
            for(Constructor<?> constructor : constructorArray){
                if(constructor.isAnnotationPresent(ConstructorJdbc.class)){
                    this.constructorJdbc = constructor;
                }
            }
        }
        return this.constructorJdbc;
    }

    @Override
    public Field getIdField() {
        if (this.idField == null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Id.class)) {
                    this.idField = field;

                }
            }
        }
        return idField;

    }

    @Override
    public List<Field> getAllFields() {
        if (allFields.isEmpty()) {
            Collections.addAll(this.allFields, clazz.getDeclaredFields());
        }
        return this.allFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        if (fieldsWithoutId.isEmpty()) {
            for (Field field : clazz.getDeclaredFields()) {
                if (!field.isAnnotationPresent(Id.class)) {
                    this.fieldsWithoutId.add(field);
                }
            }
        }
        return this.fieldsWithoutId;
    }
}
