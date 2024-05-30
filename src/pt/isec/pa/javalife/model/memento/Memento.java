package pt.isec.pa.javalife.model.memento;
import java.io.*;

/**
 * A classe Memento representa um momento específico do estado de um objeto.
 * Ele encapsula o estado do objeto em forma de bytes, permitindo que seja salvo e restaurado posteriormente.
 */
public class Memento implements Serializable {
    private final byte[] snapshot;

      /**
     * Construtor da classe Memento.
     * 
     * @param obj O objeto cujo estado será encapsulado neste memento.
     * @throws IOException Se ocorrer um erro de E/S ao criar o memento.
     */
    public Memento(Object obj) throws IOException{
        ByteArrayOutputStream baos;
        ObjectOutputStream oos = null;

        try{
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            snapshot = baos.toByteArray();
        }finally {
            if (oos!=null)
                oos.close();
        }
    }

     /**
     * Obtém o snapshot do estado encapsulado neste memento.
     * 
     * @return O objeto cujo estado está encapsulado neste memento.
     * @throws IOException            Se ocorrer um erro de E/S ao ler o snapshot.
     * @throws ClassNotFoundException Se a classe do objeto não puder ser encontrada ao ler o snapshot.
     */
    public Object getSnapShot() throws IOException, ClassNotFoundException{
        ObjectInputStream ois = null;
        if (snapshot==null)
            return null;
        try{
            ois = new ObjectInputStream(new ByteArrayInputStream(snapshot));
            return ois.readObject();
        }finally {
            if (ois!=null)
                ois.close();
        }
    }
}