package model;

import java.util.Stack;

public class Customer extends User {
    private Stack<Order> order;
    private Cart cart = new Cart();
    private Address address;

    public Customer(String username,String name, String surname, String email, int id) {
        super(username,name, surname, email,id);
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Stack<Order> getOrder() {
        return order;
    }

    public void setOrder(Stack<Order> order) {
        this.order = order;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
