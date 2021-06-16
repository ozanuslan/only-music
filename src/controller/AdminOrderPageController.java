package controller;

import helper.Helper;
import helper.Storage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

public class AdminOrderPageController {

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

    @FXML
    void backwardButtonAction(ActionEvent event) throws Exception {
        Helper.goBackward(backwardButton);
    }

    @FXML
    void logoutButtonAction(ActionEvent event) throws Exception {
        Helper.logOut(logoutButton);
    }

}
