package nise.ajou.ac.kr.roch.job;

import nise.ajou.ac.kr.roch.Goal;
import nise.ajou.ac.kr.roch.Role;
import nise.ajou.ac.kr.roch.activity.Activity;
import nise.ajou.ac.kr.roch.activity.Node;
import nise.ajou.ac.kr.roch.event.GoalRequest;
import nise.ajou.ac.kr.roch.event.GoalResponse;
import nise.ajou.ac.kr.roch.event.handler.GoalResponseHandler;
import nise.ajou.ac.kr.simulationengine.SimulationEngine;

public class RolePlayerActivity implements Node {

	private Integer organizationId;
	private Activity activity;
	private Role role;
	private Integer playerId;
	private boolean complete;
	
	public RolePlayerActivity(Integer organizationId, Activity activity, Role role, Integer playerId) {
		this.organizationId = organizationId;
		this.activity = activity;
		this.role = role;
		this.playerId = playerId;
		this.complete = false;
		
		delegate(activity.getGoal(), playerId);
	}
	
	public Activity getActivity() {
		return activity;
	}
	
	public Role getRole() {
		return role;
	}
	
	public Integer getPlayerId() {
		return playerId;
	}
	
	public boolean isComplete() {
		return complete;
	}
	
	public void setCompleted() {
		this.complete = true;
	}
	
	private void delegate(Goal goal, Integer playerId) {
		GoalRequest request = new GoalRequest(organizationId, playerId, goal.getClass().getCanonicalName());
		
		SimulationEngine.getSimulationService().subscribe(GoalResponse.class, new GoalResponseHandler(this, request));
		
		SimulationEngine.getSimulationService().publish(request);
	}
}
