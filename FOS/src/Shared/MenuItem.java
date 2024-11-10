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

public class MenuItem implements Serializable {
    private String foodName;
    private double foodPrice;
    private String subCategory;

    public MenuItem(String foodName, double foodPrice, String subCategory) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.subCategory = subCategory;
    }

    // Getters
    public String getFoodName() {
        return foodName;
    }

    public double getFoodPrice() {
        return foodPrice;
    }

    public String getSubCategory() {
        return subCategory;
    }
}