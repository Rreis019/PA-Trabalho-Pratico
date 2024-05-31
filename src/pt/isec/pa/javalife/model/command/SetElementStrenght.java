package pt.isec.pa.javalife.model.command;
import pt.isec.pa.javalife.model.data.elements.Element;
import pt.isec.pa.javalife.model.data.elements.IElement;
import pt.isec.pa.javalife.model.Ecosystem;
import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.data.elements.Flora;

public class SetElementStrenght extends CommandAdapter {
	
	private int elementId;
    private double newStrength;
    private double oldStrength;


	public SetElementStrenght(Ecosystem receiver_,IElement ent_,double newStrength_)
	{
		super(receiver_);
		elementId = ent_.getId();
		if(ent_.getType() == Element.FLORA){oldStrength = ((Flora)ent_).getStrength();}
		else if(ent_.getType() == Element.FAUNA){oldStrength = ((Fauna)ent_).getStrength();}

		newStrength = newStrength_;
	}

	@Override
    public boolean execute() { 
    	IElement ent = receiver.getElement(elementId);
    	if(ent == null){return false;}

		if(ent.getType() == Element.FLORA){((Flora)ent).setStrength(newStrength);}
		if(ent.getType() == Element.FAUNA){((Fauna)ent).setStrength(newStrength);}
    	return true;
    }

    @Override
    public boolean undo() {
    	IElement ent = receiver.getElement(elementId);
    	if(ent == null){return false;}

		if(ent.getType() == Element.FLORA){((Flora)ent).setStrength(oldStrength);}
		if(ent.getType() == Element.FAUNA){((Fauna)ent).setStrength(oldStrength);}
        return true;
    }

}