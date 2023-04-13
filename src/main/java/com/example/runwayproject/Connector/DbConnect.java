package com.example.runwayproject.Connector;

import javafx.scene.control.Alert;

import java.sql.*;

public class DbConnect {
    public static String HOST = "127.0.0.1";
    public static int PORT = 3306;
    public static String DB_NAME = "runway_redeclaration_tool";
    public static String USERNAME = "root";
    public static String PASSWORD = "";
    public static Connection connection;

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

    public static int checkDatabase (){
        String url = "jdbc:mysql://localhost:3306/"; // Replace with your database URL
        String username = USERNAME; // Replace with your database username
        String password = PASSWORD; // Replace with your database password
        String dbName = DB_NAME; // Replace with the name of the database you want to check or create

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
                return -1;
            } else {
                return 1;
            }
        } catch (SQLException e) {
            //Do Nothing
        }
        return 0;
    }

    public static void createDatabase () throws SQLException {
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
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
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

            String defaultAdmin = "INSERT INTO user (user_name, password, role) VALUES ('admin', MD5('admin'), 'ADMIN' )";
            Statement addDefaultAdmin = conn.createStatement();
            addDefaultAdmin.executeUpdate(defaultAdmin);
            System.out.println("Default admin added successfully!");
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
