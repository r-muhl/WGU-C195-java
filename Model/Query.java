/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertmuhlesteinsoftware2.Model;

import java.sql.ResultSet;
import java.sql.Statement;
import static robertmuhlesteinsoftware2.Model.DBConnection.conn;

/**
 *
 * @author robmu
 */
public class Query {
    private static String query;
    private static Statement statement;
    private static ResultSet result;
    
    public static void makeQuery (String q) {
        query = q;
        
        try {
            statement = conn.createStatement();
            
            if (query.toLowerCase().startsWith ("select")) {
                result = statement.executeQuery(query);
            }
            
            if (query.toLowerCase().startsWith ("delete") || query.toLowerCase().startsWith ("insert") || query.toLowerCase().startsWith ("update")) {
                statement.executeUpdate(query);
            }
        }// closes try
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } // closes catch

    } // closes makeQuery
    
    public static ResultSet getResult() {
        return result;
    }
    
    
    
    
} // closes Query
