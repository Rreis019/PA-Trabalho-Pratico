package pt.isec.pa.javalife.model.command;

interface ICommand {
    boolean execute();
    boolean undo();
}
