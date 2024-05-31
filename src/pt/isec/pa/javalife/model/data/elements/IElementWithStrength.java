package pt.isec.pa.javalife.model.data.elements;

public sealed interface IElementWithStrength
    permits Fauna, Flora {
    double getStrength();
    void setStrength(double strength);
}