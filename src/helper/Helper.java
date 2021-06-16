package helper;

import javafx.scene.control.Button;
import model.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Helper {

    static SceneBuilder sb = SceneBuilder.getSceneBuilder();
    static Storage storage = Storage.getStorage();
    static DatabaseConnection connection = new DatabaseConnection();
    static Connection connectDB = connection.getConnection();

    public static void logOut(Button logoutButton) throws Exception {
        sb.closeScene(logoutButton);
        storage.setActiveUser(null);
        storage.setLastLocation(new Stack<>());
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

    public static Item findItem(List<Item> il, int id){
        for(Item i: il){
            if(id == i.getId()) return i;
        }
        return null;
    }
}
