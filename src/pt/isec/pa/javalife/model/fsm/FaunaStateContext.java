package pt.isec.pa.javalife.model.fsm;
import java.io.Serializable;

import pt.isec.pa.javalife.model.Ecosystem;
import pt.isec.pa.javalife.model.data.elements.Fauna;


/**
 * O FaunaStateContext é responsável por gerenciar o estado de uma entidade Fauna e coordenar a execução de suas ações de acordo com o estado atual.
 */
public final class FaunaStateContext implements Serializable {
	private Fauna data;
	private IFaunaState state;
	private Ecosystem ecosystem;
		

	  /**
     * Construtor da classe FaunaStateContext.
     * Inicializa o contexto do estado da entidade Fauna com o estado inicial de MOVING.
     * 
     * @param ecosystem O ecossistema ao qual a entidade Fauna pertence.
     * @param fauna_ Os dados da entidade Fauna.
     */
	public FaunaStateContext(Ecosystem ecosystem,Fauna fauna_)
	{
		this.ecosystem = ecosystem;
		data = fauna_;
 		state = IFaunaState.createState(FaunaState.MOVING,this, this.ecosystem,data);
	}


	  /**
     * Obtém o estado atual da entidade Fauna.
     * 
     * @return O estado atual da entidade Fauna.
     */
	public FaunaState getState()
	{
		return state.getState();
	}


	    /**
     * Altera o estado atual da entidade Fauna.
     * 
     * @param iFaunaState O novo estado da entidade Fauna.
     */
	void changeState(IFaunaState iFaunaState){
		this.state = iFaunaState;
	}

	    /**
     * Executa a ação correspondente ao estado atual da entidade Fauna.
     * 
     * @return true se a ação foi executada com sucesso, caso contrário false.
     */
	public boolean execute()
	{
		return this.state.execute();	
	}

}
