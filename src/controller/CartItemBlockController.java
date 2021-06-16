package controller;

import helper.SceneBuilder;
import helper.Storage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Cart;
import model.CartItem;
import model.Customer;

public class CartItemBlockController{

    @FXML
    private ImageView itemImage;

    @FXML
    private Label itemNameLabel;

    @FXML
    private Label quantityLabel;

    @FXML
    private Button increaseQuantityButton;

    @FXML
    private Button decreaseQuantityButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Label itemStockLabel;

    @FXML
    private Label itemPriceLabel;

    private CartItem cartitem;

    Storage storage = Storage.getStorage();
    SceneBuilder sceneBuilder = SceneBuilder.getSceneBuilder();
    CartPageController cartPageController;

    void setCartItem(CartItem cartItem){
        this.cartitem = cartItem;
        itemNameLabel.setText(cartitem.getItem().getName());
        itemPriceLabel.setText("$"+String.valueOf(cartitem.getItem().getPrice()));
        itemStockLabel.setText(String.valueOf(cartitem.getItem().getStock()));
        quantityLabel.setText(String.valueOf(cartitem.getQuantity()));
        Image image = new Image(getClass().getResourceAsStream((cartitem.getItem()).getImagePath()));
        itemImage.setImage(image);
    }

    void setCartPageController(CartPageController cartPageController){
        this.cartPageController = cartPageController;
    }

    @FXML
    void cancelButtonAction(ActionEvent event) {
        Customer customer = (Customer)storage.getActiveUser();
        Cart cart = customer.getCart();
        cartPageController.reArrangeGrid(cartitem.getRow());
        cart.deleteItem(cartitem);
        cartPageController.setTotalCartPrice();
    }

    @FXML
    void decreaseQuantityButtonAction(ActionEvent event) {
        cartitem.decreaseQuantity();
        quantityLabel.setText(Integer.toString(cartitem.getQuantity()));
        cartPageController.setTotalCartPrice();
    }

    @FXML
    void increaseQuantityButtonAction(ActionEvent event) {
        cartitem.increaseQuantity();
        quantityLabel.setText(Integer.toString(cartitem.getQuantity()));
        cartPageController.setTotalCartPrice();
    }

}