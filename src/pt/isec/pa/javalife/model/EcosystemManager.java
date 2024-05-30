package pt.isec.pa.javalife.model;
import java.util.concurrent.ConcurrentMap;

import pt.isec.pa.javalife.model.command.*;
import pt.isec.pa.javalife.model.data.elements.IElement;
import pt.isec.pa.javalife.model.gameengine.GameEngine;
import pt.isec.pa.javalife.model.gameengine.GameEngineState;
import pt.isec.pa.javalife.model.data.elements.BaseElement;
import pt.isec.pa.javalife.model.data.elements.Element;

import java.beans.PropertyChangeListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

//Classe Facade
public class EcosystemManager
{
	private final GameEngine gameEngine;
    private Ecosystem ecosystem;
    private CommandManager commandManager;


    public EcosystemManager() {
        gameEngine = new GameEngine();
        ecosystem = new Ecosystem();
        commandManager = new CommandManager();
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

    public IElement addElementToRandomFreePosition(Element type) {
        return ecosystem.addElementToRandomFreePosition(type);
    }

    //-------------------------Commands:
    public void addElementCommand(Element element){
        ICommand addCommand = new AddElementCommand(ecosystem,element);
        commandManager.invokeCommand(addCommand);
    }
    public void removeElementCommand(IElement element){
        ICommand removeCommand = new RemoveElementCommand(ecosystem,element);
        commandManager.invokeCommand(removeCommand);
    }
    public void setStrengthElementCommand(int elementId, double newStrength){
        ICommand editCommand = new SetStrengthElementCommand(ecosystem,elementId,newStrength);
        commandManager.invokeCommand(editCommand);
    }
    public void setPositionElementCommand(int elementId, double newX, double newY) {
        ICommand command = new SetPositionElementCommand(ecosystem, elementId, newX, newY);
        commandManager.invokeCommand(command);
    }

    public void setDamageToFloraCommand(double damage){
        ICommand command = new SetDamageToFloraCommand(ecosystem, damage);
        commandManager.invokeCommand(command);
    }

    public void setDecMovementEnergyCommand(double decMovementEnergy){
        ICommand command = new SetDecMovementEnergyCommand(ecosystem, decMovementEnergy);
        commandManager.invokeCommand(command);
    }
    public void setGameIntervalCommand(long newInterval){
        ICommand command = new SetGameIntervalCommand(ecosystem, gameEngine, newInterval);
        commandManager.invokeCommand(command);
    }
    public void undo(){
        commandManager.undo();
    }

    public void redo(){
        commandManager.redo();
    }


//_________________________
    public void addElement(IElement element_) {
        ecosystem.addElement(element_);
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

    public int updateLastElementId(){
        return ecosystem.updateLastElementId();
    }

    public int getLastElementId() {
        return ecosystem.getLastElementId();
    }


    public ConcurrentMap<Integer, IElement> getElements() {
        return ecosystem.getElements();
    }

    public boolean save(String filepath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filepath))) {
            ecosystem.updateLastElementId();
            oos.writeObject(ecosystem);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean load(String filepath)
    {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath))) {
            gameEngine.unregisterClient(ecosystem);
            ecosystem = (Ecosystem)ois.readObject();
            gameEngine.registerClient(ecosystem);
            gameEngine.resume();
            BaseElement.lastId = ecosystem.getLastElementId();
           //
            //_pcs.firePropertyChange(PROP_STATE_CHANGE, null, null);
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }   
        
        return true; 
    }


    public boolean importGame(String filepath) {
        return ecosystem.importToCSV(filepath);
    }

    public boolean exportGame(String filepath) {
        return ecosystem.exportToCSV(filepath);
    }

    public void renderUpdated() {
        ecosystem.updateRender();
    }

    public void clearElements() {
        ecosystem.clearElements();
    }

    public void addPropertyChangeListener(String prop,PropertyChangeListener listener) {
        ecosystem.addPropertyChangeListener(prop,listener);
    }

    public void removeObserver(String prop,PropertyChangeListener listener) {
        ecosystem.removeObserver(prop,listener);
    }

}