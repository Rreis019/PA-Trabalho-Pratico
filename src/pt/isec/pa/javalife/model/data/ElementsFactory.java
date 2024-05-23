package pt.isec.pa.javalife.model.data;

import pt.isec.pa.javalife.model.data.elements.Element;
import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.data.elements.Flora;
import pt.isec.pa.javalife.model.data.elements.Inanimate;
import pt.isec.pa.javalife.model.data.elements.BaseElement;

public class ElementsFactory
{

	public static BaseElement CreateElement(Element type,int x,int y,double w,double h)
	{
		switch(type)
		{
			case FAUNA:
				return new Fauna(x,y);
			case FLORA:
				return new Flora(x,y);
			case INANIMATE:
				return new Inanimate(x, y);
		}
		return null;
	}

}