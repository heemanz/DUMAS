package nise.ajou.ac.kr.roch.activity;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import nise.ajou.ac.kr.roch.FitnessFunction;

public class Fork implements Node {
	
	private Node prev;
	private List<Pair<Node, FitnessFunction>> nexts;

	public Fork() {
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
	
	public List<Node> getProperNexts() {
		List<Node> result = new ArrayList<Node>();
		
		for (Pair<Node, FitnessFunction> pair:nexts) {
			result.add(pair.getValue0());
		}
		
		return result;
	}
}
