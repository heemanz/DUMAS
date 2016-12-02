package SmartGrid.goal;

import SmartGrid.task.UseElectricity;
import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.Goal;

public class UsedElectricity extends Goal {

	public UsedElectricity() {
		super();
		
		this.add(new UseElectricity(), null);
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
