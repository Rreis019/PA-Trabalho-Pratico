package pt.isec.pa.javalife.model.data.elements;

import pt.isec.pa.javalife.model.data.elements.Flora;

public sealed interface IElementWithImage
        permits Flora {
    String getImage(); // image path
    void setImage(String image);
}