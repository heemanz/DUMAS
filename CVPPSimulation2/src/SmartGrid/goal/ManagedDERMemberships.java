package SmartGrid.goal;

import SmartGrid.task.ProcessDERsRegistration;
import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.Goal;

public class ManagedDERMemberships extends Goal {

	public ManagedDERMemberships() {
		super();
		
		this.add(new ProcessDERsRegistration(), null);
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
