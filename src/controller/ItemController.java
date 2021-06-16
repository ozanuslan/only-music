package controller;

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
import javafx.scene.layout.BorderPane;
import model.CartItem;
import model.Customer;
import model.Instrument;
import model.Item;

public class ItemController {

    @FXML
    private Label itemNameLabel;

    @FXML
    private ImageView itemImage;

    @FXML
    private Label itemPriceLabel;

    @FXML
    private Button addCardButton;


    Storage storage = Storage.getStorage();
    SceneBuilder sceneBuilder = SceneBuilder.getSceneBuilder();

    @FXML
    void itemBlockAction(MouseEvent event) throws Exception {
        sceneBuilder.closeScene(addCardButton);
        storage.addLastLocation("main");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/GUI/itemPage.fxml"));
        BorderPane borderPane = fxmlLoader.load();
        ItemPageController itemPageController = fxmlLoader.getController();
        itemPageController.setItem(item);
        sceneBuilder.createScene("itemPage");
    }
    @FXML
    void addCardButtonAction(ActionEvent event) {
        ((Customer)storage.getActiveUser()).getCart().addItem(item);

        System.out.println(((Customer) storage.getActiveUser()).getCart().getItemList().get(0));
    }

    private Item item;

    public void setData(Item item){
        this.item = item;
        itemNameLabel.setText(item.getName());
        itemPriceLabel.setText("$"+String.valueOf(item.getPrice()));
        Image image = new Image(getClass().getResourceAsStream(((Instrument) item).getImagePath()));
        itemImage.setImage(image);

    }

}
