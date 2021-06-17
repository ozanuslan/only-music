package controller;

import helper.DatabaseConnection;
import helper.SceneBuilder;
import helper.Storage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderBlockCustomerController {

    @FXML
    private Label priceLabel;

    @FXML
    private Button orderDetailsButton;

    @FXML
    private Button cancelButton;

    private Order order;

    SceneBuilder sceneBuilder = SceneBuilder.getSceneBuilder();
    Storage storage = Storage.getStorage();

    CustomerOrderController customerOrderController;
    DatabaseConnection connection = new DatabaseConnection();
    Connection connectDB = connection.getConnection();

    void setOrder(Order order){
        this.order = order;
        priceLabel.setText("$"+order.getTotalPrice());
        if(order.getStatus() != 0) {
            cancelButton.setVisible(false);
        }
    }

    void setCustomerOrderController(CustomerOrderController customerOrderController){
        this.customerOrderController = customerOrderController;
    }

    @FXML
    void cancelButtonAction(ActionEvent event) throws SQLException {
        order.setStatus(2);
        customerOrderController.deleteGrid();
        customerOrderController.update();
        Statement statement = connectDB.createStatement();
        String updateQuery = "UPDATE `order` SET `status` = ? WHERE (`orderId` = ?)";
        PreparedStatement ps = connectDB.prepareStatement(updateQuery);
        ps.setString(1, "2");
        ps.setString(2, Integer.toString(order.getId()));
        ps.executeUpdate();
    }

    @FXML
    void orderDetailsAction(ActionEvent event) throws Exception {
        sceneBuilder.closeScene(orderDetailsButton);
        storage.addLastLocation("customerOrderPage");
        storage.setLastClidkedOrder(order);
        sceneBuilder.createScene("orderDetailPage");
    }

}
