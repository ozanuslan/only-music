package model;

import java.util.ArrayList;

public class Cart {

    private ArrayList<CartItem> itemList = new ArrayList<>();

    public Cart(ArrayList<CartItem> itemList) {
        this.itemList = itemList;
    }
    public Cart() {


    }

    public ArrayList<CartItem> getItemList() {
        return itemList;
    }

    public void clearCart() {
        itemList = new ArrayList<>();
    }

    public void setItemList(ArrayList<CartItem> itemList) {
        this.itemList = itemList;
    }

    public boolean addItem(Item item) {
        for (CartItem cartitem : itemList) {
            if (cartitem.getItem().getId() == item.getId()) {
                if (item.getStock() != 0 && cartitem.getQuantity() < item.getStock()) {
                    cartitem.increaseQuantity();
                    return true;
                } else {
                    return false;
                }

            }
        }
        if(item.getStock() != 0){
            this.itemList.add(new CartItem(item));
            return true;
        }
        return false;
    }

    public void deleteItem(CartItem item) {
        itemList.remove(item);
    }

    public CartItem getCartItem(int id) {
        for (CartItem item : itemList) {
            if (item.getItem().getId() == id) return item;
        }
        return null;
    }
}
