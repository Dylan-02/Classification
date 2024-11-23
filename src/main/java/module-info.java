module java {
    requires javafx.controls;
    requires com.opencsv;
    requires java.sql;
    opens model;
    opens app;
    exports app;
    opens model.data;
}