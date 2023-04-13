package com.example.runwayproject.Controller;

import com.example.runwayproject.Connector.DbConnect;
import com.example.runwayproject.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.runwayproject.Connector.DbConnect.checkDatabase;
import static com.example.runwayproject.Connector.DbConnect.createDatabase;

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
        atcButton.setVisible(true);
        amButton.setVisible(true);

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
                    switchToAdmin(event);
                }else{
                    playErrorAlert("No permission granted to this user");
                }

            }else {
                playErrorAlert("Invalid username or password");
                passwordField.clear();
            }
        }catch (SQLException | IOException e){
            playErrorAlert(String.valueOf(e));
        }
    }

    @FXML
    public void exit (ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Exit the software?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == ButtonType.OK){
            System.exit(1);
        }
    }


}
