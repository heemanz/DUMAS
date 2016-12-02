package nise.ajou.ac.kr.roch.job;

import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.CompositeTask;
import nise.ajou.ac.kr.roch.Goal;
import nise.ajou.ac.kr.roch.activity.Activity;
import nise.ajou.ac.kr.roch.activity.Decision;
import nise.ajou.ac.kr.roch.activity.EndPoint;
import nise.ajou.ac.kr.roch.activity.Node;
import nise.ajou.ac.kr.roch.activity.StartPoint;

public class CompositeJob extends Job {

	private Node current;
	
	private CompositeTask task;
	
	public CompositeJob(Agent owner, Goal goal, CompositeTask task) {
		super(owner, goal);
		
		this.task = task;
		this.task.initialize(owner);
		
		current = this.task.getStartPoint();
	}

	public CompositeTask getTask() {
		return task;
	}
	
	@Override
	public void advance(JobStack stack) {
		Node next = getProperNext();
		
		if (next == null || next instanceof EndPoint) {
			// Task is terminated
		}
		else if (next instanceof Activity) {
			Activity activity = (Activity) next;
			
			if (activity.getGoal().shouldPursue(task.getOwner())) {
				stack.push(activity.getGoal());
				current = next;
			}
		}
		else {
			current = next;
		}
	}
	
	public boolean isFinished() {
		return current == null || current instanceof EndPoint;
	}
	
	protected Node getProperNext() {
		Node next = null;
		
		if (current instanceof StartPoint) {
			StartPoint startPoint = (StartPoint) current;
			next = startPoint.getProperNext();
		}
		else if (current instanceof EndPoint) {
			next = null;
		}
		else if (current instanceof Decision) {
			Decision decision = (Decision) current;
			next = decision.getProperNext(task.getOwner());
		}
		else if (current instanceof Activity) {
			Activity activity = (Activity) current;
			next = activity.getProperNext();
		}
		
		return next;
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
