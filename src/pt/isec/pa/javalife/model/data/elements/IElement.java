package pt.isec.pa.javalife.model.data.elements;

import pt.isec.pa.javalife.model.data.Area;

import java.io.Serializable;

public sealed interface IElement
    extends Serializable,Cloneable 
    permits BaseElement {
    int getId(); // returns the identifier
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

