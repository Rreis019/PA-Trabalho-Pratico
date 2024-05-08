package pt.isec.pa.javalife.model.fsm;

import pt.isec.pa.javalife.model.fsm.states.AttackingState;
import pt.isec.pa.javalife.model.Ecosystem;
import pt.isec.pa.javalife.model.fsm.states.*;

public interface IFaunaState {

	FaunaState getState();
	boolean execute();


	//TODO : Da alguma formar passar os dados da apliocação
	static IFaunaState createState(FaunaState type, FaunaStateContext context, Ecosystem ecosystem)
	{
		return switch (type) {
            case MOVING -> new MovingState(context,ecosystem);
            case EATING -> new EatingState(context,ecosystem);
            case SEARCH_FOOD -> new SearchFoodState(context,ecosystem);
            case ATTACKING -> new AttackingState(context,ecosystem);
            case REPRODUCE -> new ReproduceState(context,ecosystem);
        };
	}

}