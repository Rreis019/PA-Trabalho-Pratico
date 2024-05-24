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
import java.util.Random;
import java.util.Set;

import javax.swing.text.StyledEditorKit.BoldAction;

import java.util.Iterator;


public class Ecosystem implements Serializable, IGameEngineEvolve, IEcosystem {
    private static final long serialVersionUID = 1L;
    private Set<IElement> elements;
    private Map<Fauna, FaunaStateContext> faunaStates;
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
        faunaStates = new HashMap<>();
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
        for (IElement e : getElements()) {
            if(e.getType() == Element.FAUNA && e.getId() != ignoreID){
                return (Fauna)e;
            }
        }
        return null;
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

    public void removeElement(IElement element_) {
        elements.remove(element_);
    }

    /*
    public void addFauna()
    {
        Random random = new Random();
        Fauna fauna = new Fauna(random.nextInt(getWidth()),random.nextInt(getHeight()));
        elements.add(fauna);
        faunaStates.put(fauna, new FaunaStateContext(this, fauna));
    }
    */

    public void addElement(Element type) {
        Random random = new Random();
        IElement ent = ElementsFactory.CreateElement(type, 0, 0);

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

        if (ent.getType() == Element.FAUNA) {
            faunaStates.put((Fauna) ent, new FaunaStateContext(this, (Fauna) ent));
        }

        elements.add(ent);
    }

    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        for (FaunaStateContext fsm: faunaStates.values()) {fsm.execute();}
        handleColisions();
        //Só para testar o gameEngine

        pcs.firePropertyChange(PROP_GAME_RENDER,null,null);

        Iterator<IElement> iterator = elements.iterator();
        while (iterator.hasNext()) {
            IElement element_ = iterator.next();
            if (element_.getType() == Element.FAUNA) {
                if(((Fauna) element_).getStrength() <= 0)
                {
                    iterator.remove(); // Remover o elemento de maneira segura
                    faunaStates.remove((Fauna) element_);
                }
            }
            else if (element_.getType() == Element.FLORA) {
                if(((Flora)element_).getStrength() <= 0)
                {
                    iterator.remove(); // Remover o elemento de maneira segura
                    faunaStates.remove((Flora)element_);
                }
            }
        }


       // System.out.printf("[%d] %d\n",currentTime,++count);
        //if (count >= 20) gameEngine.stop();

        //Depois implementar um "estado" para controlar os eventos da simulação, se está parada ou não ( tipo uma função boolean)
        //Isso vai permitir parar e continuar a simulação (através do gameEngine)

    }

    public FaunaState getState(Fauna fauna) {
        FaunaStateContext context = faunaStates.get(fauna);
        if (context != null) {
            return context.getState();
        }
        return null;  // Ou um estado padrão, se preferir
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
