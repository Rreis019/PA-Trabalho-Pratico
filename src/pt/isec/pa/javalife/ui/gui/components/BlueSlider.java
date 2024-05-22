package pt.isec.pa.javalife.ui.gui.components;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class BlueSlider extends VBox {

    private Slider slider;
    private Label valueLabel;

    public BlueSlider(String title, double width, double min, double value,double max) {
        this.slider = new Slider(min, max, value);
        this.slider.setPrefWidth(width);
        this.slider.setMaxWidth(width);

        this.valueLabel = new Label(value + "/" + max);
        this.valueLabel.getStyleClass().add("text-bold");

        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("text-bold");

        BorderPane pane = new BorderPane();
        HBox.setHgrow(pane, Priority.ALWAYS);

        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            valueLabel.setText(newValue.intValue() + "/" + (int)max);
        });

        HBox hbox = new HBox(titleLabel,pane,valueLabel);
        hbox.setSpacing(14);

        getChildren().addAll(hbox, slider);
        setMaxWidth(width);
    }

    public void setValue(double value){slider.setValue(value);}
    public double getValue(){ return slider.getValue();}

    public Slider getSlider() {return slider;}
    public Label getValueLabel() {return valueLabel;}

}
