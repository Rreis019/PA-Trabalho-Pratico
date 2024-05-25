package pt.isec.pa.javalife.model.data.elements;


import java.io.Serializable;

import javafx.scene.image.Image;
import pt.isec.pa.javalife.model.Ecosystem;
import pt.isec.pa.javalife.model.data.Area;
import pt.isec.pa.javalife.model.data.elements.BaseElement;
import pt.isec.pa.javalife.model.data.elements.Element;

/**
 * Flora
 */
public final class Flora extends BaseElement  implements IElementWithImage,IElementWithStrength,Serializable  
{
	static final long serialVersionUID = 1L;
    private static final int size = 13;
    double strenght = 50;

	 public Flora(double positionX,double positionY) {
        super(Element.FLORA,positionX,positionY,size,size);
    }


    @Override
    public String getImage() {
        // TODO : Retorna o caminho da imagem
        return null;
    }

    public void evolve(Ecosystem eco,long currentTime)
    {
        this.setStrength(getStrength() + 0.5);

        // TODO : Ainda Falta behaviour da flora 
    }

    @Override
    public void setImage(String image) {

    }

    @Override
    public double getStrength(){
    	return strenght;
    }
    
    @Override
    public void setStrength(double strength_){
        strenght = strength_;
        if(strenght > 100){strenght = 100;}
        if(strenght < 0){strenght = 0;}
    }

    @Override
    public int getSize(){return Flora.size;}

}