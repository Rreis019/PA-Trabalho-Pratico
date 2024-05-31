package pt.isec.pa.javalife.model.fsm.states;

import pt.isec.pa.javalife.model.Ecosystem;
import pt.isec.pa.javalife.model.data.Area;
import pt.isec.pa.javalife.model.data.elements.Element;
import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.data.elements.Flora;
import pt.isec.pa.javalife.model.data.elements.IElement;
import pt.isec.pa.javalife.model.fsm.FaunaState;
import pt.isec.pa.javalife.model.fsm.FaunaStateAdapter;
import pt.isec.pa.javalife.model.fsm.FaunaStateContext;

public class EatingState extends FaunaStateAdapter {

	public EatingState(FaunaStateContext context, Ecosystem ecosystem, Fauna fauna_)
	{
		super(context,ecosystem, fauna_);
	}

 	@Override
    public FaunaState getState() {
        return FaunaState.EATING;
    }

	@Override
	public boolean execute() {
		IElement closestFlora = ecosystem.getClossestElement(fauna.getArea(), Element.FLORA);
		if(closestFlora == null)
		{
			if(fauna.getStrength() < 80){changeState(FaunaState.SEARCH_FOOD);}
			else{ changeState(FaunaState.MOVING);}
		
			return false;
		}

		double distance = Area.distance(closestFlora.getArea(), fauna.getArea());
		if(distance > fauna.getSize()){changeState(FaunaState.SEARCH_FOOD);}
		
	 	
	 	Flora flora = (Flora)closestFlora;
		
		double str = flora.getStrength() >= ecosystem.getDamageToFlora() ? ecosystem.getDamageToFlora() : flora.getStrength();

		flora.setStrength(flora.getStrength() - str);
		fauna.setStrength(fauna.getStrength() + str);

		if(fauna.getStrength() == 100){changeState(FaunaState.MOVING);}
		return false;
	}

}