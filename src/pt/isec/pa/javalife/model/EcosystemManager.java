package pt.isec.pa.javalife.model;
import java.util.concurrent.ConcurrentMap;

import pt.isec.pa.javalife.model.data.elements.IElement;
import pt.isec.pa.javalife.model.gameengine.GameEngine;
import pt.isec.pa.javalife.model.gameengine.GameEngineState;
import pt.isec.pa.javalife.model.data.elements.Element;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import pt.isec.pa.javalife.model.gameengine.IGameEngine;
//Classe Facade 
public class EcosystemManager
{
	private final GameEngine gameEngine;
    private final Ecosystem ecosystem;


    public EcosystemManager() {
        gameEngine = new GameEngine();
        ecosystem = new Ecosystem();
        gameEngine.registerClient(ecosystem);
    }

	public boolean startGame(long interval) {
		return gameEngine.start(interval);
	}



    public void stopGame() {
        gameEngine.stop();
    }

    public void pauseGame() {
        gameEngine.pause();
    }

    public void resumeGame() {
        gameEngine.resume();
    }

	public void setGameInterval(long newInterval) {
        gameEngine.setInterval(newInterval);
	}

    public long getGameInterval() {
        return gameEngine.getInterval();
    }

	public GameEngineState getCurrentState() {
		return gameEngine.getCurrentState();
	}

    public void setWidth(int numUnits_){ecosystem.setNumUnitsX(numUnits_);}
    public void setHeight(int numUnits_){ecosystem.setNumUnitsY(numUnits_);}

    public int getWidth() {
        return ecosystem.getWidth();
    }

    public int getHeight() {
        return ecosystem.getHeight();
    }

    public IElement getElement(int id) {
        return ecosystem.getElement(id);
    }

    //Para testes unitarios
    public void evolve() {
        long time = System.nanoTime();
        ecosystem.evolve(gameEngine, time);
    }

    public void makeWall()
    {
    	ecosystem.makeWallOfChina();
    }

   	public void waitForTheEnd() {
   		gameEngine.waitForTheEnd();
   	}

    public void removeElement(IElement element_) {
    	ecosystem.removeElement(element_);
    }
    public void applyStrengthEvent(IElement element) {
        ecosystem.applyStrenghtEvent(element);
    }

    public void applyHerbicideEvent(IElement element) {
        ecosystem.applyHerbicideEvent(element);
    }

    public void applySunEvent(IElement element) {
        ecosystem.applySunEvent(element);
    }

    public int getTicks(){return ecosystem.getTicks();}
    public void resetTicksCounter(){ecosystem.resetTicksCounter();}


    public ConcurrentMap<Integer, IElement> getElements() {
        return ecosystem.getElements();
    }

    public boolean loadGame(String filepath) {
        return ecosystem.loadGame(filepath);
    }

    public boolean saveGame(String filepath) {
        return ecosystem.saveGame(filepath);
    }

    public void renderUpdated() {
        ecosystem.updateRender();
    }

    public void clearElements() {
        ecosystem.clearElements();
    }

    public IElement addElementToRandomFreePosition(Element type) {
        return ecosystem.addElementToRandomFreePosition(type);
    }

    public void addPropertyChangeListener(String prop,PropertyChangeListener listener) {
        ecosystem.addPropertyChangeListener(prop,listener);
    }

    public void removeObserver(String prop,PropertyChangeListener listener) {
        ecosystem.removeObserver(prop,listener);
    }


}