package com.example.runwayproject.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
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
        selectScene(event, "ATC page.fxml");
    }

    public void switchToAM (ActionEvent event) throws IOException {
        selectScene(event, "AM page.fxml");
    }

    public void switchToLoginPage (ActionEvent event) throws IOException {
        selectScene(event, "login.fxml");
    }

    public void playErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void playSuccessPopup (String message) {

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


}
