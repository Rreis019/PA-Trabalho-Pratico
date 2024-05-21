package pt.isec.pa.javalife.model.fsm;
import pt.isec.pa.javalife.model.Ecosystem;
import pt.isec.pa.javalife.model.data.elements.Fauna;

public final class FaunaStateContext {
	private Fauna data;
	private IFaunaState state;
	private Ecosystem ecosystem;
	
	public FaunaStateContext(Ecosystem ecosystem,Fauna fauna_)
	{
		this.ecosystem = ecosystem;
		data = fauna_;
 		state = IFaunaState.createState(FaunaState.MOVING,this, this.ecosystem,data);
	}


	public FaunaState getState()
	{
		return state.getState();
	}


	void changeState(FaunaState newState){
		this.state = IFaunaState.createState(FaunaState.MOVING,this, this.ecosystem,data);
	}

	public boolean execute()
	{
		return this.state.execute();	
	}

}
