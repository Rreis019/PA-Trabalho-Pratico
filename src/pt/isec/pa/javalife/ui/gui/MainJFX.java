package pt.isec.pa.javalife.ui.gui;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import pt.isec.pa.javalife.model.Ecosystem;
import pt.isec.pa.javalife.model.gameengine.GameEngine;
import pt.isec.pa.javalife.ui.gui.scenes.SplashScene;

public class MainJFX extends Application {
    private Ecosystem model;
    private GameEngine gameEngine;

    public MainJFX(){
        this.model = new Ecosystem();
        gameEngine = new GameEngine();
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

        SplashScene splashScreen = new SplashScene(primaryStage,model,gameEngine);

        primaryStage.setScene(splashScreen);
        primaryStage.show();

    }

    @Override
    public void init() throws Exception {
        gameEngine.registerClient(model);
        gameEngine.start(100);
    }

    @Override
    public void stop() throws Exception {
        gameEngine.stop();
        gameEngine.waitForTheEnd();
    }
}
