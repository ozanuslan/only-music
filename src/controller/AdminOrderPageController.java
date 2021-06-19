package controller;

import helper.GUIHelper;
import helper.Helper;
import helper.Storage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import model.Order;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminOrderPageController implements Initializable, DynamicGridController {

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

    GUIHelper guiHelper = GUIHelper.getGuiHelper();
    Storage storage = Storage.getStorage();
    ArrayList<Order> orders = new ArrayList<>();

    @FXML
    void backwardButtonAction(ActionEvent event) throws Exception {
        Helper.goBackward(backwardButton);
    }

    @FXML
    void logoutButtonAction(ActionEvent event) throws Exception {
        Helper.logOut(logoutButton);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orders = (ArrayList<Order>) Helper.getAllOrders();
        update();
    }

    public void update(){
        guiHelper.showDoubleDynamicGrid(orders,pendingOrdersGrid,completedOrdersGrid,this,"order-block-admin",20,60);
    }
    void deleteGrid(){
        Helper.clearScreen(pendingOrdersGrid);
        Helper.clearScreen(completedOrdersGrid);
    }
}
