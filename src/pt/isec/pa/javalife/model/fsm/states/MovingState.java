package pt.isec.pa.javalife.model.fsm.states;

import java.io.Serializable;
import java.util.Random;

import pt.isec.pa.javalife.model.Ecosystem;
import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.fsm.Direction;
import pt.isec.pa.javalife.model.fsm.FaunaState;
import pt.isec.pa.javalife.model.fsm.FaunaStateAdapter;
import pt.isec.pa.javalife.model.fsm.FaunaStateContext;



public class MovingState extends FaunaStateAdapter implements Serializable {

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
		Random random = new Random();
		int chance  = random.nextInt(100);
		
		if(chance < 10)
		{
			Direction currentDirection = fauna.getDirection();
            if (currentDirection == Direction.LEFT || currentDirection == Direction.RIGHT) {
                fauna.setDirection(random.nextBoolean() ? Direction.UP : Direction.DOWN);
            } else if (currentDirection == Direction.UP || currentDirection == Direction.DOWN) {
                fauna.setDirection(random.nextBoolean() ? Direction.LEFT : Direction.RIGHT);
            }
		}


		fauna.moveForward();
		if(fauna.getStrength() > 50){
			changeState(FaunaState.REPRODUCE);
		}
		else if(fauna.getStrength() < 35){
			changeState(FaunaState.SEARCH_FOOD);
		}
		return false;
	}

}