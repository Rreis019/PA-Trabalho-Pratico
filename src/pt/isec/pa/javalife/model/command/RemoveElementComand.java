package pt.isec.pa.javalife.model.command;

import pt.isec.pa.javalife.model.data.elements.IElement;
import pt.isec.pa.javalife.model.Ecosystem;

public class RemoveElementComand extends CommandAdapter {
    private IElement copy;    
    private IElement actualEnt;

	public RemoveElementComand(Ecosystem receiver_,IElement element_)
	{
		super(receiver_);
        copy = element_.clone();
        actualEnt = element_;
	}

	@Override
    public boolean execute() {
        receiver.removeElement(actualEnt);
        return true;
    }

    @Override
    public boolean undo() {
        receiver.addElement(copy);
        return true;
    }

}