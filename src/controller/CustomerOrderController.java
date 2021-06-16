package controller;

import helper.DatabaseConnection;
import helper.Storage;
import model.Administrator;
import model.Customer;
import model.Order;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static model.Administrator.authLevel.HIGH;
import static model.Administrator.authLevel.LOW;

public class CustomerOrderController {

    Storage storage = Storage.getStorage();

    void getOrder(){
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();

        String verifyLogin = "SELECT * FROM order WHERE userId="+storage.getActiveUser().getId();

        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()){
                int orderId = queryResult.getInt("orderId");
            }

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
}
