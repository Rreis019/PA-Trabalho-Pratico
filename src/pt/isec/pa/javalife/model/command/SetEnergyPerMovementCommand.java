package pt.isec.pa.javalife.model.command;
import pt.isec.pa.javalife.model.gameengine.GameEngine;
import pt.isec.pa.javalife.model.Ecosystem;


public class SetEnergyPerMovementCommand extends CommandAdapter {
	private double oldDecMovementEnergy = 0;
	private double newDecMovementEnergy = 0;
	GameEngine eng;
	public SetEnergyPerMovementCommand(Ecosystem receiver_,double newDecMovementEnergy_)
	{
		super(receiver_);
		oldDecMovementEnergy = receiver_.getFaunaMovementEnergy();
		newDecMovementEnergy = newDecMovementEnergy_;
	}

	@Override
    public boolean execute() { 
    	receiver.setFaunaMovementEnergy(newDecMovementEnergy);
    	return true;
    }

    @Override
    public boolean undo() {
    	System.out.println("undo..");
		receiver.setFaunaMovementEnergy(oldDecMovementEnergy);
        return true;
    }

}