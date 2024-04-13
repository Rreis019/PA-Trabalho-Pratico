package pt.isec.pa.javalife.model.fsm.states;

import pt.isec.pa.javalife.model.data.Ecosystem;
import pt.isec.pa.javalife.model.fsm.FaunaState;
import pt.isec.pa.javalife.model.fsm.FaunaStateAdapter;
import pt.isec.pa.javalife.model.fsm.FaunaStateContext;


public class SearchFoodState extends FaunaStateAdapter {

	public SearchFoodState(FaunaStateContext context,Ecosystem ecosystem)
	{
		super(context,ecosystem);
	}

 	@Override
    public FaunaState getState() {
        return FaunaState.SEARCH_FOOD;
    }

	@Override
	public boolean execute() {
		//TODO : Fazer SearchFoodState
		return false;
	}

}