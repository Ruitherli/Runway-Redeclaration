package com.example.runwayproject.Controller;

import com.example.runwayproject.Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LeftTopDownController extends ViewController{
    @FXML
    private Label leftAwayLabel;
    @FXML
    private Label leftTowardsLabel;
    @FXML
    private Label rightAwayLabel;
    @FXML
    private Label rightTowardsLabel;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Rectangle runway;
    @FXML
    private AnchorPane runwayPane;
    @FXML
    private Button viewRightButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

        viewLeft(runway1,o1,location1,runwayPane);
        //viewLeft(runway2,o2,location2,runwayPane);
        //viewLeft(runway2,o3,location3,runwayPane);
        //viewLeft(runway1,o4,location4,runwayPane);
    }

    public void viewLeft(Runway r,Obstacle o,ObstacleLocation ol, AnchorPane pane){
        setRunway(r,pane);
        setObstacle(r,ol,pane,0);
        drawLeft(Calculator.Status.away, Calculator.Status.over,r,o,ol,pane);
        drawLeft(Calculator.Status.towards, Calculator.Status.towards,r,o,ol,pane);
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
        rightAwayLabel.setText("");
        rightTowardsLabel.setText("");
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
                drawLine((double) newTora / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL() + Calculator.blastProtection) / scale, pane, Color.GREEN, 50,lineThickness,("TORA "+newTora)); //tora
                drawLine((double) Calculator.blastProtection / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()) / scale, pane, Color.BLUE, 50,lineThickness,("blast\nprotection "+Calculator.blastProtection)); //blast protection
                drawLine((double) newToda / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL() + Calculator.blastProtection) / scale, pane, Color.RED, 70,lineThickness,("TODA "+newToda)); //toda
                drawLine((double) newAsda / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL() + Calculator.blastProtection) / scale, pane, Color.ORANGE, 90,lineThickness,("ASDA "+newAsda)); //asda
                drawLine((double) newLda / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL() + Calculator.stripEnd + slopeCalc) / scale, pane, Color.PURPLE, 110,lineThickness,("LDA "+newLda)); //lda
                drawLine((double) slopeCalc / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()) / scale, pane, Color.MEDIUMORCHID, 110,lineThickness,("slope "+slopeCalc)); //slope
                drawLine((double) Calculator.stripEnd / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL() + slopeCalc) / scale, pane, Color.STEELBLUE, 110,lineThickness,("strip\nend "+Calculator.stripEnd)); //strip end
            } else {
                leftTowardsLabel.setText(left.getRunwayDesignatorName() + " Take off TOWARDS / Landing TOWARDS  ---------->");
                drawLine((double) newTora / scale, (double) 0 / scale, pane, Color.GREEN, 360,lineThickness,("TORA "+newTora)); //tora
                //draw((double) rd.getDisplacedThres() / rd.getTora(), (double) 0 / rd.getTora(), pane, Color.GREEN, 50); //displaced threshold
                drawLine((double) Calculator.stripEnd / scale, (double) newTora / scale, pane, Color.STEELBLUE, 360,lineThickness,("strip\nend "+Calculator.stripEnd)); //strip end
                drawLine((double) slopeCalc / scale, (double) (newTora + Calculator.stripEnd ) / scale, pane, Color.MEDIUMORCHID, 360,lineThickness,("slope "+slopeCalc)); //slope
                drawLine((double) newToda / scale, (double) 0 / scale, pane, Color.RED, 340,lineThickness,("TODA "+newToda)); //toda
                drawLine((double) newAsda / scale, (double) 0 / scale, pane, Color.ORANGE, 320,lineThickness,("ASDA "+newAsda)); //asda
                drawLine((double) newLda / scale, (double) left.getDisplacedThres() / scale, pane, Color.PURPLE, 300,lineThickness,("LDA "+newLda)); //lda
                drawLine((double) Calculator.stripEnd / scale, (double) (newLda+left.getDisplacedThres()) / scale, pane, Color.STEELBLUE, 300,lineThickness,("strip\nend "+Calculator.stripEnd)); //strip end
                drawLine((double) Calculator.RESA / scale, (double) (newLda +left.getDisplacedThres()+ Calculator.stripEnd) / scale, pane, Color.MAGENTA, 300,lineThickness,("RESA "+Calculator.RESA)); //resa
            }
        }
    }

    public void switchToRightTopDownView(ActionEvent event) throws IOException {
        selectScene(event, "right top down.fxml");
    }
}

