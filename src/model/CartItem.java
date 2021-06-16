package model;

public class CartItem{
    private Item item;
    private int quantity, row;

    public CartItem(Item item) {
        this(item, 1);
    }

    public CartItem(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void decreaseQuantity() {
        if(quantity > 1)
        quantity--;
    }

    public void increaseQuantity() {
        if(quantity < item.getStock())
        quantity++;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

}