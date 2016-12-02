package VPP.goal;

import VPP.task.InformCollectedExpectedProduction;
import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.Goal;

public class InformedCollectedExpectedProduction extends Goal {

	public InformedCollectedExpectedProduction() {
		super();
		
		add(new InformCollectedExpectedProduction(), null);
	}

	@Override
	public boolean shouldPursue(Agent agent) {
		return false;
	}

	@Override
	public boolean isAchieved() {
		return true;
	}

}
