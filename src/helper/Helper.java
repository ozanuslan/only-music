package helper;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import model.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;
import java.util.regex.*;

public class Helper {

    static SceneBuilder sb = SceneBuilder.getSceneBuilder();
    static Storage storage = Storage.getStorage();
    static DatabaseConnection connection = new DatabaseConnection();
    static Connection connectDB = connection.getConnection();

    public static void logOut(Button logoutButton) throws Exception {
        sb.closeScene(logoutButton);
        storage.setActiveUser(null);
        storage.setLastLocation(new Stack<>());
        storage.setUserList(new ArrayList<>());
        storage.setItemList(new ArrayList<>());
        sb.createScene("login");
    }

    public static void goBackward(Button backwardButton) throws Exception {
        sb.closeScene(backwardButton);
        sb.createScene(storage.popLastLocation());
    }

    public static boolean isPositiveNumber(String s) {
        try {
            int i = Integer.parseInt(s);

            return i >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void clearScreen(GridPane gridPane) {
        gridPane.getChildren().removeIf(node -> true);
    }

    public static ArrayList<Item> getItems() throws SQLException {
        ArrayList<Item> itemList = new ArrayList<>();
        String verifyLogin = "SELECT * FROM item";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()) {
                int id = Integer.parseInt(queryResult.getString("idItem"));
                int stock = Integer.parseInt(queryResult.getString("stock"));
                String name = queryResult.getString("name");
                double price = Double.parseDouble(queryResult.getString("price"));
                String soundPath = "";
                String imagePath = queryResult.getString("imagePath");


                switch (queryResult.getString("type")) {
                    case "Classical Guitar":
                        String bodyMaterial = queryResult.getString("attribute1");
                        String neckMaterial = queryResult.getString("attribute2");
                        int stringNumber = Integer.parseInt(queryResult.getString("attribute3"));
                        String bodyType = queryResult.getString("attribute4");
                        ClassicalGuitar classicalGuitar = new ClassicalGuitar(id, stock, name, price, soundPath, imagePath, bodyMaterial, neckMaterial, stringNumber, bodyType);
                        itemList.add(classicalGuitar);
                        break;
                    case "Electric Guitar":
                        String bodyMaterial1 = queryResult.getString("attribute1");
                        String neckMaterial1 = queryResult.getString("attribute2");
                        String magneticType = queryResult.getString("attribute3");
                        String bridgeType = queryResult.getString("attribute4");
                        ElectricGuitar electricGuitar = new ElectricGuitar(id, stock, name, price, soundPath, imagePath, bodyMaterial1, neckMaterial1, magneticType, bridgeType);
                        itemList.add(electricGuitar);
                        break;
                    case "Piano":
                        int keyNumber = Integer.parseInt(queryResult.getString("attribute1"));
                        String cabinetType = queryResult.getString("attribute2");
                        int width = Integer.parseInt(queryResult.getString("attribute3"));
                        int height = Integer.parseInt(queryResult.getString("attribute4"));
                        Piano piano = new Piano(id, stock, name, price, soundPath, imagePath, keyNumber, cabinetType, width, height);
                        itemList.add(piano);
                        break;
                    case "Acoustic Drum":
                        int snareDrumNumber = Integer.parseInt(queryResult.getString("attribute1"));
                        int bassDrumNumber = Integer.parseInt(queryResult.getString("attribute2"));
                        int tomNumber = Integer.parseInt(queryResult.getString("attribute3"));
                        String shellMaterial = queryResult.getString("attribute4");
                        AcousticDrum acousticDrum = new AcousticDrum(id, stock, name, price, soundPath, imagePath, snareDrumNumber, bassDrumNumber, tomNumber, shellMaterial);
                        itemList.add(acousticDrum);
                        break;
                    case "Electronic Drum":
                        int snareDrumNumber1 = Integer.parseInt(queryResult.getString("attribute1"));
                        int bassDrumNumber1 = Integer.parseInt(queryResult.getString("attribute2"));
                        int tomNumber1 = Integer.parseInt(queryResult.getString("attribute3"));
                        int soundNumber = Integer.parseInt(queryResult.getString("attribute4"));
                        ElectronicDrum electronicDrum = new ElectronicDrum(id, stock, name, price, soundPath, imagePath, snareDrumNumber1, bassDrumNumber1, tomNumber1, soundNumber);
                        itemList.add(electronicDrum);
                        break;
                    case "Violin":
                        String frontCover = queryResult.getString("attribute1");
                        String backCover = queryResult.getString("attribute2");
                        int height1 = Integer.parseInt(queryResult.getString("attribute3"));
                        int stringThick = Integer.parseInt(queryResult.getString("attribute4"));
                        Violin violin = new Violin(id, stock, name, price, soundPath, imagePath, frontCover, backCover, height1, stringThick);
                        itemList.add(violin);
                        break;
                    case "Cello":
                        String frontCover1 = queryResult.getString("attribute1");
                        String backCover1 = queryResult.getString("attribute2");
                        int height2 = Integer.parseInt(queryResult.getString("attribute3"));
                        int stringCount = Integer.parseInt(queryResult.getString("attribute4"));
                        Cello cello = new Cello(id, stock, name, price, soundPath, imagePath, frontCover1, backCover1, height2, stringCount);
                        itemList.add(cello);
                        break;
                    case "Amp":
                        String effects = queryResult.getString("attribute1");
                        int power = Integer.parseInt(queryResult.getString("attribute2"));
                        String type = queryResult.getString("attribute3");
                        int channelNumber = Integer.parseInt(queryResult.getString("attribute4"));
                        Amp amp = new Amp(id, stock, name, price, imagePath, effects, power, type, channelNumber);
                        itemList.add(amp);
                        break;
                    case "Clarinet":
                        String material = queryResult.getString("attribute1");
                        int boreWidth = Integer.parseInt(queryResult.getString("attribute2"));
                        int keyNumber1 = Integer.parseInt(queryResult.getString("attribute3"));
                        String model = queryResult.getString("attribute4");
                        Clarinet clarinet = new Clarinet(id, stock, name, price, soundPath, imagePath, material, boreWidth, keyNumber1, model);
                        itemList.add(clarinet);
                        break;
                    case "Trombone":
                        String material1 = queryResult.getString("attribute1");
                        int boreWidth1 = Integer.parseInt(queryResult.getString("attribute2"));
                        int valveNumber = Integer.parseInt(queryResult.getString("attribute3"));
                        String model1 = queryResult.getString("attribute4");
                        Trombone trombone = new Trombone(id, stock, name, price, soundPath, imagePath, material1, boreWidth1, valveNumber, model1);
                        itemList.add(trombone);
                        break;
                    case "Trumpet":
                        String material2 = queryResult.getString("attribute1");
                        int boreWidth2 = Integer.parseInt(queryResult.getString("attribute2"));
                        String pistonValve = queryResult.getString("attribute3");
                        int bellWidth = Integer.parseInt(queryResult.getString("attribute4"));
                        Trumpet trumpet = new Trumpet(id, stock, name, price, soundPath, imagePath, material2, boreWidth2, pistonValve, bellWidth);
                        itemList.add(trumpet);
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        return itemList;
    }

    public static Item findItem(List<Item> il, int id) {
        for (Item i : il) {
            if (id == i.getId()) return i;
        }
        return null;
    }

    public static User findUser(List<User> ul, int id) {
        for (User u : ul) {
            if (id == u.getId()) return u;
        }
        return null;
    }

    //email validator with regex
    public static boolean isValidEmail(String email) {
        //Regular Expression
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
        //Compile regular expression to get the pattern
        Pattern pattern = Pattern.compile(regex);
        //Create instance of matcher
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidPhone(String phone) {
        //Regular Expression
        String regex = "^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$";
        //Compile regular expression to get the pattern
        Pattern pattern = Pattern.compile(regex);
        //Create instance of matcher
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    public static List<Order> getCustomerOrders() {
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();
        Customer activeUser = (Customer) storage.getActiveUser();
        String orderQuery = "SELECT * FROM `order` WHERE userId=" + activeUser.getId();

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(orderQuery);

            ArrayList<Order> orders = new ArrayList<>();
            ArrayList<CartItem> items = new ArrayList<>();
            if (queryResult.next()) {
                int orderId = queryResult.getInt("orderId");
                long lastDate = queryResult.getLong("date");
                int lastStatus = queryResult.getInt("status");
                if (Helper.findItem(storage.getItemList(), queryResult.getInt("itemId")) != null) {
                    items.add(new CartItem(Helper.findItem(storage.getItemList(), queryResult.getInt("itemId")), queryResult.getInt("quantity")));
                } else {
                    System.out.println("Item id: " + queryResult.getInt("itemId") + " couldn't be found in the item list. Therefore item wasn't added to order id: " + queryResult.getInt("orderId"));
                }
                while (queryResult.next()) {
                    if (queryResult.getInt("orderId") != orderId) {
                        orders.add(new Order(orderId, items, new Date(lastDate), lastStatus, activeUser));
                        items = new ArrayList<>();
                    }
                    orderId = queryResult.getInt("orderId");
                    lastDate = queryResult.getLong("date");
                    lastStatus = queryResult.getInt("status");
                    items.add(new CartItem(Helper.findItem(storage.getItemList(), queryResult.getInt("itemId")), queryResult.getInt("quantity")));
                }
                orders.add(new Order(orderId, items, new Date(lastDate), lastStatus, activeUser));
            }
            return orders;

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return new ArrayList<>();
    }

    public static List<Order> getAllOrders() {
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();
        Customer orderOwner;
        String orderQuery = "SELECT * FROM `order`";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(orderQuery);

            ArrayList<Order> orders = new ArrayList<>();
            ArrayList<CartItem> items = new ArrayList<>();
            if (queryResult.next()) {
                int orderId = queryResult.getInt("orderId");
                long lastDate = queryResult.getLong("date");
                int lastStatus = queryResult.getInt("status");
                int userId = queryResult.getInt("userId");
                if (Helper.findItem(storage.getItemList(), queryResult.getInt("itemId")) != null) {
                    items.add(new CartItem(Helper.findItem(storage.getItemList(), queryResult.getInt("itemId")), queryResult.getInt("quantity")));
                } else {
                    System.out.println("Item id: " + queryResult.getInt("itemId") + " couldn't be found in the item list. Therefore item wasn't added to order id: " + queryResult.getInt("orderId"));
                }
                orderOwner = (Customer) findUser(storage.getUserList(), userId);
                while (queryResult.next()) {
                    if (queryResult.getInt("orderId") != orderId) {
                        orderOwner = (Customer) findUser(storage.getUserList(), userId);
                        orders.add(new Order(orderId, items, new Date(lastDate), lastStatus, orderOwner));
                        items = new ArrayList<>();
                    }
                    userId = queryResult.getInt("userId");
                    orderId = queryResult.getInt("orderId");
                    lastDate = queryResult.getLong("date");
                    lastStatus = queryResult.getInt("status");
                    items.add(new CartItem(Helper.findItem(storage.getItemList(), queryResult.getInt("itemId")), queryResult.getInt("quantity")));
                }
                orderOwner = (Customer) findUser(storage.getUserList(), userId);
                orders.add(new Order(orderId, items, new Date(lastDate), lastStatus, orderOwner));
            }
            return orders;
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return new ArrayList<>();
    }

    public static ArrayList<User> getAllUsers() throws SQLException {
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();

        ArrayList<User> users = new ArrayList<>();
        String verifyLogin = "SELECT * FROM user_account";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()) {
                if (queryResult.getInt("privilegeLevel") == 0) {
                    Customer customer = new Customer(queryResult.getString("username"), queryResult.getString("name"), queryResult.getString("surname"), queryResult.getString("email"), queryResult.getInt("idUser"), queryResult.getInt("privilegeLevel"));
                    setCustomerAddress(customer, connectDB);
                    users.add(customer);
                } else {
                    Administrator admin = new Administrator(queryResult.getString("username"), queryResult.getString("name"),
                            queryResult.getString("surname"), queryResult.getString("email"), queryResult.getInt("idUser"), queryResult.getInt("privilegeLevel") > 1 ? 2 : 1);
                    users.add(admin);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return users;
    }

    public static void setCustomerAddress(Customer customer, Connection connectDB) throws SQLException {
        Statement statement = connectDB.createStatement();
        String addressQuery = "SELECT * FROM `address` where idUser= " + customer.getId();
        ResultSet queryResult = statement.executeQuery(addressQuery);

        while (queryResult.next()) {
            Address address = new Address(queryResult.getString("city"), queryResult.getString("province"), queryResult.getString("address"), queryResult.getString("phone"), Integer.parseInt(queryResult.getString("postCode")));
            customer.setAddress(address);
        }
    }
}
