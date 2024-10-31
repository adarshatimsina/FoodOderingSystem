/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

/**
 *
 * @author User
 */
import java.sql.Connection;
import java.sql.DriverManager;


public class DatabaseConnection {
    public static final String URL = "jdbc:postgresql://localhost:5432/FoodOrderingSystem";
    public static final String USER = "postgres";
    public static final String PASSWORD = "6969";
    
    public static Connection getConnection() throws Exception {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


}

