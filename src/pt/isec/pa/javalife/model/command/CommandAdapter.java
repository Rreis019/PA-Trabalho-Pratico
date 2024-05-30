package pt.isec.pa.javalife.model.command;

import java.io.Serializable;

import pt.isec.pa.javalife.model.Ecosystem;
import pt.isec.pa.javalife.model.EcosystemManager;

abstract class CommandAdapter implements ICommand,Serializable {
    protected Ecosystem receiver;

    protected CommandAdapter(Ecosystem receiver) {
        this.receiver = receiver;
    }
}
