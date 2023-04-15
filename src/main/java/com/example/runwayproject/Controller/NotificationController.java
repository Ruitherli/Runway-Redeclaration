package com.example.runwayproject.Controller;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

//public class NotificationController {
//        public void checkAndSendNotification() {
//            Timeline timeline = new Timeline(new KeyFrame(javafx.util.Duration.seconds(1), event -> {
//                if (AMController.getObstacleAdded()) {
//                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setTitle("Notification");
//                    alert.setHeaderText(null);
//                    alert.setContentText("New Obstacle added, please reload the page");
//                    alert.showAndWait();
//                    AMController.setObstacleAdded(false);
//                    try {
//                      //  switchToATC();
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//                 else if (AMController.getObstacleAdded()) {
//                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setTitle("Notification");
//                    alert.setHeaderText(null);
//                    alert.setContentText("Obstacle Removed, reload the page");
//                    alert.showAndWait();
//                    AMController.setObstacleDeleted(false);
//                    try {
//                   //     switchToATC();
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            }));
//            timeline.setCycleCount(Timeline.INDEFINITE);
//            timeline.play();
//
//        }

     //   public void switchToATC() throws IOException {
//            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/runwayproject/ATC page.fxml")));
//            Scene scene = new Scene(root);
//            Stage stage = (Stage) scene.getWindow();
//            Scene newScene = new Scene(root);
//            //String css = this.getClass().getResource("/design.css").toExternalForm();
//            //newScene.getStylesheets().add(css);
//            stage.setResizable(false);
//            stage.setScene(newScene);
//            stage.show();
//            stage.centerOnScreen();
//
//
//        }

