package pt.isec.pa.javalife.model.command;

import pt.isec.pa.javalife.model.Ecosystem;
import pt.isec.pa.javalife.model.data.elements.IElement;

public class SetPositionElementCommand extends CommandAdapter{
    private int elementId;
    private double newX;
    private double newY;
    private double oldX;
    private double oldY;
    public SetPositionElementCommand(Ecosystem ecosystem, int elementId, double newX, double newY) {
        super(ecosystem);
        this.elementId = elementId;
        this.newX = newX;
        this.newY = newY;
    }
    @Override
    public boolean execute() {
        IElement element = ecosystem.getElement(elementId);
        if (element != null) {
            oldX = element.getPositionX();
            oldY = element.getPositionY();
            element.setPosition(newX, newY);
            ecosystem.updateRender();
            return true;
        }
        return false;
    }

    @Override
    public boolean undo() {
        IElement element = ecosystem.getElement(elementId);
        if (element != null) {
            element.setPosition(oldX, oldY);
            ecosystem.updateRender();
            return true;
        }
        return false;
    }
}




