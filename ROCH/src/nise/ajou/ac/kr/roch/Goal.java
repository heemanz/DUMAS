package nise.ajou.ac.kr.roch;

import java.util.*;

import org.javatuples.Pair;

public abstract class Goal {
	
	protected Agent owner;
	
	private List<Pair<Task, FitnessFunction>> means;
	
	public Goal() {
		means = new ArrayList<Pair<Task, FitnessFunction>>();
	}

	abstract public boolean shouldPursue(Agent agent);

	abstract public boolean isAchieved();

	public void initialize(Agent owner) {
		this.owner = owner;
	}
	
	public void finalize() {
		this.owner = null;
	}
	
	public Task getProperTask() {
		double maxFitness = Double.MIN_VALUE;
		Task selectedTask = null;
		
		for (Pair<Task, FitnessFunction> taskInfo : means) {
			double fitness = taskInfo.getValue1().evaluate(owner);
			if (fitness >= maxFitness) {
				maxFitness = fitness;
				selectedTask = taskInfo.getValue0();
			}
		}
		
		return selectedTask;
	}
	
	public void add(Task task, FitnessFunction fitnessFunction) {
		if (fitnessFunction == null) {
			fitnessFunction = DefaultFitnessFunction.getInstance();
		}
		
		means.add(new Pair<Task, FitnessFunction>(task, fitnessFunction));
	}
}