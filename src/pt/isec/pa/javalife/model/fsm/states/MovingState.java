package pt.isec.pa.javalife.model.fsm.states;

import java.util.Random;

import pt.isec.pa.javalife.model.Ecosystem;
import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.fsm.FaunaDirection;
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
		Random random = new Random();
		int chance  = random.nextInt(100);
		
		if(chance < 10)
		{
			FaunaDirection currentDirection = fauna.getDirection();
            if (currentDirection == FaunaDirection.LEFT || currentDirection == FaunaDirection.RIGHT) {
                fauna.setDirection(random.nextBoolean() ? FaunaDirection.UP : FaunaDirection.DOWN);
            } else if (currentDirection == FaunaDirection.UP || currentDirection == FaunaDirection.DOWN) {
                fauna.setDirection(random.nextBoolean() ? FaunaDirection.LEFT : FaunaDirection.RIGHT);
            }
		}



		fauna.moveForward();
		return false;
	}

}