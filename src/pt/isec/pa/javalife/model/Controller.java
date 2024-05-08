package pt.isec.pa.javalife.model;

import pt.isec.pa.javalife.model.fsm.FaunaStateContext;

import java.beans.PropertyChangeListener;


/*
* Classe para o Manager da FSM; Aqui terá as transições da fsm,
* gestão do estado atual e etc ...
* */
public class Controller {

    private FaunaStateContext fsm; // Contexto da FSM
    private EcosystemManager ecosystem; // Manager do projeto (ecossistema)


    public Controller() {
        ecosystem = new EcosystemManager();
        fsm = new FaunaStateContext(ecosystem);
    }

    //área para o observable
     public void addObserver(PropertyChangeListener listener) {
        ecosystem.addObserver(listener);
    }

    public void removeObserver(PropertyChangeListener listener) {
        ecosystem.removeObserver(listener);
    }

    //área para as transições


    //área para os gets



}
