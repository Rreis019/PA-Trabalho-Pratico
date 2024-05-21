package pt.isec.pa.javalife.model.data.elements;

import java.io.Serializable;
import java.lang.ModuleLayer.Controller;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import pt.isec.pa.javalife.model.data.Area;
import pt.isec.pa.javalife.model.data.elements.BaseElement;
import pt.isec.pa.javalife.model.data.elements.Element;
import pt.isec.pa.javalife.ui.gui.FaunaImagesManager;

/**
 * Fauna
 */
public final class Fauna extends BaseElement implements IElementWithStrength,IElementWithImage,Serializable
{
	static final long serialVersionUID = 1L;
    private static Image image = null;
    private static String specie = "";

	public Fauna(int positionX,int positionY,double width,double height) {
        super(Element.FAUNA, positionX,positionY,width,height);
        setImage(FaunaImagesManager.getImage(specie));
    }   

    public static void setSpecie(String s)
    {
        specie = s;
    }

    public String getSpecie(){
        return specie;
    }

    @Override
    public Image getImage()
    {
        return image;
    }

    @Override
    public void setImage(Image img_)
    {
        image = img_;
    }

    @Override
    public double getStrength(){
    	return 0;
    }
    
    @Override
    public void setStrength(double strength){

    }


}