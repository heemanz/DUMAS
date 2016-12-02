package nise.ajou.ac.kr.simulationengine;

public abstract class Event {
	
	private static int sequenceNumber = 0;
	
	private int senderId;
	private int uniqueId;
	
	public Event(int senderId) {
		this.uniqueId = sequenceNumber++;
		this.senderId = senderId;
	}
	
	public int getUniqueId() {
		return uniqueId;
	}
	
	public int getSenderId() {
		return senderId;
	}
		
	public static String InitialConfigurationFormat() {
		return "[{\"name\": \"generation-power\", \"type\": \"int\", \"units\": \"KW/h\"}, " +
			    " {\"name\": \"size\", \"type\": [{\"name\": \"x\", \"type\": \"double\", \"units\": \"m\"}, " +
				                       "{\"name\": \"x\", \"type\": \"double\", \"units\": \"m\"}, " +
			                           "{\"name\": \"x\", \"type\": \"double\", \"units\": \"m\"}]}]";
	}
}
