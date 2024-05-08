package pt.isec.pa.javalife.model.fsm.states;

import pt.isec.pa.javalife.model.EcosystemManager;
import pt.isec.pa.javalife.model.fsm.FaunaState;
import pt.isec.pa.javalife.model.fsm.FaunaStateAdapter;
import pt.isec.pa.javalife.model.fsm.FaunaStateContext;


//TODO : Fazer EatingState

public class EatingState extends FaunaStateAdapter {

	public EatingState(FaunaStateContext context, EcosystemManager ecosystem)
	{
		super(context,ecosystem);
	}

 	@Override
    public FaunaState getState() {
        return FaunaState.EATING;
    }

	@Override
	public boolean execute() {
		//TODO : Fazer EatingState
		return false;
	}

}