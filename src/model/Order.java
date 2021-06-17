package model;

import java.util.ArrayList;
import java.util.Date;

public class Order {
    private int id, status;
    private ArrayList<CartItem> items = new ArrayList<>();
    private Customer customer;
    private Date date;

    public Order(int id, ArrayList<CartItem> items, Date date, int status, Customer customer) {
        this.id = id;
        this.items = items;
        this.date = date;
        this.status = status;
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<CartItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<CartItem> items) {
        this.items = items;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTotalPrice() {
        int sum = 0;
        for (CartItem i : items) {
            sum += i.getItem().getPrice() * i.getQuantity();
        }
        return sum;
    }
}
