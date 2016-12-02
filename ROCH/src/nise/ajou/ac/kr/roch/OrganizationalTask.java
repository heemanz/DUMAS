package nise.ajou.ac.kr.roch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import nise.ajou.ac.kr.roch.activity.Fork;
import nise.ajou.ac.kr.roch.activity.Join;

public abstract class OrganizationalTask extends CompositeTask {

	protected List<Role> roles;
	
	public OrganizationalTask() {
		roles = new ArrayList<Role>();
	}

	public List<Role> getRoles() {
		return roles;
	}
	
	public Role findRole(Goal goal) {
		Role result = null;
		
		for (Role role:roles) {
			if (role.hasGoal(goal)) {
				result = role;
				break;
			}
		}
		
		return result;
	}
	
	public void add(Role role) {
		roles.add(role);
	}
	
	public abstract HashMap<Role, List<Integer>> assignRolesTo(Set<Integer> members);
	
	public static Fork createFork() {
		Fork fork = new Fork();
		
		return fork;
	}
	
	public static Join createJoin() {
		Join join = new Join();
		
		return join;
	}
}
