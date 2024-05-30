package pt.isec.pa.javalife.model.memento;

public interface IMemento {
   default Object getSnapShot() {
        return null;
    }

}
