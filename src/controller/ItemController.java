package controller;

import helper.Helper;
import helper.SceneBuilder;
import helper.Storage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import model.*;

public class ItemController implements BlockController {

    @FXML
    private Label itemNameLabel;

    @FXML
    private ImageView itemImage;

    @FXML
    private Label itemPriceLabel;

    @FXML
    private Button addCardButton;

    MainPageController mainPageController;
    Storage storage = Storage.getStorage();
    SceneBuilder sceneBuilder = SceneBuilder.getSceneBuilder();
    Customer customer = (Customer) storage.getActiveUser();
    Cart userCart = customer.getCart();

    /**
     * Opens item's page
     * @param event
     * @throws Exception
     */
    @FXML
    void itemBlockAction(MouseEvent event) throws Exception {
        sceneBuilder.closeScene(addCardButton);
        storage.addLastLocation("main");
        storage.setLastClickedItem(item);
        sceneBuilder.createScene("itemPage");
    }

    /**
     * Adds item to cart
     * @param event
     */
    @FXML
    void addCardButtonAction(ActionEvent event) {
        if (userCart.addItem(item)) {
            mainPageController.addToCartSuccesful();
        } else {
            mainPageController.addToCartFailed();
        }
    }

    private Item item;

    /**
     * Sets item and prints it
     * @param data
     * @param <T>
     */
    public <T> void setData(T data) {
        this.item = (Item) data;
        itemNameLabel.setText(item.getName());
        itemPriceLabel.setText("$" + String.valueOf(item.getPrice()));
        Image image = new Image(getClass().getResourceAsStream((item).getImagePath()));
        itemImage.setImage(image);
        if(item.getStock() == 0){
            addCardButton.getStyleClass().clear();
            addCardButton.getStyleClass().add("icon-color-cancel");
            addCardButton.getStyleClass().add("shadow");
            addCardButton.getStyleClass().add("radius-30");
            addCardButton.setText("Out of Stock");
        }
    }

    public void setController(DynamicGridController dynamicGridController) {
        this.mainPageController = (MainPageController) dynamicGridController;
    }

}
