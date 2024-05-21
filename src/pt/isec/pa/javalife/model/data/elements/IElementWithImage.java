package pt.isec.pa.javalife.model.data.elements;

import javafx.scene.image.Image;
import pt.isec.pa.javalife.model.data.elements.Flora;

public sealed interface IElementWithImage
        permits Fauna,Flora {
        Image getImage(); // image path
        void setImage(Image image);
}