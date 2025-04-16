module com.example.demo10 {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.desktop;
    requires java.sql;

    opens com.example.demo10 to javafx.fxml;
    exports com.example.demo10;
}