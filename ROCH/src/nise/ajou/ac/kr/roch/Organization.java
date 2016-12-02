package nise.ajou.ac.kr.roch;

import java.util.*;

/**
 * 
 */
public class Organization extends Agent {

	protected Set<Integer> members;

	/**
	 * Default constructor
	 */
	public Organization(long deliberationCycle) {
		super(deliberationCycle);
		
		members = new TreeSet<Integer>();
	}
	
	public Set<Integer> getMembers() {
		return members;
	}
	
	public void add(Integer memberId) {
		members.add(memberId);
	}
	
	public void remove(Agent memberId) {
		members.remove(memberId);
	}
}