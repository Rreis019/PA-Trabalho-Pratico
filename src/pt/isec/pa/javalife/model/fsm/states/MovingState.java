package pt.isec.pa.javalife.model.fsm.states;

import pt.isec.pa.javalife.model.Ecosystem;
import pt.isec.pa.javalife.model.fsm.FaunaState;
import pt.isec.pa.javalife.model.fsm.FaunaStateAdapter;
import pt.isec.pa.javalife.model.fsm.FaunaStateContext;



public class MovingState extends FaunaStateAdapter {

	public MovingState(FaunaStateContext context, Ecosystem ecosystem)
	{
		super(context,ecosystem);
	}

 	@Override
    public FaunaState getState() {
        return FaunaState.MOVING;
    }

	@Override
	public boolean execute() {
		// TODO : Fazer MovingState
		return false;
	}

}