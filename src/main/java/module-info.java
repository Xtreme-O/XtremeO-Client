module com.mycompany.xtremeo.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.base;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.materialdesign2;
    requires com.google.gson;

    opens com.mycompany.xtremeo.client.model.lobby to com.google.gson;
    opens com.mycompany.xtremeo.client.model.game to com.google.gson;
    opens com.mycompany.xtremeo.client.model.common to com.google.gson;
    opens com.mycompany.xtremeo.client.protocol.envelope to com.google.gson;
    opens com.mycompany.xtremeo.client.enums to com.google.gson;
    opens com.mycompany.xtremeo.client.controller to javafx.fxml;
    opens com.mycompany.xtremeo.client.controller.lobby to javafx.fxml;
    opens com.mycompany.xtremeo.client.service to javafx.fxml;
    opens com.mycompany.xtremeo.client.service.lobby to javafx.fxml;
    opens com.mycompany.xtremeo.client.data to javafx.fxml;
    exports com.mycompany.xtremeo.client.app;
    opens com.mycompany.xtremeo.client.model.auth.request to com.google.gson;
    opens com.mycompany.xtremeo.client.model.auth.response to com.google.gson;
}
