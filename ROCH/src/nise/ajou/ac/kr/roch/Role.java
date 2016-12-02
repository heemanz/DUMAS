package nise.ajou.ac.kr.roch;

import java.util.*;

/**
 * 
 */
public abstract class Role {

	public List<Goal> capabilities;

	public Role() {
		capabilities = new ArrayList<Goal>();
	}
	
	abstract public double calculateFitnessOf(Agent member);

	public void add(Goal goal) {
		capabilities.add(goal);
	}
	
	public boolean hasGoal(Goal goal) {
		return capabilities.contains(goal);
	}
	
	public Goal find(Class<? extends Goal> goalClass) {
		for (Goal goal:capabilities) {
			if (goal.getClass() == goalClass) {
				return goal;
			}
		}
		
		return null;
	}
}