package com.example.runwayproject.View;

import com.example.runwayproject.Model.*;
import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Visualiser extends Application{
    static Scene scene;
    static Line runway;


    public static void main(String[]args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        AnchorPane pane = new AnchorPane();
        // Create a scene with the group as the root node
        scene = new Scene(pane, 1300, 600);
        pane.setPrefWidth(scene.getWidth());
        pane.setPrefHeight(scene.getHeight());

        RunwayDesignator l1 = new RunwayDesignator(3902, 3902, 3902, 3595, 306,"09L");
        RunwayDesignator r1 = new RunwayDesignator(3884, 3962, 3884, 3884, 0,"27R");
        RunwayDesignator l2 = new RunwayDesignator(3660, 3660, 3660, 3660, 0,"27L");
        RunwayDesignator r2 = new RunwayDesignator(3660, 3660, 3660, 3353, 307,"09R");

        Obstacle o1 = new Obstacle("obs 1",12,10,10);
        ObstacleLocation location1 = new ObstacleLocation(3646,-50,0,ObstacleLocation.Direction.Centre);

        Obstacle o2 = new Obstacle("obs 2",25,10,10);
        ObstacleLocation location2 = new ObstacleLocation(2853,500,20,ObstacleLocation.Direction.South);

        Obstacle o3 = new Obstacle("obs 3",15,10,10);
        ObstacleLocation location3 = new ObstacleLocation(150,3203,60,ObstacleLocation.Direction.North);

        Obstacle o4 = new Obstacle("obs 4",20,10,10);
        ObstacleLocation location4 = new ObstacleLocation(50,3546,20,ObstacleLocation.Direction.North);

        Runway runway1 = new Runway("09L/27R", l1, r1);
        Runway runway2 = new Runway("27L/09R", l2, r2);

        /////////  TEST  ///////////
        //view(runway1,o1,location1,pane); //scenario 1
        //view(runway2,o2,location2,pane); //scenario 2
        //view(runway2,o3,location3,pane); //scenario 3
        view(runway1,o4,location4,pane); //scenario 4


        // Set the stage title and scene, and show the stage
        primaryStage.setTitle("Runway with Obstacle");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void view(Runway r,Obstacle o,ObstacleLocation ol, AnchorPane pane){
        setRunway(r,pane);
        setObstacle(r,ol,pane);

        if (ol.getDistanceThresL() <= ol.getDistanceThresR()){
            drawLeft(Calculator.Status.away, Calculator.Status.over,r,o,ol,pane);
            drawRight(Calculator.Status.towards, Calculator.Status.towards,r,o,ol,pane);
        }else{
            drawLeft(Calculator.Status.towards, Calculator.Status.towards,r,o,ol,pane);
            drawRight(Calculator.Status.away, Calculator.Status.over,r,o,ol,pane);
        }

    }

    public static void drawLeft(Calculator.Status takeOffStatus, Calculator.Status landingStatus, Runway r, Obstacle obs, ObstacleLocation obsLocation, AnchorPane pane) {
        RunwayDesignator left = r.getLeftDesignator();
        int newTora = Calculator.calcTORA(takeOffStatus, left,obs,obsLocation);
        int newToda = Calculator.calcTODA(takeOffStatus, left,obs,obsLocation);
        int newAsda = Calculator.calcASDA(takeOffStatus, left,obs,obsLocation);
        int newLda = Calculator.calcLDA(landingStatus, left,obs,obsLocation);
        int slopeCalc = (Calculator.slope*obs.getHeight());
        int lineThickness = 5;
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
                drawLine((double) newTora / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL() + Calculator.blastProtection) / left.getTora(), pane, Color.GREEN, -100,lineThickness,("TORA "+newTora)); //tora
                drawLine((double) Calculator.blastProtection / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()) / left.getTora(), pane, Color.BLUE, -100,lineThickness,("blast\nprotection "+Calculator.blastProtection)); //blast protection
                drawLine((double) newToda / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL() + Calculator.blastProtection) / left.getTora(), pane, Color.RED, -130,lineThickness,("TODA "+newToda)); //toda
                drawLine((double) newAsda / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL() + Calculator.blastProtection) / left.getTora(), pane, Color.ORANGE, -160,lineThickness,("ASDA "+newAsda)); //asda
                drawLine((double) newLda / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL() + Calculator.stripEnd + slopeCalc) / left.getTora(), pane, Color.PURPLE, -190,lineThickness,("LDA "+newLda)); //lda
                drawLine((double) slopeCalc / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()) / left.getTora(), pane, Color.MEDIUMORCHID, -190,lineThickness,("slope "+slopeCalc)); //slope
                drawLine((double) Calculator.stripEnd / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL() + slopeCalc) / left.getTora(), pane, Color.STEELBLUE, -190,lineThickness,("strip\nend "+Calculator.stripEnd)); //strip end
            } else {
                drawLine((double) newTora / left.getTora(), (double) 0 / left.getTora(), pane, Color.GREEN, -100,lineThickness,("TORA "+newTora)); //tora
                //draw((double) rd.getDisplacedThres() / rd.getTora(), (double) 0 / rd.getTora(), pane, Color.GREEN, 50); //displaced threshold
                drawLine((double) Calculator.stripEnd / left.getTora(), (double) newTora / left.getTora(), pane, Color.STEELBLUE, -100,lineThickness,("strip\nend "+Calculator.stripEnd)); //strip end
                drawLine((double) slopeCalc / left.getTora(), (double) (newTora + Calculator.stripEnd ) / left.getTora(), pane, Color.MEDIUMORCHID, -100,lineThickness,("slope "+slopeCalc)); //slope
                drawLine((double) newToda / left.getTora(), (double) 0 / left.getTora(), pane, Color.RED, -130,lineThickness,("TODA "+newToda)); //toda
                drawLine((double) newAsda / left.getTora(), (double) 0 / left.getTora(), pane, Color.ORANGE, -160,lineThickness,("ASDA "+newAsda)); //asda
                drawLine((double) newLda / left.getTora(), (double) left.getDisplacedThres() / left.getTora(), pane, Color.PURPLE, -190,lineThickness,("LDA "+newLda)); //lda
                drawLine((double) Calculator.stripEnd / left.getTora(), (double) (newLda+left.getDisplacedThres()) / left.getTora(), pane, Color.STEELBLUE, -190,lineThickness,("strip\nend "+Calculator.stripEnd)); //strip end
                drawLine((double) Calculator.RESA / left.getTora(), (double) (newLda +left.getDisplacedThres()+ Calculator.stripEnd) / left.getTora(), pane, Color.MAGENTA, -190,lineThickness,("RESA "+Calculator.RESA)); //resa
            }
        }
    }

    public static void drawRight(Calculator.Status takeOffStatus, Calculator.Status landingStatus, Runway r, Obstacle obs, ObstacleLocation obsLocation, AnchorPane pane) {
        RunwayDesignator left = r.getLeftDesignator();
        RunwayDesignator right = r.getRightDesignator();
        int newTora = Calculator.calcTORA(takeOffStatus, right,obs,obsLocation);
        int newToda = Calculator.calcTODA(takeOffStatus, right,obs,obsLocation);
        int newAsda = Calculator.calcASDA(takeOffStatus, right,obs,obsLocation);
        int newLda = Calculator.calcLDA(landingStatus, right,obs,obsLocation);
        int slopeCalc = (Calculator.slope*obs.getHeight());
        int lineThickness = 5;

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
                drawLine((double) newTora / right.getTora(), (double) 0 / right.getTora(), pane, Color.GREEN, 100,lineThickness,("TORA "+newTora)); //tora
                drawLine((double) Calculator.blastProtection / right.getTora(), (double) newTora / right.getTora(), pane, Color.BLUE, 100,lineThickness,("blast\nprotection "+Calculator.blastProtection)); //blast protection
                drawLine((double) newToda / right.getTora(), (double) (newTora-newToda) / right.getTora(), pane, Color.RED, 130,lineThickness,("TODA "+newToda)); //toda
                drawLine((double) newAsda / right.getTora(), (double) (newTora-newAsda) / right.getTora(), pane, Color.ORANGE, 160,lineThickness,("ASDA "+newAsda)); //asda
                drawLine((double) newLda / right.getTora(), (double) 0 / right.getTora(), pane, Color.PURPLE, 190,lineThickness,("LDA "+newLda)); //lda
                drawLine((double) Calculator.stripEnd / right.getTora(), (double) newLda / right.getTora(), pane, Color.STEELBLUE, 190,lineThickness,("strip\nend "+Calculator.stripEnd)); //strip end
                drawLine((double) slopeCalc / right.getTora(), (double) (newLda+Calculator.stripEnd) / right.getTora(), pane, Color.MEDIUMORCHID, 190,lineThickness,("slope "+slopeCalc)); //slope
            } else {
                drawLine((double) newTora / right.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()+slopeCalc+Calculator.stripEnd)/ right.getTora(), pane, Color.GREEN, 100,lineThickness,("TORA "+newTora)); //tora
                //draw((double) rd.getDisplacedThres() / rd.getTora(), (double) 0 / rd.getTora(), pane, Color.GREEN, 50); //displaced threshold
                drawLine((double) Calculator.stripEnd / right.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()+slopeCalc) / right.getTora(), pane, Color.STEELBLUE, 100,lineThickness,("strip\nend "+Calculator.stripEnd)); //strip end
                drawLine((double) slopeCalc / right.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL())/ right.getTora(), pane, Color.MEDIUMORCHID, 100,lineThickness,("slope "+slopeCalc)); //slope
                drawLine((double) newToda / right.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()+slopeCalc+Calculator.stripEnd) / right.getTora(), pane, Color.RED, 130,lineThickness,("TODA "+newToda)); //toda
                drawLine((double) newAsda / right.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()+slopeCalc+Calculator.stripEnd) / right.getTora(), pane, Color.ORANGE, 160,lineThickness,("ASDA "+newAsda)); //asda
                drawLine((double) newLda / right.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()+Calculator.RESA+Calculator.stripEnd) / right.getTora(), pane, Color.PURPLE, 190,lineThickness,("LDA "+newLda)); //lda
                drawLine((double) Calculator.stripEnd / right.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()+Calculator.RESA) / right.getTora(), pane, Color.STEELBLUE, 190,lineThickness,("strip\nend "+Calculator.stripEnd)); //strip end
                drawLine((double) Calculator.RESA / right.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()) / right.getTora(), pane, Color.MAGENTA, 190,lineThickness,("RESA "+Calculator.RESA)); //resa
            }
        }
    }
    public static void drawLine(double lengthFraction, double startXFraction, AnchorPane pane, Color color, int y,int thickness, String message) {
        double runwayLength = runway.getEndX() - runway.getStartX();
        double startX = runway.getStartX() + startXFraction * runwayLength;
        double lineLength = runwayLength * lengthFraction;
        double startY = runway.getStartY() + y;
        double endX = startX + lineLength;
        double endY = startY;

        if (lineLength>0) {
            Line lengthLine = new Line(startX, startY, endX, endY);
            lengthLine.setStroke(color);
            lengthLine.setStrokeWidth(thickness);
            lengthLine.toFront();
            pane.getChildren().add(lengthLine);

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
        Rectangle obstacle = new Rectangle(0, 0, 30, 70);
        double startXFraction = (double) ol.getDistanceThresL()/(r.getLeftDesignator().getTora()-r.getLeftDesignator().getDisplacedThres()-r.getRightDesignator().getDisplacedThres());
        double drawnLength = runway.getEndX() - runway.getStartX();
        double scaledLeftDisThres =  (double) r.getLeftDesignator().getDisplacedThres() / r.getLeftDesignator().getTora();
        double scaledRightDisThres = (double) r.getRightDesignator().getDisplacedThres() / r.getLeftDesignator().getTora();
        double startX = runway.getStartX()+scaledLeftDisThres*drawnLength;
        double endX = runway.getEndX() - (scaledRightDisThres * drawnLength);
        double x= startX + ((endX - startX) * startXFraction) - (obstacle.getWidth() / 2);
        double y = runway.getStartY() - obstacle.getHeight(); // set the Y position of the obstacle just above the runway

        obstacle.setX(x);
        obstacle.setY(y);

        obstacle.setFill(Color.RED); // set the fill color of the rectangle to red

        pane.getChildren().add(obstacle); // add the rectangle to the pane
    }



    public static void setRunway(Runway r, AnchorPane pane){
        int lineThickness = 10;

        //get clearway (toda - tora)
        int leftClearway = r.getRightDesignator().getClearway();  //get from right desig. because the length is measured from right desig.
        int rightClearway = r.getLeftDesignator().getClearway();
        //get stopway (asda - tora)
        int leftStopway = r.getRightDesignator().getStopway();  //get from right desig. because the length is measured from right desig.
        int rightStopway = r.getLeftDesignator().getStopway();


        double centerX = pane.getWidth() / 2.0;
        double centerY = pane.getHeight() / 2.0;
        runway = new Line(centerX - 375, centerY, centerX + 375, centerY);  //represent ori tora
        runway.setStrokeWidth(lineThickness);
        pane.getChildren().add(runway);
        //showing displaced threshold
        drawLine((double) r.getLeftDesignator().getDisplacedThres() / r.getLeftDesignator().getTora(), (double) 0, pane, Color.SLATEGRAY, (int) runway.getLayoutY(),lineThickness,("displaced\nthreshold "+r.getLeftDesignator().getDisplacedThres())); // right disp thres
        drawLine((double) r.getRightDesignator().getDisplacedThres() / r.getLeftDesignator().getTora(), (1-(double)r.getRightDesignator().getDisplacedThres() / r.getLeftDesignator().getTora()), pane, Color.SLATEGRAY, (int) runway.getLayoutY(),lineThickness,("displaced\nthreshold "+r.getRightDesignator().getDisplacedThres())); // right disp thres

        Text leftText = new Text(centerX - 400, centerY - 20, r.getLeftDesignator().getRunwayDesignatorName());
        Text rightText = new Text(centerX + 380, centerY - 20, r.getRightDesignator().getRunwayDesignatorName());
        pane.getChildren().addAll(leftText, rightText);

        drawLine((double) leftClearway / r.getLeftDesignator().getTora(), (double) -leftClearway / r.getRightDesignator().getTora(), pane, Color.CYAN, (int) runway.getLayoutY(),lineThickness,("Clearway "+leftClearway)); // left clearway
        drawLine((double) rightClearway / r.getLeftDesignator().getTora(), (double) r.getLeftDesignator().getTora() / r.getLeftDesignator().getTora(), pane, Color.CYAN, (int) runway.getLayoutY(),lineThickness,("Clearway "+rightClearway)); // right clearway

        drawLine((double) leftStopway / r.getLeftDesignator().getTora(), (double) -leftStopway / r.getRightDesignator().getTora(), pane, Color.LAVENDER, (int) runway.getLayoutY(),lineThickness,("Stopway "+leftStopway)); // left stopway
        drawLine((double) rightStopway / r.getLeftDesignator().getTora(), (double) r.getLeftDesignator().getTora() / r.getRightDesignator().getTora(), pane, Color.LAVENDER, (int) runway.getLayoutY(),lineThickness,("Stopway "+rightStopway)); // right clearway


    }


}
