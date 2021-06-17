package controller;

import helper.Helper;
import helper.Storage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import model.Order;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminOrderPageController implements Initializable {

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
        int rowPending = 0;
        int rowCompleted = 0;
        try {
            int size = orders.size();
            for(int i = 0;i<size;i++){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/GUI/order-block-admin.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();

                OrderBlockAdminController orderBlockAdminController = fxmlLoader.getController();
                orderBlockAdminController.setOrder(orders.get(i));
                orderBlockAdminController.setAdminOrderPageController(this);
                if(orders.get(i).getStatus() == 0){
                    pendingOrdersGrid.add(anchorPane, 0, rowPending++);
                    pendingOrdersGrid.setMinWidth(Region.USE_COMPUTED_SIZE);
                    pendingOrdersGrid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    pendingOrdersGrid.setMaxWidth(Region.USE_PREF_SIZE);

                    pendingOrdersGrid.setMinHeight(Region.USE_COMPUTED_SIZE);
                    pendingOrdersGrid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    pendingOrdersGrid.setMaxHeight(Region.USE_PREF_SIZE);
                }
                else{
                    completedOrdersGrid.add(anchorPane, 0, rowCompleted++);
                    completedOrdersGrid.setMinWidth(Region.USE_COMPUTED_SIZE);
                    completedOrdersGrid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    completedOrdersGrid.setMaxWidth(Region.USE_PREF_SIZE);

                    completedOrdersGrid.setMinHeight(Region.USE_COMPUTED_SIZE);
                    completedOrdersGrid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    completedOrdersGrid.setMaxHeight(Region.USE_PREF_SIZE);
                }
                GridPane.setMargin(anchorPane, new Insets(20,60,20,60));

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    void deleteGrid(){
        Helper.clearScreen(pendingOrdersGrid);
        Helper.clearScreen(completedOrdersGrid);
    }
}
