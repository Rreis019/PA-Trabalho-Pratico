package pt.isec.pa.javalife.model.fsm.states;

import pt.isec.pa.javalife.model.Ecosystem;
import pt.isec.pa.javalife.model.fsm.FaunaState;
import pt.isec.pa.javalife.model.fsm.FaunaStateAdapter;
import pt.isec.pa.javalife.model.fsm.FaunaStateContext;


public class ReproduceState extends FaunaStateAdapter {

	public ReproduceState(FaunaStateContext context, Ecosystem ecosystem)
	{
		super(context,ecosystem);
	}

 	@Override
    public FaunaState getState() {
        return FaunaState.REPRODUCE;
    }

	@Override
	public boolean execute() {
		//TODO : Fazer ReproduceState
		return false;
	}

}