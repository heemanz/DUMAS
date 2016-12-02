package Internet.app.goal;

import Event.IPResponse;
import Internet.app.task.HandleConnectionError;
import util.InternetUtil;
import util.TimeUtil;
import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.Goal;

public class HandledConnectionError extends Goal {

	public HandledConnectionError() {
		super();
		
		add(new HandleConnectionError(10 * TimeUtil.A_MINUTE), null);
	}

	@Override
	public boolean shouldPursue(Agent agent) {
		boolean result = false;
		
		if (agent.getAttribute(InternetUtil.KEY_INTERNET_ERROR) != null) {
			IPResponse response
				= (IPResponse) agent.getAttribute(
						InternetUtil.KEY_INTERNET_ERROR);
			
			if (response.getResultCode() == IPResponse.CONNECTION_ERROR)
			{
				result = true;
			}
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
