package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.CartItem;

public class OrderDetailPageBlockController implements BlockController {

    @FXML
    private ImageView itemImage;

    @FXML
    private Label itemNameLabel;

    @FXML
    private Label quantityLabel;

    @FXML
    private Label totalPriceItemLabel;

    @FXML
    private Label itemPriceLabel;

    private CartItem cartItem;

    public <T> void setData(T data){
        this.cartItem = (CartItem) data;
        double totalPrice = cartItem.getItem().getPrice() * cartItem.getQuantity();
        itemNameLabel.setText(cartItem.getItem().getName());
        itemPriceLabel.setText("$"+String.valueOf(cartItem.getItem().getPrice()));
        quantityLabel.setText("x"+cartItem.getQuantity());
        totalPriceItemLabel.setText("$"+String.valueOf(totalPrice));
        Image image = new Image(getClass().getResourceAsStream((cartItem.getItem()).getImagePath()));
        itemImage.setImage(image);
    }

    @Override
    public void setController(DynamicGridController dynamicGridController) {

    }
}

