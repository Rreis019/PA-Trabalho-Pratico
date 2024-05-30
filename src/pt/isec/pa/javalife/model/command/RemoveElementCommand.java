package pt.isec.pa.javalife.model.command;

import pt.isec.pa.javalife.model.EcosystemManager;
import pt.isec.pa.javalife.model.data.elements.Element;
import pt.isec.pa.javalife.model.data.elements.IElement;

public class RemoveElementCommand implements ICommand{
    private EcosystemManager model;
   // private Element element;
    private IElement removedElement;


    public RemoveElementCommand(EcosystemManager model, IElement element) {
        this.model = model;
        this.removedElement = element;

    }

    @Override
    public boolean execute() {
       model.setRemovedElement(removedElement); //Para armazenar o elemento apagado
       model.removeElement(removedElement);
       return true;
    }

    @Override
    public boolean undo() {
        if(removedElement != null){
            //model.addElementToRandomFreePosition(removedElement.getType()); --- Acho que iria criar um novo elemento
            model.addElement(model.getRemovedElement());
            model.renderUpdated();
            return true;
        }
         return false;
    }
}
