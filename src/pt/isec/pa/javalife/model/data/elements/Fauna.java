package pt.isec.pa.javalife.model.data.elements;

import java.io.Serializable;
import java.lang.ModuleLayer.Controller;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import pt.isec.pa.javalife.model.data.Area;
import pt.isec.pa.javalife.model.data.elements.BaseElement;
import pt.isec.pa.javalife.model.data.elements.Element;
import pt.isec.pa.javalife.model.fsm.FaunaDirection;
import pt.isec.pa.javalife.ui.gui.FaunaImagesManager;

/**
 * Fauna
 */
public final class Fauna extends BaseElement implements IElementWithStrength,IElementWithImage,Serializable
{
	static final long serialVersionUID = 1L;
    private static Image image = null;
    private static String specie = "";
    private FaunaDirection direction = FaunaDirection.RIGHT;
    private double strength = 50;
    private int velocity = 32;

    public static double descreaseEnergy = 0.5;

	public Fauna(int positionX,int positionY,double width,double height) {
        super(Element.FAUNA, positionX,positionY,width,height);
        setImage(FaunaImagesManager.getImage(specie));
    }   

    public void moveForward() {
        switch (direction) {
            case LEFT:
                move(-velocity,0);
                break;
            case RIGHT:
                move(velocity,0);
                break;
            case DOWN:
                move(0,velocity);
                break;
            case UP:
                move(0,-velocity);
                break;
        }
        decreaseEnergy();
    }

     

    public void decreaseEnergy(){strength-=descreaseEnergy;}

    public static void setSpecie(String s){specie = s;}
    public String getSpecie(){return specie;}

    public FaunaDirection getDirection(){return direction;}
    public void setDirection(FaunaDirection direction_){ direction = direction_;}

    @Override
    public Image getImage(){return image;}
    @Override
    public void setImage(Image img_){image = img_;}

    @Override
    public double getStrength(){return strength;}
    @Override
    public void setStrength(double strength_){strength = strength_;}
}