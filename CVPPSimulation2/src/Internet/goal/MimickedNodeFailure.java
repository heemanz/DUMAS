package Internet.goal;

import Internet.task.MimicNodeFailure;
import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.Goal;

public class MimickedNodeFailure extends Goal {

	public MimickedNodeFailure() {
		super();
		
		add(new MimicNodeFailure(), null);
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
