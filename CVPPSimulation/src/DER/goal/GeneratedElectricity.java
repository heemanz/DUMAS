package DER.goal;

import DER.task.GenerateElectricity;
import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.Goal;

public class GeneratedElectricity extends Goal {

	public GeneratedElectricity() {
		super();
		
		this.add(new GenerateElectricity(), null);
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
