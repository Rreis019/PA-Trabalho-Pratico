package pt.isec.pa.javalife.model.command;

import pt.isec.pa.javalife.model.Ecosystem;

import java.io.Serializable;

abstract class CommandAdapter implements ICommand, Serializable {
    protected Ecosystem ecosystem;

    protected CommandAdapter(Ecosystem ecosystem) {
        this.ecosystem = ecosystem;
    }
    @Override
    public boolean execute() {
        return false;
    }

    @Override
    public boolean undo() {
        return false;
    }
}
