package pt.isec.pa.javalife.model.fsm.states;

import pt.isec.pa.javalife.model.Ecosystem;
import pt.isec.pa.javalife.model.data.Area;
import pt.isec.pa.javalife.model.data.elements.Element;
import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.fsm.FaunaState;
import pt.isec.pa.javalife.model.fsm.FaunaStateAdapter;
import pt.isec.pa.javalife.model.fsm.FaunaStateContext;


public class ReproduceState extends FaunaStateAdapter{
	int targetFaunaID = -1;
	int ticks = 0;

	public ReproduceState(FaunaStateContext context, Ecosystem ecosystem, Fauna fauna_)
	{
		super(context,ecosystem, fauna_);
	}

 	@Override
    public FaunaState getState() {
        return FaunaState.REPRODUCE;
    }

	@Override
	public boolean execute() {
		Fauna strongestFauna = ecosystem.getStrongestFauna(fauna.getId()); 
		if(strongestFauna == null){
			ticks = 0;
			changeState(FaunaState.MOVING);
			return false;
		}

		if(Area.distance(fauna.getArea(),strongestFauna.getArea()) > 32){
			ticks = 0;
			changeState(FaunaState.MOVING);
			return false;
		}

		if(fauna.moveTo(ecosystem.getElements(), strongestFauna))
		{
			if(ticks == 10)
			{
				fauna.setStrength(fauna.getStrength() - 25);
				
				ecosystem.addElement(Element.FAUNA,fauna.getArea().left(),fauna.getArea().top());				
				changeState(FaunaState.SEARCH_FOOD);
			}
			if(targetFaunaID == strongestFauna.getId())
			{
				ticks = ticks + 1;
			}else{
				targetFaunaID = strongestFauna.getId();
				ticks = 1;
			}
		}

		return false;
	}

}