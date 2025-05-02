module com.example.enac_project {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.enac_project to javafx.fxml;
    exports com.example.enac_project;
}