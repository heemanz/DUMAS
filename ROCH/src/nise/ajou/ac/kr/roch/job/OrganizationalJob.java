package nise.ajou.ac.kr.roch.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nise.ajou.ac.kr.roch.Goal;
import nise.ajou.ac.kr.roch.Organization;
import nise.ajou.ac.kr.roch.OrganizationalTask;
import nise.ajou.ac.kr.roch.Role;
import nise.ajou.ac.kr.roch.activity.Activity;
import nise.ajou.ac.kr.roch.activity.Decision;
import nise.ajou.ac.kr.roch.activity.EndPoint;
import nise.ajou.ac.kr.roch.activity.Fork;
import nise.ajou.ac.kr.roch.activity.Join;
import nise.ajou.ac.kr.roch.activity.Node;
import nise.ajou.ac.kr.roch.activity.StartPoint;

public class OrganizationalJob extends Job {

	private List<Node> currents;
	
	private HashMap<Role, List<Integer>> rolePlayers;
	
	private OrganizationalTask task;
	
	public OrganizationalJob(Organization owner, Goal goal, OrganizationalTask task) {
		super(owner, goal);
		
		this.task = task;
		this.task.initialize(owner);
		
		rolePlayers = task.assignRolesTo(owner.getMembers());
		
		currents = new ArrayList<Node>();
		currents.add(this.task.getStartPoint());
	}
	
	public OrganizationalTask getTask() {
		return task;
	}
	
	public boolean isFinished() {
		return currents.size() == 1 && currents.get(0) instanceof EndPoint;
	}
	
	@Override
	public void advance(JobStack stack) {
		List<Node> newCurrents = new ArrayList<Node>();
		for (Node current:currents) {
			List<Node> nexts = getProperNexts(current);
			newCurrents.addAll(nexts);
		}
		
		currents.clear();
		currents = newCurrents;
	}
	
	private List<Node> getProperNexts(Node current) {
		List<Node> nexts = new ArrayList<Node>();
		
		if (current instanceof StartPoint) {
			addNext(nexts, ((StartPoint) current).getProperNext());
		}
		else if (current instanceof EndPoint) {
		}
		else if (current instanceof Decision) {
			addNext(nexts, ((Decision) current).getProperNext(task.getOwner()));
		}
		else if (current instanceof Activity) {
			addNexts(nexts, (Activity) current);
		}
		else if (current instanceof Fork) {
			addNexts(nexts, ((Fork) current).getProperNexts());
		}
		else if (current instanceof Join) {
			Join join = (Join) current;
			if (joinedAll(join)) {
				remove(join);
				addNext(nexts, join.getProperNext());
			}
		}
		else if (current instanceof RolePlayerActivity) {
			RolePlayerActivity rolePlayerActivity = (RolePlayerActivity) current;
			
			if (rolePlayerActivity.isComplete()) {
				if (completedAll(rolePlayerActivity)) {
					addNext(nexts, rolePlayerActivity.getActivity().getProperNext());
				}
				else {
					// rolePlayerActivity will be removed,
					// because it is complete.
				}
			}
			else { // not complete, yet.
				addNext(nexts, current);
			}
		}
		
		return nexts;
	}
	
	private boolean joinedAll(Join join) {
		boolean result = false;
		
		int count = 0;
		for (Node runningActivity:currents) {
			if (runningActivity instanceof Join) {
				Join runningJoin = (Join) runningActivity;
				if (join == runningJoin) {
					count++;
				}
			}
		}
		
		if (count == join.getPrevs().size()) {
			result = true;
		}
		
		return result;
	}
	
	private void remove(Node node) {
		int formerSize = 0;
		int latterSize = 0;
		
		do {
			formerSize = currents.size();
			currents.remove(node);
			latterSize = currents.size();
		} while (formerSize > latterSize);
	}
	
	private boolean completedAll(RolePlayerActivity rolePlayerActivity) {
		boolean result = false;
		
		int count = 0;
		
		for (Node runningActivity:currents) {
			if (runningActivity instanceof RolePlayerActivity) {
				RolePlayerActivity rpa = (RolePlayerActivity) runningActivity;
				if (rpa != rolePlayerActivity &&
					rpa.getActivity() == rolePlayerActivity.getActivity()) {
					count++;
				}
			}
		}
		
		if (count == 0) {
			result = true;
		}
		
		return result;
	}
	
	private void addNexts(List<Node> nexts, Activity activity) {
		Role role = task.findRole(activity.getGoal());
		
		for (Integer playerId:rolePlayers.get(role)) {
			RolePlayerActivity playerNext
					= new RolePlayerActivity(task.getOwner().getId(), activity, role, playerId);	
			nexts.add(playerNext);
		}
	}
	
	private void addNexts(List<Node> nexts, List<Node> forkedNexts) {
		nexts.addAll(forkedNexts);
	}
	
	private void addNext(List<Node> nexts, Node next) {
		nexts.add(next);
	}
	
	@Override
	public void dispose() {
		task.finalize();
		task = null;
		super.dispose();
	}
	
	public String toString() {
		String result = super.toString() + "->T:" + task.getClass().getName() + "\r\n";
		
		for (Node node:currents) {
			
			String nodeName = "";
			if (node instanceof Activity) {
				nodeName = ((Activity) node).getGoal().getClass().getName();
			}
			else if (node instanceof Decision) {
				nodeName = "Decision";
			}
			else if (node instanceof EndPoint) {
				nodeName = "EndPoint";
			}
			else if (node instanceof Fork) {
				nodeName = "Fork";
			}
			else if (node instanceof Join) {
				nodeName = "Join";
			}
			else if (node instanceof StartPoint) {
				nodeName = "StartPoint";
			}
			else {
				nodeName = "Unknown";
			}
			result += "    subactivity->" + nodeName + "\r\n";
		}
		
		return result;
	}
}
