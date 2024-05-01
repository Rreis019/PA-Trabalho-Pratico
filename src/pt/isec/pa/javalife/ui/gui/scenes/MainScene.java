package pt.isec.pa.javalife.ui.gui.scenes;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


class MainScene extends Scene
{
    Button btninspecionar, btnconfigurar;
    Stage primaryStage;

    VBox topPanel, mainpanel,bottonpanel ,lateralpanel;

    public MainScene(Stage primaryStage__)
    {
        super(new VBox());
        primaryStage =  primaryStage__;
        createView(primaryStage);
        registerHandlers();
    }

    private void createView(Stage primaryStage)
    {
        getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        VBox root = (VBox) this.getRoot();

        root.getStyleClass().add("primary-background");
        root.setPadding(new Insets(10));
        root.setSpacing(10);

        primaryStage.setTitle("Ecossistema");

        topPanel = new VBox();
        mainpanel = new VBox();
        bottonpanel = new VBox();
        lateralpanel = new VBox();


        topPanel.getStyleClass().add("secondary-background");
        mainpanel.getStyleClass().add("secondary-background");
        bottonpanel.getStyleClass().add("secondary-background");
        lateralpanel.getStyleClass().add("secondary-background");

        topPanel.setMinSize(938, 35);
        topPanel.setMaxSize(938, 35);

        mainpanel.setMinSize(658, 577);
        mainpanel.setMaxSize(658, 577);

        bottonpanel.setMinSize(270, 100);
        bottonpanel.setMaxSize(270, 100);

        lateralpanel.setMinSize(270, 477);
        lateralpanel.setMaxSize(270, 477);

        topPanel.setSpacing(10);
        mainpanel.setSpacing(10);
        lateralpanel.setSpacing(10);

        Region spacer = new Region();
        spacer.setMinWidth(10);

        btninspecionar = new Button("Inspecionar");
        btnconfigurar = new Button("Configurar");

        btninspecionar.setMinWidth(5);
        btninspecionar.setMinHeight(5);
        btnconfigurar.setMinWidth(5);
        btnconfigurar.setMinHeight(5);
        btninspecionar.getStyleClass().add("btn-secundary");
        btnconfigurar.getStyleClass().add("btn-secundary");

// Adicionando os painéis e o espaçador a um HBox
        VBox vboxBottonAndLateral = new VBox(bottonpanel, lateralpanel);

        VBox vbox = new VBox(vboxBottonAndLateral);
        bottonpanel.getChildren().addAll(btninspecionar, btnconfigurar);
        HBox hboxMainAndVBox = new HBox(mainpanel, spacer, vbox);
        HBox hboxButtons = new HBox(btninspecionar, btnconfigurar);
        bottonpanel.getChildren().add(hboxButtons);

        VBox.setVgrow(topPanel, Priority.ALWAYS);
        VBox.setVgrow(hboxMainAndVBox, Priority.ALWAYS);

        root.getChildren().addAll(topPanel,hboxMainAndVBox);

        primaryStage.setMinWidth(978);
        primaryStage.setMaxWidth(978);
        primaryStage.setMinHeight(692);
        primaryStage.setMaxHeight(692);


    }

    private void registerHandlers()
    {
        /*inspecionar.setOnAction(e -> {
            panel1.setVisible(true);
            panel2.setVisible(false);
        });

        configurar.setOnAction(e -> {
            panel1.setVisible(false);
            panel2.setVisible(true);
        });*/

        btninspecionar.setOnAction(e -> {

        });

        btnconfigurar.setOnAction(e -> {

        });
    }
}
