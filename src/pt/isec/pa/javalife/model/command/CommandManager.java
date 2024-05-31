package pt.isec.pa.javalife.model.command;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * O CommandManager é responsável por gerenciar os comandos executados e fornecer suporte para desfazer e refazer operações.
 */
public class CommandManager implements Serializable {
    private Deque<ICommand> history;
    private Deque<ICommand> redoCmds;

    /**
     * Construtor da classe CommandManager.
     * Inicializa as estruturas de dados para o histórico de comandos e de comandos desfeitos.
     */
    public CommandManager() {
        history = new ArrayDeque<>();
        redoCmds = new ArrayDeque<>();
    }

/**
     * Executa um comando e o adiciona ao histórico.
     * 
     * @param cmd O comando a ser executado.
     * @return true se o comando foi executado com sucesso e adicionado ao histórico, caso contrário false.
     */
    public boolean invokeCommand(ICommand cmd) {
        redoCmds.clear();
        if (cmd.execute()) {
            history.push(cmd);
            return true;
        }
        history.clear();
        return false;
    }

    /**
     * Desfaz o último comando executado.
     * 
     * @return true se o comando foi desfeito com sucesso, caso contrário false.
     */
    public boolean undo() {
        if (history.isEmpty())
            return false;
        ICommand cmd = history.pop();
        cmd.undo();
        redoCmds.push(cmd);
        return true;
    }

    /**
     * Refaz o último comando desfeito.
     * 
     * @return true se o comando foi refeito com sucesso, caso contrário false.
     */
    public boolean redo() {
        if (redoCmds.isEmpty())
            return false;
        ICommand cmd = redoCmds.pop();
        cmd.execute();
        history.push(cmd);
        return true;
    }

   /**
     * Verifica se há comandos que podem ser desfeitos.
     * 
     * @return true se houver comandos no histórico para desfazer, caso contrário false.
     */
    public boolean hasUndo() {
        return !history.isEmpty();
    }

   /**
     * Verifica se há comandos que podem ser refeitos.
     * 
     * @return true se houver comandos no histórico de comandos desfeitos para refazer, caso contrário false.
     */
    public boolean hasRedo() {
        return !redoCmds.isEmpty();
    }
}