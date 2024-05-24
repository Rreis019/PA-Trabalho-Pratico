package pt.isec.pa.javalife.model.fsm.states;

import java.lang.annotation.Target;
import java.util.Random;

import pt.isec.pa.javalife.model.Ecosystem;
import pt.isec.pa.javalife.model.data.Area;
import pt.isec.pa.javalife.model.data.elements.BaseElement;
import pt.isec.pa.javalife.model.data.elements.Element;
import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.data.elements.Flora;
import pt.isec.pa.javalife.model.data.elements.IElement;
import pt.isec.pa.javalife.model.data.elements.Inanimate;
import pt.isec.pa.javalife.model.fsm.Direction;
import pt.isec.pa.javalife.model.fsm.FaunaState;
import pt.isec.pa.javalife.model.fsm.FaunaStateAdapter;
import pt.isec.pa.javalife.model.fsm.FaunaStateContext;


public class SearchFoodState extends FaunaStateAdapter {


	public SearchFoodState(FaunaStateContext context, Ecosystem ecosystem, Fauna fauna_)
	{
		super(context,ecosystem, fauna_);
	}

 	@Override
    public FaunaState getState() {
        return FaunaState.SEARCH_FOOD;
    }

	@Override
	public boolean execute() {
		IElement closestFlora = ecosystem.getClossestElement(fauna.getArea(), Element.FLORA);
	    Fauna weakestFauna = null;

	    if (closestFlora != null) {
	        if (moveTo(closestFlora)) {changeState(FaunaState.EATING);}
	    } else {
	        weakestFauna = ecosystem.getWeakestFauna(fauna.getId());
	        if (weakestFauna != null && moveTo(weakestFauna)) {changeState(FaunaState.ATTACKING);}
	    }

	    changeState(FaunaState.MOVING);
	    return true;
	}


	private Direction getAlternativeDirection(Direction currentDirection) {
        switch (currentDirection) {
            case LEFT:
            case RIGHT:
                return (Math.random() > 0.5) ? Direction.UP : Direction.DOWN;
            case UP:
            case DOWN:
                return (Math.random() > 0.5) ? Direction.LEFT : Direction.RIGHT;
            default:
                return Direction.RIGHT;
        }
    }
}