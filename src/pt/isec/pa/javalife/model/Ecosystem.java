package pt.isec.pa.javalife.model;

import  pt.isec.pa.javalife.model.gameengine.IGameEngineEvolve;
import pt.isec.pa.javalife.ui.gui.FaunaImagesManager;
import  pt.isec.pa.javalife.model.gameengine.IGameEngine;
import  pt.isec.pa.javalife.model.data.elements.IElement;
import pt.isec.pa.javalife.model.data.elements.Inanimate;
import pt.isec.pa.javalife.model.fsm.Direction;
import pt.isec.pa.javalife.model.fsm.FaunaState;
import pt.isec.pa.javalife.model.fsm.FaunaStateContext;
import pt.isec.pa.javalife.model.fsm.IFaunaState;
import pt.isec.pa.javalife.model.data.Area;
import pt.isec.pa.javalife.model.data.ElementsFactory;
import pt.isec.pa.javalife.model.data.elements.BaseElement;
import pt.isec.pa.javalife.model.data.elements.Element;
import  pt.isec.pa.javalife.model.data.elements.Fauna;
import  pt.isec.pa.javalife.model.data.elements.Flora;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import javax.swing.text.StyledEditorKit.BoldAction;

import java.util.Iterator;
import java.util.LinkedList;


public class Ecosystem implements Serializable, IGameEngineEvolve, IEcosystem {
    private static final long serialVersionUID = 1L;
    private Set<IElement> elements;
    //private Map<Fauna, FaunaStateContext> faunaStates;


    private final PropertyChangeSupport pcs; // Para o observable
    public static final String PROP_GAME_RENDER = "info_property";
    public static final String PROP_INSPECT = "inspect";


    private int unitScale = 2;
    private int numUnitsX = 0;
    private int numUnitsY = 0;

    @SuppressWarnings("this-escape")
    public Ecosystem() { //Facade
        this.pcs = new PropertyChangeSupport(this);
        elements = new HashSet<>();
        //faunaStates = new HashMap<>();
    }


    public int getWidth(){return unitScale*numUnitsX;}
    public int getHeight(){return unitScale*numUnitsY;}
    public void setNumUnitsX(int numUnits_){numUnitsX = numUnits_;}
    public void setNumUnitsY(int numUnits_){numUnitsY = numUnits_;}

    public IElement getClossestElement(Area origin,Element type)
    {
        IElement ret = null;
        double maxDistance = 99999.0f;
        for (IElement ent : getElements() ) {
            if(ent.getType() == type){
                double distance = Area.distance(origin, ent.getArea());
                if(distance < maxDistance){
                    maxDistance = distance;
                    ret = ent;
                }

            }
        }
        return ret;
    }

    public Set<IElement> getElements()
    {
        return elements;
    }

    public Fauna getWeakestFauna(int ignoreID)
    {
        double str = 1000;
        Fauna weakestFauna = null;
        for (IElement e : getElements()) {
            if(e.getType() == Element.FAUNA && e.getId() != ignoreID){
                Fauna f = (Fauna)e;
                if(f.getStrength() < str){
                    str = f.getStrength();
                    weakestFauna = f;
                }


            }
        }
        return weakestFauna;
    }

    public void clearElements()
    {
        elements.clear();
    }
    public Fauna getStrongestFauna(int ignoreID)
    {
        double str = 0;
        Fauna strongestFauna = null;
        for (IElement e : getElements()) {
            if(e.getType() == Element.FAUNA && e.getId() != ignoreID){
                Fauna f = (Fauna)e;
                if(f.getStrength() > str){
                    str = f.getStrength();
                    strongestFauna = f;
                }


            }
        }
        return strongestFauna;
    }

    public IElement getElement(int id)
    {
        pcs.firePropertyChange(PROP_INSPECT,null,null);
        System.out.printf("GetElement\n");
        for(IElement ent : elements){
            if(ent.getId() == id){ return ent;}
        }

        return null;
    }


    public void updateRender()
    {
        pcs.firePropertyChange(PROP_GAME_RENDER,null,null);
    }

    public void removeElement(IElement element_) {
        elements.remove(element_);
    }

    public void addElement(Element type,double positionX,double positionY)
    {
        IElement ent = ElementsFactory.CreateElement(this,type, positionX, positionY);
        elements.add(ent);
    }

    public void addElementToRandomFreePosition(Element type) {
        Random random = new Random();
        IElement ent = ElementsFactory.CreateElement(this,type, 0, 0);

        int maxWidth = getWidth() - ent.getSize();
        int maxHeight = getHeight() - ent.getSize();

        boolean foundEmptyPosition = false;

        while (!foundEmptyPosition) {
            ent.setPosition(random.nextInt(maxWidth), random.nextInt(maxHeight));

            boolean intersects_ = false;

            for (IElement e : elements) {
                if (e.getType() == Element.INANIMATE && ent.getArea().intersects(e.getArea())) {
                    intersects_ = true;
                    break; // Saia do loop assim que encontrar uma interseção
                }
            }

            if (!intersects_) {foundEmptyPosition = true;}
        }

        //if (ent.getType() == Element.FAUNA) {
         //   faunaStates.put((Fauna) ent, new FaunaStateContext(this, (Fauna) ent));
        //}

        elements.add(ent);
    }

    @Override
     public void evolve(IGameEngine gameEngine, long currentTime) {
        // Iterar sobre os estados da fauna usando um iterador
        Iterator<IElement> iterator = elements.iterator();
        while (iterator.hasNext()) {
            IElement element = iterator.next();
            if(element.getType() == Element.FAUNA){((Fauna)element).getFSM().execute();}
        }


        // Iterar sobre os elementos usando um iterador
        Iterator<IElement> elementIterator = elements.iterator();
        while (elementIterator.hasNext()) {
            IElement element = elementIterator.next();
            if (element.getType() == Element.FAUNA) {
                if (((Fauna) element).getStrength() <= 0) {elementIterator.remove(); }
            } else if (element.getType() == Element.FLORA) {
                if (((Flora) element).getStrength() <= 0) {elementIterator.remove(); }
            }
        }

        handleColisions();
        pcs.firePropertyChange(PROP_GAME_RENDER, null, null);
    }

    public void makeWallOfChina()
    {
        
        Inanimate top = new Inanimate(0, 0);
        Inanimate left = new Inanimate(0, 0);
        Inanimate right = new Inanimate(0, 0);
        Inanimate bottom = new Inanimate(0, 0);

        int wallThickness = 4;

        top.setArea(0, 0, this.getWidth(), wallThickness);
        left.setArea(0, 0, wallThickness, this.getWidth());
        right.setArea(this.getWidth() - wallThickness,0, wallThickness, getHeight());
        bottom.setArea(0,this.getHeight() - wallThickness, getWidth(), wallThickness);

        elements.add(top);
        elements.add(left);
        elements.add(right);
        elements.add(bottom);
        
    }

    private void handleIfOutBounds(Fauna element_){
        Area area = element_.getArea();

        if(area.left() < 0){
            element_.setPositionX(0);
            element_.setDirection(Direction.RIGHT);
        }
        else if(area.right() > getWidth())
        {
            element_.setPositionX(getWidth() - (area.right() - area.left())   );
            element_.setDirection(Direction.LEFT);
        }

        if(area.top() < 0)
        {
            element_.setPositionY(0);
            element_.setDirection(Direction.DOWN);
        }
        else if(area.bottom() > getHeight()) {
            element_.setPositionY(getHeight() - (area.bottom() - area.top()));
            element_.setDirection(Direction.UP);
        }
    } 





    private void handleColisions()
    {
        ArrayList<Inanimate> inanimates = new ArrayList<>(); 
        for (IElement element_ : elements) {
            if(element_.getType() == Element.INANIMATE){
                inanimates.add((Inanimate)element_);
            }
        }

        for (IElement element_ : elements) {
            if(element_.getType() == Element.FAUNA){

                Fauna f = (Fauna)element_;
                Direction direction_ = f.getDirection(); 
                //handleIfOutBounds(f);


                for (Inanimate ina : inanimates) {
                    Area sol = f.getArea().solveColision(direction_, ina.getArea());
                    if(sol.left() != -1){
                        f.setArea(sol);
                        f.reverseDirection();
                        break;
                    }
                }
            }


        }


    }



    //área para o Observable
    @Override
    public void addPropertyChangeListener(String prop,PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(prop,listener);
    }

    @Override
    public void removeObserver(String prop,PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);

    }
}
