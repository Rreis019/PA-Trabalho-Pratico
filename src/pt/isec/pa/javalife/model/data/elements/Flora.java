package pt.isec.pa.javalife.model.data.elements;


import java.io.Serializable;

import javafx.scene.image.Image;
import pt.isec.pa.javalife.model.data.Area;
import pt.isec.pa.javalife.model.data.elements.BaseElement;
import pt.isec.pa.javalife.model.data.elements.Element;

/**
 * Flora
 */
public final class Flora extends BaseElement  implements IElementWithImage,IElementWithStrength,Serializable  
{
	static final long serialVersionUID = 1L;

	 public Flora(int positionX,int positionY,double width,double height) {
        super(Element.FLORA,positionX,positionY,width,height);
    }


    @Override
    public Image getImage() {
        // TODO : Retorna o caminho da imagem
        return null;
    }

    @Override
    public void setImage(Image image) {

    }

    @Override
    public double getStrength(){
    	return 0;
    }
    
    @Override
    public void setStrength(double strength){

    }
}