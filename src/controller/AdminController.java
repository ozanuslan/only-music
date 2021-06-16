package controller;

import helper.Helper;
import helper.SceneBuilder;
import helper.Storage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    private Button logoutButton;

    @FXML
    private Label welcomeAdminLabel;

    @FXML
    private AnchorPane orderPane;

    @FXML
    private AnchorPane itemPane;

    @FXML
    private AnchorPane userPane;



    SceneBuilder sb = SceneBuilder.getSceneBuilder();
    Storage storage = Storage.getStorage();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcomeAdminLabel.setText("Welcome "+storage.getActiveUser().getName());
    }

    @FXML
    void logoutButtonAction(ActionEvent event) throws Exception {
        Helper.logOut(logoutButton);
    }

    @FXML
    void itemsClicked(MouseEvent event) throws Exception {
        sb.closeScene(logoutButton);
        storage.addLastLocation("adminPanel");
        sb.createScene("adminPanel-item");
    }

    @FXML
    void ordersClicked(MouseEvent event) throws Exception {
        sb.closeScene(logoutButton);
        storage.addLastLocation("adminPanel");
        sb.createScene("adminPanel-orders");

    }

    @FXML
    void usersClicked(MouseEvent event) throws Exception {
        sb.closeScene(logoutButton);
        storage.addLastLocation("adminPanel");
        sb.createScene("adminPanel-users");
    }

}
