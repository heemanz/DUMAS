package Event;

import java.util.Date;
import java.util.List;

public class CollectedExpectedProductionInfo extends Message {

	private Date date;
	private List<Double> expectedProductionList;
	
	public CollectedExpectedProductionInfo(int senderId, int receiverId,
			Date date, List<Double> expectedProductionList) {
		super(senderId, receiverId);
		
		this.date = date;
		this.expectedProductionList = expectedProductionList;
	}

	public Date getDate() {
		return date;
	}
	
	public List<Double> getExpectationList() {
		return expectedProductionList;
	}
}
