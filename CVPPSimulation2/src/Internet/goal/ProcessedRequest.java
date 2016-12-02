package Internet.goal;

import Internet.task.ProcessRequest;
import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.Goal;

public class ProcessedRequest extends Goal {

	public ProcessedRequest() {
		super();
		
		add(new ProcessRequest(), null);
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
