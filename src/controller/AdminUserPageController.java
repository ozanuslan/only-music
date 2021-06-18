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
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import model.User;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class AdminUserPageController implements Initializable {

    @FXML
    private Button backwardButton;

    @FXML
    private Button logoutButton;

    @FXML
    private TextField searchBox;

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
    private TableColumn<User, Enum> privilegeLevelColumn;

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTable(userList);
    }

    @FXML
    void addUserButtonAction(ActionEvent event) {

    }

    @FXML
    void backwardButtonAction(ActionEvent event) throws Exception {
        Helper.goBackward(backwardButton);
    }

    @FXML
    void logoutButtonAction(ActionEvent event) throws Exception {
        Helper.logOut(logoutButton);
    }

    @FXML
    void searchBarTyped(KeyEvent event) {
        loadTable(getTableList(searchBox.getText()));
    }

    ObservableList getTableList(String filter){
        return FXCollections.observableList(ContentFilter.getFilteredUserList(storage.getUserList(), filter));
    }

    public void loadTable(ObservableList<User> users){
        tableView.setEditable(true);

        idColumn.setCellValueFactory(new PropertyValueFactory<User,Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<User,String>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<User,String>("surname"));
        usernameColumn.setCellValueFactory((new PropertyValueFactory<User, String>("username")));
        //privilegeLevelColumn.setCellValueFactory((new PropertyValueFactory<User, Enum>("privilegeLevel")));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<User, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<User, String> t) {
                            ((User) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                            ).setName(t.getNewValue());
                    }
                }
        );
        tableView.setItems(users);
    }

}
