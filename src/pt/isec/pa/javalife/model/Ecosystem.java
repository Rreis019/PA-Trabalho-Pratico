package pt.isec.pa.javalife.model;

import  pt.isec.pa.javalife.model.gameengine.IGameEngineEvolve;
import  pt.isec.pa.javalife.model.gameengine.IGameEngine;
import  pt.isec.pa.javalife.model.data.elements.IElement;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Set;


public class Ecosystem implements Serializable, IGameEngineEvolve, IEcosystem {
    private static final long serialVersionUID = 1L;
    private transient Set<IElement> elements;
    private final PropertyChangeSupport pcs; // Para o observable
    private int width = 0;
    private int height = 0;

    public void setWidth(int w_)
    {
        width = w_;
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

    public Ecosystem() { //Facade
        pcs = new PropertyChangeSupport(this);
    }


    //área para o gameEngine
    int count = 0;
    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {

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
