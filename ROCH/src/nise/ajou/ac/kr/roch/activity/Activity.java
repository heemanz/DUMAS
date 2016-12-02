package nise.ajou.ac.kr.roch.activity;

import nise.ajou.ac.kr.roch.DefaultFitnessFunction;
import nise.ajou.ac.kr.roch.FitnessFunction;
import nise.ajou.ac.kr.roch.Goal;

import org.javatuples.Pair;

public class Activity implements Node {
	private Goal goal;
	
	private Node prev;
	private Pair<Node, FitnessFunction> next;
	
	public Activity(Goal goal) {
		this.goal = goal;
	}

	public Pair<Node, FitnessFunction> getNext() {
		return next;
	}
	
	public void setNext(Node next, FitnessFunction fitnessFunction) {
		if (fitnessFunction == null) {
			fitnessFunction = DefaultFitnessFunction.getInstance();
		}
		this.next = new Pair<Node, FitnessFunction>(next, fitnessFunction);
	}

	public Node getPrev() {
		return prev;
	}
	
	public void setPrev(Node prev) {
		this.prev = prev;
	}
	
	public Goal getGoal() {
		return goal;
	}
	
	public Node getProperNext() {
		return next.getValue0();
	}
}