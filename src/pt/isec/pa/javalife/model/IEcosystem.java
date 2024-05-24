package pt.isec.pa.javalife.model;

import java.beans.PropertyChangeListener;

public interface IEcosystem { //Interface do facade
    void addPropertyChangeListener(String property,PropertyChangeListener listener);
    void removeObserver(String property,PropertyChangeListener listener);
}
