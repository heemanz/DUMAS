package nise.ajou.ac.kr.roch;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nise.ajou.ac.kr.roch.event.handler.JobTerminationHandler;
import nise.ajou.ac.kr.roch.job.Job;
import nise.ajou.ac.kr.roch.job.JobStack;
import nise.ajou.ac.kr.simulationengine.SimulationEngine;

public 	class DeliberationProcess {
	private Agent owner;
	private List<JobStack> jobStacks;
	
	public DeliberationProcess(Agent owner) {
		this.owner = owner;
		jobStacks = new ArrayList<JobStack>();
	}
	
	void update() {
		activateGoals();
		
		executeJobs();
		
		deactivateGoals();
		
		//display();
	}
	
	private void activateGoals() {
		List<Goal> goals = owner.getGoals();
		
		for (Goal goal:goals) {
			if (goal.shouldPursue(owner) && !isAchieving(goal)) {
				createJobStack(goal);
			}
		}
	}
	
	private void createJobStack(Goal goal) {
		JobStack newJobStack = new JobStack(owner);
		newJobStack.push(goal);
		jobStacks.add(newJobStack);
	}
	
	public void createJobStack(Goal goal, JobTerminationHandler handler) {
		JobStack newJobStack = new JobStack(owner, handler);
		newJobStack.push(goal);
		jobStacks.add(newJobStack);
		
		//owner.displayDeliberation();
		
		return;
	}
	
	private boolean isAchieving(Goal goal) {
		for (JobStack jobStack:jobStacks) {
			if (jobStack.get(0).getGoal() == goal) {
				return true;
			}
		}
		
		return false;
	}
	
	private void executeJobs() {
		for (JobStack jobStack:jobStacks) {
			Job currentJob = jobStack.peek();
			currentJob.advance(jobStack);
		}
	}
	
	private void deactivateGoals() {
		List<JobStack> jobStacksToBeRemoved = new ArrayList<JobStack>();
		
		for (JobStack jobStack:jobStacks) {
			jobStack.terminateAchievedGoal();
			
			if (jobStack.size() == 0) {
				jobStacksToBeRemoved.add(jobStack);
				
				if (jobStack.getJobTerminatedHandler() != null) {
					jobStack.getJobTerminatedHandler().onTerminated();
				}
			}
		}
		
		jobStacks.removeAll(jobStacksToBeRemoved);
	}
	
	public Agent getOwner() {
		return owner;
	}

	public void display() {
		int i = 0;
		System.out.println(new Date(SimulationEngine.getSimulationService().getTime()));
		for (JobStack jobStack:jobStacks) {
			String str = "Stack[" + i + "] " + jobStack.toString();
			System.out.println(str);
			i++;
		}
	}
};
