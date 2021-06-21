package controller;

import helper.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import model.Item;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AdminItemPageController implements Initializable,DynamicGridController {

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
    private GridPane gridPaneEditItem;

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
    private Label errorLabelEditItem;

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

    //init db connection for creating new items and editing items into database.
    DatabaseConnection db = new DatabaseConnection();
    //get connection from database with getConnection().
    Connection connectDB = db.getConnection();
    //init storage for getting itemList
    Storage storage = Storage.getStorage();
    //init the observable list for showing items in tableView
    private ObservableList<Item> itemList = FXCollections.observableArrayList(storage.getItemList());
    //init the selection model.
    TableView.TableViewSelectionModel<Item> selectionModel;
    //init the gui helper for showing the itemBlock in edit item page.
    GUIHelper guiHelper = GUIHelper.getGuiHelper();


    /*init the item types when page initialized
      specify the selection model and load the items to the table.
    */
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
        selectionModel = tableView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
        loadTable(itemList);
    }
    //returns the filtered observable item list for table view.
    ObservableList getTableList(String filter) {
        return FXCollections.observableList(ContentFilter.getFilteredItemList(storage.getItemList(), filter));
    }

    //returns the last used page.
    @FXML
    void backwardButtonAction(ActionEvent event) throws Exception {
        Helper.goBackward(backwardButton);
    }

    //performs logout operation
    @FXML
    void logoutButtonAction(ActionEvent event) throws Exception {
        Helper.logOut(logoutButton);
    }
    //if the item edited successfully label shows up.
    void itemEditSuccessful(){
        errorLabelEditItem.getStyleClass().clear();
        errorLabelEditItem.getStyleClass().add("text-item-name");
        errorLabelEditItem.getStyleClass().add("text-color-success");
        errorLabelEditItem.setText("Item stock changed successfully");
    }
    //if the item couldn't edit successfully error message prints in label
    void itemEditError(){
        errorLabelEditItem.getStyleClass().clear();
        errorLabelEditItem.getStyleClass().add("text-item-name");
        errorLabelEditItem.getStyleClass().add("text-color-error");
        errorLabelEditItem.setText("Please enter the stock value properly");
    }

    /*If the all the input fields are valid, inserts item into the database
      error text will be settled according to item added successfully or not.
    */
    @FXML
    void addItemButtonAction(ActionEvent event) throws SQLException {
        if(addItemNameInput.getText().isBlank() || addPriceInput.getText().isBlank() || addAttribute1.getText().isBlank() || addAttribute2.getText().isBlank() || addAttribute3.getText().isBlank() || addAttribute4.getText().isBlank() || addStockInput.getText().isBlank() || addItemType.getValue() == null){
            errorLabel.setText("Please fill the empty spaces.");
        } else {
            if (inputValidation()) {
                String queryText = "INSERT INTO item" + "(price,name,attribute1,attribute2,attribute3,attribute4,stock,imagePath,type) VALUES " + "(?,?,?,?,?,?,?,?,?)";
                try {
                    String imagePath;
                    if(imagePathInput.getText().isBlank()){
                        imagePath="/images/only-music-item.png";
                    }else imagePath = imagePathInput.getText();

                    Statement st = connectDB.createStatement();
                    PreparedStatement ps = connectDB.prepareStatement(queryText);

                    ps.setString(1, addPriceInput.getText());
                    ps.setString(2, addItemNameInput.getText());
                    ps.setString(3, addAttribute1.getText());
                    ps.setString(4, addAttribute2.getText());
                    ps.setString(5, addAttribute3.getText());
                    ps.setString(6, addAttribute4.getText());
                    ps.setString(7, addStockInput.getText());
                    ps.setString(8, imagePath);
                    ps.setString(9, addItemType.getValue());

                    ps.executeUpdate();
                    errorLabel.getStyleClass().clear();
                    errorLabel.getStyleClass().add("text-item-name");
                    errorLabel.getStyleClass().add("text-color-success");
                    errorLabel.setText("Item has successfully created.");
                    imagePathLabel.setText("");
                } catch (Exception e3) {
                    e3.printStackTrace();
                    errorLabel.setText("Item name must be different.");
                }
                itemList = FXCollections.observableArrayList(Helper.getItems());
                loadTable(itemList);
            }
        }
    }


    //when item type selection is changed, the attribute labels will be changed according to item type.
    @FXML
    void typeInputChanged(ActionEvent event) {
        switch (addItemType.getValue()) {
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

    /*
    selects photo for add item page.
    if the image is not in the src/images path, the image path is not valid.
    Takes the image path reorganizes for the source folder.
    User can only choose png and jpg formatted images.
    * */
    @FXML
    void changePhotoButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open item image");
        //set the initial directory to the src/images
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir") + "\\src\\images"));
        //set file type restriction
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.jpg;*.png"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        //open the selection window
        File file = fileChooser.showOpenDialog((Stage) changePhotoButton.getScene().getWindow());

        if (file != null) {
            //if the selected image is not in the src/images then print error.
            if (!file.getPath().contains(System.getProperty("user.dir") + "\\src\\images"))
                imagePathLabel.setText("Image must be chosen from images folder.");
            /*
            if the images is correct then change the '\' char with '/' in the path and
            take only /images/.. part of the path.
             */
            else {
                imagePathLabel.setText("");
                int index = file.getPath().indexOf("\\images");
                String newPath = file.getPath().substring(index, file.getPath().length());
                newPath = newPath.replace('\\', '/');

                imagePathInput.setText(newPath);
                itemImage.setImage(new Image(getClass().getResourceAsStream(newPath)));
            }
        }
    }

    //input validation for the item attributes and item stock,price and type
    boolean inputValidation() {
        //set the error css classes to the label.
        errorLabel.getStyleClass().clear();
        errorLabel.getStyleClass().add("text-item-name");
        errorLabel.getStyleClass().add("text-color-error");
        //if the price is not a positive number then print error.
        if (!Helper.isPositiveNumber(addPriceInput.getText())) {
            errorLabel.setText("Item price must be bigger than zero");
            return false;
        }
        //if the stock is not a positive number then print error.
        if (!Helper.isPositiveNumber(addStockInput.getText())) {
            errorLabel.setText("Item stock must be bigger than zero");
            return false;
        }
        //if the item type is not selected print error.
        if (addItemType.getValue() == null) {
            errorLabel.setText("Item type must be selected");
            return false;
        }

        //if the item type is selected check its attribute inputs are correct.
        switch (addItemType.getValue()) {
            case "Classical Guitar":
                //if classical guitar attribute 3 (string number) is not a positive number then print error.
                if (!Helper.isPositiveNumber(addAttribute3.getText())) {
                    errorLabel.setText("String Number must be bigger than zero");
                    return false;
                }
                break;
            case "Piano":
                //if piano attribute 1 (key number) is not a positive number then print error.
                if (!Helper.isPositiveNumber(addAttribute1.getText())) {
                    errorLabel.setText("Key Number must bigger than zero");
                    return false;
                }
                //if the piano attribute 3 (width) is not a positive number then print error.
                if (!Helper.isPositiveNumber(addAttribute3.getText())) {
                    errorLabel.setText("Width must be bigger than zero");
                    return false;
                }
                //if the attribute 4 (height) is not a positive number then print error.
                if (!Helper.isPositiveNumber(addAttribute4.getText())) {
                    errorLabel.setText("Height must be bigger than zero");
                    return false;
                }
                break;
            case "Acoustic Drum":
                //if acoustic drum attribute 1 (snare drum number) is not a positive number then print error.
                if (!Helper.isPositiveNumber(addAttribute1.getText())) {
                    errorLabel.setText("Snare Drum Number must bigger than zero");
                    return false;
                }
                //if acoustic drum attribute 2 (bass drum number) is not a positive number then print error.
                if (!Helper.isPositiveNumber(addAttribute2.getText())) {
                    errorLabel.setText("Bass Drum Number must be bigger than zero");
                    return false;
                }
                //if acoustic drum attribute 3 (tom number) is not a positive number then print error.
                if (!Helper.isPositiveNumber(addAttribute3.getText())) {
                    errorLabel.setText("Tom Number must be bigger than zero");
                    return false;
                }
                break;
            case "Electronic Drum":
                //if electronic drum attribute 1 (snare drum number) is not a positive number then print error
                if (!Helper.isPositiveNumber(addAttribute1.getText())) {
                    errorLabel.setText("Snare Drum Number must bigger than zero");
                    return false;
                }
                //if electronic drum attribute 2 (bass drum number) is not a positive number then print error.
                if (!Helper.isPositiveNumber(addAttribute2.getText())) {
                    errorLabel.setText("Bass Drum Number must be bigger than zero");
                    return false;
                }
                //if electronic drum attribute 3 (tom number) is not a positive number then print error.
                if (!Helper.isPositiveNumber(addAttribute3.getText())) {
                    errorLabel.setText("Tom Number must be bigger than zero");
                    return false;
                }
                break;
            case "Violin":
                //if violin attribute 3 (height) is not a positive number then print error.
                if (!Helper.isPositiveNumber(addAttribute3.getText())) {
                    errorLabel.setText("Height must be bigger than zero");
                    return false;
                }
                //if violin attribute 4 (string thickness) is not a positive number then print error.
                if (!Helper.isPositiveNumber(addAttribute4.getText())) {
                    errorLabel.setText("String Thickness must be bigger than zero");
                    return false;
                }
                break;
            case "Cello":
                //if cello attribute 3 (height) is not a positive number then print error.
                if (!Helper.isPositiveNumber(addAttribute3.getText())) {
                    errorLabel.setText("Height must be bigger than zero");
                    return false;
                }
                //if cello attribute 4 (string count) is not a positive number then print error.
                if (!Helper.isPositiveNumber(addAttribute4.getText())) {
                    errorLabel.setText("String Count must be bigger than zero");
                    return false;
                }
                break;
            case "Amp":
                //if amp attribute 2 (power) is not a positive number then print error.
                if (!Helper.isPositiveNumber(addAttribute2.getText())) {
                    errorLabel.setText("Power must be bigger than zero");
                    return false;
                }
                //if amp attribute 4 (channel number) is not a positive number then print error.
                if (!Helper.isPositiveNumber(addAttribute4.getText())) {
                    errorLabel.setText("Channel Number must be bigger than zero");
                    return false;
                }
                break;
            case "Clarinet":
                //if clarinet attribute 2 (bore width) is not a positive number then print error.
                if (!Helper.isPositiveNumber(addAttribute2.getText())) {
                    errorLabel.setText("Bore Width must be bigger than zero");
                    return false;
                }
                //if clarinet attribute 3 (key number) is not a positive number then print error.
                if (!Helper.isPositiveNumber(addAttribute3.getText())) {
                    errorLabel.setText("Key Number must be bigger than zero");
                    return false;
                }
                break;
            case "Trombone":
                //if trombone attribute 2 (bore width) is not a positive number then print error.
                if (!Helper.isPositiveNumber(addAttribute2.getText())) {
                    errorLabel.setText("Bore Width must be bigger than zero");
                    return false;
                }
                //if clarinet attribute 3 (valve number) is not a positive number then print error.
                if (!Helper.isPositiveNumber(addAttribute3.getText())) {
                    errorLabel.setText("Valve Number must be bigger than zero");
                    return false;
                }
                break;
            case "Trumpet":
                //if trumpet attribute 2 (bore width) is not a positive number then print error.
                if (!Helper.isPositiveNumber(addAttribute2.getText())) {
                    errorLabel.setText("Bore Width must be bigger than zero");
                    return false;
                }
                //if trumpet attribute 4 (piston valve) is not a positive number then print error.
                if (!Helper.isPositiveNumber(addAttribute4.getText())) {
                    errorLabel.setText("Piston Valve must be bigger than zero");
                    return false;
                }
                break;
        }

        return true;
    }


    //********************ITEM EDIT PAGE************************

    //if table clicked get the selected item and set into itemPreviewBlock
    @FXML
    void tableClickedAction(MouseEvent event) throws IOException {
        Item selectedItem = selectionModel.getSelectedItem();
        if(selectedItem != null){
            Helper.clearScreen(gridPaneEditItem);
            guiHelper.showBlock(selectedItem,"itemPreviewBlock",this,gridPaneEditItem);
        }
    }

    //loadTable with given observable item list.
    public void loadTable(ObservableList<Item> items) {
        tableView.setEditable(false);
        itemName.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        itemId.setCellValueFactory(new PropertyValueFactory<Item, Integer>("id"));
        itemPrice.setCellValueFactory(new PropertyValueFactory<Item, Double>("price"));
        itemStock.setCellValueFactory((new PropertyValueFactory<Item, Integer>("stock")));
        tableView.setItems(items);
    }

    //if search bar is typed loadTable with filtered itemList
    @FXML
    void searchBarTyped(KeyEvent event) {
        loadTable(getTableList(searchBox.getText()));
    }

}
