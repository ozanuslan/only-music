package controller;

import helper.ContentFilter;
import helper.DatabaseConnection;
import helper.Helper;
import helper.Storage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.util.converter.IntegerStringConverter;
import model.Administrator;
import model.User;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AdminUserPageController implements Initializable {

    @FXML
    private Button backwardButton;

    @FXML
    private Label errorLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private TextField searchBox;

    @FXML
    private TextField emailInput;

    @FXML
    private TableView<User> tableView;

    @FXML
    private TableColumn<User, Integer> idColumn;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, String> nameColumn;

    @FXML
    private TableColumn<User, String> surnameColumn;

    @FXML
    private TableColumn<User, Integer> privilegeLevelColumn;

    @FXML
    private TextField nameInput;

    @FXML
    private TextField surnameInput;

    @FXML
    private TextField usernameInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private TextField privilegeLevelInput;

    DatabaseConnection db = new DatabaseConnection();
    Connection connectDB = db.getConnection();
    Storage storage = Storage.getStorage();
    private ObservableList<User> userList = FXCollections.observableArrayList(storage.getUserList());


    /**
     * loads the table with observable userList
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTable(userList);
    }

    /**
     * if the inputs are valid adds the user to the userList and database.
     * @param event
     * @throws SQLException
     */
    @FXML
    void addUserButtonAction(ActionEvent event) throws SQLException {
            if(isInputValid()){
                String queryText = "INSERT INTO user_account" + "(username,password,name,surname,email,privilegeLevel) VALUES " + "(?,?,?,?,?,?)";
                PreparedStatement ps = connectDB.prepareStatement(queryText);
                ps.setString(1, usernameInput.getText());
                ps.setString(2, passwordInput.getText());
                ps.setString(3, nameInput.getText());
                ps.setString(4, surnameInput.getText());
                ps.setString(5, emailInput.getText());
                ps.setString(6, privilegeLevelInput.getText());
                ps.executeUpdate();
                errorLabel.setText("New admin successfully created");
                errorLabel.getStyleClass().clear();
                errorLabel.getStyleClass().add("text-item-name");
                errorLabel.getStyleClass().add("text-color-success");
                userList = FXCollections.observableArrayList(Helper.getAllUsers());
                loadTable(userList);
            }
    }

    /**
     *  check all of the inputs for the add user section. If not prints the related error.
     * @return inputs are valid or not
     */
    boolean isInputValid(){
        if (nameInput.getText().isBlank() || surnameInput.getText().isBlank() || usernameInput.getText().isBlank() || passwordInput.getText().isBlank() || privilegeLevelInput.getText().isBlank() || !Helper.isPositiveNumber(privilegeLevelInput.getText())) {
            errorLabel.setText("Please fill all the blanks properly");
            errorLabel.getStyleClass().clear();
            errorLabel.getStyleClass().add("text-item-name");
            errorLabel.getStyleClass().add("text-color-error");
            return false;
        }
        else if(Integer.parseInt(privilegeLevelInput.getText()) > 2 || Integer.parseInt(privilegeLevelInput.getText()) < 1){
            errorLabel.setText("Privilege level can only be 1 or 2");
            errorLabel.getStyleClass().clear();
            errorLabel.getStyleClass().add("text-item-name");
            errorLabel.getStyleClass().add("text-color-error");
            return false;
        }
        else if(!Helper.isValidEmail(emailInput.getText())){
            errorLabel.setText("Please enter email field properly");
            errorLabel.getStyleClass().clear();
            errorLabel.getStyleClass().add("text-item-name");
            errorLabel.getStyleClass().add("text-color-error");
            return false;
        }
        return true;
    }

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

    /**
     * if search bar typed loads table again with filtered userList
     * @param event
     */
    @FXML
    void searchBarTyped(KeyEvent event) {
        loadTable(getTableList(searchBox.getText()));
    }

    /**
     * returns filtered observable list
     * @param filter
     * @return
     */
    ObservableList getTableList(String filter) {
        return FXCollections.observableList(ContentFilter.getFilteredUserList(storage.getUserList(), filter));
    }

    /**
     * loads the table according to observable user list.
     * admin can change the privilege level of the other admins in the program.
     * @param users
     */
    public void loadTable(ObservableList<User> users) {
        tableView.setEditable(true);

        idColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("surname"));
        usernameColumn.setCellValueFactory((new PropertyValueFactory<User, String>("username")));
        privilegeLevelColumn.setCellValueFactory((new PropertyValueFactory<User, Integer>("privilegeLevel")));
        privilegeLevelColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        privilegeLevelColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<User, Integer>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<User, Integer> t) {
                        User user = t.getTableView().getItems().get(t.getTablePosition().getRow());
                        if (user instanceof Administrator && !user.getUsername().equals(storage.getActiveUser().getUsername()) && t.getNewValue() > 0 && t.getNewValue() < 3) {
                            if (!t.getNewValue().equals(t.getOldValue())) {
                                Administrator admin = (Administrator) user;
                                admin = (Administrator) Helper.findUser(storage.getUserList(), admin.getId());
                                admin.setPrivilegeLevel(t.getNewValue());
                                String updateQuery = "UPDATE `user_account` SET `privilegeLevel` = " + t.getNewValue() + " WHERE idUser = " + admin.getId();
                                try {
                                    PreparedStatement ps = connectDB.prepareStatement(updateQuery);
                                    ps.executeUpdate();
                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                }
                            }
                        }
                    }
                }
        );
        tableView.setItems(users);
    }

}
