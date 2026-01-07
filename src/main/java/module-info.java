module com.mycompany.xtremeo.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.materialdesign2;

    opens com.mycompany.xtremeo.client.controller to javafx.fxml;
    exports com.mycompany.xtremeo.client.app;
}
