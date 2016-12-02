package DER.goal;

import DER.WindTurbine;
import DER.task.SubscribeToVPP;
import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.Goal;

public class SubscribedToVPP extends Goal {

	public SubscribedToVPP() {
		super();
		
		this.add(new SubscribeToVPP(), null);
	}

	@Override
	public boolean shouldPursue(Agent agent) {
		boolean result = false;
		
		boolean subscribedToVPP = (Boolean) agent.getAttribute(WindTurbine.KEY_SUBSCRIBED_TO_VPP);
		boolean subscribedToSG = (Boolean) agent.getAttribute(WindTurbine.KEY_SUBSCRIBED_TO_SG);
		
		if (!subscribedToVPP && subscribedToSG) {
			result = true;
		}
		
		return result;
	}

	@Override
	public boolean isAchieved() {
		boolean result = false;
		
		if (owner != null) {
			boolean subscribedToVPP = (Boolean) owner.getAttribute(WindTurbine.KEY_SUBSCRIBED_TO_VPP);
			if (subscribedToVPP) {
				result = true;
			}
		}
		
		return result;
	}

}
