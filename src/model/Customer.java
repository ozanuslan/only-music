package model;

import java.util.ArrayList;
import java.util.Stack;

public class Customer extends User {
    private ArrayList<Order> orders;
    private Cart cart = new Cart();
    private Address address;

    public Customer(String username,String name, String surname, String email, int id,int privilegeLevel) {
        super(username,name, surname, email,id,privilegeLevel);
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ArrayList<Order> getOrder() {
        return orders;
    }

    public void setOrder(ArrayList<Order> order) {
        this.orders = order;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void addNewOrder(Order order){
        orders.add(order);
    }
}
