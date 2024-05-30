package pt.isec.pa.javalife.model.command;

import pt.isec.pa.javalife.model.Ecosystem;
import pt.isec.pa.javalife.model.data.elements.IElement;

public class RemoveElementCommand extends CommandAdapter{
    private IElement removedElement;

    public RemoveElementCommand(Ecosystem ecosystem, IElement element) {
        super(ecosystem);
        this.ecosystem = ecosystem;
        this.removedElement = element;
    }

    @Override
    public boolean execute() {
       ecosystem.removeElement(removedElement);
       ecosystem.updateRender();
       System.out.println(removedElement.getId() + "ID2");
       return true;
    }

    @Override
    public boolean undo() {
        if(removedElement != null){
            //ecosystem.addElementToRandomFreePosition(removedElement.getType()); --- Acho que iria criar um novo elemento
            ecosystem.addElement(removedElement);
            ecosystem.updateRender();
            System.out.println(removedElement.getId() + "ID1");
            return true;
        }
         return false;
    }

}
