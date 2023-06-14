package dao;

import connection.ConnectionFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AbstractDao<T> {
    protected static final Logger LOGGER = Logger. getLogger(AbstractDao.class.getName());
    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDao(Class<T> type){
        this.type = type;
    }

    public String truncateQuery(String tableName){
        StringBuilder sb = new StringBuilder();
        sb.append("TRUNCATE TABLE ").append(tableName);
        return sb.toString();
    }

    private String insertQuery(String[] fields){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO " + type.getSimpleName().toLowerCase() + " (");

        for(int i = 0; i < fields.length; i++){
            stringBuilder.append(fields[i]);
            if(i < fields.length-1)
                 stringBuilder.append(", ");
        }

        stringBuilder.append(") VALUES (");
        for(int i = 0; i < fields.length; i++){
            stringBuilder.append("?");
            if(i < fields.length-1)
                stringBuilder.append(", ");
        }
        stringBuilder.append(");");
        return stringBuilder.toString();
    }

    private String selectAllQuery(){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        return sb.toString();
    }

    private String selectQuery(String field){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append("*");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    public String updateQuery(String field, String fieldCond){
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(type.getSimpleName());
        sb.append(" SET " + field + " = ?");
        sb.append(" WHERE " + fieldCond + " = ?");
        return sb.toString();
    }

    private String deleteQuery(String field){
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " = ? ");
        return sb.toString();
    }

    private List<T> createObjects(ResultSet resultSet){
        List<T> list = new ArrayList<>();
        Constructor<T>[] constructors = (Constructor<T>[]) type.getDeclaredConstructors();
        Constructor<T> constructor = null;
        for (int i = 0; i < constructors.length; i++) {
            Constructor<T> item = constructors[i];
            constructor = item;
            if (constructor.getGenericParameterTypes().length == 0) {
                break;
            }
        }
        try {
            while(resultSet.next() && constructor!= null){
                constructor.setAccessible(true);
                T instance = constructor.newInstance();
                for(Field field : type.getDeclaredFields()){
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (SQLException |
                InvocationTargetException |
                InstantiationException |
                IllegalAccessException |
                IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void truncateTable() {
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = ConnectionFactory.getConnection();
            String tableName = type.getSimpleName();
            String query = truncateQuery(tableName);
            statement = conn.prepareStatement(query);
            statement.executeUpdate();
            System.out.println("Table truncated: " + tableName);
        } catch(SQLException ex) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:truncateTable " + ex.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(conn);
        }
    }

    public List<T> findAll() {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = selectAllQuery();
        try {
            conn = ConnectionFactory.getConnection();
            statement = conn.prepareStatement(query);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);
        } catch (SQLException ex) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + ex.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(conn);
        }
        return new ArrayList<>();
    }

    public int insertEntry(T object){
        Class<?> tclass = object.getClass();
        Field[] fields = tclass.getDeclaredFields();
        String[] columns = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            columns[i] = fields[i].getName();
        }
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = insertQuery(columns);
        try {
            conn = ConnectionFactory.getConnection();
            statement = conn.prepareStatement(query);
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                Object value = fields[i].get(object);
                statement.setObject(i + 1, value);
            }
            return statement.executeUpdate();
        } catch(SQLException ex){
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findbyid " + ex.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(conn);
        }
        return 0;
    }

    public T findbyid(int id){
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = selectQuery("id");
        try {
            conn = ConnectionFactory.getConnection();
            statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch(SQLException ex){
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findbyid " + ex.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(conn);
        }
        return null;
    }

    public void updateTable(int id, String columnName, Object value){
        Connection conn = null;
        PreparedStatement statement = null;
        String query = updateQuery(columnName,"id");
        try {
            conn = ConnectionFactory.getConnection();
            statement = conn.prepareStatement(query);
            statement.setObject(1, value);
            statement.setInt(2, id);
            statement.executeUpdate();
            System.out.println("Table updated: " + type.getSimpleName());
        } catch (SQLException ex) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:deleteById " + ex.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(conn);
        }
    }

    public void deleteById(int id) {
            Connection conn = null;
            PreparedStatement statement = null;
            String query = deleteQuery("id");
            try {
                conn = ConnectionFactory.getConnection();
                statement = conn.prepareStatement(query);
                statement.setInt(1, id);
                statement.executeUpdate();
            } catch (SQLException ex) {
                LOGGER.log(Level.WARNING, type.getName() + "DAO:deleteById " + ex.getMessage());
            } finally {
                ConnectionFactory.close(statement);
                ConnectionFactory.close(conn);
            }
    }
}
