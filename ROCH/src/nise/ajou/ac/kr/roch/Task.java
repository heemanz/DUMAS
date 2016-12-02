package nise.ajou.ac.kr.roch;

public abstract class Task {

	protected Agent owner;

	public Task() {
	}

	public void initialize(Agent owner) {
		this.owner = owner;
		
		registerHandlers();
	}
	
	public void finalize() {
		deregisterHandlers();
		
		this.owner = null;
	}
	
	public Agent getOwner() {
		return owner;
	}
	
	abstract protected void registerHandlers();
	abstract protected void deregisterHandlers();
}