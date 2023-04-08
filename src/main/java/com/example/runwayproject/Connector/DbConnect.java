package com.example.runwayproject.Connector;

import javafx.scene.control.Alert;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.*;

public class DbConnect {
    private static String HOST = "127.0.0.1";
    private static int PORT = 3306;
    private static String DB_NAME = "runway_redeclaration_tool";
    private static String USERNAME = "root";
    private static String PASSWORD = "";
    private static Connection connection;

    public static Connection getConnection() {

        try {
            connection = DriverManager.getConnection(String.format("jdbc:mysql://%s:%d/%s", HOST, PORT, DB_NAME), USERNAME, PASSWORD);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Error: Could not connect to database. The database may be offline or unavailable at this time. Please check your network connection and try again later.");
            alert.showAndWait();
        }

        return connection;
    }

    public static void main(String[] args) throws SQLException, IOException, ParserConfigurationException, SAXException {
        importDatabase();
    }

    public static void checkDatabase (){
        String url = "jdbc:mysql://localhost:3306/"; // Replace with your database URL
        String username = USERNAME; // Replace with your database username
        String password = PASSWORD; // Replace with your database password
        String dbName = DB_NAME; // Replace with the name of the database you want to check or create
        String table1CreateQuery = "CREATE TABLE `airport` (\n" +
                "  `airport_id` int(11) NOT NULL,\n" +
                "  `airport_name` varchar(128) NOT NULL\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci";
        String table2CreateQuery = "CREATE TABLE `constant` (\n" +
                "  `constant_id` int(11) NOT NULL,\n" +
                "  `RESA` int(11) NOT NULL,\n" +
                "  `strip_end` int(11) NOT NULL,\n" +
                "  `blast_protection` int(11) NOT NULL,\n" +
                "  `slope` int(11) NOT NULL,\n" +
                "  `minRunDistance` int(11) NOT NULL,\n" +
                "  `minLandingDistance` int(11) NOT NULL,\n" +
                "  `averageRunwayWidth` int(11) NOT NULL,\n" +
                "  `maxObsHeight` int(11) NOT NULL\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci";
        String table3CreateQuery = "CREATE TABLE `obstacle` (\n" +
                "  `obstacle_id` int(11) NOT NULL,\n" +
                "  `name` varchar(16) NOT NULL,\n" +
                "  `height` int(8) NOT NULL,\n" +
                "  `length` int(8) NOT NULL,\n" +
                "  `width` int(8) NOT NULL\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci";
        String table4CreateQuery = "CREATE TABLE `obstacle_location` (\n" +
                "  `location_id` int(11) NOT NULL,\n" +
                "  `obstacle_id` int(11) NOT NULL,\n" +
                "  `runway_id` int(11) NOT NULL,\n" +
                "  `distance_from_threshold_R` int(11) NOT NULL,\n" +
                "  `distance_from_threshold_L` int(11) NOT NULL,\n" +
                "  `distance_from_centerline` int(11) NOT NULL,\n" +
                "  `direction_from_centerline` varchar(11) NOT NULL\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci";
        String table5CreateQuery = "CREATE TABLE `runway` (\n" +
                "  `runway_id` int(11) NOT NULL,\n" +
                "  `runway_name` varchar(8) NOT NULL,\n" +
                "  `designator_id_1` int(8) NOT NULL,\n" +
                "  `designator_id_2` int(8) NOT NULL\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci";
        String table6CreateQuery = "CREATE TABLE `runway_designator` (\n" +
                "  `designator_id` int(11) NOT NULL,\n" +
                "  `designator_name` varchar(8) NOT NULL,\n" +
                "  `TORA` int(8) NOT NULL,\n" +
                "  `TODA` int(8) NOT NULL,\n" +
                "  `ASDA` int(8) NOT NULL,\n" +
                "  `LDA` int(8) NOT NULL,\n" +
                "  `displaced_thres` int(8) NOT NULL\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci";
        String table7CreateQuery = "CREATE TABLE `user` (\n" +
                "  `user_name` varchar(12) NOT NULL,\n" +
                "  `password` text NOT NULL,\n" +
                "  `role` enum('ADMIN','AM','ATC') NOT NULL\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet resultSet = meta.getCatalogs();
            boolean dbExists = false;

            while (resultSet.next()) {
                String databaseName = resultSet.getString(1);
                if (databaseName.equals(dbName)) {
                    dbExists = true;
                    break;
                }
            }

            if (!dbExists) {
                Statement stmt = conn.createStatement();
                String createDBQuery = "CREATE DATABASE " + dbName;
                stmt.executeUpdate(createDBQuery);
                System.out.println("Database created successfully!");

                String useDBQuery = "USE " + dbName;
                stmt.executeUpdate(useDBQuery);
                Statement createTableStmt = conn.createStatement();
                createTableStmt.executeUpdate(table1CreateQuery);
                createTableStmt.executeUpdate(table2CreateQuery);
                createTableStmt.executeUpdate(table3CreateQuery);
                createTableStmt.executeUpdate(table4CreateQuery);
                createTableStmt.executeUpdate(table5CreateQuery);
                createTableStmt.executeUpdate(table6CreateQuery);
                createTableStmt.executeUpdate(table7CreateQuery);
                System.out.println("Tables created successfully!");

                String addConstraintQuery1 = "ALTER TABLE `airport`\n" +
                        "  ADD PRIMARY KEY (`airport_id`)";
                String addConstraintQuery2 = "ALTER TABLE `constant`\n" +
                        "  ADD PRIMARY KEY (`constant_id`)";
                String addConstraintQuery3 = "ALTER TABLE `obstacle`\n" +
                        "  ADD PRIMARY KEY (`obstacle_id`),\n" +
                        "  ADD UNIQUE KEY `name` (`name`)";
                String addConstraintQuery4 = "ALTER TABLE `obstacle_location`\n" +
                        "  ADD PRIMARY KEY (`location_id`),\n" +
                        "  ADD UNIQUE KEY `runway_id_2` (`runway_id`),\n" +
                        "  ADD UNIQUE KEY `runway_id_3` (`runway_id`),\n" +
                        "  ADD UNIQUE KEY `runway_id_4` (`runway_id`),\n" +
                        "  ADD KEY `obstacle_id` (`obstacle_id`,`runway_id`),\n" +
                        "  ADD KEY `runway_id` (`runway_id`)";
                String addConstraintQuery5 = "ALTER TABLE `runway`\n" +
                        "  ADD PRIMARY KEY (`runway_id`),\n" +
                        "  ADD KEY `designator_id_1` (`designator_id_1`,`designator_id_2`),\n" +
                        "  ADD KEY `designator_id_2` (`designator_id_2`)";
                String addConstraintQuery6 = "ALTER TABLE `runway_designator`\n" +
                        "  ADD PRIMARY KEY (`designator_id`)";
                String addConstraintQuery7 = "ALTER TABLE `user`\n" +
                        "  ADD PRIMARY KEY (`user_name`)";

                String addConstraintQuery8 = "ALTER TABLE `airport`\n" +
                        "  MODIFY `airport_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2";
                String addConstraintQuery9 = "ALTER TABLE `obstacle`\n" +
                        "  MODIFY `obstacle_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38";
                String addConstraintQuery10 = "ALTER TABLE `obstacle_location`\n" +
                        "  MODIFY `location_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46";
                String addConstraintQuery11 = "ALTER TABLE `runway`\n" +
                        "  MODIFY `runway_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3";
                String addConstraintQuery12 = "ALTER TABLE `runway_designator`\n" +
                        "  MODIFY `designator_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5";
                String addConstraintQuery13 = "ALTER TABLE `obstacle_location`\n" +
                        "  ADD CONSTRAINT `obstacle_location_ibfk_1` FOREIGN KEY (`obstacle_id`) REFERENCES `obstacle` (`obstacle_id`) ON DELETE CASCADE ON UPDATE CASCADE,\n" +
                        "  ADD CONSTRAINT `obstacle_location_ibfk_2` FOREIGN KEY (`runway_id`) REFERENCES `runway` (`runway_id`) ON DELETE CASCADE ON UPDATE CASCADE";
                String addConstraintQuery14 = "ALTER TABLE `runway`\n" +
                        "  ADD CONSTRAINT `runway_ibfk_1` FOREIGN KEY (`designator_id_1`) REFERENCES `runway_designator` (`designator_id`) ON DELETE CASCADE ON UPDATE CASCADE,\n" +
                        "  ADD CONSTRAINT `runway_ibfk_2` FOREIGN KEY (`designator_id_2`) REFERENCES `runway_designator` (`designator_id`) ON DELETE CASCADE ON UPDATE CASCADE";


                Statement addConstraintStmt = conn.createStatement();
                addConstraintStmt.executeUpdate(addConstraintQuery1);
                addConstraintStmt.executeUpdate(addConstraintQuery2);
                addConstraintStmt.executeUpdate(addConstraintQuery3);
                addConstraintStmt.executeUpdate(addConstraintQuery4);
                addConstraintStmt.executeUpdate(addConstraintQuery5);
                addConstraintStmt.executeUpdate(addConstraintQuery6);
                addConstraintStmt.executeUpdate(addConstraintQuery7);
                addConstraintStmt.executeUpdate(addConstraintQuery8);
                addConstraintStmt.executeUpdate(addConstraintQuery9);
                addConstraintStmt.executeUpdate(addConstraintQuery10);
                addConstraintStmt.executeUpdate(addConstraintQuery11);
                addConstraintStmt.executeUpdate(addConstraintQuery12);
                addConstraintStmt.executeUpdate(addConstraintQuery13);
                addConstraintStmt.executeUpdate(addConstraintQuery14);
                System.out.println("Constraints added successfully!");
            } else {
                System.out.println("Database already exists!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void exportDatabase (){
        String dbName = DB_NAME;
        String username = USERNAME;
        String password = PASSWORD;
        String dumpPath = "runway_redeclaration_tool.xml";

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

    public static void importDatabase () throws ParserConfigurationException, IOException, SAXException, SQLException {
        String url = "jdbc:mysql://localhost:3306/"; // Replace with your database URL
        String username = USERNAME; // Replace with your database username
        String pass = PASSWORD; // Replace with your database password
        String dbName = DB_NAME;
        //Check if database exists
        checkDatabase();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        File xmlFile = new File("runway_redeclaration_tool.xml");

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
