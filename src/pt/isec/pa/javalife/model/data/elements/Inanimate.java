package pt.isec.pa.javalife.model.data.elements;

/**
 * Inanimate
 */
public non-sealed class Inanimate extends BaseElement
{
	public static final int size = 16;

 	public Inanimate(double positionX,double positionY) {
        super(Element.INANIMATE,positionX,positionY,size,size);
    }

    @Override
    public int getSize(){return Inanimate.size;}
}