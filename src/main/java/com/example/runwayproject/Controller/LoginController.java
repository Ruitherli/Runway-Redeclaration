package com.example.runwayproject.Controller;

import com.example.runwayproject.Connector.DbConnect;
import com.example.runwayproject.Model.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController extends MainController{

    @FXML
    private Button atcButton;

    @FXML
    private Button amButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUsernameFormat(usernameField);
        setUsernameFormat(passwordField);
        atcButton.setVisible(false);
        amButton.setVisible(false);
    }

    @FXML
    public void logIn(ActionEvent event) {
        Connection connection = DbConnect.getConnection();

        try{
            //Checks if the entered username and password is registered into the database
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM user WHERE user_name = '"+usernameField.getText()+"' AND password = MD5('"+passwordField.getText()+"');";
            ResultSet resultSet = statement.executeQuery(query);

            //If it exists, it will check for admin privileges and logs in to the system
            User user = new User();
            if(resultSet.next()){
                user.setUsername(resultSet.getString("user_name"));
                user.setPassword(resultSet.getString("password"));
                user.setRoles(User.Roles.valueOf(resultSet.getString("role")));

                String role = String.valueOf(user.getRoles());
                //System.out.println(user.getUsername());
                //System.out.println(user.getPassword());
                //System.out.println(user.getRoles());
                if (role.equals("AM")){
                    switchToAM(event);
                }else if (role.equals("ATC")){
                    switchToATC(event);
                }else if (role.equals("ADMIN")){

                }else{
                    playErrorAlert("No permission granted to this user");
                }

            }else {
                playErrorAlert("Invalid username or password");
                usernameField.clear();
                passwordField.clear();
            }
        }catch (SQLException | IOException e){
            playErrorAlert(String.valueOf(e));
        }
    }

    @FXML
    public void exit (ActionEvent event){

        System.exit(1);
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
