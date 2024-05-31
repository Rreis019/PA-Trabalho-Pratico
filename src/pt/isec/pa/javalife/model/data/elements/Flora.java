package pt.isec.pa.javalife.model.data.elements;

import pt.isec.pa.javalife.model.Ecosystem;
import pt.isec.pa.javalife.model.data.Area;

/**
 * Flora
 */
public non-sealed class Flora extends BaseElement  implements IElementWithStrength
{
    //config
    private static final double initialStrength = 50;
	private static final double incEnergy = 0.5;
    private static final int size = 13;
    double strenght = initialStrength;
    private int reproductionCount = 0;
    private boolean attemptedReproduction = false;

	 public Flora(double positionX,double positionY) {
        super(Element.FLORA,positionX,positionY,size,size);
    }

    public void evolve(Ecosystem eco, long currentTime) {
        if(getStrength() == 0){return;}
        if(eco.isSunEventActive()){this.setStrength(getStrength() + incEnergy * 2);}
        else{this.setStrength(getStrength() + incEnergy);}

        //"reproductionCount < 2 -> Este tipo de elemento apenas se pode  reproduzir duas vezes durante a sua vida"
        if (getStrength() >= 90 && reproductionCount < 2) {
            boolean reproduced = tryReproduce(eco);
            if (reproduced) {
                this.setStrength(60);
                reproductionCount++;
                attemptedReproduction = false;
            } else {
                attemptedReproduction = true;
            }
        }
    }

    private boolean tryReproduce(Ecosystem eco) {
        Area area = this.getArea();
        double[][] adjacentPositions = {
            {area.left() - size, area.top()}, //esquerda
            {area.left() + size, area.top()}, //direita
            {area.left(), area.bottom()}, //baixo
            {area.left(), area.top() - size}  //em cima
        };

        for (double[] pos : adjacentPositions) {
            Area newArea = new Area(pos[1], pos[0], pos[1] + size, pos[0] + size);
            if (!eco.isOutBounds(newArea) && eco.isAreaFree(newArea)) {
                eco.addElement(Element.FLORA, pos[0], pos[1]);
                return true;
            }
        }
        return false;
    }

    @Override
    public double getStrength(){
    	return strenght;
    }
    
    @Override
    public void setStrength(double strength_){
        strenght = strength_;
        if(strenght > 100){strenght = 100;}
        if(strenght < 0){strenght = 0;}
    }

    @Override
    public int getSize(){return Flora.size;}

}