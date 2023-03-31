package com.example.runwayproject.Controller;

import com.example.runwayproject.Model.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ViewController extends AMController implements Initializable {

    static Rectangle obstacle;

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

    ArrayList<Rectangle> temporaryRect = new ArrayList<Rectangle>();
    ArrayList<Line> temporaryLine = new ArrayList<Line>();
    ArrayList<Text> temporaryText = new ArrayList<Text>();
    ArrayList<Polygon> temporaryPolygons = new ArrayList<Polygon>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }



    public void drawLine(double lengthFraction, double startXFraction, AnchorPane pane, Color color, int y,int thickness, String message) {

        double runwayLength = runway.getWidth();
        double startX = runway.getLayoutX() + startXFraction * runwayLength ;
        double lineLength = runwayLength * lengthFraction;
        double startY = runway.getY() + y;
        double endX = startX + lineLength;
        double endY = startY;

        if (lineLength>0) {
            Rectangle lengthLine = new Rectangle(startX, startY - thickness/2, lineLength, thickness);
            temporaryRect.add(lengthLine);
            lengthLine.setFill(color);
            lengthLine.toFront();

            Line startMarker = new Line(startX, startY, startX, runway.getLayoutY() + runway.getHeight());
            temporaryLine.add(startMarker);
            Line endMarker = new Line(endX, startY, endX, runway.getLayoutY() + runway.getHeight());
            temporaryLine.add(endMarker);
            pane.getChildren().addAll(lengthLine,startMarker,endMarker);

            Text text = new Text(message);
            temporaryText.add(text);
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

    public void setObstacle(Runway r, ObstacleLocation ol, AnchorPane pane, double distanceFromCenterline) {
        int length = 30;
        int width = 30;
        obstacle = new Rectangle(0, 0, length, width);
        double startXFraction = (double) ol.getDistanceThresL()/(r.getLeftDesignator().getTora()-r.getLeftDesignator().getDisplacedThres()-r.getRightDesignator().getDisplacedThres());
        double drawnLength = runway.getWidth();
        double scaledLeftDisThres =  (double) r.getLeftDesignator().getDisplacedThres() / r.getLeftDesignator().getTora();
        double scaledRightDisThres = (double) r.getRightDesignator().getDisplacedThres() / r.getLeftDesignator().getTora();
        double startX = runway.getLayoutX() + scaledLeftDisThres*drawnLength;
        double endX = runway.getLayoutX() + runway.getWidth() - (scaledRightDisThres * drawnLength);
        double x = startX + ((endX - startX) * startXFraction) - (obstacle.getWidth() / 2);
        //double y = runway.getLayoutY() + (runway.getHeight() - obstacle.getHeight()) / 2;
        double y;
        if (distanceFromCenterline<0) {
            y = runway.getLayoutY() + (runway.getHeight() / 2) - (obstacle.getHeight()) - (distanceFromCenterline * runway.getHeight() / 2);
        }else{
            y = runway.getLayoutY() + (runway.getHeight() / 2) - (obstacle.getHeight()) - (distanceFromCenterline * runway.getHeight() / 2) + obstacle.getHeight();
        }

        obstacle.setX(x);
        obstacle.setY(y);

        obstacle.setFill(Color.RED);

        pane.getChildren().add(obstacle);
    }

    public void setRunway(Runway r, AnchorPane pane){
        double lineThickness = runway.getHeight();

        //get clearway (toda - tora)
        int leftClearway = r.getRightDesignator().getClearway();  //get from right desig. because the length is measured from right desig.
        int rightClearway = r.getLeftDesignator().getClearway();
        //get stopway (asda - tora)
        int leftStopway = r.getRightDesignator().getStopway();  //get from right desig. because the length is measured from right desig.
        int rightStopway = r.getLeftDesignator().getStopway();

        drawLine((double) r.getLeftDesignator().getDisplacedThres() / r.getLeftDesignator().getTora(), (double) 0, pane, Color.SLATEGRAY, (int) ((int) runway.getLayoutY()+lineThickness/2), (int) lineThickness,("displaced\nthreshold "+r.getLeftDesignator().getDisplacedThres())); // right disp thres
        drawLine((double) r.getRightDesignator().getDisplacedThres() / r.getLeftDesignator().getTora(), (1-(double)r.getRightDesignator().getDisplacedThres() / r.getLeftDesignator().getTora()), pane, Color.SLATEGRAY, (int) ((int) runway.getLayoutY()+lineThickness/2), (int) lineThickness,("displaced\nthreshold "+r.getRightDesignator().getDisplacedThres())); // right disp thres

        drawLine((double) leftClearway / r.getLeftDesignator().getTora(), (double) -leftClearway / r.getRightDesignator().getTora(), pane, Color.CYAN, (int) ((int) runway.getLayoutY()+lineThickness/2), (int) lineThickness,("Clearway "+leftClearway)); // left clearway
        drawLine((double) rightClearway / r.getLeftDesignator().getTora(), (double) r.getLeftDesignator().getTora() / r.getLeftDesignator().getTora(), pane, Color.CYAN, (int) ((int) runway.getLayoutY()+lineThickness/2), (int) lineThickness,("Clearway "+rightClearway)); // right clearway

        drawLine((double) leftStopway / r.getLeftDesignator().getTora(), (double) -leftStopway / r.getRightDesignator().getTora(), pane, Color.LAVENDER, (int) ((int) runway.getLayoutY()+lineThickness/2), (int) lineThickness,("Stopway "+leftStopway)); // left stopway
        drawLine((double) rightStopway / r.getLeftDesignator().getTora(), (double) r.getLeftDesignator().getTora() / r.getRightDesignator().getTora(), pane, Color.LAVENDER, (int) ((int) runway.getLayoutY()+lineThickness/2), (int) lineThickness,("Stopway "+rightStopway)); // right clearway

    }

    public void drawSlope(double lengthFraction, double startXFraction, double slopeHeight, AnchorPane pane, Color color, boolean slopeUp,  String message) {
        double runwayLength = runway.getWidth();
        double startX = runway.getLayoutX() + startXFraction * runwayLength;
        double slopeLength = runwayLength * lengthFraction;
        double startY = runway.getLayoutY();
        double endX = startX + slopeLength;
        double endY = startY - slopeHeight;

        Polygon slope = new Polygon(startX, startY, endX, startY, endX, endY);
        if (slopeUp){
            slope.setScaleX(-1);
        }
        temporaryPolygons.add(slope);
        slope.setFill(Color.YELLOWGREEN);
        slope.setOpacity(0.5);
        pane.getChildren().add(slope);

        Text text = new Text(message);
        temporaryText.add(text);
        text.setFont(Font.font("Arial", 10));
        text.setFill(Color.BLACK);
        Bounds lineBounds = slope.getBoundsInParent();
        // Calculate the center point of the slope
        double centerX = (startX + endX) / 2;
        double centerY = (startY + endY) / 2;

        // Adjust the x-coordinate of the text to be the center of the slope
        double textX = centerX - text.getLayoutBounds().getWidth() / 2;
        double textY = centerY - 5; // Adjust the y-coordinate of the text for better alignment

        text.setX(textX);
        text.setY(textY);
        pane.getChildren().add(text);
    }


}
