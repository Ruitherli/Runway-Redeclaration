package com.example.runwayproject.Connector;

import javafx.scene.control.Alert;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

    public static void main(String[] args) throws SQLException, FileNotFoundException {
        importDatabase();
    }

    public static void exportDatabase (){
        String dbName = DB_NAME;
        String username = USERNAME;
        String password = PASSWORD;
        String dumpPath = "runway_redeclaration_tool.xml";

        ProcessBuilder builder = new ProcessBuilder(
                "mysqldump",
                "--user=" + username,
                "--password=" + password,
                "--xml", // add the --xml option to dump in XML format
                dbName
        );

        builder.redirectOutput(ProcessBuilder.Redirect.to(new File(dumpPath))); // redirect the output to the specified file

        try {
            Process process = builder.start();
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Backup successful.");
            } else {
                System.err.println("Backup failed. Exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void importDatabase () {

    }
}
