package pt.isec.pa.javalife.model.command;

import pt.isec.pa.javalife.model.Ecosystem;
import pt.isec.pa.javalife.model.data.elements.Fauna;

public class SetDamageToFloraCommand extends CommandAdapter {
    private double newDamageToFlora;
    private double oldDamageToFlora;

    public SetDamageToFloraCommand(Ecosystem ecosystem, double newDamageToFlora) {
        super(ecosystem);
        this.newDamageToFlora = newDamageToFlora;
        this.oldDamageToFlora = Fauna.damageToFlora;
    }

    @Override
    public boolean execute() {
        Fauna.damageToFlora = newDamageToFlora;
        return true;
    }

    @Override
    public boolean undo() {
        Fauna.damageToFlora = oldDamageToFlora;
        return true;
    }
}

