package nise.ajou.ac.kr.roch.event.handler;

import nise.ajou.ac.kr.roch.event.GoalRequest;
import nise.ajou.ac.kr.roch.event.GoalResponse;
import nise.ajou.ac.kr.roch.job.RolePlayerActivity;
import nise.ajou.ac.kr.simulationengine.Event;
import nise.ajou.ac.kr.simulationengine.EventHandler;
import nise.ajou.ac.kr.simulationengine.SimulationEngine;

public class GoalResponseHandler implements EventHandler {

	private RolePlayerActivity rolePlayerActivity;
	private GoalRequest request;
	
	public GoalResponseHandler(RolePlayerActivity rolePlayerActivity, GoalRequest request) {
		this.rolePlayerActivity = rolePlayerActivity;
		this.request = request;
	}
	
	@Override
	public boolean canProcess(Event evt) {
		boolean result = false;
		
		if (evt instanceof GoalResponse) {
			GoalResponse response = (GoalResponse) evt;
			
			if (response.getRequestId() == request.getUniqueId() &&
				response.getReceiverId() == request.getSenderId() &&
				response.getSenderId() == request.getReceiverId() &&
				response.getGoalName() == request.getGoalName()) {
				result = true;
			}
		}
		else {
			System.out.println("Event(" + evt.getClass().getCanonicalName() + ") is unknown goal request.");
		}
		
		return result;
	}
	
	@Override
	public void handle(Event evt) {
		if (evt instanceof GoalResponse) {
			rolePlayerActivity.setCompleted();
			SimulationEngine.getSimulationService().unsubscribe(GoalResponse.class, this);
		}
		else {
			System.out.println("Event(" + evt.getClass().getCanonicalName() + ") is unknown goal request.");
		}
	}
}
