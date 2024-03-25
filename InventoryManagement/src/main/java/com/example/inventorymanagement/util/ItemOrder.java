package com.example.inventorymanagement.util;

import java.io.Serializable;
import java.util.LinkedList;

public class ItemOrder implements Serializable {
    private int orderId;
    private User byUser;
    private String date;
    private LinkedList<OrderDetail> orderDetails = new LinkedList<>();

    /**
     * Default constructor
     */
    public ItemOrder(){
        orderId = -1;
        byUser = null;
        date = null;
    }

    /**
     * Constructor method for the class
     * @param orderId       ID of order, would be set by the server side
     * @param byUser        Object of User that created this item order
     * @param date          Date when the order was created
     * @param orderDetails  LinkedList of object OrderDetails, to store details regarding this order
     */
    public ItemOrder(int orderId, User byUser, String date, LinkedList<OrderDetail> orderDetails) {
        this.orderId = orderId;
        this.byUser = byUser;
        this.date = date;
        this.orderDetails = orderDetails;
    }

    /**
     * Method to add orderDetail to orderDetails
     * @param orderDetail object of OrderDetail
     */
    public void addOrderDetail(OrderDetail orderDetail){
        orderDetails.addLast(orderDetail);
    }

    // Getter and setter below
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public User getByUser() {
        return byUser;
    }

    public void setByUser(User byUser) {
        this.byUser = byUser;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public LinkedList<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(LinkedList<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
