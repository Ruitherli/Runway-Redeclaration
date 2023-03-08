package com.example.runwayproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UIController {

    @FXML
    private TextField heightText;
    @FXML
    private TextField thresholdText;
    @FXML
    private TextField centrelineText;

    public Stage stage;
    public Scene scene;

    public void obstacleInfo() {

        //Integer distanceFromThreshold = Integer.valueOf(tBdistanceFromThreshold.getText());
    }

   public void selectScene(ActionEvent event,String fxml) throws IOException{
       Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));
       stage = (Stage)((Node)event.getSource()).getScene().getWindow();
       scene = new Scene(root);
       stage.setResizable(false);
       stage.setScene(scene);
       stage.show();
       stage.centerOnScreen();
   }

   public void goToCalculatePage (ActionEvent event) throws IOException {
       selectScene(event, "calculation.fxml");
   }

   public void goToObstaclePage (ActionEvent event) throws IOException {
       selectScene(event, "obstacle.fxml");
   }

   @FXML
   private void initialize(){

   }

}
