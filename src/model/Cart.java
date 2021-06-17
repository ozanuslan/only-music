package model;

import java.util.ArrayList;

public class Cart {

    private ArrayList<CartItem> itemList = new ArrayList<>();

    public Cart(ArrayList<CartItem> itemList) {
        this.itemList = itemList;
    }
    public Cart(){


    }

    public ArrayList<CartItem> getItemList() {
        return itemList;
    }

    public void clearCart(){
        itemList= new ArrayList<>();
    }

    public void setItemList(ArrayList<CartItem> itemList) {
        this.itemList = itemList;
    }
    public void addItem(Item item){
        boolean flag=true;
        for (CartItem cartitem:itemList) {
            if(cartitem.getItem().getId()== item.getId()) {
                flag=false;
                cartitem.increaseQuantity();
            }
        }
        if(flag) this.itemList.add(new CartItem(item));
    }

    public void deleteItem(CartItem item){
        itemList.remove(item);
    }
}
