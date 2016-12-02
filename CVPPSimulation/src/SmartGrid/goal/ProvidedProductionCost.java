package SmartGrid.goal;

import SmartGrid.task.ProvideProductionCost;
import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.Goal;

public class ProvidedProductionCost extends Goal {

	public ProvidedProductionCost() {
		super();
		
		this.add(new ProvideProductionCost(), null);
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
