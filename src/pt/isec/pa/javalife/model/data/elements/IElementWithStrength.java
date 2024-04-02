package pt.isec.pa.javalife.model.data.elements;
import pt.isec.pa.javalife.model.data.elements.Flora;
import pt.isec.pa.javalife.model.data.elements.Fauna;

public sealed interface IElementWithStrength
    permits Fauna, Flora {
    double getStrength();
    void setStrength(double strength);
}