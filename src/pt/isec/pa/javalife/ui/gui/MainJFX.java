package pt.isec.pa.javalife.ui.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Group;


public class MainJFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Javalife");
        Scene scene = new Scene(new Group(), 400, 300); // Grupo vazio, largura e altura definidas

        primaryStage.setScene(scene);

        primaryStage.show();
        
    }
}
