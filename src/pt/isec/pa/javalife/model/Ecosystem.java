package pt.isec.pa.javalife.model;

import pt.isec.pa.javalife.model.data.elements.*;
import  pt.isec.pa.javalife.model.gameengine.IGameEngineEvolve;
import pt.isec.pa.javalife.model.memento.IMementoOriginator;
import pt.isec.pa.javalife.model.memento.Memento;
import  pt.isec.pa.javalife.model.gameengine.IGameEngine;
import pt.isec.pa.javalife.model.fsm.Direction;
import pt.isec.pa.javalife.model.data.Area;
import pt.isec.pa.javalife.model.data.ElementsFactory;




import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.Locale;

/**
 * Representa um ecossistema que contém vários elementos (fauna, flora, inanimados).
 * Implementa Serializable, IGameEngineEvolve e IMementoOriginator para suportar 
 * funcionalidades de serialização, evolução do jogo e memento.
 */
public class Ecosystem implements Serializable, IGameEngineEvolve , IMementoOriginator {
    private ConcurrentMap<Integer, IElement> elements;
    int cacheLastElementId = -1;
    private double faunaMovementEnergy = 0.5;
    private double damageToFlora = 1;
    private final PropertyChangeSupport pcs; // Para o observable

    /**
     * Retorna o valor do dano que fauna provoca à flora.
     * @return Valor do dano à flora.
     */
    public double getDamageToFlora(){return damageToFlora;}


    /**
     * Retorna a energia de movimento gasta por tick pela fauna
     * @return Gasto de Energia movimento da fauna.
     */
    public double getFaunaMovementEnergy() {return faunaMovementEnergy;}

    /**
     * Define um novo valor para o dano que fauna provoca à flora..
     * @param newValue Novo valor para o dano à flora.
     */
    public void setDamageToFlora(double newValue){damageToFlora = newValue;}

    /**
     * Define um novo valor para a energia gasta movimento da fauna.
     * @param newValue Novo valor para a energia de movimento da fauna.
     */
    public void setFaunaMovementEnergy(double newValue){faunaMovementEnergy = newValue;}

     /**
     * Obtém o ID do último elemento adicionado ao ecossistema.
     * @return ID do último elemento.
     */
    public int getLastElementId() {
        int maxId = -1;

        for (Integer key : elements.keySet()) {
            if (key > maxId) {
                maxId = key;
            }
        }

        cacheLastElementId = maxId;
        return cacheLastElementId;
    }


    @Override
    public Memento getMemento() throws IOException {
         return new Memento(new Object[]{elements,faunaMovementEnergy,damageToFlora});
    }

    @Override
    public void setMemento(Memento m) throws IOException, ClassNotFoundException {
        Object[] objects = (Object[]) m.getSnapShot();
        this.elements = (ConcurrentMap<Integer, IElement>) objects[0];
        this.faunaMovementEnergy = (double) objects[1];
        this.damageToFlora = (double) objects[2];
    }

    private static boolean sunEventEffect = false;
    private int sunEventTick = 0;

    private int unitScale = 2;
    private int numUnitsX = 300;
    private int numUnitsY = 300;

    @SuppressWarnings("this-escape")
    public Ecosystem(PropertyChangeSupport pcs_) {
        elements = new ConcurrentHashMap<>();
        pcs = pcs_;
    }

    /**
     * Obtém a largura do ecossistema.
     * @return Largura do ecossistema.
     */
    public int getWidth(){return unitScale*numUnitsX;}

    /**
     * Obtém a altura do ecossistema.
     * @return Altura do ecossistema.
     */
    public int getHeight(){return unitScale*numUnitsY;}
    public void setNumUnitsX(int numUnits_){numUnitsX = numUnits_;}
    public void setNumUnitsY(int numUnits_){numUnitsY = numUnits_;}

     /**
     * Retorna a entidade mais próxima do elemento de origem.
     * @param origin A área de origem a partir da qual a proximidade é medida.
     * @param type Tipo de elemento que queremos procurar.
     * @return Elemento mais próximo do tipo especificado.
     */
    public IElement getClossestElement(Area origin,Element type)
    {
        IElement ret = null;
        double maxDistance = 99999.0f;
        for (IElement ent : getElements().values() ) {
            if(ent.getType() == type){
                double distance = Area.distance(origin, ent.getArea());
                if(distance < maxDistance){
                    maxDistance = distance;
                    ret = ent;
                }

            }
        }
        return ret;
    }

    /**
     * Obtém os elementos do ecossistema.
     * @return Mapa dos elementos do ecossistema.
     */
    public ConcurrentMap<Integer, IElement> getElements()
    {
        return elements;
    }


    /**
     * Obtém a fauna mais fraca, ignorando o ID especificado.
     * @param ignoreID ID a ser ignorado.
     * @return A fauna mais fraca.
     */
    public Fauna getWeakestFauna(int ignoreID)
    {
        double str = 1000;
        Fauna weakestFauna = null;
        for (IElement e : getElements().values()) {
            if(e.getType() == Element.FAUNA && e.getId() != ignoreID){
                Fauna f = (Fauna)e;
                if(f.getStrength() < str){
                    str = f.getStrength();
                    weakestFauna = f;
                }
            }
        }
        return weakestFauna;
    }

    /**
     * Remove todos os elementos do ecossistema.
     */
    public void clearElements()
    {
        elements.clear();
    }

     /**
     * Obtém a fauna mais forte, ignorando o ID especificado.
     * @param ignoreID O ID a ser ignorado.
     * @return A fauna mais forte.
     */
    public Fauna getStrongestFauna(int ignoreID)
    {
        double str = 0;
        Fauna strongestFauna = null;
        for (IElement e : getElements().values()) {
            if(e.getType() == Element.FAUNA && e.getId() != ignoreID){
                Fauna f = (Fauna)e;
                if(f.getStrength() > str){
                    str = f.getStrength();
                    strongestFauna = f;
                }


            }
        }
        return strongestFauna;
    }

    /**
     * Obtém um elemento pelo seu ID.
     * @param id ID do elemento.
     * @return Elemento correspondente ao ID.
     */
    public IElement getElement(int id) {
        return elements.get(id);
    }

     /**
     * Remove um elemento do ecossistema.
     * @param element_ Elemento a ser removido.
     */
    public void removeElement(IElement element_) {
        if(element_.isReadOnly()){return;}
        elements.remove(element_.getId());
    }

     /**
     * Remove um elemento pelo seu ID.
     * @param id ID do elemento a ser removido.
     */
    public void removeElement(int id) {
        elements.remove(id);
    }

     /**
     * Adiciona um elemento ao ecossistema.
     * @param element_ Elemento a ser adicionado.
     * @return true se o elemento foi adicionado, false caso contrário.
     */
    public void addElement(IElement element_){
        elements.put(element_.getId(), element_);
    }

    public void addElement(Element type, double positionX, double positionY) {
        IElement ent = ElementsFactory.CreateElement(this, type, positionX, positionY);
        //elements.put(ent.getId(), ent);
        addElement(ent);
    }

    public boolean isAreaFree(Area area) {
        for (IElement element : elements.values()) {
            if (element.getArea().intersects(area)) {
                return false;
            }
        }
        return true;
    }

    public IElement addElementToRandomFreePosition(Element type) {
        Random random = new Random();

        double positionX = 0;
        double positionY = 0;
        IElement ent;

        switch (type) {
            case INANIMATE:
                ent = new Stone(positionX, positionY);
                break;
            case FLORA:
                ent = new Grass(positionX, positionY);
                break;
            case FAUNA:
               ent = new Animal(this, positionX, positionY);
                break;
            default:
                throw new IllegalArgumentException("Tipo de elemento desconhecido: " + type);
        }

        int maxWidth = (int) (getWidth() - ent.getArea().right() - ent.getArea().left());
        int maxHeight = (int) (getHeight() - ent.getArea().bottom() - ent.getArea().top());

        boolean foundEmptyPosition = false;

        while (!foundEmptyPosition) {
            ent.setPosition(random.nextInt(maxWidth), random.nextInt(maxHeight));

            boolean intersects_ = false;

            for (IElement e : elements.values()) {
                if (e.getType() == Element.INANIMATE && ent.getArea().intersects(e.getArea())) {
                    intersects_ = true;
                    break; // Sai do loop assim que encontra uma interseção
                }
            }

            if (!intersects_) {foundEmptyPosition = true;}
        }

        elements.put(ent.getId(),ent);
        return ent;
    }

    /**
     * Chamado a cada X tempo definido no game engine
     * Atualiza todos os eventos e remove os elementos mortos e lida com as colissoes
     * @param gameEngine Motor do jogo utilizado para a evolução.
     * @param currentTime Tempo atual do jogo.
     */
    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        // Iterar sobre os estados da fauna com o iterador
        for (IElement element : elements.values()) {
            if(element.getType() == Element.FAUNA){((Fauna)element).getFSM().execute();}
            if(element.getType() == Element.FLORA){ ((Flora)element).evolve(this,currentTime);}
        }

        for (IElement element : elements.values()) {
            if (element.getType() == Element.FAUNA) {
                if (((Fauna) element).getStrength() <= 0) { removeElement(element); }
            } else if (element.getType() == Element.FLORA) {
                if (((Flora) element).getStrength() <= 0) { removeElement(element); }//elementIterator.remove(); }
            }
        }

        if (sunEventEffect && sunEventTick == 10){sunEventEffect = false; sunEventTick = 0;}
        else if(sunEventEffect){
            sunEventTick = sunEventTick + 1;
        }

        handleColisions();
        pcs.firePropertyChange(EcosystemManager.PROP_STATE, null, null);
    }

    /**
     * Aplica um evento de aumento de força a um elemento.
     * 
     * @param element Elemento ao qual o evento será aplicado.
     */
    public void applyStrenghtEvent(IElement element)
    {
        if(element.getType() == Element.FAUNA)
        {
            Fauna f = (Fauna)element; 
            f.setStrength(f.getStrength() + 50);
        }
    }

    /**
     * Aplica um evento de herbicida a um elemento.
     * 
     * @param element Elemento ao qual o evento será aplicado.
     */
    public void applyHerbicideEvent(IElement element)
    {
        if(element.getType() == Element.FLORA)
        {
            Flora f = (Flora)element; 
            f.setStrength(-1337);
        }
    }

    /**
     * Aplica um evento de sol a um elemento.
     * 
     * @param element Elemento ao qual o evento será aplicado.
     */
    public void applySunEvent(IElement element)
    {
        sunEventTick = 0;
        sunEventEffect = true;

    }

    /**
     * Verifica se o evento de sol está ativo.
     * 
     * @return true se o evento de sol estiver ativo, false caso contrário.
     */
    public boolean isSunEventActive()
    {
        return sunEventEffect;
    }

    /**
     * Cria uma muralha a volta do ecossistema
     */
    public void makeWallOfChina()
    {
        Inanimate top = new Inanimate(0, 0);
        Inanimate left = new Inanimate(0, 0);
        Inanimate right = new Inanimate(0, 0);
        Inanimate bottom = new Inanimate(0, 0);
        top.setReadonly(true);
        left.setReadonly(true);
        right.setReadonly(true);
        bottom.setReadonly(true);

        int wallThickness = 4;

        top.setArea(0, 0, this.getWidth(), wallThickness);//----
        left.setArea(0,wallThickness, wallThickness, this.getHeight() - wallThickness*2);

        right.setArea(this.getWidth() - wallThickness,wallThickness, wallThickness, getHeight() - wallThickness*2);
        bottom.setArea(0,this.getHeight() - wallThickness, getWidth(), wallThickness);

        addElement(top);
        addElement(left);
        addElement(right);
        addElement(bottom);
    }

    /**
     * Verifica se uma área está fora dos limites do ecossistema.
     * 
     * @param area Área a ser verificada.
     * @return true se a área estiver fora dos limites, false caso contrário.
     */
    public boolean isOutBounds(Area area){

        if(area.left() < 0){return true;}
        else if(area.right() > getWidth()){return true;}
        if(area.top() < 0){return true;}
        else if(area.bottom() > getHeight()) {return true;}

        return false;
    }

    /**
     * Lida com colisões entre elementos do ecossistema.
     */
    private void handleColisions()
    {
        ArrayList<Inanimate> inanimates = new ArrayList<>(); 
        //for (IElement element_ : elements) {
        for (IElement element_ : elements.values()) {
            if(element_.getType() == Element.INANIMATE){
                inanimates.add((Inanimate)element_);
            }
        }

        for (IElement element_ : elements.values()) {
            if(element_.getType() == Element.FAUNA){
                Fauna f = (Fauna)element_;
                Direction direction_ = f.getDirection(); 

                for (Inanimate ina : inanimates) {
                    Area sol = f.getArea().solveColision(direction_, ina.getArea());
                    if(sol.left() != -1){
                        f.setArea(sol);
                        f.reverseDirection();
                        break;
                    }
                }
            }
        }
    }

    /**
     * Exporta os a tipo,força e area dos elementos do ecossistema para um ficheiro CSV.
     * 
     * @param filepath Caminho do ficheiro CSV.
     * @return true se a exportação foi bem-sucedida, false caso contrário.
     */
    public boolean exportToCSV(String filepath) 
    {
        BufferedWriter writer = null;
        try 
        {
            writer = new BufferedWriter(new FileWriter(filepath));
            writer.write("Type,Strength,PositionX,PositionY,Bottom,Right\n");
            //for (IElement element : elements) {
            for (IElement element : elements.values()) 
            {
                String type = element.getType().toString();
                double strength = 0;
                if (element instanceof Fauna) {
                    strength = ((Fauna) element).getStrength();
                } else if (element instanceof Flora) {
                    strength = ((Flora) element).getStrength();
                }
                Area area = element.getArea();
                //Locale.US -> para o float serem com ponto ex: 1.4
                writer.write(String.format(Locale.US, "%s;%.2f;%.0f;%.0f;%.0f;%.0f\n", type, strength, area.left(), area.top(),area.bottom(),area.right()));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    // Erro fechar o arquivo
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Importa elementos para o ecossistema a partir de um ficheiro CSV.
     * 
     * @param filepath Caminho do ficheiro CSV.
     * @return true se a importação foi bem-sucedida, false caso contrário.
     */
    public boolean importToCSV(String filepath) 
    {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(filepath));
            // Ignora a primeira linha, que é o cabeçalho
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 6) {
                    String typeStr = parts[0];
                    double strength = Double.parseDouble(parts[1]);
                    double positionX = Double.parseDouble(parts[2]);
                    double positionY = Double.parseDouble(parts[3]);
                    double bottom = Double.parseDouble(parts[4]);
                    double right = Double.parseDouble(parts[5]);
                    Element type = Element.valueOf(typeStr);
                    Area area = new Area(positionY, positionX, bottom, right);

                    System.out.println("fauna " + type + "\n");

                    // Verifica se a área está dentro dos limites da simulação
                    if (!isOutBounds(area)) {
                        // Verifica se a área do elemento se sobrepõe a algum elemento existente
                        boolean overlap = false;
                        //for (IElement existingElement : elements) {
                        for (IElement existingElement : elements.values()) {
                            if (existingElement.getArea().intersects(area)) {
                                if(type == Element.FAUNA){

                                }else{
                                    overlap = true;
                                    break;
                                }
                            }
                        }
                        // Se não houver sobreposição, adiciona o elemento à simulação
                        if (!overlap) {
                            IElement element = ElementsFactory.CreateElement(this, type, positionX, positionY); // Aqui você precisa substituir o "null" pelo contexto correto
                            ((BaseElement)element).setArea(area);


                            if (element.getType() == Element.FAUNA) {
                                ((Fauna) element).setStrength(strength);

                            } else if (element.getType() == Element.FLORA) {
                                ((Flora) element).setStrength(strength);
                            }
                            addElement(element);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return true;
    }
}
