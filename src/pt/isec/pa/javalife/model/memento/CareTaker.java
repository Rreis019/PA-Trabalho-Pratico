package pt.isec.pa.javalife.model.memento;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * A classe CareTaker é responsável por gerenciar o histórico de estados do padrão Memento,
 * permitindo salvar, restaurar e realizar replay de momentos anteriores de um objeto originator.
 */
public class CareTaker implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final IMementoOriginator originator;

    private Deque<Memento> history = new ArrayDeque<>();
    private Deque<Memento> redoHist = new ArrayDeque<>();

    /**
     * Construtor da classe CareTaker.
     * 
     * @param originator O originator associado a este CareTaker.
     */
    public CareTaker(IMementoOriginator originator) {
        this.originator = originator;
    }

      /**
     * Salva o estado atual do originator no histórico.
     * 
     * @throws IOException Se ocorrer um erro de E/S ao salvar o estado.
     */
    public void save() throws IOException {
        history.push(originator.getMemento());
        if (history.size()>10)
            history.removeFirst();

        redoHist.push(originator.getMemento());
    }

     /**
     * Limpa o histórico.
     */
    public void cleanStack(){
        history.clear();
    }

     /**
     * Retorna o tamanho atual do histórico.
     * 
     * @return O tamanho atual do histórico.
     */
    public int size(){
        return history.size();
    }

     /**
     * Restaura o estado anterior do originator a partir do histórico.
     * 
     * @throws Exception Se ocorrer um erro ao restaurar o estado anterior.
     */
    public void restore() throws Exception{
        if (history.isEmpty())
            return;
            Memento anterior = history.pop();
            originator.setMemento(anterior);

    }

    public void startReplay() throws Exception {
        Memento next = redoHist.removeLast();
        originator.setMemento(next);
    }
}