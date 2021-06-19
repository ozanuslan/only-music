package controller;

import helper.Helper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import helper.DatabaseConnection;
import helper.SceneBuilder;

import java.sql.*;

public class RegisterController {

    @FXML
    private Button registerButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label labelMessage;
    DatabaseConnection connection = new DatabaseConnection();
    Connection connectDB = connection.getConnection();

    public void registerButtonAction(ActionEvent event) throws SQLException {
        if(usernameField.getText().isBlank() || passwordField.getText().isBlank() || nameField.getText().isBlank() || surnameField.getText().isBlank() || emailField.getText().isBlank()){
            labelMessage.setText("Please enter empty fields.");
        } else{
            boolean register=true;
            Statement statement = connectDB.createStatement();
            String usernameQuery = "SELECT username FROM `user_account`";
            ResultSet queryResult = statement.executeQuery(usernameQuery);
            while(queryResult.next()){
                if(usernameField.getText().equals(queryResult.getString("username"))) register=false;
            }
            if(register) {
                registerUser();
            }else{
                labelMessage.getStyleClass().clear();
                labelMessage.getStyleClass().add("text-color-error");
                labelMessage.setText("User name is already taken.");
            }
        }
    }

    public void cancelButtonAction(ActionEvent event) throws Exception {
        Helper.goBackward(cancelButton);
    }

    public void registerUser(){
        String queryText = "INSERT INTO user_account"+"(username,password,name,surname,email) VALUES "+"(?,?,?,?,?)";
        try {
            PreparedStatement ps=connectDB.prepareStatement(queryText);
            ps.setString(1, usernameField.getText());
            ps.setString(2, passwordField.getText());
            ps.setString(3, nameField.getText());
            ps.setString(4, surnameField.getText());
            ps.setString(5, emailField.getText());
            ps.executeUpdate();
            labelMessage.setText("Successfully registered.");
            Thread.sleep(1000);
            Helper.goBackward(cancelButton);
        }  catch (Exception e3) {
            e3.printStackTrace();
        }
    }
}
