package pt.isec.pa.javalife.model.memento;

import pt.isec.pa.javalife.model.Ecosystem;

public class Memento implements IMemento{

    private byte[] snapshot;
    public Memento(Ecosystem ecosystem) {
    }

    @Override
    public Object getSnapShot() {
        if (snapshot == null) return null;

        return IMemento.super.getSnapShot();
    }

}
