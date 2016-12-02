package Event;

import nise.ajou.ac.kr.simulationengine.Event;

public abstract class IPPacket extends Message {

	private Event body;
	private boolean throughInternet;
	
	public IPPacket(int senderId, int receiverId, Event body) {
		super(senderId, receiverId);
		
		this.body = body;
		this.throughInternet = false;
	}

	public Event getBody() {
		return body;
	}
	
	public void setThroughInternet() {
		throughInternet = true;
	}
	
	public boolean throughInternet() {
		return throughInternet;
	}
}
