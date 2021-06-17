package controller;

import helper.DatabaseConnection;
import helper.SceneBuilder;
import helper.Storage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.CartItem;
import model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderBlockAdminController {

    @FXML
    private Label receiverLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Button orderDetailsButton;

    @FXML
    private Button checkButton;

    @FXML
    private Button cancelButton;

    SceneBuilder sceneBuilder = SceneBuilder.getSceneBuilder();
    Storage storage = Storage.getStorage();


    AdminOrderPageController adminOrderPageController;

    private Order order;
    DatabaseConnection connection = new DatabaseConnection();
    Connection connectDB = connection.getConnection();

    void setOrder(Order order){
        this.order = order;
        receiverLabel.setText(order.getCustomer().getName());
        priceLabel.setText("$"+String.valueOf(order.getTotalPrice()));
        if(order.getStatus() != 0){
            cancelButton.setVisible(false);
            checkButton.setVisible(false);
        }
    }
    void setAdminOrderPageController(AdminOrderPageController adminOrderPageController){
        this.adminOrderPageController = adminOrderPageController;
    }

    @FXML
    void cancelButtonAction(ActionEvent event) throws SQLException {
        order.setStatus(2);
        adminOrderPageController.deleteGrid();
        adminOrderPageController.update();
        Statement statement = connectDB.createStatement();
        String updateStatusQuery = "UPDATE `order` SET `status` = ? WHERE (`orderId` = ?)";
        PreparedStatement ps1 = connectDB.prepareStatement(updateStatusQuery);
        ps1.setString(1, "2");
        ps1.setString(2, Integer.toString(order.getId()));
        ps1.executeUpdate();

        String updateStockQuery = "UPDATE `item` SET `stock` = ? WHERE (`idItem` = ?)";
        PreparedStatement ps2 = connectDB.prepareStatement(updateStockQuery);
        for (CartItem item : order.getItems()) {
            ps2.setString(1, Integer.toString(item.getItem().getStock() + item.getQuantity()));
            ps2.setString(2, Integer.toString(item.getItem().getId()));
            ps2.executeUpdate();
            item.getItem().increaseStock(item.getQuantity());
        }
    }

    @FXML
    void checkButtonAction(ActionEvent event) throws SQLException {
        order.setStatus(1);
        adminOrderPageController.deleteGrid();
        adminOrderPageController.update();
        Statement statement = connectDB.createStatement();
        String updateStatusQuery = "UPDATE `order` SET `status` = ? WHERE (`orderId` = ?)";
        PreparedStatement ps1 = connectDB.prepareStatement(updateStatusQuery);
        ps1.setString(1, "1");
        ps1.setString(2, Integer.toString(order.getId()));
        ps1.executeUpdate();
    }

    @FXML
    void orderDetailsAction(ActionEvent event) throws Exception {
        sceneBuilder.closeScene(orderDetailsButton);
        storage.addLastLocation("adminPanel-orders");
        storage.setLastClidkedOrder(order);
        sceneBuilder.createScene("orderDetailPage");
    }

}
