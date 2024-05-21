package pt.isec.pa.javalife.model.fsm.states;

import pt.isec.pa.javalife.model.Ecosystem;
import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.fsm.FaunaState;
import pt.isec.pa.javalife.model.fsm.FaunaStateAdapter;
import pt.isec.pa.javalife.model.fsm.FaunaStateContext;


public class ReproduceState extends FaunaStateAdapter {

	public ReproduceState(FaunaStateContext context, Ecosystem ecosystem, Fauna fauna_)
	{
		super(context,ecosystem, fauna_);
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