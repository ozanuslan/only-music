package controller;

import helper.Storage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import model.Address;
import model.Administrator;
import model.Customer;
import helper.DatabaseConnection;
import helper.SceneBuilder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static model.Administrator.authLevel.HIGH;
import static model.Administrator.authLevel.LOW;


public class LoginController{
    @FXML
    private Button registerButton;

    @FXML
    private Button loginButton;

    @FXML
    private Label loginMessageLabel;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    SceneBuilder sb = SceneBuilder.getSceneBuilder();
    Storage storage = Storage.getStorage();

    public void loginButtonAction(ActionEvent event){

        if(usernameField.getText().isBlank() && passwordField.getText().isBlank()){
            loginMessageLabel.setText("Please enter username and password");
        } else{
            validateLogin();
        }
    }
    public void registerButtonOnAction(ActionEvent event) throws Exception {
        sb.closeScene(registerButton);
        storage.addLastLocation("login");
        sb.createScene("register");
    }
    public void validateLogin(){
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();

        String verifyLogin = "SELECT * FROM user_account";

        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()){
                if(queryResult.getString("username").equals(usernameField.getText()) && queryResult.getString("password").equals(passwordField.getText())){
                    loginMessageLabel.setText("Congratulations!");
                    Thread.sleep(1000);


                    sb.closeScene(loginButton);
                    if(queryResult.getInt("privilegeLevel") == 0){
                        Customer customer = new Customer(queryResult.getString("username"), queryResult.getString("name"), queryResult.getString("surname"),queryResult.getString("email"),queryResult.getInt("idUser"));
                        storage.setActiveUser(customer);
                        setCustomerAddress(customer,connectDB);
                        sb.createScene("main");
                    }

                    else{
                        Administrator admin = new Administrator(queryResult.getString("username"), queryResult.getString("name"),
                                queryResult.getString("surname"),queryResult.getString("email"),queryResult.getInt("idUser"),queryResult.getInt("privilegeLevel")> 1 ? HIGH : LOW);
                        storage.setActiveUser(admin);
                        sb.createScene("adminPanel");
                    }

                    break;
                }
                else{
                   loginMessageLabel.setText("Invalid login please try again.");
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    void setCustomerAddress(Customer customer, Connection connectDB) throws SQLException {
        Statement statement = connectDB.createStatement();
        String addressQuery = "SELECT * FROM `address` where idUser= " + customer.getId();
        ResultSet queryResult = statement.executeQuery(addressQuery);

        while(queryResult.next()){
            Address address = new Address(queryResult.getString("city"),queryResult.getString("province"),queryResult.getString("address"),queryResult.getString("phone"),Integer.parseInt(queryResult.getString("postCode")));
            customer.setAddress(address);
        }
    }
}
