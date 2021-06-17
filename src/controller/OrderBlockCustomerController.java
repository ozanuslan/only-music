package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.Order;

public class OrderBlockCustomerController {

    @FXML
    private Label priceLabel;

    @FXML
    private Button orderDetailsButton;

    @FXML
    private Button cancelButton;

    private Order order;

    CustomerOrderController customerOrderController;

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
    void cancelButtonAction(ActionEvent event) {
        order.setStatus(2);
        customerOrderController.deleteGrid();
        customerOrderController.update();
    }

    @FXML
    void orderDetailsAction(ActionEvent event) {

    }

}
