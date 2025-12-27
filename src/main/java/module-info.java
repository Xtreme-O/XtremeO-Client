module com.mycompany.xtremeo.client {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.xtremeo.client to javafx.fxml;
    exports com.mycompany.xtremeo.client;
}
