package pt.isec.pa.javalife.model.data.elements;

import java.io.Serializable;
import java.lang.ModuleLayer.Controller;

import pt.isec.pa.javalife.model.data.Area;
import pt.isec.pa.javalife.model.data.elements.BaseElement;
import pt.isec.pa.javalife.model.data.elements.Element;

/**
 * Fauna
 */
public final class Fauna extends BaseElement implements IElementWithStrength,Serializable
{
	static final long serialVersionUID = 1L;
    Controller controller;
	public Fauna(int positionX,int positionY,double width,double height) {
        super(Element.FAUNA, positionX,positionY,width,height);
    }   

    @Override
    public double getStrength(){
    	return 0;
    }
    
    @Override
    public void setStrength(double strength){

    }

}