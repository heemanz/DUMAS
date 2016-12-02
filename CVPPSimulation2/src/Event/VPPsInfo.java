package Event;

public class VPPsInfo extends Message {

	public int vppId;
	
	public VPPsInfo(int senderId, int receiverId, int vppId) {
		super(senderId, receiverId);
		
		this.vppId = vppId;
	}

	public int getVPPId() {
		return vppId;
	}
}
