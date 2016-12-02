package VPP.goal;

import VPP.task.SubscribeToSmartGrid;
import DER.WindTurbine;
import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.Goal;

public class SubscribedToSmartGrid extends Goal {

	public SubscribedToSmartGrid() {
		super();
		
		add(new SubscribeToSmartGrid(), null);
	}

	@Override
	public boolean shouldPursue(Agent agent) {
		boolean result = false;
		
		boolean subscribed = (Boolean) agent.getAttribute(WindTurbine.KEY_SUBSCRIBED_TO_SG);
		if (!subscribed) {
			result = true;
		}
		
		return result;
	}

	@Override
	public boolean isAchieved() {
		boolean result = false;
		
		if (owner != null) {
			boolean subscribed = (Boolean) owner.getAttribute(WindTurbine.KEY_SUBSCRIBED_TO_SG);
			if (subscribed) {
				result = true;
			}
		}
		
		return result;
	}

}
