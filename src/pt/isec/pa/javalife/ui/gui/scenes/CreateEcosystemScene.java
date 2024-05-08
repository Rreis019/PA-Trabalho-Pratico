package pt.isec.pa.javalife.ui.gui.scenes;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import pt.isec.pa.javalife.model.Ecosystem;
import pt.isec.pa.javalife.ui.gui.components.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class CreateEcosystemScene extends Scene
{
    Stage primaryStage;
    private Scene scene;

    Button criarButton;
    Ecosystem model;
    public CreateEcosystemScene(Stage primaryStage_,Ecosystem ecosystem)
    {
        super(new VBox());
        model = ecosystem;
        primaryStage =  primaryStage_;
        createView(primaryStage_);
        registerHandlers();
    }

    private void createView(Stage primaryStage)
    {
        //Adiciona o ficheiro de classes de css
        getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        VBox root = (VBox) this.getRoot();
        root.getStyleClass().add("primary-background");
        root.setPadding(new Insets(10));
        root.setSpacing(8);

        VBox secondaryBackground = new VBox();
        secondaryBackground.getStyleClass().add("secondary-background");
        secondaryBackground.setAlignment(Pos.CENTER);
        VBox.setVgrow(secondaryBackground, Priority.ALWAYS);

        //Titulo da pagina
        primaryStage.setTitle("JavaLife-CriarEcossistema");

        //Paa colocar tudo com a cor secundária de fundo
        VBox content = new VBox();
        content.getStyleClass().add("secondary-background");
        content.setSpacing(10);
        content.setMaxWidth(305);

        //Texto
        Label Texto = new Label("Criação do ecossistema");
        Texto.setWrapText(true);
        Texto.setAlignment(Pos.CENTER_RIGHT);
        Texto.getStyleClass().add("heading");
        Texto.setStyle("-fx-text-alignment: left;");


        //Barra Branco
        Rectangle separate = new Rectangle();
        separate.setHeight(2);
        separate.setWidth(305);
        separate.setFill(Color.WHITE);

        // Campos de texto
        Label TextoNome = new Label("Nome");
        TextoNome.setStyle("-fx-text-fill: white; -fx-text-alignment: left; -fx-font-size : 15px;");
        TextField Nome = new TextField();

        //Componentes
        BlueSpinner Slargura = new BlueSpinner("Largura",10,100,5);
        BlueSpinner Scomprimento = new BlueSpinner("Comprimento",10,100,5);
        BlueSpinner Sfauna = new BlueSpinner("Quantidade Fauna",10,100,5);
        BlueSpinner Sflora = new BlueSpinner("Quantidade Flora",10,100,5);
        BlueSpinner Sinanimados = new BlueSpinner("Quantidade Inanimados",10,100,5);
        BlueSlider Sunidade = new BlueSlider("Unidade de Tempo", 300, 50, 100);

        // Botão de criar
        criarButton = new Button("Criar");
        criarButton.setMinWidth(150);
        criarButton.setMinHeight(45);
        criarButton.getStyleClass().add("btn-primary");


        HBox hbox1 = new HBox();
        hbox1.getChildren().addAll(Slargura, Scomprimento);
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
        root.getChildren().addAll(secondaryBackground);

        //Tamanho da janela
        primaryStage.setMinWidth(400);
        primaryStage.setMaxWidth(400);
        primaryStage.setMinHeight(600);
        primaryStage.setMaxHeight(600);
    }

    private void registerHandlers()
    {
        criarButton.setOnAction(e -> {
            // Aqui você pode adicionar o código para a ação do botão "Criar"
            System.out.println("Botão 'Criar' foi clicado");
            MainScene mainscene = new MainScene(primaryStage,model);
            // Criar um novo palco (janela)
            primaryStage.setScene(mainscene);
            primaryStage.show();
        });
    }

}
