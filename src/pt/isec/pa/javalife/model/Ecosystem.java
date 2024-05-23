package pt.isec.pa.javalife.model;

import  pt.isec.pa.javalife.model.gameengine.IGameEngineEvolve;
import pt.isec.pa.javalife.ui.gui.FaunaImagesManager;
import  pt.isec.pa.javalife.model.gameengine.IGameEngine;
import  pt.isec.pa.javalife.model.data.elements.IElement;
import pt.isec.pa.javalife.model.fsm.FaunaDirection;
import pt.isec.pa.javalife.model.fsm.FaunaState;
import pt.isec.pa.javalife.model.fsm.FaunaStateContext;
import pt.isec.pa.javalife.model.fsm.IFaunaState;
import pt.isec.pa.javalife.model.data.Area;
import pt.isec.pa.javalife.model.data.ElementsFactory;
import pt.isec.pa.javalife.model.data.elements.BaseElement;
import pt.isec.pa.javalife.model.data.elements.Element;
import  pt.isec.pa.javalife.model.data.elements.Fauna;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Iterator;


public class Ecosystem implements Serializable, IGameEngineEvolve, IEcosystem {
    private static final long serialVersionUID = 1L;
    private transient Set<BaseElement> elements;
    private Map<Fauna, FaunaStateContext> faunaStates;
    private final PropertyChangeSupport pcs; // Para o observable

    private int unitScale = 2;
    private int numUnitsX = 0;
    private int numUnitsY = 0;

    public Ecosystem() { //Facade
        pcs = new PropertyChangeSupport(this);
        elements = new HashSet<>();
        faunaStates = new HashMap<>();
    }

    public int getWidth(){return unitScale*numUnitsX;}
    public int getHeight(){return unitScale*numUnitsY;}
    public void setNumUnitsX(int numUnits_){numUnitsX = numUnits_;}
    public void setNumUnitsY(int numUnits_){numUnitsY = numUnits_;}

    public Set<BaseElement> getElements()
    {
        return elements;
    }

    public BaseElement getElement(int id)
    {
        for(BaseElement ent : elements){
            if(ent.getId() == id){ return ent;}
        }

        return null;
    }

    public void removeElement(BaseElement element_) {
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

    public void addElement(Element type)
    {
        Random random = new Random();
        BaseElement ent = ElementsFactory.CreateElement(type, random.nextInt(getWidth()),random.nextInt(getHeight()));

        if(ent.getType() == Element.FAUNA){
            faunaStates.put((Fauna)ent, new FaunaStateContext(this, (Fauna)ent));
        }
        elements.add(ent);
    }

    //área para o gameEngine
    int count = 0;
    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        for (FaunaStateContext fsm: faunaStates.values()) {fsm.execute();}
        handleColisions();
        //Só para testar o gameEngine

        Iterator<BaseElement> iterator = elements.iterator();
        while (iterator.hasNext()) {
            BaseElement element_ = iterator.next();
            if (element_.getType() == Element.FAUNA && ((Fauna) element_).getStrength() <= 0) {
                iterator.remove(); // Remover o elemento de maneira segura
                faunaStates.remove((Fauna) element_);
            }
        }

       // System.out.printf("[%d] %d\n",currentTime,++count);
        //if (count >= 20) gameEngine.stop();

        //Depois implementar um "estado" para controlar os eventos da simulação, se está parada ou não ( tipo uma função boolean)
        //Isso vai permitir parar e continuar a simulação (através do gameEngine)

    }

    private void handleIfOutBounds(Fauna element_){
        Area area = element_.getArea();

        if(area.left() < 0){
            element_.setPositionX(0);
            element_.setDirection(FaunaDirection.RIGHT);
        }
        else if(area.right() > getWidth())
        {
            element_.setPositionX(getWidth() - (area.right() - area.left())   );
            element_.setDirection(FaunaDirection.LEFT);
        }

        if(area.top() < 0)
        {
            element_.setPositionY(0);
            element_.setDirection(FaunaDirection.DOWN);
        }
        else if(area.bottom() > getHeight()) {
            element_.setPositionY(getHeight() - (area.bottom() - area.top()));
            element_.setDirection(FaunaDirection.UP);
        }
    } 

    private void handleColisions()
    {
        for (BaseElement element_ : elements) {
            if(element_.getType() == Element.FAUNA){handleIfOutBounds((Fauna)element_);}


        }


    }

    //área para o Observable
    @Override
    public void addObserver(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    @Override
    public void removeObserver(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);

    }
}
