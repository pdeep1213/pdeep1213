module com.example.javafx_clinicmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.javafx_clinicmanager to javafx.fxml;
    exports com.example.javafx_clinicmanager;
}