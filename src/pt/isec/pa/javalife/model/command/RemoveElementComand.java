package pt.isec.pa.javalife.model.command;
import pt.isec.pa.javalife.model.data.elements.Element;
import pt.isec.pa.javalife.model.data.elements.IElement;
import pt.isec.pa.javalife.model.Ecosystem;
import pt.isec.pa.javalife.model.EcosystemManager;
public class RemoveElementComand extends CommandAdapter {

    private IElement copy;    
    private IElement actualEnt;

	public RemoveElementComand(Ecosystem receiver_,IElement element_)
	{
		super(receiver_);
		//type = id;
        copy = element_.clone();
        actualEnt = element_;
        //
	}

	@Override
    public boolean execute() { 
        System.out.println("bruh");
        receiver.removeElement(actualEnt);
        return true;
    }

    @Override
    public boolean undo() {
        receiver.addElement(copy);
        return true;
    }

}