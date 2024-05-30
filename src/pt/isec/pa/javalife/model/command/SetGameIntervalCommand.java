package pt.isec.pa.javalife.model.command;

import pt.isec.pa.javalife.model.Ecosystem;
import pt.isec.pa.javalife.model.gameengine.GameEngine;

public class SetGameIntervalCommand extends CommandAdapter{
    private long newInterval;
    private long oldInterval;
    private GameEngine gameEngine;

    public SetGameIntervalCommand(Ecosystem ecosystem, GameEngine gameEngine, long newInterval) {
        super(ecosystem);
        this.gameEngine = gameEngine;
        this.newInterval = newInterval;
        this.oldInterval = gameEngine.getInterval();
    }

    @Override
    public boolean execute() {
        oldInterval = gameEngine.getInterval();
        gameEngine.setInterval(newInterval);
        return true;
    }

    @Override
    public boolean undo() {
       gameEngine.setInterval(oldInterval);
       return true;
    }
}
