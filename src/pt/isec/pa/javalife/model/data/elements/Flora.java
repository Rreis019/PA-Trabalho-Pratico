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
    private static final int size = 10;

	 public Flora(int positionX,int positionY) {
        super(Element.FLORA,positionX,positionY,size,size);
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