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
import model.Customer;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerProfilePageController implements Initializable {

        @FXML
        private Button cartButton;

        @FXML
        private Button logoutButton;

        @FXML
        private Button backwardButton;

        @FXML
        private Label welcomeLabel;

        SceneBuilder sceneBuilder = SceneBuilder.getSceneBuilder();
        Storage storage = Storage.getStorage();

        Customer customer = (Customer)(storage.getActiveUser());

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
                welcomeLabel.setText("Hi \uD83D\uDC4B, "+customer.getName()+"!");
        }

        @FXML
        void backwardButtonAction(ActionEvent event) throws Exception {
                Helper.goBackward(backwardButton);
        }

        @FXML
        void cartButtonAction(ActionEvent event) throws Exception {
                sceneBuilder.closeScene(cartButton);
                storage.addLastLocation("customerProfilePage");
                sceneBuilder.createScene("cartPage");
        }

        @FXML
        void editProfileAction(MouseEvent event) throws Exception {
                sceneBuilder.closeScene(cartButton);
                storage.addLastLocation("customerProfilePage");
                sceneBuilder.createScene("customerProfileEditPage");
        }

        @FXML
        void logoutButtonAction(ActionEvent event) throws Exception {
                Helper.logOut(logoutButton);
        }

        @FXML
        void myOrdersAction(MouseEvent event) throws Exception {
                sceneBuilder.closeScene(cartButton);
                storage.addLastLocation("customerProfilePage");
                sceneBuilder.createScene("customerOrderPage");
        }

}
