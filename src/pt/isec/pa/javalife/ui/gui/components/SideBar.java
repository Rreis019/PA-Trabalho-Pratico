package pt.isec.pa.javalife.ui.gui.components;

import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TextField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * SideBar
 */
public class SideBar extends VBox {

    private SideBarNavbar navbar;
    private VBox ecoTab;
    private VBox inspectTab;



    private ComboBox<String> faunaDropdown;
    private Button btnAddElement;
    private Button btnCreteEco;
    private Button btnImport;
    private Button btnExport;
    private BlueSlider strenghtSlider;
    private Button btnDelElement;
    private TextField txtId;
    private TextField txtType;
    private TextField txtX;
    private TextField txtY;
    private TextField txtEsq;
    private TextField txtCima;
    private TextField txtDir;
    private TextField txtBaixo;

    public SideBar()
    {
        int width = 200;
        setPrefWidth(width);
        setMaxWidth(width);
        VBox.setMargin(this, new Insets(0));
        setAlignment(Pos.TOP_CENTER);

        getStyleClass().add("secondary-background");
        setStyle("-fx-border-width: 0 0 0 2px; -fx-border-color: #373054;");

        ecoTab = new VBox();
        ecoTab.setSpacing(10);
        ecoTab.setMaxWidth(width - 10);

        //VBox.setMargin(ecoTab, new Insets(5));


        faunaDropdown = new ComboBox<>();
        faunaDropdown.getItems().addAll("Fauna", "Flora","Inanimados"); // Add your options here
        faunaDropdown.setPrefWidth(width - 10);
        faunaDropdown.setFocusTraversable(false);
        faunaDropdown.setStyle("-fx-border-width: 0;");
        faunaDropdown.getSelectionModel().selectFirst();

        btnAddElement = new Button("Adicionar Elemento");
        btnAddElement.getStyleClass().add("btn-primary");
        btnAddElement.setPrefWidth(width - 10);
        btnAddElement.setMinHeight(40);

        btnCreteEco = new Button("Criar Ecossistema");
        btnCreteEco.getStyleClass().add("btn-primary");
        btnCreteEco.setPrefWidth(width - 10);
        btnCreteEco.setMinHeight(40);

        btnImport = new Button("Importar");
        btnImport.getStyleClass().add("btn-primary");
        btnImport.setPrefWidth(width - 10);
        btnImport.setMinHeight(40);

        btnExport = new Button("Exportar");
        btnExport.getStyleClass().add("btn-primary");
        btnExport.setPrefWidth(width - 10);
        btnExport.setMinHeight(40);

        Rectangle separate = new Rectangle();
        separate.setWidth(width - 10);
        separate.setHeight(2);
        separate.setFill(Color.web("#373054"));

         Rectangle separate2 = new Rectangle();
        separate2.setWidth(width - 10);
        separate2.setHeight(2);
        separate2.setFill(Color.web("#373054"));

        Rectangle separate3 = new Rectangle();
        separate3.setWidth(width - 10);
        separate3.setHeight(2);
        separate3.setFill(Color.web("#373054"));

        ecoTab.getChildren().addAll(faunaDropdown,btnAddElement, separate, btnCreteEco, btnImport, btnExport);


        inspectTab = new VBox();
        inspectTab.setSpacing(10);
        inspectTab.setMaxWidth(width - 10);
        inspectTab.setVisible(false);

        strenghtSlider = new BlueSlider("Força", 190,0, 0, 100);

        btnDelElement = new Button("Remover Elemento");
        btnDelElement.getStyleClass().add("btn-primary");
        btnDelElement.setPrefWidth(width - 10);
        btnDelElement.setMinHeight(40);

        btnDelElement.setTooltip(new Tooltip("Remove a entidade que esta selecionada"));


        HBox containerId = new HBox();
        Label lbId = new Label("Id  ");
        lbId.setPrefWidth(40);
        txtId = new TextField();
        txtId.setEditable(false);
        lbId.getStyleClass().addAll("text-bold");
        containerId.getChildren().addAll(lbId,txtId);
        containerId.setAlignment(Pos.CENTER);
        txtId.setPrefWidth(TextField.USE_COMPUTED_SIZE);
        containerId.setSpacing(12);



        HBox containerType = new HBox();
        Label lbType = new Label("Tipo");
        lbType.setPrefWidth(40);
        lbType.getStyleClass().addAll("text-bold");
        HBox.setHgrow(lbType, Priority.ALWAYS);
        txtType = new TextField();
        txtType.setEditable(false);
        containerType.getChildren().addAll(lbType,txtType);
        containerType.setAlignment(Pos.CENTER);
        containerType.setSpacing(12);


        Label lbPosicao = new Label("Posição");
        lbPosicao.getStyleClass().addAll("text-bold");


        VBox containerX = new VBox();
        Label lbX = new Label("x");
        lbX.getStyleClass().addAll("text-regular");
        txtX = new TextField();
        containerX.getChildren().addAll(lbX,txtX);

        VBox containerY = new VBox();
        Label lbY = new Label("y");
        lbY.getStyleClass().addAll("text-regular");
        txtY = new TextField();
        containerY.getChildren().addAll(lbY,txtY);

        HBox  containerPos = new HBox();
        containerPos.setSpacing(6);
        containerPos.getChildren().addAll(containerX,containerY);


        Label lbArea = new Label("Area");
        lbArea.getStyleClass().addAll("text-bold");

        VBox containerEsq = new VBox();
        Label lbEsq = new Label("esq");
        txtEsq = new TextField();
        lbEsq.getStyleClass().addAll("text-regular");
        containerEsq.getChildren().addAll(lbEsq,txtEsq);

        VBox containerCima = new VBox();
        Label lbCima = new Label("cima");
        txtCima = new TextField();
        lbCima.getStyleClass().addAll("text-regular");
        containerCima.getChildren().addAll(lbCima,txtCima);

        VBox containerDir = new VBox();
        Label lbDir = new Label("direita");
        txtDir = new TextField();
        lbDir.getStyleClass().addAll("text-regular");
        containerDir.getChildren().addAll(lbDir,txtDir);

        VBox containerBaixo = new VBox();
        Label lbBaixo = new Label("baixo");
        txtBaixo = new TextField();
        lbBaixo.getStyleClass().addAll("text-regular");
        containerBaixo.getChildren().addAll(lbBaixo,txtBaixo);

        HBox containerArea = new HBox();
        containerArea.getChildren().addAll(containerEsq,containerCima,containerDir,containerBaixo);
        containerArea.setSpacing(6);

        StackPane containerImg = new StackPane();
        containerImg.setAlignment(Pos.CENTER);

        VBox rectImg = new VBox();
        rectImg.setPrefWidth(50);
        rectImg.setPrefHeight(50);
        rectImg.setMaxWidth(50);
        rectImg.setMaxHeight(50);
        rectImg.getStyleClass().addAll("primary-background");
        containerImg.getChildren().addAll(rectImg);
        inspectTab.getChildren().addAll(containerId,containerType,separate,lbPosicao,containerPos,separate2,lbArea,containerArea,separate3,strenghtSlider,btnDelElement);


        navbar = new SideBarNavbar(new SideBarNavbar.NavbarCallback() {
            @Override
            public void onFirstButtonClicked() {
                ecoTab.setVisible(true);
                ecoTab.setManaged(true);
                inspectTab.setVisible(false);
                inspectTab.setManaged(false);
            }

            @Override
            public void onSecondButtonClicked() {
                ecoTab.setVisible(false);
                ecoTab.setManaged(false);
                inspectTab.setVisible(true);
                inspectTab.setManaged(true);
            }
        } ,200,35,"Ecossistema","Inspecionar");

        Region space = new Region();
        space.setMinWidth(5);
        space.setMinHeight(5);

        makeNumeric( txtId);
        //makeNumeric( txtType);
        makeNumeric( txtX);
        makeNumeric( txtY);
        makeNumeric( txtEsq);
        makeNumeric( txtCima);
        makeNumeric( txtDir);
        makeNumeric( txtBaixo);
        this.getChildren().addAll(navbar,space,ecoTab,inspectTab);
    }



    public void showEcoTab()
    {
        navbar.simulateFirstButtonClick();
    }
   
    public void showInspectTab()
    {
        navbar.simulateSecondButtonClick();
    }

    public ComboBox<String> getFaunaDropdown() {return faunaDropdown;}
    public Button getBtnAddElement() {return btnAddElement;}
    public Button getBtnCreteEco() {return btnCreteEco;}
    public Button getBtnImport() {return btnImport;}
    public Button getBtnExport() {return btnExport;}
    public BlueSlider getStrenghtSlider() {return strenghtSlider;}
    public Button getBtnDelElement() {return btnDelElement;}
    public TextField getTxtId() {return txtId;}
    public TextField getTxtType() {return txtType;}


    public TextField getTxtX() {return txtX;}
    public TextField getTxtY() {return txtY;}
    public TextField getTxtEsq() {return txtEsq;}
    public TextField getTxtDir() {return txtDir;}
    public TextField getTxtCima() {return txtCima;}
    public TextField getTxtBaixo() {return txtBaixo;}


    public static void makeNumeric(TextField textField) {
        // Adiciona um ChangeListener para validar a entrada do usuário
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) { // Verifica se a nova entrada contém apenas dígitos
                    textField.setText(newValue.replaceAll("[^\\d]", "")); // Remove caracteres não numéricos
                }
            }
        });
    }
}