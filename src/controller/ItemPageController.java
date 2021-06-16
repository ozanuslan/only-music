package controller;

import helper.SceneBuilder;
import helper.Storage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.*;

public class ItemPageController {

    @FXML
    private Button cartButton;

    @FXML
    private Button userButton;

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
    private Item item;

    @FXML
    void backwardButtonAction(ActionEvent event) throws Exception {
        sceneBuilder.closeScene(backwardButton);
        sceneBuilder.createScene(storage.popLastLocation());
    }

    void setItem(Item item) {
        this.item = item;
        System.out.println(item.getName());
        System.out.println(String.valueOf(item.getPrice()));
        System.out.println(item.getImagePath());
        System.out.println(item.getDescription());
    }

    void setLabels() {
        //itemNameLabel.setText(item.getName());
        itemNameLabel.setText("SAXDXKXC");
        itemPriceLabel.setText(String.valueOf(item.getPrice()));
        itemDetailsLabel.setText(item.getDescription());
        Image image = new Image(getClass().getResourceAsStream(item.getImagePath()));
        itemImage.setImage(image);
    }


}
