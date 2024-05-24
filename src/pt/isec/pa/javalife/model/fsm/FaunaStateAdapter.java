package pt.isec.pa.javalife.model.fsm;

import pt.isec.pa.javalife.model.Ecosystem;
import pt.isec.pa.javalife.model.data.elements.Fauna;

public abstract class FaunaStateAdapter implements IFaunaState {

	protected FaunaStateContext context; 
	protected Ecosystem ecosystem;
	protected Fauna fauna;

    protected void changeState(FaunaState newState){
        context.changeState(IFaunaState.createState(newState, context, ecosystem, fauna));
    }


	public FaunaStateAdapter(FaunaStateContext context, Ecosystem eco,Fauna fauna_)
	{
		this.context = context;
		this.ecosystem = eco;
		this.fauna = fauna_;
	}

  	@Override
    public boolean execute() { return false; }
}