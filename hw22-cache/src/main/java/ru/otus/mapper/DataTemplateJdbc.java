package ru.otus.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData<T> entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData<T> entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {

        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), resultSet -> {
            try {
                if (resultSet.next()) {
                    return entityClassMetaData.getConstructor().newInstance(getFieldsNameForRs(resultSet));
                }
            } catch (SQLException | IllegalAccessException | NoSuchMethodException | InstantiationException |
                     InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            return null;
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), resultSet -> {
            var clientList = new ArrayList<T>();
            try {
                while (resultSet.next()) {
                    clientList.add(entityClassMetaData.getConstructor().newInstance(getFieldsNameForRs(resultSet)));
                }
                return clientList;
            } catch (SQLException | InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new DataTemplateException(e);
            }
        }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T client) {
        String insertSql = entitySQLMetaData.getInsertSql();
        List<Field> fieldList = entityClassMetaData.getFieldsWithoutId();
        List<Object> params = this.getParams(fieldList, client);

        return dbExecutor.executeStatement(connection, insertSql, params);
    }

    @Override
    public void update(Connection connection, T client) {
        String updateSql = entitySQLMetaData.getUpdateSql();
        List<Field> fieldList = entityClassMetaData.getFieldsWithoutId();
        List<Object> params = this.getParams(fieldList, client);

        dbExecutor.executeStatement(connection, updateSql, params);
    }

    private List<Object> getParams(List<Field> fields, T client) {

        List<Object> params = new ArrayList<>();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                params.add(field.get(client));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return params;
    }

    private Object[] getFieldsNameForRs(ResultSet rs) throws IllegalAccessException, SQLException {
        List<Field> fieldsList = entityClassMetaData.getFieldsWithoutId();
        Object[] objects = new Object[fieldsList.size() + 1];
        objects[0] = rs.getLong(entityClassMetaData.getIdField().getName());
        for (int i = 1; i < fieldsList.size(); i++) {
            objects[i] = rs.getString(fieldsList.get(i).getName());
        }
        return objects;
    }
}
