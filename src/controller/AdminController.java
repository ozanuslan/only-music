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
import model.Administrator;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    //init logOut button.
    @FXML
    private Button logoutButton;

    //init welcomeAdminLabel.
    @FXML
    private Label welcomeAdminLabel;

    //init orderPane.
    @FXML
    private AnchorPane orderPane;

    //init itemPane.
    @FXML
    private AnchorPane itemPane;

    //init errorLabel.
    @FXML
    private Label errorLabel;

    //init userPane.
    @FXML
    private AnchorPane userPane;


    //init the SceneBuilder for opening new scenes and closing scenes.
    SceneBuilder sb = SceneBuilder.getSceneBuilder();
    //init the storage for taking the active user in the program.
    Storage storage = Storage.getStorage();


    //init the welcome label for welcome message to the admin.
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcomeAdminLabel.setText("Welcome \uD83D\uDC4B, " + storage.getActiveUser().getName()+"!");
    }

    //performs logout when logout button clicked.
    @FXML
    void logoutButtonAction(ActionEvent event) throws Exception {
        Helper.logOut(logoutButton);
    }

    //directs to adminItem page and closes the current page.
    @FXML
    void itemsClicked(MouseEvent event) throws Exception {
        sb.closeScene(logoutButton);
        storage.addLastLocation("adminPanel");
        sb.createScene("adminPanel-item");
    }

    //directs to adminOrder page and closes the current page.
    @FXML
    void ordersClicked(MouseEvent event) throws Exception {
        sb.closeScene(logoutButton);
        storage.addLastLocation("adminPanel");
        sb.createScene("adminPanel-orders");

    }

    /*directs to adminUser page if admin privilege leve is equal to 2 closes current page.
    if admin has no permission then error label shows up. */
    @FXML
    void usersClicked(MouseEvent event) throws Exception {
        if (((Administrator) storage.getActiveUser()).getPrivilegeLevel() == 2) {
            sb.closeScene(logoutButton);
            storage.addLastLocation("adminPanel");
            sb.createScene("adminPanel-users");
        } else {
            errorLabel.setText("You have no privilege to enter this page");
            errorLabel.getStyleClass().clear();
            errorLabel.getStyleClass().add("text-item-name");
            errorLabel.getStyleClass().add("text-color-error");
        }
    }
}
