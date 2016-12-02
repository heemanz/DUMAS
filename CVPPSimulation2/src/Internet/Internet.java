package Internet;

import Internet.goal.MimickedNodeFailure;
import Internet.goal.ProcessedRequest;
import nise.ajou.ac.kr.roch.Agent;

public class Internet extends Agent {

	public static final String KEY_NODE_STATE_TABLE = "NodeStateTable";
	
	private NodeStateTable nodeStateTable;
	
	public Internet(long deliberationCycle) {
		super(deliberationCycle);

		add(new MimickedNodeFailure());
		add(new ProcessedRequest());
		
		nodeStateTable = new NodeStateTable();
		setAttribute(KEY_NODE_STATE_TABLE, nodeStateTable);
	}

	public void add(Integer agentId, double probabilityToFail) {
		nodeStateTable.add(agentId, probabilityToFail);
	}
}
