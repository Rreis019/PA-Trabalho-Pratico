package pt.isec.pa.javalife.model.command;

import pt.isec.pa.javalife.model.Ecosystem;

public class SetDamageFaunaToFloraCommand extends CommandAdapter {
	private double newDamage = 0;
	private double oldDamage = 0;

	public SetDamageFaunaToFloraCommand(Ecosystem receiver_,double newDamage_)
	{
		super(receiver_);
		oldDamage = receiver_.getDamageToFlora();
		newDamage = newDamage_;
	}

	@Override
    public boolean execute() { 
    	receiver.setDamageToFlora(newDamage);
    	return true;
    }

    @Override
    public boolean undo() {
		receiver.setDamageToFlora(oldDamage);
        return true;
    }

}