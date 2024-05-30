package pt.isec.pa.javalife.model.memento;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;

public class CareTaker implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final IMementoOriginator originator;

    private Deque<Memento> history = new ArrayDeque<>();
    private Deque<Memento> redoHist = new ArrayDeque<>();

    public CareTaker(IMementoOriginator originator) {
        this.originator = originator;
    }

    public void save() throws IOException {

            history.push(originator.getMemento());
            if (history.size()>10)
                history.removeFirst();

            redoHist.push(originator.getMemento());
    }

    public void cleanStack(){
        history.clear();
    }

    public int size(){
        return history.size();
    }

    public void restore() throws Exception{
        if (history.isEmpty())
            return;
            Memento anterior = history.pop();
            originator.setMemento(anterior);

    }

    public void startReplay() throws Exception {
        Memento next = redoHist.removeLast();
        originator.setMemento(next);


    }
}