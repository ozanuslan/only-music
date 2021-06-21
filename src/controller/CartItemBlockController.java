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

public class CartItemBlockController implements BlockController{

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

    /**
     * sets the cart item and sets the labels information with cart item attrbiutes.
     * @param data
     * @param <T>
     */
    public <T> void setData(T data){
        this.cartitem = (CartItem) data;
        itemNameLabel.setText(cartitem.getItem().getName());
        itemPriceLabel.setText("$"+String.valueOf(cartitem.getItem().getPrice()));
        itemStockLabel.setText(String.valueOf(cartitem.getItem().getStock()));
        quantityLabel.setText(String.valueOf(cartitem.getQuantity()));
        Image image = new Image(getClass().getResourceAsStream((cartitem.getItem()).getImagePath()));
        itemImage.setImage(image);
    }

    /**
     * sets the controller for controlling the grid pane in the cartPage.
     * @param dynamicGridController
     */
    public void setController(DynamicGridController dynamicGridController){
        this.cartPageController = (CartPageController) dynamicGridController;
    }

    /**
     * Cancel button action deletes the cart item in the customer cart.
     * @param event
     */
    @FXML
    void cancelButtonAction(ActionEvent event) {
        Customer customer = (Customer)storage.getActiveUser();
        Cart cart = customer.getCart();
        cart.deleteItem(cartitem);
        cartPageController.update();
    }

    /**
     * decreases the quantity of the cart item in the customer cart.
     * @param event
     */
    @FXML
    void decreaseQuantityButtonAction(ActionEvent event) {
        cartitem.decreaseQuantity();
        quantityLabel.setText(Integer.toString(cartitem.getQuantity()));
        cartPageController.setTotalCartPrice();
    }

    /**
     * increases the quantity of the cart item in the customer cart.
     * @param event
     */
    @FXML
    void increaseQuantityButtonAction(ActionEvent event) {
        cartitem.increaseQuantity();
        quantityLabel.setText(Integer.toString(cartitem.getQuantity()));
        cartPageController.setTotalCartPrice();
    }

}