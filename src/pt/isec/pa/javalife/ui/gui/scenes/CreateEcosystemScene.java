package pt.isec.pa.javalife.ui.gui.scenes;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.stage.Stage;

class CreateEcosystemScene extends Scene
{
    Stage primaryStage;
    private Scene scene;

    Button criarButton;
    public CreateEcosystemScene(Stage primaryStage_)
    {
        super(new VBox());
        createView(primaryStage_);
        registerHandlers();
        primaryStage =  primaryStage_;
    }

    private void createView(Stage primaryStage)
    {
        getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        VBox root = (VBox) this.getRoot();
        root.getStyleClass().add("primary-background");


        primaryStage.setTitle("CriarEcossistema");

        // Campos de texto
        TextField nomeField = new TextField();
        nomeField.setPromptText("Nome");

        TextField larguraField = new TextField("500");
        larguraField.setPromptText("Largura (px)");

        TextField comprimentoField = new TextField("300");
        comprimentoField.setPromptText("Comprimento (px)");

        // Contadores
        Spinner<Integer> faunaSpinner = new Spinner<>(0, 100, 23);
        Spinner<Integer> floraSpinner = new Spinner<>(0, 100, 23);
        Spinner<Integer> inanimadosSpinner = new Spinner<>(0, 100, 23);

        // Switch de mensagem de erro
        CheckBox erroCheckBox = new CheckBox("Mensagem de Erro");
        erroCheckBox.getStyleClass().add("heading");

        // Botão de criar
        criarButton = new Button("Criar");
        criarButton.setMinWidth(150);
        criarButton.setMinHeight(45);
        criarButton.getStyleClass().add("btn-primary");


        root.setPadding(new Insets(10));
        root.setSpacing(8);


        primaryStage.setMinWidth(400);
        primaryStage.setMaxWidth(400);
        primaryStage.setMinHeight(600);
        primaryStage.setMaxHeight(600);

        root.getChildren().addAll(nomeField, larguraField, comprimentoField, faunaSpinner, floraSpinner, inanimadosSpinner, erroCheckBox, criarButton);
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
