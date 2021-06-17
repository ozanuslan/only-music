package controller;

import helper.ContentFilter;
import helper.Helper;
import helper.SceneBuilder;
import helper.Storage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import model.Item;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {

    @FXML
    private Button cartButton;

    @FXML
    private TextField searchBarInput;

    @FXML
    private Button userButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Label errorLabel;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane gridPane;

    Storage storage = Storage.getStorage();
    SceneBuilder sceneBuilder = SceneBuilder.getSceneBuilder();
    ArrayList<Item> filteredList = storage.getItemList();

    @FXML
    void cartButtonAction(ActionEvent event) throws Exception {
        sceneBuilder.closeScene(cartButton);
        storage.addLastLocation("main");
        sceneBuilder.createScene("cartPage");
    }

    @FXML
    void logoutButtonAction(ActionEvent event) throws Exception {
        Helper.logOut(logoutButton);
    }

    @FXML
    void userButtonAction(ActionEvent event) throws Exception {
        sceneBuilder.closeScene(userButton);
        storage.addLastLocation("main");
        sceneBuilder.createScene("customerProfilePage");
    }

    @FXML
    void ampMenuButtonAction(ActionEvent event) {
        filterByCategory(ContentFilter.ItemFilterType.AMP);
    }

    @FXML
    void guitarMenuButtonAction(ActionEvent event) {
        filterByCategory(ContentFilter.ItemFilterType.GUITAR);
    }

    @FXML
    void percussionMenuButtonAction(ActionEvent event) {
        filterByCategory(ContentFilter.ItemFilterType.PERCUSSION_INSTRUMENT);
    }

    @FXML
    void pianoMenuButtonAction(ActionEvent event) {
        filterByCategory(ContentFilter.ItemFilterType.PIANO);
    }

    @FXML
    void searchBarTyped(KeyEvent event) {
        filterByText(searchBarInput.getText());
    }

    @FXML
    void stringMenuButtonAction(ActionEvent event) {
        filterByCategory(ContentFilter.ItemFilterType.STRING_INSTRUMENT);
    }

    @FXML
    void windMenuButtonAction(ActionEvent event) {
        filterByCategory(ContentFilter.ItemFilterType.WIND_INSTRUMENT);
    }

    @FXML
    void allMenuButtonAction(ActionEvent event) {
        filteredList = storage.getItemList();
        update(filteredList);
    }

    void filterByCategory(ContentFilter.ItemFilterType filterType){
        filteredList = (ArrayList<Item>) ContentFilter.getFilteredItemList(storage.getItemList(), filterType);
        update(filteredList);
    }
    void filterByText(String text){
        update((ArrayList<Item>) ContentFilter.getFilteredItemList(filteredList, text));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int column = 0;
        int row = 1;
        try {
            int size = filteredList.size();
            for(int i = 0;i<size;i++){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/GUI/itemBlock.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setData(filteredList.get(i));

                if(column == 4){
                    column = 0;
                    row++;
                }

                gridPane.add(anchorPane, column++, row);
                gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                gridPane.setMaxWidth(Region.USE_PREF_SIZE);

                gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
                gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridPane.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(20));
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void update(ArrayList<Item> itemList){
        deleteGrid();
        int column = 0;
        int row = 1;
        try {
            int size = itemList.size();
            for(int i = 0;i<size;i++){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/GUI/itemBlock.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setData(itemList.get(i));

                if(column == 4){
                    column = 0;
                    row++;
                }

                gridPane.add(anchorPane, column++, row);
                gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                gridPane.setMaxWidth(Region.USE_PREF_SIZE);

                gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
                gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridPane.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(20));
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    void deleteGrid(){
        gridPane.getChildren().removeIf(node -> true);
    }
}
