package nise.ajou.ac.kr.roch.job;

import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.Goal;
import nise.ajou.ac.kr.roch.Task;

public class PrimitiveJob extends Job {

	private Task task;
	
	public PrimitiveJob(Agent owner, Goal goal, Task task) {
		super(owner, goal);
		
		this.task = task;
		this.task.initialize(owner);
	}

	public Task getTask() {
		return task;
	}
	
	@Override
	public void advance(JobStack stack) {
		// do nothing, because primitive job waiting for the achievement of its goal.
	}
	
	@Override
	public void dispose() {
		task.finalize();
		task = null;
		super.dispose();
	}
	
	public String toString() {
		return super.toString() + "->T:" + task.getClass().getName();
	}
}
