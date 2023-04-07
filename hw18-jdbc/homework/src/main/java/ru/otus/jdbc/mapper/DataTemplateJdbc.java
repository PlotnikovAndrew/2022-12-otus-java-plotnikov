package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.executor.DbExecutor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        String findByIdSql = entitySQLMetaData.getSelectByIdSql();
        return dbExecutor.executeSelect(connection, findByIdSql, List.of(id),
                rs -> {
                    try {
                        if(rs.next()){
                            T model = entityClassMetaData.getConstructor().newInstance();

                            List<Field> fieldList = entityClassMetaData.getAllFields();
                            for (Field field : fieldList) {
                                field.setAccessible(true);
                                field.set(model, rs.getObject(field.getName()));
                        }
                    } catch (SQLException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                             InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                })
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> findAll(Connection connection) {
        throw new UnsupportedOperationException();
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

    private List<Object> getParams(List<Field> fields, T client){

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

    private Object[] getParams(t)
}
