package helper;

import model.Item;
import model.Order;
import model.User;

import java.util.ArrayList;
import java.util.Stack;

public class Storage {
    private static Storage storage; // Singleton object
    private User activeUser; // Global active user in the system
    private ArrayList<User> userList; // User list of the system
    private ArrayList<Item> itemList; // Item list of the system
    private Item lastClickedItem; // Stores the last clicked item block in the system for GUI operations
    private Order lastClidkedOrder; // Stores the last clicked order block in the system for GUI operations
    private Stack<String> lastLocation = new Stack<>(); // Stories last visited pages on a stack to go back for later

    public static Storage getStorage(){
        return storage == null ? storage = new Storage() : storage;
    }

    public static void setStorage(Storage storage) {
        Storage.storage = storage;
    }

    public User getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }

    public ArrayList<Item> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<Item> itemList) {
        this.itemList = itemList;
    }

    public void addLastLocation(String location) {
        lastLocation.add(location);
    }

    public String popLastLocation() {
       return lastLocation.pop();
    }

    public void setLastLocation(Stack<String> lastLocation) { this.lastLocation = lastLocation; }

    public Item getLastClickedItem() {
        return lastClickedItem;
    }

    public void setLastClickedItem(Item lastClickedItem) {
        this.lastClickedItem = lastClickedItem;
    }

    public Order getLastClidkedOrder() {
        return lastClidkedOrder;
    }

    public void setLastClidkedOrder(Order lastClidkedOrder) {
        this.lastClidkedOrder = lastClidkedOrder;
    }
}
