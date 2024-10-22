module java {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.opencsv;
    requires java.sql;
    opens model;
    opens app;
    exports app;
}