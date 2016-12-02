package Internet.app.goal;

import Internet.app.task.ProcessInternetMessage;
import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.Goal;

public class ProcessedInternetMessage extends Goal {

	public ProcessedInternetMessage() {
		super();
		
		add(new ProcessInternetMessage(), null);
	}

	@Override
	public boolean shouldPursue(Agent agent) {
		return true;
	}

	@Override
	public boolean isAchieved() {
		return false;
	}

}
