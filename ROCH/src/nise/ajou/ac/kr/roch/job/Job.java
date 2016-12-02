package nise.ajou.ac.kr.roch.job;

import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.CompositeTask;
import nise.ajou.ac.kr.roch.Goal;
import nise.ajou.ac.kr.roch.Organization;
import nise.ajou.ac.kr.roch.OrganizationalTask;
import nise.ajou.ac.kr.roch.Task;

public abstract class Job {
	protected Goal goal;
	
	protected Job(Agent owner, Goal goal) {
		goal.initialize(owner);
		
		this.goal = goal;
	}
	
	public Goal getGoal() {
		return goal;
	}
	
	public void dispose() {
		goal.finalize();
		goal = null;
	}
	
	public abstract void advance(JobStack stack);
	
	public static Job CreateJob(Agent owner, Goal goal, Task task) {
		Job result = null;
		
		if (owner instanceof Organization && task instanceof OrganizationalTask) {
			result = new OrganizationalJob((Organization) owner, goal, (OrganizationalTask) task);
		}
		else if (task instanceof CompositeTask) {
			result = new CompositeJob(owner, goal, (CompositeTask) task);
		}
		else {
			result = new PrimitiveJob(owner, goal, task);
		}
		
		return result;
	}
	
	public String toString() {
		return "G:" + goal.getClass().getName();
	}
}
