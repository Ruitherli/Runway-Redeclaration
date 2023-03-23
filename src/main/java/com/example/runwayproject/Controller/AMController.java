package com.example.runwayproject.Controller;

import com.example.runwayproject.Connector.DbConnect;
import com.example.runwayproject.Model.Obstacle;
import com.example.runwayproject.Model.ObstacleLocation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.example.runwayproject.Model.Calculator.*;

public class AMController extends MainController {
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
    private Button objectAddButton;

    @FXML
    private Button objectResetButton;

    @FXML
    private Button presetAddButton;

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

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    ObservableList<RunwayObsTable> runwayObsList = FXCollections.observableArrayList();
    ObservableList<RunwayTable> runwayList = FXCollections.observableArrayList();
    ObservableList<Obstacle> obstacleList = FXCollections.observableArrayList();

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
            loadObjectTable();
            loadRunwayObsTable();
            setConstants();
            setAirportName();
        } catch (SQLException e) {
            e.printStackTrace();
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
        connection = DbConnect.getConnection();
        preparedStatement = connection.prepareStatement("SELECT runway_name FROM runway");
        resultSet = preparedStatement.executeQuery();


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
        nameTextField.clear();
        heightTextField.clear();
        lengthTextField.clear();
        widthTextField.clear();
        thresLTextField.clear();
        thresRTextField.clear();
        centerlineTextField.clear();
        directionComboBox.setValue(null);
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
        presetThresLTextField.clear();
        presetThresRTextField.clear();
        presetCenterlineTextField.clear();
        presetDirectionComboBox.setValue(null);
        presetNameComboBox.setValue(null);
    }

    private void resetPreset (){
        presetThresLTextField.clear();
        presetThresRTextField.clear();
        presetCenterlineTextField.clear();
        presetDirectionComboBox.setValue(null);
        presetNameComboBox.setValue(null);
    }
    @FXML
    private void refreshObjectTable() {
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

            for(Obstacle i : obstacleList){
                System.out.println(i.getObstacleName());
            }
            connection.close();
            preparedStatement.close();
            resultSet.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void loadObjectTable(){
        connection = DbConnect.getConnection();
        refreshObjectTable();

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
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    public void loadRunwayTable(ActionEvent event){
        connection = DbConnect.getConnection();
        refreshRunwayTable();

        designatorCol.setCellValueFactory(new PropertyValueFactory<>("designatorName"));
        toraCol.setCellValueFactory(new PropertyValueFactory<>("TORA"));
        todaCol.setCellValueFactory(new PropertyValueFactory<>("TODA"));
        asdaCol.setCellValueFactory(new PropertyValueFactory<>("ASDA"));
        ldaCol.setCellValueFactory(new PropertyValueFactory<>("LDA"));

        runwayTable.setItems(runwayList);
    }

    public void addObstacle(ActionEvent event){
        if (nameTextField.getText().isEmpty() || heightTextField.getText().isEmpty() || lengthTextField.getText().isEmpty() || widthTextField.getText().isEmpty()
        || thresRTextField.getText().isEmpty() || thresLTextField.getText().isEmpty() || centerlineTextField.getText().isEmpty()){
            System.out.println("Please fill out all the details");
        } else if (runwayComboBox.getValue()==null){
            System.out.println("Please select a runway");
        } else if (directionComboBox.getValue()==null){
            System.out.println("Please select obstacle direction");
        } else {
            connection = DbConnect.getConnection();
            String query = null;
            String runway = runwayComboBox.getValue();

            String obsName = nameTextField.getText();
            int obsHeight = Integer.parseInt(heightTextField.getText());
            int obsLength = Integer.parseInt(lengthTextField.getText());
            int obsWidth = Integer.parseInt(widthTextField.getText());
            Obstacle obstacle = new Obstacle(obsName,obsHeight,obsLength,obsWidth);

            int thresR = Integer.parseInt(thresRTextField.getText());
            int thresL = Integer.parseInt(thresLTextField.getText());
            int centerline = Integer.parseInt(centerlineTextField.getText());
            ObstacleLocation.Direction direction = ObstacleLocation.Direction.valueOf(directionComboBox.getValue());
            ObstacleLocation obstacleLocation = new ObstacleLocation(thresR,thresL,centerline,direction);

            /*System.out.println(obstacle.getObstacleName());
            System.out.println(obstacle.getHeight());
            System.out.println(obstacle.getLength());
            System.out.println(obstacle.getWidth());
            System.out.println(obstacleLocation.getDistanceThresR());
            System.out.println(obstacleLocation.getDistanceThresL());
            System.out.println(obstacleLocation.getDistanceFromCenterline());
            System.out.println(obstacleLocation.getDirection());*/
            try{
                query = "INSERT INTO obstacle (name, height, length, width) VALUES (?,?,?,?)";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1,obsName);
                preparedStatement.setInt(2, obsHeight);
                preparedStatement.setInt(3, obsLength);
                preparedStatement.setInt(4, obsWidth);
                preparedStatement.execute();
                System.out.println("Successfully added obstacle into preset table");

                query = "INSERT INTO obstacle_location (obstacle_id, runway_id, distance_from_threshold_R, distance_from_threshold_L, distance_from_centerline, direction_from_centerline)\n" +
                        "SELECT o.obstacle_id, r.runway_id, "+thresR+", "+ thresL +", " + centerline+ ", '"+ direction +"'\n" +
                        "FROM obstacle o, runway r\n" +
                        "WHERE r.runway_name = '" + runway + "' AND o.name = '"+ obsName +"';";

                preparedStatement = connection.prepareStatement(query);
                preparedStatement.execute();
                System.out.println("Successfully added the obstacle on the runway");

                setComboBox();
                loadObjectTable();
                loadRunwayObsTable();

                connection.close();
                preparedStatement.close();
                resultSet.close();
                reset();

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void addPresetObstacle(ActionEvent event) throws SQLException {
        if (presetThresRTextField.getText().isEmpty() || presetThresLTextField.getText().isEmpty() || presetCenterlineTextField.getText().isEmpty()){
            System.out.println("Please fill out all the details");
        } else if (presetNameComboBox.getValue().isEmpty()){
            System.out.println("Please select an obstacle");
        } else if (runwayComboBox.getValue().isEmpty()) {
            System.out.println("Please select a runway");
        }else if (presetDirectionComboBox.getValue().isEmpty()){
            System.out.println("Please select a direction");
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

            query = "INSERT INTO obstacle_location (obstacle_id, runway_id, distance_from_threshold_R, distance_from_threshold_L, distance_from_centerline, direction_from_centerline)\n" +
                    "SELECT o.obstacle_id, r.runway_id, "+thresR+", "+ thresL +", " + centerline+ ", '"+ direction +"'\n" +
                    "FROM obstacle o, runway r\n" +
                    "WHERE r.runway_name = '" + runway + "' AND o.name = '"+ obsName +"';";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
            System.out.println("Successfully added the obstacle on the runway");

            connection.close();
            preparedStatement.close();
            resultSet.close();

            resetPreset();
            loadObjectTable();
            loadRunwayObsTable();
        }
    }

    public void setPresetObstacleValues(ActionEvent event) throws SQLException {
        connection = DbConnect.getConnection();
        preparedStatement = connection.prepareStatement("SELECT * FROM obstacle WHERE name = " + "'" + presetNameComboBox.getValue() + "';");
        resultSet = preparedStatement.executeQuery();

        Obstacle obstacle = null;
        while (resultSet.next()) {
            obstacle = new Obstacle(resultSet.getString("name"), resultSet.getInt("height"), resultSet.getInt("length"), resultSet.getInt("width"));
        }

        presetHeightText.setText(String.valueOf(obstacle.getHeight()));
        presetLengthText.setText(String.valueOf(obstacle.getLength()));
        presetWidthText.setText(String.valueOf(obstacle.getWidth()));

        connection.close();
        preparedStatement.close();
        resultSet.close();

    }

    public void refreshTable(ActionEvent event){
        loadObjectTable();
        loadRunwayObsTable();
    }
}

