package Event;

import nise.ajou.ac.kr.simulationengine.Event;

public class IPRequest extends IPPacket {

	public IPRequest(int senderId, int receiverId, Event body) {
		super(senderId, receiverId, body);
	}
}
