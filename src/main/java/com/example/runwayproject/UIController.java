package com.example.runwayproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.SocketTimeoutException;


public class UIController {
    //tB == text box
    @FXML
    private TextField tBdistanceFromThreshold;
    @FXML
    private TextField tBdistanceFromCenterline;
    @FXML
    private TextField tBobstacleHeight;


    public void obstacleInfo() {

        //Integer distanceFromThreshold = Integer.valueOf(tBdistanceFromThreshold.getText());
    }

    public void goToCalculatePage() throws IOException {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("calculation.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Breakdown Of Calculations");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ei) {
            ei.printStackTrace();
        }
    }

    public void calculate(ActionEvent event) throws IOException {
           goToCalculatePage();
        }
    }
