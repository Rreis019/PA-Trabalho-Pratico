package pt.isec.pa.javalife.model.command;

import pt.isec.pa.javalife.model.Ecosystem;
import pt.isec.pa.javalife.model.data.elements.Fauna;

public class SetDecMovementEnergyCommand extends CommandAdapter{
    private double newDecMovementEnergy;
    private double oldDecMovementEnergy;

    public SetDecMovementEnergyCommand(Ecosystem ecosystem, double newDecMovementEnergy) {
        super(ecosystem);
        this.newDecMovementEnergy = newDecMovementEnergy;
        this.oldDecMovementEnergy = Fauna.decMovementEnergy;
    }

    @Override
    public boolean execute() {
        Fauna.decMovementEnergy = newDecMovementEnergy;
        return true;
    }

    @Override
    public boolean undo() {
        Fauna.decMovementEnergy = oldDecMovementEnergy;
        return true;
    }
}

