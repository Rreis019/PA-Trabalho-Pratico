package pt.isec.pa.javalife.ui.gui.components;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class SideBarNavbar extends AnchorPane {
    
    private NavbarCallback callback;
    Rectangle backgroundInspect;
    Polygon backgroundEcossistema;

    public SideBarNavbar(NavbarCallback callback_,double width_,double height_,String firstButtonText,String secondButtonText) {

        //setWidth(width_);
        callback = callback_;

           setPrefSize(width_, height_);
        setMaxSize(width_, height_);
        setMinSize(width_, height_);
        
        double firstButtonWidth = width_ / 2;
        double firstButtonHeight = height_;

        backgroundEcossistema = new Polygon();
        //(firstButtonWidth / 5) -> width do triangulo
        backgroundEcossistema.getPoints().addAll(new Double[]{
                0.0, 0.0,
                firstButtonWidth - (firstButtonWidth / 5), 0.0,
                firstButtonWidth + firstButtonWidth / 5, firstButtonHeight,
                0.0, firstButtonHeight});
        backgroundEcossistema.getStyleClass().add("btn-primary");

        Label lbEcossistema = new Label(firstButtonText);
        lbEcossistema.setAlignment(Pos.CENTER);
        lbEcossistema.setPrefWidth(width_ / 2);
        lbEcossistema.setPrefHeight(height_);
        lbEcossistema.setLayoutX(0);
        lbEcossistema.setDisable(true);
        lbEcossistema.getStyleClass().add("disabled-label");

        Label lbInspecionar = new Label(secondButtonText);
        lbInspecionar.setAlignment(Pos.CENTER);
        lbInspecionar.setPrefWidth(width_ / 2);
        lbInspecionar.setPrefHeight(height_);
        lbInspecionar.setLayoutX(firstButtonWidth);
        lbInspecionar.setDisable(true);
        lbInspecionar.getStyleClass().add("disabled-label");

        backgroundInspect = new Rectangle();
        backgroundInspect.setWidth(width_);
        backgroundInspect.setHeight(height_);
        backgroundInspect.setLayoutX(0);
        backgroundInspect.setLayoutY(0);
        backgroundInspect.getStyleClass().add("btn-secondary");


        backgroundInspect.setOnMouseClicked(event -> {
            backgroundInspect.getStyleClass().clear();
            backgroundInspect.getStyleClass().add("btn-primary");
            backgroundEcossistema.getStyleClass().clear();
            backgroundEcossistema.getStyleClass().add("btn-secondary");
            callback.onSecondButtonClicked();
        });



         backgroundEcossistema.setOnMouseClicked(event -> {
            backgroundEcossistema.getStyleClass().clear();
            backgroundEcossistema.getStyleClass().add("btn-primary");
            backgroundInspect.getStyleClass().clear();
            backgroundInspect.getStyleClass().add("btn-secondary");
            callback.onFirstButtonClicked();
        });

        this.getChildren().addAll(backgroundInspect, backgroundEcossistema, lbEcossistema, lbInspecionar);
    }



    public interface NavbarCallback {
        void onFirstButtonClicked();
        void onSecondButtonClicked();
    }

    public void simulateFirstButtonClick() {
        backgroundEcossistema.fireEvent(new javafx.scene.input.MouseEvent(javafx.scene.input.MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, javafx.scene.input.MouseButton.PRIMARY, 1, false, false, false, false, false, false, false, false, false, false, null));
    }

    // Método para simular um clique do mouse no backgroundInspect
    public void simulateSecondButtonClick() {
        backgroundInspect.fireEvent(new javafx.scene.input.MouseEvent(javafx.scene.input.MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, javafx.scene.input.MouseButton.PRIMARY, 1, false, false, false, false, false, false, false, false, false, false, null));
    }
}
