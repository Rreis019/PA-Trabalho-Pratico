package pt.isec.pa.javalife.ui.gui.scenes;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import pt.isec.pa.javalife.model.EcosystemManager;
import pt.isec.pa.javalife.model.command.CommandManager;


public class SplashScene extends Scene
{
    Stage primaryStage;
    private EcosystemManager model;
    //private CommandManager commandManager;
    public SplashScene(Stage primaryStage_, EcosystemManager manager_)
    {
        super(new VBox());
        createView(primaryStage_);
        registerHandlers();
        primaryStage = primaryStage_;
        model = manager_;
       // commandManager = commandManager_;
    }

    private void createView( Stage primaryStage)
    {
        getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        VBox root = (VBox) this.getRoot();
        root.getStyleClass().add("primary-background");
        root.setPadding(new Insets(10));
        root.setSpacing(10);

        BorderPane mainPane = new BorderPane();
        mainPane.getStyleClass().add("secondary-background");

        VBox.setVgrow(mainPane, Priority.ALWAYS);

        // Header
        ImageView imgHeader = new ImageView(new Image(getClass().getResourceAsStream("/images/logoIsec.png")));
        Label lbHeader1 = new Label("Instituto Superior de Engenharia de Coimbra");
        lbHeader1.getStyleClass().add("text-bold");
        lbHeader1.setTextFill(Color.web("#D9D9D9"));
        Label lbHeader2 = new Label("Licenciatura em Engenharia Informática");
        lbHeader2.getStyleClass().add("text-bold");
        lbHeader2.setTextFill(Color.web("#D9D9D9"));
        Label lbHeader3 = new Label("Programação Avançada - 2023/2024");
        lbHeader3.getStyleClass().add("text-bold");
        lbHeader3.setTextFill(Color.web("#D9D9D9"));

        imgHeader.setFitHeight(80);
        imgHeader.setFitWidth(100);

        VBox headerBox = new VBox();
        headerBox.getChildren().addAll(imgHeader, lbHeader1, lbHeader2, lbHeader3);
        headerBox.setAlignment(Pos.CENTER);

        //Center
        ImageView imgCenter = new ImageView(new Image(getClass().getResourceAsStream("/images/logoJLExtended.png")));
        Label lbInfoStart = new Label("Pressione qualquer tecla para começar\n");

        imgCenter.setFitWidth(550);
        imgCenter.setFitHeight(100);

        lbInfoStart.setStyle("-fx-font-size: 20px;");

        VBox centerBox = new VBox();
        centerBox.getChildren().addAll(imgCenter,lbInfoStart);
        centerBox.setSpacing(20);
        centerBox.setAlignment(Pos.CENTER);

       //Footer
        Label lbFooter1 = new Label("Desenvolvido por:");
        lbFooter1.getStyleClass().add("text-bold");
        lbFooter1.setTextFill(Color.web("#D9D9D9"));
        Label lbFooter2 = new Label("Chelsea Duarte; Diogo Ribeiro; e Rodrigo Reis");
        lbFooter2.getStyleClass().add("text-bold");
        lbFooter2.setTextFill(Color.web("#D9D9D9"));
        Label lbFooter3 = new Label("2021100010; 2022136604; 2022137090");
        lbFooter3.getStyleClass().add("text-bold");
        lbFooter3.setTextFill(Color.web("#D9D9D9"));

        VBox footerBox = new VBox();
        footerBox.getChildren().addAll(lbFooter1, lbFooter2, lbFooter3);
        footerBox.setAlignment(Pos.CENTER);


        // Configuração da transição para a label "start" fazer blink na tela
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), lbInfoStart);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.setAutoReverse(true);

        // Para iniciar a transição
        fadeTransition.play();

        mainPane.setTop(headerBox);
        mainPane.setCenter(centerBox);
        mainPane.setBottom(footerBox);
        mainPane.setPadding(new Insets(0,0,20,0));

        root.getChildren().add(mainPane);

        primaryStage.setWidth(732);
        primaryStage.setHeight(515);

    }

    private void registerHandlers(){
        setOnKeyPressed(event -> {
            StartScene startScene = new StartScene(primaryStage,model);
            primaryStage.setScene(startScene);
            primaryStage.centerOnScreen();
            primaryStage.show();
        });
    }
}
