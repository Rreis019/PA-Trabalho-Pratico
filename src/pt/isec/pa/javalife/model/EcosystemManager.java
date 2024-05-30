package pt.isec.pa.javalife.model;
import java.util.concurrent.ConcurrentMap;

import pt.isec.pa.javalife.model.data.elements.IElement;
import pt.isec.pa.javalife.model.gameengine.GameEngine;
import pt.isec.pa.javalife.model.gameengine.GameEngineState;
import pt.isec.pa.javalife.model.data.Area;
import pt.isec.pa.javalife.model.data.elements.BaseElement;
import pt.isec.pa.javalife.model.data.elements.Element;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import pt.isec.pa.javalife.model.command.AddElementComand;
import pt.isec.pa.javalife.model.command.SetIntervalCommand;
import pt.isec.pa.javalife.model.command.CommandManager;
import pt.isec.pa.javalife.model.command.RemoveElementComand;
import pt.isec.pa.javalife.model.command.SetDamageFaunaToFloraCommand;
import pt.isec.pa.javalife.model.command.SetElementPos;
import pt.isec.pa.javalife.model.command.SetElementStrenght;
import pt.isec.pa.javalife.model.command.SetEnergyPerMovementCommand;
import pt.isec.pa.javalife.model.gameengine.IGameEngine;
import pt.isec.pa.javalife.model.memento.CareTaker;
import pt.isec.pa.javalife.model.memento.Memento;

//Classe Facade 
public class EcosystemManager implements Serializable
{
	private final transient  GameEngine gameEngine;
    private Ecosystem ecosystem;
    private CommandManager command;
    private CareTaker careTaker;

    private transient  PropertyChangeSupport pcs;
    public static final String PROP_STATE = "state";
    public static final String PROP_DATA = "data";

    private int inspectedTargetId = -1;

    public EcosystemManager() {
        pcs = new PropertyChangeSupport(this);
        gameEngine = new GameEngine();
        ecosystem = new Ecosystem(pcs);
        command = new CommandManager();
        careTaker = new CareTaker(ecosystem);
        gameEngine.registerClient(ecosystem);
    }

    /*-----------------------------------------------------------------*/

    public void saveSnapshot() throws IOException{
        careTaker.save();
    }

    public void restoreSnapshot() throws Exception{
        careTaker.restore();
    } 

    //-------------------------Commands:

    public boolean hasUndo(){return command.hasUndo();}
    public boolean undo(){
        boolean res = command.undo();
        pcs.firePropertyChange(PROP_STATE, null, null);
        pcs.firePropertyChange(PROP_DATA, null, null);
        return res;
    }

    public boolean hasRedo(){return command.hasRedo();}
    public boolean redo(){
        boolean res = command.redo();
        pcs.firePropertyChange(PROP_STATE, null, null);
        pcs.firePropertyChange(PROP_DATA, null, null);
        return res;
    }


    public boolean addElement(Element type_)
    {       
        boolean result = command.invokeCommand(new AddElementComand(ecosystem, type_));
        pcs.firePropertyChange(PROP_STATE, null, null);   
        return result;
    }

    public boolean setDamageFaunaToFlora(double damage)
    {
        boolean result = command.invokeCommand(new SetDamageFaunaToFloraCommand(ecosystem,damage));
        pcs.firePropertyChange(PROP_DATA, null, null);  
        return result;
    }

    public boolean setEnergyPerMovement(double energy)
    {
        boolean result = command.invokeCommand(new SetEnergyPerMovementCommand(ecosystem,energy));
        pcs.firePropertyChange(PROP_DATA, null, null); 
        return result;
    }

    public boolean setInterval(long newInterval) {
        //gameEngine.setInterval(newInterval);
        boolean result = command.invokeCommand(new SetIntervalCommand(ecosystem,gameEngine,newInterval));
        return result;
    }

    public boolean setElementPos(IElement el,double posX,double posY)
    {
        boolean result = command.invokeCommand(new SetElementPos(ecosystem,el,posX,posY));
        pcs.firePropertyChange(PROP_STATE, null, null); 
        return result;
    }

    public boolean setElementStrenght(IElement el,double strenght_)
    {
        boolean result = command.invokeCommand(new SetElementStrenght(ecosystem,el,strenght_));
        pcs.firePropertyChange(PROP_DATA, null, null); 
        return result;
    }

    public boolean removeElement(IElement element_) {
    	//ecosystem.removeElement(element_);
        boolean result = command.invokeCommand(new RemoveElementComand(ecosystem,element_));
        pcs.firePropertyChange(PROP_STATE, null, null); 
        return result;
    }

    /*-----------------------------------------------------------------*/

    public int getInspectTargetId()
    {
        return inspectedTargetId;
    }

    public void setInspectTarget(int id)
    {
        inspectedTargetId = id;
        pcs.firePropertyChange(PROP_STATE, null, null);
        pcs.firePropertyChange(PROP_DATA, null, null); 
    }

    public double getDamageToFlora(){return ecosystem.getDamageToFlora();}
    public double getFaunaMovementEnergy() {return ecosystem.getFaunaMovementEnergy();}


    public void setGameInterval(long newInterval) {
        gameEngine.setInterval(newInterval);
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

    public int getLastElementId() {
        return ecosystem.getLastElementId();
    }


    public void resumeGame() {
        gameEngine.resume();
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

    public void applyStrengthEvent(IElement element) {
        ecosystem.applyStrenghtEvent(element);
        pcs.firePropertyChange(PROP_STATE, null, null);
    }

    public void applyHerbicideEvent(IElement element) {
        ecosystem.applyHerbicideEvent(element);
          pcs.firePropertyChange(PROP_STATE, null, null);
    }

    public void applySunEvent(IElement element) {
        ecosystem.applySunEvent(element);
          pcs.firePropertyChange(PROP_STATE, null, null);
    }

    public int getTicks(){return ecosystem.getTicks();}
    public void resetTicksCounter(){ecosystem.resetTicksCounter();}


    public ConcurrentMap<Integer, IElement> getElements() {
        return ecosystem.getElements();
    }

      
    public boolean save(String filepath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filepath))) {
            ecosystem.getLastElementId();
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
              pcs.firePropertyChange(PROP_STATE, null, null);
            //
            //_pcs.firePropertyChange(PROP_STATE_CHANGE);
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }   
        
        return true; 
    }


    public boolean importGame(String filepath) {
          pcs.firePropertyChange(PROP_STATE, null, null);
        return ecosystem.importToCSV(filepath);
    }

    public boolean exportGame(String filepath) {
        return ecosystem.exportToCSV(filepath);
    }

    public void renderUpdated() {
          pcs.firePropertyChange(PROP_STATE, null, null);
          pcs.firePropertyChange(PROP_DATA, null, null);
    }

    public void clearElements() {
        ecosystem.clearElements();
    }

    public IElement addElementToRandomFreePosition(Element type) {
        return ecosystem.addElementToRandomFreePosition(type);
    }


    public void addPropertyChangeListener(String prop,PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(prop,listener);
    }

    public void removeObserver(String prop,PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(prop,listener);
    }


}