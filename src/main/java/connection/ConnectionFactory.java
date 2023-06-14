package connection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionFactory {
    private static final Logger LOGGER = Logger.getLogger((ConnectionFactory.class.getName()));
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/ordermngdb";
    private static final String USER = "root";
    private static final String PASS = "root";

    private static ConnectionFactory singleInstance = new ConnectionFactory();

    private ConnectionFactory(){
        try{
            Class.forName(DRIVER);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    private Connection createConnection(){
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(DBURL, USER, PASS);
            LOGGER.log(Level.INFO, "Database connection established.");
        } catch(SQLException ex){
            LOGGER.log(Level.SEVERE, "Oops! An error has occurred while trying to connect to the database.");
            ex.printStackTrace();
        }
        return conn;
    }
    public static Connection getConnection(){
        return singleInstance.createConnection();
    }
    public static void close(Connection connection){
        if(connection != null){
            try{
                connection.close();
                LOGGER.log(Level.INFO, "Connection closed.");
            }catch(SQLException ex){
                LOGGER.log(Level.SEVERE, "Oops! An error has occurred while trying to close the connection.", ex);
            }
        }
    }
    public static void close(Statement statement){
        if(statement != null){
            try{
                statement.close();
                LOGGER.log(Level.INFO, "Statement closed.");
            }catch(SQLException ex){
                LOGGER.log(Level.SEVERE, "Oops! An error has occurred while trying to close the statement.", ex);
            }
        }
    }
    public static void close(ResultSet resultSet){
        if(resultSet != null){
            try{
                resultSet.close();
                LOGGER.log(Level.INFO, "ResultSet closed.");
            }catch(SQLException ex){
                LOGGER.log(Level.SEVERE, "Oops! An error has occurred while trying to close the result set.", ex);
            }
        }
    }
}
