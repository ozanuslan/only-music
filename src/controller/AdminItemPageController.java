package controller;

import helper.ContentFilter;
import helper.DatabaseConnection;
import helper.Helper;
import helper.Storage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import model.Item;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminItemPageController implements Initializable {

    @FXML
    private Button backwardButton;

    @FXML
    private Button logoutButton;

    @FXML
    private ImageView itemImage;

    @FXML
    private ChoiceBox<String> addItemType;

    @FXML
    private TextField addItemNameInput;

    @FXML
    private TextField addPriceInput;

    @FXML
    private TextField addStockInput;

    @FXML
    private Label errorLabel;

    @FXML
    private Label imagePathLabel;

    @FXML
    private TextField imagePathInput;

    @FXML
    private Label attribute1Label;

    @FXML
    private TextField addAttribute1;

    @FXML
    private Label attribute2Label;

    @FXML
    private TextField addAttribute2;

    @FXML
    private Label attribute3Label;

    @FXML
    private TextField addAttribute3;

    @FXML
    private Label attribute4Label;

    @FXML
    private TextField addAttribute4;

    @FXML
    private Button addItemButton;

    @FXML
    private Button changePhotoButton;

    @FXML
    private TextField searchBox;

    @FXML
    private TableView<Item> tableView;

    @FXML
    private TableColumn<Item, Integer> itemId;

    @FXML
    private TableColumn<Item, String> itemName;

    @FXML
    private TableColumn<Item, Double> itemPrice;

    @FXML
    private TableColumn<Item, Integer> itemStock;

    DatabaseConnection db = new DatabaseConnection();
    Connection connectDB = db.getConnection();
    Storage storage = Storage.getStorage();
    private ObservableList<Item> itemList = FXCollections.observableArrayList(storage.getItemList());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addItemType.getItems().add("Classical Guitar");
        addItemType.getItems().add("Electric Guitar");
        addItemType.getItems().add("Piano");
        addItemType.getItems().add("Clarinet");
        addItemType.getItems().add("Trombone");
        addItemType.getItems().add("Trumpet");
        addItemType.getItems().add("Acoustic Drum");
        addItemType.getItems().add("Electronic Drum");
        addItemType.getItems().add("Violin");
        addItemType.getItems().add("Cello");
        addItemType.getItems().add("Amp");
        loadTable(itemList);
    }

    ObservableList getTableList(String filter){
        return FXCollections.observableList(ContentFilter.getFilteredItemList(storage.getItemList(), filter));
    }
    @FXML
    void backwardButtonAction(ActionEvent event) throws Exception {
        Helper.goBackward(backwardButton);
    }

    @FXML
    void logoutButtonAction(ActionEvent event) throws Exception {
        Helper.logOut(logoutButton);
    }

    @FXML
    void addItemButtonAction(ActionEvent event) {
        if(addItemNameInput.getText().isBlank() || addPriceInput.getText().isBlank() || addAttribute1.getText().isBlank() || addAttribute2.getText().isBlank() || addAttribute3.getText().isBlank() || addAttribute4.getText().isBlank() || addStockInput.getText().isBlank() || addItemType.getValue() == null){
            errorLabel.setText("Please fill the empty spaces.");
        }
        else{
            if(inputValidation()){
                String queryText = "INSERT INTO item"+"(price,name,attribute1,attribute2,attribute3,attribute4,stock,imagePath,type) VALUES "+"(?,?,?,?,?,?,?,?,?)";
                try {

                    Statement st=connectDB.createStatement();
                    PreparedStatement ps=connectDB.prepareStatement(queryText);

                    ps.setString(1, addPriceInput.getText());
                    ps.setString(2, addItemNameInput.getText());
                    ps.setString(3, addAttribute1.getText());
                    ps.setString(4, addAttribute2.getText());
                    ps.setString(5, addAttribute3.getText());
                    ps.setString(6, addAttribute4.getText());
                    ps.setString(7, addStockInput.getText());
                    ps.setString(8, imagePathInput.getText());
                    ps.setString(9, addItemType.getValue());

                    ps.executeUpdate();
                    errorLabel.setText("Item has successfully created.");
                    imagePathLabel.setText("");
                }  catch (Exception e3) {
                    e3.printStackTrace();
                    errorLabel.setText("Item name must be different.");
                }

            }
        }
    }


    @FXML
    void typeInputChanged(ActionEvent event) {
        switch (addItemType.getValue()){
            case "Classical Guitar":
                attribute1Label.setText("Body Material");
                attribute2Label.setText("Neck Material");
                attribute3Label.setText("String Number");
                attribute4Label.setText("Body Type");
                break;
            case "Electric Guitar":
                attribute1Label.setText("Body Material");
                attribute2Label.setText("Neck Material");
                attribute3Label.setText("Magnetic Type");
                attribute4Label.setText("Bridge Type");
                break;
            case "Piano":
                attribute1Label.setText("Key Number");
                attribute2Label.setText("Cabinet Type");
                attribute3Label.setText("Width");
                attribute4Label.setText("Height");
                break;
            case "Acoustic Drum":
                attribute1Label.setText("Snare Drum Number");
                attribute2Label.setText("Bass Drum Number");
                attribute3Label.setText("Tom Number");
                attribute4Label.setText("Shell Material");
                break;
            case "Electronic Drum":
                attribute1Label.setText("Snare Drum Number");
                attribute2Label.setText("Bass Drum Number");
                attribute3Label.setText("Tom Number");
                attribute4Label.setText("Sound Number");
                break;
            case "Violin":
                attribute1Label.setText("Front Cover");
                attribute2Label.setText("Back Cover");
                attribute3Label.setText("Height");
                attribute4Label.setText("String Thickness");
                break;
            case "Cello":
                attribute1Label.setText("Front Cover");
                attribute2Label.setText("Back Cover");
                attribute3Label.setText("Height");
                attribute4Label.setText("String Count");
                break;
            case "Amp":
                attribute1Label.setText("Effects");
                attribute2Label.setText("Power");
                attribute3Label.setText("Type");
                attribute4Label.setText("Channel Number");
                break;
            case "Clarinet":
                attribute1Label.setText("Material");
                attribute2Label.setText("Bore Width");
                attribute3Label.setText("Key Number");
                attribute4Label.setText("Model");
                break;
            case "Trombone":
                attribute1Label.setText("Material");
                attribute2Label.setText("Bore Width");
                attribute3Label.setText("Valve Number");
                attribute4Label.setText("Model");
                break;
            case "Trumpet":
                attribute1Label.setText("Material");
                attribute2Label.setText("Bore Width");
                attribute3Label.setText("Piston Valve");
                attribute4Label.setText("Bell Width");
                break;



        }
    }

    @FXML
    void changePhotoButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open item image");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")+"\\src\\images"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.jpg;*.png"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        File file = fileChooser.showOpenDialog((Stage)changePhotoButton.getScene().getWindow());

        if(file != null){
            if(!file.getPath().contains(System.getProperty("user.dir")+"\\src\\images"))
                imagePathLabel.setText("Image must be chosen from images folder.");
            else{
                imagePathLabel.setText("");
                int index = file.getPath().indexOf("\\images");
                String newPath = file.getPath().substring(index, file.getPath().length());
                newPath = newPath.replace('\\','/');

                imagePathInput.setText(newPath);
                itemImage.setImage(new Image(getClass().getResourceAsStream(newPath)));
            }
        }
    }

    boolean inputValidation(){
        System.out.println(addItemType.getValue());
        if(!Helper.isPositiveNumber(addPriceInput.getText())){
            errorLabel.setText("Item price must be bigger than zero");
            return false;
        }
        if(!Helper.isPositiveNumber(addStockInput.getText())){
            errorLabel.setText("Item stock must be bigger than zero");
            return false;
        }
        if(addItemType.getValue() == null){
            errorLabel.setText("Item type must be selected");
            return false;
        }

        switch (addItemType.getValue()) {
            case "Classical Guitar":
                if (!Helper.isPositiveNumber(addAttribute3.getText())) {
                    errorLabel.setText("String Number must be bigger than zero");
                    return false;
                }
                break;
            case "Piano":
                if (!Helper.isPositiveNumber(addAttribute1.getText())) {
                    errorLabel.setText("Key Number must bigger than zero");
                    return false;
                }
                if (!Helper.isPositiveNumber(addAttribute3.getText())){
                    errorLabel.setText("Width must be bigger than zero");
                    return false;
                }
                if(!Helper.isPositiveNumber(addAttribute4.getText())){
                    errorLabel.setText("Height must be bigger than zero");
                    return false;
                }
                break;
            case "Acoustic Drum":
                if (!Helper.isPositiveNumber(addAttribute1.getText())) {
                    errorLabel.setText("Snare Drum Number must bigger than zero");
                    return false;
                }
                if (!Helper.isPositiveNumber(addAttribute2.getText())){
                    errorLabel.setText("Bass Drum Number must be bigger than zero");
                    return false;
                }
                if(!Helper.isPositiveNumber(addAttribute3.getText())){
                    errorLabel.setText("Tom Number must be bigger than zero");
                    return false;
                }
                break;
            case "Electronic Drum":
                if (!Helper.isPositiveNumber(addAttribute1.getText())) {
                    errorLabel.setText("Snare Drum Number must bigger than zero");
                    return false;
                }
                if (!Helper.isPositiveNumber(addAttribute2.getText())){
                    errorLabel.setText("Bass Drum Number must be bigger than zero");
                    return false;
                }
                if(!Helper.isPositiveNumber(addAttribute3.getText())){
                    errorLabel.setText("Tom Number must be bigger than zero");
                    return false;
                }
                break;
            case "Violin":
                if (!Helper.isPositiveNumber(addAttribute3.getText())){
                    errorLabel.setText("Height must be bigger than zero");
                    return false;
                }
                if(!Helper.isPositiveNumber(addAttribute4.getText())){
                    errorLabel.setText("String Thickness must be bigger than zero");
                    return false;
                }
                break;
            case "Cello":
                if (!Helper.isPositiveNumber(addAttribute3.getText())){
                    errorLabel.setText("Height must be bigger than zero");
                    return false;
                }
                if(!Helper.isPositiveNumber(addAttribute4.getText())){
                    errorLabel.setText("String Count must be bigger than zero");
                    return false;
                }
                break;
            case "Amp":
                if(!Helper.isPositiveNumber(addAttribute2.getText())){
                    errorLabel.setText("Power must be bigger than zero");
                    return false;
                }
                if(!Helper.isPositiveNumber(addAttribute4.getText())){
                    errorLabel.setText("Channel Number must be bigger than zero");
                    return false;
                }
                break;
            case "Clarinet":
                if(!Helper.isPositiveNumber(addAttribute2.getText())){
                    errorLabel.setText("Bore Width must be bigger than zero");
                    return false;
                }
                if(!Helper.isPositiveNumber(addAttribute3.getText())){
                    errorLabel.setText("Key Number must be bigger than zero");
                    return false;
                }
                break;
            case "Trombone":
                if(!Helper.isPositiveNumber(addAttribute2.getText())){
                    errorLabel.setText("Bore Width must be bigger than zero");
                    return false;
                }
                if(!Helper.isPositiveNumber(addAttribute3.getText())){
                    errorLabel.setText("Valve Number must be bigger than zero");
                    return false;
                }
                break;
            case "Trumpet":
                if(!Helper.isPositiveNumber(addAttribute2.getText())){
                    errorLabel.setText("Bore Width must be bigger than zero");
                    return false;
                }
                if(!Helper.isPositiveNumber(addAttribute4.getText())){
                    errorLabel.setText("Piston Valve must be bigger than zero");
                    return false;
                }
                break;
        }

        return true;
    }


    //********************ITEM EDIT PAGE************************


    public void loadTable(ObservableList<Item> items){
        tableView.setEditable(true);

        itemName.setCellValueFactory(new PropertyValueFactory<Item,String>("name"));
        itemId.setCellValueFactory(new PropertyValueFactory<Item,Integer>("id"));
        itemPrice.setCellValueFactory(new PropertyValueFactory<Item,Double>("price"));
        itemStock.setCellValueFactory((new PropertyValueFactory<Item, Integer>("stock")));
        itemStock.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        itemStock.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Item, Integer>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Item, Integer> t) {
                        if(t.getNewValue() >= 0){
                            ((Item) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                            ).setStock(t.getNewValue());
                        }
                        else{
                            ((Item) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                            ).setStock(t.getOldValue());
                        }
                    }
                }
        );
        tableView.setItems(items);
    }

    @FXML
    void searchBarTyped(KeyEvent event) {
        loadTable(getTableList(searchBox.getText()));
    }

}
