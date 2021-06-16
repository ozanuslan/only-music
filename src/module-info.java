module only.music {

    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires java.sql;
    requires mysql.connector.java;

    opens GUI;
    opens helper;
    opens controller;
    opens model;
}