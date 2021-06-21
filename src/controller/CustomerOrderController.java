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

    /**
     * directs the last used page.
     * @param event
     * @throws Exception
     */
    @FXML
    void backwardButtonAction(ActionEvent event) throws Exception {
        Helper.goBackward(backwardButton);
    }

    /**
     * performs log out operation of the user.
     * @param event
     * @throws Exception
     */
    @FXML
    void logoutButtonAction(ActionEvent event) throws Exception {
        Helper.logOut(logoutButton);
    }

    Storage storage = Storage.getStorage();
    GUIHelper guiHelper = GUIHelper.getGuiHelper();
    ArrayList<Order> orders = ((Customer)(storage.getActiveUser())).getOrder();

    /**
     * initialize the first view of the orders in the page.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        update();
    }

    /**
     * deletes grid and insert the order list into the grid pane and print it.
     */
    public void update(){
        deleteGrid();
        guiHelper.showDoubleDynamicGrid(orders, pendingOrdersGrid,completedOrdersGrid,this,"order-block-customer",10,60);
    }

    /**
     * clears the every grid in the customer order page.
     */
    void deleteGrid(){
        pendingOrdersGrid.getChildren().removeIf(node -> true);
        completedOrdersGrid.getChildren().removeIf(node -> true);
    }


}
