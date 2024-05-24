package pt.isec.pa.javalife.model.data.elements;

import java.io.Serializable;
import java.lang.ModuleLayer.Controller;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import pt.isec.pa.javalife.model.data.Area;
import pt.isec.pa.javalife.model.data.elements.BaseElement;
import pt.isec.pa.javalife.model.data.elements.Element;
import pt.isec.pa.javalife.model.fsm.Direction;
import pt.isec.pa.javalife.ui.gui.FaunaImagesManager;

/**
 * Fauna
 */
public final class Fauna extends BaseElement implements IElementWithStrength,IElementWithImage,Serializable
{
	static final long serialVersionUID = 1L;
    private static Image image = null;
    private static String specie = "";
    private Direction direction = Direction.RIGHT;
    private double strength = 50;
    private int velocity = 32;
    private static final int size = 32;

    public static double descreaseEnergy = 0.5;

	public Fauna(int positionX,int positionY) {
        super(Element.FAUNA, positionX,positionY,size,size);
        setImage(FaunaImagesManager.getImage(specie));
    }   

    public int getVelocity(){return velocity;}

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


   
    
    public void reverseDirection() {
        switch (direction) {
            case LEFT:
                direction = Direction.RIGHT;
                break;
            case RIGHT:
                direction = Direction.LEFT;
                break;
            case UP:
                direction = Direction.DOWN;
                break;
            case DOWN:
                direction = Direction.UP;
                break;
        }
    }

    public void decreaseEnergy(){strength-=descreaseEnergy;}

    public static void setSpecie(String s){specie = s;}
    public String getSpecie(){return specie;}

    public Direction getDirection(){return direction;}
    public void setDirection(Direction direction_){ direction = direction_;}

    @Override
    public Image getImage(){return image;}
    @Override
    public void setImage(Image img_){image = img_;}

    @Override
    public double getStrength(){return strength;}
    @Override
    public void setStrength(double strength_){
        strength = strength_;
        if(strength > 100){strength = 100;}
    }

    @Override
    public int getSize(){return Fauna.size;}

}