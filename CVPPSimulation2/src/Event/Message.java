package Event;

import nise.ajou.ac.kr.simulationengine.Event;

public abstract class Message extends Event {

	private int receiverId;
	
	public Message(int senderId, int receiverId) {
		super(senderId);
		this.receiverId = receiverId;
	}

	public int getReceiverId() {
		return receiverId;
	}
}
