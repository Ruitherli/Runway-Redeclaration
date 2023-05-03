package com.example.runwayproject.View;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

import static com.example.runwayproject.Connector.DbConnect.checkDatabase;
import static com.example.runwayproject.Connector.DbConnect.createDatabase;

public class MainUI extends Application {
    Connection connection = null;
    @Override
    public void start(Stage primaryStage) throws IOException, SQLException {
        if (checkDatabase() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Error: Could not connect to database. The database may be offline or unavailable at this time. Please check your network connection and try again later.");
            alert.showAndWait();
        } else if (checkDatabase() == -1) {

            //Create database and add default admin
            createDatabase();
            //Switch to admin page
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/runwayproject/ADMIN page.fxml")));
                primaryStage.setTitle("Runway Redeclaration Tool");
                primaryStage.setResizable(false);
                Scene scene = new Scene(root);

                //Get css stylesheet
                String css = this.getClass().getResource("/com/example/runwayproject/design.css").toExternalForm();
                scene.getStylesheets().add(css);

                primaryStage.setScene(scene);
                primaryStage.show();
                primaryStage.centerOnScreen();
            } catch (Exception e) {
                //Do nothing
            }
        } else {
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/runwayproject/login.fxml")));
                primaryStage.setTitle("Runway Redeclaration Tool");
                primaryStage.setResizable(false);
                Scene scene = new Scene(root);
                //Get css stylesheet
                String css = this.getClass().getResource("/com/example/runwayproject/design.css").toExternalForm();
                scene.getStylesheets().add(css);

                primaryStage.setScene(scene);
                primaryStage.show();
                primaryStage.centerOnScreen();

            } catch (Exception e) {
                //Do nothing
            }
        }
    }

    public static void main(String[] args) {
        launch();

    }
}

