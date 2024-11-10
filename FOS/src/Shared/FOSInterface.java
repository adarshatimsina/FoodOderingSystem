/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Shared;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
/**
 *
 * @author User
 */
public interface FOSInterface extends Remote{
    
    boolean registerUser(String password, String firstName, String lastName, String governmentID) throws RemoteException;
    boolean userLogin(String govermentID, String password) throws RemoteException;
    List<MenuItem> getMenuItems() throws RemoteException;
    int createOrder(double totalPrice, String customerId) throws Exception;
    void addOrderItem(int orderId, int foodId, int quantity) throws Exception;
    int getFoodIDByName(String foodName) throws Exception;
    List<OrderHistoryItem> getOrderHistory(String governmentID) throws RemoteException;
    boolean changePassword(String governmentID, String currentPassword, String newPassword) throws RemoteException;

    
    
    
      
}
