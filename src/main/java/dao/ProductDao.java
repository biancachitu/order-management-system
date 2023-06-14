package dao;

import connection.ConnectionFactory;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;

public class ProductDao extends AbstractDao<Product>{
    public ProductDao(Class<Product> type) {
        super(type);
    }

    public String insertQuery(String name, String qty, String price){
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(" product ");
        sb.append("(" + name + "," +  qty + "," + price + ")");
        sb.append(" VALUES ");
        sb.append("(?,?,?,?)");
        return sb.toString();
    }

}
