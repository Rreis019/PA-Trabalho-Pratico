package pt.isec.pa.javalife.model.fsm.states;

import pt.isec.pa.javalife.model.Ecosystem;
import pt.isec.pa.javalife.model.data.Area;
import pt.isec.pa.javalife.model.data.elements.Element;
import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.fsm.FaunaState;
import pt.isec.pa.javalife.model.fsm.FaunaStateAdapter;
import pt.isec.pa.javalife.model.fsm.FaunaStateContext;

import java.util.ArrayList;
import java.util.List;


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
				// Para encontrar a área livre ou com a flora mais próxima
				Area newArea = findFreeOrFloraAreaNear(fauna.getArea(), ecosystem);
				if (newArea != null) {
					fauna.setStrength(fauna.getStrength() - 25);
					ecosystem.addElement(Element.FAUNA, newArea.left(), newArea.top());
				}

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

	private Area findFreeOrFloraAreaNear(Area area, Ecosystem eco) {
		List<Area> adjacentAreas = getAdjacentAreas(area);
		for (Area adjacentArea : adjacentAreas) {
			if (!eco.isOutBounds(adjacentArea) && (eco.isAreaFree(adjacentArea) || eco.hasFlora(adjacentArea))) {
				return adjacentArea;
			}
		}
		return null;
	}

	private List<Area> getAdjacentAreas(Area area) {
		double size = area.bottom() - area.top();
		List<Area> adjacentAreas = new ArrayList<>();
		adjacentAreas.add(new Area(area.top(), area.left() - size, area.bottom(), area.left()));
		adjacentAreas.add(new Area(area.top(), area.right(), area.bottom(), area.right() + size));
		adjacentAreas.add(new Area(area.bottom(), area.left(), area.bottom() + size, area.right()));
		adjacentAreas.add(new Area(area.top() - size, area.left(), area.top(), area.right()));
		return adjacentAreas;
	}

}