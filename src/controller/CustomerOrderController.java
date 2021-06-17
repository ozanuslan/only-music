package controller;

import helper.DatabaseConnection;
import helper.Helper;
import helper.Storage;
import model.CartItem;
import model.Customer;
import model.Order;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;

public class CustomerOrderController {

    Storage storage = Storage.getStorage();

    void getOrder() {
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();

        String orderQuery = "SELECT * FROM order WHERE userId=" + storage.getActiveUser().getId();

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(orderQuery);

            Stack<Order> orders = new Stack<>();
            ArrayList<CartItem> items = new ArrayList<>();
            if (queryResult.next()) {
                int orderId = queryResult.getInt("orderId");
                long lastDate = queryResult.getLong("date");
                int lastStatus = queryResult.getInt("status");
                if (Helper.findItem(storage.getItemList(), queryResult.getInt("itemId")) != null) {
                    items.add(new CartItem(Helper.findItem(storage.getItemList(), queryResult.getInt("itemId")), queryResult.getInt("quantity")));
                } else {
                    System.out.println("Item id: " + queryResult.getInt("itemId") + " couldn't be found in the item list. Therefore item wasn't added to order id: " + queryResult.getInt("orderId"));
                }
                while (queryResult.next()) {
                    if (queryResult.getInt("orderId") != orderId) {
                        orders.add(new Order(orderId, items, new Date(lastDate), lastStatus));
                        items = new ArrayList<>();
                    }
                    orderId = queryResult.getInt("orderId");
                    lastDate = queryResult.getLong("date");
                    lastStatus = queryResult.getInt("status");
                    items.add(new CartItem(Helper.findItem(storage.getItemList(), queryResult.getInt("itemId")), queryResult.getInt("quantity")));
                }
                orders.add(new Order(orderId, items, new Date(lastDate), lastStatus));
            }
            ((Customer) storage.getActiveUser()).setOrder(orders);

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}
