module com.enac.enac_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.slf4j;
    requires ch.qos.logback.classic;
    requires ch.qos.logback.core;

    opens com.enac.enac_project to javafx.fxml;
    exports com.enac.enac_project;
}