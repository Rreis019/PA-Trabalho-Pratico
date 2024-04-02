package pt.isec.pa.javalife.model.data.elements;
import java.io.Serializable;

import pt.isec.pa.javalife.model.data.Area;
import pt.isec.pa.javalife.model.data.elements.BaseElement;

public sealed interface IElement
    extends Serializable
    permits BaseElement {
    int getId(); // returns the identifier
    Area getArea(); // returns the occupied area
}

