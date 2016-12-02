package VPP.goal;

import VPP.task.ProvideDERsIncentive;
import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.Goal;

public class EncouragedDERsParticipation extends Goal {

	public EncouragedDERsParticipation() {
		super();
		
		add(new ProvideDERsIncentive(), null);
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
