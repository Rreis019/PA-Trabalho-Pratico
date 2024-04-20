package pt.isec.pa.javalife.ui.gui.scenes;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class StartScene extends Scene {

    public StartScene(Stage primaryStage) {
        super(new VBox());
        createView(primaryStage);
        registerHandlers();
    }

    private void createView(Stage primaryStage) {
    	getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        VBox root = (VBox) this.getRoot();

        root.getStyleClass().add("primary-background");
        root.setPadding(new Insets(10));
        root.setSpacing(10);

        HBox topPanel = new HBox();
        HBox bottomPanel = new HBox();
        topPanel.getStyleClass().add("secondary-background");
        bottomPanel.getStyleClass().add("secondary-background");
        VBox.setVgrow(topPanel, Priority.ALWAYS);
        VBox.setVgrow(bottomPanel, Priority.ALWAYS);

        topPanel.setAlignment(Pos.CENTER);
        bottomPanel.setAlignment(Pos.CENTER);
        topPanel.setSpacing(30);
        bottomPanel.setSpacing(30);

        Button btnCreate = new Button("Criar");
        Label lbCreate = new Label("Comece do Zero: Crie Seu Próprio Ecossistema");
        Button btnImport = new Button("Importar");
        Label lbImport = new Label("Traga Diversidade: Importe um Ecossistema Existente");

        lbCreate.setWrapText(true);
        lbCreate.setMaxWidth(300);
        lbImport.setMaxWidth(300);
        lbImport.setWrapText(true);
        lbImport.setAlignment(Pos.CENTER_RIGHT);
        lbCreate.getStyleClass().add("heading");
        lbImport.getStyleClass().add("heading");
        lbImport.setStyle("-fx-text-alignment: right;");

        btnCreate.setMinWidth(150);
        btnCreate.setMinHeight(45);
        btnImport.setMinWidth(150);
        btnImport.setMinHeight(45);
        btnCreate.getStyleClass().add("btn-primary");
        btnImport.getStyleClass().add("btn-primary");

        topPanel.getChildren().addAll(lbCreate, btnCreate);
        bottomPanel.getChildren().addAll(btnImport, lbImport);

        root.getChildren().addAll(topPanel, bottomPanel);

        primaryStage.setWidth(671);
        primaryStage.setHeight(515);
    }

    private void registerHandlers() {
        // Implementação do registro de event handlers
    }
}
