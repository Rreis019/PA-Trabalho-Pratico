package pt.isec.pa.javalife.model.data.elements;
import java.io.Serializable;

import pt.isec.pa.javalife.model.data.Area;

public sealed interface IElement
    extends Serializable,Cloneable 
    permits BaseElement {
    int getId(); // returns the identifier
    int getSize();
    Area getArea(); // returns the occupied area+
    Element getType();
    String getTypeString();

    void setPositionX(double position);
    void setPositionY(double position);
    void setPosition(double positionX,double positionY);

    IElement clone();

    boolean isReadOnly();
    void setReadonly(boolean inv);
}

