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

public class ItemController {

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

    @FXML
    void itemBlockAction(MouseEvent event) throws Exception {
        sceneBuilder.closeScene(addCardButton);
        storage.addLastLocation("main");
        storage.setLastClickedItem(item);
        sceneBuilder.createScene("itemPage");
    }

    @FXML
    void addCardButtonAction(ActionEvent event) {
        if (userCart.addItem(item)) {
            mainPageController.addToCartSuccesful();
        } else {
            mainPageController.addToCartFailed();
        }
    }

    private Item item;

    public void setData(Item item) {
        this.item = item;
        itemNameLabel.setText(item.getName());
        itemPriceLabel.setText("$" + String.valueOf(item.getPrice()));
        Image image = new Image(getClass().getResourceAsStream(((Instrument) item).getImagePath()));
        itemImage.setImage(image);
    }

    public void setMainPageController(MainPageController mainPageController) {
        this.mainPageController = mainPageController;
    }

}
