package nise.ajou.ac.kr.roch.activity;

import java.util.Set;
import java.util.TreeSet;

public class EndPoint implements Node {

	private Set<Node> prevs;
	
	public EndPoint() {
		prevs = new TreeSet<Node>();
	}
	
	public Set<Node> getPrevs() {
		return prevs;
	}
	
	public void addPrev(Node prev) {
		prevs.add(prev);
	}
	
	public void removePrev(Node prev) {
		prevs.remove(prev);
	}
}
