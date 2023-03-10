package com.example.runwayproject.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SceneController implements Initializable {

    public Stage stage;
    public Scene scene;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void selectScene(ActionEvent event,String fxml) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/runwayproject/"+fxml)));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        //String css = this.getClass().getResource("/design.css").toExternalForm();
        //scene.getStylesheets().add(css);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public void switchToCalculation (ActionEvent event) throws IOException {
        selectScene(event, "ATC page");
    }

    public void switchToObstacle (ActionEvent event) throws IOException {
        selectScene(event, "ATC page.fxml");
    }
}
