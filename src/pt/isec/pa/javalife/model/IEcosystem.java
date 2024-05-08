package pt.isec.pa.javalife.model;

import java.beans.PropertyChangeListener;

public interface IEcosystem { //Interface do facade
    void addObserver(PropertyChangeListener listener);
    void removeObserver(PropertyChangeListener listener);
}
