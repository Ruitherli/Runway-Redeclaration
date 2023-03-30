package com.example.runwayproject.Controller;

import com.example.runwayproject.Model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SideViewController extends ViewController{

    @FXML
    private Label leftAwayLabel;
    @FXML
    private AnchorPane leftPane;
    @FXML
    private Label leftTowardsLabel;
    @FXML
    private Label rightAwayLabel;
    @FXML
    private AnchorPane rightPane;
    @FXML
    private Label rightTowardsLabel;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Rectangle runway;
    @FXML
    private Rectangle runway1;
    @FXML
    private Button viewLeftButton;
    @FXML
    private Button viewRightButton;





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        leftPane.setVisible(true);
        rightPane.setVisible(false);

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
        //view(runway1,o1,location1,leftPane,rightPane); //scenario 1
        view(runway2,o2,location2,leftPane,rightPane); //scenario 2
        //view(runway2,o3,location3,leftPane,rightPane); //scenario 3
        //view(runway1,o4,location4,leftPane,rightPane); //scenario 4
    }

    public void view(Runway r,Obstacle o,ObstacleLocation ol, AnchorPane pane, AnchorPane pane2){
        setRunway(r,pane);
        setObstacle(r,ol,pane);
        setRunway(r,pane2);
        setObstacle(r,ol,pane2);

        viewLeft(r,o,ol,pane);
        viewRight(r,o,ol,pane2);

        int leftNum = Integer.parseInt(r.getLeftDesignator().getRunwayDesignatorName().substring(0,2));
        int rightNum = Integer.parseInt(r.getRightDesignator().getRunwayDesignatorName().substring(0,2));

        if (rightNum < leftNum){
            flip();
        }

    }

    public void viewLeft(Runway r,Obstacle o,ObstacleLocation ol, AnchorPane pane){
        setRunway(r,pane);
        setObstacle(r,ol,pane);
        drawLeft(Calculator.Status.away, Calculator.Status.over,r,o,ol,pane);
        drawLeft(Calculator.Status.towards, Calculator.Status.towards,r,o,ol,pane);
    }

    public void viewRight(Runway r,Obstacle o,ObstacleLocation ol, AnchorPane pane){
        setRunway(r,pane);
        setObstacle(r,ol,pane);
        drawRight(Calculator.Status.away, Calculator.Status.over,r,o,ol,pane);
        drawRight(Calculator.Status.towards, Calculator.Status.towards,r,o,ol,pane);
    }

    public void drawLeft(Calculator.Status takeOffStatus, Calculator.Status landingStatus, Runway r, Obstacle obs, ObstacleLocation obsLocation, AnchorPane pane) {
        RunwayDesignator left = r.getLeftDesignator();
        RunwayDesignator right = r.getRightDesignator();
        int scale = left.getTora();
        int newTora = Calculator.calcTORA(takeOffStatus, left,obs,obsLocation);
        int newToda = Calculator.calcTODA(takeOffStatus, left,obs,obsLocation);
        int newAsda = Calculator.calcASDA(takeOffStatus, left,obs,obsLocation);
        int newLda = Calculator.calcLDA(landingStatus, left,obs,obsLocation);
        int slopeCalc = (Calculator.slope*obs.getHeight());
        int lineThickness = 6;
        leftTowardsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        leftAwayLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        AnchorPane.setLeftAnchor(leftAwayLabel,10.0);
        AnchorPane.setTopAnchor(leftAwayLabel,10.0);
        AnchorPane.setLeftAnchor(leftTowardsLabel,10.0);
        AnchorPane.setBottomAnchor(leftTowardsLabel,10.0);

        if (newTora < Calculator.minRunDistance){
            if (takeOffStatus == Calculator.Status.away && landingStatus == Calculator.Status.over) {
                leftAwayLabel.setText(left.getRunwayDesignatorName() + " Not suitable for Take off " +takeOffStatus + " / Landing " + landingStatus);
                leftAwayLabel.setTextFill(Color.RED);
            }else{
                leftTowardsLabel.setText(left.getRunwayDesignatorName() + " Not suitable for Take off " +takeOffStatus + " / Landing " + landingStatus);
                leftTowardsLabel.setTextFill(Color.RED);
            }
        }else {
            if (takeOffStatus == Calculator.Status.away && landingStatus == Calculator.Status.over) {
                leftAwayLabel.setText(left.getRunwayDesignatorName() + " Take off AWAY / Landing OVER  ---------->");
                drawLine((double) newTora / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL() + Calculator.blastProtection) / scale, pane, Color.GREEN, 110,lineThickness,("TORA "+newTora)); //tora
                drawLine((double) Calculator.blastProtection / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()) / scale, pane, Color.BLUE, 110,lineThickness,("blast\nprotection "+Calculator.blastProtection)); //blast protection
                drawLine((double) newToda / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL() + Calculator.blastProtection) / scale, pane, Color.RED, 130,lineThickness,("TODA "+newToda)); //toda
                drawLine((double) newAsda / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL() + Calculator.blastProtection) / scale, pane, Color.ORANGE, 150,lineThickness,("ASDA "+newAsda)); //asda
                drawLine((double) newLda / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL() + Calculator.stripEnd + slopeCalc) / scale, pane, Color.PURPLE, 170,lineThickness,("LDA "+newLda)); //lda
                drawLine((double) slopeCalc / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()) / scale, pane, Color.MEDIUMORCHID, 170,lineThickness,("slope "+slopeCalc)); //slope
                drawLine((double) Calculator.stripEnd / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL() + slopeCalc) / scale, pane, Color.STEELBLUE, 170,lineThickness,("strip\nend "+Calculator.stripEnd)); //strip end
            } else {
                leftTowardsLabel.setText(left.getRunwayDesignatorName() + " Take off TOWARDS / Landing TOWARDS  ---------->");
                drawLine((double) newTora / scale, (double) 0 / scale, pane, Color.GREEN, 450,lineThickness,("TORA "+newTora)); //tora
                //draw((double) rd.getDisplacedThres() / rd.getTora(), (double) 0 / rd.getTora(), pane, Color.GREEN, 50); //displaced threshold
                drawLine((double) Calculator.stripEnd / scale, (double) newTora / scale, pane, Color.STEELBLUE, 450,lineThickness,("strip\nend "+Calculator.stripEnd)); //strip end
                drawLine((double) slopeCalc / scale, (double) (newTora + Calculator.stripEnd ) / scale, pane, Color.MEDIUMORCHID, 450,lineThickness,("slope "+slopeCalc)); //slope
                drawLine((double) newToda / scale, (double) 0 / scale, pane, Color.RED, 430,lineThickness,("TODA "+newToda)); //toda
                drawLine((double) newAsda / scale, (double) 0 / scale, pane, Color.ORANGE, 410,lineThickness,("ASDA "+newAsda)); //asda
                drawLine((double) newLda / scale, (double) left.getDisplacedThres() / scale, pane, Color.PURPLE, 390,lineThickness,("LDA "+newLda)); //lda
                drawLine((double) Calculator.stripEnd / scale, (double) (newLda+left.getDisplacedThres()) / scale, pane, Color.STEELBLUE, 390,lineThickness,("strip\nend "+Calculator.stripEnd)); //strip end
                drawLine((double) Calculator.RESA / scale, (double) (newLda +left.getDisplacedThres()+ Calculator.stripEnd) / scale, pane, Color.MAGENTA, 390,lineThickness,("RESA "+Calculator.RESA)); //resa
            }
        }
    }

    public void drawRight(Calculator.Status takeOffStatus, Calculator.Status landingStatus, Runway r, Obstacle obs, ObstacleLocation obsLocation, AnchorPane pane) {
        RunwayDesignator left = r.getLeftDesignator();
        RunwayDesignator right = r.getRightDesignator();
        int scale = right.getTora();
        int newTora = Calculator.calcTORA(takeOffStatus, right,obs,obsLocation);
        int newToda = Calculator.calcTODA(takeOffStatus, right,obs,obsLocation);
        int newAsda = Calculator.calcASDA(takeOffStatus, right,obs,obsLocation);
        int newLda = Calculator.calcLDA(landingStatus, right,obs,obsLocation);
        int slopeCalc = (Calculator.slope*obs.getHeight());
        int lineThickness = 6;
        rightTowardsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        rightAwayLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        AnchorPane.setRightAnchor(rightAwayLabel,10.0);
        AnchorPane.setTopAnchor(rightAwayLabel,10.0);
        AnchorPane.setRightAnchor(rightTowardsLabel,10.0);
        AnchorPane.setBottomAnchor(rightTowardsLabel,10.0);

        if (newTora < Calculator.minRunDistance){
            if (takeOffStatus == Calculator.Status.away && landingStatus == Calculator.Status.over) {
                rightAwayLabel.setText(right.getRunwayDesignatorName() + " Not suitable for Take off " +takeOffStatus + " / Landing " + landingStatus);
                rightAwayLabel.setTextFill(Color.RED);
            }else{
                rightTowardsLabel.setText(right.getRunwayDesignatorName() + " Not suitable for Take off " +takeOffStatus + " / Landing " + landingStatus);
                rightTowardsLabel.setTextFill(Color.RED);
            }
        }else {
            if (takeOffStatus == Calculator.Status.away && landingStatus == Calculator.Status.over) {
                rightAwayLabel.setText("<----------  " + right.getRunwayDesignatorName() + " Take off AWAY / Landing OVER");
                drawLine((double) newTora / scale, (double) 0 / scale, pane, Color.GREEN, 110,lineThickness,("TORA "+newTora)); //tora
                drawLine((double) Calculator.blastProtection / scale, (double) newTora / scale, pane, Color.BLUE, 110,lineThickness,("blast\nprotection "+Calculator.blastProtection)); //blast protection
                drawLine((double) newToda / scale, (double) (newTora-newToda) / scale, pane, Color.RED, 130,lineThickness,("TODA "+newToda)); //toda
                drawLine((double) newAsda / scale, (double) (newTora-newAsda) / scale, pane, Color.ORANGE, 150,lineThickness,("ASDA "+newAsda)); //asda
                drawLine((double) newLda / scale, (double) 0 / scale, pane, Color.PURPLE, 170,lineThickness,("LDA "+newLda)); //lda
                drawLine((double) Calculator.stripEnd / scale, (double) newLda / scale, pane, Color.STEELBLUE, 170,lineThickness,("strip\nend "+Calculator.stripEnd)); //strip end
                drawLine((double) slopeCalc / scale, (double) (newLda+Calculator.stripEnd) / scale, pane, Color.MEDIUMORCHID, 170,lineThickness,("slope "+slopeCalc)); //slope
            } else {
                rightTowardsLabel.setText("<----------  " + right.getRunwayDesignatorName() + " Take off TOWARDS / Landing TOWARDS");
                drawLine((double) newTora / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()+slopeCalc+Calculator.stripEnd)/ scale, pane, Color.GREEN, 450,lineThickness,("TORA "+newTora)); //tora
                drawLine((double) Calculator.stripEnd / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()+slopeCalc) / scale, pane, Color.STEELBLUE, 450,lineThickness,("strip\nend "+Calculator.stripEnd)); //strip end
                drawLine((double) slopeCalc / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL())/ scale, pane, Color.MEDIUMORCHID, 450,lineThickness,("slope "+slopeCalc)); //slope
                drawLine((double) newToda / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()+slopeCalc+Calculator.stripEnd) / scale, pane, Color.RED, 430,lineThickness,("TODA "+newToda)); //toda
                drawLine((double) newAsda / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()+slopeCalc+Calculator.stripEnd) / scale, pane, Color.ORANGE, 410,lineThickness,("ASDA "+newAsda)); //asda
                drawLine((double) newLda / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()+Calculator.RESA+Calculator.stripEnd) / scale, pane, Color.PURPLE, 390,lineThickness,("LDA "+newLda)); //lda
                drawLine((double) Calculator.stripEnd / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()+Calculator.RESA) / scale, pane, Color.STEELBLUE, 390,lineThickness,("strip\nend "+Calculator.stripEnd)); //strip end
                drawLine((double) Calculator.RESA / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()) / scale, pane, Color.MAGENTA, 390,lineThickness,("RESA "+Calculator.RESA)); //resa
            }
        }
    }

    public void setObstacle(Runway r, ObstacleLocation ol, AnchorPane pane) {
        obstacle = new Rectangle(0, 0, 30, 60);
        double startXFraction = (double) ol.getDistanceThresL()/(r.getLeftDesignator().getTora()-r.getLeftDesignator().getDisplacedThres()-r.getRightDesignator().getDisplacedThres());
        double drawnLength = runway.getWidth();
        double scaledLeftDisThres =  (double) r.getLeftDesignator().getDisplacedThres() / r.getLeftDesignator().getTora();
        double scaledRightDisThres = (double) r.getRightDesignator().getDisplacedThres() / r.getLeftDesignator().getTora();
        double startX = runway.getLayoutX() + scaledLeftDisThres*drawnLength;
        double endX = runway.getLayoutX() + runway.getWidth() - (scaledRightDisThres * drawnLength);
        double x = startX + ((endX - startX) * startXFraction) - (obstacle.getWidth() / 2);
        double y = runway.getLayoutY()  - obstacle.getHeight();  // set the Y position of the obstacle just above the runway

        obstacle.setX(x);
        obstacle.setY(y);

        obstacle.setFill(Color.RED);

        pane.getChildren().add(obstacle);
    }

    public void switchPane() {
        // Toggle visibility of pane1 and pane2
        leftPane.setVisible(!leftPane.isVisible());
        rightPane.setVisible(!rightPane.isVisible());
    }

    public void flip(){
        leftPane.setScaleX(-1);
        rightPane.setScaleX(-1);
        leftAwayLabel.setScaleX(-1);
        leftTowardsLabel.setScaleX(-1);
        rightAwayLabel.setScaleX(-1);
        rightTowardsLabel.setScaleX(-1);
        viewLeftButton.setScaleX(-1);
        viewRightButton.setScaleX(-1);


        for (Text t : temporaryText){
            t.setScaleX(-1);
        }
    }

}
