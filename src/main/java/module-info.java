module com.mycompany.xtremeo.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.mycompany.xtremeo.client.controller to javafx.fxml;
    exports com.mycompany.xtremeo.client.app;
}
