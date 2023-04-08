package com.example.runwayproject.Controller;

import com.example.runwayproject.Connector.DbConnect;
import com.example.runwayproject.Model.RunwayDesignator;
import com.example.runwayproject.Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminController extends MainController {
    @FXML
    private TableView<RunwayDesignator> runwayDesignatorTable;

    @FXML
    private TableColumn<Constant, Integer> avgRunCol;

    @FXML
    private TableColumn<Constant, Integer> blastCol;

    @FXML
    private TableView<Constant> constantsTable;

    @FXML
    private TableColumn<RunwayDesignator, Integer> dThresCol;

    @FXML
    private TableColumn<RunwayDesignator, Integer> ldaCol;

    @FXML
    private TableColumn<Constant, Integer> maxHeightCol;

    @FXML
    private TableColumn<Constant, Integer> minLandCol;

    @FXML
    private TableColumn<Constant, Integer> minRunCol;

    @FXML
    private TableColumn<RunwayDesignator, String> nameCol;

    @FXML
    private TableColumn<User, String> passwordCol;

    @FXML
    private TableColumn<RunwayDesignator, Integer> resaCol;

    @FXML
    private TableColumn<User, String> roleCol;

    @FXML
    private TableColumn<RunwayDesignator, Integer> asdaCol;

    @FXML
    private TableColumn<Constant, Integer> slopeCol;

    @FXML
    private TableColumn<Constant, Integer> stripCol;

    @FXML
    private TableColumn<RunwayDesignator, Integer> todaCol;

    @FXML
    private TableColumn<RunwayDesignator, Integer> toraCol;

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, String> usernameCol;

    public class Constant {
        int blast_protection, RESA, stripEnd, slope, minRunDistance, minLandDistance, avgRunwayWidth, maxObsHeight;

        public Constant(int blast_protection, int RESA, int stripEnd, int slope, int minRunDistance, int minLandDistance, int avgRunwayWidth, int maxObsHeight) {
            this.blast_protection = blast_protection;
            this.RESA = RESA;
            this.stripEnd = stripEnd;
            this.slope = slope;
            this.minRunDistance = minRunDistance;
            this.minLandDistance = minLandDistance;
            this.avgRunwayWidth = avgRunwayWidth;
            this.maxObsHeight = maxObsHeight;
        }

        public int getBlast_protection() {
            return blast_protection;
        }

        public int getRESA() {
            return RESA;
        }

        public int getStripEnd() {
            return stripEnd;
        }

        public int getSlope() {
            return slope;
        }

        public int getMinRunDistance() {
            return minRunDistance;
        }

        public int getMinLandDistance() {
            return minLandDistance;
        }

        public int getAvgRunwayWidth() {
            return avgRunwayWidth;
        }

        public int getMaxObsHeight() {
            return maxObsHeight;
        }
    }

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    ObservableList<RunwayDesignator> runwayDesignators = FXCollections.observableArrayList();
    ObservableList<Constant> constants = FXCollections.observableArrayList();
    ObservableList<User> users = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        formatTable(runwayDesignatorTable);
        formatTable(constantsTable);
        formatTable(userTable);

        loadRunwayDesignatorTable();
        loadConstantsTable();
        loadUsersTable();
    }

    @FXML
    private void refreshRunwayDesignatorTable (){
        try{
            runwayDesignators.clear();

            connection = DbConnect.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM runway_designator");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                runwayDesignators.add(new RunwayDesignator(
                        resultSet.getInt("TORA"),
                        resultSet.getInt("TODA"),
                        resultSet.getInt("ASDA"),
                        resultSet.getInt("LDA"),
                        resultSet.getInt("displaced_thres"),
                        resultSet.getString("designator_name")
                ));
                runwayDesignatorTable.setItems(runwayDesignators);
            }

            connection.close();
            preparedStatement.close();
            resultSet.close();

        }catch (SQLException e){
            playErrorAlert(String.valueOf(e));
        }
    }

    private void loadRunwayDesignatorTable (){
        connection = DbConnect.getConnection();
        refreshRunwayDesignatorTable();

        nameCol.setCellValueFactory(new PropertyValueFactory<>("runwayDesignatorName"));
        toraCol.setCellValueFactory(new PropertyValueFactory<>("tora"));
        todaCol.setCellValueFactory(new PropertyValueFactory<>("toda"));
        asdaCol.setCellValueFactory(new PropertyValueFactory<>("asda"));
        ldaCol.setCellValueFactory(new PropertyValueFactory<>("lda"));
        dThresCol.setCellValueFactory(new PropertyValueFactory<>("displacedThres"));

        runwayDesignatorTable.setItems(runwayDesignators);
    }

    @FXML
    private void refreshConstantsTable (){
        try{
            constants.clear();

            connection = DbConnect.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM constant");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                constants.add(new Constant(
                        resultSet.getInt("blast_protection"),
                        resultSet.getInt("RESA"),
                        resultSet.getInt("strip_end"),
                        resultSet.getInt("slope"),
                        resultSet.getInt("minRunDistance"),
                        resultSet.getInt("minLandingDistance"),
                        resultSet.getInt("averageRunwayWidth"),
                        resultSet.getInt("maxObsHeight")
                ));
                constantsTable.setItems(constants);
            }

            connection.close();
            preparedStatement.close();
            resultSet.close();

        }catch (SQLException e){
            playErrorAlert(String.valueOf(e));
        }
    }

    private void loadConstantsTable (){
        connection = DbConnect.getConnection();
        refreshConstantsTable();
        resaCol.setCellValueFactory(new PropertyValueFactory<>("RESA"));
        stripCol.setCellValueFactory(new PropertyValueFactory<>("stripEnd"));
        blastCol.setCellValueFactory(new PropertyValueFactory<>("blast_protection"));
        slopeCol.setCellValueFactory(new PropertyValueFactory<>("slope"));
        minRunCol.setCellValueFactory(new PropertyValueFactory<>("minRunDistance"));
        minLandCol.setCellValueFactory(new PropertyValueFactory<>("minLandDistance"));
        avgRunCol.setCellValueFactory(new PropertyValueFactory<>("avgRunwayWidth"));
        maxHeightCol.setCellValueFactory(new PropertyValueFactory<>("maxObsHeight"));

       constantsTable.setItems(constants);
    }

    @FXML
    private void refreshUsersTable (){
        try{
            users.clear();

            connection = DbConnect.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM user");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                users.add(new User(
                        resultSet.getString("user_name"),
                        resultSet.getString("password"),
                        User.Roles.valueOf(resultSet.getString("role"))
                ));
                userTable.setItems(users);
            }

            connection.close();
            preparedStatement.close();
            resultSet.close();

        }catch (SQLException e){
            playErrorAlert(String.valueOf(e));
        }
    }

    private void loadUsersTable (){
        connection = DbConnect.getConnection();
        refreshUsersTable();

        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        roleCol.setCellValueFactory(new PropertyValueFactory<>("roles"));

        constantsTable.setItems(constants);
    }
}
