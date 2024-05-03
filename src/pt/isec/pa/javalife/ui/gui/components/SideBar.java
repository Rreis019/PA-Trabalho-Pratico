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
/**
 * SideBar
 */
public class SideBar extends VBox {

    private SideBarNavbar navbar;
    private VBox ecoTab;
    private VBox inspectTab;

    public SideBar()
    {
        setPrefWidth(200);
        setMaxWidth(200);
        VBox.setMargin(this, new Insets(0));
        setAlignment(Pos.TOP_CENTER);

        getStyleClass().add("secondary-background");
        setStyle("-fx-border-width: 0 0 0 2px; -fx-border-color: #373054;");

        ecoTab = new VBox();
        ecoTab.setSpacing(10);
        ecoTab.setMaxWidth(190);

        //VBox.setMargin(ecoTab, new Insets(5));


        ComboBox<String> faunaDropdown = new ComboBox<>();
        faunaDropdown.getItems().addAll("Fauna", "Flora","Inanimados"); // Add your options here
        faunaDropdown.setPromptText("Fauna");
        faunaDropdown.setPrefWidth(190);
        faunaDropdown.setFocusTraversable(false);
        faunaDropdown.setStyle("-fx-border-width: 0;");

        Button btnAddElement = new Button("Adicionar Elemento");
        btnAddElement.getStyleClass().add("btn-primary");
        btnAddElement.setPrefWidth(190);
        btnAddElement.setMinHeight(40);






        Button btnCreteEco = new Button("Criar Ecossistema");
        btnCreteEco.getStyleClass().add("btn-primary");
        btnCreteEco.setPrefWidth(190);
        btnCreteEco.setMinHeight(40);

        Button btnImport = new Button("Importar");
        btnImport.getStyleClass().add("btn-primary");
        btnImport.setPrefWidth(190);
        btnImport.setMinHeight(40);

        Button btnExport = new Button("Exportar");
        btnExport.getStyleClass().add("btn-primary");
        btnExport.setPrefWidth(190);
        btnExport.setMinHeight(40);

        Rectangle separate = new Rectangle();
        separate.setWidth(190);
        separate.setHeight(2);
        separate.setFill(Color.web("#373054"));

         Rectangle separate2 = new Rectangle();
        separate2.setWidth(190);
        separate2.setHeight(2);
        separate2.setFill(Color.web("#373054"));

        Rectangle separate3 = new Rectangle();
        separate3.setWidth(190);
        separate3.setHeight(2);
        separate3.setFill(Color.web("#373054"));

        ecoTab.getChildren().addAll(faunaDropdown,btnAddElement, separate, btnCreteEco, btnImport, btnExport);


        inspectTab = new VBox();
        inspectTab.setSpacing(10);
        inspectTab.setMaxWidth(190);
        inspectTab.setVisible(false);

        BlueSlider strenghtSlider = new BlueSlider("Força", 190, 0, 100);

        Button btnDelElement = new Button("Remover Elemento");
        btnDelElement.getStyleClass().add("btn-primary");
        btnDelElement.setPrefWidth(190);
        btnDelElement.setMinHeight(40);

        btnDelElement.setTooltip(new Tooltip("Remove a entidade que esta selecionada"));


        HBox containerId = new HBox();
        Label lbId = new Label("Id  ");
        lbId.setPrefWidth(40);
        TextField txtId = new TextField();
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
        TextField txtType = new TextField();
        containerType.getChildren().addAll(lbType,txtType);
        containerType.setAlignment(Pos.CENTER);
        containerType.setSpacing(12);


        Label lbPosicao = new Label("Posição");
        lbPosicao.getStyleClass().addAll("text-bold");


        VBox containerX = new VBox();
        Label lbX = new Label("x");
        lbX.getStyleClass().addAll("text-regular");
        TextField txtX = new TextField();
        containerX.getChildren().addAll(lbX,txtX);

        VBox containerY = new VBox();
        Label lbY = new Label("y");
        lbY.getStyleClass().addAll("text-regular");
        TextField txtY = new TextField();
        containerY.getChildren().addAll(lbY,txtY);

        HBox  containerPos = new HBox();
        containerPos.setSpacing(6);
        containerPos.getChildren().addAll(containerX,containerY);


        Label lbArea = new Label("Area");
        lbArea.getStyleClass().addAll("text-bold");

        VBox containerEsq = new VBox();
        Label lbEsq = new Label("esq");
        TextField txtEsq = new TextField();
        lbEsq.getStyleClass().addAll("text-regular");
        containerEsq.getChildren().addAll(lbEsq,txtEsq);

        VBox containerCima = new VBox();
        Label lbCima = new Label("cima");
        TextField txtCima = new TextField();
        lbCima.getStyleClass().addAll("text-regular");
        containerCima.getChildren().addAll(lbCima,txtCima);

        VBox containerDir = new VBox();
        Label lbDir = new Label("direita");
        TextField txtDir = new TextField();
        lbDir.getStyleClass().addAll("text-regular");
        containerDir.getChildren().addAll(lbDir,txtDir);

        VBox containerBaixo = new VBox();
        Label lbBaixo = new Label("baixo");
        TextField txtBaixo = new TextField();
        lbBaixo.getStyleClass().addAll("text-regular");
        containerBaixo.getChildren().addAll(lbBaixo,txtBaixo);

        HBox containerArea = new HBox();
        containerArea.getChildren().addAll(containerEsq,containerCima,containerDir,containerBaixo);
        containerArea.setSpacing(6);

        StackPane containerImg = new StackPane();
        containerImg.setAlignment(Pos.CENTER);

        VBox rectImg = new VBox();
        rectImg.setPrefWidth(100);
        rectImg.setPrefHeight(100);
        rectImg.setMaxWidth(100);
        rectImg.setMaxHeight(100);
        rectImg.getStyleClass().addAll("primary-background");
        containerImg.getChildren().addAll(rectImg);
        inspectTab.getChildren().addAll(containerImg,containerId,containerType,separate,lbPosicao,containerPos,separate2,lbArea,containerArea,separate3,strenghtSlider,btnDelElement);


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

        this.getChildren().addAll(navbar,space,ecoTab,inspectTab);
    }
}