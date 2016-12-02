package DER.goal;

import DER.task.ReceiveProductionCost;
import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.Goal;

public class ReceivedProductionCost extends Goal {

	public ReceivedProductionCost() {
		super();
		
		this.add(new ReceiveProductionCost(), null);
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
