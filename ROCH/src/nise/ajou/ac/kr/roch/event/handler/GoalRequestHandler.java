package nise.ajou.ac.kr.roch.event.handler;

import nise.ajou.ac.kr.roch.DeliberationProcess;
import nise.ajou.ac.kr.roch.Goal;
import nise.ajou.ac.kr.roch.event.GoalRequest;
import nise.ajou.ac.kr.simulationengine.Event;
import nise.ajou.ac.kr.simulationengine.EventHandler;

public class GoalRequestHandler implements EventHandler {

	private DeliberationProcess deliberationProcess;
	
	public GoalRequestHandler(DeliberationProcess deliberationProcess) {
		this.deliberationProcess = deliberationProcess;
	}

	@Override
	public boolean canProcess(Event evt) {
		boolean result = false;
		
		if (evt instanceof GoalRequest) {
			GoalRequest goalRequest = (GoalRequest) evt;
			
			if (goalRequest.getReceiverId() == deliberationProcess.getOwner().getId() &&
				deliberationProcess.getOwner().find(goalRequest.getGoalName()) != null) {
				result = true;
			}
		}
		else {
			System.out.println("Event(" + evt.getClass().getCanonicalName() + ") is unknown goal requiest.");
		}
		
		return result;
	}
	
	@Override
	public void handle(Event evt) {
		if (evt instanceof GoalRequest) {
			GoalRequest goalRequest = (GoalRequest) evt;
			Goal goal = deliberationProcess.getOwner().find(goalRequest.getGoalName());
			
			if (goal != null) {
				deliberationProcess.createJobStack(goal, new GoalTerminationHandler(goalRequest));
			}
		}
		else {
			System.out.println("Event(" + evt.getClass().getCanonicalName() + ") is unknown goal requiest.");
		}
	}
}
