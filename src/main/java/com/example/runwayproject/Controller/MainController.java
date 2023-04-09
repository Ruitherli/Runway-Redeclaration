package com.example.runwayproject.Controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    public Stage stage;
    public Scene scene;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void formatTable(TableView<?> table){
        for (TableColumn column : table.getColumns()) {
            column.setResizable(false);
            column.setReorderable(false);
        }
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

    public void switchToATC (ActionEvent event) throws IOException {
        try {
            selectScene(event, "ATC page.fxml");
        }catch (Exception e){
            //Do nothing
        }
    }

    public void switchToAM (ActionEvent event) throws IOException {
        try {
            selectScene(event, "AM page.fxml");
        }catch (Exception e){
            //Do nothing
        }
    }

    public void switchToAdmin (ActionEvent event) throws IOException {
        try {
            selectScene(event, "ADMIN page.fxml");
        }catch (Exception e){
            //Do nothing
        }
    }
    public void switchToLoginPage (ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Are you sure you want to logout? Any unsaved changes will be lost.");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == ButtonType.OK){
            selectScene(event, "login.fxml");
        }

    }

    public void playErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void playWarningAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void playInformationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    /*public void setNumericFormat(TextField textField){
        //Only allow numerical values to be typed into the textfield
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {{
                try{
                    if (!t1.matches("^-?\\d+(\\.\\d+)*$")) {
                        textField.setText(t1.replaceAll("[^\\d\\-\\.]", ""));
                    }
                }catch (Exception ignored){

                }
            }
            }
        });
    }*/

    public void setNumericFormat(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("-?\\d*")) {
                textField.setText(oldValue);
            }
        });
    }

    public void setIntegerFormat(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(oldValue);
            }
        });
    }
    public static void setUsernameFormat(TextField textField) {
        textField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[a-zA-Z0-9!#$%&()*?@^~]*")) {
                return change;
            }
            return null;
        }));

        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.matches("[a-zA-Z0-9!#$%&()*?@^~]*")) {
                    textField.setText(oldValue);
                }
            }
        });
    }
}
