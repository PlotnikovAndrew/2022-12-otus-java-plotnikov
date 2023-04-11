package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private final EntityClassMetaData<?> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return "SELECT * FROM " + this.entityClassMetaData.getName();
    }

    @Override
    public String getSelectByIdSql() {
        try {
            return String.format("SELECT * FROM %S WHERE %S = ?",
                    this.entityClassMetaData.getName().toLowerCase(),
                    this.entityClassMetaData.getIdField().getName());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getInsertSql() {
        StringBuilder insertSql = new StringBuilder();
        String fieldsName = this.getFieldsNameString();
        String values = this.getValuesPressHoldersString();

        insertSql.append("INSERT INTO ")
                .append(this.entityClassMetaData.getName())
                .append(" ")
                .append(fieldsName)
                .append(" VALUES ")
                .append(values)
                .append(";");

        return insertSql.toString();
    }

    @Override
    public String getUpdateSql() {
        StringBuilder updateSql = new StringBuilder();
        String fieldsNameEqualsPressHolders = this.getFieldsNameEqualsPressHoldersString();

        updateSql.append("UPDATE ")
                .append(this.entityClassMetaData.getName())
                .append(" SET ")
                .append(fieldsNameEqualsPressHolders)
                .append(" WHERE id = ?;");
        return updateSql.toString();
    }

    private String getFieldsNameString() {
        int size = entityClassMetaData.getFieldsWithoutId().size();
        StringBuilder fieldsName = new StringBuilder();
        fieldsName.append("(");
        for (int i = 0; i < size; i++) {
            Field field = entityClassMetaData.getFieldsWithoutId().get(i);
            if (i == size - 1) {
                fieldsName.append(field.getName())
                        .append(")");
            } else {
                fieldsName.append(field.getName())
                        .append(", ");
            }
        }
        return fieldsName.toString();
    }

    private String getValuesPressHoldersString() {
        StringBuilder pressHolders = new StringBuilder();
        int size = entityClassMetaData.getFieldsWithoutId().size();
        pressHolders.append("(");
        for (int i = 0; i < size; i++) {
            if (i == size - 1) {
                pressHolders.append(" ?")
                        .append(")");
            } else {
                pressHolders.append(" ?")
                        .append(", ");
            }
        }
        return pressHolders.toString();
    }

    private String getFieldsNameEqualsPressHoldersString() {
        StringBuilder fieldsEqualsHolders = new StringBuilder();
        List<Field> fieldsList = this.entityClassMetaData.getFieldsWithoutId();
        for (int i = 0; i < fieldsList.size(); i++) {
            Field field = fieldsList.get(i);
            if (i == fieldsList.size() - 1) {
                fieldsEqualsHolders.append(field.getName())
                        .append(" = ? ");
            } else {
                fieldsEqualsHolders.append(field.getName())
                        .append(" = ? ,");
            }
        }
        return fieldsEqualsHolders.toString();
    }

}
