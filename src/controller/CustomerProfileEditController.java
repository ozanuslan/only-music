package controller;

import helper.DatabaseConnection;
import helper.Helper;
import helper.SceneBuilder;
import helper.Storage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Customer;

import java.sql.*;

public class CustomerProfileEditController {

    @FXML
    private Label errorLabel;

    @FXML
    private Button cartButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button backwardButton;

    @FXML
    private TextField cityInput;

    @FXML
    private TextField provinceInput;

    @FXML
    private TextField addressInput;

    @FXML
    private TextField phoneNumberInput;

    @FXML
    private TextField postCodeInput;

    @FXML
    private Button setAddressButton;

    @FXML
    private TextField oldPasswordInput;

    @FXML
    private TextField newPasswordInput;

    @FXML
    private TextField rewritePasswordInput;

    @FXML
    private Button changePasswordButton;

    @FXML
    private TextField emailInput;

    @FXML
    private Button setEmailButton;

    DatabaseConnection connection = new DatabaseConnection();
    Connection connectDB = connection.getConnection();
    Storage storage = Storage.getStorage();
    SceneBuilder sceneBuilder = SceneBuilder.getSceneBuilder();
    Customer customer = (Customer) storage.getActiveUser();

    @FXML
    void backwardButtonAction(ActionEvent event) throws Exception {
        Helper.goBackward(backwardButton);
    }

    @FXML
    void cartButtonAction(ActionEvent event) throws Exception {
        sceneBuilder.closeScene(cartButton);
        storage.addLastLocation("main");
        sceneBuilder.createScene("cartPage");
    }

    @FXML
    void changePasswordButtonAction(ActionEvent event) throws SQLException {
        if (newPasswordInput.getText() != null && rewritePasswordInput.getText() != null && rewritePasswordInput.getText() != null) {
            Statement statement = connectDB.createStatement();
            String passwordQuery = "SELECT password FROM `only-music`.user_account where idUser= " + customer.getId();
            ResultSet queryResult = statement.executeQuery(passwordQuery);
            queryResult.next();
            String customerPassword = queryResult.getString("password");
            if (newPasswordInput.getText().equals(rewritePasswordInput.getText()) && oldPasswordInput.getText().equals(customerPassword)) {
                String updateQuery = "UPDATE `user_account` SET `password` = ? WHERE (`idUser` = ?)";
                PreparedStatement ps = connectDB.prepareStatement(updateQuery);
                ps.setString(1, newPasswordInput.getText());
                ps.setString(2, Integer.toString(customer.getId()));
                ps.executeUpdate();
            }
        }
    }

    @FXML
    void logoutButtonAction(ActionEvent event) throws Exception {
        Helper.logOut(logoutButton);
    }

    @FXML
    void setAddressButtonAction(ActionEvent event) throws SQLException {
        Statement statement = connectDB.createStatement();
        if (cityInput.getText() != null && provinceInput.getText() != null && postCodeInput.getText() != null && phoneNumberInput.getText() != null && addressInput.getText() != null) {
            if (customer.getAddress() == null) {
                String insertQuery = "INSERT INTO `address` (`idUser`, `city`, `province`, `address`, `phone`, `postCode`) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = connectDB.prepareStatement(insertQuery);
                ps.setString(1, Integer.toString(customer.getId()));
                ps.setString(2, cityInput.getText());
                ps.setString(3, provinceInput.getText());
                ps.setString(4, addressInput.getText());
                ps.setString(5, phoneNumberInput.getText());
                ps.setString(6, postCodeInput.getText());
                ps.executeUpdate();
            }else{
                String updateQuery="UPDATE `address` SET `city` = ?, `province` = ?, `address` = ?, `postCode` = ? WHERE (`idUser` = ?)";
                PreparedStatement ps = connectDB.prepareStatement(updateQuery);
                ps.setString(1, cityInput.getText());
                ps.setString(2, provinceInput.getText());
                ps.setString(3, addressInput.getText());
                ps.setString(4, phoneNumberInput.getText());
                ps.setString(5, postCodeInput.getText());
                ps.setString(6, Integer.toString(customer.getId()));
                ps.executeUpdate();
            }
        } else {
        }
    }

    @FXML
    void setEmailButtonAction(ActionEvent event) throws SQLException {
        if (emailInput.getText() != null) {
            Statement statement = connectDB.createStatement();
            String updateQuery = "UPDATE `user_account` SET `email` = ? WHERE (`idUser` = ?)";
            PreparedStatement ps = connectDB.prepareStatement(updateQuery);
            ps.setString(1, emailInput.getText());
            ps.setString(2, Integer.toString(customer.getId()));
            ps.executeUpdate();
        } else {

        }
    }

}
