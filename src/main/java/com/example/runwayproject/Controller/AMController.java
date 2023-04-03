package com.example.runwayproject.Controller;

import com.example.runwayproject.Connector.DbConnect;
import com.example.runwayproject.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.runwayproject.Model.Calculator.*;

public class AMController extends MainController {

    @FXML
    private MenuItem opensite;

    @FXML
    private Text alsText;

    @FXML
    private Text blastProtectionText;

    @FXML
    private TextField centerlineTextField;

    @FXML
    private ComboBox<String> directionComboBox;

    @FXML
    private TextField heightTextField;

    @FXML
    private TextField lengthTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField presetCenterlineTextField;

    @FXML
    private ComboBox<String> presetDirectionComboBox;

    @FXML
    private Text presetHeightText;

    @FXML
    private Text presetLengthText;

    @FXML
    private ComboBox<String> presetNameComboBox;

    @FXML
    private Button presetResetButton;

    @FXML
    private TextField presetThresLTextField;

    @FXML
    private TextField presetThresRTextField;

    @FXML
    private Text presetWidthText;

    @FXML
    private Text resaText;

    @FXML
    private ComboBox<String> runwayComboBox;

    @FXML
    private TableView<RunwayTable> runwayTable;

    @FXML
    private TableView<RunwayObsTable> runwayObstacleTable;

    @FXML
    private TableView<Obstacle> obstacleTable;

    @FXML
    private Text stripEndText;

    @FXML
    private TextField thresLTextField;

    @FXML
    private TextField thresRTextField;

    @FXML
    private Text tocsText;

    @FXML
    private TextField widthTextField;

    @FXML
    private Button AMlogout;

    @FXML
    private Label airportNameSV;

    @FXML
    private Label airportNameTD;

    @FXML
    private TableColumn<Obstacle, Integer> presetLengthCol;

    @FXML
    private TableColumn<Obstacle, String> presetNameCol;

    @FXML
    private TableColumn<Obstacle, Integer> presetHeightCol;

    @FXML
    private TableColumn<Obstacle, Integer> presetWidthCol;

    @FXML
    private TableColumn<RunwayObsTable, Integer> thresholdLCol;

    @FXML
    private TableColumn<RunwayObsTable, Integer> thresholdRCol;

    @FXML
    private TableColumn<RunwayObsTable, String> runwayNameCol;

    @FXML
    private TableColumn<RunwayObsTable, String> obsNameCol;

    @FXML
    private TableColumn<RunwayObsTable, Integer> heightCol;

    @FXML
    private TableColumn<RunwayTable, String> designatorCol;

    @FXML
    private TableColumn<RunwayTable, Integer> toraCol;

    @FXML
    private TableColumn<RunwayTable, Integer> todaCol;

    @FXML
    private TableColumn<RunwayTable, Integer> asdaCol;

    @FXML
    private TableColumn<RunwayTable, Integer> ldaCol;


    static Rectangle obstacle;
    @FXML
    private Tab sideTab;
    @FXML
    private Tab topTab;
    @FXML
    private Label sideLeftAwayLabel;
    @FXML
    private AnchorPane sideLeftPane;
    @FXML
    private AnchorPane sideRootPane;
    @FXML
    private Rectangle sideRunway;
    @FXML
    private Text sideLeftDesignator;
    @FXML
    private Text sideLeftLabel;
    @FXML
    private Text sideRightDesignator;
    @FXML
    private Text sideRightLabel;


    @FXML
    private AnchorPane topLeftPane;
    @FXML
    private AnchorPane topRootPane;
    @FXML
    private Rectangle topRunway;
    @FXML
    private Text topLeftDesignator;
    @FXML
    private Text topLeftLabel;
    @FXML
    private Text topRightDesignator;
    @FXML
    private Text topRightLabel;


    ArrayList<Rectangle> temporaryRect = new ArrayList<Rectangle>();
    ArrayList<Line> temporaryLine = new ArrayList<Line>();
    ArrayList<Text> temporaryText = new ArrayList<Text>();

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    ObservableList<RunwayObsTable> runwayObsList = FXCollections.observableArrayList();
    ObservableList<RunwayTable> runwayList = FXCollections.observableArrayList();
    ObservableList<Obstacle> obstacleList = FXCollections.observableArrayList();

    Obstacle currentObstacle = new Obstacle();
    Runway currentRunway = new Runway();
    ObstacleLocation currentLocation = new ObstacleLocation();

    public class RunwayObsTable {
        private String runwayName;
        private String obstacleName;
        private int height;
        private int distanceFromL;
        private int distanceFromR;

        public String getRunwayName() {
            return runwayName;
        }

        public String getObstacleName() {
            return obstacleName;
        }

        public int getHeight() {
            return height;
        }

        public int getDistanceFromL() {
            return distanceFromL;
        }

        public int getDistanceFromR() {
            return distanceFromR;
        }

        public RunwayObsTable(String runwayName, String obstacleName, int height, int distanceFromL, int distanceFromR) {
            this.runwayName = runwayName;
            this.obstacleName = obstacleName;
            this.height = height;
            this.distanceFromL = distanceFromL;
            this.distanceFromR = distanceFromR;

        }
    }
    public class RunwayTable {
        private String designatorName;
        private int TORA;
        private int TODA;
        private int ASDA;
        private int LDA;;

        public RunwayTable(String designatorName, int tora, int toda, int asda, int lda) {
            this.designatorName = designatorName;
            TORA = tora;
            TODA = toda;
            ASDA = asda;
            LDA = lda;
        }

        public String getDesignatorName() {
            return designatorName;
        }

        public int getTORA() {
            return TORA;
        }

        public int getTODA() {
            return TODA;
        }

        public int getASDA() {
            return ASDA;
        }

        public int getLDA() {
            return LDA;
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setComboBox();
            formatTable(runwayTable);
            formatTable(runwayObstacleTable);
            formatTable(obstacleTable);
            loadObstacleTable();
            loadRunwayObsTable();
            setConstants();
            setAirportName();
            setNameFormat(nameTextField);
            setIntegerFormat(heightTextField);
            setIntegerFormat(widthTextField);
            setIntegerFormat(lengthTextField);
            setNumericFormat(thresLTextField);
            setNumericFormat(thresRTextField);
            setNumericFormat(centerlineTextField);
            setNumericFormat(presetCenterlineTextField);
            setNumericFormat(presetThresLTextField);
            setNumericFormat(presetThresRTextField);


            /*sideLeftPane.setVisible(true);
            sideRightPane.setVisible(false);
            topLeftPane.setVisible(true);
            topRightPane.setVisible(false);*/

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


            //setRunway();
            /////////  TEST  ///////////
            //sideView(runway1,o1,location1,sideLeftPane,sideRightPane,sideRunway,sideLeftAwayLabel,sideLeftTowardsLabel,sideRightAwayLabel,sideRightTowardsLabel); //scenario 1
            //sideView(runway2,o2,location2,sideLeftPane,sideRightPane,sideRunway,sideLeftAwayLabel,sideLeftTowardsLabel,sideRightAwayLabel,sideRightTowardsLabel); //scenario 2
            //sideView(runway2,o3,location3,sideLeftPane,sideRightPane,sideRunway,sideLeftAwayLabel,sideLeftTowardsLabel,sideRightAwayLabel,sideRightTowardsLabel); //scenario 3
            //sideView(runway1,o4,location4,sideLeftPane,sideRightPane,sideRunway,sideLeftAwayLabel,sideLeftTowardsLabel,sideRightAwayLabel,sideRightTowardsLabel); //scenario 4

            //topView(runway1,o1,location1,topLeftPane,topRightPane,topRunway,topLeftAwayLabel,topLeftTowardsLabel,topRightAwayLabel,topRightTowardsLabel); //scenario 1
            //topView(runway2,o2,location2,topLeftPane,topRightPane,topRunway,topLeftAwayLabel,topLeftTowardsLabel,topRightAwayLabel,topRightTowardsLabel); //scenario 2
            //topView(runway2,o3,location3,topLeftPane,topRightPane,topRunway,topLeftAwayLabel,topLeftTowardsLabel,topRightAwayLabel,topRightTowardsLabel); //scenario 3
            //topView(runway1,o4,location4,topLeftPane,topRightPane,topRunway,topLeftAwayLabel,topLeftTowardsLabel,topRightAwayLabel,topRightTowardsLabel); //scenario 4

        } catch (SQLException e) {
            playErrorAlert(String.valueOf(e));
        }
    }

    private void setConstants () {
        resaText.setText(String.valueOf(RESA));
        stripEndText.setText(String.valueOf(stripEnd));
        blastProtectionText.setText(String.valueOf(blastProtection));
        alsText.setText(String.valueOf(slope));
        tocsText.setText(String.valueOf(slope));
    }

    private void setComboBox () throws SQLException {

        directionComboBox.setButtonCell(new ListCell<>(){
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(directionComboBox.getPromptText());
                } else {
                    setText(item);
                }
            }
        });

        presetDirectionComboBox.setButtonCell(new ListCell<>(){
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(presetDirectionComboBox.getPromptText());
                } else {
                    setText(item);
                }
            }
        });

        runwayComboBox.setButtonCell(new ListCell<>(){
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(runwayComboBox.getPromptText());
                } else {
                    setText(item);
                }
            }
        });

        presetNameComboBox.setButtonCell(new ListCell<>(){
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(presetNameComboBox.getPromptText());
                } else {
                    setText(item);
                }
            }
        });
        runwayComboBox.getItems().clear();
        try{
            connection = DbConnect.getConnection();
            preparedStatement = connection.prepareStatement("SELECT runway_name FROM runway");
            resultSet = preparedStatement.executeQuery();
        }catch (Exception e){
            //Do nothing
        }


        while (resultSet.next()){
            runwayComboBox.getItems().add(resultSet.getString("runway_name"));
        }

        preparedStatement = connection.prepareStatement("SELECT name FROM obstacle");
        resultSet = preparedStatement.executeQuery();
        presetNameComboBox.getItems().clear();

        while (resultSet.next()){

            presetNameComboBox.getItems().add(resultSet.getString("name"));
        }

        ObservableList<String> directionEnum = FXCollections.observableArrayList(
                "North",
                "South",
                "Center"
        );

        directionComboBox.getItems().addAll(directionEnum);
        presetDirectionComboBox.getItems().addAll(directionEnum);

        connection.close();
        preparedStatement.close();
        resultSet.close();
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
    @FXML
    private void reset (ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Reset the data in the obstacle fields?");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            nameTextField.clear();
            heightTextField.clear();
            lengthTextField.clear();
            widthTextField.clear();
            thresLTextField.clear();
            thresRTextField.clear();
            centerlineTextField.clear();
            directionComboBox.setValue(null);
        }

    }

    private void reset (){
        nameTextField.clear();
        heightTextField.clear();
        lengthTextField.clear();
        widthTextField.clear();
        thresLTextField.clear();
        thresRTextField.clear();
        centerlineTextField.clear();
        directionComboBox.setValue(null);
    }

    @FXML
    private void resetPreset (ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Reset the data in the obstacle fields?");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            presetThresLTextField.clear();
            presetThresRTextField.clear();
            presetCenterlineTextField.clear();
            presetDirectionComboBox.setValue(null);
            presetNameComboBox.setValue(null);
            presetHeightText.setText("0");
            presetWidthText.setText("0");
            presetLengthText.setText("0");
        }
    }

    private void resetPreset (){
        presetThresLTextField.clear();
        presetThresRTextField.clear();
        presetCenterlineTextField.clear();
        presetDirectionComboBox.setValue(null);
        presetNameComboBox.setValue(null);
        presetHeightText.setText("0");
        presetWidthText.setText("0");
        presetLengthText.setText("0");
    }
    @FXML
    private void refreshObstacleTable() {
        try{
            obstacleList.clear();

            connection = DbConnect.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM obstacle");
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                obstacleList.add(new Obstacle(
                        resultSet.getString("name"),
                        resultSet.getInt("height"),
                        resultSet.getInt("length"),
                        resultSet.getInt("width")
                ));
                obstacleTable.setItems(obstacleList);
            }

            connection.close();
            preparedStatement.close();
            resultSet.close();

        }catch (SQLException e){
            playErrorAlert(String.valueOf(e));
        }
    }

    private void loadObstacleTable(){
        connection = DbConnect.getConnection();
        refreshObstacleTable();

        presetNameCol.setCellValueFactory(new PropertyValueFactory<>("obstacleName"));
        presetHeightCol.setCellValueFactory(new PropertyValueFactory<>("height"));
        presetLengthCol.setCellValueFactory(new PropertyValueFactory<>("length"));
        presetWidthCol.setCellValueFactory(new PropertyValueFactory<>("width"));

        obstacleTable.setItems(obstacleList);
    }

    @FXML
    private void refreshRunwayObsTable(){
        try{
            runwayObsList.clear();

            connection = DbConnect.getConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT r.runway_name, o.name AS obstacle_name, o.height, ol.distance_from_threshold_L, ol.distance_from_threshold_R\n" +
                    "FROM obstacle_location ol\n" +
                    "INNER JOIN obstacle o ON ol.obstacle_id = o.obstacle_id\n" +
                    "INNER JOIN runway r ON ol.runway_id = r.runway_id\n" +
                    "WHERE ol.location_id = location_id;");
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                runwayObsList.add(new RunwayObsTable(
                        resultSet.getString("runway_name"),
                        resultSet.getString("obstacle_name"),
                        resultSet.getInt("height"),
                        resultSet.getInt("distance_from_threshold_L"),
                        resultSet.getInt("distance_from_threshold_R")
                ));
                runwayObstacleTable.setItems(runwayObsList);
            }

            connection.close();
            preparedStatement.close();
            resultSet.close();

        }catch (SQLException e){
            playErrorAlert(String.valueOf(e));
        }
    }

    private void loadRunwayObsTable(){
        connection = DbConnect.getConnection();
        refreshRunwayObsTable();

        runwayNameCol.setCellValueFactory(new PropertyValueFactory<>("runwayName"));
        obsNameCol.setCellValueFactory(new PropertyValueFactory<>("obstacleName"));
        heightCol.setCellValueFactory(new PropertyValueFactory<>("height"));
        thresholdLCol.setCellValueFactory(new PropertyValueFactory<>("distanceFromL"));
        thresholdRCol.setCellValueFactory(new PropertyValueFactory<>("distanceFromR"));

        runwayObstacleTable.setItems(runwayObsList);
    }

    public void refreshRunwayTable(){
        String runway = runwayComboBox.getValue();
        try{
            runwayList.clear();

            connection = DbConnect.getConnection();
            preparedStatement = connection.prepareStatement("SELECT rd.designator_name, rd.tora, rd.toda, rd.asda, rd.lda\n" +
                    "FROM runway r\n" +
                    "JOIN runway_designator rd ON r.designator_id_1 = rd.designator_id OR r.designator_id_2 = rd.designator_id\n" +
                    "WHERE r.runway_name = '" + runway + "';");
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                runwayList.add(new RunwayTable(
                        resultSet.getString("designator_name"),
                        resultSet.getInt("tora"),
                        resultSet.getInt("toda"),
                        resultSet.getInt("asda"),
                        resultSet.getInt("lda")
                ));
                runwayTable.setItems(runwayList);
            }

            connection.close();
            preparedStatement.close();
            resultSet.close();

        }catch (SQLException e){
            playErrorAlert(String.valueOf(e));
        }
    }

    public void loadRunwayTable(ActionEvent event) throws SQLException {
        connection = DbConnect.getConnection();
        refreshRunwayTable();
        connection = DbConnect.getConnection();

        removeObjects();
        designatorCol.setCellValueFactory(new PropertyValueFactory<>("designatorName"));
        toraCol.setCellValueFactory(new PropertyValueFactory<>("TORA"));
        todaCol.setCellValueFactory(new PropertyValueFactory<>("TODA"));
        asdaCol.setCellValueFactory(new PropertyValueFactory<>("ASDA"));
        ldaCol.setCellValueFactory(new PropertyValueFactory<>("LDA"));

        runwayTable.setItems(runwayList);

        String runwayName = runwayComboBox.getValue();
        preparedStatement = connection.prepareStatement("SELECT r.runway_name\n" +
                "FROM runway r\n" +
                "LEFT JOIN obstacle_location ol ON r.runway_id = ol.runway_id\n" +
                "WHERE ol.runway_id IS NOT NULL AND r.runway_name = '"+ runwayName +"';");
        resultSet = preparedStatement.executeQuery();

        if(resultSet.next()){
            //Obstacle exists on runway
            preparedStatement = connection.prepareStatement("SELECT o.*\n" +
                    "FROM obstacle o\n" +
                    "INNER JOIN obstacle_location ol ON o.obstacle_id = ol.obstacle_id\n" +
                    "INNER JOIN runway r ON ol.runway_id = r.runway_id\n" +
                    "WHERE r.runway_name = '" + runwayName + "';");
            resultSet = preparedStatement.executeQuery();

            Obstacle obstacle = new Obstacle();
            while(resultSet.next()){
                obstacle.setObstacleName(resultSet.getString("name"));
                obstacle.setHeight(resultSet.getInt("height"));
                obstacle.setLength(resultSet.getInt("length"));
                obstacle.setWidth(resultSet.getInt("width"));

            }

            //Getting the position of the obstacle on the runway
            preparedStatement = connection.prepareStatement("SELECT ol.distance_from_threshold_R, ol.distance_from_threshold_L, ol.distance_from_centerline, ol.direction_from_centerline\n" +
                    "FROM obstacle_location ol\n" +
                    "JOIN runway r ON ol.runway_id = r.runway_id\n" +
                    "JOIN runway_designator rd ON r.designator_id_1 = rd.designator_id OR r.designator_id_2 = rd.designator_id\n" +
                    "WHERE r.runway_name = '" + runwayName +"' LIMIT 1;");
            resultSet = preparedStatement.executeQuery();

            ObstacleLocation obstacleLocation = new ObstacleLocation();
            while (resultSet.next()){
                obstacleLocation.setDistanceThresR(resultSet.getInt("distance_from_threshold_R"));
                obstacleLocation.setDistanceThresL(resultSet.getInt("distance_from_threshold_L"));
                obstacleLocation.setDistanceFromCenterline(resultSet.getInt("distance_from_centerline"));
                obstacleLocation.setDirection(ObstacleLocation.Direction.valueOf(resultSet.getString("direction_from_centerline")));

            }

            //Getting the runway original distances
            preparedStatement = connection.prepareStatement("SELECT rd.designator_name, rd.tora, rd.toda, rd.asda, rd.lda, rd.displaced_thres\n" +
                    "                    FROM runway r\n" +
                    "                    JOIN runway_designator rd ON r.designator_id_1 = rd.designator_id OR r.designator_id_2 = rd.designator_id\n" +
                    "                    WHERE r.runway_name = '" + runwayName +"';");
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
            Runway runway = new Runway();
            runway.setRunwayName(runwayName);
            for ( RunwayDesignator i : runwayDesignators){
                if (i.getRunwayDesignatorName().endsWith("L")){
                    runway.setLeft(i);
                }else {
                    runway.setRight(i);
                }
            }
            //with obs
            sideView(runway, obstacle,obstacleLocation,sideLeftPane,sideRunway);
            topView(runway,obstacle,obstacleLocation,topLeftPane,topRunway);

        }else{
            //Obstacle does not exists on runway
            //Getting the runway original distances
            preparedStatement = connection.prepareStatement("SELECT rd.designator_name, rd.tora, rd.toda, rd.asda, rd.lda, rd.displaced_thres\n" +
                    "                    FROM runway r\n" +
                    "                    JOIN runway_designator rd ON r.designator_id_1 = rd.designator_id OR r.designator_id_2 = rd.designator_id\n" +
                    "                    WHERE r.runway_name = '" + runwayName +"';");
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
            Runway runway = new Runway();
            runway.setRunwayName(runwayName);
            for ( RunwayDesignator i : runwayDesignators){
                if (i.getRunwayDesignatorName().endsWith("L")){
                    runway.setLeft(i);
                }else {
                    runway.setRight(i);
                }
            }
            //view (without obs)
            setRunway(runway,sideLeftPane,sideRunway);
            setRunway(runway,topLeftPane,topRunway);
        }
    }

    public void addObstacle(ActionEvent event){
        if (nameTextField.getText().isEmpty() || heightTextField.getText().isEmpty() || lengthTextField.getText().isEmpty() || widthTextField.getText().isEmpty()
        || thresRTextField.getText().isEmpty() || thresLTextField.getText().isEmpty() || centerlineTextField.getText().isEmpty()){
            playErrorAlert("Please fill out all the details");
        } else if (runwayComboBox.getValue()==null){
            playErrorAlert("Please select a runway");
        } else if (directionComboBox.getValue()==null){
            playErrorAlert("Please select obstacle direction");
        } else {
            connection = DbConnect.getConnection();
            String query = null;
            String runway = runwayComboBox.getValue();

            String obsName = nameTextField.getText().trim().replaceAll(" +", " ");;
            int obsHeight = Integer.parseInt(heightTextField.getText());
            int obsLength = Integer.parseInt(lengthTextField.getText());
            int obsWidth = Integer.parseInt(widthTextField.getText());
            Obstacle obstacle = new Obstacle(obsName,obsHeight,obsLength,obsWidth);

            int thresR = Integer.parseInt(thresRTextField.getText());
            int thresL = Integer.parseInt(thresLTextField.getText());
            int centerline = Integer.parseInt(centerlineTextField.getText());
            ObstacleLocation.Direction direction = ObstacleLocation.Direction.valueOf(directionComboBox.getValue());
            ObstacleLocation obstacleLocation = new ObstacleLocation(thresR,thresL,centerline,direction);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Add this obstacle "+ obsName +" on the runway " + runway + "?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try{
                    try{
                        query = "INSERT INTO obstacle (name, height, length, width) VALUES (?,?,?,?)";
                        preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setString(1,obsName);
                        preparedStatement.setInt(2, obsHeight);
                        preparedStatement.setInt(3, obsLength);
                        preparedStatement.setInt(4, obsWidth);
                        preparedStatement.execute();
                        System.out.println("Successfully added obstacle into preset table");
                    }catch (SQLException e){
                        playInformationAlert("Obstacle name already exists in the database");
                    }

                    currentObstacle.setObstacleName(obsName);
                    currentObstacle.setHeight(obsHeight);
                    currentObstacle.setLength(obsLength);
                    currentObstacle.setWidth(obsWidth);

                    currentLocation.setDistanceThresR(thresR);
                    currentLocation.setDistanceThresL(thresL);
                    currentLocation.setDistanceFromCenterline(centerline);
                    currentLocation.setDirection(direction);

                    preparedStatement = connection.prepareStatement("SELECT rd.designator_name, rd.tora, rd.toda, rd.asda, rd.lda, rd.displaced_thres\n" +
                            "                    FROM runway r\n" +
                            "                    JOIN runway_designator rd ON r.designator_id_1 = rd.designator_id OR r.designator_id_2 = rd.designator_id\n" +
                            "                    WHERE r.runway_name = '" + runway +"';");
                    resultSet = preparedStatement.executeQuery();

                    ObservableList<RunwayDesignator> runwayDesignators = FXCollections.observableArrayList();
                    while(resultSet.next()){
                        runwayDesignators.add(new RunwayDesignator(
                                resultSet.getInt("tora"),
                                resultSet.getInt("toda"),
                                resultSet.getInt("asda"),
                                resultSet.getInt("lda"),
                                resultSet.getInt("displaced_thres"),
                                resultSet.getString("designator_name")
                        ));
                    }

                    for (RunwayDesignator i : runwayDesignators){
                        if (i.getRunwayDesignatorName().endsWith("L")){
                            currentRunway.setLeft(i);
                        }else {
                            currentRunway.setRight(i);
                        }
                    }
                    currentRunway.setRunwayName(runway);

                    try{
                        query = "INSERT INTO obstacle_location (obstacle_id, runway_id, distance_from_threshold_R, distance_from_threshold_L, distance_from_centerline, direction_from_centerline)\n" +
                                "SELECT o.obstacle_id, r.runway_id, "+thresR+", "+ thresL +", " + centerline+ ", '"+ direction +"'\n" +
                                "FROM obstacle o, runway r\n" +
                                "WHERE r.runway_name = '" + runway + "' AND o.name = '"+ obsName +"';";
                        preparedStatement = connection.prepareStatement(query);
                        preparedStatement.execute();
                        System.out.println("Successfully added the obstacle on the runway");

                        sideView(currentRunway,currentObstacle,currentLocation,sideLeftPane,sideRunway);
                        topView(currentRunway,currentObstacle,currentLocation,topLeftPane,topRunway);


                    }catch (SQLException e){
                        playErrorAlert("Runway "+runway+" already has an obstacle. Please remove the current obstacle on the runway "+runway+" before adding a new obstacle");
                    }

                    connection.close();
                    preparedStatement.close();
                    resultSet.close();
                    reset();
                    //setComboBox();
                    loadObstacleTable();
                    loadRunwayObsTable();

                }catch (SQLException e){
                    playErrorAlert(String.valueOf(e));
                }
            }
        }
    }

    public void addPresetObstacle(ActionEvent event) throws SQLException {
        if (presetThresRTextField.getText().isEmpty() || presetThresLTextField.getText().isEmpty() || presetCenterlineTextField.getText().isEmpty()){
            playErrorAlert("Please fill out all the details");
        } else if (presetNameComboBox.getValue()==null){
            playErrorAlert("Please select an obstacle");
        } else if (runwayComboBox.getValue()==null) {
            playErrorAlert("Please select a runway");
        }else if (presetDirectionComboBox.getValue()==null){
            playErrorAlert("Please select a direction");
        }else{
            connection = DbConnect.getConnection();
            String query = null;
            String runway = runwayComboBox.getValue();

            String obsName = presetNameComboBox.getValue();
            int obsHeight = Integer.parseInt(presetHeightText.getText());
            int obsLength = Integer.parseInt(presetLengthText.getText());
            int obsWidth = Integer.parseInt(presetWidthText.getText());
            Obstacle obstacle = new Obstacle(obsName,obsHeight,obsLength,obsWidth);

            int thresR = Integer.parseInt(presetThresRTextField.getText());
            int thresL = Integer.parseInt(presetThresLTextField.getText());
            int centerline = Integer.parseInt(presetCenterlineTextField.getText());
            ObstacleLocation.Direction direction = ObstacleLocation.Direction.valueOf(presetDirectionComboBox.getValue());
            ObstacleLocation obstacleLocation = new ObstacleLocation(thresR,thresL,centerline,direction);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Add this obstacle "+ obsName +" on the runway " + runway + "?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try{
                    preparedStatement = connection.prepareStatement("SELECT rd.designator_name, rd.tora, rd.toda, rd.asda, rd.lda, rd.displaced_thres\n" +
                            "                    FROM runway r\n" +
                            "                    JOIN runway_designator rd ON r.designator_id_1 = rd.designator_id OR r.designator_id_2 = rd.designator_id\n" +
                            "                    WHERE r.runway_name = '" + runway +"';");
                    resultSet = preparedStatement.executeQuery();

                    ObservableList<RunwayDesignator> runwayDesignators = FXCollections.observableArrayList();
                    while(resultSet.next()){
                        runwayDesignators.add(new RunwayDesignator(
                                resultSet.getInt("tora"),
                                resultSet.getInt("toda"),
                                resultSet.getInt("asda"),
                                resultSet.getInt("lda"),
                                resultSet.getInt("displaced_thres"),
                                resultSet.getString("designator_name")
                        ));
                    }

                    for (RunwayDesignator i : runwayDesignators){
                        if (i.getRunwayDesignatorName().endsWith("L")){
                            currentRunway.setLeft(i);
                        }else {
                            currentRunway.setRight(i);
                        }
                    }
                    currentRunway.setRunwayName(runway);

                    try{
                        query = "INSERT INTO obstacle_location (obstacle_id, runway_id, distance_from_threshold_R, distance_from_threshold_L, distance_from_centerline, direction_from_centerline)\n" +
                                "SELECT o.obstacle_id, r.runway_id, "+thresR+", "+ thresL +", " + centerline+ ", '"+ direction +"'\n" +
                                "FROM obstacle o, runway r\n" +
                                "WHERE r.runway_name = '" + runway + "' AND o.name = '"+ obsName +"';";

                        preparedStatement = connection.prepareStatement(query);
                        preparedStatement.execute();
                        System.out.println("Successfully added the obstacle on the runway");

                        sideView(currentRunway,obstacle,obstacleLocation,sideLeftPane,sideRunway);
                        topView(currentRunway,obstacle,obstacleLocation,topLeftPane,topRunway);

                    }catch (SQLException e){
                        playErrorAlert("Runway "+runway+" already has an obstacle. Please remove the current obstacle on the runway "+runway+" before adding a new obstacle");
                    }

                }catch (SQLException e){
                    playErrorAlert(String.valueOf(e));
                }
            }

            connection.close();
            preparedStatement.close();
            resultSet.close();

            resetPreset();
            loadObstacleTable();
            loadRunwayObsTable();
        }
    }

    public void setPresetObstacleValues(ActionEvent event) throws SQLException {
        connection = DbConnect.getConnection();
        preparedStatement = connection.prepareStatement("SELECT * FROM obstacle WHERE name = " + "'" + presetNameComboBox.getValue() + "';");
        resultSet = preparedStatement.executeQuery();

        Obstacle obstacle = new Obstacle();
        while (resultSet.next()) {
            obstacle.setObstacleName(resultSet.getString("name"));
            obstacle.setHeight(resultSet.getInt("height"));
            obstacle.setLength(resultSet.getInt("length"));
            obstacle.setWidth(resultSet.getInt("width"));
        }

        presetHeightText.setText(String.valueOf(obstacle.getHeight()));
        presetLengthText.setText(String.valueOf(obstacle.getLength()));
        presetWidthText.setText(String.valueOf(obstacle.getWidth()));

        connection.close();
        preparedStatement.close();
        resultSet.close();

    }

    public void refreshTable(ActionEvent event){
        loadObstacleTable();
        loadRunwayObsTable();
    }

    public void setNameFormat(TextField textField) {
        textField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                if (!newValue.matches("^[a-zA-Z0-9\\s-.]*$")) {
                    textField.setText(newValue.replaceAll("[^a-zA-Z0-9\\s-.]", ""));
                }
            } catch (Exception ignored) {
                // Do nothing if an exception occurs
            }
        });
    }

    @FXML
    void hyperlink2(ActionEvent event) throws URISyntaxException, IOException {
        System.out.println("opened");
        //Desktop.getDesktop().browse(new URI("https://drive.google.com/file/d/1A0YGkIcy6O6BGTx-QHKhXmDhOp5zt4D3/view?usp=sharing"));
        File file = new File("UserManualAM.pdf");
        Desktop.getDesktop().open(file);
    }


    ////side visualisation
    public void sideView(Runway r, Obstacle o, ObstacleLocation ol, AnchorPane pane, Rectangle drawnRunway){
        setRunway(r,pane,drawnRunway);
        setObstacle(r,ol,pane,drawnRunway);
        /*setRunway(r,pane2,drawnRunway);
        setObstacle(r,ol,pane2,drawnRunway);*/

       // viewLeft(r,o,ol,pane,drawnRunway,leftAwayLabel,leftTowardsLabel);
       // viewRight(r,o,ol,pane2,drawnRunway,rightAwayLabel,rightTowardsLabel);

        sideLeftDesignator.setVisible(true);
        sideRightDesignator.setVisible(true);

        int leftNum = Integer.parseInt(r.getLeftDesignator().getRunwayDesignatorName().substring(0,2));
        int rightNum = Integer.parseInt(r.getRightDesignator().getRunwayDesignatorName().substring(0,2));

        if (rightNum < leftNum){
            flip();
        }else {
            unflip();
        }
    }

    public void topView(Runway r, Obstacle o, ObstacleLocation ol, AnchorPane pane, Rectangle drawnRunway){
        setRunway(r,pane,drawnRunway);
        setTopObstacle(r,ol,pane,0);
        /*setRunway(r,pane2,drawnRunway);
        setTopObstacle(r,ol,pane2,0);*/

        //viewLeft(r,o,ol,pane,drawnRunway,leftAwayLabel,leftTowardsLabel);
        //viewRight(r,o,ol,pane2,drawnRunway,rightAwayLabel,rightTowardsLabel);

        topLeftDesignator.setVisible(true);
        topRightDesignator.setVisible(true);

        int leftNum = Integer.parseInt(r.getLeftDesignator().getRunwayDesignatorName().substring(0,2));
        int rightNum = Integer.parseInt(r.getRightDesignator().getRunwayDesignatorName().substring(0,2));

        if (rightNum < leftNum){
            flip();
        }else {
            unflip();
        }
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
                awayLabel.setTextFill(Color.RED);
            }else{
                towardsLabel.setText(left.getRunwayDesignatorName() + " Not suitable for Take off " +takeOffStatus + " / Landing " + landingStatus);
                towardsLabel.setTextFill(Color.RED);
            }
        }else {
            if (takeOffStatus == Status.away && landingStatus == Status.over) {
                awayLabel.setText(left.getRunwayDesignatorName() + " Take off AWAY / Landing OVER  ---------->");
                drawLine((double) newTora / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL() + blastProtection) / scale, pane, Color.GREEN, awayPos,lineThickness,("TORA "+newTora),drawnRunway); //tora
                drawLine((double) blastProtection / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()) / scale, pane, Color.BLUE, awayPos,lineThickness,("blast\nprotection "+ blastProtection),drawnRunway); //blast protection
                drawLine((double) newToda / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL() + blastProtection) / scale, pane, Color.RED, awayPos+20,lineThickness,("TODA "+newToda),drawnRunway); //toda
                drawLine((double) newAsda / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL() + blastProtection) / scale, pane, Color.ORANGE, awayPos+40,lineThickness,("ASDA "+newAsda),drawnRunway); //asda
                drawLine((double) newLda / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL() + stripEnd + slopeCalc) / scale, pane, Color.PURPLE, awayPos+60,lineThickness,("LDA "+newLda),drawnRunway); //lda
                drawLine((double) slopeCalc / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()) / scale, pane, Color.MEDIUMORCHID, awayPos+60,lineThickness,("slope "+slopeCalc),drawnRunway); //slope
                drawLine((double) stripEnd / scale, (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL() + slopeCalc) / scale, pane, Color.STEELBLUE, awayPos+60,lineThickness,("strip\nend "+ stripEnd),drawnRunway); //strip end
            } else {
                towardsLabel.setText(left.getRunwayDesignatorName() + " Take off TOWARDS / Landing TOWARDS  ---------->");
                drawLine((double) newTora / scale, (double) 0 / scale, pane, Color.GREEN, towardsPos+60,lineThickness,("TORA "+newTora),drawnRunway); //tora
                //draw((double) rd.getDisplacedThres() / rd.getTora(), (double) 0 / rd.getTora(), pane, Color.GREEN, 50); //displaced threshold
                drawLine((double) stripEnd / scale, (double) newTora / scale, pane, Color.STEELBLUE, towardsPos+60,lineThickness,("strip\nend "+ stripEnd),drawnRunway); //strip end
                drawLine((double) slopeCalc / scale, (double) (newTora + stripEnd ) / scale, pane, Color.MEDIUMORCHID, towardsPos+60,lineThickness,("slope "+slopeCalc),drawnRunway); //slope
                drawLine((double) newToda / scale, (double) 0 / scale, pane, Color.RED, towardsPos+40,lineThickness,("TODA "+newToda),drawnRunway); //toda
                drawLine((double) newAsda / scale, (double) 0 / scale, pane, Color.ORANGE, towardsPos+20,lineThickness,("ASDA "+newAsda),drawnRunway); //asda
                drawLine((double) newLda / scale, (double) left.getDisplacedThres() / scale, pane, Color.PURPLE, towardsPos,lineThickness,("LDA "+newLda),drawnRunway); //lda
                drawLine((double) stripEnd / scale, (double) (newLda+left.getDisplacedThres()) / scale, pane, Color.STEELBLUE, towardsPos,lineThickness,("strip\nend "+ stripEnd),drawnRunway); //strip end
                drawLine((double) RESA / scale, (double) (newLda +left.getDisplacedThres()+ stripEnd) / scale, pane, Color.MAGENTA, towardsPos,lineThickness,("RESA "+ RESA),drawnRunway); //resa
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
                awayLabel.setTextFill(Color.RED);
            }else{
                towardsLabel.setText(right.getRunwayDesignatorName() + " Not suitable for Take off " +takeOffStatus + " / Landing " + landingStatus);
                towardsLabel.setTextFill(Color.RED);
            }
        }else {
            if (takeOffStatus == Status.away && landingStatus == Status.over) {
                awayLabel.setText("<----------  " + right.getRunwayDesignatorName() + " Take off AWAY / Landing OVER");
                drawLine((double) newTora / scale, (double) 0 / scale, pane, Color.GREEN, awayPos,lineThickness,("TORA "+newTora),drawnRunway); //tora
                drawLine((double) blastProtection / scale, (double) newTora / scale, pane, Color.BLUE, awayPos,lineThickness,("blast\nprotection "+ blastProtection),drawnRunway); //blast protection
                drawLine((double) newToda / scale, (double) (newTora-newToda) / scale, pane, Color.RED, awayPos+20,lineThickness,("TODA "+newToda),drawnRunway); //toda
                drawLine((double) newAsda / scale, (double) (newTora-newAsda) / scale, pane, Color.ORANGE, awayPos+40,lineThickness,("ASDA "+newAsda),drawnRunway); //asda
                drawLine((double) newLda / scale, (double) 0 / scale, pane, Color.PURPLE, awayPos+60,lineThickness,("LDA "+newLda),drawnRunway); //lda
                drawLine((double) stripEnd / scale, (double) newLda / scale, pane, Color.STEELBLUE, awayPos+60,lineThickness,("strip\nend "+ stripEnd),drawnRunway); //strip end
                drawLine((double) slopeCalc / scale, (double) (newLda+ stripEnd) / scale, pane, Color.MEDIUMORCHID, awayPos+60,lineThickness,("slope "+slopeCalc),drawnRunway); //slope
            } else {
                towardsLabel.setText("<----------  " + right.getRunwayDesignatorName() + " Take off TOWARDS / Landing TOWARDS");
                drawLine((double) newTora / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()+slopeCalc+ stripEnd)/ scale, pane, Color.GREEN, towardsPos+60,lineThickness,("TORA "+newTora),drawnRunway); //tora
                drawLine((double) stripEnd / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()+slopeCalc) / scale, pane, Color.STEELBLUE, towardsPos+60,lineThickness,("strip\nend "+ stripEnd),drawnRunway); //strip end
                drawLine((double) slopeCalc / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL())/ scale, pane, Color.MEDIUMORCHID, towardsPos+60,lineThickness,("slope "+slopeCalc),drawnRunway); //slope
                drawLine((double) newToda / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()+slopeCalc+ stripEnd) / scale, pane, Color.RED, towardsPos+40,lineThickness,("TODA "+newToda),drawnRunway); //toda
                drawLine((double) newAsda / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()+slopeCalc+ stripEnd) / scale, pane, Color.ORANGE, towardsPos+20,lineThickness,("ASDA "+newAsda),drawnRunway); //asda
                drawLine((double) newLda / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()+ RESA+ stripEnd) / scale, pane, Color.PURPLE, towardsPos,lineThickness,("LDA "+newLda),drawnRunway); //lda
                drawLine((double) stripEnd / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()+ RESA) / scale, pane, Color.STEELBLUE, towardsPos,lineThickness,("strip\nend "+ stripEnd),drawnRunway); //strip end
                drawLine((double) RESA / left.getTora(), (double) (left.getDisplacedThres()+obsLocation.getDistanceThresL()) / scale, pane, Color.MAGENTA, towardsPos,lineThickness,("RESA "+ RESA),drawnRunway); //resa
            }
        }
    }

    public void drawLine(double lengthFraction, double startXFraction, AnchorPane pane, Color color, int y,int thickness, String message, Rectangle drawnRunway) {

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

        obstacle.setFill(Color.RED);

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

        obstacle.setFill(Color.RED);

        pane.getChildren().add(obstacle);
    }

    public void setRunway(Runway r, AnchorPane pane, Rectangle drawnRunway){
        double lineThickness = drawnRunway.getHeight();

        //get the name of the designators
        String leftDesig = r.getLeftDesignator().getRunwayDesignatorName().substring(0,2);
        String rightDesig = r.getRightDesignator().getRunwayDesignatorName().substring(0,2);
        sideLeftDesignator.setText(leftDesig);
        topLeftDesignator.setText(leftDesig);
        sideRightDesignator.setText(rightDesig);
        topRightDesignator.setText(rightDesig);

        //get clearway (toda - tora)
        int leftClearway = r.getRightDesignator().getClearway();  //get from right desig. because the length is measured from right desig.
        int rightClearway = r.getLeftDesignator().getClearway();
        //get stopway (asda - tora)
        int leftStopway = r.getRightDesignator().getStopway();  //get from right desig. because the length is measured from right desig.
        int rightStopway = r.getLeftDesignator().getStopway();

        drawLine((double) r.getLeftDesignator().getDisplacedThres() / r.getLeftDesignator().getTora(), (double) 0, pane, Color.SLATEGRAY, (int) ((int) drawnRunway.getLayoutY()+lineThickness/2), (int) lineThickness,("displaced\nthreshold "+r.getLeftDesignator().getDisplacedThres()),drawnRunway); // left disp thres
        drawLine((double) r.getRightDesignator().getDisplacedThres() / r.getLeftDesignator().getTora(), (1-(double)r.getRightDesignator().getDisplacedThres() / r.getLeftDesignator().getTora()), pane, Color.SLATEGRAY, (int) ((int) drawnRunway.getLayoutY()+lineThickness/2), (int) lineThickness,("displaced\nthreshold "+r.getRightDesignator().getDisplacedThres()),drawnRunway); // right disp thres

        drawLine((double) leftClearway / r.getLeftDesignator().getTora(), (double) -leftClearway / r.getRightDesignator().getTora(), pane, Color.CYAN, (int) ((int) drawnRunway.getLayoutY()+lineThickness/2), (int) lineThickness,("Clearway "+leftClearway),drawnRunway); // left clearway
        drawLine((double) rightClearway / r.getLeftDesignator().getTora(), (double) r.getLeftDesignator().getTora() / r.getLeftDesignator().getTora(), pane, Color.CYAN, (int) ((int) drawnRunway.getLayoutY()+lineThickness/2), (int) lineThickness,("Clearway "+rightClearway),drawnRunway); // right clearway

        drawLine((double) leftStopway / r.getLeftDesignator().getTora(), (double) -leftStopway / r.getRightDesignator().getTora(), pane, Color.LAVENDER, (int) ((int) drawnRunway.getLayoutY()+lineThickness/2), (int) lineThickness,("Stopway "+leftStopway),drawnRunway); // left stopway
        drawLine((double) rightStopway / r.getLeftDesignator().getTora(), (double) r.getLeftDesignator().getTora() / r.getRightDesignator().getTora(), pane, Color.LAVENDER, (int) ((int) drawnRunway.getLayoutY()+lineThickness/2), (int) lineThickness,("Stopway "+rightStopway),drawnRunway); // right clearway

    }

    public void switchPane() {
        // Toggle visibility of pane1 and pane2
        sideLeftPane.setVisible(!sideLeftPane.isVisible());
    }

    public void switchTopViewPane() {
        // Toggle visibility of pane1 and pane2
        topLeftPane.setVisible(!topLeftPane.isVisible());
    }

    public void removeObjects(){
        for (Rectangle r : temporaryRect){
            sideLeftPane.getChildren().remove(r);
            topLeftPane.getChildren().remove(r);
        }
        for (Line l : temporaryLine){
            sideLeftPane.getChildren().remove(l);
            topLeftPane.getChildren().remove(l);
        }
        for (Text l : temporaryText){
            sideLeftPane.getChildren().remove(l);
            topLeftPane.getChildren().remove(l);
        }

    }

    public void flip(){
        sideLeftPane.setScaleX(-1);
        topLeftPane.setScaleX(-1);

        sideLeftLabel.setScaleX(-1);
        sideRightLabel.setScaleX(-1);
        sideLeftDesignator.setScaleX(-1);
        sideRightDesignator.setScaleX(-1);

        topLeftLabel.setScaleX(-1);
        topRightLabel.setScaleX(-1);
        topLeftDesignator.setScaleX(-1);
        topRightDesignator.setScaleX(-1);

        for (Text t : temporaryText){
            t.setScaleX(-1);
        }
    }

    public void unflip(){
        sideLeftPane.setScaleX(1);
        topLeftPane.setScaleX(1);

        sideLeftLabel.setScaleX(1);
        sideRightLabel.setScaleX(1);
        sideLeftDesignator.setScaleX(1);
        sideRightDesignator.setScaleX(1);

        topLeftLabel.setScaleX(1);
        topRightLabel.setScaleX(1);
        topLeftDesignator.setScaleX(1);
        topRightDesignator.setScaleX(1);

        for (Text t : temporaryText){
            t.setScaleX(1);
        }
    }



}

