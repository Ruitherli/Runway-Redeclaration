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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

    public static void importDatabase () throws ParserConfigurationException, IOException, SAXException {

        //Check if database exists
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        File xmlFile = new File("runway_redeclaration_tool.xml");

        //Parse XML then insert table data
        try {
            Document document = builder.parse(xmlFile);
            NodeList tableNodes = document.getElementsByTagName("table_data");

            for (int i = 0; i < tableNodes.getLength(); i++) {
                Node tableNode = tableNodes.item(i);
                NamedNodeMap attributes = tableNode.getAttributes();
                Node nameAttr = attributes.getNamedItem("name");

                System.out.println(nameAttr.getNodeValue() + "Table");
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
                        }
                    }
                }
                if (nameAttr.getNodeValue().equals("constant")) {
                    NodeList rowNodes = tableNode.getChildNodes();
                    for (int j = 0; j < rowNodes.getLength(); j++) {
                        Node rowNode = rowNodes.item(j);
                        if (rowNode.getNodeName().equals("row")) {
                            NodeList fieldNodes = rowNode.getChildNodes();
                            String RESA = "";
                            String strip_end = "";
                            String blast_protection = "";
                            String ALS = "";
                            String TOCS = "";
                            for (int k = 0; k < fieldNodes.getLength(); k++) {
                                Node fieldNode = fieldNodes.item(k);
                                if (fieldNode.getNodeName().equals("field")) {
                                    NamedNodeMap fieldAttributes = fieldNode.getAttributes();
                                    Node fieldNameAttr = fieldAttributes.getNamedItem("name");

                                    if (fieldNameAttr.getNodeValue().equals("RESA")) {
                                        RESA = fieldNode.getTextContent().trim();
                                    } else if (fieldNameAttr.getNodeValue().equals("strip_end")) {
                                        strip_end = fieldNode.getTextContent().trim();
                                    } else if (fieldNameAttr.getNodeValue().equals("blast_protection")) {
                                        blast_protection = fieldNode.getTextContent().trim();
                                    } else if (fieldNameAttr.getNodeValue().equals("ALS")) {
                                        ALS = fieldNode.getTextContent().trim();
                                    } else if (fieldNameAttr.getNodeValue().equals("TOCS")) {
                                        TOCS = fieldNode.getTextContent().trim();
                                    }
                                }
                            }
                            System.out.println("RESA: " + RESA);
                            System.out.println("Strip End: " + strip_end);
                            System.out.println("Blast Protection: " + blast_protection);
                            System.out.println("ALS: " + ALS);
                            System.out.println("TOCS: " + TOCS);
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
                        }
                    }
                }
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
                        }
                    }
                }
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
                                    } else if (fieldNameAttr.getNodeValue().equals("designator_is_2")) {
                                        designator_id_2 = fieldNode.getTextContent().trim();
                                    }
                                }
                            }
                            System.out.println("Runway_id: " + runway_id);
                            System.out.println("Runway_name: " + runway_name);
                            System.out.println("Designator 1: " + designator_id_1);
                            System.out.println("Designator 2: " + designator_id_2);
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
                        }
                    }
                }
            }

        } catch (SAXException | IOException e) {
            System.out.println(e);
        }

    }
}
