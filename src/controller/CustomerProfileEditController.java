package controller;

import helper.DatabaseConnection;
import helper.Helper;
import helper.SceneBuilder;
import helper.Storage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Address;
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
    private PasswordField oldPasswordInput;

    @FXML
    private PasswordField newPasswordInput;

    @FXML
    private PasswordField rewritePasswordInput;
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
        if (!newPasswordInput.getText().equals("") && !rewritePasswordInput.getText().equals("") && !rewritePasswordInput.getText().equals("")) {
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
                errorLabel.setText("Your password is successfully set");
                errorLabel.getStyleClass().clear();
                errorLabel.getStyleClass().add("text-item-price");
                errorLabel.getStyleClass().add("text-color-success");
            } else {
                if (!oldPasswordInput.getText().equals(customerPassword)) {
                    errorLabel.setText("Your entered your current password wrong");
                } else if (!newPasswordInput.getText().equals(rewritePasswordInput.getText())) {
                    errorLabel.setText("Please rewrite your new password correctly");
                }
                errorLabel.getStyleClass().clear();
                errorLabel.getStyleClass().add("text-item-price");
                errorLabel.getStyleClass().add("text-color-error");
            }
        } else {
            errorLabel.setText("Please enter all inputs properly");
            errorLabel.getStyleClass().clear();
            errorLabel.getStyleClass().add("text-item-price");
            errorLabel.getStyleClass().add("text-color-error");
        }
    }

    @FXML
    void logoutButtonAction(ActionEvent event) throws Exception {
        Helper.logOut(logoutButton);
    }

    @FXML
    void setAddressButtonAction(ActionEvent event) throws SQLException {
        if (!cityInput.getText().isBlank() && !provinceInput.getText().isBlank() && !postCodeInput.getText().isBlank() && Helper.isPositiveNumber(postCodeInput.getText()) && !phoneNumberInput.getText().isBlank() && !addressInput.getText().isBlank() && Helper.isValidPhone(phoneNumberInput.getText())) {
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
            } else {
                String updateQuery = "UPDATE `address` SET `city` = ?, `province` = ?, `address` = ?, `phone` = ?, `postCode` = ? WHERE (`idUser` = ?)";
                PreparedStatement ps = connectDB.prepareStatement(updateQuery);
                ps.setString(1, cityInput.getText());
                ps.setString(2, provinceInput.getText());
                ps.setString(3, addressInput.getText());
                ps.setString(4, phoneNumberInput.getText());
                ps.setString(5, postCodeInput.getText());
                ps.setString(6, Integer.toString(customer.getId()));
                ps.executeUpdate();
            }
            customer.setAddress(new Address(cityInput.getText(), provinceInput.getText(), addressInput.getText(), phoneNumberInput.getText(), Integer.parseInt(postCodeInput.getText())));
            errorLabel.setText("Your address is successfully set");
            errorLabel.getStyleClass().clear();
            errorLabel.getStyleClass().add("text-item-price");
            errorLabel.getStyleClass().add("text-color-success");
        } else {
            errorLabel.setText("Please enter all inputs properly");
            errorLabel.getStyleClass().clear();
            errorLabel.getStyleClass().add("text-item-price");
            errorLabel.getStyleClass().add("text-color-error");
        }
    }

    @FXML
    void setEmailButtonAction(ActionEvent event) throws SQLException {
        boolean isValidEmail = Helper.isValidEmail(emailInput.getText());
        if (isValidEmail) {
            Statement statement = connectDB.createStatement();
            String updateQuery = "UPDATE `user_account` SET `email` = ? WHERE (`idUser` = ?)";
            PreparedStatement ps = connectDB.prepareStatement(updateQuery);
            ps.setString(1, emailInput.getText());
            ps.setString(2, Integer.toString(customer.getId()));
            ps.executeUpdate();
            errorLabel.setText("Your email is successfully set");
            errorLabel.getStyleClass().clear();
            errorLabel.getStyleClass().add("text-item-price");
            errorLabel.getStyleClass().add("text-color-success");
        } else {
            errorLabel.setText("Please enter your email address properly");
            errorLabel.getStyleClass().clear();
            errorLabel.getStyleClass().add("text-item-price");
            errorLabel.getStyleClass().add("text-color-error");
        }
    }
}
