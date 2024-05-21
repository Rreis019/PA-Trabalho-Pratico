package pt.isec.pa.javalife.model;

import  pt.isec.pa.javalife.model.gameengine.IGameEngineEvolve;
import pt.isec.pa.javalife.ui.gui.FaunaImagesManager;
import  pt.isec.pa.javalife.model.gameengine.IGameEngine;
import  pt.isec.pa.javalife.model.data.elements.IElement;
import pt.isec.pa.javalife.model.fsm.FaunaState;
import pt.isec.pa.javalife.model.fsm.FaunaStateContext;
import pt.isec.pa.javalife.model.fsm.IFaunaState;
import pt.isec.pa.javalife.model.data.Area;
import pt.isec.pa.javalife.model.data.elements.BaseElement;
import  pt.isec.pa.javalife.model.data.elements.Fauna;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;


public class Ecosystem implements Serializable, IGameEngineEvolve, IEcosystem {
    private static final long serialVersionUID = 1L;
    private transient Set<BaseElement> elements;
    private Map<Fauna, FaunaStateContext> faunaStates;
    private final PropertyChangeSupport pcs; // Para o observable
    private int width = 0;
    private int height = 0;



    public Ecosystem() { //Facade
        pcs = new PropertyChangeSupport(this);
        elements = new HashSet<>();
        faunaStates = new HashMap<>();
    }


    public void setWidth(int w_)
    {
        width = w_;
    }

    public Set<BaseElement> getElements()
    {
        return elements;
    }

    public void setHeight(int h_)
    {
        height = h_;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }




    public void addFauna()
    {
        Random random = new Random();
        Fauna fauna = new Fauna(random.nextInt(getWidth()),random.nextInt(getHeight()),32,32);
        elements.add(fauna);
        faunaStates.put(fauna, new FaunaStateContext(this, fauna));
    }

    //área para o gameEngine
    int count = 0;
    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        for (FaunaStateContext fsm: faunaStates.values()) {
            fsm.execute();
        }

        //Só para testar o gameEngine

       // System.out.printf("[%d] %d\n",currentTime,++count);
        //if (count >= 20) gameEngine.stop();

        //Depois implementar um "estado" para controlar os eventos da simulação, se está parada ou não ( tipo uma função boolean)
        //Isso vai permitir parar e continuar a simulação (através do gameEngine)

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
