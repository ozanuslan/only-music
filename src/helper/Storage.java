package helper;

import model.Item;
import model.User;

import java.util.ArrayList;
import java.util.Stack;

public class Storage {
    private static Storage storage;
    private User activeUser;
    private ArrayList<User> userList;
    private ArrayList<Item> itemList;
    private Stack<String> lastLocation = new Stack<>();

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

    public void setLastLocation(Stack<String> lastLocation) {
        this.lastLocation = lastLocation;
    }
}
