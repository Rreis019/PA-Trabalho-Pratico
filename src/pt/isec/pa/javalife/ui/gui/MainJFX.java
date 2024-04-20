package pt.isec.pa.javalife.ui.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


import pt.isec.pa.javalife.ui.gui.scenes.StartScene;

public class MainJFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Javaldsife");

        Font.loadFont(getClass().getResource("/fonts/Inter-Regular.ttf").toExternalForm(), 20);

        StartScene startScene = new StartScene(primaryStage);
        primaryStage.setScene(startScene);
        primaryStage.show();
    }
}
