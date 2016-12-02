package Internet.app.goal;

import Internet.app.task.HandleInternetError;
import util.InternetUtil;
import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.Goal;

public class HandledInternetError extends Goal {

	public HandledInternetError() {
		super();
		
		this.add(new HandleInternetError(), null);
	}

	@Override
	public boolean shouldPursue(Agent agent) {
		boolean result = false;
				
		if (agent.getAttribute(InternetUtil.KEY_INTERNET_ERROR) != null) {
			result = true;
		}
		
		return result;
	}

	@Override
	public boolean isAchieved() {
		boolean result = false;
		
		if (owner.getAttribute(InternetUtil.KEY_INTERNET_ERROR) == null) {
			result = true;
		}
		
		return result;
	}

}
