package VPP.goal;

import VPP.task.ProcessDERsSubscription;
import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.Goal;

public class RegisteredDER extends Goal {

	public RegisteredDER() {
		super();
		
		add(new ProcessDERsSubscription(), null);
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
