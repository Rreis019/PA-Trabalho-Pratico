package pt.isec.pa.javalife.model;

import  pt.isec.pa.javalife.model.gameengine.IGameEngineEvolve;
import  pt.isec.pa.javalife.model.gameengine.IGameEngine;
import  pt.isec.pa.javalife.model.data.elements.IElement;
import pt.isec.pa.javalife.model.data.elements.Inanimate;
import pt.isec.pa.javalife.model.fsm.Direction;
import pt.isec.pa.javalife.model.data.Area;
import pt.isec.pa.javalife.model.data.ElementsFactory;
import pt.isec.pa.javalife.model.data.elements.BaseElement;
import pt.isec.pa.javalife.model.data.elements.Element;
import  pt.isec.pa.javalife.model.data.elements.Fauna;
import  pt.isec.pa.javalife.model.data.elements.Flora;
import pt.isec.pa.javalife.model.memento.IMemento;
import pt.isec.pa.javalife.model.memento.Memento;

import java.beans.PropertyChangeListener;
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


public class Ecosystem implements Serializable, IGameEngineEvolve, IEcosystem {
    private static final long serialVersionUID = 1L;
    private ConcurrentMap<Integer, IElement> elements;
    int cacheLastElementId = -1;
    public int getLastElementId() { return cacheLastElementId;}    
    public int updateLastElementId() {
        int maxId = -1;

        for (Integer key : elements.keySet()) {
            if (key > maxId) {
                maxId = key;
            }
        }

        cacheLastElementId = maxId;
        return cacheLastElementId;
    }


    int totalTicks = 0;


    private final PropertyChangeSupport pcs; // Para o observable
    public static final String PROP_GAME_RENDER = "info_property";
    public static final String PROP_INSPECT = "inspect";


    private static boolean sunEventEffect = false;
    private int sunEventTick = 0;

    private int unitScale = 2;
    private int numUnitsX = 300;
    private int numUnitsY = 300;

    @SuppressWarnings("this-escape")
    public Ecosystem() {
        this.pcs = new PropertyChangeSupport(this);
        //elements = new HashSet<>();
        elements = new ConcurrentHashMap<>();
        //faunaStates = new HashMap<>();
    }


    public int getWidth(){return unitScale*numUnitsX;}
    public int getHeight(){return unitScale*numUnitsY;}
    public void setNumUnitsX(int numUnits_){numUnitsX = numUnits_;}
    public void setNumUnitsY(int numUnits_){numUnitsY = numUnits_;}

    /**
     * Retorna entidade mais proxima do elemento origem
     * @param origin A área de origem a partir da qual a proximidade é medida.
     * @param type Tipo de Elemento queremos procurar
     * @return IElement
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

    public ConcurrentMap<Integer, IElement> getElements()
    {
        return elements;
    }


    public int getTicks(){return totalTicks;}
    public void resetTicksCounter(){totalTicks = 0;}

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

    public void clearElements()
    {
        elements.clear();
    }
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


    public void updateRender()
    {
        pcs.firePropertyChange(PROP_GAME_RENDER,null,null);
    }

    public IElement getElement(int id) {
        pcs.firePropertyChange(PROP_INSPECT, null, null);
        return elements.get(id);
    }

   public void removeElement(IElement element_) {
        elements.remove(element_.getId());
    }
    public void addElement(IElement element_){
        elements.put(element_.getId(), element_);
    }

    public void addElement(Element type, double positionX, double positionY) {
        IElement ent = ElementsFactory.CreateElement(this, type, positionX, positionY);
        addElement(ent);
    }

    public void editElement(int elementId, double oldStrength) {
        IElement element = getElement(elementId);
        if (element != null) {
            if (element instanceof Fauna) {
                ((Fauna) element).setStrength(oldStrength);
            } else if (element instanceof Flora) {
                ((Flora) element).setStrength(oldStrength);
            }
        }
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
        IElement ent = ElementsFactory.CreateElement(this,type, 0, 0);

        int maxWidth = getWidth() - ent.getSize();
        int maxHeight = getHeight() - ent.getSize();

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
        addElement(ent);
        return ent;
    }

    @Override
     public void evolve(IGameEngine gameEngine, long currentTime) {
        // Iterar sobre os estados da fauna usando um iterador
        for (IElement element : elements.values()) {
            if(element.getType() == Element.FAUNA){((Fauna)element).getFSM().execute();}
            if(element.getType() == Element.FLORA){ ((Flora)element).evolve(this,currentTime);}
        }

        //Iterator<IElement> elementIterator = elements.iterator();
        //while (elementIterator.hasNext()) {
         //   IElement element = elementIterator.next();
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
        pcs.firePropertyChange(PROP_GAME_RENDER, null, null);   
        //totalTicks = totalTicks + 1;
        
    }

    public void applyStrenghtEvent(IElement element)
    {
        if(element.getType() == Element.FAUNA)
        {
            Fauna f = (Fauna)element; 
            f.setStrength(f.getStrength() + 50);
        }
    }

    public void applyHerbicideEvent(IElement element)
    {
        if(element.getType() == Element.FLORA)
        {
            Flora f = (Flora)element; 
            f.setStrength(-1337);
        }
    }

    public void applySunEvent(IElement element)
    {
        sunEventTick = 0;
        sunEventEffect = true;
    }

    public boolean isSunEventActive()
    {
        return sunEventEffect;
    }


    public void makeWallOfChina()
    {
        
        Inanimate top = new Inanimate(0, 0);
        Inanimate left = new Inanimate(0, 0);
        Inanimate right = new Inanimate(0, 0);
        Inanimate bottom = new Inanimate(0, 0);

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


    public boolean isOutBounds(Area area){

        if(area.left() < 0){return true;}
        else if(area.right() > getWidth()){return true;}
        if(area.top() < 0){return true;}
        else if(area.bottom() > getHeight()) {return true;}

        return false;
    }

    private void handleColisions()
    {
        ArrayList<Inanimate> inanimates = new ArrayList<>();
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


    @Override
    public IMemento save() {
        return new Memento(this);
    }

    @Override
    public void restore(IMemento memento) {
        Object snapshot = memento.getSnapShot();
        if (snapshot instanceof Ecosystem) {
            Ecosystem ecosystem = (Ecosystem) snapshot;
        }
    }

    //área para o Observable
    @Override
    public void addPropertyChangeListener(String prop,PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(prop,listener);
    }

    @Override
    public void removeObserver(String prop,PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);

    }
}
