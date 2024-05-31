package pt.isec.pa.javalife.model.command;

import pt.isec.pa.javalife.model.gameengine.GameEngine;
import pt.isec.pa.javalife.model.Ecosystem;

public class SetIntervalCommand extends CommandAdapter {
	private long newInterval = 0;
	private long oldInterval = 0;
	GameEngine eng;
	public SetIntervalCommand(Ecosystem receiver_,GameEngine eng_,long newInterval_)
	{
		super(receiver_);
		eng = eng_;
		newInterval = newInterval_;
		oldInterval = eng.getInterval();
	}

	@Override
    public boolean execute() { 
    	eng.setInterval(newInterval);
    	return true;
    }

    @Override
    public boolean undo() {
    	eng.setInterval(oldInterval);
        return true;
    }

}