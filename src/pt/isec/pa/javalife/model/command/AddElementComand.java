package pt.isec.pa.javalife.model.command;

import pt.isec.pa.javalife.model.data.elements.Element;
import pt.isec.pa.javalife.model.data.elements.IElement;

import pt.isec.pa.javalife.model.Ecosystem;

public class AddElementComand extends CommandAdapter {
	private int id = -1;
	private Element type;
	private IElement copy;

	public AddElementComand(Ecosystem receiver_,Element type_)
	{
		super(receiver_);
		type = type_;
	}

	@Override
    public boolean execute() {
    	if(id == -1){
	    	IElement element = receiver.addElementToRandomFreePosition(type);
	    	if(element == null){return false;}
	    	copy = element.clone();
	    	id = element.getId();

    	}else{
    		receiver.addElement(copy);
    	}
    	return true;
    }

    @Override
    public boolean undo() {
    	IElement ent = receiver.getElement(id);
    	if(ent == null){return false;}
        receiver.removeElement(ent);
        return true;
    }

}