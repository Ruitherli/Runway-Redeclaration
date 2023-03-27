package com.example.runwayproject.Controller;

import com.example.runwayproject.Connector.DbConnect;
import com.example.runwayproject.Model.Obstacle;
import com.example.runwayproject.Model.ObstacleLocation;
import com.example.runwayproject.Model.RunwayDesignator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
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

            setRunwayComboBox();
        } catch (SQLException e) {
            playErrorAlert(String.valueOf(e));
        }
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
    }

    @FXML
    void hyperlink(ActionEvent event) throws URISyntaxException, IOException {
        System.out.println("opened");
        Desktop.getDesktop().browse(new URI("https://drive.google.com/file/d/1A0YGkIcy6O6BGTx-QHKhXmDhOp5zt4D3/view?usp=sharing"));

    }
}
