package pt.isec.pa.javalife.model.command;

import pt.isec.pa.javalife.model.EcosystemManager;
import pt.isec.pa.javalife.model.data.elements.Element;
import pt.isec.pa.javalife.model.data.elements.IElement;

public class AddElementCommand implements ICommand{
    private EcosystemManager model;
    private Element element;
    private IElement addedElement;

    public AddElementCommand(EcosystemManager model, Element element) {
        this.model = model;
        this.element = element;
    }

    @Override
    public boolean execute() {
        addedElement = model.addElementToRandomFreePosition(element);
        model.renderUpdated();
        return true;
    }

    @Override
    public boolean undo() {
        if (addedElement != null) {
            model.removeElement(addedElement);
            model.renderUpdated();
            return true;
        }
        return false;
    }
}
