package VPP.goal;

import VPP.task.InformCollectedActualProduction;
import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.Goal;

public class InformedCollectedActualProduction extends Goal {

	public InformedCollectedActualProduction() {
		super();
		
		add(new InformCollectedActualProduction(), null);
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
