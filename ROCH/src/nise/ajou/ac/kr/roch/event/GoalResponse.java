package nise.ajou.ac.kr.roch.event;

import nise.ajou.ac.kr.simulationengine.Event;

public class GoalResponse extends Event {

	private int requestId;
	private int targetId;
	private String goalName;
	private String result;
	
	public GoalResponse(int requestId, int senderId, int targetId,
			String goalName, String result) {
		super(senderId);
		
		this.requestId = requestId;
		this.targetId = targetId;
		this.goalName = goalName;
		this.result = result;
	}

	public int getRequestId() {
		return requestId;
	}
	
	public int getReceiverId() {
		return targetId;
	}
	
	public String getGoalName() {
		return goalName;
	}
	
	public String getResult() {
		return result;
	}
}
