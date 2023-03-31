package com.example.runwayproject.Controller;

import com.example.runwayproject.Connector.DbConnect;
import com.example.runwayproject.Model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import static com.example.runwayproject.Model.Calculator.*;

public class ATCController extends MainController {


    @FXML
    private MenuItem about;

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
    private Label airportNameSV;

    @FXML
    private Label airportNameTD;

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
    private Button sideViewLeftButton;
    @FXML
    private Button sideViewRightButton;

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
    private Button topViewLeftButton;
    @FXML
    private Button topViewRightButton;

    ArrayList<javafx.scene.shape.Rectangle> temporaryRect = new ArrayList<Rectangle>();
    ArrayList<Line> temporaryLine = new ArrayList<Line>();
    ArrayList<Text> temporaryText = new ArrayList<Text>();

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
            formatTable(oriDistanceTable);
            formatTable(recAwayDistanceTable);
            formatTable(runwayTable);
            setConstants();
            setAirportName();
            setTextArea();

            sideLeftPane.setVisible(true);
            sideRightPane.setVisible(false);
            topLeftPane.setVisible(true);
            topRightPane.setVisible(false);

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


            setRunwayComboBox();
        } catch (SQLException e) {
            playErrorAlert(String.valueOf(e));
        }
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
        String runway = runwayComboBox.getValue();
        try{
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
            while(resultSet.next()){
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
                    "WHERE r.runway_name = '" + runway +"' LIMIT 1;");
            resultSet = preparedStatement.executeQuery();

            ObstacleLocation obstacleLocation = new ObstacleLocation();
            while (resultSet.next()){
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
                    "                    WHERE r.runway_name = '" + runway +"';");
            resultSet = preparedStatement.executeQuery();

            ObservableList<RunwayDesignator> runwayDesignators = FXCollections.observableArrayList();
            while (resultSet.next()){
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
            for (RunwayDesignator i : runwayDesignators){
                if(i.getRunwayDesignatorName().endsWith("L")){
                    currentRunway.setLeft(i);
                }else{
                    currentRunway.setRight(i);
                }
            }
            currentRunway.setRunwayName(runway);

            ArrayList<String> toraBreakdown = new ArrayList<>();
            ArrayList<String> todaBreakdown = new ArrayList<>();
            ArrayList<String> asdaBreakdown = new ArrayList<>();
            ArrayList<String> ldaBreakdown = new ArrayList<>();

            for (RunwayDesignator i: runwayDesignators){
                //Take off away, Landing over
                char side = i.getRunwayDesignatorName().charAt(i.getRunwayDesignatorName().length()-1);
                if(side=='L'){
                    int newToraA = calcTORA(Status.away, i, obstacle, obstacleLocation);
                    int newTodaA = calcTODA(Status.away,i,obstacle,obstacleLocation);
                    int newAsdaA = calcASDA(Status.away,i,obstacle,obstacleLocation);
                    int newLdaA = calcLDA(Status.over,i,obstacle,obstacleLocation);

                    int newToraT = calcTORA(Status.towards, i, obstacle, obstacleLocation);
                    int newTodaT = calcTODA(Status.towards,i,obstacle,obstacleLocation);
                    int newAsdaT = calcASDA(Status.towards,i,obstacle,obstacleLocation);
                    int newLdaT = calcLDA(Status.towards,i,obstacle,obstacleLocation);

                    recAwayTableList.add(new RecTable(i.getRunwayDesignatorName(),newToraA, newTodaA, newLdaA, newAsdaA));
                    recTowardTableList.add(new RecTable(i.getRunwayDesignatorName(),newToraT, newTodaT, newLdaT, newAsdaT));

                    toraBreakdown.add(printTORA(Status.away, i, obstacle, obstacleLocation));
                    toraBreakdown.add(printTORA(Status.towards, i, obstacle, obstacleLocation));
                    todaBreakdown.add(printTODA(Status.away, i, obstacle, obstacleLocation));
                    todaBreakdown.add(printTODA(Status.towards, i, obstacle, obstacleLocation));
                    asdaBreakdown.add(printASDA(Status.away, i, obstacle, obstacleLocation));
                    asdaBreakdown.add(printASDA(Status.towards, i, obstacle, obstacleLocation));
                    ldaBreakdown.add(printLDA(Status.over, i, obstacle, obstacleLocation));
                    ldaBreakdown.add(printLDA(Status.towards, i, obstacle, obstacleLocation));

                }else if (side=='R'){
                    int newToraA = calcTORA(Status.away, i, obstacle, obstacleLocation);
                    int newTodaA = calcTODA(Status.away,i,obstacle,obstacleLocation);
                    int newAsdaA = calcASDA(Status.away,i,obstacle,obstacleLocation);
                    int newLdaA = calcLDA(Status.over,i,obstacle,obstacleLocation);

                    int newToraT = calcTORA(Status.towards, i, obstacle, obstacleLocation);
                    int newTodaT = calcTODA(Status.towards,i,obstacle,obstacleLocation);
                    int newAsdaT = calcASDA(Status.towards,i,obstacle,obstacleLocation);
                    int newLdaT = calcLDA(Status.towards,i,obstacle,obstacleLocation);

                    recAwayTableList.add(new RecTable(i.getRunwayDesignatorName(),newToraA, newTodaA, newLdaA, newAsdaA));
                    recTowardTableList.add(new RecTable(i.getRunwayDesignatorName(),newToraT, newTodaT, newLdaT, newAsdaT));

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
            Collections.swap(toraBreakdown,1,2);
            Collections.swap(todaBreakdown,1,2);
            Collections.swap(asdaBreakdown,1,2);
            Collections.swap(ldaBreakdown,1,2);

            toraTextArea.clear();
            todaTextArea.clear();
            asdaTextArea.clear();
            ldaTextArea.clear();

            for(String text : toraBreakdown){
                toraTextArea.appendText(text+"\n");
            }
            for(String text : todaBreakdown){
                todaTextArea.appendText(text+"\n");
            }
            for(String text : asdaBreakdown){
                asdaTextArea.appendText(text+"\n");
            }
            for(String text : ldaBreakdown){
                ldaTextArea.appendText(text+"\n");
            }

            recAwayDistanceTable.setItems(recAwayTableList);
            recTowardDistanceTable.setItems(recTowardTableList);

            removeObjects();
            sideView(currentRunway,obstacle,obstacleLocation,sideLeftPane,sideRightPane,sideRunway,sideLeftAwayLabel,sideLeftTowardsLabel,sideRightAwayLabel,sideRightTowardsLabel);
            topView(currentRunway,obstacle,obstacleLocation,topLeftPane,topRightPane,topRunway,topLeftAwayLabel,topLeftTowardsLabel,topRightAwayLabel,topRightTowardsLabel);

            connection.close();
            preparedStatement.close();
            resultSet.close();

        }catch (SQLException e){
            playErrorAlert(String.valueOf(e));
        }
    }

    public void loadRecTable (ActionEvent event){
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

    public void loadData(ActionEvent event){
        loadRunwayTable(event);
        loadRecTable(event);
        toraTextArea.positionCaret(0);
        todaTextArea.positionCaret(0);
        asdaTextArea.positionCaret(0);
        ldaTextArea.positionCaret(0);
    }

    @FXML
    void hyperlink(ActionEvent event) throws URISyntaxException, IOException {
        System.out.println("opened");
        Desktop.getDesktop().browse(new URI("https://drive.google.com/file/d/1A0YGkIcy6O6BGTx-QHKhXmDhOp5zt4D3/view?usp=sharing"));

    }

    ////side visualisation
    public void sideView(Runway r, Obstacle o, ObstacleLocation ol, AnchorPane pane, AnchorPane pane2, Rectangle drawnRunway, Label leftAwayLabel, Label leftTowardsLabel, Label rightAwayLabel, Label rightTowardsLabel){
        setRunway(r,pane,drawnRunway);
        setObstacle(r,ol,pane,drawnRunway);
        setRunway(r,pane2,drawnRunway);
        setObstacle(r,ol,pane2,drawnRunway);

         viewLeft(r,o,ol,pane,drawnRunway,leftAwayLabel,leftTowardsLabel);
         viewRight(r,o,ol,pane2,drawnRunway,rightAwayLabel,rightTowardsLabel);
    }

    public void topView(Runway r, Obstacle o, ObstacleLocation ol, AnchorPane pane, AnchorPane pane2, Rectangle drawnRunway, Label leftAwayLabel, Label leftTowardsLabel, Label rightAwayLabel, Label rightTowardsLabel){
        setRunway(r,pane,drawnRunway);
        setTopObstacle(r,ol,pane,0);
        setRunway(r,pane2,drawnRunway);
        setTopObstacle(r,ol,pane2,0);

        viewLeft(r,o,ol,pane,drawnRunway,leftAwayLabel,leftTowardsLabel);
        viewRight(r,o,ol,pane2,drawnRunway,rightAwayLabel,rightTowardsLabel);
    }

    public void viewLeft(Runway r,Obstacle o,ObstacleLocation ol, AnchorPane pane, Rectangle drawnRunway, Label awayLabel, Label towardsLabel){
        //setRunway(r,pane,drawnRunway);
        //setObstacle(r,ol,pane,drawnRunway);
        drawLeft(Calculator.Status.away, Calculator.Status.over,r,o,ol,pane,drawnRunway,awayLabel,towardsLabel);
        drawLeft(Calculator.Status.towards, Calculator.Status.towards,r,o,ol,pane,drawnRunway,awayLabel,towardsLabel);
    }

    public void viewRight(Runway r,Obstacle o,ObstacleLocation ol, AnchorPane pane, Rectangle drawnRunway, Label awayLabel, Label towardsLabel){
        //setRunway(r,pane,drawnRunway);
        //setObstacle(r,ol,pane,drawnRunway);
        drawRight(Calculator.Status.away, Calculator.Status.over,r,o,ol,pane,drawnRunway,awayLabel,towardsLabel);
        drawRight(Calculator.Status.towards, Calculator.Status.towards,r,o,ol,pane,drawnRunway,awayLabel,towardsLabel);
    }

    public void drawLeft(Status takeOffStatus, Status landingStatus, Runway r, Obstacle obs, ObstacleLocation obsLocation, AnchorPane pane, Rectangle drawnRunway, Label awayLabel, Label towardsLabel) {
        RunwayDesignator left = r.getLeftDesignator();
        RunwayDesignator right = r.getRightDesignator();
        int scale = left.getTora();
        int newTora = calcTORA(takeOffStatus, left,obs,obsLocation);
        int newToda = calcTODA(takeOffStatus, left,obs,obsLocation);
        int newAsda = calcASDA(takeOffStatus, left,obs,obsLocation);
        int newLda = calcLDA(landingStatus, left,obs,obsLocation);
        int slopeCalc = (slope*obs.getHeight());
        int lineThickness = 6;
        int awayPos = 200;
        int towardsPos = 500;
        towardsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        awayLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        AnchorPane.setLeftAnchor(awayLabel,10.0);
        AnchorPane.setTopAnchor(awayLabel,10.0);
        AnchorPane.setLeftAnchor(towardsLabel,10.0);
        AnchorPane.setBottomAnchor(towardsLabel,10.0);

        if (newTora < minRunDistance){
            if (takeOffStatus == Status.away && landingStatus == Status.over) {
                awayLabel.setText(left.getRunwayDesignatorName() + " Not suitable for Take off " +takeOffStatus + " / Landing " + landingStatus);
                awayLabel.setTextFill(javafx.scene.paint.Color.RED);
            }else{
                towardsLabel.setText(left.getRunwayDesignatorName() + " Not suitable for Take off " +takeOffStatus + " / Landing " + landingStatus);
                towardsLabel.setTextFill(javafx.scene.paint.Color.RED);
            }
        }else {
            if (takeOffStatus == Status.away && landingStatus == Status.over) {
                awayLabel.setText(left.getRunwayDesignatorName() + " Take off AWAY / Landing OVER  ---------->");
                drawLine((double) newTora / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL() + blastProtection) / scale, pane, javafx.scene.paint.Color.GREEN, awayPos,lineThickness,("TORA "+newTora),drawnRunway); //tora
                drawLine((double) blastProtection / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()) / scale, pane, javafx.scene.paint.Color.BLUE, awayPos,lineThickness,("blast\nprotection "+ blastProtection),drawnRunway); //blast protection
                drawLine((double) newToda / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL() + blastProtection) / scale, pane, javafx.scene.paint.Color.RED, awayPos+20,lineThickness,("TODA "+newToda),drawnRunway); //toda
                drawLine((double) newAsda / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL() + blastProtection) / scale, pane, javafx.scene.paint.Color.ORANGE, awayPos+40,lineThickness,("ASDA "+newAsda),drawnRunway); //asda
                drawLine((double) newLda / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL() + stripEnd + slopeCalc) / scale, pane, javafx.scene.paint.Color.PURPLE, awayPos+60,lineThickness,("LDA "+newLda),drawnRunway); //lda
                drawLine((double) slopeCalc / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()) / scale, pane, javafx.scene.paint.Color.MEDIUMORCHID, awayPos+60,lineThickness,("slope "+slopeCalc),drawnRunway); //slope
                drawLine((double) stripEnd / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL() + slopeCalc) / scale, pane, javafx.scene.paint.Color.STEELBLUE, awayPos+60,lineThickness,("strip\nend "+ stripEnd),drawnRunway); //strip end
            } else {
                towardsLabel.setText(left.getRunwayDesignatorName() + " Take off TOWARDS / Landing TOWARDS  ---------->");
                drawLine((double) newTora / scale, (double) 0 / scale, pane, javafx.scene.paint.Color.GREEN, towardsPos+60,lineThickness,("TORA "+newTora),drawnRunway); //tora
                //draw((double) rd.getDisplacedThres() / rd.getTora(), (double) 0 / rd.getTora(), pane, Color.GREEN, 50); //displaced threshold
                drawLine((double) stripEnd / scale, (double) newTora / scale, pane, javafx.scene.paint.Color.STEELBLUE, towardsPos+60,lineThickness,("strip\nend "+ stripEnd),drawnRunway); //strip end
                drawLine((double) slopeCalc / scale, (double) (newTora + stripEnd ) / scale, pane, javafx.scene.paint.Color.MEDIUMORCHID, towardsPos+60,lineThickness,("slope "+slopeCalc),drawnRunway); //slope
                drawLine((double) newToda / scale, (double) 0 / scale, pane, javafx.scene.paint.Color.RED, towardsPos+40,lineThickness,("TODA "+newToda),drawnRunway); //toda
                drawLine((double) newAsda / scale, (double) 0 / scale, pane, javafx.scene.paint.Color.ORANGE, towardsPos+20,lineThickness,("ASDA "+newAsda),drawnRunway); //asda
                drawLine((double) newLda / scale, (double) left.getDisplacedThres() / scale, pane, javafx.scene.paint.Color.PURPLE, towardsPos,lineThickness,("LDA "+newLda),drawnRunway); //lda
                drawLine((double) stripEnd / scale, (double) (newLda+left.getDisplacedThres()) / scale, pane, javafx.scene.paint.Color.STEELBLUE, towardsPos,lineThickness,("strip\nend "+ stripEnd),drawnRunway); //strip end
                drawLine((double) RESA / scale, (double) (newLda +left.getDisplacedThres()+ stripEnd) / scale, pane, javafx.scene.paint.Color.MAGENTA, towardsPos,lineThickness,("RESA "+ RESA),drawnRunway); //resa
            }
        }
    }

    public void drawRight(Status takeOffStatus, Status landingStatus, Runway r, Obstacle obs, ObstacleLocation obsLocation, AnchorPane pane, Rectangle drawnRunway, Label awayLabel, Label towardsLabel) {
        RunwayDesignator left = r.getLeftDesignator();
        RunwayDesignator right = r.getRightDesignator();
        int scale = right.getTora();
        int newTora = calcTORA(takeOffStatus, right,obs,obsLocation);
        int newToda = calcTODA(takeOffStatus, right,obs,obsLocation);
        int newAsda = calcASDA(takeOffStatus, right,obs,obsLocation);
        int newLda = calcLDA(landingStatus, right,obs,obsLocation);
        int slopeCalc = (slope*obs.getHeight());
        int lineThickness = 6;
        int awayPos = 200;
        int towardsPos = 500;
        towardsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        awayLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        AnchorPane.setRightAnchor(awayLabel,10.0);
        AnchorPane.setTopAnchor(awayLabel,10.0);
        AnchorPane.setRightAnchor(towardsLabel,10.0);
        AnchorPane.setBottomAnchor(towardsLabel,10.0);

        if (newTora < minRunDistance){
            if (takeOffStatus == Status.away && landingStatus == Status.over) {
                awayLabel.setText(right.getRunwayDesignatorName() + " Not suitable for Take off " +takeOffStatus + " / Landing " + landingStatus);
                awayLabel.setTextFill(javafx.scene.paint.Color.RED);
            }else{
                towardsLabel.setText(right.getRunwayDesignatorName() + " Not suitable for Take off " +takeOffStatus + " / Landing " + landingStatus);
                towardsLabel.setTextFill(javafx.scene.paint.Color.RED);
            }
        }else {
            if (takeOffStatus == Status.away && landingStatus == Status.over) {
                awayLabel.setText("<----------  " + right.getRunwayDesignatorName() + " Take off AWAY / Landing OVER");
                drawLine((double) newTora / scale, (double) 0 / scale, pane, javafx.scene.paint.Color.GREEN, awayPos,lineThickness,("TORA "+newTora),drawnRunway); //tora
                drawLine((double) blastProtection / scale, (double) newTora / scale, pane, javafx.scene.paint.Color.BLUE, awayPos,lineThickness,("blast\nprotection "+ blastProtection),drawnRunway); //blast protection
                drawLine((double) newToda / scale, (double) (newTora-newToda) / scale, pane, javafx.scene.paint.Color.RED, awayPos+20,lineThickness,("TODA "+newToda),drawnRunway); //toda
                drawLine((double) newAsda / scale, (double) (newTora-newAsda) / scale, pane, javafx.scene.paint.Color.ORANGE, awayPos+40,lineThickness,("ASDA "+newAsda),drawnRunway); //asda
                drawLine((double) newLda / scale, (double) 0 / scale, pane, javafx.scene.paint.Color.PURPLE, awayPos+60,lineThickness,("LDA "+newLda),drawnRunway); //lda
                drawLine((double) stripEnd / scale, (double) newLda / scale, pane, javafx.scene.paint.Color.STEELBLUE, awayPos+60,lineThickness,("strip\nend "+ stripEnd),drawnRunway); //strip end
                drawLine((double) slopeCalc / scale, (double) (newLda+ stripEnd) / scale, pane, javafx.scene.paint.Color.MEDIUMORCHID, awayPos+60,lineThickness,("slope "+slopeCalc),drawnRunway); //slope
            } else {
                towardsLabel.setText("<----------  " + right.getRunwayDesignatorName() + " Take off TOWARDS / Landing TOWARDS");
                drawLine((double) newTora / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()+slopeCalc+ stripEnd)/ scale, pane, javafx.scene.paint.Color.GREEN, towardsPos+60,lineThickness,("TORA "+newTora),drawnRunway); //tora
                drawLine((double) stripEnd / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()+slopeCalc) / scale, pane, javafx.scene.paint.Color.STEELBLUE, towardsPos+60,lineThickness,("strip\nend "+ stripEnd),drawnRunway); //strip end
                drawLine((double) slopeCalc / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL())/ scale, pane, javafx.scene.paint.Color.MEDIUMORCHID, towardsPos+60,lineThickness,("slope "+slopeCalc),drawnRunway); //slope
                drawLine((double) newToda / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()+slopeCalc+ stripEnd) / scale, pane, javafx.scene.paint.Color.RED, towardsPos+40,lineThickness,("TODA "+newToda),drawnRunway); //toda
                drawLine((double) newAsda / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()+slopeCalc+ stripEnd) / scale, pane, javafx.scene.paint.Color.ORANGE, towardsPos+20,lineThickness,("ASDA "+newAsda),drawnRunway); //asda
                drawLine((double) newLda / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()+ RESA+ stripEnd) / scale, pane, javafx.scene.paint.Color.PURPLE, towardsPos,lineThickness,("LDA "+newLda),drawnRunway); //lda
                drawLine((double) stripEnd / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()+ RESA) / scale, pane, javafx.scene.paint.Color.STEELBLUE, towardsPos,lineThickness,("strip\nend "+ stripEnd),drawnRunway); //strip end
                drawLine((double) RESA / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()) / scale, pane, javafx.scene.paint.Color.MAGENTA, towardsPos,lineThickness,("RESA "+ RESA),drawnRunway); //resa
            }
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

            Line startMarker = new Line(startX, startY, startX, drawnRunway.getLayoutY() + drawnRunway.getHeight());
            temporaryLine.add(startMarker);
            Line endMarker = new Line(endX, startY, endX, drawnRunway.getLayoutY() + drawnRunway.getHeight());
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

    public void setTopObstacle(Runway r, ObstacleLocation ol, AnchorPane pane, double distanceFromCenterline) {
        int length = 30;
        int width = 30;
        obstacle = new Rectangle(0, 0, length, width);
        temporaryRect.add(obstacle);
        double startXFraction = (double) ol.getDistanceThresL()/(r.getLeftDesignator().getTora()-r.getLeftDesignator().getDisplacedThres()-r.getRightDesignator().getDisplacedThres());
        double drawnLength = topRunway.getWidth();
        double scaledLeftDisThres =  (double) r.getLeftDesignator().getDisplacedThres() / r.getLeftDesignator().getTora();
        double scaledRightDisThres = (double) r.getRightDesignator().getDisplacedThres() / r.getLeftDesignator().getTora();
        double startX = topRunway.getLayoutX() + scaledLeftDisThres*drawnLength;
        double endX = topRunway.getLayoutX() + topRunway.getWidth() - (scaledRightDisThres * drawnLength);
        double x = startX + ((endX - startX) * startXFraction) - (obstacle.getWidth() / 2);
        //double y = runway.getLayoutY() + (runway.getHeight() - obstacle.getHeight()) / 2;
        double y;
        if (distanceFromCenterline<0) {
            y = topRunway.getLayoutY() + (topRunway.getHeight() / 2) - (obstacle.getHeight()) - (distanceFromCenterline * topRunway.getHeight() / 2);
        }else{
            y = topRunway.getLayoutY() + (topRunway.getHeight() / 2) - (obstacle.getHeight()) - (distanceFromCenterline * topRunway.getHeight() / 2) + obstacle.getHeight();
        }

        obstacle.setX(x);
        obstacle.setY(y);

        obstacle.setFill(javafx.scene.paint.Color.RED);

        pane.getChildren().add(obstacle);
    }

    public void setRunway(Runway r, AnchorPane pane, Rectangle drawnRunway){
        double lineThickness = drawnRunway.getHeight();

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
        topLeftPane.setVisible(!topLeftPane.isVisible());
        topRightPane.setVisible(!topRightPane.isVisible());
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

    }
}
