package pt.isec.pa.javalife.model.fsm.states;

import pt.isec.pa.javalife.model.Ecosystem;
import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.fsm.FaunaState;
import pt.isec.pa.javalife.model.fsm.FaunaStateAdapter;
import pt.isec.pa.javalife.model.fsm.FaunaStateContext;



public class MovingState extends FaunaStateAdapter {

	public MovingState(FaunaStateContext context, Ecosystem ecosystem,Fauna fauna_)
	{
		super(context,ecosystem, fauna_);
	}

 	@Override
    public FaunaState getState() {
        return FaunaState.MOVING;
    }

	@Override
	public boolean execute() {
		// TODO : Fazer MovingState

		fauna.move(1, 0);
		return false;
	}

}