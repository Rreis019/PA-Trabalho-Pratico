package pt.isec.pa.javalife.model.fsm;
import pt.isec.pa.javalife.model.Ecosystem;
import pt.isec.pa.javalife.model.data.elements.Fauna;

public final class FaunaStateContext {
	private Fauna data;
	private IFaunaState state;
	private Ecosystem ecosystem;
	
	public FaunaStateContext(Ecosystem ecosystem)
	{
		this.ecosystem = ecosystem;
 		state = IFaunaState.createState(FaunaState.MOVING,this, this.ecosystem);
	}


	public FaunaState getState()
	{
		return state.getState();
	}


	void changeState(IFaunaState newState){
		this.state = newState;
	}

	boolean execute()
	{
		return this.state.execute();	
	}

}
