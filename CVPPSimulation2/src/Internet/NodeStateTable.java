package Internet;

import java.util.HashMap;
import java.util.Set;

public class NodeStateTable {

	private HashMap<Integer, Record> table;
	
	public NodeStateTable() {
		table = new HashMap<Integer, Record>();
	}

	public void add(Integer agentId, double probabilityToFail) {
		table.put(agentId, new Record(NodeState.Active, probabilityToFail));
	}

	public Set<Integer> getNodeSet() {
		return table.keySet();
	}

	public Record getStateRecord(Integer agentId) {
		return table.get(agentId);
	}

	public NodeState getAgentNodeState(Integer agentId) {
		return table.get(agentId).getState();
	}
}
