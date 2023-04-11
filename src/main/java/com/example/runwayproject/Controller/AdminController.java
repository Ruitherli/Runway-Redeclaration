package com.example.runwayproject.Controller;

import com.example.runwayproject.Connector.DbConnect;
import com.example.runwayproject.Model.Calculator;
import com.example.runwayproject.Model.Obstacle;
import com.example.runwayproject.Model.RunwayDesignator;
import com.example.runwayproject.Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.runwayproject.Connector.DbConnect.*;
import static com.example.runwayproject.Model.Calculator.maxObsHeight;

//testing
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

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField1;

    @FXML
    private PasswordField passwordField2;

    @FXML
    private ComboBox<String> roleComboBox;

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
        roleComboBox.setButtonCell(new ListCell<>(){
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(roleComboBox.getPromptText());
                } else {
                    setText(item);
                }
            }
        });
        ObservableList<String> roleEnum = FXCollections.observableArrayList(
                "ADMIN",
                "AM",
                "ATC"
        );
        roleComboBox.getItems().addAll(roleEnum);

        formatTable(runwayDesignatorTable);
        formatTable(constantsTable);
        formatTable(userTable);

        setUsernameFormat(usernameTextField);
        setUsernameFormat(passwordField1);
        setUsernameFormat(passwordField2);

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
    public Button editDesignatorButton;
    @FXML
    public Button doneEditDesignatorsButton;

    //edit runway designator table
    public void editDesignatorTable(ActionEvent e1) throws SQLException{

        runwayDesignatorTable.setEditable(true);
        editDesignatorButton.setText("Edit Mode");
        doneEditDesignatorsButton.setVisible(true);
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        toraCol.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
        todaCol.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
        asdaCol.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
        ldaCol.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
        dThresCol.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
        nameCol.setOnEditCommit(event -> {
            RunwayDesignator name = event.getRowValue();
            String check = name.getRunwayDesignatorName();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            connection = DbConnect.getConnection();
            try {
                preparedStatement = connection.prepareStatement("SELECT designator_name from runway_designator where designator_name = '" + event.getNewValue() + "' limit 1");
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    //System.out.println(s);
                    playInformationAlert("Runway Designator name already exists in the database");
                    loadConstantsTable();
                    loadRunwayDesignatorTable();
                } else {
                    alert.setContentText("Change the Runway Designator from " + check + " to " + event.getNewValue() + "?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        String sql1 = ("update runway_designator set designator_name = '" + event.getNewValue() + "' WHERE designator_name = '" + check + "'");
                        PreparedStatement r = connection.prepareStatement(sql1);
                        r.execute();
                        loadRunwayDesignatorTable();
                        loadConstantsTable();
                        connection.close();
                    }
                    loadRunwayDesignatorTable();
                    loadConstantsTable();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        toraCol.setOnEditCommit(event -> {
            RunwayDesignator tora = event.getRowValue();
            String check = tora.getRunwayDesignatorName();
            if (event.getNewValue() == -1) {
                playErrorAlert("Enter Integer Value");
                loadRunwayDesignatorTable();
            } else if (event.getNewValue() < 0) {
                playErrorAlert("TORA cannot have a negative value");
                loadRunwayDesignatorTable();
                } else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setContentText("Change the TORA to " + event.getNewValue() + " for Runway Designator " + check + "?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        try {
                            connection = DbConnect.getConnection();
                            PreparedStatement stmt = connection.prepareStatement("update runway_designator set TORA = '" + event.getNewValue() + "' WHERE designator_name = '" + check + "'");
                            stmt.execute();
                            connection.close();
                        } catch (SQLException e) {
                            playErrorAlert(String.valueOf(e));
                        }
                    }loadRunwayDesignatorTable();
                }
        });
        todaCol.setOnEditCommit(event -> {
            RunwayDesignator toda = event.getRowValue();
            String check = toda.getRunwayDesignatorName();
            if (event.getNewValue() == -1) {
                playErrorAlert("Enter Integer Value");
                loadRunwayDesignatorTable();
            } else if (event.getNewValue() < 0) {
                playErrorAlert("TODA cannot have a negative value");
                loadRunwayDesignatorTable();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setContentText("Change the TODA to " + event.getNewValue() + " for Runway Designator " + check + "?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                        connection = DbConnect.getConnection();
                        PreparedStatement stmt = connection.prepareStatement("update runway_designator set TODA = '" + event.getNewValue() + "' WHERE designator_name = '" + check + "'");
                        stmt.execute();
                        connection.close();
                    } catch (SQLException e) {
                        playErrorAlert(String.valueOf(e));
                    }
                }loadRunwayDesignatorTable();
            }
        });
        asdaCol.setOnEditCommit(event -> {
            RunwayDesignator asda = event.getRowValue();
            String check = asda.getRunwayDesignatorName();
            if (event.getNewValue() == -1) {
                playErrorAlert("Enter Integer Value");
                loadRunwayDesignatorTable();
            } else if (event.getNewValue() < 0) {
                playErrorAlert("ASDA cannot have a negative value");
                loadRunwayDesignatorTable();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setContentText("Change the ASDA to " + event.getNewValue() + " for Runway Designator " + check + "?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                        connection = DbConnect.getConnection();
                        PreparedStatement stmt = connection.prepareStatement("update runway_designator set ASDA = '" + event.getNewValue() + "' WHERE designator_name = '" + check + "'");
                        stmt.execute();
                        connection.close();
                    } catch (SQLException e) {
                        playErrorAlert(String.valueOf(e));
                    }
                }loadRunwayDesignatorTable();
            }
        });
        ldaCol.setOnEditCommit(event -> {
            RunwayDesignator lda = event.getRowValue();
            String check = lda.getRunwayDesignatorName();
            if (event.getNewValue() == -1) {
                playErrorAlert("Enter Integer Value");
                loadRunwayDesignatorTable();
            } else if (event.getNewValue() < 0) {
                playErrorAlert("LDA cannot have a negative value");
                loadRunwayDesignatorTable();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setContentText("Change the LDA to " + event.getNewValue() + " for Runway Designator " + check + "?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                        connection = DbConnect.getConnection();
                        PreparedStatement stmt = connection.prepareStatement("update runway_designator set LDA = '" + event.getNewValue() + "' WHERE designator_name = '" + check + "'");
                        stmt.execute();
                        connection.close();
                    } catch (SQLException e) {
                        playErrorAlert(String.valueOf(e));
                    }
                }loadRunwayDesignatorTable();
            }
        });
        dThresCol.setOnEditCommit(event -> {
            RunwayDesignator dThresh = event.getRowValue();
            String check = dThresh.getRunwayDesignatorName();
            if (event.getNewValue() == -1) {
                playErrorAlert("Enter Integer Value");
            } else if (event.getNewValue() < 0) {
                playErrorAlert("Displaced Threshold cannot have a negative value");
                loadRunwayDesignatorTable();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setContentText("Change the Displaced Threshold to " + event.getNewValue() + " for Runway Designator " + check + "?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                        connection = DbConnect.getConnection();
                        PreparedStatement stmt = connection.prepareStatement("update runway_designator set displaced_thres = '" + event.getNewValue() + "' WHERE designator_name = '" + check + "'");
                        stmt.execute();
                        connection.close();
                    } catch (SQLException e) {
                        playErrorAlert(String.valueOf(e));
                    }
                }loadRunwayDesignatorTable();
            }
        });
    }
        public void doneEditDesignator(ActionEvent e){
        doneEditDesignatorsButton.setVisible(false);
        editDesignatorButton.setText("Edit Runway Designators");
        loadConstantsTable();
        loadRunwayDesignatorTable();
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
    private Button editConstantButton;

    @FXML
    private Button doneConstantEdit;



    public void editConstantTable(ActionEvent e1) {
        loadRunwayDesignatorTable();
        constantsTable.setEditable(true);
        editConstantButton.setText("Edit Mode");
        doneConstantEdit.setVisible(true);
        blastCol.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
        resaCol.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
        stripCol.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
        slopeCol.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
        minRunCol.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
        minLandCol.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
        avgRunCol.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
        maxHeightCol.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
        blastCol.setOnEditCommit(event -> {
            if (event.getNewValue() < 0) {
                playErrorAlert("Blast Protection cannot have a negative value");
                loadRunwayDesignatorTable();
                loadConstantsTable();
            } else if (event.getNewValue() == -1) {
                playErrorAlert("Enter Integer Value");
                loadRunwayDesignatorTable();
                loadConstantsTable();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setContentText("Change Blast Protection to " + event.getNewValue() + "?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                        connection = DbConnect.getConnection();
                        PreparedStatement stmt = connection.prepareStatement("update constant set blast_protection = " + event.getNewValue());
                        //Calculator.setBlastProtection(event.getNewValue());
                        stmt.execute();

                        connection.close();
                    } catch (SQLException e2) {
                        playErrorAlert(String.valueOf(e2));
                    }
                }
            }
        });
        resaCol.setOnEditCommit(event -> {
            if (event.getNewValue() < 0) {
                playErrorAlert("RESA cannot have a negative value");
                loadRunwayDesignatorTable();
                loadConstantsTable();
            } else if (event.getNewValue() == -1) {
                playErrorAlert("Enter Integer Value");
                loadRunwayDesignatorTable();
                loadConstantsTable();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setContentText("Change RESA to " + event.getNewValue() + "?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                        connection = DbConnect.getConnection();
                        PreparedStatement stmt = connection.prepareStatement("update constant set RESA = " + event.getNewValue());
                       // Calculator.setRESA(event.getNewValue());
                        stmt.execute();

                        connection.close();
                    } catch (SQLException e2) {
                        playErrorAlert(String.valueOf(e2));
                    }
                }
            }
        });
        stripCol.setOnEditCommit(event -> {
            if (event.getNewValue() < 0) {
                playErrorAlert("Strip End cannot have a negative value");
                loadRunwayDesignatorTable();
                loadConstantsTable();
            } else if (event.getNewValue() == -1) {
                playErrorAlert("Enter Integer Value");
                loadRunwayDesignatorTable();
                loadConstantsTable();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setContentText("Change Strip End to " + event.getNewValue() + "?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                        connection = DbConnect.getConnection();
                        PreparedStatement stmt = connection.prepareStatement("update constant set strip_end = " + event.getNewValue());
                       //Calculator.setStripEnd(event.getNewValue());
                        stmt.execute();

                        connection.close();
                    } catch (SQLException e2) {
                        playErrorAlert(String.valueOf(e2));
                    }
                }
            }
        });
        slopeCol.setOnEditCommit(event -> {
            if (event.getNewValue() < 0) {
                playErrorAlert("Slope cannot have a negative value");
                loadRunwayDesignatorTable();
                loadConstantsTable();
            } else if (event.getNewValue() == -1) {
                playErrorAlert("Enter Integer Value");
                loadRunwayDesignatorTable();
                loadConstantsTable();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setContentText("Change Slope to " + event.getNewValue() + "?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                        connection = DbConnect.getConnection();
                        PreparedStatement stmt = connection.prepareStatement("update constant set slope = " + event.getNewValue());
                       // Calculator.setSlope(event.getNewValue());
                        stmt.execute();

                        connection.close();
                    } catch (SQLException e2) {
                        playErrorAlert(String.valueOf(e2));
                    }
                }
            }
        });
        minRunCol.setOnEditCommit(event -> {
            if (event.getNewValue() < 0) {
                playErrorAlert("Minimum Run Distance cannot have a negative value");
                loadRunwayDesignatorTable();
                loadConstantsTable();
            } else if (event.getNewValue() == -1) {
                playErrorAlert("Enter Integer Value");
                loadRunwayDesignatorTable();
                loadConstantsTable();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setContentText("Change Minimum Run Distance to " + event.getNewValue() + "?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                        connection = DbConnect.getConnection();
                        PreparedStatement stmt = connection.prepareStatement("update constant set minRunDistance = " + event.getNewValue());
                        //Calculator.setMinRunDistance(event.getNewValue());
                        stmt.execute();

                        connection.close();
                    } catch (SQLException e2) {
                        playErrorAlert(String.valueOf(e2));
                    }
                }
            }
        });
        minLandCol.setOnEditCommit(event -> {
            if (event.getNewValue() < 0) {
                playErrorAlert("Minimum Landing Distance cannot have a negative value");
                loadRunwayDesignatorTable();
                loadConstantsTable();
            } else if (event.getNewValue() == -1) {
                playErrorAlert("Enter Integer Value");
                loadRunwayDesignatorTable();
                loadConstantsTable();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setContentText("Change Minimum Landing Distance to " + event.getNewValue() + "?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                        connection = DbConnect.getConnection();
                        PreparedStatement stmt = connection.prepareStatement("update constant set minLandingDistance = " + event.getNewValue());
                      //  Calculator.setMinLandingDistance(event.getNewValue());
                        stmt.execute();

                        connection.close();
                    } catch (SQLException e2) {
                        playErrorAlert(String.valueOf(e2));
                    }
                }
            }
        });
        avgRunCol.setOnEditCommit(event -> {
            if (event.getNewValue() < 0) {
                playErrorAlert("Average Run Width cannot have a negative value");
                loadRunwayDesignatorTable();
                loadConstantsTable();
            } else if (event.getNewValue() == -1) {
                playErrorAlert("Enter Integer Value");
                loadRunwayDesignatorTable();
                loadConstantsTable();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setContentText("Change Average Run Width to " + event.getNewValue() + "?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                        connection = DbConnect.getConnection();
                        PreparedStatement stmt = connection.prepareStatement("update constant set averageRunwayWidth = " + event.getNewValue());
                       // Calculator.setAverageRunwayWidth(event.getNewValue());
                        stmt.execute();

                        connection.close();
                    } catch (SQLException e2) {
                        playErrorAlert(String.valueOf(e2));
                    }
                }
            }
        });
        maxHeightCol.setOnEditCommit(event -> {
            if (event.getNewValue() < 0) {
                playErrorAlert("Maximum Obstacle Height cannot have a negative value");
                loadRunwayDesignatorTable();
                loadConstantsTable();
            } else if (event.getNewValue() == -1) {
                playErrorAlert("Enter Integer Value");
                loadRunwayDesignatorTable();
                loadConstantsTable();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setContentText("Change Maximum Obstacle Height to " + event.getNewValue() + "?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                        connection = DbConnect.getConnection();
                        PreparedStatement stmt = connection.prepareStatement("update constant set maxObsHeight = " + event.getNewValue());
                        //Calculator.setMaxObsHeight(event.getNewValue());
                        stmt.execute();

                        connection.close();
                    } catch (SQLException e2) {
                        playErrorAlert(String.valueOf(e2));
                    }
                }
            }
        });
    }
    public void doneEditConstant(ActionEvent e){
        constantsTable.setEditable(false);
        editConstantButton.setText("Edit Constants");
        doneConstantEdit.setVisible(false);
        loadRunwayDesignatorTable();
        loadConstantsTable();
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

    public void addAccount (ActionEvent event) throws SQLException {
        connection = DbConnect.getConnection();
        if(usernameTextField.getText().isEmpty() || passwordField1.getText().isEmpty() || passwordField2.getText().isEmpty() || roleComboBox.getValue()==null){
            playErrorAlert("Please fill in all the details");
        } else if (!Objects.equals(passwordField1.getText(), passwordField2.getText())){
            playErrorAlert("Passwords do not match");
            passwordField1.clear();
            passwordField2.clear();
        } else {
            String username = usernameTextField.getText();
            String password = passwordField1.getText();
            String role = roleComboBox.getValue();

            try {
                preparedStatement = connection.prepareStatement("INSERT INTO user (user_name, password, role) VALUES ('" + username + "', MD5('" + password + "'), '" + role + "');");
                preparedStatement.execute();
                playInformationAlert("Successfully added account!");
                loadUsersTable();
            }catch (SQLException e){
                playErrorAlert("Username already exists");
                usernameTextField.clear();
                passwordField1.clear();
                passwordField2.clear();
            }
        }
    }

    public void reset (ActionEvent event) {
        usernameTextField.clear();
        passwordField1.clear();
        passwordField2.clear();
        roleComboBox.setValue(null);
    }

    public void importDatabase (ActionEvent event) throws ParserConfigurationException, IOException, SAXException, SQLException {
        String url = "jdbc:mysql://localhost:3306/"; // Replace with your database URL
        String username = USERNAME; // Replace with your database username
        String pass = PASSWORD; // Replace with your database password
        String dbName = DB_NAME;
        //Check if database exists
        checkDatabase();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        //File xmlFile = new File("runway_redeclaration_tool.xml");

        File xmlFile;

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open XML file");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("XML Files","*.xml")
        );

        Stage stage = new Stage();
        xmlFile = fileChooser.showOpenDialog(stage);

        if (xmlFile != null){
            try (Connection conn = DriverManager.getConnection(url, username, pass)) {
                Statement stmt = conn.createStatement();

                // Use the database
                String useDBQuery = "USE " + dbName;
                stmt.executeUpdate(useDBQuery);

                //Parse XML then insert table data
                try {
                    Document document = builder.parse(xmlFile);
                    NodeList tableNodes = document.getElementsByTagName("table_data");

                    for (int i = 0; i < tableNodes.getLength(); i++) {
                        Node tableNode = tableNodes.item(i);
                        NamedNodeMap attributes = tableNode.getAttributes();
                        Node nameAttr = attributes.getNamedItem("name");

                        if (nameAttr.getNodeValue().equals("airport")) {
                            NodeList rowNodes = tableNode.getChildNodes();
                            for (int j = 0; j < rowNodes.getLength(); j++) {
                                Node rowNode = rowNodes.item(j);
                                if (rowNode.getNodeName().equals("row")) {
                                    NodeList fieldNodes = rowNode.getChildNodes();
                                    String airportId = "";
                                    String airportName = "";
                                    for (int k = 0; k < fieldNodes.getLength(); k++) {
                                        Node fieldNode = fieldNodes.item(k);
                                        if (fieldNode.getNodeName().equals("field")) {
                                            NamedNodeMap fieldAttributes = fieldNode.getAttributes();
                                            Node fieldNameAttr = fieldAttributes.getNamedItem("name");

                                            if (fieldNameAttr.getNodeValue().equals("airport_id")) {
                                                airportId = fieldNode.getTextContent().trim();
                                            } else if (fieldNameAttr.getNodeValue().equals("airport_name")) {
                                                airportName = fieldNode.getTextContent().trim();
                                            }
                                        }
                                    }
                                    System.out.println("Airport ID: " + airportId);
                                    System.out.println("Airport Name: " + airportName);
                                    try{
                                        String insertQuery = "INSERT INTO airport (airport_id, airport_name) VALUES (" + airportId + ", '" + airportName + "')";
                                        System.out.println(insertQuery);
                                        stmt.executeUpdate(insertQuery);
                                    }catch (SQLException e){
                                        //Do nothing
                                    }
                                }
                            }
                        }
                        if (nameAttr.getNodeValue().equals("constant")) {
                            NodeList rowNodes = tableNode.getChildNodes();
                            for (int j = 0; j < rowNodes.getLength(); j++) {
                                Node rowNode = rowNodes.item(j);
                                if (rowNode.getNodeName().equals("row")) {
                                    NodeList fieldNodes = rowNode.getChildNodes();
                                    String constant_id = "";
                                    String RESA = "";
                                    String strip_end = "";
                                    String blast_protection = "";
                                    String slope = "";
                                    String minRunDistance = "";
                                    String minLandingDistance = "";
                                    String averageRunwayWidth = "";
                                    String maxObsHeight = "";

                                    for (int k = 0; k < fieldNodes.getLength(); k++) {
                                        Node fieldNode = fieldNodes.item(k);
                                        if (fieldNode.getNodeName().equals("field")) {
                                            NamedNodeMap fieldAttributes = fieldNode.getAttributes();
                                            Node fieldNameAttr = fieldAttributes.getNamedItem("name");

                                            if (fieldNameAttr.getNodeValue().equals("constant_id")){
                                                constant_id = fieldNode.getTextContent().trim();
                                            } else if (fieldNameAttr.getNodeValue().equals("RESA")) {
                                                RESA = fieldNode.getTextContent().trim();
                                            } else if (fieldNameAttr.getNodeValue().equals("strip_end")) {
                                                strip_end = fieldNode.getTextContent().trim();
                                            } else if (fieldNameAttr.getNodeValue().equals("blast_protection")) {
                                                blast_protection = fieldNode.getTextContent().trim();
                                            } else if (fieldNameAttr.getNodeValue().equals("slope")) {
                                                slope = fieldNode.getTextContent().trim();
                                            } else if (fieldNameAttr.getNodeValue().equals("minRunDistance")) {
                                                minRunDistance = fieldNode.getTextContent().trim();
                                            } else if (fieldNameAttr.getNodeValue().equals("minLandingDistance")) {
                                                minLandingDistance = fieldNode.getTextContent().trim();
                                            } else if (fieldNameAttr.getNodeValue().equals("averageRunwayWidth")) {
                                                averageRunwayWidth = fieldNode.getTextContent().trim();
                                            } else if (fieldNameAttr.getNodeValue().equals("maxObsHeight")) {
                                                maxObsHeight = fieldNode.getTextContent().trim();
                                            }
                                        }
                                    }
                                    System.out.println("RESA: " + RESA);
                                    System.out.println("Strip End: " + strip_end);
                                    System.out.println("Blast Protection: " + blast_protection);
                                    System.out.println("Slope: " + slope);
                                    System.out.println("minRunDistance: " + minRunDistance);

                                    try{
                                        String insertQuery = "INSERT INTO constant (constant_id, RESA, strip_end, blast_protection, slope, minRunDistance, minLandingDistance, averageRunwayWidth, maxObsHeight) VALUES (" +
                                                constant_id + ", " + RESA + ", " + strip_end + ", " + blast_protection + ", " + slope+ ", " + minRunDistance + ", " + minLandingDistance+ ", " + averageRunwayWidth+ ", " + maxObsHeight+ ")";
                                        System.out.println(insertQuery);
                                        stmt.executeUpdate(insertQuery);
                                    }catch (SQLException e){
                                        //Do nothing
                                    }
                                }
                            }
                        }
                        if (nameAttr.getNodeValue().equals("obstacle")) {
                            NodeList rowNodes = tableNode.getChildNodes();
                            for (int j = 0; j < rowNodes.getLength(); j++) {
                                Node rowNode = rowNodes.item(j);
                                if (rowNode.getNodeName().equals("row")) {
                                    NodeList fieldNodes = rowNode.getChildNodes();
                                    String obstacle_id = "";
                                    String name = "";
                                    String height = "";
                                    String length = "";
                                    String width = "";
                                    for (int k = 0; k < fieldNodes.getLength(); k++) {
                                        Node fieldNode = fieldNodes.item(k);
                                        if (fieldNode.getNodeName().equals("field")) {
                                            NamedNodeMap fieldAttributes = fieldNode.getAttributes();
                                            Node fieldNameAttr = fieldAttributes.getNamedItem("name");

                                            if (fieldNameAttr.getNodeValue().equals("obstacle_id")) {
                                                obstacle_id = fieldNode.getTextContent().trim();
                                            } else if (fieldNameAttr.getNodeValue().equals("name")) {
                                                name = fieldNode.getTextContent().trim();
                                            } else if (fieldNameAttr.getNodeValue().equals("height")) {
                                                height = fieldNode.getTextContent().trim();
                                            } else if (fieldNameAttr.getNodeValue().equals("length")) {
                                                length = fieldNode.getTextContent().trim();
                                            } else if (fieldNameAttr.getNodeValue().equals("width")) {
                                                width = fieldNode.getTextContent().trim();
                                            }
                                        }
                                    }
                                    System.out.println("Obstacle_id: " + obstacle_id);
                                    System.out.println("Name: " + name);
                                    System.out.println("Height: " + height);
                                    System.out.println("Length: " + length);
                                    System.out.println("Width: " + width);

                                    try{
                                        String insertQuery = "INSERT INTO obstacle (obstacle_id, name, height, length, width) VALUES (" +
                                                obstacle_id + ", '" + name + "', " + height + ", " + length + ", " + width+ ")";
                                        System.out.println(insertQuery);
                                        stmt.executeUpdate(insertQuery);
                                    }catch (SQLException e){
                                        //Do nothing
                                    }
                                }
                            }
                        }

                        if (nameAttr.getNodeValue().equals("runway_designator")) {
                            NodeList rowNodes = tableNode.getChildNodes();
                            for (int j = 0; j < rowNodes.getLength(); j++) {
                                Node rowNode = rowNodes.item(j);
                                if (rowNode.getNodeName().equals("row")) {
                                    NodeList fieldNodes = rowNode.getChildNodes();
                                    String designator_id = "";
                                    String designator_name = "";
                                    String TORA = "";
                                    String TODA = "";
                                    String ASDA = "";
                                    String LDA = "";
                                    String displaced_thres = "";
                                    for (int k = 0; k < fieldNodes.getLength(); k++) {
                                        Node fieldNode = fieldNodes.item(k);
                                        if (fieldNode.getNodeName().equals("field")) {
                                            NamedNodeMap fieldAttributes = fieldNode.getAttributes();
                                            Node fieldNameAttr = fieldAttributes.getNamedItem("name");

                                            if (fieldNameAttr.getNodeValue().equals("designator_id")) {
                                                designator_id = fieldNode.getTextContent().trim();
                                            } else if (fieldNameAttr.getNodeValue().equals("designator_name")) {
                                                designator_name = fieldNode.getTextContent().trim();
                                            } else if (fieldNameAttr.getNodeValue().equals("TORA")) {
                                                TORA = fieldNode.getTextContent().trim();
                                            } else if (fieldNameAttr.getNodeValue().equals("TODA")) {
                                                TODA = fieldNode.getTextContent().trim();
                                            } else if (fieldNameAttr.getNodeValue().equals("ASDA")) {
                                                ASDA = fieldNode.getTextContent().trim();
                                            } else if (fieldNameAttr.getNodeValue().equals("LDA")) {
                                                LDA = fieldNode.getTextContent().trim();
                                            } else if (fieldNameAttr.getNodeValue().equals("displaced_thres")) {
                                                displaced_thres = fieldNode.getTextContent().trim();
                                            }
                                        }
                                    }
                                    System.out.println("Designator_id: " + designator_id);
                                    System.out.println("Designator name: " + designator_name);
                                    System.out.println("TORA: " + TORA);
                                    System.out.println("TODA: " + TODA);
                                    System.out.println("ASDA: " + ASDA);
                                    System.out.println("LDA: " + LDA);
                                    System.out.println("displaced_thres: " + displaced_thres);

                                    try{
                                        String insertQuery = "INSERT INTO runway_designator (designator_id, designator_name, TORA, TODA, ASDA, LDA, displaced_thres) VALUES (" +
                                                designator_id + ", '" + designator_name + "', " + TORA + ", " + TODA + ", " + ASDA + ", " + LDA + ", " + displaced_thres + ")";
                                        System.out.println(insertQuery);
                                        stmt.executeUpdate(insertQuery);
                                    }catch (SQLException e){
                                        //Do nothing
                                    }
                                }
                            }
                        }
                        if (nameAttr.getNodeValue().equals("user")) {
                            NodeList rowNodes = tableNode.getChildNodes();
                            for (int j = 0; j < rowNodes.getLength(); j++) {
                                Node rowNode = rowNodes.item(j);
                                if (rowNode.getNodeName().equals("row")) {
                                    NodeList fieldNodes = rowNode.getChildNodes();
                                    String user_name = "";
                                    String password = "";
                                    String role = "";
                                    for (int k = 0; k < fieldNodes.getLength(); k++) {
                                        Node fieldNode = fieldNodes.item(k);
                                        if (fieldNode.getNodeName().equals("field")) {
                                            NamedNodeMap fieldAttributes = fieldNode.getAttributes();
                                            Node fieldNameAttr = fieldAttributes.getNamedItem("name");

                                            if (fieldNameAttr.getNodeValue().equals("user_name")) {
                                                user_name = fieldNode.getTextContent().trim();
                                            } else if (fieldNameAttr.getNodeValue().equals("password")) {
                                                password = fieldNode.getTextContent().trim();
                                            } else if (fieldNameAttr.getNodeValue().equals("role")) {
                                                role = fieldNode.getTextContent().trim();
                                            }
                                        }
                                    }
                                    System.out.println("username: " + user_name);
                                    System.out.println("password: " + password);
                                    System.out.println("role: " + role);

                                    try{
                                        String insertQuery = "INSERT INTO user (user_name, password, role) VALUES ('" +
                                                user_name + "', '" + password + "', '" + role + "')";
                                        System.out.println(insertQuery);
                                        stmt.executeUpdate(insertQuery);
                                    }catch (SQLException e){
                                        //Do nothing
                                    }
                                }
                            }
                        }
                    }

                } catch (SAXException | IOException e) {
                    e.printStackTrace();
                }
                try {
                    Document document = builder.parse(xmlFile);
                    NodeList tableNodes = document.getElementsByTagName("table_data");

                    for (int i = 0; i < tableNodes.getLength(); i++) {
                        Node tableNode = tableNodes.item(i);
                        NamedNodeMap attributes = tableNode.getAttributes();
                        Node nameAttr = attributes.getNamedItem("name");

                        if (nameAttr.getNodeValue().equals("runway")) {
                            NodeList rowNodes = tableNode.getChildNodes();
                            for (int j = 0; j < rowNodes.getLength(); j++) {
                                Node rowNode = rowNodes.item(j);
                                if (rowNode.getNodeName().equals("row")) {
                                    NodeList fieldNodes = rowNode.getChildNodes();
                                    String runway_id = "";
                                    String runway_name = "";
                                    String designator_id_1 = "";
                                    String designator_id_2 = "";
                                    for (int k = 0; k < fieldNodes.getLength(); k++) {
                                        Node fieldNode = fieldNodes.item(k);
                                        if (fieldNode.getNodeName().equals("field")) {
                                            NamedNodeMap fieldAttributes = fieldNode.getAttributes();
                                            Node fieldNameAttr = fieldAttributes.getNamedItem("name");

                                            if (fieldNameAttr.getNodeValue().equals("runway_id")) {
                                                runway_id = fieldNode.getTextContent().trim();
                                            } else if (fieldNameAttr.getNodeValue().equals("runway_name")) {
                                                runway_name = fieldNode.getTextContent().trim();
                                            } else if (fieldNameAttr.getNodeValue().equals("designator_id_1")) {
                                                designator_id_1 = fieldNode.getTextContent().trim();
                                            } else if (fieldNameAttr.getNodeValue().equals("designator_id_2")) {
                                                designator_id_2 = fieldNode.getTextContent().trim();
                                            }
                                        }
                                    }
                                    System.out.println("Runway_id: " + runway_id);
                                    System.out.println("Runway_name: " + runway_name);
                                    System.out.println("Designator 1: " + designator_id_1);
                                    System.out.println("Designator 2: " + designator_id_2);

                                    try{
                                        String insertQuery = "INSERT INTO runway (runway_id, runway_name, designator_id_1, designator_id_2) VALUES (" +
                                                runway_id + ", '" + runway_name + "', " + designator_id_1 + ", " + designator_id_2 + ")";
                                        System.out.println(insertQuery);
                                        stmt.executeUpdate(insertQuery);
                                    }catch (SQLException e){
                                        //Do nothing
                                    }
                                }
                            }
                        }
                    }

                } catch (SAXException | IOException e) {
                    e.printStackTrace();
                }
                try {
                    Document document = builder.parse(xmlFile);
                    NodeList tableNodes = document.getElementsByTagName("table_data");

                    for (int i = 0; i < tableNodes.getLength(); i++) {
                        Node tableNode = tableNodes.item(i);
                        NamedNodeMap attributes = tableNode.getAttributes();
                        Node nameAttr = attributes.getNamedItem("name");

                        if (nameAttr.getNodeValue().equals("obstacle_location")) {
                            NodeList rowNodes = tableNode.getChildNodes();
                            for (int j = 0; j < rowNodes.getLength(); j++) {
                                Node rowNode = rowNodes.item(j);
                                if (rowNode.getNodeName().equals("row")) {
                                    NodeList fieldNodes = rowNode.getChildNodes();
                                    String location_id = "";
                                    String obstacle_id = "";
                                    String runway_id = "";
                                    String distance_from_threshold_R = "";
                                    String distance_from_threshold_L = "";
                                    String distance_from_centerline = "";
                                    String direction_from_centerline = "";
                                    for (int k = 0; k < fieldNodes.getLength(); k++) {
                                        Node fieldNode = fieldNodes.item(k);
                                        if (fieldNode.getNodeName().equals("field")) {
                                            NamedNodeMap fieldAttributes = fieldNode.getAttributes();
                                            Node fieldNameAttr = fieldAttributes.getNamedItem("name");

                                            if (fieldNameAttr.getNodeValue().equals("location_id")) {
                                                location_id = fieldNode.getTextContent().trim();
                                            } else if (fieldNameAttr.getNodeValue().equals("obstacle_id")) {
                                                obstacle_id = fieldNode.getTextContent().trim();
                                            } else if (fieldNameAttr.getNodeValue().equals("runway_id")) {
                                                runway_id = fieldNode.getTextContent().trim();
                                            } else if (fieldNameAttr.getNodeValue().equals("distance_from_threshold_R")) {
                                                distance_from_threshold_R = fieldNode.getTextContent().trim();
                                            } else if (fieldNameAttr.getNodeValue().equals("distance_from_threshold_L")) {
                                                distance_from_threshold_L = fieldNode.getTextContent().trim();
                                            } else if (fieldNameAttr.getNodeValue().equals("distance_from_centerline")) {
                                                distance_from_centerline = fieldNode.getTextContent().trim();
                                            } else if (fieldNameAttr.getNodeValue().equals("direction_from_centerline")) {
                                                direction_from_centerline = fieldNode.getTextContent().trim();
                                            }
                                        }
                                    }
                                    System.out.println("Location_id: " + location_id);
                                    System.out.println("Obstacle_id: " + obstacle_id);
                                    System.out.println("Runway_id: " + runway_id);
                                    System.out.println("Thres R: " + distance_from_threshold_R);
                                    System.out.println("Thres L: " + distance_from_threshold_L);
                                    System.out.println("Center: " + distance_from_centerline);
                                    System.out.println("Direction: " + direction_from_centerline);
                                    try{
                                        String insertQuery = "INSERT INTO obstacle_location (location_id, obstacle_id, runway_id, distance_from_threshold_R, distance_from_threshold_L, distance_from_centerline, direction_from_centerline) VALUES (" +
                                                location_id + ", " + obstacle_id + ", " + runway_id + ", " + distance_from_threshold_R + ", " + distance_from_threshold_L + ", " + distance_from_centerline + ", '" + direction_from_centerline + "')";
                                        System.out.println(insertQuery);
                                        stmt.executeUpdate(insertQuery);
                                    }catch (SQLException e){
                                        //Do nothing
                                    }
                                }
                            }
                        }
                    }

                } catch (SAXException | IOException e) {
                    e.printStackTrace();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

    }

    public void exportDatabase (ActionEvent event){
        String dbName = DB_NAME;
        String username = USERNAME;
        String password = PASSWORD;
        //String dumpPath = "runway_redeclaration_tool.xml";

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Export Directory");
        File selectedDirectory = directoryChooser.showDialog(null);

        if (selectedDirectory == null){
            System.out.println("Export cancelled");
            return;
        }

        String dumpPath = selectedDirectory.getAbsolutePath() + File.separator + "runway_redeclaration_tool.xml";

        ProcessBuilder builder = new ProcessBuilder(
                "mysqldump",
                "--user=" + username,
                "--password=" + password,
                "--xml", // add the --xml option to dump in XML format
                dbName
        );

        builder.redirectOutput(ProcessBuilder.Redirect.to(new File(dumpPath))); // redirect the output to the specified file

        try {
            Process process = builder.start();
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Backup successful.");
            } else {
                System.err.println("Backup failed. Exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
