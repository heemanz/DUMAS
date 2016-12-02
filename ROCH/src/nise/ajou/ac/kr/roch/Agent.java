package nise.ajou.ac.kr.roch;

import java.util.*;

import nise.ajou.ac.kr.roch.event.GoalRequest;
import nise.ajou.ac.kr.roch.event.handler.GoalRequestHandler;
import nise.ajou.ac.kr.simulationengine.EventHandler;
import nise.ajou.ac.kr.simulationengine.SimulationEngine;
import nise.ajou.ac.kr.simulationengine.SimulationObject;
import nise.ajou.ac.kr.simulationengine.TickHandler;

public class Agent extends SimulationObject {

	private List<Goal> goals;

	private HashMap<String, Object> attributes;

	private long deliberationCycle; // in miliseconds
	
	private DeliberationProcess deliberationProcess;
	
	private EventHandler goalRequestHandler;
	
	private TickHandler deliberation = new TickHandler() {
		@Override
		public void tick(long delta) {
			deliberationProcess.update();
		}
	};
	
	public Agent(long deliberationCycle) {
		setState(SimulationObject.State.created);
		
		this.goals = new ArrayList<Goal>();
		this.attributes = new HashMap<String, Object>();
		this.deliberationCycle = deliberationCycle;
		this.deliberationProcess = new DeliberationProcess(this);
		this.goalRequestHandler = new GoalRequestHandler(this.deliberationProcess);
	}
	
	public HashMap<String, Object> getAttributes() {
		return attributes;
	}

	public Object getAttribute(String key) {
		return attributes.get(key);
	}
	
	public void setAttribute(String key, Object value) {
		attributes.put(key, value);
	}
	
	public void add(Goal goal) {
		goals.add(goal);
	}
	
	public List<Goal> getGoals() {
		return goals;
	}
	
	public Goal find(String goalName) {
		Goal result = null;
		
		for (Goal goal:goals) {
			if (goal.getClass().getCanonicalName() == goalName) {
				result = goal;
				break;
			}
		}
		
		return result;
	}
	
	public void initialize() {
		SimulationEngine.getSimulationService().notifyPeriodically(deliberationCycle, deliberation);
		SimulationEngine.getSimulationService().subscribe(GoalRequest.class, goalRequestHandler);
	}

	public void finalize() {
		SimulationEngine.getSimulationService().cancelToNotify(deliberation);
		SimulationEngine.getSimulationService().unsubscribe(GoalRequest.class, goalRequestHandler);
	}
	
	public void displayDeliberation() {
		this.deliberationProcess.display();
	}
}