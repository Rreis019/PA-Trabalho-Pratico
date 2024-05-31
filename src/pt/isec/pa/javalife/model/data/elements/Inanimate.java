package pt.isec.pa.javalife.model.data.elements;

import java.util.Random;

/**
 * Inanimate
 */
public non-sealed class Inanimate extends BaseElement
{
    static Random random = new Random();

 	public Inanimate(double positionX,double positionY) {
        super(Element.INANIMATE,positionX,positionY,(double)random.nextInt(20)  + 10,(double)random.nextInt(20)  + 10);
    }

}