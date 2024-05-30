package pt.isec.pa.javalife.model.command;

import pt.isec.pa.javalife.model.Ecosystem;
import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.data.elements.Flora;
import pt.isec.pa.javalife.model.data.elements.IElement;

public class SetStrengthElementCommand extends CommandAdapter{
   // private Ecosystem ecosystem;
    private int elementId;
    private double newStrength;
    private double oldStrength;

    public SetStrengthElementCommand(Ecosystem ecosystem, int elementId, double newStrength) {
        super(ecosystem);
        this.ecosystem = ecosystem;
        this.elementId = elementId;
        this.newStrength = newStrength;
    }
    @Override
    public boolean execute() {
        IElement element = ecosystem.getElement(elementId);
        if (element != null) {
          if(element instanceof Fauna){
            oldStrength = ((Fauna)element).getStrength();
          }
          else if(element instanceof Flora){
            oldStrength = ((Flora)element).getStrength();
          }
          ecosystem.editElement(elementId, newStrength);
          return true;
        }
        return false;
    }

    @Override
    public boolean undo() {
        //Para restaurar a força anterior
        System.out.println(oldStrength + " e agora: " + newStrength); //---Falta notificar a ui para atualizar o slider
        if (oldStrength == newStrength) return false;
        ecosystem.editElement(elementId, oldStrength);
        //ecosystem.updateRender();
        return true;
    }
}
