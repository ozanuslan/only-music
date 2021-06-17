package model;

import java.util.ArrayList;
import java.util.Date;

public class Order {
    private int id, status;
    private ArrayList<CartItem> items = new ArrayList<>();
    private Date date;

    public Order(int id, ArrayList<CartItem> items, Date date, int status) {
        this.id = id;
        this.items = items;
        this.date = date;
        this.status = status;
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
}
