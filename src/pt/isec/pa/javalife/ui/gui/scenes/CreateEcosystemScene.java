package pt.isec.pa.javalife.ui.gui.scenes;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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
    public CreateEcosystemScene(Stage primaryStage_)
    {
        super(new VBox());
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

        //Titulo da pagina
        primaryStage.setTitle("JavaLife-CriarEcossistema");

        //Paa colocar tudo com a cor secundária de fundo
        VBox secondaryVBox = new VBox();
        secondaryVBox.getStyleClass().add("secondary-background");
        secondaryVBox.setSpacing(10);
        VBox.setVgrow(secondaryVBox, Priority.ALWAYS);

        //Texto
        Label Texto = new Label("Criação do ecossistema");
        Texto.setWrapText(true);
        Texto.setMaxWidth(300);
        Texto.setAlignment(Pos.CENTER_RIGHT);
        Texto.getStyleClass().add("heading");
        Texto.setStyle("-fx-text-alignment: right;");


        //Barra Branco
        Rectangle separate = new Rectangle();
        separate.setWidth(190);
        separate.setHeight(2);
        separate.setFill(Color.WHITE);

        // Campos de texto
        Label TextoNome = new Label("Nome");
        TextoNome.setMaxWidth(300);
        TextoNome.setStyle("-fx-text-fill: white; -fx-text-alignment: left; -fx-font-size : 15px;");
        TextField Nome = new TextField();
        Nome.setStyle("-fx-background-color: rgba(0,0,0,0.5);-fx-text-fill: white;");

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
        //hbox1.setAlignment(Pos.CENTER);

        HBox hbox2 = new HBox();
        hbox2.getChildren().addAll(Sfauna, Sflora);
        hbox2.setSpacing(10); // Espaçamento entre os componentes
        //hbox2.setAlignment(Pos.CENTER);

        Label Erro = new Label("Mensagem de Erro");
        Erro.setStyle("-fx-text-fill: white;-fx-font-size : 15px;");

        //Adiciona tudo ao plano secundário
        secondaryVBox.setAlignment(Pos.CENTER);
        secondaryVBox.getChildren().addAll(Texto, separate, TextoNome,Nome, hbox1, hbox2,Sinanimados, Sunidade , Erro, criarButton);

        //Adiciona o segundo plano ao plano principal
        root.getChildren().addAll(secondaryVBox);

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
            MainScene mainscene = new MainScene(primaryStage);
            // Criar um novo palco (janela)
            primaryStage.setScene(mainscene);
            primaryStage.show();
        });
    }

}
