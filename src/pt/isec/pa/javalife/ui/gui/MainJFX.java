package pt.isec.pa.javalife.ui.gui;

import pt.isec.pa.javalife.model.EcosystemManager;
import pt.isec.pa.javalife.ui.gui.scenes.SplashScene;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.text.Font;


public class MainJFX extends Application {
    private EcosystemManager model;

    public MainJFX(){
        this.model = new EcosystemManager();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaLife");
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();

        Font.loadFont(getClass().getResource("/fonts/Inter-Regular.ttf").toExternalForm(), 20);

        Image icon = new Image(getClass().getResourceAsStream("/images/icon.png"));
        primaryStage.getIcons().add(icon);

        SplashScene splashScreen = new SplashScene(primaryStage,model);

        primaryStage.setScene(splashScreen);
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        model.startGame(100);
    }

    @Override
    public void stop() throws Exception {
        model.stopGame();
        model.waitForTheEnd();
    }
}
