package pt.isec.pa.javalife.model.data;

import  pt.isec.pa.javalife.model.gameengine.IGameEngineEvolve;
import  pt.isec.pa.javalife.model.gameengine.IGameEngine;
import  pt.isec.pa.javalife.model.data.elements.IElement;
import java.io.Serializable;
import java.util.Set;


public class Ecosystem implements Serializable, IGameEngineEvolve {
    private static final long serialVersionUID = 1L;
    private transient Set<IElement> elements;

    public void evolve(IGameEngine gameEngine, long currentTime){

    }
}
