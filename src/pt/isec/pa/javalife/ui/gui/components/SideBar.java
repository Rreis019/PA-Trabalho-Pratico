package pt.isec.pa.javalife.ui.gui.components;

import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.shape.Rectangle;
import pt.isec.pa.javalife.model.EcosystemManager;
import pt.isec.pa.javalife.model.data.elements.Element;
import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.data.elements.Flora;
import pt.isec.pa.javalife.model.data.elements.IElement;
import pt.isec.pa.javalife.model.gameengine.GameEngineState;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import pt.isec.pa.javalife.model.data.Area;
import pt.isec.pa.javalife.model.data.elements.BaseElement;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
/**
 * 
 */
public class SideBar extends VBox {

    private SideBarNavbar navbar;
    private VBox ecoTab;
    private VBox inspectTab;



    private ComboBox<String> faunaDropdown;
    private Button btnAddElement;
    private Button btnCreteEco;
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

    BlueSlider sUnitTimer;
    BlueSlider sEnergyMovement;
    BlueSlider sDamageFauna;

    private Button btnUndo;
    private Button btnRedo;

    EcosystemManager model;


    public SideBar(EcosystemManager manager_)
    {
        model = manager_;
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

                Rectangle separate4 = new Rectangle();
        separate4.setWidth(width - 10);
        separate4.setHeight(2);
        separate4.setFill(Color.web("#373054"));

        sUnitTimer = new BlueSlider("UnidadeTempo", 300,10,100, 1000);
        sEnergyMovement = new BlueSlider("DecEnergia", 300,0.1,0.5, 5);
        sDamageFauna = new BlueSlider("DanoDaFauna", 300,0.1,1, 5);

        sEnergyMovement.setFloat(true);
        sDamageFauna.setFloat(true);


        btnUndo = new Button("Undo");
        btnUndo.getStyleClass().add("btn-primary");
        btnUndo.setPrefWidth(width - 10);
        btnUndo.setMinHeight(40);

        btnRedo = new Button("Redo");
        btnRedo.getStyleClass().add("btn-primary");
        btnRedo.setPrefWidth(width - 10);
        btnRedo.setMinHeight(40);


        ecoTab.getChildren().addAll(faunaDropdown,btnAddElement, separate4, btnCreteEco,btnUndo,btnRedo,sUnitTimer,sEnergyMovement,sDamageFauna);


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

        txtEsq.setDisable(true);
        txtDir.setDisable(true);
        txtCima.setDisable(true);
        txtBaixo.setDisable(true);

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

        if(model.getCurrentState() == GameEngineState.RUNNING){disable();}

        navbar = new SideBarNavbar(new SideBarNavbar.NavbarCallback() {
            @Override
            public boolean onFirstButtonClicked() {
                ecoTab.setVisible(true);
                ecoTab.setManaged(true);
                inspectTab.setVisible(false);
                inspectTab.setManaged(false);

                return true;
            }

            @Override
            public boolean onSecondButtonClicked() {

               if (model.getElement(model.getInspectTargetId()) == null ) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText(null);
                    alert.setContentText("Para inspecionar um objeto, primeiro clique com o botão direito em um.");
                    alert.showAndWait();
                    return false;
                }

                ecoTab.setVisible(false);
                ecoTab.setManaged(false);
                inspectTab.setVisible(true);
                inspectTab.setManaged(true);

                return true;
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
        
        sUnitTimer.setValue(model.getGameInterval());
        sEnergyMovement.setValue(model.getFaunaMovementEnergy());
        sDamageFauna.setValue(model.getDamageToFlora());
        registerHandlers();
    }


    public void disable()
    {
        txtX.setDisable(true);
        txtY.setDisable(true);
        sUnitTimer.setDisable(true);
        sDamageFauna.setDisable(true);
        sEnergyMovement.setDisable(true);
        strenghtSlider.setDisable(true);
    }

    public void enable()
    {
        txtX.setDisable(false);
        txtY.setDisable(false);
        sUnitTimer.setDisable(false);
        sDamageFauna.setDisable(false);
        sEnergyMovement.setDisable(false);
        strenghtSlider.setDisable(false);
    }



    // private void showInspect(IElement element) {
   public void update()
    {
        int id =  model.getInspectTargetId();
        IElement ent = model.getElement(id);

        this.getSsEnergyMovement().setValue(model.getFaunaMovementEnergy());
        this.getSUnitTimer().setValue(model.getGameInterval());
        this.getSsDamageFauna().setValue(model.getDamageToFlora());

        if(ent != null)
        {
            this.getTxtId().setText(String.valueOf((int)ent.getId()));
            this.getTxtType().setText(ent.getTypeString());
            this.getTxtX().setText(String.valueOf((int)ent.getArea().left()));
            this.getTxtY().setText(String.valueOf((int)ent.getArea().top()));


            this.getTxtEsq().setText(String.valueOf((int)ent.getArea().left()));
            this.getTxtDir().setText(String.valueOf((int)ent.getArea().right()));
            this.getTxtCima().setText(String.valueOf((int)ent.getArea().top()));
            this.getTxtBaixo().setText(String.valueOf((int)ent.getArea().bottom()));

            if(ent.getType() == Element.FAUNA)
            {
                this.getStrenghtSlider().setValue((int)((Fauna)ent).getStrength());
            }
            else if(ent.getType() == Element.FLORA)
            {
                this.getStrenghtSlider().setValue((int)((Flora)ent).getStrength());
            }

        }
    }

    void registerHandlers()
    {
        sUnitTimer.getSlider().addEventFilter(MouseEvent.MOUSE_RELEASED, event -> {
            if (model.getCurrentState() != GameEngineState.PAUSED) {showModError(); return;}
            model.setInterval((long)sUnitTimer.getValue());
        });

        sEnergyMovement.getSlider().addEventFilter(MouseEvent.MOUSE_RELEASED, event -> {
            if (model.getCurrentState() != GameEngineState.PAUSED) {showModError(); return;}
            model.setEnergyPerMovement(sEnergyMovement.getValue());
        });

        sDamageFauna.getSlider().addEventFilter(MouseEvent.MOUSE_RELEASED, event -> {
            if (model.getCurrentState() != GameEngineState.PAUSED) {showModError(); return;}
            model.setDamageFaunaToFlora(sDamageFauna.getValue());
        });

        txtX.setOnKeyReleased(event -> {
                if(model.getCurrentState() != GameEngineState.PAUSED){return;}
                IElement ent = model.getElement(model.getInspectTargetId());
                if(ent == null){return;}
                Area area = ((BaseElement)ent).getArea();
                model.setElementPos(ent,Integer.valueOf(txtX.getText()) ,area.top() );
        });

        txtY.setOnKeyReleased(event -> {
                if(model.getCurrentState() != GameEngineState.PAUSED){return;}
                IElement ent = model.getElement(model.getInspectTargetId());
                if(ent == null){return;}
                Area area = ((BaseElement)ent).getArea();
                model.setElementPos(ent,area.left(),Integer.valueOf(txtY.getText()));

                //ent.setPositionY(Integer.valueOf(sidebar.getTxtY().getText()));
        });

       /*
        txtY.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                            System.out.println("y :):(:(:):(:");
                if(model.getCurrentState() != GameEngineState.PAUSED){return;}
                IElement ent = model.getElement(model.getInspectTargetId());
                if(ent == null){return;}
                

                Area area = ((BaseElement)ent).getArea();

                //model.editElement(ent,area.left(),(double)Integer.valueOf(sidebar.getTxtY()),);
    
                model.setElementPos(ent,area.left(),Integer.valueOf(txtY.getText()));

                //ent.setPositionY(Integer.valueOf(sidebar.getTxtY().getText()));
            }
        });
*/  
         //private void showInspect(IElement element) {

       

        /*
        txtEsq.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(model.getCurrentState() != GameEngineState.PAUSED){showModError(); return;}
                IElement ent = model.getElement(model.getInspectTargetId());
                if(ent == null){return;}
                ent.setPositionX(Integer.valueOf(txtEsq.getText()));
            }
        });
        */

        strenghtSlider.getSlider().addEventFilter(MouseEvent.MOUSE_RELEASED, event -> {
            if(model.getCurrentState() != GameEngineState.PAUSED){return;}
            IElement ent = model.getElement(model.getInspectTargetId());
            if(ent == null){return;}
            model.setElementStrenght(ent, Double.valueOf(strenghtSlider.getValue()));
        });


        btnDelElement.setOnAction(event -> {
            if (model.getCurrentState() != GameEngineState.PAUSED) {showModError(); return;}
            IElement element_ = model.getElement(model.getInspectTargetId());
            if(element_ == null){return;}
            model.removeElement(element_);
            showEcoTab();
        //    onRender(gc);
        });


        btnAddElement.setOnAction(event -> {
            if (model.getCurrentState() != GameEngineState.PAUSED) {showModError(); return;}
            String selectedType = faunaDropdown.getSelectionModel().getSelectedItem();

            // Dependendo do tipo selecionado, adicione o tipo correspondente de elemento
            if (selectedType != null) {
                Element elementType;
                switch (selectedType) {
                    case "Fauna":
                        elementType = Element.FAUNA;
                        break;
                    case "Flora":
                        elementType = Element.FLORA;
                        break;
                    case "Inanimados":
                        elementType = Element.INANIMATE;
                        break;
                    default:
                        // Trate qualquer outro caso aqui, se necessário
                        elementType = null;
                        break;
                }
                // Adicione o elemento com o tipo determinado
                if (elementType != null) {
                    model.addElement(elementType);
                    int id = model.getLastElementId();
                    model.setInspectTarget(id);
                    this.update();
                    System.out.println("id" + id);
                    showInspectTab();
                    model.renderUpdated();
                }
            }
        });

        btnUndo.setOnAction(event -> {model.undo();});
        btnRedo.setOnAction(event -> {model.redo();});
    }

    public void showEcoTab()
    {
        navbar.simulateFirstButtonClick();
    }
   
    public void showInspectTab()
    {
        navbar.simulateSecondButtonClick();
    }


    void showModError()
    {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText("Tem que parar simulação para configurar o ecossistema...");
        alert.showAndWait();
    } 


    public ComboBox<String> getFaunaDropdown() {return faunaDropdown;}
    public Button getBtnAddElement() {return btnAddElement;}
    public Button getBtnCreteEco() {return btnCreteEco;}
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

    public BlueSlider getSUnitTimer(){return sUnitTimer;}
    public BlueSlider getSsEnergyMovement(){return sEnergyMovement;}
    public BlueSlider getSsDamageFauna(){return sDamageFauna;}

    public static void makeNumeric(TextField textField) {
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