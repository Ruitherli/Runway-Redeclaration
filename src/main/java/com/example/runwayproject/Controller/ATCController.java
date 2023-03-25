package com.example.runwayproject.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

import java.awt.Desktop;
import java.net.URI;


import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.runwayproject.Model.Calculator.*;

public class ATCController extends MainController {


    @FXML
    private MenuItem about;

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
        setConstants();
    }

    public void setConstants () {
        resaText.setText(String.valueOf(RESA));
        stripEndText.setText(String.valueOf(stripEnd));
        blastProtectionText.setText(String.valueOf(blastProtection));
        alsText.setText(String.valueOf(slope));
        tocsText.setText(String.valueOf(slope));
    }

    @FXML
    void hyperlink(ActionEvent event) throws URISyntaxException, IOException {
        System.out.println("opened");
        Desktop.getDesktop().browse(new URI("https://drive.google.com/file/d/1A0YGkIcy6O6BGTx-QHKhXmDhOp5zt4D3/view?usp=sharing"));

    }


}
