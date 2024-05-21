package pt.isec.pa.javalife.ui.gui.scenes;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import pt.isec.pa.javalife.model.Ecosystem;
import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.gameengine.GameEngine;
import pt.isec.pa.javalife.ui.gui.FaunaImagesManager;
import pt.isec.pa.javalife.ui.gui.components.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class CreateEcosystemScene extends Scene
{
    Stage primaryStage;
    Button criarButton;

    Ecosystem model;
    GameEngine gameEngine;

    BlueSpinner SAltura;
    BlueSpinner Scomprimento;
    BlueSpinner Sfauna;
    BlueSpinner Sflora;
    BlueSpinner Sinanimados;
    BlueSlider Sunidade;

    ToggleGroup characterGroup;
    VBox option1Box, option2Box, option3Box, option4Box;
    RadioButton option1, option2, option3, option4;



    public CreateEcosystemScene(Stage primaryStage_,Ecosystem ecosystem,GameEngine gameEngine_)
    {
        super(new HBox());
        model = ecosystem;
        primaryStage =  primaryStage_;
        createView(primaryStage_);
        registerHandlers();
        gameEngine = gameEngine_;
    }

    private void createView(Stage primaryStage)
    {
        //Adiciona o ficheiro de classes de css
        getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        HBox root = (HBox) this.getRoot();
        root.getStyleClass().add("primary-background");
        root.setSpacing(5);

        VBox secondaryBackground = new VBox();
        secondaryBackground.getStyleClass().add("secondary-background");
        secondaryBackground.setAlignment(Pos.TOP_CENTER);
        VBox.setVgrow(secondaryBackground, Priority.ALWAYS);

        //Titulo da pagina
        primaryStage.setTitle("JavaLife - Criar Ecossistema");

        //Para colocar tudo com a cor secundária de fundo
        VBox content = new VBox();
        content.getStyleClass().add("secondary-background");
        content.setSpacing(10);
        content.setMaxWidth(350);
        VBox.setMargin(content, new Insets(5));

        //Texto
        Label Texto = new Label("Criação do ecossistema");
        Texto.setWrapText(true);
        Texto.setAlignment(Pos.CENTER_RIGHT);
        Texto.getStyleClass().add("heading");
        Texto.setStyle("-fx-text-alignment: left;");

        //Barra Branca
        Rectangle separate = new Rectangle();
        separate.setHeight(2);
        separate.setWidth(305);
        separate.setFill(Color.WHITE);

        // Campos de texto
        Label TextoNome = new Label("Nome");
        TextoNome.setStyle("-fx-text-fill: white; -fx-text-alignment: left; -fx-font-size : 15px;");
        TextField Nome = new TextField();
        //Componentes
        SAltura = new BlueSpinner("Altura",300,200,500,20);
        Scomprimento = new BlueSpinner("Comprimento",300,200,500,5);
        Sfauna = new BlueSpinner("Quantidade Fauna",10,0,100,5);
        Sflora = new BlueSpinner("Quantidade Flora",10,0,100,5);
        Sinanimados = new BlueSpinner("Quantidade Inanimados",10,0,100,5);
        Sunidade = new BlueSlider("Ticks per segundo", 300, 60, 1000);

        // Botão de criar
        criarButton = new Button("Criar");
        criarButton.setMinWidth(150);
        criarButton.setMinHeight(45);
        criarButton.getStyleClass().add("btn-primary");

        HBox hbox1 = new HBox();
        hbox1.getChildren().addAll(SAltura, Scomprimento);
        hbox1.setSpacing(10); // Espaçamento entre os componentes
        hbox1.setAlignment(Pos.CENTER);

        HBox hbox2 = new HBox();
        hbox2.getChildren().addAll(Sfauna, Sflora);
        hbox2.setSpacing(10); // Espaçamento entre os componentes
        hbox2.setAlignment(Pos.CENTER);

        HBox hbox3 = new HBox();
        hbox3.getChildren().addAll(Sinanimados);

        Label Erro = new Label("Mensagem de Erro");
        Erro.setStyle("-fx-text-fill: white;-fx-font-size : 15px;");

        content.getChildren().addAll(Texto, separate, TextoNome,Nome, hbox1, hbox2,hbox3, Sunidade , Erro, criarButton);
        secondaryBackground.getChildren().addAll(content);

        //--------- Área de seleção de ícone para a fauna
        Label lbFauna = new Label("Selecione um ícone para a fauna");
        lbFauna.getStyleClass().add("text-bold");

        characterGroup = new ToggleGroup();

        option1 = new RadioButton();
        option1.setToggleGroup(characterGroup);
        Label lbLobo = new Label("Lobo");
        lbLobo.getStyleClass().add("text-bold");
        ImageView loboImage = new ImageView(FaunaImagesManager.getImage("lobo"));
        loboImage.setFitWidth(100);
        loboImage.setFitHeight(100);

        option2 = new RadioButton();
        option2.setToggleGroup(characterGroup);
        Label lbOvelha = new Label("Ovelha");
        lbOvelha.getStyleClass().add("text-bold");
        ImageView ovelhaImage = new ImageView(FaunaImagesManager.getImage("ovelha"));
        ovelhaImage.setFitWidth(100);
        ovelhaImage.setFitHeight(100);

        option3 = new RadioButton();
        option3.setToggleGroup(characterGroup);
        Label lbUrso = new Label("Urso");
        lbUrso.getStyleClass().add("text-bold");
        ImageView ursoImage = new ImageView(FaunaImagesManager.getImage("urso"));
        ursoImage.setFitWidth(100);
        ursoImage.setFitHeight(100);

        option4 = new RadioButton();
        option4.setToggleGroup(characterGroup);
        Label lbCobra = new Label("Cobra");
        lbCobra.getStyleClass().add("text-bold");
        ImageView cobraImage = new ImageView(FaunaImagesManager.getImage("cobra"));
        cobraImage.setFitWidth(100);
        cobraImage.setFitHeight(90);

       //Para fazer com que os radio buttons desapareçam
        option1.setStyle("-fx-opacity: 0");
        option2.setStyle("-fx-opacity: 0");
        option3.setStyle("-fx-opacity: 0");
        option4.setStyle("-fx-opacity: 0");

        option1Box = new VBox();
        option1Box.setAlignment(Pos.CENTER);
        option1Box.getChildren().addAll(lbLobo,option1,loboImage);

        option2Box = new VBox();
        option2Box.setAlignment(Pos.CENTER);
        option2Box.getChildren().addAll(lbOvelha,option2,ovelhaImage);

        HBox charactersBox1 = new HBox();
        charactersBox1.getChildren().addAll(option1Box, option2Box);
        charactersBox1.setSpacing(60);
        VBox.setMargin(charactersBox1, new Insets(0, 0, 0, 30));

        option3Box = new VBox();
        option3Box.setAlignment(Pos.CENTER);
        option3Box.getChildren().addAll(lbUrso,option3,ursoImage);

        option4Box = new VBox();
        option4Box.setAlignment(Pos.CENTER);
        option4Box.getChildren().addAll(lbCobra,option4,cobraImage);

        HBox charactersBox2 = new HBox();
        charactersBox2.getChildren().addAll(option3Box, option4Box);
        charactersBox2.setSpacing(60);
        VBox.setMargin(charactersBox2, new Insets(0, 0, 0, 30));

        VBox characterBox = new VBox();
        characterBox.setAlignment(Pos.CENTER);
        characterBox.getStyleClass().add("secondary-background");
        characterBox.setSpacing(20);

        characterBox.getChildren().addAll(lbFauna, charactersBox1, charactersBox2, criarButton);
        HBox.setHgrow(characterBox, Priority.ALWAYS);

        root.setPadding(new Insets(5));
        root.getChildren().addAll(secondaryBackground, characterBox);

        //Tamanho da janela
        primaryStage.setWidth(700);
        primaryStage.setHeight(500);
    }

    private void registerHandlers()
    {
        Fauna.setSpecie("lobo");

        criarButton.setOnAction(e -> {
            System.out.println("Botão 'Criar' foi clicado");
            
            model.setWidth(Scomprimento.getNumero());
            model.setHeight(SAltura.getNumero());

            for (int i = 0; i < Sfauna.getNumero();i++ ) {
                model.addFauna();
            }

            gameEngine.setInterval(1000 / (int)Sunidade.getValue());
            MainScene mainscene = new MainScene(primaryStage,model,gameEngine);
            primaryStage.setScene(mainscene);
            primaryStage.show();
        });

        option1Box.setOnMouseClicked(e -> option1.setSelected(true));

        option2Box.setOnMouseClicked(e -> option2.setSelected(true));

        option3Box.setOnMouseClicked(e -> option3.setSelected(true));

        option4Box.setOnMouseClicked(e -> option4.setSelected(true));

        characterGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == option1) {
                option1Box.getStyleClass().add("selected-container");
                option2Box.getStyleClass().remove("selected-container");
                option3Box.getStyleClass().remove("selected-container");
                option4Box.getStyleClass().remove("selected-container");
                Fauna.setSpecie("lobo");
            } else if (newValue == option2) {
                option2Box.getStyleClass().add("selected-container");
                option1Box.getStyleClass().remove("selected-container");
                option3Box.getStyleClass().remove("selected-container");
                option4Box.getStyleClass().remove("selected-container");
                Fauna.setSpecie("ovelha");
            } else if (newValue == option3) {
                option3Box.getStyleClass().add("selected-container");
                option1Box.getStyleClass().remove("selected-container");
                option2Box.getStyleClass().remove("selected-container");
                option4Box.getStyleClass().remove("selected-container");
                Fauna.setSpecie("urso");
            } else if (newValue == option4) {
                option4Box.getStyleClass().add("selected-container");
                option1Box.getStyleClass().remove("selected-container");
                option2Box.getStyleClass().remove("selected-container");
                option3Box.getStyleClass().remove("selected-container");
                Fauna.setSpecie("cobra");
            }
        });
    }

}
