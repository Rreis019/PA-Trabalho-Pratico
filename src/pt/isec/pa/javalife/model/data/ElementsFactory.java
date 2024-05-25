package pt.isec.pa.javalife.model.data;

import pt.isec.pa.javalife.model.Ecosystem;
import pt.isec.pa.javalife.model.data.elements.Element;
import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.data.elements.Flora;
import pt.isec.pa.javalife.model.data.elements.IElement;
import pt.isec.pa.javalife.model.data.elements.Inanimate;
import pt.isec.pa.javalife.model.fsm.FaunaStateContext;

public class ElementsFactory
{

	public static IElement CreateElement(Ecosystem eco,Element type,double positionX,double positionY)
	{
		switch(type)
		{
			case FAUNA:
				return new Fauna(eco,positionX,positionY);
			case FLORA:
				return new Flora(positionX,positionY);
			case INANIMATE:
				return new Inanimate(positionX, positionY);
		}
		return null;
	}

}