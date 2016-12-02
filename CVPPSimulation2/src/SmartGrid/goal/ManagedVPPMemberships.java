package SmartGrid.goal;

import SmartGrid.task.ProcessVPPsRegistration;
import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.Goal;

public class ManagedVPPMemberships extends Goal {

	public ManagedVPPMemberships() {
		super();
		
		this.add(new ProcessVPPsRegistration(), null);
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
