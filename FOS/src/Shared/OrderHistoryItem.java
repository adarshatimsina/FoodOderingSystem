/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Shared;

/**
 *
 * @author User
 */
import java.io.Serializable;

public class OrderHistoryItem implements Serializable {
    private int orderId;
    private String foodName;
    private int quantity;
    private double totalPrice;

    // Constructor
    public OrderHistoryItem(int orderId, String foodName, int quantity, double totalPrice) {
        this.orderId = orderId;
        this.foodName = foodName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    // Getters and setters
    public int getOrderId() { return orderId; }
    public String getFoodName() { return foodName; }
    public int getQuantity() { return quantity; }
    public double getTotalPrice() { return totalPrice; }
}
