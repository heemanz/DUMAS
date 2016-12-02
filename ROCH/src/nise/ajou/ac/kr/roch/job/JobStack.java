package nise.ajou.ac.kr.roch.job;

import java.util.Stack;

import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.Goal;
import nise.ajou.ac.kr.roch.Task;
import nise.ajou.ac.kr.roch.event.handler.JobTerminationHandler;

public class JobStack extends Stack<Job> {
	private static final long serialVersionUID = 52663723493010629L;

	private Agent owner;
	private JobTerminationHandler jobTerminationHandler;
	
	public JobStack(Agent owner) {
		this.owner = owner;
		this.jobTerminationHandler = null;
	}
	
	public JobStack(Agent owner, JobTerminationHandler jobTerminatedHandler) {
		this.owner = owner;
		this.jobTerminationHandler = jobTerminatedHandler;
	}
	
	public void push(Goal goal) {
		Task task = goal.getProperTask();
		Job newJob = Job.CreateJob(owner, goal, task);
		push(newJob);
	}
	
	public void terminateAchievedGoal() {
		Job achievedJob = null;
		
		for (Job job:this) {
			if (job.getGoal().isAchieved()) {
				achievedJob = job;
				break;
			}
			else {
				if (job instanceof CompositeJob) {
					CompositeJob cjob = (CompositeJob) job;
					if (cjob.isFinished() == true) {
						System.out.println(cjob.getGoal().getClass().getName() +
								" goal is inconsistent with " +
								cjob.getTask().getClass().getName() + 
								" task.");
					}
				}
				else if (job instanceof OrganizationalJob) {
					OrganizationalJob ojob = (OrganizationalJob) job;
					if (ojob.isFinished() == true) {
						System.out.println(ojob.getGoal().getClass().getName() +
								" goal is inconsistent with " +
								ojob.getTask().getClass().getName() + 
								" task.");
					}
				}
			}
		}
		
		if (achievedJob != null) {
			Job topMostJob = null;
			
			// Terminate Sub-jobs;
			while ((topMostJob = this.peek()) != achievedJob) {
				topMostJob.dispose();
				pop();
			}
			
			// Terminate achievedJob
			achievedJob.dispose();
			pop();
		}
	}
	
	public JobTerminationHandler getJobTerminatedHandler() {
		return this.jobTerminationHandler;
	}
	
	public String toString() {
		String result = "";
		for (Job job:this) {
			result += "| " + job.toString();
		}
		
		return result;
	}
}