package pt.isec.pa.javalife.model.command;

import pt.isec.pa.javalife.model.Ecosystem;
import pt.isec.pa.javalife.model.data.elements.Element;
import pt.isec.pa.javalife.model.data.elements.IElement;

public class AddElementCommand extends CommandAdapter{
    private Element element;
    private IElement addedElement;

    public AddElementCommand(Ecosystem ecosystem, Element element) {
        super(ecosystem);
        this.element = element;
    }

    @Override
    public boolean execute() {
        addedElement = ecosystem.addElementToRandomFreePosition(element);
        ecosystem.updateRender();
        return true;
    }

    @Override
    public boolean undo() {
        if (addedElement != null) {
            ecosystem.removeElement(addedElement);
            ecosystem.updateRender();
            return true;
        }
        return false;
    }
}
