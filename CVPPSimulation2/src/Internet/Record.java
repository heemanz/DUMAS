package Internet;

public class Record {

	private NodeState state; 
	private double probabilityToFail;
	
	public Record(NodeState state,
			double probabilityToFail) {
		this.state = state;
		this.probabilityToFail = probabilityToFail;
	}
	
	public void setState(NodeState state) {
		this.state = state;
	}
	
	public NodeState getState() {
		return state;
	}
	
	public double getProbabilityToFail() {
		return probabilityToFail;
	}
	
}
