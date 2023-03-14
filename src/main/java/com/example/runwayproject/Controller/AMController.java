package com.example.runwayproject.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class AMController extends MainController {
    @FXML
    private Text alsText;

    @FXML
    private Text blastProtectionText;

    @FXML
    private TextField centerlineTextField;

    @FXML
    private ComboBox<?> directionComboBox;

    @FXML
    private TextField heightTextField;

    @FXML
    private TextField lengthTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private Button objectAddButton;

    @FXML
    private Button objectResetButton;

    @FXML
    private TableView<?> oriDistanceTable;

    @FXML
    private Button presetAddButton;

    @FXML
    private TextField presetCenterlineTextField;

    @FXML
    private ComboBox<?> presetDirectionComboBox;

    @FXML
    private Text presetHeightText;

    @FXML
    private Text presetLengthText;

    @FXML
    private ComboBox<?> presetNameComboBox;

    @FXML
    private Button presetResetButton;

    @FXML
    private TextField presetThresLTextField;

    @FXML
    private TextField presetThresRTextField;

    @FXML
    private Text presetWidthText;

    @FXML
    private TableView<?> recDistanceTable;

    @FXML
    private Text resaText;

    @FXML
    private ComboBox<?> runwayComboBox;

    @FXML
    private TableView<?> runwayTable;

    @FXML
    private TableView<?> runwayObjectTable;

    @FXML
    private TableView<?> objectTable;

    @FXML
    private Text stripEndText;

    @FXML
    private TextField thresLTextField;

    @FXML
    private TextField thresRTextField;

    @FXML
    private Text tocsText;

    @FXML
    private TextField widthTextField;

    @FXML
    private Button AMlogout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        formatTable(runwayTable);
        formatTable(runwayObjectTable);
        formatTable(objectTable);
    }
}
