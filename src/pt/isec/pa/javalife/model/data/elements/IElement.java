package pt.isec.pa.javalife.model.data.elements;
import java.io.Serializable;

import pt.isec.pa.javalife.model.data.Area;

public sealed interface IElement
    extends Serializable
    permits BaseElement {
    int getId(); // returns the identifier
    int getSize();
    Area getArea(); // returns the occupied area+
    Element getType();
    String getTypeString();

    void setPositionX(double position);
    void setPositionY(double position);
    double getPositionY();
    double getPositionX();
    void setPosition(double positionX,double positionY);
}

