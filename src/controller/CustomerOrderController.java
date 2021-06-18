package controller;

import helper.GUIHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

import helper.Helper;
import helper.Storage;
import model.Customer;
import model.Order;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CustomerOrderController implements Initializable, DynamicGridController {


    @FXML
    private Button backwardButton;

    @FXML
    private Button logoutButton;

    @FXML
    private ScrollPane pendingOrdersScroll;

    @FXML
    private GridPane pendingOrdersGrid;

    @FXML
    private ScrollPane completedOrderScroll;

    @FXML
    private GridPane completedOrdersGrid;

    @FXML
    void backwardButtonAction(ActionEvent event) throws Exception {
        Helper.goBackward(backwardButton);
    }

    @FXML
    void logoutButtonAction(ActionEvent event) throws Exception {
        Helper.logOut(logoutButton);
    }

    Storage storage = Storage.getStorage();
    GUIHelper guiHelper = GUIHelper.getGuiHelper();
    ArrayList<Order> orders = ((Customer)(storage.getActiveUser())).getOrder();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        update();
    }
    public void update(){
        deleteGrid();
        guiHelper.showDoubleDynamicGrid(orders, pendingOrdersGrid,completedOrdersGrid,this,"order-block-customer",10,60);
    }
    void deleteGrid(){
        pendingOrdersGrid.getChildren().removeIf(node -> true);
        completedOrdersGrid.getChildren().removeIf(node -> true);
    }


}
