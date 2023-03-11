package com.example.runwayproject.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class ATCController extends SceneController{
    @FXML
    private TableView<?> oriDistanceTable;

    @FXML
    private TableView<?> recDistanceTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        formatTable(oriDistanceTable);
        formatTable(recDistanceTable);

    }

}
