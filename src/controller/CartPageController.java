package controller;

import helper.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import model.Cart;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import model.CartItem;
import model.Customer;
import model.Order;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.ResourceBundle;

public class CartPageController implements Initializable, DynamicGridController {

    public static CartPageController cartPageController;

    public static CartPageController getCartPageController() {
        return cartPageController == null ? cartPageController = new CartPageController() : cartPageController;
    }

    @FXML
    private Button backwardButton;

    @FXML
    private Button logOutButton;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane gridPane;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private Button checkoutButton;

    @FXML
    private Label errorLabel;

    SceneBuilder sceneBuilder = SceneBuilder.getSceneBuilder();
    DatabaseConnection connection = new DatabaseConnection();
    Connection connectDB = connection.getConnection();
    Storage storage = Storage.getStorage();
    Customer customer = (Customer) storage.getActiveUser();
    Cart cart = customer.getCart();
    GUIHelper guiHelper = GUIHelper.getGuiHelper();

    /**
     * initialize the cart items in the grid pane and shows the total price of the cart with update() method
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        update();
    }

    /**
     * shows the cart items in the grid pane and shows the total price of the cart
     */
    public void update() {
        deleteGrid();
        guiHelper.showDynamicGrid(cart.getItemList(), gridPane,this,"cartItemBlock",20,20);
        setTotalCartPrice();
    }

    /**
     * calculates the total cart price in the cart and prints it into label.
     */
    void setTotalCartPrice() {
        int sum = 0;
        for (CartItem i : cart.getItemList()) {
            sum += i.getItem().getPrice() * i.getQuantity();
        }
        totalPriceLabel.setText("$" + Integer.toString(sum));
    }

    /**
     * deletes every item in the grid pane.
     */
    void deleteGrid() {
        gridPane.getChildren().removeIf(node -> true);
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
     *  checks the customer address and the cart size.
     * @return the customer address information is valid and cart is empty
     */
    boolean cartIsValid() {
        if (customer.getAddress() == null) {
            errorLabel.setText("Customer address must be filled from profile");
            errorLabel.getStyleClass().clear();
            errorLabel.getStyleClass().add("text-item-name");
            errorLabel.getStyleClass().add("text-color-error");
            return false;
        } else if (cart.getItemList() == null || cart.getItemList().size() == 0) {
            errorLabel.setText("Cart is empty.");
            errorLabel.getStyleClass().clear();
            errorLabel.getStyleClass().add("text-item-name");
            errorLabel.getStyleClass().add("text-color-error");
            return false;
        }

        errorLabel.setText("Order successfully created");
        errorLabel.getStyleClass().clear();
        errorLabel.getStyleClass().add("text-item-name");
        errorLabel.getStyleClass().add("text-color-success");
        return true;

    }

    /**
     * if the cart is valid creates a order for the active user.
     * decreases the item stock in the database and itemList
     * clears the grid pane
     * @param event
     * @throws SQLException
     */
    @FXML
    void checkoutButtonAction(ActionEvent event) throws SQLException {
        if (cartIsValid()) {
            int orderId = createOrderId();
            for (CartItem item : cart.getItemList()) {
                String insertOrder = "INSERT INTO `order` (orderId, userId, itemId, quantity, status, date) VALUES (?, ?, ?, ?, ?, ?);";
                try {
                    Statement statement = connectDB.createStatement();
                    PreparedStatement ps = connectDB.prepareStatement(insertOrder);
                    ps.setString(1, Integer.toString(orderId));
                    ps.setString(2, Integer.toString(customer.getId()));
                    ps.setString(3, Integer.toString(item.getItem().getId()));
                    ps.setString(4, Integer.toString(item.getQuantity()));
                    ps.setString(5, "0");
                    ps.setString(6, Long.toString(System.currentTimeMillis()));
                    ps.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                    e.getCause();
                }
            }

            String updateStockQuery = "UPDATE `item` SET `stock` = ? WHERE (`idItem` = ?)";
            PreparedStatement ps2 = connectDB.prepareStatement(updateStockQuery);
            for (CartItem item : cart.getItemList()) {
                ps2.setString(1, Integer.toString(item.getItem().getStock() - item.getQuantity()));
                ps2.setString(2, Integer.toString(item.getItem().getId()));
                ps2.executeUpdate();
                item.getItem().decreaseStock(item.getQuantity());
            }
            customer.addNewOrder(new Order(orderId,cart.getItemList(), new Date(System.currentTimeMillis()), 0, customer));
            Helper.clearScreen(gridPane);
            totalPriceLabel.setText("$0");
            customer.getCart().clearCart();
        }
    }

    /**
     * creates new order id different from previous orders.
     * @return new order ID
     * @throws SQLException
     */
    int createOrderId() throws SQLException {
        String query = "SELECT * FROM `order` ORDER BY `orderId` DESC LIMIT 1";
        Statement statement = connectDB.createStatement();
        ResultSet queryResult = statement.executeQuery(query);
        int orderId = 0;
        if (queryResult.next()) {
            orderId = queryResult.getInt("orderId");
            orderId++;
        }
        return orderId;
    }

    /**
     * performs log out operation of the user.
     * @param event
     * @throws Exception
     */
    @FXML
    void logOutButtonAction(ActionEvent event) throws Exception {
        Helper.logOut(logOutButton);
    }


}