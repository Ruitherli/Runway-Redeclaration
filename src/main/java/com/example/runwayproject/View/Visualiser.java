package com.example.runwayproject.View;

import com.example.runwayproject.Model.*;
import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Visualiser extends Application{
    Stage stage = new Stage();
    static AnchorPane pane = new AnchorPane();
    static AnchorPane pane2 = new AnchorPane();
    static Scene scene;
    static Scene scene2;
    static boolean isScene1 = true;
    static Rectangle runway;
    static int runwayLength=750;
    static Rectangle obstacle;


    public static void main(String[]args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){

        // Create a button to switch panes
        Button switchButton = new Button("View Right");
        switchButton.setOnAction(event -> switchPanes());
        AnchorPane.setRightAnchor(switchButton,10.0);
        AnchorPane.setTopAnchor(switchButton,300.0);
        pane.getChildren().add(switchButton);

        Button switchButton2 = new Button("View Left");
        switchButton2.setOnAction(event -> switchPanes());
        AnchorPane.setRightAnchor(switchButton2,10.0);
        AnchorPane.setTopAnchor(switchButton2,300.0);
        pane2.getChildren().add(switchButton2);

        // Create a scene with the group as the root node
        scene = new Scene(pane, 1300, 600);
        scene2 = new Scene(pane2,1300,600);
        pane.setPrefWidth(scene.getWidth());
        pane.setPrefHeight(scene.getHeight());
        pane2.setPrefWidth(scene2.getWidth());
        pane2.setPrefHeight(scene2.getHeight());


        RunwayDesignator l1 = new RunwayDesignator(3902, 3902, 3902, 3595, 306,"09L");
        RunwayDesignator r1 = new RunwayDesignator(3884, 3962, 3884, 3884, 0,"27R");
        RunwayDesignator l2 = new RunwayDesignator(3660, 3660, 3660, 3660, 0,"27L");
        RunwayDesignator r2 = new RunwayDesignator(3660, 3660, 3660, 3353, 307,"09R");

        Obstacle o1 = new Obstacle("obs 1",12,10,10);
        ObstacleLocation location1 = new ObstacleLocation(3646,-50,0,ObstacleLocation.Direction.Center);

        Obstacle o2 = new Obstacle("obs 2",25,10,10);
        ObstacleLocation location2 = new ObstacleLocation(2853,500,20,ObstacleLocation.Direction.South);

        Obstacle o3 = new Obstacle("obs 3",15,10,10);
        ObstacleLocation location3 = new ObstacleLocation(150,3203,60,ObstacleLocation.Direction.North);

        Obstacle o4 = new Obstacle("obs 4",20,10,10);
        ObstacleLocation location4 = new ObstacleLocation(50,3546,20,ObstacleLocation.Direction.North);

        Runway runway1 = new Runway("09L/27R", l1, r1);
        Runway runway2 = new Runway("27L/09R", l2, r2);

        /////////  TEST  ///////////
        view(runway1,o1,location1,pane,pane2); //scenario 1
        System.out.println(Calculator.printTORA(Calculator.Status.away,r1,o1,location1));
        System.out.println(Calculator.printTORA(Calculator.Status.towards,r1,o1,location1));
        //Calculator.printTORA(Calculator.Status.away,r1,o1,location1);
        //view(runway2,o2,location2,pane,pane2); //scenario 2
       // view(runway2,o3,location3,pane,pane2); //scenario 3
        //view(runway1,o4,location4,pane,pane2); //scenario 4

        // Set the stage title and scene, and show the stage
        stage.setTitle("Runway with Obstacle");
        stage.setScene(scene);
        stage.show();
    }

    private void switchPanes() {
        if (isScene1) {
            stage.setScene(scene2);
            isScene1 = false;
        } else {
            stage.setScene(scene);
            isScene1 = true;
        }
    }

    /*public static void view(Runway r,Obstacle o,ObstacleLocation ol, AnchorPane pane){
        setRunway(r,pane);
        setObstacle(r,ol,pane);

        if (ol.getDistanceThresL() <= ol.getDistanceThresR()){
            drawLeft(Calculator.Status.away, Calculator.Status.over,r,o,ol,pane);
            drawRight(Calculator.Status.towards, Calculator.Status.towards,r,o,ol,pane);
        }else{
            drawLeft(Calculator.Status.towards, Calculator.Status.towards,r,o,ol,pane);
            drawRight(Calculator.Status.away, Calculator.Status.over,r,o,ol,pane);
        }
    }*/
    public static void view(Runway r,Obstacle o,ObstacleLocation ol, AnchorPane pane, AnchorPane pane2){
        setRunway(r,pane);
        setObstacle(r,ol,pane);
        setRunway(r,pane2);
        setObstacle(r,ol,pane2);

        viewLeft(r,o,ol,pane);
        viewRight(r,o,ol,pane2);
    }

    public static void viewLeft(Runway r,Obstacle o,ObstacleLocation ol, AnchorPane pane){
        setRunway(r,pane);
        setObstacle(r,ol,pane);
        drawLeft(Calculator.Status.away, Calculator.Status.over,r,o,ol,pane);
        drawLeft(Calculator.Status.towards, Calculator.Status.towards,r,o,ol,pane);
    }

    public static void viewRight(Runway r,Obstacle o,ObstacleLocation ol, AnchorPane pane){
        setRunway(r,pane);
        setObstacle(r,ol,pane);
        drawRight(Calculator.Status.away, Calculator.Status.over,r,o,ol,pane);
        drawRight(Calculator.Status.towards, Calculator.Status.towards,r,o,ol,pane);
    }

    /*public static void drawLeft(Calculator.Status takeOffStatus, Calculator.Status landingStatus, Runway r, Obstacle obs, ObstacleLocation obsLocation, AnchorPane pane) {
        RunwayDesignator left = r.getLeftDesignator();
        RunwayDesignator right = r.getRightDesignator();
        int scale = Math.max(left.getTora(), right.getTora());
        int newTora = Calculator.calcTORA(takeOffStatus, left,obs,obsLocation);
        int newToda = Calculator.calcTODA(takeOffStatus, left,obs,obsLocation);
        int newAsda = Calculator.calcASDA(takeOffStatus, left,obs,obsLocation);
        int newLda = Calculator.calcLDA(landingStatus, left,obs,obsLocation);
        int slopeCalc = (Calculator.slope*obs.getHeight());
        int lineThickness = 6;
        // Add the title label
        Label titleLabel = new Label(left.getRunwayDesignatorName() + " Take off " +takeOffStatus + " / Landing " + landingStatus);
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        AnchorPane.setTopAnchor(titleLabel, 10.0);
        AnchorPane.setLeftAnchor(titleLabel, 10.0);
        pane.getChildren().add(titleLabel);

        if (newTora < Calculator.minRunDistance){
            System.out.println("Not suitable for take off");
        }else {
            if (takeOffStatus == Calculator.Status.away && landingStatus == Calculator.Status.over) {
                drawLine((double) newTora / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL() + Calculator.blastProtection) / scale, pane, Color.GREEN, -100,lineThickness,("TORA "+newTora)); //tora
                drawLine((double) Calculator.blastProtection / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()) / scale, pane, Color.BLUE, -100,lineThickness,("blast\nprotection "+Calculator.blastProtection)); //blast protection
                drawLine((double)(left.getDisplacedThres()+obsLocation.getDistanceThresL()) / scale,0,pane,Color.CYAN,-105,lineThickness,"test" );
                drawLine((double) newToda / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL() + Calculator.blastProtection) / scale, pane, Color.RED, -130,lineThickness,("TODA "+newToda)); //toda
                drawLine((double) newAsda / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL() + Calculator.blastProtection) / scale, pane, Color.ORANGE, -160,lineThickness,("ASDA "+newAsda)); //asda
                drawLine((double) newLda / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL() + Calculator.stripEnd + slopeCalc) / scale, pane, Color.PURPLE, -190,lineThickness,("LDA "+newLda)); //lda
                drawLine((double) slopeCalc / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()) / scale, pane, Color.MEDIUMORCHID, -190,lineThickness,("slope "+slopeCalc)); //slope
                drawLine((double) Calculator.stripEnd / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL() + slopeCalc) / scale, pane, Color.STEELBLUE, -190,lineThickness,("strip\nend "+Calculator.stripEnd)); //strip end
            } else {
                drawLine((double) newTora / scale, (double) 0 / scale, pane, Color.GREEN, -100,lineThickness,("TORA "+newTora)); //tora
                //draw((double) rd.getDisplacedThres() / rd.getTora(), (double) 0 / rd.getTora(), pane, Color.GREEN, 50); //displaced threshold
                drawLine((double) Calculator.stripEnd / scale, (double) newTora / scale, pane, Color.STEELBLUE, -100,lineThickness,("strip\nend "+Calculator.stripEnd)); //strip end
                drawLine((double) slopeCalc / scale, (double) (newTora + Calculator.stripEnd ) / scale, pane, Color.MEDIUMORCHID, -100,lineThickness,("slope "+slopeCalc)); //slope
                drawLine((double) newToda / scale, (double) 0 / scale, pane, Color.RED, -130,lineThickness,("TODA "+newToda)); //toda
                drawLine((double) newAsda / scale, (double) 0 / scale, pane, Color.ORANGE, -160,lineThickness,("ASDA "+newAsda)); //asda
                drawLine((double) newLda / scale, (double) left.getDisplacedThres() / scale, pane, Color.PURPLE, -190,lineThickness,("LDA "+newLda)); //lda
                drawLine((double) Calculator.stripEnd / scale, (double) (newLda+left.getDisplacedThres()) / scale, pane, Color.STEELBLUE, -190,lineThickness,("strip\nend "+Calculator.stripEnd)); //strip end
                drawLine((double) Calculator.RESA / scale, (double) (newLda +left.getDisplacedThres()+ Calculator.stripEnd) / scale, pane, Color.MAGENTA, -190,lineThickness,("RESA "+Calculator.RESA)); //resa
            }
        }
    }*/



    public static void drawLeft(Calculator.Status takeOffStatus, Calculator.Status landingStatus, Runway r, Obstacle obs, ObstacleLocation obsLocation, AnchorPane pane) {
        RunwayDesignator left = r.getLeftDesignator();
        RunwayDesignator right = r.getRightDesignator();
        int scale = left.getTora();
        int newTora = Calculator.calcTORA(takeOffStatus, left,obs,obsLocation);
        int newToda = Calculator.calcTODA(takeOffStatus, left,obs,obsLocation);
        int newAsda = Calculator.calcASDA(takeOffStatus, left,obs,obsLocation);
        int newLda = Calculator.calcLDA(landingStatus, left,obs,obsLocation);
        int slopeCalc = (Calculator.slope*obs.getHeight());
        int lineThickness = 6;
        // Add the title label
        //Label titleLabel = new Label(left.getRunwayDesignatorName() + " Take off " +takeOffStatus + " / Landing " + landingStatus);
        Label titleLabel = new Label();
        if (takeOffStatus == Calculator.Status.away && landingStatus == Calculator.Status.over){
            titleLabel.setText(left.getRunwayDesignatorName() + " Take off AWAY / Landing OVER  ---------->");
        }else{
            titleLabel.setText(left.getRunwayDesignatorName() + " Take off TOWARDS / Landing TOWARDS  ---------->");
        }

        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        pane.getChildren().add(titleLabel);

        if (newTora < Calculator.minRunDistance){
            titleLabel.setText(left.getRunwayDesignatorName() + " Not suitable for Take off " +takeOffStatus + " / Landing " + landingStatus);
            titleLabel.setTextFill(Color.RED);
            if (takeOffStatus == Calculator.Status.away && landingStatus == Calculator.Status.over) {
                AnchorPane.setTopAnchor(titleLabel, 10.0);
                AnchorPane.setLeftAnchor(titleLabel, 10.0);
            }else{
                AnchorPane.setBottomAnchor(titleLabel, 10.0);
                AnchorPane.setLeftAnchor(titleLabel, 10.0);
            }
        }else {
            if (takeOffStatus == Calculator.Status.away && landingStatus == Calculator.Status.over) {
                AnchorPane.setTopAnchor(titleLabel, 10.0);
                AnchorPane.setLeftAnchor(titleLabel, 10.0);
                drawLine((double) newTora / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL() + Calculator.blastProtection) / scale, pane, Color.GREEN, -100,lineThickness,("TORA "+newTora)); //tora
                drawLine((double) Calculator.blastProtection / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()) / scale, pane, Color.BLUE, -100,lineThickness,("blast\nprotection "+Calculator.blastProtection)); //blast protection
                drawLine((double) newToda / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL() + Calculator.blastProtection) / scale, pane, Color.RED, -130,lineThickness,("TODA "+newToda)); //toda
                drawLine((double) newAsda / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL() + Calculator.blastProtection) / scale, pane, Color.ORANGE, -160,lineThickness,("ASDA "+newAsda)); //asda
                drawLine((double) newLda / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL() + Calculator.stripEnd + slopeCalc) / scale, pane, Color.PURPLE, -190,lineThickness,("LDA "+newLda)); //lda
                drawLine((double) slopeCalc / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()) / scale, pane, Color.MEDIUMORCHID, -190,lineThickness,("slope "+slopeCalc)); //slope
                drawLine((double) Calculator.stripEnd / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL() + slopeCalc) / scale, pane, Color.STEELBLUE, -190,lineThickness,("strip\nend "+Calculator.stripEnd)); //strip end
            } else {
                drawLine((double) newTora / scale, (double) 0 / scale, pane, Color.GREEN, 100,lineThickness,("TORA "+newTora)); //tora
                //draw((double) rd.getDisplacedThres() / rd.getTora(), (double) 0 / rd.getTora(), pane, Color.GREEN, 50); //displaced threshold
                drawLine((double) Calculator.stripEnd / scale, (double) newTora / scale, pane, Color.STEELBLUE, 100,lineThickness,("strip\nend "+Calculator.stripEnd)); //strip end
                drawLine((double) slopeCalc / scale, (double) (newTora + Calculator.stripEnd ) / scale, pane, Color.MEDIUMORCHID, 100,lineThickness,("slope "+slopeCalc)); //slope
                drawLine((double) newToda / scale, (double) 0 / scale, pane, Color.RED, 130,lineThickness,("TODA "+newToda)); //toda
                drawLine((double) newAsda / scale, (double) 0 / scale, pane, Color.ORANGE, 160,lineThickness,("ASDA "+newAsda)); //asda
                drawLine((double) newLda / scale, (double) left.getDisplacedThres() / scale, pane, Color.PURPLE, 190,lineThickness,("LDA "+newLda)); //lda
                drawLine((double) Calculator.stripEnd / scale, (double) (newLda+left.getDisplacedThres()) / scale, pane, Color.STEELBLUE, 190,lineThickness,("strip\nend "+Calculator.stripEnd)); //strip end
                drawLine((double) Calculator.RESA / scale, (double) (newLda +left.getDisplacedThres()+ Calculator.stripEnd) / scale, pane, Color.MAGENTA, 190,lineThickness,("RESA "+Calculator.RESA)); //resa
                AnchorPane.setBottomAnchor(titleLabel, 10.0);
                AnchorPane.setLeftAnchor(titleLabel, 10.0);
            }
        }
    }

    /*public static void drawRight(Calculator.Status takeOffStatus, Calculator.Status landingStatus, Runway r, Obstacle obs, ObstacleLocation obsLocation, AnchorPane pane) {
        RunwayDesignator left = r.getLeftDesignator();
        RunwayDesignator right = r.getRightDesignator();
        int scale = Math.max(left.getTora(), right.getTora());
        int newTora = Calculator.calcTORA(takeOffStatus, right,obs,obsLocation);
        int newToda = Calculator.calcTODA(takeOffStatus, right,obs,obsLocation);
        int newAsda = Calculator.calcASDA(takeOffStatus, right,obs,obsLocation);
        int newLda = Calculator.calcLDA(landingStatus, right,obs,obsLocation);
        int slopeCalc = (Calculator.slope*obs.getHeight());
        int lineThickness = 6;

        // Add the title label
        Label titleLabel = new Label(right.getRunwayDesignatorName() + " Take off " +takeOffStatus + " / Landing " + landingStatus);
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        AnchorPane.setBottomAnchor(titleLabel, 10.0);
        AnchorPane.setRightAnchor(titleLabel, 10.0);
        pane.getChildren().add(titleLabel);

        if (newTora < Calculator.minRunDistance){
            System.out.println("Not suitable for take off");
        }else {
            if (takeOffStatus == Calculator.Status.away && landingStatus == Calculator.Status.over) {
                drawLine((double) newTora / scale, (double) 0 / scale, pane, Color.GREEN, 100,lineThickness,("TORA "+newTora)); //tora
                drawLine((double) Calculator.blastProtection / scale, (double) newTora / scale, pane, Color.BLUE, 100,lineThickness,("blast\nprotection "+Calculator.blastProtection)); //blast protection
                drawLine((double) newToda / scale, (double) (newTora-newToda) / scale, pane, Color.RED, 130,lineThickness,("TODA "+newToda)); //toda
                drawLine((double) newAsda / scale, (double) (newTora-newAsda) / scale, pane, Color.ORANGE, 160,lineThickness,("ASDA "+newAsda)); //asda
                drawLine((double) newLda / scale, (double) 0 / scale, pane, Color.PURPLE, 190,lineThickness,("LDA "+newLda)); //lda
                drawLine((double) Calculator.stripEnd / scale, (double) newLda / scale, pane, Color.STEELBLUE, 190,lineThickness,("strip\nend "+Calculator.stripEnd)); //strip end
                drawLine((double) slopeCalc / scale, (double) (newLda+Calculator.stripEnd) / scale, pane, Color.MEDIUMORCHID, 190,lineThickness,("slope "+slopeCalc)); //slope
            } else {
                drawLine((double) newTora / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()+slopeCalc+Calculator.stripEnd)/ scale, pane, Color.GREEN, 100,lineThickness,("TORA "+newTora)); //tora
                //draw((double) rd.getDisplacedThres() / rd.getTora(), (double) 0 / rd.getTora(), pane, Color.GREEN, 50); //displaced threshold
                drawLine((double) Calculator.stripEnd / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()+slopeCalc) / scale, pane, Color.STEELBLUE, 100,lineThickness,("strip\nend "+Calculator.stripEnd)); //strip end
                drawLine((double) slopeCalc / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL())/ scale, pane, Color.MEDIUMORCHID, 100,lineThickness,("slope "+slopeCalc)); //slope
                drawLine((double) newToda / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()+slopeCalc+Calculator.stripEnd) / scale, pane, Color.RED, 130,lineThickness,("TODA "+newToda)); //toda
                drawLine((double) newAsda / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()+slopeCalc+Calculator.stripEnd) / scale, pane, Color.ORANGE, 160,lineThickness,("ASDA "+newAsda)); //asda
                drawLine((double) newLda / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()+Calculator.RESA+Calculator.stripEnd) / scale, pane, Color.PURPLE, 190,lineThickness,("LDA "+newLda)); //lda
                drawLine((double) Calculator.stripEnd / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()+Calculator.RESA) / scale, pane, Color.STEELBLUE, 190,lineThickness,("strip\nend "+Calculator.stripEnd)); //strip end
                drawLine((double) Calculator.RESA / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()) / scale, pane, Color.MAGENTA, 190,lineThickness,("RESA "+Calculator.RESA)); //resa
            }
        }
    }*/

    public static void drawRight(Calculator.Status takeOffStatus, Calculator.Status landingStatus, Runway r, Obstacle obs, ObstacleLocation obsLocation, AnchorPane pane) {
        RunwayDesignator left = r.getLeftDesignator();
        RunwayDesignator right = r.getRightDesignator();
        int scale = right.getTora();
        int newTora = Calculator.calcTORA(takeOffStatus, right,obs,obsLocation);
        int newToda = Calculator.calcTODA(takeOffStatus, right,obs,obsLocation);
        int newAsda = Calculator.calcASDA(takeOffStatus, right,obs,obsLocation);
        int newLda = Calculator.calcLDA(landingStatus, right,obs,obsLocation);
        int slopeCalc = (Calculator.slope*obs.getHeight());
        int lineThickness = 6;
        // Add the title label
        Label titleLabel = new Label();
        if (takeOffStatus == Calculator.Status.away && landingStatus == Calculator.Status.over){
            titleLabel.setText("<----------  " + right.getRunwayDesignatorName() + " Take off AWAY / Landing OVER");
        }else{
            titleLabel.setText("<----------  " + right.getRunwayDesignatorName() + " Take off TOWARDS / Landing TOWARDS");
        }
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        pane.getChildren().add(titleLabel);

        if (newTora < Calculator.minRunDistance){
            titleLabel.setText(right.getRunwayDesignatorName() + " Not suitable for Take off " +takeOffStatus + " / Landing " + landingStatus);
            titleLabel.setTextFill(Color.RED);
            if (takeOffStatus == Calculator.Status.away && landingStatus == Calculator.Status.over) {
                AnchorPane.setTopAnchor(titleLabel, 10.0);
                AnchorPane.setRightAnchor(titleLabel, 10.0);
            }else{
                AnchorPane.setBottomAnchor(titleLabel, 10.0);
                AnchorPane.setRightAnchor(titleLabel, 10.0);
            }
        }else {
            if (takeOffStatus == Calculator.Status.away && landingStatus == Calculator.Status.over) {
                AnchorPane.setTopAnchor(titleLabel, 10.0);
                AnchorPane.setRightAnchor(titleLabel, 10.0);
                drawLine((double) newTora / scale, (double) 0 / scale, pane, Color.GREEN, -100,lineThickness,("TORA "+newTora)); //tora
                drawLine((double) Calculator.blastProtection / scale, (double) newTora / scale, pane, Color.BLUE, -100,lineThickness,("blast\nprotection "+Calculator.blastProtection)); //blast protection
                drawLine((double) newToda / scale, (double) (newTora-newToda) / scale, pane, Color.RED, -130,lineThickness,("TODA "+newToda)); //toda
                drawLine((double) newAsda / scale, (double) (newTora-newAsda) / scale, pane, Color.ORANGE, -160,lineThickness,("ASDA "+newAsda)); //asda
                drawLine((double) newLda / scale, (double) 0 / scale, pane, Color.PURPLE, -190,lineThickness,("LDA "+newLda)); //lda
                drawLine((double) Calculator.stripEnd / scale, (double) newLda / scale, pane, Color.STEELBLUE, -190,lineThickness,("strip\nend "+Calculator.stripEnd)); //strip end
                drawLine((double) slopeCalc / scale, (double) (newLda+Calculator.stripEnd) / scale, pane, Color.MEDIUMORCHID, -190,lineThickness,("slope "+slopeCalc)); //slope
            } else {
                drawLine((double) newTora / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()+slopeCalc+Calculator.stripEnd)/ scale, pane, Color.GREEN, 100,lineThickness,("TORA "+newTora)); //tora
                drawLine((double) Calculator.stripEnd / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()+slopeCalc) / scale, pane, Color.STEELBLUE, 100,lineThickness,("strip\nend "+Calculator.stripEnd)); //strip end
                drawLine((double) slopeCalc / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL())/ scale, pane, Color.MEDIUMORCHID, 100,lineThickness,("slope "+slopeCalc)); //slope
                drawLine((double) newToda / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()+slopeCalc+Calculator.stripEnd) / scale, pane, Color.RED, 130,lineThickness,("TODA "+newToda)); //toda
                drawLine((double) newAsda / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()+slopeCalc+Calculator.stripEnd) / scale, pane, Color.ORANGE, 160,lineThickness,("ASDA "+newAsda)); //asda
                drawLine((double) newLda / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()+Calculator.RESA+Calculator.stripEnd) / scale, pane, Color.PURPLE, 190,lineThickness,("LDA "+newLda)); //lda
                drawLine((double) Calculator.stripEnd / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()+Calculator.RESA) / scale, pane, Color.STEELBLUE, 190,lineThickness,("strip\nend "+Calculator.stripEnd)); //strip end
                drawLine((double) Calculator.RESA / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()) / scale, pane, Color.MAGENTA, 190,lineThickness,("RESA "+Calculator.RESA)); //resa
                AnchorPane.setBottomAnchor(titleLabel, 10.0);
                AnchorPane.setRightAnchor(titleLabel, 10.0);
            }
        }
    }


    public static void drawLine(double lengthFraction, double startXFraction, AnchorPane pane, Color color, int y,int thickness, String message) {

        double runwayLength = 750;
        double startX = runway.getX() + startXFraction * runwayLength ;
        double lineLength = runwayLength * lengthFraction;
        double startY = runway.getY() + y;
        double endX = startX + lineLength;
        double endY = startY;

        if (lineLength>0) {
            Rectangle lengthLine = new Rectangle(startX, startY - thickness/2, lineLength, thickness);
            lengthLine.setFill(color);
            lengthLine.toFront();

            Line startMarker = new Line(startX, startY, startX, runway.getY() + runway.getHeight());
            Line endMarker = new Line(endX, startY, endX, runway.getY() + runway.getHeight());
            pane.getChildren().addAll(lengthLine,startMarker,endMarker);

            Text text = new Text(message);
            text.setFont(Font.font("Arial", 10));
            text.setFill(color);
            Bounds lineBounds = lengthLine.getBoundsInParent();
            double textX = lineBounds.getMinX() + (lineBounds.getWidth() - text.getLayoutBounds().getWidth()) / 2;
            double textY = lineBounds.getMaxY() + 10;
            text.setX(textX);
            text.setY(textY);
            pane.getChildren().add(text);
        }else{
            return;
        }
    }



    public static void setObstacle(Runway r, ObstacleLocation ol, AnchorPane pane) {
        obstacle = new Rectangle(0, 0, 30, 60);
        double startXFraction = (double) ol.getDistanceThresL()/(r.getLeftDesignator().getTora()-r.getLeftDesignator().getDisplacedThres()-r.getRightDesignator().getDisplacedThres());
        double drawnLength = runway.getWidth();
        double scaledLeftDisThres =  (double) r.getLeftDesignator().getDisplacedThres() / r.getLeftDesignator().getTora();
        double scaledRightDisThres = (double) r.getRightDesignator().getDisplacedThres() / r.getLeftDesignator().getTora();
        double startX = runway.getX() + scaledLeftDisThres*drawnLength;
        double endX = runway.getX() + runway.getWidth() - (scaledRightDisThres * drawnLength);
        double x = startX + ((endX - startX) * startXFraction) - (obstacle.getWidth() / 2);
        double y = runway.getY()  - obstacle.getHeight();  // set the Y position of the obstacle just above the runway

        obstacle.setX(x);
        obstacle.setY(y);

        obstacle.setFill(Color.RED);

        pane.getChildren().add(obstacle);
    }


    public static void setRunway(Runway r, AnchorPane pane){
        int lineThickness = 14;

        //get clearway (toda - tora)
        int leftClearway = r.getRightDesignator().getClearway();  //get from right desig. because the length is measured from right desig.
        int rightClearway = r.getLeftDesignator().getClearway();
        //get stopway (asda - tora)
        int leftStopway = r.getRightDesignator().getStopway();  //get from right desig. because the length is measured from right desig.
        int rightStopway = r.getLeftDesignator().getStopway();

        double centerX = pane.getWidth() / 2.0;
        double centerY = pane.getHeight() / 2.0;

        double runwayLength = 750; //set the length of the runway
        double runwayWidth = lineThickness; //set the width of the runway

        //calculate the coordinates of the top-left corner of the runway rectangle
        double runwayX = centerX - runwayLength/2;
        double runwayY = centerY - runwayWidth/2;

        //create a rectangle to represent the runway
        runway = new Rectangle(runwayX, runwayY, runwayLength, runwayWidth);
        runway.setStroke(Color.BLACK); //set the stroke color
        runway.setFill(Color.LIGHTGRAY); //set the fill color

        pane.getChildren().add(runway); //add the runway rectangle to the pane
        //showing displaced threshold
        drawLine((double) r.getLeftDesignator().getDisplacedThres() / r.getLeftDesignator().getTora(), (double) 0, pane, Color.SLATEGRAY, (int) runway.getLayoutY()+lineThickness/2,lineThickness,("displaced\nthreshold "+r.getLeftDesignator().getDisplacedThres())); // right disp thres
        drawLine((double) r.getRightDesignator().getDisplacedThres() / r.getLeftDesignator().getTora(), (1-(double)r.getRightDesignator().getDisplacedThres() / r.getLeftDesignator().getTora()), pane, Color.SLATEGRAY, (int) runway.getLayoutY()+lineThickness/2,lineThickness,("displaced\nthreshold "+r.getRightDesignator().getDisplacedThres())); // right disp thres

        Text leftText = new Text(centerX - 400, centerY - 20, r.getLeftDesignator().getRunwayDesignatorName());
        Text rightText = new Text(centerX + 380, centerY - 20, r.getRightDesignator().getRunwayDesignatorName());
        pane.getChildren().addAll(leftText, rightText);

        drawLine((double) leftClearway / r.getLeftDesignator().getTora(), (double) -leftClearway / r.getRightDesignator().getTora(), pane, Color.CYAN, (int) runway.getLayoutY()+lineThickness/2,lineThickness,("Clearway "+leftClearway)); // left clearway
        drawLine((double) rightClearway / r.getLeftDesignator().getTora(), (double) r.getLeftDesignator().getTora() / r.getLeftDesignator().getTora(), pane, Color.CYAN, (int) runway.getLayoutY()+lineThickness/2,lineThickness,("Clearway "+rightClearway)); // right clearway

        drawLine((double) leftStopway / r.getLeftDesignator().getTora(), (double) -leftStopway / r.getRightDesignator().getTora(), pane, Color.LAVENDER, (int) runway.getLayoutY()+lineThickness/2,lineThickness,("Stopway "+leftStopway)); // left stopway
        drawLine((double) rightStopway / r.getLeftDesignator().getTora(), (double) r.getLeftDesignator().getTora() / r.getRightDesignator().getTora(), pane, Color.LAVENDER, (int) runway.getLayoutY()+lineThickness/2,lineThickness,("Stopway "+rightStopway)); // right clearway

    }


}
