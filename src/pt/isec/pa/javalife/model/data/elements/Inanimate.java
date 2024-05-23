package pt.isec.pa.javalife.model.data.elements;
import java.io.Serializable;

import pt.isec.pa.javalife.model.data.Area;
import pt.isec.pa.javalife.model.data.elements.BaseElement;
import pt.isec.pa.javalife.model.data.elements.Element;

/**
 * Inanimate
 */
public final class Inanimate extends BaseElement implements Serializable
{
	static final long serialVersionUID = 1L;
	private static final int size = 16;

 	public Inanimate(int positionX,int positionY) {
        super(Element.INANIMATE,positionX,positionY,size,size);
    }
	
}