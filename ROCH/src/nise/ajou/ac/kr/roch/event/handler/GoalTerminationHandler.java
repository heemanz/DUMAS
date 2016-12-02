package nise.ajou.ac.kr.roch.event.handler;

import nise.ajou.ac.kr.roch.event.GoalRequest;
import nise.ajou.ac.kr.roch.event.GoalResponse;
import nise.ajou.ac.kr.simulationengine.SimulationEngine;

public class GoalTerminationHandler implements JobTerminationHandler {
	private GoalRequest request;
	
	public GoalTerminationHandler(GoalRequest request) {
		this.request = request;
	}
	
	@Override
	public void onTerminated() {
		GoalResponse response
			= new GoalResponse(request.getUniqueId(),
					request.getReceiverId(), request.getSenderId(),
					request.getGoalName(), null);
		
		SimulationEngine.getSimulationService().publish(response);
	}
};