package pt.isec.pa.javalife.model.data.elements;
import java.io.Serializable;

import pt.isec.pa.javalife.model.data.Area;
import pt.isec.pa.javalife.model.data.elements.BaseElement;
import pt.isec.pa.javalife.model.data.elements.Element;

/**
 * Inanimate
 */
public non-sealed class Inanimate extends BaseElement
{
	public static final int size = 16;

 	public Inanimate(double positionX,double positionY) {
        super(Element.INANIMATE,positionX,positionY,size,size);
    }
	

	
    @Override
    public int getSize(){return Inanimate.size;}
}