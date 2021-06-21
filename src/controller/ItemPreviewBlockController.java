package controller;

import helper.DatabaseConnection;
import helper.Helper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ItemPreviewBlockController implements BlockController {
    @FXML
    private Label itemNameLabel;

    @FXML
    private Label itemPriceLabel;

    @FXML
    private ImageView itemImage;

    @FXML
    private TextField stockInput;

    private Item item;
    private AdminItemPageController adminItemPageController;
    DatabaseConnection db = new DatabaseConnection();
    Connection connectDB = db.getConnection();

    /**
     * Sets data and prints it
     * @param data
     * @param <T>
     */
    public <T> void setData(T data){
        this.item = (Item) data;
        itemNameLabel.setText(item.getName());
        itemPriceLabel.setText("$"+String.valueOf(item.getPrice()));
        Image image = new Image(getClass().getResourceAsStream((item).getImagePath()));
        itemImage.setImage(image);
        stockInput.setText(String.valueOf(item.getStock()));
    }

    public void setController(DynamicGridController dynamicGridController){
        this.adminItemPageController = (AdminItemPageController) dynamicGridController;
    }

    /**
     * Sets item's stock and updates database
     * @param event
     * @throws SQLException
     */
    @FXML
    void setStockButtonAction(ActionEvent event) throws SQLException {
        if(Helper.isPositiveNumber(stockInput.getText())){
            item.setStock(Integer.parseInt(stockInput.getText()));
            String updateQuery = "UPDATE `item` SET `stock` = " + stockInput.getText() + " WHERE idItem = " + item.getId();
            try {
                PreparedStatement ps = connectDB.prepareStatement(updateQuery);
                ps.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            adminItemPageController.itemEditSuccessful();
            adminItemPageController.loadTable(FXCollections.observableArrayList(Helper.getItems()));
        }
        else{
            adminItemPageController.itemEditError();
        }
    }
}


