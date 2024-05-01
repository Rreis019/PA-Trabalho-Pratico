package pt.isec.pa.javalife.ui.gui.scenes;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;



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

        btninspecionar.setMinWidth(10);
        btninspecionar.setMinHeight(5);
        btnconfigurar.setMinWidth(10);
        btnconfigurar.setMinHeight(5);
        btninspecionar.getStyleClass().add("btn-secundary");
        btnconfigurar.getStyleClass().add("btn-secundary");

        VBox vboxBottonAndLateral = new VBox(bottonpanel, lateralpanel);
        VBox vbox = new VBox(vboxBottonAndLateral);
        bottonpanel.getChildren().addAll(btninspecionar, btnconfigurar);
        HBox hboxMainAndVBox = new HBox(mainpanel, spacer, vbox);
        HBox hboxButtons = new HBox(btninspecionar, btnconfigurar);
        hboxButtons.setSpacing(5);
        hboxButtons.setPadding(new Insets(5));
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
        btninspecionar.setOnAction(e -> {
            VBox inspecionarPanel = new VBox();

            ComboBox<String> faunaDropdown = new ComboBox<>();
            faunaDropdown.getItems().addAll("Option 1", "Option 2"); // Add your options here
            faunaDropdown.setPromptText("Fauna");

            Button btnAdicionarElemento = new Button("Adicionar Elemento");
            Button btnCriarEcossistema = new Button("Criar Ecossistema");
            Button btnImportar = new Button("Importar");
            Button btnExportar = new Button("Exportar");

            inspecionarPanel.getChildren().addAll(faunaDropdown, btnAdicionarElemento, btnCriarEcossistema, btnImportar, btnExportar);

            // Assuming mainpanel is the panel you want to update
            lateralpanel.getChildren().clear();
            lateralpanel.getChildren().add(inspecionarPanel);

            System.out.println("inspecionar");
        });

        btnconfigurar.setOnAction(e -> {
            VBox configurarPanel = new VBox();

            Label lblImagemDoBicho = new Label("Imagem do bicho");
            TextField txtId = new TextField();
            txtId.setPromptText("Id");

            ComboBox<String> tipoDropdown = new ComboBox<>();
            tipoDropdown.getItems().addAll("Option 1", "Option 2"); // Add your options here
            tipoDropdown.setPromptText("Tipo: Fauna");

            TextField txtPosicao = new TextField();
            txtPosicao.setPromptText("Posição");

            TextField txtArea = new TextField();
            txtArea.setPromptText("Area");

            Slider sliderForca = new Slider();
            sliderForca.setMin(0);
            sliderForca.setMax(100);
            sliderForca.setValue(23);

            Button btnRemover = new Button("Remover");

            configurarPanel.getChildren().addAll(lblImagemDoBicho, txtId, tipoDropdown, txtPosicao, txtArea, sliderForca, btnRemover);

            // Assuming mainpanel is the panel you want to update
            lateralpanel.getChildren().clear();
            lateralpanel.getChildren().add(configurarPanel);
        });
    }
}
