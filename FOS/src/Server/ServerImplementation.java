/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;
import Database.DatabaseConnection;
import Shared.FOSInterface;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 *
 * @author User
 */
public class ServerImplementation extends UnicastRemoteObject implements FOSInterface {
    
    public ServerImplementation() throws RemoteException {
    super(); // Call to the parent constructor of UnicastRemoteObject
}
    
    @Override
    public boolean registerUser(String password, String firstName, String lastName, String governmentID) throws RemoteException{
        String insertQuery = "INSERT INTO customers (Password, FirstName, LastName, GovernmentID) VALUES (?, ?, ?, ?)";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {

        // Set password, first name, last name, and government ID in the prepared statement
        pstmt.setString(1, password);
        pstmt.setString(2, firstName);
        pstmt.setString(3, lastName);
        pstmt.setString(4, governmentID);

        // Execute update
        pstmt.executeUpdate();
        return true; // Registration successful
    } catch (SQLException ex) {
        ex.printStackTrace();
        return false; // Registration failed
    } catch (Exception e) {
        e.printStackTrace();
        return false; // Connection failed
    }
    }
@Override
public boolean userLogin(String governmentID, String password) throws RemoteException {
    String selectQuery = "SELECT * FROM customers WHERE GovernmentID = ? AND Password = ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(selectQuery)) {

        // Set government ID and password in the prepared statement
        pstmt.setString(1, governmentID);
        pstmt.setString(2, password);

        // Execute query and return true if a matching record is found
        return pstmt.executeQuery().next();
        
    } catch (SQLException ex) {
        ex.printStackTrace();
        return false; // SQL error occurred
    } catch (Exception e) {
        e.printStackTrace();
        return false; // Connection error occurred
    }
}    

}