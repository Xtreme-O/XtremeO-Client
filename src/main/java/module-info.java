module com.mycompany.xtremeo.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires com.google.gson;
    opens com.mycompany.xtremeo.client.model to com.google.gson;
    opens com.mycompany.xtremeo.client.model.auth to com.google.gson;
    opens com.mycompany.xtremeo.client.model.game to com.google.gson;
    opens com.mycompany.xtremeo.client.model.common to com.google.gson;
    opens com.mycompany.xtremeo.client.protocol.envelope to com.google.gson;
    opens com.mycompany.xtremeo.client.enums to com.google.gson;
    opens com.mycompany.xtremeo.client.controller to javafx.fxml;
    exports com.mycompany.xtremeo.client.app;
}
