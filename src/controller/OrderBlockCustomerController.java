package controller;

import helper.DatabaseConnection;
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
    }

    @FXML
    void orderDetailsAction(ActionEvent event) {

    }

}
