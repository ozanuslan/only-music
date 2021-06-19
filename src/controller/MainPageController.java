package controller;

import helper.*;
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

public class MainPageController implements Initializable, DynamicGridController {

    @FXML
    private Button cartButton;

    @FXML
    private TextField searchBarInput;

    @FXML
    private Button menuAllButton;

    @FXML
    private Button menuGuitarButton;

    @FXML
    private Button menuPianoButton;

    @FXML
    private Button menuPercussionButton;

    @FXML
    private Button menuWindButton;

    @FXML
    private Button menuStringButton;

    @FXML
    private Button menuAmpButton;

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
    GUIHelper guiHelper = GUIHelper.getGuiHelper();

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
         setSelected(menuAmpButton);
    }

    @FXML
    void guitarMenuButtonAction(ActionEvent event) {
        filterByCategory(ContentFilter.ItemFilterType.GUITAR);
        setSelected(menuGuitarButton);
    }

    @FXML
    void percussionMenuButtonAction(ActionEvent event) {
        filterByCategory(ContentFilter.ItemFilterType.PERCUSSION_INSTRUMENT);
        setSelected(menuPercussionButton);
    }

    @FXML
    void pianoMenuButtonAction(ActionEvent event) {
        filterByCategory(ContentFilter.ItemFilterType.PIANO);
        setSelected(menuPianoButton);
    }

    @FXML
    void searchBarTyped(KeyEvent event) {
        filterByText(searchBarInput.getText());
    }

    @FXML
    void stringMenuButtonAction(ActionEvent event) {
        filterByCategory(ContentFilter.ItemFilterType.STRING_INSTRUMENT);
        setSelected(menuStringButton);
    }

    @FXML
    void windMenuButtonAction(ActionEvent event) {
        filterByCategory(ContentFilter.ItemFilterType.WIND_INSTRUMENT);
        setSelected(menuWindButton);
    }

    @FXML
    void allMenuButtonAction(ActionEvent event) {
        filteredList = storage.getItemList();
        update(filteredList);
        setSelected(menuAllButton);
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
        setSelected(menuAllButton);
        guiHelper.showDynamicGrid(filteredList, gridPane,this,"itemBlock",20,20);
    }
    public void update(ArrayList<Item> itemList){
        deleteGrid();
        guiHelper.showDynamicGrid(itemList, gridPane,this,"itemBlock",20,20);
    }
    void deleteGrid(){
        gridPane.getChildren().removeIf(node -> true);
    }

    public void addToCartFailed(){
        errorLabel.setText("Cannot be added to your cart, not enough stock");
        errorLabel.getStyleClass().clear();
        errorLabel.getStyleClass().add("text-item-price");
        errorLabel.getStyleClass().add("text-color-error");
    }
    public void addToCartSuccesful(){
        errorLabel.setText("Successfully added to your cart");
        errorLabel.getStyleClass().clear();
        errorLabel.getStyleClass().add("text-item-price");
        errorLabel.getStyleClass().add("text-color-success");
    }

    public void setSelected(Button button){
        setOthersInitial();
        button.getStyleClass().clear();
        button.getStyleClass().add("radius-30");
        button.getStyleClass().add("shadow-high");
        button.getStyleClass().add("icon-color-blue");
    }

    public void setOthersInitial(){
        setInitial(menuAllButton);
        setInitial(menuGuitarButton);
        setInitial(menuPianoButton);
        setInitial(menuPercussionButton);
        setInitial(menuStringButton);
        setInitial(menuWindButton);
        setInitial(menuAmpButton);
    }
    public void setInitial(Button button){
        button.getStyleClass().clear();
        button.getStyleClass().add("radius-30");
        button.getStyleClass().add("shadow");
        button.getStyleClass().add("background-color-dark-blue");
    }


}
