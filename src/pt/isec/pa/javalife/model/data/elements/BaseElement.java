package pt.isec.pa.javalife.model.data.elements;

import pt.isec.pa.javalife.model.data.elements.Element;
import pt.isec.pa.javalife.model.data.elements.IElement;
import pt.isec.pa.javalife.model.data.elements.Inanimate;
import pt.isec.pa.javalife.model.data.elements.Flora;
import pt.isec.pa.javalife.model.data.elements.Fauna;


import pt.isec.pa.javalife.model.data.Area;

public abstract sealed class BaseElement
    implements IElement
    permits Inanimate, Flora, Fauna {

    static final long serialVersionUID = 1L;

    protected final int id;
    protected final Element type;
    protected transient Area area;

    public BaseElement(int id, Element type, Area area) {
        this.id = id;
        this.type = type;
        this.area = area;
    }

    @Override
    public int getId() {
        return id;
    }


    public Element getType() {
        return type;
    }

    @Override
    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}