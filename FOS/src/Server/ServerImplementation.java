/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;
import Database.DatabaseConnection;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import Shared.FOSInterface;
import Shared.MenuItem;
import Shared.OrderHistoryItem;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

  @Override
public List<MenuItem> getMenuItems() throws RemoteException {
    List<MenuItem> menuItems = new ArrayList<>();
    try {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT FoodName, FoodPrice, SubCategory FROM menu";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            String foodName = resultSet.getString("FoodName");
            double foodPrice = resultSet.getDouble("FoodPrice");
            String subCategory = resultSet.getString("SubCategory");
            menuItems.add(new MenuItem(foodName, foodPrice, subCategory));
        }

        resultSet.close();
        statement.close();
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RemoteException("Error fetching menu items.");
    } catch (Exception e) { // Catch generic Exception here
        e.printStackTrace();
        throw new RemoteException("Unexpected error fetching menu items.");
    }
    return menuItems;
}

 @Override
    public int createOrder(double totalPrice, String customerId) throws Exception {
        int orderId = 0;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO Orders (TotalPrice, GovernmentID) VALUES (?, ?) RETURNING OrderID";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setDouble(1, totalPrice);
                stmt.setString(2, customerId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    orderId = rs.getInt("OrderID");
                }
            }
        }
        return orderId;
    }

    @Override
    public void addOrderItem(int orderId, int foodId, int quantity) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO OrderItems (OrderID, FoodID, Quantity) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, orderId);
                stmt.setInt(2, foodId);
                stmt.setInt(3, quantity);
                stmt.executeUpdate();
            }
        }
    }

    @Override
    public int getFoodIDByName(String foodName) throws Exception {
        int foodId = -1; // Default value if not found
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT FoodID FROM menu WHERE FoodName = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, foodName);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    foodId = rs.getInt("FoodID");
                }
            }
        }
        return foodId;
    }
    
    @Override
public List<OrderHistoryItem> getOrderHistory(String governmentID) throws RemoteException {
    List<OrderHistoryItem> orderHistoryList = new ArrayList<>();
    String query = "SELECT o.OrderID, m.FoodName, oi.Quantity, (oi.Quantity * m.FoodPrice) AS TotalPrice " +
                   "FROM Orders o " +
                   "JOIN OrderItems oi ON o.OrderID = oi.OrderID " +
                   "JOIN Menu m ON oi.FoodID = m.FoodID " +
                   "WHERE o.GovernmentID = ? " +
                   "ORDER BY o.OrderID";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setString(1, governmentID);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int orderId = rs.getInt("OrderID");
            String foodName = rs.getString("FoodName");
            int quantity = rs.getInt("Quantity");
            double totalPrice = rs.getDouble("TotalPrice");

            OrderHistoryItem item = new OrderHistoryItem(orderId, foodName, quantity, totalPrice);
            orderHistoryList.add(item);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }   catch (Exception ex) {
            Logger.getLogger(ServerImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
    return orderHistoryList;
}

@Override
public boolean changePassword(String governmentID, String currentPassword, String newPassword) throws RemoteException {
    boolean isPasswordChanged = false;

    try (Connection connection = DatabaseConnection.getConnection()) {
        // Check current password
        String query = "SELECT * FROM Customers WHERE GovernmentID = ? AND Password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, governmentID);
            preparedStatement.setString(2, currentPassword);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Current password matches, proceed to update
                String updateQuery = "UPDATE Customers SET Password = ? WHERE GovernmentID = ?";
                try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                    updateStatement.setString(1, newPassword);
                    updateStatement.setString(2, governmentID);
                    int rowsAffected = updateStatement.executeUpdate();

                    isPasswordChanged = (rowsAffected > 0);
                    System.out.println("Password updated, rows affected: " + rowsAffected);
                }
            } else {
                System.out.println("Current password does not match for Government ID: " + governmentID);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } catch (Exception ex) {
        Logger.getLogger(ServerImplementation.class.getName()).log(Level.SEVERE, null, ex);
    }

    return isPasswordChanged;
}

}

    
