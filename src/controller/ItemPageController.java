package controller;

import helper.SceneBuilder;
import helper.Storage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.*;

import java.net.URL;
import java.util.ResourceBundle;

public class ItemPageController implements Initializable {

    @FXML
    private Button cartButton;

    @FXML
    private Label stockLabel;

    @FXML
    private Button userButton;

    @FXML
    private Label errorLabel;

    @FXML
    private Button backwardButton;

    @FXML
    private ImageView itemImage;

    @FXML
    private Label itemNameLabel;

    @FXML
    private Label itemPriceLabel;

    @FXML
    private Label itemDetailsLabel;

    Storage storage = Storage.getStorage();
    SceneBuilder sceneBuilder = SceneBuilder.getSceneBuilder();
    private Item item = storage.getLastClickedItem();

    /**
     * Prints page when first opened
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(item.getName());
        System.out.println(String.valueOf(item.getPrice()));
        System.out.println(item.getImagePath());
        System.out.println(item.getDescription());
        itemNameLabel.setText(item.getName());
        itemPriceLabel.setText("$"+String.valueOf(item.getPrice()));
        itemDetailsLabel.setText(item.getDescription());
        if(item.getStock() != 0)
        stockLabel.setText(String.valueOf(item.getStock()));
        else {
            stockLabel.setText("Out of stock");
            stockLabel.getStyleClass().add("text-color-error");
        }

        Image image = new Image(getClass().getResourceAsStream(item.getImagePath()));
        itemImage.setImage(image);
        if(item.getStock() == 0){
            cartButton.getStyleClass().clear();
            cartButton.getStyleClass().add("text-item-name");
            cartButton.getStyleClass().add("icon-color-cancel");
            cartButton.getStyleClass().add("text-color-white");
            cartButton.getStyleClass().add("radius-30");
            cartButton.setText("Out of Stock");
        }
    }

    @FXML
    void backwardButtonAction(ActionEvent event) throws Exception {
        sceneBuilder.closeScene(backwardButton);
        sceneBuilder.createScene(storage.popLastLocation());
    }

    /**
     * Adds item to cart
     * @param event
     */
    @FXML
    void addToCartAction(ActionEvent event) {
        if(item.getStock() != 0) {
            ((Customer) (storage.getActiveUser())).getCart().addItem(item);
            errorLabel.setText("Item added to the cart successfully");
            errorLabel.getStyleClass().clear();
            errorLabel.getStyleClass().add("text-item-name");
            errorLabel.getStyleClass().add("text-color-success");
        }
        else{
            errorLabel.setText("Item could not be added to the cart");
            errorLabel.getStyleClass().clear();
            errorLabel.getStyleClass().add("text-item-name");
            errorLabel.getStyleClass().add("text-color-error");
        }

    }


}
