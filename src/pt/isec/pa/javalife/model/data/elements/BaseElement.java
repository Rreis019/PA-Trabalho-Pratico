package pt.isec.pa.javalife.model.data.elements;

import pt.isec.pa.javalife.model.data.elements.Element;
import pt.isec.pa.javalife.model.data.elements.IElement;
import pt.isec.pa.javalife.model.data.elements.Inanimate;
import pt.isec.pa.javalife.model.data.elements.Flora;
import pt.isec.pa.javalife.model.data.elements.Fauna;
import javafx.scene.canvas.GraphicsContext;
import pt.isec.pa.javalife.model.data.Area;

public abstract sealed class BaseElement
    implements IElement
    permits Inanimate, Flora, Fauna {
    private static final long serialVersionUID = 1L;
    public static int lastId = 0;

    protected final int id;

    protected Element type;

    protected Area area;

    public BaseElement(Element type, double positionX,double positionY,double width,double height) {
        //this.id = id;
        lastId = lastId + 1;
        this.id = lastId;
        this.type = type;
        //this.area = area;
        setArea(positionX,positionY,width,height);
    }

    public void move(int positionX,int positionY){
        this.setPosition(positionX +  area.left(), positionY + area.top());
    }

    @Override
    public int getId() {return id;}

    @Override
    public Element getType() {return type;}

    @Override
    public int getSize(){return 0;}
    
    @Override
    public String getTypeString()
    {
        switch(type){
        case FAUNA:
            return "Fauna";
        case FLORA:
            return "Flora";
        case INANIMATE:
            return "Inanimado";
        }

        return "None";
    }

    
    public void setPositionX(double position){setPosition(position, area.top());}
    public void setPositionY(double position){setPosition(area.left(), position);}

    public double getPositionX() {
        return area.left();
    }

    public double getPositionY() {
        return area.top();
    }
    public void setPosition(double positionX,double positionY){
        double width = area.right() - area.left();
        double height = area.bottom() - area.top();
        setArea(positionX, positionY, width, height);
    }


    public void setArea(double positionX,double positionY,double width,double height){
        this.area = new Area(positionY, positionX, positionY + height, positionX + width); 
    }
    

    @Override
    public Area getArea() {return area;}
    public void setArea(Area area) {this.area = area;}

    



}