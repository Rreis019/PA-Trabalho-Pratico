package pt.isec.pa.javalife.ui.gui.components;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class BlueSlider extends VBox {

    private Slider slider;
    private Label valueLabel;
    private boolean isFloat;

    public BlueSlider(String title, double width, double min, double value, double max) {
        this(title, width, min, value, max, false);
    }

    public BlueSlider(String title, double width, double min, double value, double max, boolean isFloat) {
        this.slider = new Slider(min, max, value);
        this.slider.setPrefWidth(width);
        this.slider.setMaxWidth(width);
        this.isFloat = isFloat;

        this.valueLabel = new Label(formatValue(value, max));
        this.valueLabel.getStyleClass().add("text-bold");

        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("text-bold");

        BorderPane pane = new BorderPane();
        HBox.setHgrow(pane, Priority.ALWAYS);

        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            valueLabel.setText(formatValue(newValue.doubleValue(), max));
        });

        HBox hbox = new HBox(titleLabel, pane, valueLabel);

        getChildren().addAll(hbox, slider);
        setMaxWidth(width);
    }

    private String formatValue(double value, double max) {
        if (isFloat) {
            return String.format("%.2f / %.2f", value, max);
        } else {
            return String.format("%d / %d", (int) value, (int) max);
        }
    }

    public void setValue(double value) {
        slider.setValue(value);
    }

    public double getValue() {
        return slider.getValue();
    }

    public Slider getSlider() {
        return slider;
    }

    public Label getValueLabel() {
        return valueLabel;
    }

    public void setFloat(boolean isFloat) {
        this.isFloat = isFloat;
        valueLabel.setText(formatValue(slider.getValue(), slider.getMax()));
    }
}
