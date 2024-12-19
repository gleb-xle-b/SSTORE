module org.example.store {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    requires java.sql;
    requires java.desktop;

    //opens com.example.universitytest.controllers to javafx.fxml; // Добавьте эту строку


    opens org.example.store.Controllers to javafx.fxml;
    opens org.example.store to javafx.fxml;
    exports org.example.store;
}