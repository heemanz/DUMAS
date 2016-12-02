package DER.goal;

import DER.task.InformActualProduction;
import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.Goal;

public class InformedActualProduction extends Goal {

	public InformedActualProduction() {
		super();
		
		this.add(new InformActualProduction(), null);
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
