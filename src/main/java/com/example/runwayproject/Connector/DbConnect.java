package com.example.runwayproject.Connector;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnect {
    private static String HOST = "127.0.0.1";
    private static int PORT = 3306;
    private static String DB_NAME = "runway_redeclaration_tool";
    private static String USERNAME = "root";
    private static String PASSWORD = "";
    private static Connection connection;

    public static Connection getConnection() {

        try {
            connection = DriverManager.getConnection(String.format("jdbc:mysql://%s:%d/%s", HOST, PORT, DB_NAME), USERNAME, PASSWORD);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Error: Could not connect to database. The database may be offline or unavailable at this time. Please check your network connection and try again later.");
            alert.showAndWait();
        }

        return connection;
    }
}
