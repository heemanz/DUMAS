package nise.ajou.ac.kr.roch.activity;

import java.util.Set;
import java.util.TreeSet;

import org.javatuples.Pair;

import nise.ajou.ac.kr.roch.DefaultFitnessFunction;
import nise.ajou.ac.kr.roch.FitnessFunction;

public class Join implements Node {
	
	private Set<Node> prevs;
	private Pair<Node, FitnessFunction> next;
	
	public Join() {
		prevs = new TreeSet<Node>();
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
	
	public Set<Node> getPrevs() {
		return prevs;
	}
	
	public void addPrevs(Node prev) {
		prevs.add(prev);
	}
		
	public void removePrevs(Node prev) {
		prevs.remove(prev);
	}
	
	public Node getProperNext() {
		return next.getValue0();
	}
}
