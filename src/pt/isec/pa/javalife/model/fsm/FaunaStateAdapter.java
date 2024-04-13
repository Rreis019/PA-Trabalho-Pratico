package pt.isec.pa.javalife.model.fsm;

import pt.isec.pa.javalife.model.data.Ecosystem;

public abstract class FaunaStateAdapter implements IFaunaState {

	protected FaunaStateContext context; 
	protected Ecosystem ecosystem;

	public FaunaStateAdapter(FaunaStateContext context,Ecosystem eco)
	{
		this.context = context;
		this.ecosystem = eco;
	}

  	@Override
    public boolean execute() { return false; }
}