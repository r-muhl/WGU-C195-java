/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertmuhlesteinsoftware2.Model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author robmu
 */
public class DBConnection {
    private static final String databaseName = "U05MgN";
    private static final String DB_URL = "jdbc:mysql://52.206.157.109/" + databaseName;
    private static final String userName = "U05MgN";
    private static final String password = "53688542692";
    private static final String driver = "com.mysql.jdbc.Driver";
    static Connection conn;
    
    public static void makeConnection () throws ClassNotFoundException, SQLException, Exception{
        Class.forName(driver);
        conn = DriverManager.getConnection(DB_URL, userName, password);
        System.out.println("Connection successful");
    }
    
    public static void closeConnection() throws ClassNotFoundException, SQLException, Exception {
        conn.close();
        System.out.println("Connection terminated");
    }
    
    public static Connection getConn() {
        return conn;
    }
    
}

