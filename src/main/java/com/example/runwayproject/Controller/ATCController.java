package com.example.runwayproject.Controller;

import com.example.runwayproject.Connector.DbConnect;
import com.example.runwayproject.Model.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.TabPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import javax.imageio.ImageIO;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import java.util.ArrayList;


import static com.example.runwayproject.Model.Calculator.*;

public class ATCController extends MainController {

    @FXML
    private Button recentreButton;

    @FXML
    private Label rotateLabel;

    @FXML
    private Label zoomLabel;

    @FXML
    private Label rotationDegreeLabel;

    @FXML
    private Label zoomScaleLabel;

    @FXML
    private MenuItem screenshotButton;

    @FXML
    private AnchorPane middlePane;

    @FXML
    private Text alsText;

    @FXML
    private Text blastProtectionText;

    @FXML
    private Text centerlineText;

    @FXML
    private Text directionText;

    @FXML
    private Text heightText;

    @FXML
    private Text lengthText;

    @FXML
    private Text nameText;

    @FXML
    private TableView<OriTable> oriDistanceTable;

    @FXML
    private TableView<RecTable> recAwayDistanceTable;

    @FXML
    private TableView<RecTable> recTowardDistanceTable;

    @FXML
    private Text resaText;

    @FXML
    private ComboBox<String> runwayComboBox;

    @FXML
    private TableView<RunwayTable> runwayTable;

    @FXML
    private Text stripEndText;

    @FXML
    private Text thresholdLText;

    @FXML
    private Text thresholdRText;

    @FXML
    private Text tocsText;

    @FXML
    private Text widthText;

    @FXML
    private Button ATClogout;

    @FXML
    private Button ATCprintTXT;

    @FXML
    private Label airportNameSV;

    @FXML
    private Label airportNameTD;

    @FXML
    private Button exportButton;

    @FXML
    private Button TopDownExport;

    @FXML
    private TableColumn<RunwayTable, String> runDesigCol;

    @FXML
    private TableColumn<RunwayTable, Integer> clearwayCol;

    @FXML
    private TableColumn<RunwayTable, Integer> stopwayCol;

    @FXML
    private TableColumn<RunwayTable, Integer> thresholdCol;

    @FXML
    private TableColumn<OriTable, String> oriDesigCol;

    @FXML
    private TableColumn<OriTable, Integer> oriToraCol;

    @FXML
    private TableColumn<OriTable, Integer> oriTodaCol;

    @FXML
    private TableColumn<OriTable, Integer> oriLdaCol;

    @FXML
    private TableColumn<OriTable, Integer> oriAsdaCol;

    @FXML
    private TableColumn<RecTable, String> awayDesigCol;

    @FXML
    private TableColumn<RecTable, Integer> awayToraCol;

    @FXML
    private TableColumn<RecTable, Integer> awayTodaCol;

    @FXML
    private TableColumn<RecTable, Integer> awayAsdaCol;

    @FXML
    private TableColumn<RecTable, Integer> awayLdaCol;

    @FXML
    private TableColumn<RecTable, String> towardDesigCol;

    @FXML
    private TableColumn<RecTable, Integer> towardToraCol;

    @FXML
    private TableColumn<RecTable, Integer> towardTodaCol;

    @FXML
    private TableColumn<RecTable, Integer> towardAsdaCol;

    @FXML
    private TableColumn<RecTable, Integer> towardLdaCol;

    @FXML
    private TextArea todaTextArea;

    @FXML
    private TextArea toraTextArea;

    @FXML
    private TextArea ldaTextArea;

    @FXML
    private TextArea asdaTextArea;

    static javafx.scene.shape.Rectangle obstacle;
    @FXML
    private Tab sideTab;
    @FXML
    private Tab topTab;
    @FXML
    private Label sideLeftAwayLabel;
    @FXML
    private AnchorPane sideLeftPane;
    @FXML
    private Label sideLeftTowardsLabel;
    @FXML
    private Label sideRightAwayLabel;
    @FXML
    private AnchorPane sideRightPane;
    @FXML
    private Label sideRightTowardsLabel;
    @FXML
    private AnchorPane sideRootPane;
    @FXML
    private javafx.scene.shape.Rectangle sideRunway;
    @FXML
    private javafx.scene.shape.Rectangle sideRunway1;
    @FXML
    private Button sideSwitchSideButton;
    @FXML
    private TabPane TabPane;
    @FXML
    private Label topLeftAwayLabel;
    @FXML
    private AnchorPane topLeftPane;
    @FXML
    private Label topLeftTowardsLabel;
    @FXML
    private Label topRightAwayLabel;
    @FXML
    private AnchorPane topRightPane;
    @FXML
    private Label topRightTowardsLabel;
    @FXML
    private AnchorPane topRootPane;
    @FXML
    private javafx.scene.shape.Rectangle topRunway;
    @FXML
    private javafx.scene.shape.Rectangle topRunway1;
    @FXML
    private Button topSwitchSideButton;

    @FXML
    private Polygon sideLeftAwayArrowhead;
    @FXML
    private Line sideLeftAwayArrowline;
    @FXML
    private Polygon sideLeftTowardsArrowhead;
    @FXML
    private Line sideLeftTowardsArrowline;
    @FXML
    private Polygon sideRightAwayArrowhead;
    @FXML
    private Line sideRightAwayArrowline;
    @FXML
    private Polygon sideRightTowardsArrowhead;
    @FXML
    private Line sideRightTowardsArrowline;
    @FXML
    private Polygon topLeftAwayArrowhead;
    @FXML
    private Line topLeftAwayArrowline;
    @FXML
    private Polygon topLeftTowardsArrowhead;
    @FXML
    private Line topLeftTowardsArrowline;
    @FXML
    private Polygon topRightAwayArrowhead;
    @FXML
    private Line topRightAwayArrowline;
    @FXML
    private Polygon topRightTowardsArrowhead;
    @FXML
    private Line topRightTowardsArrowline;

    @FXML
    private Slider rotationSlider;
    @FXML
    private AnchorPane compass;
    @FXML
    private Slider zoomSlider;
    @FXML
    private ScrollPane leftScrollPane;
    @FXML
    private ScrollPane rightScrollPane;
    @FXML
    private GridPane leftGridPane;
    @FXML
    private GridPane rightGridPane;

    @FXML
    private Label topLeftLeftDesig;
    @FXML
    private Label topLeftRightDesig;
    @FXML
    private Label topRightLeftDesig;
    @FXML
    private Label topRightRightDesig;
    @FXML
    private Label sideLeftLeftDesig;
    @FXML
    private Label sideLeftLeftDesigLabel;
    @FXML
    private Label sideLeftRightDesig;
    @FXML
    private Label sideLeftRightDesigLabel;
    @FXML
    private Label sideRightLeftDesig;
    @FXML
    private Label sideRightLeftDesigLabel;
    @FXML
    private Label sideRightRightDesig;
    @FXML
    private Label sideRightRightDesigLabel;

    @FXML
    private ToggleButton colourBlindToggle;



    ArrayList<javafx.scene.shape.Rectangle> temporaryRect = new ArrayList<Rectangle>();
    ArrayList<Line> temporaryLine = new ArrayList<Line>();
    ArrayList<Color> colorsUsed = new ArrayList<>();
    ArrayList<Text> temporaryText = new ArrayList<Text>();
    ArrayList<Polygon> temporaryPolygons = new ArrayList<Polygon>();
    ArrayList<Text> polygonText = new ArrayList<Text>();
    ArrayList<Text> labelToChangeColour = new ArrayList<Text>(); // same as temporary text but without the slope label
    ArrayList<javafx.scene.shape.Rectangle> rectToChangeColour = new ArrayList<Rectangle>();  // same as temporary rect but without the obstacle


    //ArrayList<Color> originalColors = new ArrayList<>(); // add a new ArrayList to store the original colors
    boolean toggleOn = false;


    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    ObservableList<RunwayDesignator> runwayList = FXCollections.observableArrayList();
    ObservableList<RunwayTable> newRunwayList = FXCollections.observableArrayList();
    ObservableList<OriTable> oriTablesList = FXCollections.observableArrayList();
    ObservableList<RecTable> recAwayTableList = FXCollections.observableArrayList();
    ObservableList<RecTable> recTowardTableList = FXCollections.observableArrayList();

    public class OriTable {
        private String designator;
        private int oriTora;
        private int oriToda;
        private int oriLda;
        private int oriAsda;

        public OriTable(String designator, int oriTora, int oriToda, int oriLda, int oriAsda) {
            this.designator = designator;
            this.oriTora = oriTora;
            this.oriToda = oriToda;
            this.oriLda = oriLda;
            this.oriAsda = oriAsda;
        }

        public String getDesignator() {
            return designator;
        }

        public int getOriTora() {
            return oriTora;
        }

        public int getOriToda() {
            return oriToda;
        }

        public int getOriLda() {
            return oriLda;
        }

        public int getOriAsda() {
            return oriAsda;
        }
    }

    public class RecTable {
        private String designator;
        private int recTora;
        private int recToda;
        private int recLda;
        private int recAsda;

        public RecTable(String designator, int recTora, int recToda, int recLda, int recAsda) {
            this.designator = designator;
            this.recTora = recTora;
            this.recToda = recToda;
            this.recLda = recLda;
            this.recAsda = recAsda;
        }

        public String getDesignator() {
            return designator;
        }

        public int getRecTora() {
            return recTora;
        }

        public int getRecToda() {
            return recToda;
        }

        public int getRecLda() {
            return recLda;
        }

        public int getRecAsda() {
            return recAsda;
        }
    }

    public class RunwayTable {
        private String designator;
        private int clearway;
        private int stopway;
        private int threshold;

        public RunwayTable(String designator, int clearway, int stopway, int threshold) {
            this.designator = designator;
            this.clearway = clearway;
            this.stopway = stopway;
            this.threshold = threshold;
        }

        public String getDesignator() {
            return designator;
        }

        public int getClearway() {
            return clearway;
        }

        public int getStopway() {
            return stopway;
        }

        public int getThreshold() {
            return threshold;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            //checkChanges();
            startPeriodicCheck();
            getConstants();
            setRunwayComboBox();
            formatTable(oriDistanceTable);
            formatTable(recAwayDistanceTable);
            formatTable(runwayTable);
            setConstants();
            setAirportName();
            setTextArea();

            sideLeftPane.setVisible(true);
            sideRightPane.setVisible(false);
            leftScrollPane.setVisible(true);
            rightScrollPane.setVisible(false);
            topLeftAwayLabel.setVisible(true);
            topLeftTowardsLabel.setVisible(true);
            topRightAwayLabel.setVisible(false);
            topRightTowardsLabel.setVisible(false);

            zoomSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                double roundedValue = Math.round(newValue.doubleValue() * 10) / 10.0;
                zoomScaleLabel.setText("X " + String.format("%.1f", roundedValue));
                leftGridPane.setScaleX(newValue.doubleValue());
                leftGridPane.setScaleY(newValue.doubleValue());
                leftGridPane.setScaleZ(newValue.doubleValue());
                rightGridPane.setScaleX(newValue.doubleValue());
                rightGridPane.setScaleY(newValue.doubleValue());
                rightGridPane.setScaleZ(newValue.doubleValue());

                // Calculate the bounding box of the rotated grid pane
                Bounds bounds = topLeftPane.localToScene(topLeftPane.getBoundsInLocal());
                Bounds bounds2 = topRightPane.localToScene(topRightPane.getBoundsInLocal());

                // Calculate the translation that will center the grid pane within the scroll pane
                double offsetX = (leftScrollPane.getWidth() - bounds.getWidth()) / 2;
                double offsetY = (leftScrollPane.getHeight() - bounds.getHeight()) / 2;
                double maxOffsetX = Math.max(0, offsetX);
                double maxOffsetY = Math.max(0, offsetY);
                double minOffsetX = Math.min(0, offsetX - (leftScrollPane.getWidth() - bounds.getWidth()));
                double minOffsetY = Math.min(0, offsetY - (leftScrollPane.getHeight() - bounds.getHeight()));
                double translateX = Math.max(minOffsetX, Math.min(maxOffsetX, offsetX));
                double translateY = Math.max(minOffsetY, Math.min(maxOffsetY, offsetY));
                leftGridPane.setTranslateX(translateX);
                leftGridPane.setTranslateY(translateY);

                // Repeat for the right grid pane
                double offsetX2 = (rightScrollPane.getWidth() - bounds2.getWidth()) / 2;
                double offsetY2 = (rightScrollPane.getHeight() - bounds2.getHeight()) / 2;
                double maxOffsetX2 = Math.max(0, offsetX2);
                double maxOffsetY2 = Math.max(0, offsetY2);
                double minOffsetX2 = Math.min(0, offsetX2 - (rightScrollPane.getWidth() - bounds2.getWidth()));
                double minOffsetY2 = Math.min(0, offsetY2 - (rightScrollPane.getHeight() - bounds2.getHeight()));
                double translateX2 = Math.max(minOffsetX2, Math.min(maxOffsetX2, offsetX2));
                double translateY2 = Math.max(minOffsetY2, Math.min(maxOffsetY2, offsetY2));
                rightGridPane.setTranslateX(translateX2);
                rightGridPane.setTranslateY(translateY2);

                // Set the preferred size of the grid pane to fit within the visible area of the scroll pane
                leftGridPane.setPrefSize(bounds.getWidth(), bounds.getHeight());
                rightGridPane.setPrefSize(bounds2.getWidth(), bounds2.getHeight());
            });

            recAwayDistanceTable.setRowFactory(tv -> {
                TableRow<RecTable> row = new TableRow<>();
                row.itemProperty().addListener((obs, previousRec, currentRec) -> {
                    if (currentRec != null) {
                        if (currentRec.getRecTora() < minRunDistance || currentRec.getRecLda() < minLandingDistance) {
                            row.setStyle("-fx-text-background-color: red;");
                        } else {
                            row.setStyle("");
                        }
                    }
                });
                return row;
            });

            recTowardDistanceTable.setRowFactory(tv -> {
                TableRow<RecTable> row = new TableRow<>();
                row.itemProperty().addListener((obs, previousRec, currentRec) -> {
                    if (currentRec != null) {
                        if (currentRec.getRecTora() < minRunDistance || currentRec.getRecLda() < minLandingDistance) {
                            row.setStyle("-fx-text-background-color: red;");
                        } else {
                            row.setStyle("");
                        }
                    }
                });
                return row;
            });


        } catch (SQLException e) {
            playErrorAlert(String.valueOf(e));
        }
    }
    private Timestamp datetimeValue;
    private Timeline timeline1;
    private Timeline timeline2;

    public Timestamp getDatetimeValue() {
        return datetimeValue;
    }

    public void setDatetimeValue(Timestamp datetimeValue) {
        this.datetimeValue = datetimeValue;
    }

    public void startPeriodicCheck() throws SQLException {
        timeline1 = new Timeline(new KeyFrame(Duration.seconds(20), event -> {
            try {
                checkChanges();

            } catch (SQLException e) {

                throw new RuntimeException(e);
            }

                try {
                    refreshAll();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "Page is refreshing. Please wait...");
                alert1.show();
                timeline2 = new Timeline(new KeyFrame(Duration.seconds(2), event1 -> {
                    alert1.setResult(ButtonType.CANCEL);
                    alert1.close();
                }));
                timeline2.play();

                Platform.runLater(() ->
                {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Notification");
                alert.setHeaderText("Notification Title");

                if (AMController.getObstacleDeleted()) {
                        alert.setContentText("An Obstacle has been deleted at "+getDatetimeValue());
                        alert.showAndWait();
                        AMController.setObstacleDeleted(false);
                    }try {
                        clearChanges();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                if (AMController.getObstacleAdded()) {
                        alert.setContentText("An New Obstacle has been Added at "+getDatetimeValue());
                        alert.showAndWait();
                        AMController.setObstacleAdded(false);
                    try {
                        clearChanges();

                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }));

        clearChanges();

        timeline1.setCycleCount(Timeline.INDEFINITE);
        timeline1.play();
    }

    public void stopPeriodicCheck(){
        if (timeline1 != null) {
            timeline1.stop();
            timeline1 = null;
        }
        if (timeline2 != null) {
            timeline2.stop();
            timeline2 = null;
        }

    }
    public void switchToLogin2(ActionEvent e) throws IOException {
       stopPeriodicCheck();
       MainController login = new MainController();
       login.switchToLoginPage(e);
    }

    public void refreshAll(ActionEvent event) throws SQLException {
        refreshAll();
    }

    public void refreshAll() throws SQLException {
        loadData();
        runwayComboBox.getSelectionModel().clearSelection();
        setRunwayComboBox();
        toraTextArea.clear();
        todaTextArea.clear();
        asdaTextArea.clear();
        ldaTextArea.clear();
        ATCprintTXT.setVisible(false);
//refresh visualisation
    }

    public void clearChanges() throws SQLException {
        connection = DbConnect.getConnection();
        Statement statement = connection.createStatement();
        statement.execute("DELETE FROM obstacle_history");
        statement.close();
        connection.close();
    }


    public void checkChanges() throws SQLException {
        connection = DbConnect.getConnection();
        preparedStatement = connection.prepareStatement("SELECT added_deleted,time_stamp from obstacle_history");
        resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            setDatetimeValue(Timestamp.valueOf(resultSet.getTimestamp("time_stamp").toLocalDateTime()));
            String action = resultSet.getString("added_deleted");
            if("deleted".equals(action)){
                AMController.setObstacleDeleted(true);
                AMController.setObstacleAdded(false);
            }
            else if("added".equals(action)){
                AMController.setObstacleDeleted(false);
                AMController.setObstacleAdded(true);
            }
        }

        resultSet = preparedStatement.executeQuery();
        connection.close();
        preparedStatement.close();
        resultSet.close();
    }


    public void setTextArea (){
        asdaTextArea.setEditable(false);
        todaTextArea.setEditable(false);
        toraTextArea.setEditable(false);
        ldaTextArea.setEditable(false);
    }

    public void setConstants () {
        resaText.setText(String.valueOf(RESA));
        stripEndText.setText(String.valueOf(stripEnd));
        blastProtectionText.setText(String.valueOf(blastProtection));
        alsText.setText(String.valueOf(slope));
        tocsText.setText(String.valueOf(slope));
    }

    private void setRunwayComboBox () throws SQLException {
        runwayComboBox.getItems().clear();
        connection = DbConnect.getConnection();
        preparedStatement = connection.prepareStatement("SELECT runway.runway_name FROM obstacle_location JOIN runway ON obstacle_location.runway_id = runway.runway_id;");
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            runwayComboBox.getItems().add(resultSet.getString("runway_name"));
        }
    }

    private void setAirportName() throws SQLException {
        connection = DbConnect.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT airport_name FROM airport LIMIT 1");
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            String name = resultSet.getString("airport_name");
            airportNameSV.setText(name);
            airportNameTD.setText(name);
        }

        connection.close();
        preparedStatement.close();
        resultSet.close();

    }

    public void refreshRunwayTable() {
        String runway = runwayComboBox.getValue();
        try{
            newRunwayList.clear();
            oriTablesList.clear();

            connection = DbConnect.getConnection();
            preparedStatement = connection.prepareStatement("SELECT rd.designator_name, rd.tora, rd.toda, rd.asda, rd.lda, rd.displaced_thres\n" +
                    "                    FROM runway r\n" +
                    "                    JOIN runway_designator rd ON r.designator_id_1 = rd.designator_id OR r.designator_id_2 = rd.designator_id\n" +
                    "                    WHERE r.runway_name = '" + runway +"';");
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                runwayList.add(new RunwayDesignator(
                        resultSet.getInt("tora"),
                        resultSet.getInt("toda"),
                        resultSet.getInt("asda"),
                        resultSet.getInt("lda"),
                        resultSet.getInt("displaced_thres"),
                        resultSet.getString("designator_name")
                ));
            }

            for(RunwayDesignator i: runwayList){
                newRunwayList.add(new RunwayTable(i.getRunwayDesignatorName(),i.getClearway(),i.getStopway(),i.getDisplacedThres()));
                oriTablesList.add(new OriTable(i.getRunwayDesignatorName(),i.getTora(),i.getToda(),i.getLda(),i.getAsda()));
            }

            runwayList.clear();
            runwayTable.setItems(newRunwayList);
            oriDistanceTable.setItems(oriTablesList);

            connection.close();
            preparedStatement.close();
            resultSet.close();

        }catch (SQLException e){
            playErrorAlert(String.valueOf(e));
        }
    }

    public void loadRunwayTable(ActionEvent event){
        loadRunwayTable();
    }
    public void loadRunwayTable(){
        connection = DbConnect.getConnection();
        refreshRunwayTable();

        runDesigCol.setCellValueFactory(new PropertyValueFactory<>("designator"));
        clearwayCol.setCellValueFactory(new PropertyValueFactory<>("clearway"));
        stopwayCol.setCellValueFactory(new PropertyValueFactory<>("stopway"));
        thresholdCol.setCellValueFactory(new PropertyValueFactory<>("threshold"));

        oriDesigCol.setCellValueFactory(new PropertyValueFactory<>("designator"));
        oriToraCol.setCellValueFactory(new PropertyValueFactory<>("oriTora"));
        oriTodaCol.setCellValueFactory(new PropertyValueFactory<>("oriToda"));
        oriLdaCol.setCellValueFactory(new PropertyValueFactory<>("oriLda"));
        oriAsdaCol.setCellValueFactory(new PropertyValueFactory<>("oriAsda"));


        runwayTable.setItems(newRunwayList);
        oriDistanceTable.setItems(oriTablesList);
    }

    public void refreshRecTable() {
        try {
            String runway = runwayComboBox.getValue();
            try {
                recAwayTableList.clear();
                recTowardTableList.clear();

                //Getting the obstacle that is on the runway
                connection = DbConnect.getConnection();
                preparedStatement = connection.prepareStatement("SELECT o.*\n" +
                        "FROM obstacle o\n" +
                        "INNER JOIN obstacle_location ol ON o.obstacle_id = ol.obstacle_id\n" +
                        "INNER JOIN runway r ON ol.runway_id = r.runway_id\n" +
                        "WHERE r.runway_name = '" + runway + "';");
                resultSet = preparedStatement.executeQuery();

                Obstacle obstacle = new Obstacle();
                while (resultSet.next()) {
                    obstacle.setObstacleName(resultSet.getString("name"));
                    obstacle.setHeight(resultSet.getInt("height"));
                    obstacle.setLength(resultSet.getInt("length"));
                    obstacle.setWidth(resultSet.getInt("width"));

                }

                nameText.setText(obstacle.getObstacleName());
                heightText.setText(String.valueOf(obstacle.getHeight()));
                lengthText.setText(String.valueOf(obstacle.getLength()));
                widthText.setText(String.valueOf(obstacle.getWidth()));

                //Getting the position of the obstacle on the runway
                preparedStatement = connection.prepareStatement("SELECT ol.distance_from_threshold_R, ol.distance_from_threshold_L, ol.distance_from_centerline, ol.direction_from_centerline\n" +
                        "FROM obstacle_location ol\n" +
                        "JOIN runway r ON ol.runway_id = r.runway_id\n" +
                        "JOIN runway_designator rd ON r.designator_id_1 = rd.designator_id OR r.designator_id_2 = rd.designator_id\n" +
                        "WHERE r.runway_name = '" + runway + "' LIMIT 1;");
                resultSet = preparedStatement.executeQuery();

                ObstacleLocation obstacleLocation = new ObstacleLocation();
                while (resultSet.next()) {
                    obstacleLocation.setDistanceThresR(resultSet.getInt("distance_from_threshold_R"));
                    obstacleLocation.setDistanceThresL(resultSet.getInt("distance_from_threshold_L"));
                    obstacleLocation.setDistanceFromCenterline(resultSet.getInt("distance_from_centerline"));
                    obstacleLocation.setDirection(ObstacleLocation.Direction.valueOf(resultSet.getString("direction_from_centerline")));

                }

                thresholdRText.setText(String.valueOf(obstacleLocation.getDistanceThresR()));
                thresholdLText.setText(String.valueOf(obstacleLocation.getDistanceThresL()));
                centerlineText.setText(String.valueOf(obstacleLocation.getDistanceFromCenterline()));
                directionText.setText(String.valueOf(obstacleLocation.getDirection()));

                //Getting the runway original distances
                preparedStatement = connection.prepareStatement("SELECT rd.designator_name, rd.tora, rd.toda, rd.asda, rd.lda, rd.displaced_thres\n" +
                        "                    FROM runway r\n" +
                        "                    JOIN runway_designator rd ON r.designator_id_1 = rd.designator_id OR r.designator_id_2 = rd.designator_id\n" +
                        "                    WHERE r.runway_name = '" + runway + "';");
                resultSet = preparedStatement.executeQuery();

                ObservableList<RunwayDesignator> runwayDesignators = FXCollections.observableArrayList();
                while (resultSet.next()) {
                    runwayDesignators.add(new RunwayDesignator(
                            resultSet.getInt("tora"),
                            resultSet.getInt("toda"),
                            resultSet.getInt("asda"),
                            resultSet.getInt("lda"),
                            resultSet.getInt("displaced_thres"),
                            resultSet.getString("designator_name")
                    ));
                }
                Runway currentRunway = new Runway();
                for (RunwayDesignator i : runwayDesignators) {
                    if (i.getRunwayDesignatorName().endsWith("L")) {
                        currentRunway.setLeft(i);
                    } else {
                        currentRunway.setRight(i);
                    }
                }
                currentRunway.setRunwayName(runway);

                ArrayList<String> toraBreakdown = new ArrayList<>();
                ArrayList<String> todaBreakdown = new ArrayList<>();
                ArrayList<String> asdaBreakdown = new ArrayList<>();
                ArrayList<String> ldaBreakdown = new ArrayList<>();

                for (RunwayDesignator i : runwayDesignators) {
                    //Take off away, Landing over
                    char side = i.getRunwayDesignatorName().charAt(i.getRunwayDesignatorName().length() - 1);
                    if (side == 'L') {
                        int newToraA = calcTORA(Status.away, i, obstacle, obstacleLocation);
                        int newTodaA = calcTODA(Status.away, i, obstacle, obstacleLocation);
                        int newAsdaA = calcASDA(Status.away, i, obstacle, obstacleLocation);
                        int newLdaA = calcLDA(Status.over, i, obstacle, obstacleLocation);

                        int newToraT = calcTORA(Status.towards, i, obstacle, obstacleLocation);
                        int newTodaT = calcTODA(Status.towards, i, obstacle, obstacleLocation);
                        int newAsdaT = calcASDA(Status.towards, i, obstacle, obstacleLocation);
                        int newLdaT = calcLDA(Status.towards, i, obstacle, obstacleLocation);

                        recAwayTableList.add(new RecTable(i.getRunwayDesignatorName(), newToraA, newTodaA, newLdaA, newAsdaA));
                        recTowardTableList.add(new RecTable(i.getRunwayDesignatorName(), newToraT, newTodaT, newLdaT, newAsdaT));

                        toraBreakdown.add(printTORA(Status.away, i, obstacle, obstacleLocation));
                        toraBreakdown.add(printTORA(Status.towards, i, obstacle, obstacleLocation));
                        todaBreakdown.add(printTODA(Status.away, i, obstacle, obstacleLocation));
                        todaBreakdown.add(printTODA(Status.towards, i, obstacle, obstacleLocation));
                        asdaBreakdown.add(printASDA(Status.away, i, obstacle, obstacleLocation));
                        asdaBreakdown.add(printASDA(Status.towards, i, obstacle, obstacleLocation));
                        ldaBreakdown.add(printLDA(Status.over, i, obstacle, obstacleLocation));
                        ldaBreakdown.add(printLDA(Status.towards, i, obstacle, obstacleLocation));

                    } else if (side == 'R') {
                        int newToraA = calcTORA(Status.away, i, obstacle, obstacleLocation);
                        int newTodaA = calcTODA(Status.away, i, obstacle, obstacleLocation);
                        int newAsdaA = calcASDA(Status.away, i, obstacle, obstacleLocation);
                        int newLdaA = calcLDA(Status.over, i, obstacle, obstacleLocation);

                        int newToraT = calcTORA(Status.towards, i, obstacle, obstacleLocation);
                        int newTodaT = calcTODA(Status.towards, i, obstacle, obstacleLocation);
                        int newAsdaT = calcASDA(Status.towards, i, obstacle, obstacleLocation);
                        int newLdaT = calcLDA(Status.towards, i, obstacle, obstacleLocation);

                        recAwayTableList.add(new RecTable(i.getRunwayDesignatorName(), newToraA, newTodaA, newLdaA, newAsdaA));
                        recTowardTableList.add(new RecTable(i.getRunwayDesignatorName(), newToraT, newTodaT, newLdaT, newAsdaT));

                        toraBreakdown.add(printTORA(Status.away, i, obstacle, obstacleLocation));
                        toraBreakdown.add(printTORA(Status.towards, i, obstacle, obstacleLocation));
                        todaBreakdown.add(printTODA(Status.away, i, obstacle, obstacleLocation));
                        todaBreakdown.add(printTODA(Status.towards, i, obstacle, obstacleLocation));
                        asdaBreakdown.add(printASDA(Status.away, i, obstacle, obstacleLocation));
                        asdaBreakdown.add(printASDA(Status.towards, i, obstacle, obstacleLocation));
                        ldaBreakdown.add(printLDA(Status.over, i, obstacle, obstacleLocation));
                        ldaBreakdown.add(printLDA(Status.towards, i, obstacle, obstacleLocation));
                    }
                }
                Collections.swap(toraBreakdown, 1, 2);
                Collections.swap(todaBreakdown, 1, 2);
                Collections.swap(asdaBreakdown, 1, 2);
                Collections.swap(ldaBreakdown, 1, 2);

                toraTextArea.clear();
                todaTextArea.clear();
                asdaTextArea.clear();
                ldaTextArea.clear();

                for (String text : toraBreakdown) {
                    toraTextArea.appendText(text + "\n");
                }
                for (String text : todaBreakdown) {
                    todaTextArea.appendText(text + "\n");
                }
                for (String text : asdaBreakdown) {
                    asdaTextArea.appendText(text + "\n");
                }
                for (String text : ldaBreakdown) {
                    ldaTextArea.appendText(text + "\n");
                }

                recAwayDistanceTable.setItems(recAwayTableList);
                recTowardDistanceTable.setItems(recTowardTableList);

                removeObjects();
                sideView(currentRunway, obstacle, obstacleLocation, sideLeftPane, sideRightPane, sideRunway, sideLeftAwayLabel, sideLeftTowardsLabel, sideRightAwayLabel, sideRightTowardsLabel);
                topView(currentRunway, obstacle, obstacleLocation, topLeftPane, topRightPane, topRunway, topLeftAwayLabel, topLeftTowardsLabel, topRightAwayLabel, topRightTowardsLabel);

                connection.close();
                preparedStatement.close();
                resultSet.close();

            } catch (SQLException e) {
                playErrorAlert(String.valueOf(e));
            }
        } catch (Exception e) {

        }
    }


    public void loadRecTable (ActionEvent event){
       loadRecTable();
    }
    public void loadRecTable (){
        connection = DbConnect.getConnection();
        refreshRecTable();

        awayDesigCol.setCellValueFactory(new PropertyValueFactory<>("designator"));
        awayToraCol.setCellValueFactory(new PropertyValueFactory<>("recTora"));
        awayTodaCol.setCellValueFactory(new PropertyValueFactory<>("recToda"));
        awayAsdaCol.setCellValueFactory(new PropertyValueFactory<>("recAsda"));
        awayLdaCol.setCellValueFactory(new PropertyValueFactory<>("recLda"));

        towardDesigCol.setCellValueFactory(new PropertyValueFactory<>("designator"));
        towardToraCol.setCellValueFactory(new PropertyValueFactory<>("recTora"));
        towardTodaCol.setCellValueFactory(new PropertyValueFactory<>("recToda"));
        towardAsdaCol.setCellValueFactory(new PropertyValueFactory<>("recAsda"));
        towardLdaCol.setCellValueFactory(new PropertyValueFactory<>("recLda"));

        recAwayDistanceTable.setItems(recAwayTableList);
        recTowardDistanceTable.setItems(recTowardTableList);

    }


    public void loadData(ActionEvent event) {
      loadData();
    }
    public void loadData() {
        try {
            loadRunwayTable();
            loadRecTable();
            toraTextArea.positionCaret(0);
            todaTextArea.positionCaret(0);
            asdaTextArea.positionCaret(0);
            ldaTextArea.positionCaret(0);
            ATCprintTXT.setVisible(true);
        } catch (Exception e) {

        }
    }

    //add option to choose the file location add obstacle info/ runway info
    public void printToTXT(ActionEvent event) {
    try {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy,HH-mm-ss");
        Date date = new Date();
        final DirectoryChooser directoryChooser =
                new DirectoryChooser();
        final File selectedDirectory =
                directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            FileWriter myWriter = new FileWriter(selectedDirectory.getAbsolutePath() + "/Runway Calculations " +dateFormat.format(date)  + ".txt");
            myWriter.write("Date and Time of save: " + dateFormat.format(date) + "\n\n Runway Name: " + runwayComboBox.getValue() + "\n\n ------------------Obstacle Information----------------\n\n Obstacle name: " + nameText.getText() + "\n\n Obstacle height: " + heightText.getText()
                    + "\n\n Obstacle length: " + lengthText.getText() + "\n\n obstacle width: " + widthText.getText()
                    + "\n\n Obstacle Distance from Right Threshold: " + thresholdRText.getText() + "\n\n Obstacle Distance from Left Threshold: " + thresholdLText.getText()
                    + "\n\n Distance From Center line: " + centerlineText.getText() + "\n\n Direction: " + directionText.getText()
                    + "\n\n\n------------------TORA Calculations-----------------\n\n" + toraTextArea.getText()
                    + "\n ------------------TODA Calculations----------------\n\n" + todaTextArea.getText()
                    + "\n ------------------ASDA Calculations----------------\n\n" + asdaTextArea.getText()
                    + "\n ------------------LDA Calculations----------------\n\n" + ldaTextArea.getText());
            myWriter.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "File Exported Successfully");
            alert.showAndWait();
        }

    } catch (IOException e) {
        playErrorAlert(String.valueOf(e));

        }
    }

    @FXML
    void hyperlink(ActionEvent event) throws URISyntaxException, IOException {
        System.out.println("opened");
        //Desktop.getDesktop().browse(new URI("https://drive.google.com/file/d/1A0YGkIcy6O6BGTx-QHKhXmDhOp5zt4D3/view?usp=sharing"));
        File file = new File("src/main/resources/UserManualATC.pdf");
        Desktop.getDesktop().open(file);
    }

    ////side visualisation
    public void sideView(Runway r, Obstacle o, ObstacleLocation ol, AnchorPane pane, AnchorPane pane2, Rectangle drawnRunway, Label leftAwayLabel, Label leftTowardsLabel, Label rightAwayLabel, Label rightTowardsLabel){
        setRunway(r,pane,drawnRunway);
        setObstacle(r,ol,pane,drawnRunway);
        setRunway(r,pane2,drawnRunway);
        setObstacle(r,ol,pane2,drawnRunway);

         viewLeft(r,o,ol,pane,drawnRunway,leftAwayLabel,leftTowardsLabel,200,470);
         viewRight(r,o,ol,pane2,drawnRunway,rightAwayLabel,rightTowardsLabel,200,470);

        int leftNum = Integer.parseInt(r.getLeftDesignator().getRunwayDesignatorName().substring(0,2));
        int rightNum = Integer.parseInt(r.getRightDesignator().getRunwayDesignatorName().substring(0,2));

        if (rightNum < leftNum){
            flip();
        }else {
            unflip();
        }
    }

    public void topView(Runway r, Obstacle o, ObstacleLocation ol, AnchorPane pane, AnchorPane pane2, Rectangle drawnRunway, Label leftAwayLabel, Label leftTowardsLabel, Label rightAwayLabel, Label rightTowardsLabel){
        setRunway(r,pane,drawnRunway);
        setTopObstacle(r,ol,pane);
        setRunway(r,pane2,drawnRunway);
        setTopObstacle(r,ol,pane2);

        viewLeft(r,o,ol,pane,drawnRunway,leftAwayLabel,leftTowardsLabel,140,360);
        viewRight(r,o,ol,pane2,drawnRunway,rightAwayLabel,rightTowardsLabel,140,360);

        int leftNum = Integer.parseInt(r.getLeftDesignator().getRunwayDesignatorName().substring(0,2));
        int rightNum = Integer.parseInt(r.getRightDesignator().getRunwayDesignatorName().substring(0,2));

        if (rightNum < leftNum){
            flip();
        }else {
            unflip();
        }
    }

    public void viewLeft(Runway r,Obstacle o,ObstacleLocation ol, AnchorPane pane, Rectangle drawnRunway, Label awayLabel, Label towardsLabel, int awayPos, int towardsPos){
        //setRunway(r,pane,drawnRunway);
        //setObstacle(r,ol,pane,drawnRunway);
        drawLeft(Calculator.Status.away, Calculator.Status.over,r,o,ol,pane,drawnRunway,awayLabel,towardsLabel,awayPos,towardsPos);
        drawLeft(Calculator.Status.towards, Calculator.Status.towards,r,o,ol,pane,drawnRunway,awayLabel,towardsLabel,awayPos,towardsPos);
    }

    public void viewRight(Runway r,Obstacle o,ObstacleLocation ol, AnchorPane pane, Rectangle drawnRunway, Label awayLabel, Label towardsLabel, int awayPos, int towardsPos){
        //setRunway(r,pane,drawnRunway);
        //setObstacle(r,ol,pane,drawnRunway);
        drawRight(Calculator.Status.away, Calculator.Status.over,r,o,ol,pane,drawnRunway,awayLabel,towardsLabel,awayPos,towardsPos);
        drawRight(Calculator.Status.towards, Calculator.Status.towards,r,o,ol,pane,drawnRunway,awayLabel,towardsLabel,awayPos,towardsPos);
    }

    public void drawLeft(Status takeOffStatus, Status landingStatus, Runway r, Obstacle obs, ObstacleLocation obsLocation, AnchorPane pane, Rectangle drawnRunway, Label awayLabel, Label towardsLabel, int awayPos, int towardsPos) {
        RunwayDesignator left = r.getLeftDesignator();
        RunwayDesignator right = r.getRightDesignator();
        int scale = left.getTora();
        int newTora = calcTORA(takeOffStatus, left,obs,obsLocation);
        int newToda = calcTODA(takeOffStatus, left,obs,obsLocation);
        int newAsda = calcASDA(takeOffStatus, left,obs,obsLocation);
        int newLda = calcLDA(landingStatus, left,obs,obsLocation);
        int slopeCalc = (slope*obs.getHeight());
        int lineThickness = 6;
        int blastOrResa = Math.max(blastProtection, (RESA+stripEnd));
        int slopeOrResaOrBlast = Math.max(blastProtection, Math.max(slopeCalc, RESA));

        //int awayPos = 140;
        //int towardsPos = 360;
        towardsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        awayLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        AnchorPane.setLeftAnchor(awayLabel,10.0);
        AnchorPane.setTopAnchor(awayLabel,10.0);
        AnchorPane.setLeftAnchor(towardsLabel,10.0);
        AnchorPane.setBottomAnchor(towardsLabel,10.0);

        if (newTora < minRunDistance || newLda < minLandingDistance){
            if (takeOffStatus == Status.away && landingStatus == Status.over) {
                awayLabel.setText(left.getRunwayDesignatorName() + " Not suitable for Take off " +takeOffStatus + " / Landing " + landingStatus);
                awayLabel.setTextFill(javafx.scene.paint.Color.RED);
                sideLeftAwayArrowhead.setVisible(false);
                sideLeftAwayArrowline.setVisible(false);
                topLeftAwayArrowhead.setVisible(false);
                topLeftAwayArrowline.setVisible(false);
            }else{
                towardsLabel.setText(left.getRunwayDesignatorName() + " Not suitable for Take off " +takeOffStatus + " / Landing " + landingStatus);
                towardsLabel.setTextFill(javafx.scene.paint.Color.RED);
                sideLeftTowardsArrowhead.setVisible(false);
                sideLeftTowardsArrowline.setVisible(false);
                topLeftTowardsArrowhead.setVisible(false);
                topLeftTowardsArrowline.setVisible(false);
            }
        }else {
            if (takeOffStatus == Status.away && landingStatus == Status.over) {
                awayLabel.setTextFill(Color.BLACK);
                sideLeftAwayArrowhead.setVisible(true);
                sideLeftAwayArrowline.setVisible(true);
                topLeftAwayArrowhead.setVisible(true);
                topLeftAwayArrowline.setVisible(true);
                awayLabel.setText(left.getRunwayDesignatorName() + " Take off AWAY / Landing OVER");
                drawLine((double) newTora / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL() + blastOrResa) / scale, pane, javafx.scene.paint.Color.GREEN, awayPos,lineThickness,("TORA "+newTora),drawnRunway); //tora
                if (blastProtection >= RESA+stripEnd) {
                    drawLine((double) blastProtection / scale, (double) (left.getDisplacedThres() + obsLocation.getDistanceThresL()) / scale, pane, javafx.scene.paint.Color.BLUE, awayPos, lineThickness, ("blast\nprotection " + blastProtection), drawnRunway); //blast protection
                }else{
                    drawLine((double) RESA / scale, (double) (left.getDisplacedThres() + obsLocation.getDistanceThresL()) / scale, pane, javafx.scene.paint.Color.MAGENTA, awayPos, lineThickness, ("RESA " + RESA), drawnRunway); //resa
                    drawLine((double) stripEnd / scale, (double) (left.getDisplacedThres() + obsLocation.getDistanceThresL() + RESA) / scale, pane, javafx.scene.paint.Color.STEELBLUE, awayPos, lineThickness, ("strip\nend " + stripEnd), drawnRunway); //strip end
                }
                drawLine((double) newToda / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL() + blastOrResa) / scale, pane, javafx.scene.paint.Color.RED, awayPos+20,lineThickness,("TODA "+newToda),drawnRunway); //toda
                drawLine((double) newAsda / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL() + blastOrResa) / scale, pane, javafx.scene.paint.Color.ORANGE, awayPos+40,lineThickness,("ASDA "+newAsda),drawnRunway); //asda
                drawLine((double) newLda / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL() + stripEnd + slopeOrResaOrBlast) / scale, pane, javafx.scene.paint.Color.PURPLE, awayPos+60,lineThickness,("LDA "+newLda),drawnRunway); //lda
                if (slopeOrResaOrBlast == slopeCalc) {
                    drawLine((double) slopeCalc / scale, (double) (left.getDisplacedThres() + obsLocation.getDistanceThresL()) / scale, pane, javafx.scene.paint.Color.MEDIUMORCHID, awayPos + 60, lineThickness, ("slope " + slopeCalc), drawnRunway); //slope
                    drawSlope((double) slopeCalc / scale, (double) (left.getDisplacedThres() + obsLocation.getDistanceThresL()) / scale, 60, pane, Color.MEDIUMORCHID, true, ("slope")); //slope
                }else if(slopeOrResaOrBlast == RESA){
                    drawLine((double) RESA / scale, (double) (left.getDisplacedThres() + obsLocation.getDistanceThresL()) / scale, pane, javafx.scene.paint.Color.MAGENTA, awayPos + 60, lineThickness, ("RESA " + RESA), drawnRunway); //resa
                }else{
                    drawLine((double) blastProtection / scale, (double) (left.getDisplacedThres() + obsLocation.getDistanceThresL()) / scale, pane, Color.BLUE, awayPos + 60, lineThickness, ("blast\nprotection " + blastProtection), drawnRunway); //blast protect
                }
                drawLine((double) stripEnd / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL() + slopeOrResaOrBlast) / scale, pane, javafx.scene.paint.Color.STEELBLUE, awayPos+60,lineThickness,("strip\nend "+ stripEnd),drawnRunway); //strip end
            } else {
                towardsLabel.setTextFill(Color.BLACK);
                sideLeftTowardsArrowhead.setVisible(true);
                sideLeftTowardsArrowline.setVisible(true);
                topLeftTowardsArrowhead.setVisible(true);
                topLeftTowardsArrowline.setVisible(true);
                towardsLabel.setText(left.getRunwayDesignatorName() + " Take off TOWARDS / Landing TOWARDS");
                drawLine((double) newTora / scale, (double) 0 / scale, pane, javafx.scene.paint.Color.GREEN, towardsPos+60,lineThickness,("TORA "+newTora),drawnRunway); //tora
                drawLine((double) stripEnd / scale, (double) newTora / scale, pane, javafx.scene.paint.Color.STEELBLUE, towardsPos+60,lineThickness,("strip\nend "+ stripEnd),drawnRunway); //strip end
                if (slopeCalc >= RESA) {
                    drawLine((double) slopeCalc / scale, (double) (newTora + stripEnd) / scale, pane, javafx.scene.paint.Color.MEDIUMORCHID, towardsPos + 60, lineThickness, ("slope " + slopeCalc), drawnRunway); //slope
                    drawSlope((double) slopeCalc / scale, (double) (newTora + Calculator.stripEnd) / scale, 60, pane, Color.MEDIUMORCHID, false, ("slope")); //slope
                }else {
                    drawLine((double) RESA / scale, (double) (newTora + stripEnd) / scale, pane, javafx.scene.paint.Color.MAGENTA, towardsPos + 60, lineThickness, ("RESA " + RESA), drawnRunway); //resa
                }
                drawLine((double) newToda / scale, (double) 0 / scale, pane, javafx.scene.paint.Color.RED, towardsPos+40,lineThickness,("TODA "+newToda),drawnRunway); //toda
                drawLine((double) newAsda / scale, (double) 0 / scale, pane, javafx.scene.paint.Color.ORANGE, towardsPos+20,lineThickness,("ASDA "+newAsda),drawnRunway); //asda
                drawLine((double) newLda / scale, (double) left.getDisplacedThres() / scale, pane, javafx.scene.paint.Color.PURPLE, towardsPos,lineThickness,("LDA "+newLda),drawnRunway); //lda
                drawLine((double) stripEnd / scale, (double) (newLda+left.getDisplacedThres()) / scale, pane, javafx.scene.paint.Color.STEELBLUE, towardsPos,lineThickness,("strip\nend "+ stripEnd),drawnRunway); //strip end
                drawLine((double) RESA / scale, (double) (newLda +left.getDisplacedThres()+ stripEnd) / scale, pane, javafx.scene.paint.Color.MAGENTA, towardsPos,lineThickness,("RESA "+ RESA),drawnRunway); //resa
            }
            //removing the slope for top down view
            removeSlope();
        }
    }

    public void drawRight(Status takeOffStatus, Status landingStatus, Runway r, Obstacle obs, ObstacleLocation obsLocation, AnchorPane pane, Rectangle drawnRunway, Label awayLabel, Label towardsLabel, int awayPos, int towardsPos) {
        RunwayDesignator left = r.getLeftDesignator();
        RunwayDesignator right = r.getRightDesignator();
        int scale = right.getTora();
        int newTora = calcTORA(takeOffStatus, right,obs,obsLocation);
        int newToda = calcTODA(takeOffStatus, right,obs,obsLocation);
        int newAsda = calcASDA(takeOffStatus, right,obs,obsLocation);
        int newLda = calcLDA(landingStatus, right,obs,obsLocation);
        int slopeCalc = (slope*obs.getHeight());
        int lineThickness = 6;
        int slopeOrResa = Math.max(slopeCalc, RESA);
        int slopeOrResaOrBlast = Math.max(blastProtection, Math.max(slopeCalc, RESA));
        //int awayPos = 140;
        //int towardsPos = 360;
        towardsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        awayLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        AnchorPane.setRightAnchor(awayLabel,10.0);
        AnchorPane.setTopAnchor(awayLabel,10.0);
        AnchorPane.setRightAnchor(towardsLabel,10.0);
        AnchorPane.setBottomAnchor(towardsLabel,10.0);

        if (newTora < minRunDistance || newLda < minLandingDistance){
            if (takeOffStatus == Status.away && landingStatus == Status.over) {
                awayLabel.setText(right.getRunwayDesignatorName() + " Not suitable for Take off " +takeOffStatus + " / Landing " + landingStatus);
                awayLabel.setTextFill(javafx.scene.paint.Color.RED);
                sideRightAwayArrowhead.setVisible(false);
                sideRightAwayArrowline.setVisible(false);
                topRightAwayArrowhead.setVisible(false);
                topRightAwayArrowline.setVisible(false);
            }else{
                towardsLabel.setText(right.getRunwayDesignatorName() + " Not suitable for Take off " +takeOffStatus + " / Landing " + landingStatus);
                towardsLabel.setTextFill(javafx.scene.paint.Color.RED);
                sideRightTowardsArrowhead.setVisible(false);
                sideRightTowardsArrowline.setVisible(false);
                topRightTowardsArrowhead.setVisible(false);
                topRightTowardsArrowline.setVisible(false);
            }
        }else {
            if (takeOffStatus == Status.away && landingStatus == Status.over) {
                awayLabel.setTextFill(Color.BLACK);
                sideRightAwayArrowhead.setVisible(true);
                sideRightAwayArrowline.setVisible(true);
                topRightAwayArrowhead.setVisible(true);
                topRightAwayArrowline.setVisible(true);
                awayLabel.setText(right.getRunwayDesignatorName() + " Take off AWAY / Landing OVER");
                drawLine((double) newTora / scale, (double) 0 / scale, pane, javafx.scene.paint.Color.GREEN, awayPos,lineThickness,("TORA "+newTora),drawnRunway); //tora
                if (blastProtection >= RESA+stripEnd) {
                    drawLine((double) blastProtection / scale, (double) newTora / scale, pane, javafx.scene.paint.Color.BLUE, awayPos, lineThickness, ("blast\nprotection " + blastProtection), drawnRunway); //blast protection
                }else{
                    drawLine((double) stripEnd / scale, (double) newTora / scale, pane, javafx.scene.paint.Color.STEELBLUE, awayPos, lineThickness, ("RESA " + RESA), drawnRunway); //resa
                    drawLine((double) RESA / scale, (double) (newTora+stripEnd) / scale, pane, javafx.scene.paint.Color.MAGENTA, awayPos, lineThickness, ("RESA " + RESA), drawnRunway); //resa
                }
                drawLine((double) newToda / scale, (double) (newTora-newToda) / scale, pane, javafx.scene.paint.Color.RED, awayPos+20,lineThickness,("TODA "+newToda),drawnRunway); //toda
                drawLine((double) newAsda / scale, (double) (newTora-newAsda) / scale, pane, javafx.scene.paint.Color.ORANGE, awayPos+40,lineThickness,("ASDA "+newAsda),drawnRunway); //asda
                drawLine((double) newLda / scale, (double) 0 / scale, pane, javafx.scene.paint.Color.PURPLE, awayPos+60,lineThickness,("LDA "+newLda),drawnRunway); //lda
                drawLine((double) stripEnd / scale, (double) newLda / scale, pane, javafx.scene.paint.Color.STEELBLUE, awayPos+60,lineThickness,("strip\nend "+ stripEnd),drawnRunway); //strip end
                if (slopeOrResaOrBlast == slopeCalc) {
                    drawLine((double) slopeCalc / scale, (double) (newLda + stripEnd) / scale, pane, javafx.scene.paint.Color.MEDIUMORCHID, awayPos + 60, lineThickness, ("slope " + slopeCalc), drawnRunway); //slope
                    drawSlope((double) slopeCalc / scale, (double) (newLda+Calculator.stripEnd) / scale, 60, pane, Color.MEDIUMORCHID, false, ("slope")); //slope
                }else if (slopeOrResaOrBlast == RESA){
                    drawLine((double) RESA / scale, (double) (newLda + stripEnd) / scale, pane, javafx.scene.paint.Color.MAGENTA, awayPos + 60, lineThickness, ("RESA " + RESA), drawnRunway); //resa
                }else{
                    drawLine((double) blastProtection / scale, (double) (newLda + stripEnd) / scale, pane, Color.BLUE, awayPos + 60, lineThickness, ("blast\nprotection " + blastProtection), drawnRunway); //blast protection
                }
            } else {
                towardsLabel.setTextFill(Color.BLACK);
                sideRightTowardsArrowhead.setVisible(true);
                sideRightTowardsArrowline.setVisible(true);
                topRightTowardsArrowhead.setVisible(true);
                topRightTowardsArrowline.setVisible(true);
                towardsLabel.setText(right.getRunwayDesignatorName() + " Take off TOWARDS / Landing TOWARDS");
                drawLine((double) newTora / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()+slopeOrResa+ stripEnd)/ scale, pane, javafx.scene.paint.Color.GREEN, towardsPos+60,lineThickness,("TORA "+newTora),drawnRunway); //tora
                drawLine((double) stripEnd / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()+slopeOrResa) / scale, pane, javafx.scene.paint.Color.STEELBLUE, towardsPos+60,lineThickness,("strip\nend "+ stripEnd),drawnRunway); //strip end
                if (slopeCalc >= RESA) {
                    drawLine((double) slopeCalc / left.getTora(), (double) (left.getDisplacedThres() + obsLocation.getDistanceThresL()) / scale, pane, javafx.scene.paint.Color.MEDIUMORCHID, towardsPos + 60, lineThickness, ("slope " + slopeCalc), drawnRunway); //slope
                    drawSlope((double) slopeCalc / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()) / scale, 60, pane, Color.MEDIUMORCHID, true, ("slope")); //slope
                }else{
                    drawLine((double) RESA / left.getTora(), (double) (left.getDisplacedThres() + obsLocation.getDistanceThresL()) / scale, pane, javafx.scene.paint.Color.MAGENTA, towardsPos + 60, lineThickness, ("RESA " + RESA), drawnRunway); //resa
                }
                drawLine((double) newToda / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()+slopeOrResa+ stripEnd) / scale, pane, javafx.scene.paint.Color.RED, towardsPos+40,lineThickness,("TODA "+newToda),drawnRunway); //toda
                drawLine((double) newAsda / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()+slopeOrResa+ stripEnd) / scale, pane, javafx.scene.paint.Color.ORANGE, towardsPos+20,lineThickness,("ASDA "+newAsda),drawnRunway); //asda
                drawLine((double) newLda / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()+ RESA+ stripEnd) / scale, pane, javafx.scene.paint.Color.PURPLE, towardsPos,lineThickness,("LDA "+newLda),drawnRunway); //lda
                drawLine((double) stripEnd / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()+ RESA) / scale, pane, javafx.scene.paint.Color.STEELBLUE, towardsPos,lineThickness,("strip\nend "+ stripEnd),drawnRunway); //strip end
                drawLine((double) RESA / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()) / scale, pane, javafx.scene.paint.Color.MAGENTA, towardsPos,lineThickness,("RESA "+ RESA),drawnRunway); //resa
            }
            //removing the slope for top down view
            removeSlope();
        }
    }

    public void drawLine(double lengthFraction, double startXFraction, AnchorPane pane, javafx.scene.paint.Color color, int y, int thickness, String message, Rectangle drawnRunway) {

        double runwayLength = drawnRunway.getWidth();
        double startX = drawnRunway.getLayoutX() + startXFraction * runwayLength ;
        double lineLength = runwayLength * lengthFraction;
        double startY = drawnRunway.getY() + y;
        double endX = startX + lineLength;
        double endY = startY;

        if (lineLength>0) {
            Rectangle lengthLine = new Rectangle(startX, startY - thickness/2, lineLength, thickness);
            temporaryRect.add(lengthLine); //add to temporary list
            lengthLine.setFill(color);
            lengthLine.toFront();
            //changeColour(lengthLine);  //check if need to change colour

            //Line startMarker = new Line(startX, startY, startX, drawnRunway.getLayoutY() + drawnRunway.getHeight());
            Line startMarker = new Line(startX, startY, startX, drawnRunway.getLayoutY()+ drawnRunway.getHeight()/2);
            temporaryLine.add(startMarker);
            //Line endMarker = new Line(endX, startY, endX, drawnRunway.getLayoutY() + drawnRunway.getHeight());
            Line endMarker = new Line(endX, startY, endX, drawnRunway.getLayoutY()+ drawnRunway.getHeight()/2);
            temporaryLine.add(endMarker);
            pane.getChildren().addAll(lengthLine,startMarker,endMarker);

            Text text = new Text(message);
            temporaryText.add(text);
            text.setFont(Font.font("Arial", 10));
            text.setFill(color);

            //-----for colour blind purpose-------
            rectToChangeColour.add(lengthLine);
            labelToChangeColour.add(text);
            changeColour(lengthLine,text);  //check if need to change colour
            //------------------------------------

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


    public void setObstacle(Runway r, ObstacleLocation ol, AnchorPane pane,Rectangle drawnRunway) {
        obstacle = new Rectangle(0, 0, 30, 60);
        temporaryRect.add(obstacle);
        double startXFraction = (double) ol.getDistanceThresL()/(r.getLeftDesignator().getTora()-r.getLeftDesignator().getDisplacedThres()-r.getRightDesignator().getDisplacedThres());
        double drawnLength = drawnRunway.getWidth();
        double scaledLeftDisThres =  (double) r.getLeftDesignator().getDisplacedThres() / r.getLeftDesignator().getTora();
        double scaledRightDisThres = (double) r.getRightDesignator().getDisplacedThres() / r.getLeftDesignator().getTora();
        double startX = drawnRunway.getLayoutX() + scaledLeftDisThres*drawnLength;
        double endX = drawnRunway.getLayoutX() + drawnRunway.getWidth() - (scaledRightDisThres * drawnLength);
        double x = startX + ((endX - startX) * startXFraction) - (obstacle.getWidth() / 2);
        double y = drawnRunway.getLayoutY()  - obstacle.getHeight();  // set the Y position of the obstacle just above the runway

        obstacle.setX(x);
        obstacle.setY(y);

        obstacle.setFill(javafx.scene.paint.Color.RED);

        pane.getChildren().add(obstacle);
    }

    public void setTopObstacle(Runway r, ObstacleLocation ol, AnchorPane pane) {
        int length = 30;
        int width = 30;
        double distanceFromCenterline;
        if (ol.getDirection()== ObstacleLocation.Direction.Center){
            distanceFromCenterline = 0;
        }else if (ol.getDirection() == ObstacleLocation.Direction.North){
            distanceFromCenterline = (double) ol.getDistanceFromCenterline()/((double) averageRunwayWidth/2);
        }else {
            distanceFromCenterline = (double) -ol.getDistanceFromCenterline()/((double) averageRunwayWidth/2);
        }
        obstacle = new Rectangle(0, 0, length, width);
        temporaryRect.add(obstacle);
        double startXFraction = (double) ol.getDistanceThresL()/(r.getLeftDesignator().getTora()-r.getLeftDesignator().getDisplacedThres()-r.getRightDesignator().getDisplacedThres());
        double drawnLength = topRunway.getWidth();
        double scaledLeftDisThres =  (double) r.getLeftDesignator().getDisplacedThres() / r.getLeftDesignator().getTora();
        double scaledRightDisThres = (double) r.getRightDesignator().getDisplacedThres() / r.getLeftDesignator().getTora();
        double startX = topRunway.getLayoutX() + scaledLeftDisThres*drawnLength;
        double endX = topRunway.getLayoutX() + topRunway.getWidth() - (scaledRightDisThres * drawnLength);
        double x = startX + ((endX - startX) * startXFraction) - (obstacle.getWidth() / 2);
        double y = topRunway.getLayoutY() + (topRunway.getHeight() - obstacle.getHeight()) / 2 - distanceFromCenterline*topRunway.getHeight()/2;
        /*double y;
        if (distanceFromCenterline<0) {
            y = topRunway.getLayoutY() + (topRunway.getHeight() / 2) - (obstacle.getHeight()) - (distanceFromCenterline * topRunway.getHeight() / 2);
        }else{
            y = topRunway.getLayoutY() + (topRunway.getHeight() / 2) - (obstacle.getHeight()) - (distanceFromCenterline * topRunway.getHeight() / 2) + obstacle.getHeight();
        }*/

        obstacle.setX(x);
        obstacle.setY(y);

        obstacle.setFill(javafx.scene.paint.Color.RED);

        pane.getChildren().add(obstacle);
    }

    public void setRunway(Runway r, AnchorPane pane, Rectangle drawnRunway){
        double lineThickness = drawnRunway.getHeight();

        //get the number of right designator to configure the compass
        int rotationDegree = Integer.parseInt(r.getRightDesignator().getRunwayDesignatorName().substring(0,2))*10;
        compass.setRotate(rotationDegree);

        rotationSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double roundedValue = Math.round(newValue.doubleValue() * 10) / 10.0;
            rotationDegreeLabel.setText(String.format("%.1f", roundedValue) + "\u00B0");

            leftGridPane.setRotate(newValue.doubleValue());
            rightGridPane.setRotate(newValue.doubleValue());
            compass.setRotate(newValue.doubleValue()+rotationDegree);

            // Calculate the bounding box of the rotated grid pane
            Bounds bounds = topLeftPane.localToScene(topLeftPane.getBoundsInLocal());
            Bounds bounds2 = topRightPane.localToScene(topRightPane.getBoundsInLocal());

            // Calculate the translation that will center the grid pane within the scroll pane
            double offsetX = (leftScrollPane.getWidth() - bounds.getWidth()) / 2;
            double offsetY = (leftScrollPane.getHeight() - bounds.getHeight()) / 2;
            double maxOffsetX = Math.max(0, offsetX);
            double maxOffsetY = Math.max(0, offsetY);
            double minOffsetX = Math.min(0, offsetX - (leftScrollPane.getWidth() - bounds.getWidth()));
            double minOffsetY = Math.min(0, offsetY - (leftScrollPane.getHeight() - bounds.getHeight()));
            double translateX = Math.max(minOffsetX, Math.min(maxOffsetX, offsetX));
            double translateY = Math.max(minOffsetY, Math.min(maxOffsetY, offsetY));
            leftGridPane.setTranslateX(translateX);
            leftGridPane.setTranslateY(translateY);

            // Repeat for the right grid pane
            double offsetX2 = (rightScrollPane.getWidth() - bounds2.getWidth()) / 2;
            double offsetY2 = (rightScrollPane.getHeight() - bounds2.getHeight()) / 2;
            double maxOffsetX2 = Math.max(0, offsetX2);
            double maxOffsetY2 = Math.max(0, offsetY2);
            double minOffsetX2 = Math.min(0, offsetX2 - (rightScrollPane.getWidth() - bounds2.getWidth()));
            double minOffsetY2 = Math.min(0, offsetY2 - (rightScrollPane.getHeight() - bounds2.getHeight()));
            double translateX2 = Math.max(minOffsetX2, Math.min(maxOffsetX2, offsetX2));
            double translateY2 = Math.max(minOffsetY2, Math.min(maxOffsetY2, offsetY2));
            rightGridPane.setTranslateX(translateX2);
            rightGridPane.setTranslateY(translateY2);

            // Set the preferred size of the grid pane to fit within the visible area of the scroll pane
            leftGridPane.setPrefSize(bounds.getWidth(), bounds.getHeight());
            rightGridPane.setPrefSize(bounds2.getWidth(), bounds2.getHeight());
        });


        //get the name of the designators
        String leftDesig = r.getLeftDesignator().getRunwayDesignatorName();
        String rightDesig = r.getRightDesignator().getRunwayDesignatorName();

        topLeftLeftDesig.setText(leftDesig);
        topRightLeftDesig.setText(leftDesig);
        topLeftRightDesig.setText(rightDesig);
        topRightRightDesig.setText(rightDesig);

        sideLeftLeftDesig.setText(leftDesig.substring(0,2));
        sideRightLeftDesig.setText(leftDesig.substring(0,2));
        sideLeftRightDesig.setText(rightDesig.substring(0,2));
        sideRightRightDesig.setText(rightDesig.substring(0,2));

        //get clearway (toda - tora)
        int leftClearway = r.getRightDesignator().getClearway();  //get from right desig. because the length is measured from right desig.
        int rightClearway = r.getLeftDesignator().getClearway();
        //get stopway (asda - tora)
        int leftStopway = r.getRightDesignator().getStopway();  //get from right desig. because the length is measured from right desig.
        int rightStopway = r.getLeftDesignator().getStopway();

        drawLine((double) r.getLeftDesignator().getDisplacedThres() / r.getLeftDesignator().getTora(), (double) 0, pane, javafx.scene.paint.Color.SLATEGRAY, (int) ((int) drawnRunway.getLayoutY()+lineThickness/2), (int) lineThickness,("displaced\nthreshold "+r.getLeftDesignator().getDisplacedThres()),drawnRunway); // left disp thres
        drawLine((double) r.getRightDesignator().getDisplacedThres() / r.getLeftDesignator().getTora(), (1-(double)r.getRightDesignator().getDisplacedThres() / r.getLeftDesignator().getTora()), pane, javafx.scene.paint.Color.SLATEGRAY, (int) ((int) drawnRunway.getLayoutY()+lineThickness/2), (int) lineThickness,("displaced\nthreshold "+r.getRightDesignator().getDisplacedThres()),drawnRunway); // right disp thres

        drawLine((double) leftClearway / r.getLeftDesignator().getTora(), (double) -leftClearway / r.getRightDesignator().getTora(), pane, javafx.scene.paint.Color.CYAN, (int) ((int) drawnRunway.getLayoutY()+lineThickness/2), (int) lineThickness,("Clearway "+leftClearway),drawnRunway); // left clearway
        drawLine((double) rightClearway / r.getLeftDesignator().getTora(), (double) r.getLeftDesignator().getTora() / r.getLeftDesignator().getTora(), pane, javafx.scene.paint.Color.CYAN, (int) ((int) drawnRunway.getLayoutY()+lineThickness/2), (int) lineThickness,("Clearway "+rightClearway),drawnRunway); // right clearway

        drawLine((double) leftStopway / r.getLeftDesignator().getTora(), (double) -leftStopway / r.getRightDesignator().getTora(), pane, javafx.scene.paint.Color.LAVENDER, (int) ((int) drawnRunway.getLayoutY()+lineThickness/2), (int) lineThickness,("Stopway "+leftStopway),drawnRunway); // left stopway
        drawLine((double) rightStopway / r.getLeftDesignator().getTora(), (double) r.getLeftDesignator().getTora() / r.getRightDesignator().getTora(), pane, Color.LAVENDER, (int) ((int) drawnRunway.getLayoutY()+lineThickness/2), (int) lineThickness,("Stopway "+rightStopway),drawnRunway); // right clearway

    }

    public void switchPane() {
        // Toggle visibility of pane1 and pane2
        sideLeftPane.setVisible(!sideLeftPane.isVisible());
        sideRightPane.setVisible(!sideRightPane.isVisible());
    }

    public void switchTopViewPane() {
        // Toggle visibility of pane1 and pane2
        leftScrollPane.setVisible(!leftScrollPane.isVisible());
        rightScrollPane.setVisible(!rightScrollPane.isVisible());

        topLeftAwayLabel.setVisible(!topLeftAwayLabel.isVisible());
        topLeftTowardsLabel.setVisible(!topLeftTowardsLabel.isVisible());
        topRightAwayLabel.setVisible(!topRightAwayLabel.isVisible());
        topRightTowardsLabel.setVisible(!topRightTowardsLabel.isVisible());
    }

    public void removeObjects(){
        for (Rectangle r : temporaryRect){
            sideLeftPane.getChildren().remove(r);
            sideRightPane.getChildren().remove(r);
            topLeftPane.getChildren().remove(r);
            topRightPane.getChildren().remove(r);
        }
        for (Line l : temporaryLine){
            sideLeftPane.getChildren().remove(l);
            sideRightPane.getChildren().remove(l);
            topLeftPane.getChildren().remove(l);
            topRightPane.getChildren().remove(l);
        }
        for (Text l : temporaryText){
            sideLeftPane.getChildren().remove(l);
            sideRightPane.getChildren().remove(l);
            topLeftPane.getChildren().remove(l);
            topRightPane.getChildren().remove(l);
        }
        for (Polygon l : temporaryPolygons){
            sideLeftPane.getChildren().remove(l);
            sideRightPane.getChildren().remove(l);
        }


    }

    public void drawSlope(double lengthFraction, double startXFraction, double slopeHeight, AnchorPane pane, Color color, boolean slopeUp,  String message) {
        double runwayLength = sideRunway.getWidth();
        double startX = sideRunway.getLayoutX() + startXFraction * runwayLength;
        double slopeLength = runwayLength * lengthFraction;
        double startY = sideRunway.getLayoutY();
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
        polygonText.add(text);
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

    public void removeSlope(){
        for (Polygon p : temporaryPolygons){
            topLeftPane.getChildren().remove(p);
            topRightPane.getChildren().remove(p);
        }
        for (Text t : polygonText){
            topLeftPane.getChildren().remove(t);
            topRightPane.getChildren().remove(t);
        }
    }

    public void flip(){
        //flipping the diagrams
        sideLeftPane.setScaleX(-1);
        sideRightPane.setScaleX(-1);
        topLeftPane.setScaleX(-1);
        topRightPane.setScaleX(-1);

        //flip back the components which do not require to be flipped initially
        sideLeftAwayLabel.setScaleX(-1);
        sideLeftTowardsLabel.setScaleX(-1);
        sideRightAwayLabel.setScaleX(-1);
        sideRightTowardsLabel.setScaleX(-1);

        topLeftAwayLabel.setScaleX(-1);
        topLeftTowardsLabel.setScaleX(-1);
        topRightAwayLabel.setScaleX(-1);
        topRightTowardsLabel.setScaleX(-1);

        topLeftLeftDesig.setScaleX(-1);
        topLeftRightDesig.setScaleX(-1);
        topRightLeftDesig.setScaleX(-1);
        topRightRightDesig.setScaleX(-1);

        sideLeftLeftDesig.setScaleX(-1);
        sideLeftRightDesig.setScaleX(-1);
        sideRightLeftDesig.setScaleX(-1);
        sideRightRightDesig.setScaleX(-1);

        sideLeftLeftDesigLabel.setScaleX(-1);
        sideLeftRightDesigLabel.setScaleX(-1);
        sideRightLeftDesigLabel.setScaleX(-1);
        sideRightRightDesigLabel.setScaleX(-1);


        for (Text t : temporaryText){
            t.setScaleX(-1);
        }
    }

    public void unflip(){
        //flipping the diagrams
        sideLeftPane.setScaleX(1);
        sideRightPane.setScaleX(1);
        topLeftPane.setScaleX(1);
        topRightPane.setScaleX(1);

        //flip back the components which do not require to be flipped initially
        sideLeftAwayLabel.setScaleX(1);
        sideLeftTowardsLabel.setScaleX(1);
        sideRightAwayLabel.setScaleX(1);
        sideRightTowardsLabel.setScaleX(1);

        topLeftAwayLabel.setScaleX(1);
        topLeftTowardsLabel.setScaleX(1);
        topRightAwayLabel.setScaleX(1);
        topRightTowardsLabel.setScaleX(1);

        topLeftLeftDesig.setScaleX(1);
        topLeftRightDesig.setScaleX(1);
        topRightLeftDesig.setScaleX(1);
        topRightRightDesig.setScaleX(1);

        sideLeftLeftDesig.setScaleX(1);
        sideLeftRightDesig.setScaleX(1);
        sideRightLeftDesig.setScaleX(1);
        sideRightRightDesig.setScaleX(1);

        sideLeftLeftDesigLabel.setScaleX(1);
        sideLeftRightDesigLabel.setScaleX(1);
        sideRightLeftDesigLabel.setScaleX(1);
        sideRightRightDesigLabel.setScaleX(1);

        for (Text t : temporaryText){
            t.setScaleX(1);
        }
    }

    public void recentre() {
        zoomSlider.setValue(1);
        rotationSlider.setValue(0);
    }


//    @FXML
//    private void export1() {
//
//        sideSwitchSideButton.setVisible(false);
//        rotationSlider.setVisible(false);
//        zoomSlider.setVisible(false);
//        topSwitchSideButton.setVisible(false);
//        recentreButton.setVisible(false);
//        rotateLabel.setVisible(false);
//        zoomLabel.setVisible(false);
//        rotationDegreeLabel.setVisible(false);
//        zoomScaleLabel.setVisible(false);
//
//        WritableImage snapshot = TabPane.snapshot(new SnapshotParameters(), null);
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Save Visualization");
//        fileChooser.setInitialFileName("visualization.png");
//        fileChooser.getExtensionFilters().addAll(
//                new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png"),
//                new FileChooser.ExtensionFilter("JPEG files (*.jpg, *.jpeg)", "*.jpg", "*.jpeg"),
//                new FileChooser.ExtensionFilter("Bitmap files (*.bmp)", "*.bmp"),
//                new FileChooser.ExtensionFilter("GIF files (*.gif)", "*.gif")
//        );
//        File file = fileChooser.showSaveDialog(null);
//        if (file != null) {
//            try {
//                String extension = getFileExtension(file.getName());
//                ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), extension, file);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        sideSwitchSideButton.setVisible(true);
//        rotationSlider.setVisible(true);
//        zoomSlider.setVisible(true);
//        topSwitchSideButton.setVisible(true);
//        recentreButton.setVisible(true);
//        rotateLabel.setVisible(true);
//        zoomLabel.setVisible(true);
//        rotationDegreeLabel.setVisible(true);
//        zoomScaleLabel.setVisible(true);
//
//    }
//
//    private String getFileExtension(String fileName) {
//        int dotIndex = fileName.lastIndexOf('.');
//        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
//            return fileName.substring(dotIndex + 1).toLowerCase();
//        } else {
//            return "png"; // Default to PNG if file extension is not found
//        }
//    }

    @FXML
    private void export1() {
        // Hide the UI elements that are not part of the visualization
        sideSwitchSideButton.setVisible(false);
        rotationSlider.setVisible(false);
        zoomSlider.setVisible(false);
        topSwitchSideButton.setVisible(false);
        recentreButton.setVisible(false);
        rotateLabel.setVisible(false);
        zoomLabel.setVisible(false);
        rotationDegreeLabel.setVisible(false);
        zoomScaleLabel.setVisible(false);

        // Take a snapshot of the visualization
        WritableImage snapshot = TabPane.snapshot(new SnapshotParameters(), null);

        // Create a file chooser dialog
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Visualization");

        // Set the default file name to the current date and time
        String defaultFileName = "Visualization_" + getCurrentDateTime() + ".png";
        fileChooser.setInitialFileName(defaultFileName);

        // Set the file extension filters
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png"),
                new FileChooser.ExtensionFilter("JPEG files (*.jpg, *.jpeg)", "*.jpg", "*.jpeg"),
                new FileChooser.ExtensionFilter("Bitmap files (*.bmp)", "*.bmp"),
                new FileChooser.ExtensionFilter("GIF files (*.gif)", "*.gif")
        );

        // Show the dialog and get the selected file
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                // Get the file extension
                String extension = getFileExtension(file.getName());

                // Write the snapshot to the selected file
                ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), extension, file);

                System.out.println("File saved successfully: " + file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Show the UI elements again
        sideSwitchSideButton.setVisible(true);
        rotationSlider.setVisible(true);
        zoomSlider.setVisible(true);
        topSwitchSideButton.setVisible(true);
        recentreButton.setVisible(true);
        rotateLabel.setVisible(true);
        zoomLabel.setVisible(true);
        rotationDegreeLabel.setVisible(true);
        zoomScaleLabel.setVisible(true);
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex >= 0) {
            return fileName.substring(dotIndex + 1);
        } else {
            return "";
        }
    }

    private String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return dateFormat.format(new Date());
    }

    /*private void changeColour(Rectangle rectangle) {
        if (!rectangle.getProperties().containsKey("originalColor")) { // check if the original color has already been stored
            rectangle.getProperties().put("originalColor", rectangle.getFill()); // store the original color
        }
        Color originalColor = (Color) rectangle.getProperties().get("originalColor");
        if (toggleOn) {  //if toggle button is on ....
            if (temporaryRect.indexOf(rectangle) % 2 == 0) {   // alternating between these 2 colours
                rectangle.setFill(Color.DARKSLATEBLUE);
            } else {
                rectangle.setFill(Color.DARKKHAKI);
            }
        } else {  // if toggle button is off, switch back to original colour
            rectangle.setFill(originalColor);
        }
    }


    public void toggleColour(){
        toggleOn = colourBlindToggle.isSelected(); // toggle the boolean variable when the toggle button is clicked
        for (Rectangle rectangle : temporaryRect) {
            changeColour(rectangle);
        }
    }*/

    private void changeColour(Rectangle rectangle, Text text) {
        // Store the original color if it hasn't been stored yet
        if (!rectangle.getProperties().containsKey("originalColor")) {
            rectangle.getProperties().put("originalColor", rectangle.getFill());
            //text.getProperties().put("originalColor", text.getFill());
        }
        if (!text.getProperties().containsKey("originalTextColor")) {
            text.getProperties().put("originalTextColor", text.getFill());
        }

        // Get the original color of the rectangle and text
        Color originalColor = (Color) rectangle.getProperties().get("originalColor");
        Color originalTextColor = (Color) text.getProperties().get("originalTextColor");

        // Check if the toggle button is on
        if (toggleOn) {
            // Use the same color for both the rectangle and text
            Color nextColor = getNextColor();
            rectangle.setFill(nextColor);
            text.setFill(nextColor);
        } else {
            // If the toggle button is off, switch back to the original color
            rectangle.setFill(originalColor);
            text.setFill(originalTextColor);
        }
    }

    public void toggleColour(){
        toggleOn = colourBlindToggle.isSelected(); // toggle the boolean variable when the toggle button is clicked
        for (int i = 0; i < rectToChangeColour.size(); i++) {
            //Rectangle rectangle = temporaryRect.get(i);
            //Text text = temporaryText.get(i);
            Rectangle rectangle = rectToChangeColour.get(i);
            Text text = labelToChangeColour.get(i);
            changeColour(rectangle, text);
        }
        lastColorIndex = -1;  //reset the colour index to ensure same colour instead of random colour everytime clicking the toggle button
    }

    private int lastColorIndex = -1; // Keep track of the last color index returned
    private Color getNextColor() {
        // Define an array of available colors
        Color[] availableColors = {Color.web("#0000FF"), // Pure blue
                Color.web("#0066CC"), // Deep sky blue
                Color.web("#1E90FF"), // Dodger blue
                Color.web("#6495ED"), // Cornflower blue
                Color.web("#008000"), // Green
                Color.web("#006400"), // Dark green
                Color.web("#228B22"), // Forest green
                Color.web("#ADFF2F"), // Green-yellow
                Color.web("#800080"), // Purple
                Color.web("#BA55D3"), // Medium orchid
                Color.web("#9370DB"), // Medium purple
                Color.web("#8A2BE2"), // Blue-violet
                Color.web("#FFFF00"), // Pure yellow
                Color.web("#FFD700"), // Gold
                Color.web("#FFA500"), // Orange
                Color.web("#FFFFE0")  // Light yellow
        };
        lastColorIndex++; // Increment the last color index
        if (lastColorIndex >= availableColors.length) {
            lastColorIndex = 0; // If we reach the end of the array, start over
        }
        return availableColors[lastColorIndex];
    }




}



