package controller;

import helper.GUIHelper;
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

public class OrderDetailPageController implements DynamicGridController,Initializable {

    @FXML
    private Button backwardButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Label nameSurnameLabel;

    @FXML
    private Label emailLabel;

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
    private Customer customer = order.getCustomer();
    GUIHelper guiHelper = GUIHelper.getGuiHelper();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        guiHelper.showDynamicGrid(order.getItems(),gridPane,this,"orderDetailPageBlock",20,20);
        addressLabel.setText(customer.getAddress().addressToString());
        totalPriceLabel.setText("$"+String.valueOf(order.getTotalPrice()));
        nameSurnameLabel.setText(order.getCustomer().getName()+" "+order.getCustomer().getSurname());
        emailLabel.setText(order.getCustomer().getEmail());
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
