package pt.isec.pa.javalife.model.fsm;

import pt.isec.pa.javalife.model.EcosystemManager;

public abstract class FaunaStateAdapter implements IFaunaState {

	protected FaunaStateContext context; 
	protected EcosystemManager ecosystem;

	public FaunaStateAdapter(FaunaStateContext context, EcosystemManager eco)
	{
		this.context = context;
		this.ecosystem = eco;
	}

  	@Override
    public boolean execute() { return false; }
}