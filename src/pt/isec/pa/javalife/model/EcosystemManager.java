package pt.isec.pa.javalife.model;
import java.util.concurrent.ConcurrentMap;

import pt.isec.pa.javalife.model.data.elements.IElement;
import pt.isec.pa.javalife.model.gameengine.GameEngine;
import pt.isec.pa.javalife.model.gameengine.GameEngineState;
import pt.isec.pa.javalife.model.data.elements.BaseElement;
import pt.isec.pa.javalife.model.data.elements.Element;

import pt.isec.pa.javalife.model.command.AddElementComand;
import pt.isec.pa.javalife.model.command.SetIntervalCommand;
import pt.isec.pa.javalife.model.command.CommandManager;
import pt.isec.pa.javalife.model.command.RemoveElementComand;
import pt.isec.pa.javalife.model.command.SetDamageFaunaToFloraCommand;
import pt.isec.pa.javalife.model.command.SetElementPos;
import pt.isec.pa.javalife.model.command.SetElementStrenght;
import pt.isec.pa.javalife.model.command.SetEnergyPerMovementCommand;
import pt.isec.pa.javalife.model.memento.CareTaker;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/*
 Classe facade do ecossistema que guarda ecosystem,memento,command
 */
public class EcosystemManager implements Serializable
{
	private final transient  GameEngine gameEngine;
    private Ecosystem ecosystem;
    private CommandManager command;
    private CareTaker careTaker;

    private transient  PropertyChangeSupport pcs;
    public static final String PROP_STATE = "state";
    public static final String PROP_DATA = "data";
    public static final String PROP_UNSAVED_CHANGES = "unsavedChanges";

    private int inspectedTargetId = -1;

    private boolean unsavedChanges = false;

    public EcosystemManager() {
        pcs = new PropertyChangeSupport(this);
        gameEngine = new GameEngine();
        ecosystem = new Ecosystem(pcs);
        command = new CommandManager();
        careTaker = new CareTaker(ecosystem);
        gameEngine.registerClient(ecosystem);
    }

    /*-----------------------------------------------------------------*/

    /**
     * Guarda o estado atual do ecossistema.
     * @throws IOException Exceção de E/S caso ocorra um erro durante a operação de salvamento.
     */
    public void saveSnapshot() throws IOException{
        careTaker.save();
    }

     /**
     * Restaura o ecossistema para um estado anterior guardado em um instantâneo.
     * @throws Exception Exceção lançada caso ocorra um erro durante a operação de restauração.
     */
    public void restoreSnapshot() throws Exception{
        careTaker.restore();
    } 

    //-------------------------Commands:

    /**
     * Verifica se existe um comando de desfazer disponível.
     * @return true se existir um comando de desfazer, false caso contrário.
     */
    public boolean hasUndo(){return command.hasUndo();}

      /**
     * Desfaz a última ação realizada no ecossistema.
     * @return true se a operação de desfazer foi bem-sucedida, false caso contrário.
     */
    public boolean undo(){
        boolean res = command.undo();
        pcs.firePropertyChange(PROP_STATE, null, null);
        pcs.firePropertyChange(PROP_DATA, null, null);
        return res;
    }

   /**
     * Verifica se existe um comando de refazer disponível.
     * @return true se existir um comando de refazer, false caso contrário.
    */
    public boolean hasRedo(){return command.hasRedo();}



    /**
     * Refaz a última ação desfeita no ecossistema.
     * @return true se a operação de refazer foi bem-sucedida, false caso contrário.
     */
    public boolean redo(){
        boolean res = command.redo();
        pcs.firePropertyChange(PROP_STATE, null, null);
        pcs.firePropertyChange(PROP_DATA, null, null);
        return res;
    }


    /**
     * Adiciona um novo elemento ao ecossistema.
     * @param type_ Tipo do elemento a adicionar.
     * @return true se o elemento foi adicionado com sucesso, false caso contrário.
     */
    public boolean addElement(Element type_)
    {       
        boolean result = command.invokeCommand(new AddElementComand(ecosystem, type_));
        if(result){setUnsavedChanges(true);}
        pcs.firePropertyChange(PROP_STATE, null, null);
        return result;
    }

     /**
     * Define o dano que a fauna causa à flora.
     * @param damage Dano a ser definido.
     * @return true se o dano foi definido com sucesso, false caso contrário.
     */
    public boolean setDamageFaunaToFlora(double damage)
    {
        boolean result = command.invokeCommand(new SetDamageFaunaToFloraCommand(ecosystem,damage));
        if(result){setUnsavedChanges(true);}
        pcs.firePropertyChange(PROP_DATA, null, null);
        return result;
    }

    /**
     * Define a energia gasta por movimento da fauna.
     * @param energy Energia por movimento a ser definida.
     * @return true se a energia foi definida com sucesso, false caso contrário.
     */
    public boolean setEnergyPerMovement(double energy)
    {
        boolean result = command.invokeCommand(new SetEnergyPerMovementCommand(ecosystem,energy));
        if(result){setUnsavedChanges(true);}
        pcs.firePropertyChange(PROP_DATA, null, null); 
        return result;
    }

     /**
     * Define o intervalo de tempo do jogo.
     * @param newInterval Novo intervalo de tempo.
     * @return true se o intervalo foi definido com sucesso, false caso contrário.
     */
    public boolean setInterval(long newInterval) {
        //gameEngine.setInterval(newInterval);
        boolean result = command.invokeCommand(new SetIntervalCommand(ecosystem,gameEngine,newInterval));
        return result;
    }

      /**
     * Define a posição de um elemento no ecossistema.
     * @param el Elemento a ser movido.
     * @param posX Posição X.
     * @param posY Posição Y.
     * @return true se a posição foi definida com sucesso, false caso contrário.
     */
    public boolean setElementPos(IElement el,double posX,double posY)
    {
        if(el.isReadOnly()){return false;}
        boolean result = command.invokeCommand(new SetElementPos(ecosystem,el,posX,posY));
        if(result){setUnsavedChanges(true);}
        pcs.firePropertyChange(PROP_STATE, null, null); 
        return result;
    }

      /**
     * Define a força de um elemento do ecossistema.
     * @param el Elemento a ser afetado.
     * @param strenght_ Força a ser definida.
     * @return true se a força foi definida com sucesso, false caso contrário.
     */
    public boolean setElementStrenght(IElement el,double strenght_)
    {
        if(el.isReadOnly()){return false;}
        boolean result = command.invokeCommand(new SetElementStrenght(ecosystem,el,strenght_));
        if(result){setUnsavedChanges(true);}
        pcs.firePropertyChange(PROP_DATA, null, null); 
        return result;
    }

    /**
     * Remove um elemento do ecossistema.
     * @param element_ Elemento a ser removido.
     * @return true se o elemento foi removido com sucesso, false caso contrário.
     */
    public boolean removeElement(IElement element_) {
    	//ecosystem.removeElement(element_);
        boolean result = command.invokeCommand(new RemoveElementComand(ecosystem,element_));
        if(result){setUnsavedChanges(true);}
        pcs.firePropertyChange(PROP_STATE, null, null); 
        return result;
    }

    /*-----------------------------------------------------------------*/

    /**
     * Obtém o ID do alvo inspecionado atualmente.
     * @return O ID do alvo inspecionado.
     */
    public int getInspectTargetId()
    {
        return inspectedTargetId;
    }


    /**
     * Define o alvo a ser inspecionado.
     * @param id ID do alvo a ser inspecionado.
     */
    public void setInspectTarget(int id)
    {
        inspectedTargetId = id;
        pcs.firePropertyChange(PROP_STATE, null, null);
        pcs.firePropertyChange(PROP_DATA, null, null); 
    }

    /**
     * Obtém o dano causado à flora pela fauna.
     * @return O dano causado à flora.
     */
    public double getDamageToFlora(){return ecosystem.getDamageToFlora();}


    /**
     * Obtém a energia gasta pela fauna por movimento.
     * @return A energia gasta pela fauna por movimento.
     */
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


    public ConcurrentMap<Integer, IElement> getElements() {
        return ecosystem.getElements();
    }

    /**
     * Salva o estado atual do ecossistema em um arquivo.
     * 
     * @param filepath O caminho do arquivo onde o estado será salvo.
     * @return true se o estado foi salvo com sucesso, false caso contrário.
     */
    public boolean save(String filepath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filepath))) {
            ecosystem.getLastElementId();
            oos.writeObject(ecosystem);
            setUnsavedChanges(false);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Carrega um estado previamente salvo do ecossistema a partir de um arquivo.
     * 
     * @param filepath O caminho do arquivo onde o estado está salvo.
     * @return true se o estado foi carregado com sucesso, false caso contrário.
     */
    public boolean load(String filepath)
    {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath))) {
            gameEngine.unregisterClient(ecosystem);
            ecosystem = (Ecosystem)ois.readObject();
            gameEngine.registerClient(ecosystem);
            gameEngine.resume();
            BaseElement.lastId = ecosystem.getLastElementId();
            pcs.firePropertyChange(PROP_STATE, null, null);
            setUnsavedChanges(false);
            //
            //_pcs.firePropertyChange(PROP_STATE_CHANGE);
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }   
        
        return true; 
    }

    /**
     * Importa um jogo a partir de um arquivo CSV especificado.
     * 
     * @param filepath O caminho do arquivo CSV contendo os dados do jogo a ser importado.
     * @return true se o jogo foi importado com sucesso, false caso contrário.
     */
    public boolean importGame(String filepath) {
        pcs.firePropertyChange(PROP_STATE, null, null);
        return ecosystem.importToCSV(filepath);
    }

    /**
     * Exporta o jogo atual para um arquivo CSV especificado.
     * 
     * @param filepath O caminho do arquivo CSV onde os dados do jogo serão exportados.
     * @return true se o jogo foi exportado com sucesso, false caso contrário.
     */
    public boolean exportGame(String filepath) {
        return ecosystem.exportToCSV(filepath);
    }

    /**
     * Notifica que o estado do jogo foi atualizado.
     */
    public void renderUpdated() {
          pcs.firePropertyChange(PROP_STATE, null, null);
          pcs.firePropertyChange(PROP_DATA, null, null);
    }

    /**
     * Remove todos os elementos do ecossistema.
     */
    public void clearElements() {
        ecosystem.clearElements();
    }

    public IElement addElementToRandomFreePosition(Element type) {
        return ecosystem.addElementToRandomFreePosition(type);
    }

    /**
     * Adiciona um listener de propriedade para observar mudanças em uma propriedade específica.
     * 
     * @param prop     A propriedade a ser observada.
     * @param listener O listener de propriedade a ser adicionado.
     */
    public void addPropertyChangeListener(String prop,PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(prop,listener);
    }

    /**
     * Remove um listener de propriedade.
     * 
     * @param prop     A propriedade a ser observada.
     * @param listener O listener de propriedade a ser removido.
     */
    public void removeObserver(String prop,PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(prop,listener);
    }

    public boolean hasUnsavedChanges() {
        return unsavedChanges;
    }

    private void setUnsavedChanges(boolean unsavedChanges) {
        boolean old = this.unsavedChanges;
        this.unsavedChanges = unsavedChanges;
        pcs.firePropertyChange(PROP_UNSAVED_CHANGES, old, unsavedChanges);
    }


}