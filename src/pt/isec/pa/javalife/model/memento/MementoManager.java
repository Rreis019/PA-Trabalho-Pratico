package pt.isec.pa.javalife.model.memento;

import pt.isec.pa.javalife.model.IEcosystem;

import java.util.Deque;
import java.util.ArrayDeque;


public class MementoManager {
    private IEcosystem originator;
    private Deque<IMemento> history;
    private Deque<IMemento> redoHist;

    public MementoManager(IEcosystem originator) {
        this.originator = originator;
        history = new ArrayDeque<>();
        redoHist = new ArrayDeque<>();
    }

    public void save() {
        redoHist.clear();
        history.push(originator.save());
    }

    public void undo() {
        if (!history.isEmpty()) {
            redoHist.push(originator.save());
            originator.restore(history.pop());
        }
    }

    public void redo() {
        if (!redoHist.isEmpty()) {
            history.push(originator.save());
            originator.restore(redoHist.pop());
        }
    }

    public void reset() {
        history.clear();
        redoHist.clear();
    }

    public boolean hasUndo() {
        return !history.isEmpty();
    }

    public boolean hasRedo() {
        return !redoHist.isEmpty();
    }


}
