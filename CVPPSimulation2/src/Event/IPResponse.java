package Event;

import nise.ajou.ac.kr.simulationengine.Event;

public class IPResponse extends IPPacket {

	public static final int SUCCESS = 200;
	public static final int CONNECTION_ERROR = 404;
	public static final int CANNOT_CONNECT_TO_SERVER = 503;
	
	int resultCode;

	public IPResponse(int senderId, int receiverId,
			int resultCode, Event body) {
		super(senderId, receiverId, body);
		
		this.resultCode = resultCode;
	}

	public int getResultCode() {
		return resultCode;
	}
}
