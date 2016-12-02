package nise.ajou.ac.kr.roch.activity;

import org.javatuples.Pair;

import nise.ajou.ac.kr.roch.DefaultFitnessFunction;
import nise.ajou.ac.kr.roch.FitnessFunction;

public class StartPoint implements Node {

	private Pair<Node, FitnessFunction> next;
	
	public StartPoint() {
		// TODO Auto-generated constructor stub
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
	
	public Node getProperNext() {
		return next.getValue0();
	}
}
