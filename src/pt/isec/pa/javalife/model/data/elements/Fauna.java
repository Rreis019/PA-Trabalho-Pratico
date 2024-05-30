package pt.isec.pa.javalife.model.data.elements;

import java.io.Serializable;
import java.lang.ModuleLayer.Controller;
import java.util.Set;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import pt.isec.pa.javalife.model.Ecosystem;
import pt.isec.pa.javalife.model.data.Area;
import pt.isec.pa.javalife.model.data.elements.BaseElement;
import pt.isec.pa.javalife.model.data.elements.Element;
import pt.isec.pa.javalife.model.fsm.Direction;
import pt.isec.pa.javalife.model.fsm.FaunaState;
import pt.isec.pa.javalife.model.fsm.FaunaStateContext;
import pt.isec.pa.javalife.ui.gui.FaunaImagesManager;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Fauna
 */
public non-sealed class Fauna extends BaseElement implements IElementWithStrength,IElementWithImage
{
    private static String image = "lobo";
    private Direction direction = Direction.RIGHT;
    private double strength = 50;
    private int velocity = 32;
    private static final int size = 32;

    private FaunaStateContext ctx;
    private Ecosystem eco;

	public Fauna(Ecosystem ecosystem,double positionX,double positionY) {
        super(Element.FAUNA, positionX,positionY,size,size);
        ctx = new FaunaStateContext(ecosystem, this);
        this.eco = ecosystem;
    }   

    @Override
public Fauna clone() {
    Fauna cloned = (Fauna) super.clone();
    cloned.ctx = new FaunaStateContext(this.eco, cloned);
    return cloned;
}


    public FaunaState getState(){return ctx.getState();}
    public FaunaStateContext getFSM(){return ctx;}
    public int getVelocity()
    {
        if(eco.isSunEventActive()){return velocity/2;}
        return velocity;

    }

    public void moveForward() {
        switch (direction) {
            case LEFT:
                move(-getVelocity(),0);
                break;
            case RIGHT:
                move(getVelocity(),0);
                break;
            case DOWN:
                move(0,getVelocity());
                break;
            case UP:
                move(0,-getVelocity());
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



    private Direction getAlternativeDirection(Direction currentDirection) {
        switch (currentDirection) {
            case LEFT:
            case RIGHT:
                return (Math.random() > 0.5) ? Direction.UP : Direction.DOWN;
            case UP:
            case DOWN:
                return (Math.random() > 0.5) ? Direction.LEFT : Direction.RIGHT;
            default:
                return Direction.RIGHT;
        }
    }


    //true -> ja chegou ao alvo
    //public boolean moveTo(Set<IElement> elements,IElement target) {
     public boolean moveTo(ConcurrentMap<Integer, IElement> elements,IElement target) {
        double deltaX = target.getArea().left() - getArea().left();
        double deltaY = target.getArea().top() - getArea().top();

        Direction dirX = (deltaX > 0) ? Direction.RIGHT : Direction.LEFT;
        Direction dirY = (deltaY > 0) ? Direction.DOWN : Direction.UP;

        if (Math.abs(deltaX) > Math.abs(deltaY)) {setDirection(dirX);
        } else {setDirection(dirY);}


        if (this.haveObstacleAhead(elements)) {  
            Direction dirRandom = getAlternativeDirection(getDirection());
            setDirection(dirRandom);
        }


        if(!getArea().intersects(target.getArea())){
            moveForward();
            return false;
        }

        return true;
    }


    //private boolean haveObstacleAhead(Set<IElement> elements) {
    private boolean haveObstacleAhead(ConcurrentMap<Integer, IElement> elements) {    
        Area futureArea = null;
        switch (this.getDirection()) {
            case LEFT:
                futureArea = new Area(this.getArea().top(), this.getArea().left() - this.getVelocity(), this.getArea().bottom(), this.getArea().right() - this.getVelocity());
                break;
            case RIGHT:
                futureArea = new Area(this.getArea().top(), this.getArea().left() + this.getVelocity(), this.getArea().bottom(), this.getArea().right() + this.getVelocity());
                break;
            case UP:
                futureArea = new Area(this.getArea().top() - this.getVelocity(), this.getArea().left(), this.getArea().bottom() - this.getVelocity(), this.getArea().right());
                break;
            case DOWN:
                futureArea = new Area(this.getArea().top() + this.getVelocity(), this.getArea().left(), this.getArea().bottom() + this.getVelocity(), this.getArea().right());
                break;
        }
        
        for (IElement element : elements.values()) {
            if (element.getType() == Element.INANIMATE && futureArea.intersects(element.getArea())) {
                return true;
            }
        }

        return false;
    }

    public void decreaseEnergy(){strength-=eco.getFaunaMovementEnergy();}

    public Direction getDirection(){return direction;}
    public void setDirection(Direction direction_){ direction = direction_;}

    @Override
    public String getImage(){return image;}
    @Override
    public void setImage(String img_){image = img_;}

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