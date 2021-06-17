package controller;

import helper.Helper;
import helper.Storage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import model.Customer;
import model.Order;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OrderDetailPageController implements Initializable {

    @FXML
    private Button backwardButton;

    @FXML
    private Button logoutButton;

    @FXML
    private GridPane gridPane;

    @FXML
    private Label addressLabel;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private Label orderStatusLabel;

    @FXML
    private Label orderDateLabel;

    private Storage storage = Storage.getStorage();
    private Order order = storage.getLastClidkedOrder();
    private Customer customer = (Customer) (storage.getActiveUser());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            int size = order.getItems().size();
            for(int i = 0;i<size;i++){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/GUI/orderDetailPageBlock.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();

                OrderDetailPageBlockController orderDetailPageBlockController = fxmlLoader.getController();
                orderDetailPageBlockController.setCartItem(order.getItems().get(i));

                gridPane.add(anchorPane, 0, i);
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
        addressLabel.setText(customer.getAddress().addressToString());
        totalPriceLabel.setText(String.valueOf(order.getTotalPrice()));
        if(order.getStatus() == 0)
        orderStatusLabel.setText("Pending order");
        else if(order.getStatus() == 1)
            orderStatusLabel.setText("Completed order");
        else
            orderStatusLabel.setText("Canceled order");

        orderDateLabel.setText(order.getDate().toString());
    }

    void setOrder(Order order){
        this.order = order;
    }
    @FXML
    void backwardButtonAction(ActionEvent event) throws Exception {
        Helper.goBackward(backwardButton);
    }

    @FXML
    void logoutButtonAction(ActionEvent event) throws Exception {
        Helper.logOut(logoutButton);
    }



}
