package pt.isec.pa.javalife.model;

import pt.isec.pa.javalife.model.memento.IMemento;

import java.beans.PropertyChangeListener;

public interface IEcosystem { //Interface do facade
    IMemento save();
    void restore(IMemento memento);
    void addPropertyChangeListener(String property,PropertyChangeListener listener);
    void removeObserver(String property,PropertyChangeListener listener);
}
