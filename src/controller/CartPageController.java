package controller;

import helper.DatabaseConnection;
import helper.Helper;
import helper.SceneBuilder;
import helper.Storage;
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

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class CartPageController implements Initializable {

    public static CartPageController cartPageController;

    public static CartPageController getCartPageController() {
        return cartPageController == null ? cartPageController = new CartPageController() : cartPageController;
    }

    @FXML
    private Button backwardButton;

    @FXML
    private Button userButton;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        update();
        setTotalCartPrice();
    }

    public void update() {
        try {
            int size = cart.getItemList().size();
            for (int i = 0; i < size; i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/GUI/cartItemBlock.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();

                CartItemBlockController cartItemBlockController = fxmlLoader.getController();
                cart.getItemList().get(i).setRow(i);
                cartItemBlockController.setCartItem(cart.getItemList().get(i));
                cartItemBlockController.setCartPageController(this);

                gridPane.add(anchorPane, 0, i);
                gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                gridPane.setMaxWidth(Region.USE_PREF_SIZE);

                gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
                gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridPane.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(20));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void setTotalCartPrice() {
        int sum = 0;
        for (CartItem i : cart.getItemList()) {
            sum += i.getItem().getPrice() * i.getQuantity();
        }
        totalPriceLabel.setText("$" + Integer.toString(sum));
    }

    void reArrangeGrid(int deletedRow) {
        gridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) == deletedRow);
    }

    @FXML
    void backwardButtonAction(ActionEvent event) throws Exception {
        Helper.goBackward(backwardButton);
    }

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

    @FXML
    void checkoutButtonAction(ActionEvent event) throws SQLException {
        if (cartIsValid()) {
            String orderId = Integer.toString(createOrderId());
            for (CartItem item : cart.getItemList()) {
                String insertOrder = "INSERT INTO `order` (orderId, userId, itemId, quantity, status, date) VALUES (?, ?, ?, ?, ?, ?);";
                try {
                    Statement statement = connectDB.createStatement();
                    PreparedStatement ps = connectDB.prepareStatement(insertOrder);
                    ps.setString(1, orderId);
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
        }
    }

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

    @FXML
    void logOutButtonAction(ActionEvent event) throws Exception {
        Helper.logOut(logOutButton);
    }

    @FXML
    void userButtonAction(ActionEvent event) {

    }

}