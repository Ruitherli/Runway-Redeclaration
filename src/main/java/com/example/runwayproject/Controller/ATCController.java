package com.example.runwayproject.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ATCController extends MainController {

    @FXML
    private Text alsText;

    @FXML
    private Text blastProtectionText;

    @FXML
    private Text centerlineText;

    @FXML
    private Text directionText;

    @FXML
    private Text heightText;

    @FXML
    private Text lengthText;

    @FXML
    private Text nameText;

    @FXML
    private TableView<?> oriDistanceTable;

    @FXML
    private TableView<?> recDistanceTable;

    @FXML
    private Text resaText;

    @FXML
    private ComboBox<?> runwayComboBox;

    @FXML
    private TableView<?> runwayTable;

    @FXML
    private Text stripEndText;

    @FXML
    private Text thresholdLText;

    @FXML
    private Text thresholdRText;

    @FXML
    private Text tocsText;

    @FXML
    private Text widthText;

    @FXML
    private Button ATClogout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        formatTable(oriDistanceTable);
        formatTable(recDistanceTable);
        formatTable(runwayTable);
    }



}
