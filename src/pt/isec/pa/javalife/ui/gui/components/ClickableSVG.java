package pt.isec.pa.javalife.ui.gui.components;
import javafx.scene.shape.SVGPath;

//O metodo original de colisão de SVG nao funciona bem para alguns svg entao 
//modifiquei para ter bounding box de colisão em vez de um path
public class ClickableSVG extends SVGPath {
    public ClickableSVG() {
        super();
    }

    @Override
    public boolean contains(double x, double y) {
        double minX = getBoundsInLocal().getCenterX() - getBoundsInLocal().getWidth() / 2;
        double minY = getBoundsInLocal().getCenterY()  - getBoundsInLocal().getHeight() / 2;
        double maxX = getBoundsInLocal().getCenterX() + getBoundsInLocal().getWidth() / 2;
        double maxY = getBoundsInLocal().getCenterY() + getBoundsInLocal().getHeight() / 2;

        return x >= minX && x <= maxX && y >= minY && y <= maxY;
    }
}
