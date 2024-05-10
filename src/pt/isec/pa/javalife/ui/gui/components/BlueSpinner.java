package pt.isec.pa.javalife.ui.gui.components;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class BlueSpinner extends VBox {
    private int numero = 0;
    private int max = 0;
    private int incNumber = 1;//O quanto incrementa por clickada xd
    private TextField counter = new TextField();

    public BlueSpinner(String title,int numero_,int max_,int incNumber_) {
        setSpacing(6);

    	numero = numero_;
        max = max_;
    	incNumber = incNumber_;
        counter.getStyleClass().add("spinner-textfield");
        counter.setMaxWidth(77);
        counter.setMinHeight(36);
        counter.setAlignment(Pos.CENTER);
        counter.setText(Integer.toString(numero));

        SVGPath plus = new SVGPath();
        plus.setContent("M19.8804 8.87591H13.2071V2.20265C13.2071 1.38379 12.543 0.719707 11.7241 0.719707H10.2412C9.42234 0.719707 8.75826 1.38379 8.75826 2.20265V8.87591H2.085C1.26613 8.87591 0.602051 9.53999 0.602051 10.3589V11.8418C0.602051 12.6607 1.26613 13.3247 2.085 13.3247H8.75826V19.998C8.75826 20.8169 9.42234 21.481 10.2412 21.481H11.7241C12.543 21.481 13.2071 20.8169 13.2071 19.998V13.3247H19.8804C20.6992 13.3247 21.3633 12.6607 21.3633 11.8418V10.3589C21.3633 9.53999 20.6992 8.87591 19.8804 8.87591Z");
        plus.setFill(Color.web("#5A508C"));
        
        SVGPath minus = new SVGPath();
        minus.setContent("M19.9911 0.505173H2.19578C1.37691 0.505173 0.71283 1.27993 0.71283 2.23528V3.96538C0.71283 4.92072 1.37691 5.69548 2.19578 5.69548H19.9911C20.81 5.69548 21.4741 4.92072 21.4741 3.96538V2.23528C21.4741 1.27993 20.81 0.505173 19.9911 0.505173Z");
        minus.setFill(Color.web("#5A508C"));

        counter.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                counter.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });


        counter.setOnAction(e -> {
            try {
                numero = Integer.parseInt(counter.getText());
            } catch (NumberFormatException ex) {
                // Handle a conversão falhada
                counter.setText(Integer.toString(numero)); // Restaura o valor anterior
            }
        });

        Button btnIncrease = new Button();
        btnIncrease.getStylesheets().clear();
        btnIncrease.getStyleClass().addAll("spinner-btn", "spinner-btn-left");
        btnIncrease.setMinWidth(35);
        btnIncrease.setMaxWidth(35);
        btnIncrease.setMinHeight(35);
        btnIncrease.setMaxHeight(35);
        btnIncrease.setOnAction(e -> increaseNumero());
        btnIncrease.setGraphic(plus);
        btnIncrease.setFocusTraversable(false);

        Button btnDecrease = new Button();
        btnDecrease.getStyleClass().addAll("spinner-btn", "spinner-btn-right");
        btnDecrease.setMinWidth(35);
        btnDecrease.setMaxWidth(35);
        btnDecrease.setMinHeight(35);
        btnDecrease.setMaxHeight(35);
        btnDecrease.setOnAction(e -> decreaseNumero());
        btnDecrease.setGraphic(minus);
        btnDecrease.setFocusTraversable(false);
        
        HBox spinner = new HBox();

        spinner.getChildren().addAll(btnIncrease, counter, btnDecrease);
        Label lbTitle = new Label(title); 
        lbTitle.getStyleClass().add("spinner-title");
        this.getChildren().addAll(lbTitle,spinner);
    }

    public int getNumero()
    {
        return numero;
    }

   public void increaseNumero() {
        try {numero = Integer.parseInt(counter.getText());} catch (NumberFormatException e) {}
        numero += incNumber;
        if (numero > max) {
            numero = max;
        }
        counter.setText(Integer.toString(numero));
    }

    public void decreaseNumero() {
        try {numero = Integer.parseInt(counter.getText());} catch (NumberFormatException e) {}

        if (numero > 0) {
            numero-=incNumber;
            counter.setText(Integer.toString(numero));
        }
    }
}
