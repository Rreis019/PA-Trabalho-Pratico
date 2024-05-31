package pt.isec.pa.javalife.model.command;

import pt.isec.pa.javalife.model.data.elements.IElement;
import pt.isec.pa.javalife.model.Ecosystem;
import pt.isec.pa.javalife.model.data.elements.BaseElement;


public class SetElementPos extends CommandAdapter {
	private double oldPositionX,oldPositionY;
	private double newPositionX,newPositionY;

	private IElement element;

	public SetElementPos(Ecosystem receiver_,IElement ent_,double newPositionX_,double newPositionY_)
	{
		super(receiver_);

		element = ent_;

		oldPositionX = element.getArea().left();
		oldPositionY = element.getArea().top();

		newPositionX = newPositionX_;
		newPositionY = newPositionY_; 
	}

	@Override
    public boolean execute() { 
		((BaseElement)element).setPosition(newPositionX, newPositionY);
    	return true;
    }

    @Override
    public boolean undo() {
    	if(receiver.getElement(element.getId()) == null){return true;}

		((BaseElement)element).setPosition(oldPositionX, oldPositionY);
        return true;
    }

}