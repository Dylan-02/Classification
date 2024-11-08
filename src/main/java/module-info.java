module java {
    requires javafx.controls;
    requires com.opencsv;
    opens model;
    opens app;
    exports app;
}