package pt.isec.pa.javalife.model.data.elements;

import java.io.Serializable;

import pt.isec.pa.javalife.model.data.Area;
import pt.isec.pa.javalife.model.data.elements.BaseElement;
import pt.isec.pa.javalife.model.data.elements.Element;

/**
 * Fauna
 */
public final class Fauna extends BaseElement implements IElementWithImage,IElementWithStrength,Serializable
{
	static final long serialVersionUID = 1L;

	public Fauna(int id, Element tipo, Area area) {
        super(id, tipo, area);
    }

    @Override
    public double getStrength(){
    	return 0;
    }
    
    @Override
    public void setStrength(double strength){

    }

    @Override
    public String getImage() {
        return null;
    }

    @Override
    public void setImage(String image) {

    }
}