package pt.isec.pa.javalife.model.fsm;

import pt.isec.pa.javalife.model.fsm.states.AttackingState;
import pt.isec.pa.javalife.model.Ecosystem;
import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.fsm.states.*;

public interface IFaunaState {

	FaunaState getState();
	boolean execute();


	//TODO : Da alguma formar passar os dados da apliocação
	public static IFaunaState createState(FaunaState type, FaunaStateContext context, Ecosystem ecosystem,Fauna fauna_)
	{
		return switch (type) {
            case MOVING -> new MovingState(context,ecosystem,fauna_);
            case EATING -> new EatingState(context,ecosystem,fauna_);
            case SEARCH_FOOD -> new SearchFoodState(context,ecosystem,fauna_);
            case ATTACKING -> new AttackingState(context,ecosystem,fauna_);
            case REPRODUCE -> new ReproduceState(context,ecosystem,fauna_);
        };
	}

}