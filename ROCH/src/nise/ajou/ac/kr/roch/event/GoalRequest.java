package nise.ajou.ac.kr.roch.event;

import nise.ajou.ac.kr.simulationengine.Event;

public class GoalRequest extends Event {

	private int targetId;
	private String goalName;
	
	public GoalRequest(int senderId, int targetId, String goalName) {
		super(senderId);
		
		this.targetId = targetId;
		this.goalName = goalName;
	}

	public int getReceiverId() {
		return targetId;
	}
	
	public String getGoalName() {
		return goalName;
	}
}
