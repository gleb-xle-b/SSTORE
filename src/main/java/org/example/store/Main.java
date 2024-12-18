package org.example.store;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Store");
        stage.setScene(scene);
        stage.show();
        System.err.println("........................................„--~*'¯…….'\\\n" +
                "..................................... („-~~--„¸_….,/ì'Ì\n" +
                "................................„-^\"¯ : : :. : :¸-¯\"¯/'\n" +
                "¯¯¯'^^~-„„„----~^*'\"¯ : : : : : :.... : : : :¸-\"\n" +
                ".:.:.:.:.„-^\" : : : : : : : : : : : : : : : : :„-\"\n" +
                ":.:.:.:.:.:.:.:.:.:.: : : : : : : : : : ¸„-^¯\n" +
                ".::.:.:.:.:.:.:.:. : : : : : : : ¸„„-^¯\n" +
                ":.' : : '\\ : : : : : : : ;¸„„-~\"¯\n" +
                ":.:.:: :\"-„\"\"***/*'ì¸'¯\n" +
                ":.': : : : :\"-„ : : :\"\\\n" +
                ".:.:.: : : : :\" : : : : \\,\n" +
                ":.: : : : : : : : : : : : 'Ì\n" +
                ": : : : : : :, : : : : : :/\n" +
                "\"-„_::::_„-*__„„~\"");
    }



    public static void main(String[] args) {
        launch();
        System.err.println("ГЛЕБ ЛОХ ");
    }
}