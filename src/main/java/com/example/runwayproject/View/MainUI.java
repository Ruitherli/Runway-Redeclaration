package com.example.runwayproject.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainUI extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        try{
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/runwayproject/AM page.fxml")));
            primaryStage.setTitle("Runway Redeclaration Tool");
            primaryStage.setResizable(false);
            Scene scene = new Scene(root);

            //Get css stylesheet
            String css = this.getClass().getResource("/com/example/runwayproject/design.css").toExternalForm();
            scene.getStylesheets().add(css);

            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.centerOnScreen();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}

