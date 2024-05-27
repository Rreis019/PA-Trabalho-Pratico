package pt.isec.pa.javalife.ui.gui.scenes;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import pt.isec.pa.javalife.model.Ecosystem;
import pt.isec.pa.javalife.model.EcosystemManager;
import pt.isec.pa.javalife.model.data.elements.Element;
import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.gameengine.GameEngine;
import pt.isec.pa.javalife.ui.gui.FaunaImagesManager;
import pt.isec.pa.javalife.ui.gui.components.*;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class CreateEcosystemScene extends Scene
{
    Stage primaryStage;
    Button criarButton;

    EcosystemManager model;

    BlueSpinner SAltura;
    BlueSpinner Scomprimento;
    BlueSpinner Sfauna;
    BlueSpinner Sflora;
    BlueSpinner Sinanimados;
    BlueSpinner sUnitTimer;
    BlueSlider sEnergyMovement;
    BlueSlider sDamageFauna;

    ToggleGroup characterGroup;
    VBox option1Box, option2Box, option3Box, option4Box;
    RadioButton option1, option2, option3, option4;



    public CreateEcosystemScene(Stage primaryStage_,EcosystemManager manager_)
    {
        super(new HBox());
        model = manager_;
        primaryStage =  primaryStage_;
        createView(primaryStage_);
        registerHandlers();
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
        Texto.setAlignment(Pos.CENTER_LEFT);
        Texto.getStyleClass().add("heading");
        Texto.setStyle("-fx-text-alignment: left;");

        //Barra Branca
        Rectangle separate = new Rectangle();
        separate.setHeight(2);
        separate.setWidth(305);
        separate.setFill(Color.WHITE);

        // Campos de texto
        //Label TextoNome = new Label("Nome");
        //TextoNome.setStyle("-fx-text-fill: white; -fx-text-alignment: left; -fx-font-size : 15px;");
        //TextField Nome = new TextField();
        

        //Componentes
        SAltura = new BlueSpinner("Altura",300,200,500,20);
        Scomprimento = new BlueSpinner("Comprimento",300,200,500,5);
        Sfauna = new BlueSpinner("Quantidade Fauna",10,0,100,5);
        Sflora = new BlueSpinner("Quantidade Flora",10,0,100,5);
        Sinanimados = new BlueSpinner("Quantidade Inanimados",10,0,100,5);
        sUnitTimer = new BlueSpinner("Unidade de tempo", 100,10,100, 1000);

        sEnergyMovement = new BlueSlider("EnergiaMovimento(Fauna)", 300,0.1,0.5, 5);
        sDamageFauna = new BlueSlider("Dano da Fauna", 300,0.1,1, 5);



        sEnergyMovement.setFloat(true);
        sDamageFauna.setFloat(true);


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
        hbox3.getChildren().addAll(Sinanimados,sUnitTimer);
        hbox3.setSpacing(10); // Espaçamento entre os componentes
        hbox3.setAlignment(Pos.CENTER);


        VBox title = new VBox();
        title.getChildren().addAll(Texto,separate);
        //Label Erro = new Label("Mensagem de Erro");
        //Erro.setStyle("-fx-text-fill: white;-fx-font-size : 15px;");
        content.getChildren().addAll(title, hbox1, hbox2,hbox3,sDamageFauna,sEnergyMovement, criarButton);
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
        charactersBox1.setAlignment(Pos.CENTER);
        option3Box = new VBox();
        option3Box.setAlignment(Pos.CENTER);
        option3Box.getChildren().addAll(lbUrso,option3,ursoImage);

        option4Box = new VBox();
        option4Box.setAlignment(Pos.CENTER);
        option4Box.getChildren().addAll(lbCobra,option4,cobraImage);

        HBox charactersBox2 = new HBox();
        charactersBox2.getChildren().addAll(option3Box, option4Box);
        charactersBox2.setSpacing(60);
         charactersBox2.setAlignment(Pos.CENTER);

        VBox characterBox = new VBox();
        characterBox.setAlignment(Pos.CENTER);
        characterBox.getStyleClass().add("secondary-background");
        characterBox.setSpacing(20);

        characterBox.getChildren().addAll(lbFauna, charactersBox1, charactersBox2, criarButton);
        characterBox.setAlignment(Pos.CENTER);


        HBox.setHgrow(characterBox, Priority.ALWAYS);

        root.setPadding(new Insets(5));
        root.getChildren().addAll(secondaryBackground, characterBox);

        //Tamanho da janela
        primaryStage.setWidth(732);
        primaryStage.setHeight(515);
    }

    private void registerHandlers()
    {
        Fauna temp = new Fauna(null, 0, 0);

        criarButton.setOnAction(e -> {
            System.out.println("Botão 'Criar' foi clicado");
            
            model.clearElements();
            model.setWidth(Scomprimento.getNumero());
            model.setHeight(SAltura.getNumero());
            model.makeWall();

            Fauna.decMovementEnergy = sEnergyMovement.getValue();
            Fauna.damageToFlora = sDamageFauna.getValue();
            model.resetTicksCounter();

            for (int i = 0; i < Sinanimados.getNumero();i++ ) {model.addElementToRandomFreePosition(Element.INANIMATE);}



            for (int i = 0; i < Sfauna.getNumero();i++ ) {model.addElementToRandomFreePosition(Element.FAUNA);}
            for (int i = 0; i < Sflora.getNumero();i++ ) {model.addElementToRandomFreePosition(Element.FLORA);}

            model.setGameInterval((long)sUnitTimer.getNumero());
            model.resumeGame();
            MainScene mainscene = new MainScene(primaryStage,model);
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
                temp.setImage("lobo");
            } else if (newValue == option2) {
                option2Box.getStyleClass().add("selected-container");
                option1Box.getStyleClass().remove("selected-container");
                option3Box.getStyleClass().remove("selected-container");
                option4Box.getStyleClass().remove("selected-container");
                temp.setImage("ovelha");
            } else if (newValue == option3) {
                option3Box.getStyleClass().add("selected-container");
                option1Box.getStyleClass().remove("selected-container");
                option2Box.getStyleClass().remove("selected-container");
                option4Box.getStyleClass().remove("selected-container");
                temp.setImage("urso");
            } else if (newValue == option4) {
                option4Box.getStyleClass().add("selected-container");
                option1Box.getStyleClass().remove("selected-container");
                option2Box.getStyleClass().remove("selected-container");
                option3Box.getStyleClass().remove("selected-container");
                temp.setImage("cobra");
            }
        });
    }

}
