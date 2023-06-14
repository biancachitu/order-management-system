package presentation;

import connection.ConnectionFactory;
import dao.AbstractDao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static connection.ConnectionFactory.getConnection;

public class Reflection<T> extends DefaultTableModel {
    private Class<?> type;
    String[] columns;
    DefaultTableModel model;
    AbstractDao<T> abstractDao;
    public JTable table;

    @SuppressWarnings("unchecked")
    public Reflection(Class<T> type){
        this.type = type;
        abstractDao = new AbstractDao<>(type);
        table = new JTable();
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
        super.setValueAt(value, row, column);
        if (row >= 0 && column >= 0) {
            String tableName = type.getSimpleName().toLowerCase();
            String columnName = model.getColumnName(column);
            Object objectId = model.getValueAt(row, 0); // Assuming the first column is the ID column
            try {
                updateDatabase(columnName, value, objectId);
                System.out.println("Database updated.");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public  JTable populateTable(List<T> data) {
        for(T elem : data){
            abstractDao.insertEntry(elem);
        }

        putInTable();
        return table;
    }

    public  void insertOneSingle(T elem) {
        abstractDao.insertEntry(elem);
    }

    public JTable putInTable(){

        List<T> data = abstractDao.findAll();
        if(data.isEmpty()){
            System.out.println("Make sure the list is not empty");
            return null;
        }
        Class<?> tclass = data.get(0).getClass();
        Field[] fields = tclass.getDeclaredFields();
        columns = new String[fields.length];

        for(int i = 0; i < fields.length; i++){
            columns[i] = fields[i].getName();
        }
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for(T object : data){
            Object[] rowData = new Object[fields.length];
            for(int i = 0; i < fields.length; i++){
                try{
                    fields[i].setAccessible(true);
                    rowData[i] = fields[i].get(object);
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
            model.addRow(rowData);
        }

        table.setModel(model);
        table.setFillsViewportHeight(true);
        return table;
    }

    private void updateDatabase(String columnName, Object value, Object objectId) throws SQLException {
        String tableName = type.getSimpleName().toLowerCase();
        String updateQuery = "UPDATE " + tableName + " SET " + columnName + " = ? WHERE id = ?";
        Connection conn = getConnection();
        PreparedStatement statement = conn.prepareStatement(updateQuery);

        try {
            statement.setObject(1, value);
            statement.setObject(2, objectId);

            System.out.println("SQL Query: " + statement);

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Data updated in the database successfully!");

                int rowIndex = findRowIndex(objectId);
                int columnIndex = model.findColumn(columnName);
                model.setValueAt(value, rowIndex, columnIndex);
            } else {
                System.out.println("No rows were updated in the database.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(conn);
        }
    }

    private int findRowIndex(Object objectId) {
        for (int i = 0; i < model.getRowCount(); i++) {
            Object value = model.getValueAt(i, 0); //id
            if (value.equals(objectId)) {
                return i;
            }
        }
        return -1;
    }
}
