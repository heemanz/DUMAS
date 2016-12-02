package nise.ajou.ac.kr.roch.activity;

import java.util.ArrayList;
import java.util.List;
import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.FitnessFunction;

import org.javatuples.Pair;

public class Decision implements Node {

	private Node prev;
	private List<Pair<Node, FitnessFunction>> nexts;
	
	public Decision() {
		this.nexts = new ArrayList<Pair<Node, FitnessFunction>>();
	}

	public List<Pair<Node, FitnessFunction>> getNexts() {
		return nexts;
	}
	
	public void addNext(Pair<Node, FitnessFunction> next) {
		nexts.add(next);
	}

	public void removeNext(Pair<Node, FitnessFunction> next) {
		nexts.remove(next);
	}
	
	public Node getPrev() {
		return prev;
	}
	
	public void setPrev(Node prev) {
		this.prev = prev;
	}
	
	public Node getProperNext(Agent agent) {
		List<Pair<Node, FitnessFunction>> candidates = getNexts();
		double maxFitness = Double.MIN_VALUE;
		Node next = null;
		for (Pair<Node, FitnessFunction> candidate:candidates) {
			double fitness = candidate.getValue1().evaluate(agent);
			
			if (maxFitness < fitness) {
				maxFitness = fitness;
				next = candidate.getValue0();
			}
		}
		
		return next;
	}
}
