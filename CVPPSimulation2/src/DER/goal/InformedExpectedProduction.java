package DER.goal;

import DER.task.InformExpectedProduction;
import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.Goal;

public class InformedExpectedProduction extends Goal {

	public InformedExpectedProduction() {
		super();
		
		this.add(new InformExpectedProduction(), null);
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
