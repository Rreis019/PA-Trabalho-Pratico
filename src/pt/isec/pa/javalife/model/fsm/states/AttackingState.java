package pt.isec.pa.javalife.model.fsm.states;

import java.io.Serializable;

import pt.isec.pa.javalife.model.Ecosystem;
import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.fsm.FaunaState;
import pt.isec.pa.javalife.model.fsm.FaunaStateAdapter;
import pt.isec.pa.javalife.model.fsm.FaunaStateContext;


public class AttackingState extends FaunaStateAdapter{


	public AttackingState(FaunaStateContext context, Ecosystem ecosystem, Fauna fauna_)
	{
		super(context,ecosystem, fauna_);
	}

 	@Override
    public FaunaState getState() {
        return FaunaState.ATTACKING;
    }

	@Override
	public boolean execute() {
	 	Fauna targetFauna = (Fauna) ecosystem.getWeakestFauna(fauna.getId());  
	 	if(targetFauna == null){changeState(FaunaState.MOVING); return false;}
	    fauna.setStrength(fauna.getStrength() - 10);

	    FaunaState tState = targetFauna.getState(); 
	    if(tState == FaunaState.SEARCH_FOOD || tState == FaunaState.EATING || tState == FaunaState.ATTACKING)
	    {
	    	if(fauna.getStrength() < targetFauna.getStrength())
	    	{
	    		targetFauna.setStrength(targetFauna.getStrength() + fauna.getStrength());
	    		fauna.setStrength(0);
	    	}else{
				fauna.setStrength(targetFauna.getStrength() + fauna.getStrength());
	    		targetFauna.setStrength(0);
	    	}
	    }
	    if (fauna.getStrength() > 0) {
	        // Fauna atacante sobreviveu
	        fauna.setStrength(fauna.getStrength() + targetFauna.getStrength());
	        targetFauna.setStrength(0); 
	    } else {
	        // Fauna atacante morreu
	        targetFauna.setStrength(targetFauna.getStrength() + fauna.getStrength() + 10);
	        fauna.setStrength(0);
	        //ecosystem.removeElement(fauna);  // Remove a fauna atacante do ecossistema
	    }

	    changeState(FaunaState.MOVING);
		return false;
	}

}