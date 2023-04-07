package ru.otus.jdbc.mapper;

import ru.otus.annotations.ConstructorJdbc;
import ru.otus.annotations.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData {

    private final Class<T> clazz;
    private Field idField = null;
    private Constructor constructor = null;
    private final List<Field> allFields = new ArrayList<>();
    private final List<Field> fieldsWithoutId = new ArrayList<>();

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String getName() {
        return this.clazz.getName().toLowerCase();
    }

    @Override
    public Constructor getConstructor() {

        Constructor[] constructorArray = clazz.getConstructors();
        for(Constructor constructor : constructorArray){
            if(constructor.isAnnotationPresent(ConstructorJdbc.class)){
                this.constructor = constructor;
            } else {
                throw new RuntimeException("Конструктор не задан");
            }
        }
        return this.constructor;
    }

    @Override
    public Field getIdField() throws IllegalAccessException {
        if (this.idField == null) {
            for (Field field : clazz.getFields()) {
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
            Collections.addAll(allFields, clazz.getFields());
        }
        return this.allFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        if (fieldsWithoutId.isEmpty()) {
            for (Field field : clazz.getFields()) {
                if (!field.isAnnotationPresent(Id.class)) {
                    this.fieldsWithoutId.add(field);
                }
            }
        }
        return this.fieldsWithoutId;
    }
}
