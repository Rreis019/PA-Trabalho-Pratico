package pt.isec.pa.javalife.model.command;

import pt.isec.pa.javalife.model.EcosystemManager;
import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.data.elements.Flora;
import pt.isec.pa.javalife.model.data.elements.IElement;

public class EditElementCommand implements ICommand{
    private EcosystemManager model;
    private int elementId;
    private double newStrength;
    private double oldStrength;

    public EditElementCommand(EcosystemManager model, int elementId, double newStrength) {
        this.model = model;
        this.elementId = elementId;
        this.newStrength = newStrength;
    }
    @Override
    public boolean execute() {
        IElement element = model.getElement(elementId);
        if (element != null) {
          if(element instanceof Fauna){
            oldStrength = ((Fauna)element).getStrength();
          }
          else if(element instanceof Flora){
            oldStrength = ((Flora)element).getStrength();
          }
          model.editElement(elementId, newStrength);
          return true;
        }
        return false;
    }

    @Override
    public boolean undo() {
        //Para restaurar a força anterior
        if (oldStrength == newStrength) return false;
        model.editElement(elementId, oldStrength);
        return true;
    }
}
