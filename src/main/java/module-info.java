module com.example.runwayproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.logging;
    requires java.sql;

    opens com.example.runwayproject to javafx.fxml;
    exports com.example.runwayproject.Model;
    opens com.example.runwayproject.Model to javafx.fxml;
    exports com.example.runwayproject.Controller;
    opens com.example.runwayproject.Controller to javafx.fxml;
    exports com.example.runwayproject.View;
    opens com.example.runwayproject.View to javafx.fxml;
}
