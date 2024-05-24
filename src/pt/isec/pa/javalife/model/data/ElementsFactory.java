package pt.isec.pa.javalife.model.data;

import pt.isec.pa.javalife.model.data.elements.Element;
import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.data.elements.Flora;
import pt.isec.pa.javalife.model.data.elements.IElement;
import pt.isec.pa.javalife.model.data.elements.Inanimate;

public class ElementsFactory
{

	public static IElement CreateElement(Element type,int x,int y)
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